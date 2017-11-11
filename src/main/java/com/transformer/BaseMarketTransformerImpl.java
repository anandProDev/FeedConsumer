package com.transformer;

import com.domain.BaseMarket;
import com.model.Market;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class BaseMarketTransformerImpl implements BaseMarketTransformer {


    private static final Logger LOGGER = LogManager.getLogger(BaseMarketTransformerImpl.class);

    @Override
    public Optional<BaseMarket> transform(Market market) {

        try {
            return Optional.of(BaseMarket.BaseMarketBuilder.aBaseMarket().withHeader(market.getHeader())
                    .withEventId(market.getEventId())
                    .withMarketId(market.getMarketId())
                    .withName(market.getName())
                    .withDisplayed(market.isDisplayed())
                    .withSuspended(market.isSuspended())
                    .withOutcomes(new ArrayList<>())
                    .build());

        } catch (Exception e){

            LOGGER.error("Unable to transoform market to baseMarket " + market, e);
            return Optional.empty();
        }
    }
}
