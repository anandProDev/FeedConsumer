package com;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.service.FeedReceiver;

@Component
public class CommandAppStartupRunner implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(CommandAppStartupRunner.class);

    private FeedReceiver feedReceiver;

    @Autowired
    public CommandAppStartupRunner(FeedReceiver feedReceiver) {
        this.feedReceiver = feedReceiver;
    }

    @Override
    public void run(String...args){
        try{
            feedReceiver.receiveFeeds();

        }
        catch (Exception e){
            LOGGER.error("Error processing feeds", e);
        }
    }
}