package com.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class FeedMeClient {

    @Autowired
    TheClient client;

    @FeignClient(name = "FeedMe")
    interface TheClient {

        @RequestMapping(path = "/FeedMe/status", method = RequestMethod.GET)
        @ResponseBody
        String getStatus();
    }

    public String getStatus() {
        return client.getStatus();
    }
}
