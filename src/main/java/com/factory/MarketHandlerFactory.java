package com.factory;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MarketHandlerFactory {

    private static final Logger LOGGER = LogManager.getLogger(MarketHandlerFactory.class);
    private final CreateMarketHandler createMarketHandler;
    private final UpdateMarketHandler updateMarketHandler;

    @Autowired
    public MarketHandlerFactory(CreateMarketHandler createMarketHandler, UpdateMarketHandler updateMarketHandler) {
        this.createMarketHandler = createMarketHandler;
        this.updateMarketHandler = updateMarketHandler;
    }

    public Optional<MarketHander> getHandler(String operation){

        if(Operation.CREATE.getName().equalsIgnoreCase(operation))
            return Optional.of(createMarketHandler);
        else if(Operation.UPDATE.getName().equalsIgnoreCase(operation))
            return Optional.of(updateMarketHandler);
        else {
            LOGGER.error("Unable to find MarketHander for operation " + operation);
            return Optional.empty();
        }
    }
}
