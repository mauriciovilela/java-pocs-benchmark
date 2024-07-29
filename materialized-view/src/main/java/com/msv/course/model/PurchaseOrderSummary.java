package com.msv.course.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PurchaseOrderSummary {

    @Id
    private String state;
    private Double totalSale;

}
