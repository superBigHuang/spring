package com.huang.springcloud.controller;

import com.huang.springcloud.entity.CommonResult;
import com.huang.springcloud.entity.Payment;
import com.huang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // 开启服务发现，讲自己注册进eureka后，eureka能获得改服务的一些相关信息
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;



    @PostMapping("/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****插入结果：" + result);
        if (result > 0) {
            return new CommonResult(200, "插入成功,serverPort:" + serverPort, result);
        } else {
            return new CommonResult(444, "插入失败", null);
        }
    }

    @GetMapping("/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("aaa查询结果：" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功,serverPort:" + serverPort, payment);
        } else {
            return new CommonResult(444, "查询失败", null);
        }
    }


    @GetMapping("/discovety")
    public Object discovety() {
        // 有哪些服务
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("service:" + service);
        }
        // 通过服务名称获得服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("Service Id"+instance.getServiceId());
            log.info("主机名称"+instance.getHost());
            log.info("端口号"+instance.getPort());
            log.info("服务URI"+instance.getUri());
        }
        return this.discoveryClient;
    }

}
