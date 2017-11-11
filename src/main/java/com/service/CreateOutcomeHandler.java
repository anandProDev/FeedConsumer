package com.service;


import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.model.Market;
import com.model.Outcome;
import com.util.FeedMeConsumerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateOutcomeHandler implements OutcomeHandler {

    private final EventRetrieverService eventRetrieverService;
    private final RepositoryService repositoryService;

    @Autowired
    public CreateOutcomeHandler(EventRetrieverService eventRetrieverService, RepositoryService repositoryService) {
        this.eventRetrieverService = eventRetrieverService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Outcome outcome, String eventId) {

        Optional<BaseEvent> baseEvent = eventRetrieverService.getBaseEvent(eventId);

        baseEvent.ifPresent(event -> {

            for(BaseMarket market : event.getMarkets()){
                if(market.getMarketId().equalsIgnoreCase(outcome.getMarketId())){
                    market.getOutcomes().add(outcome);
                    break;
                }
            }

            FeedMeConsumerUtility.transform(baseEvent.get()).
                    ifPresent(document -> repositoryService.insertDocument(document));
        });

    }

}
