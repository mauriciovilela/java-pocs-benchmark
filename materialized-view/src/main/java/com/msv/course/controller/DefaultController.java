package com.msv.course.controller;

import com.msv.course.dto.PurchaseOrderSummaryDto;
import com.msv.course.dto.PurchaseOrderSummaryViewDto;
import com.msv.course.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class DefaultController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping("/query-with-tables")
    public List<PurchaseOrderSummaryViewDto> findByQuery(){
        return this.purchaseOrderService.findByQuery();
    }

    @GetMapping("/query-with-view")
    public List<PurchaseOrderSummaryDto> findByView(){
        return this.purchaseOrderService.findByView();
    }

}