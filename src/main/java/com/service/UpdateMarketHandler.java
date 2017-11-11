package com.service;

import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseMarket;
import com.model.Market;
import com.transformer.BaseMarketTransformer;
import com.util.FeedMeConsumerUtility;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


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

        LOGGER.debug("Handling Market feed with Id: "+ marketFeed.getMarketId());
        baseMarketTransformer.transform(marketFeed).ifPresent(baseMarket -> {

            eventRetrieverService.getBaseEvent(baseMarket.getEventId()).ifPresent( baseEvent -> {
                List<BaseMarket> markets = baseEvent.getMarkets();
                updateMarket(baseMarket, markets);
                   FeedMeConsumerUtility.transformToJsonDoc(baseEvent)
                           .ifPresent(document -> repositoryService.updateDocument(document));
            });

        });

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
