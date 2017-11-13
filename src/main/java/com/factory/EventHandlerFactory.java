package com.factory;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventHandlerFactory {

    private static final Logger LOGGER = LogManager.getLogger(EventHandlerFactory.class);
    private final CreateEventHandler createEventHandler;
    private final UpdateEventHandler updateEventHandler;

    @Autowired
    public EventHandlerFactory(CreateEventHandler createEventHandler, UpdateEventHandler updateEventHandler) {

        this.createEventHandler = createEventHandler;
        this.updateEventHandler = updateEventHandler;
    }

    public Optional<EventHandler> getHandler(String operation){

        if(Operation.CREATE.getName().equalsIgnoreCase(operation))
            return Optional.of(createEventHandler);
        else if(Operation.UPDATE.getName().equalsIgnoreCase(operation))
            return Optional.of(updateEventHandler);
        else {
            LOGGER.error("Unable to find EventHandler for operation " + operation);
            return Optional.empty();
        }
    }
}
