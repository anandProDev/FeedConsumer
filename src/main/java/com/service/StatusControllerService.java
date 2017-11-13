package com.service;

import com.factory.FeedMeClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/status")
public class StatusControllerService {

    private static final Logger LOGGER = LogManager.getLogger(StatusControllerService.class);
    private FeedMeClient feedMeClient;

    private String applicationName;

    @Autowired
    public StatusControllerService(FeedMeClient feedMeClient,
                                   @Value("${application.name}") String applicationName) {
        this.feedMeClient = feedMeClient;
        this.applicationName = applicationName;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String status() {
        String appStatus =  "*** OK from "+ applicationName+ "\n ";

        try{
            return appStatus + " "+feedMeClient.getStatus();
        } catch (Exception e){
            LOGGER.error(" Something went wrong with FeedMe" ,e);
            return appStatus + " Status of feedMeProducer "+ e;
        }

    }
}
