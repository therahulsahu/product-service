package com.product.client;

import com.product.model.EmailDetails;
import com.product.model.Smsrequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-notification-client", url = "http://localhost:8081")
public interface EmailNotificationClient {

    @PostMapping("/sendMail")
    String sendMail(@RequestBody EmailDetails emailDetails);

    @PostMapping("/sendmessage")
    String sendMessage(@RequestBody Smsrequest smsrequest);

}
