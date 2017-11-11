package com.service;


import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseMarket;
import com.model.Outcome;
import com.util.FeedMeConsumerUtility;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOutcomeHandler implements OutcomeHandler {

    private final EventRetrieverService eventRetrieverService;
    private final RepositoryService repositoryService;
    private static final Logger LOGGER = LogManager.getLogger(CreateOutcomeHandler.class);

    @Autowired
    public CreateOutcomeHandler(EventRetrieverService eventRetrieverService, RepositoryService repositoryService) {
        this.eventRetrieverService = eventRetrieverService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Outcome outcome, String eventId) {
        LOGGER.debug("Handling outcome with Id: "+ outcome.getOutcomeId());

        eventRetrieverService.getBaseEvent(eventId).ifPresent(event -> {

            for(BaseMarket market : event.getMarkets()){
                if(market.getMarketId().equalsIgnoreCase(outcome.getMarketId())){
                    market.getOutcomes().add(outcome);
                    break;
                }
            }

            FeedMeConsumerUtility.transform(event).
                    ifPresent(document -> repositoryService.insertDocument(document));
        });

    }

}
