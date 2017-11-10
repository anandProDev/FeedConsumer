package com.transformer;

import com.domain.BaseMarket;
import com.model.Market;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BaseMarketTransformerImpl implements BaseMarketTransformer {

    @Override
    public BaseMarket transform(Market market) {
        return BaseMarket.BaseMarketBuilder.aBaseMarket().withHeader(market.getHeader())
                .withEventId(market.getEventId())
                .withMarketId(market.getMarketId())
                .withName(market.getName())
                .withDisplayed(market.isDisplayed())
                .withSuspended(market.isSuspended())
                .withOutcomes(new ArrayList<>())
                .build();
    }
}
