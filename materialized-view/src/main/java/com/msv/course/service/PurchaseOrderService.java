package com.msv.course.service;

import com.msv.course.dto.PurchaseOrderSummaryDto;
import com.msv.course.dto.PurchaseOrderSummaryViewDto;
import com.msv.course.repository.PurchaseOrderRepository;
import com.msv.course.repository.PurchaseOrderSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderSummaryRepository purchaseOrderSummaryRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrderSummaryDto> findByView() {
        return this.purchaseOrderSummaryRepository.findAll()
                        .stream()
                        .map(pos -> {
                            PurchaseOrderSummaryDto dto = new PurchaseOrderSummaryDto();
                            dto.setState(pos.getState());
                            dto.setTotalSale(pos.getTotalSale());
                            return dto;
                        })
                        .toList();
    }

    public List<PurchaseOrderSummaryViewDto> findByQuery() {
        return purchaseOrderRepository.findByQuery();
    }

}
