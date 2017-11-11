package com.service;


import com.db.CouchbaseRepositoryServiceImpl;
import com.db.EventRetrieverService;
import com.domain.BaseMarket;
import com.model.Outcome;
import com.util.FeedMeConsumerUtility;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateOutcomeHandler implements OutcomeHandler {

    private static final Logger LOGGER = LogManager.getLogger(UpdateOutcomeHandler.class);
    private final EventRetrieverService eventRetrieverService;
    private final CouchbaseRepositoryServiceImpl repositoryService;

    @Autowired
    public UpdateOutcomeHandler(EventRetrieverService eventRetrieverService, CouchbaseRepositoryServiceImpl repositoryService) {
        this.eventRetrieverService = eventRetrieverService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Outcome outcome, String eventId) {

        LOGGER.debug("Handling Outcome feed with Id: "+ outcome.getOutcomeId());

        eventRetrieverService.getBaseEvent(eventId).ifPresent( event -> {
            List<BaseMarket> markets = event.getMarkets();
            findAndUpdateOutcome(outcome, markets);

            FeedMeConsumerUtility.transformToJsonDoc(event).ifPresent(
                    document -> repositoryService.updateDocument(document));
        });
    }

    private void findAndUpdateOutcome(Outcome outcome, List<BaseMarket> markets) {
        for(BaseMarket market : markets){
            if(market.getMarketId().equalsIgnoreCase(outcome.getMarketId())){
                updateOutcome(outcome, market);
                break;
            }
        }
    }

    private void updateOutcome(Outcome outcome, BaseMarket market) {
        for(Outcome outcome1 : market.getOutcomes()) {
            if(outcome1.getOutcomeId().equalsIgnoreCase(outcome.getOutcomeId())){
                market.getOutcomes().remove(outcome1);
                market.getOutcomes().add(outcome);
                break;
            }
        }
    }
}
