package com.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OutcomeHandlerFactory {

    CreateOutcomeHandler createOutcomeHandler;
    UpdateOutcomeHandler updateOutcomeHandler;
    private static final Logger LOGGER = LogManager.getLogger(OutcomeHandlerFactory.class);

    @Autowired
    public OutcomeHandlerFactory(CreateOutcomeHandler createOutcomeHandler, UpdateOutcomeHandler updateOutcomeHandler) {
        this.createOutcomeHandler = createOutcomeHandler;
        this.updateOutcomeHandler = updateOutcomeHandler;
    }

    public Optional<OutcomeHandler> getHandler(String operation){

        if(Operation.CREATE.getName().equalsIgnoreCase(operation))
            return Optional.of(createOutcomeHandler);
        else if(Operation.UPDATE.getName().equalsIgnoreCase(operation))
            return Optional.of(updateOutcomeHandler);
        else {
            LOGGER.error("Unable to find OutcomeHandler for operation " + operation);
            return Optional.empty();
        }

    }

}
