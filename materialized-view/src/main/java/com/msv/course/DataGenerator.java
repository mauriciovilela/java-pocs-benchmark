package com.msv.course;

import com.msv.course.model.Product;
import com.msv.course.model.PurchaseOrder;
import com.msv.course.model.User;
import com.msv.course.repository.ProductRepository;
import com.msv.course.repository.PurchaseOrderRepository;
import com.msv.course.repository.PurchaseOrderSummaryRepository;
import com.msv.course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataGenerator implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderSummaryRepository purchaseOrderSummaryRepository;

    private static final int TOTAL_USERS = 10000;
    private static final int TOTAL_PRODUCTS = 1000;
    private static final int TOTAL_ORDERS = 100_000;

    @Override
    public void run(String... args) {
        purchaseOrderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        this.createUsers();
        log.info("Created users");

        this.createProducts();
        log.info("Created products");

        this.createOrders();
        log.info("Created orders");

        purchaseOrderSummaryRepository.refreshMaterializedView();
        log.info("Refreshed view");
    }

    private void createUsers() {
        List<String> states = Arrays.asList("AC", "AL", "AP", "AM",
                "BA", "CE", "DF", "ES",
                "GO", "MA", "MS", "MT",
                "MG", "PA", "PB", "PR",
                "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC",
                "SP", "SE", "TO");
        List<User> users = IntStream.range(0, TOTAL_USERS)
                .mapToObj(i -> {
                    User user = new User();
                    user.setFirstName(RandomStringUtils.randomAlphabetic(10));
                    int idx = RandomUtils.nextInt(0, states.size());
                    user.setState(states.get(idx));
                    return user;
                })
                .toList();
        this.userRepository.saveAll(users);
    }

    private void createProducts() {
        List<Product> products = IntStream.range(0, TOTAL_PRODUCTS)
                .mapToObj(i -> {
                    int price = RandomUtils.nextInt(0, 500);
                    Product product = new Product();
                    product.setDescription(RandomStringUtils.randomAlphabetic(10));
                    product.setPrice(price);
                    return product;
                })
                .toList();
        this.productRepository.saveAll(products);
    }

    private void createOrders() {
        List<Product> products = this.productRepository.findAll();
        List<User> users = this.userRepository.findAll();
        List<PurchaseOrder> purchaseOrders = IntStream.rangeClosed(0, TOTAL_ORDERS)
                .parallel()
                .mapToObj(i -> {
                    int userIndex = RandomUtils.nextInt(0, TOTAL_USERS);
                    int prodIndex = RandomUtils.nextInt(0, TOTAL_PRODUCTS);
                    PurchaseOrder purchaseOrder = new PurchaseOrder();
                    purchaseOrder.setUserId(users.get(userIndex).getId());
                    purchaseOrder.setProductId(products.get(prodIndex).getId());
                    return purchaseOrder;
                })
                .toList();
        this.purchaseOrderRepository.saveAll(purchaseOrders);
    }

}
