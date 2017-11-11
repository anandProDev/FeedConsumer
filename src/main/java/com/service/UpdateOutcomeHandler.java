package com.service;


import com.couchbase.client.java.document.JsonDocument;
import com.db.CouchbaseRepositoryServiceImpl;
import com.db.EventRetrieverService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.model.Outcome;
import com.util.FeedMeConsumerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UpdateOutcomeHandler implements OutcomeHandler {

    private final EventRetrieverService eventRetrieverService;
    private final CouchbaseRepositoryServiceImpl repositoryService;

    @Autowired
    public UpdateOutcomeHandler(EventRetrieverService eventRetrieverService, CouchbaseRepositoryServiceImpl repositoryService) {
        this.eventRetrieverService = eventRetrieverService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Outcome outcome, String eventId) {

        Optional<BaseEvent> baseEvent = eventRetrieverService.getBaseEvent(eventId);

        baseEvent.ifPresent( event -> {
            List<BaseMarket> markets = event.getMarkets();
            findAndUpdateOutcome(outcome, markets);
        });
        FeedMeConsumerUtility.transform(baseEvent.get()).ifPresent(
                document -> repositoryService.updateDocument(document));

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
