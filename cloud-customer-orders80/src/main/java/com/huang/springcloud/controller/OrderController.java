package com.huang.springcloud.controller;

import com.huang.springcloud.entity.CommonResult;
import com.huang.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderController {

    // 单机，直接写死
    // public static final String PAYMENT_URL = "http://localhost:8001/payment";
    // 从Eureka获得地址，实现负载均衡
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/payment";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/create",
                payment,
                CommonResult.class);
    }

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/getPaymentById/" + id, CommonResult.class);
    }

}
