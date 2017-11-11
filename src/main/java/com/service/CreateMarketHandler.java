package com.service;


import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.model.Market;
import com.transformer.BaseMarketTransformer;
import com.util.FeedMeConsumerUtility;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateMarketHandler implements MarketHander {

    private static final Logger LOGGER = LogManager.getLogger(CreateMarketHandler.class);
    BaseMarketTransformer baseMarketTransformer;
    EventRetrieverService eventRetrieverService;
    RepositoryService repositoryService;

    @Autowired
    public CreateMarketHandler(BaseMarketTransformer baseMarketTransformer,
                               EventRetrieverService eventRetrieverService,
                               RepositoryService repositoryService) {
        this.baseMarketTransformer = baseMarketTransformer;
        this.eventRetrieverService = eventRetrieverService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Market market) {
        LOGGER.debug("Handling market event " + market.getMarketId());

        baseMarketTransformer.transform(market).ifPresent(baseMarket ->  {
                    eventRetrieverService.getBaseEvent(baseMarket.getEventId())
                        .ifPresent( baseEvent -> {
                            baseEvent.getMarkets().add(baseMarket);
                            FeedMeConsumerUtility.transform(baseEvent).
                                    ifPresent(document -> repositoryService.insertDocument(document));
            });
        });
    }
}
