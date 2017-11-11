package com.service;

import com.couchbase.client.java.document.JsonDocument;
import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.model.Market;
import com.transformer.BaseMarketTransformer;
import com.util.FeedMeConsumerUtility;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UpdateMarketHandler implements MarketHander {


    private static final Logger LOGGER = LogManager.getLogger(UpdateMarketHandler.class);
    BaseMarketTransformer baseMarketTransformer;
    EventRetrieverService eventRetrieverService;
    RepositoryService repositoryService;

    @Autowired
    public UpdateMarketHandler(BaseMarketTransformer baseMarketTransformer,
                              EventRetrieverService eventRetrieverService,
                              RepositoryService repositoryService) {
        this.baseMarketTransformer = baseMarketTransformer;
        this.eventRetrieverService = eventRetrieverService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Market marketFeed) {
        BaseMarket baseMarket = baseMarketTransformer.transform(marketFeed);

        Optional<BaseEvent> baseEventOptional = eventRetrieverService.getBaseEvent(baseMarket.getEventId());

        baseEventOptional.ifPresent( baseEvent -> {
            List<BaseMarket> markets = baseEvent.getMarkets();
            updateMarket(baseMarket, markets);
        });

       FeedMeConsumerUtility.transform(baseEventOptional.get())
               .ifPresent(document -> repositoryService.updateDocument(document));
    }

    protected void updateMarket(BaseMarket baseMarket, List<BaseMarket> markets) {
        for(BaseMarket market : markets){
            if(baseMarket.getMarketId().equalsIgnoreCase(market.getMarketId())) {
                markets.remove(market);
                baseMarket.getOutcomes().addAll(market.getOutcomes());
                markets.add(baseMarket);
                break;
            }
        }
    }
}
