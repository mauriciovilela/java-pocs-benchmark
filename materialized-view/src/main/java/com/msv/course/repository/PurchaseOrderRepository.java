package com.msv.course.repository;

import com.msv.course.dto.PurchaseOrderSummaryViewDto;
import com.msv.course.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query(nativeQuery = true, value =
            """
                select u.state, sum(p.price) as total_sale
                from users u, product p, purchase_order po
                where u.id = po.user_id and p.id = po.product_id
                group by u.state order by u.state
            """)
    List<PurchaseOrderSummaryViewDto> findByQuery();

}
