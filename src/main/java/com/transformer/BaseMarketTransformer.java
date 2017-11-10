package com.transformer;


import com.domain.BaseMarket;
import com.model.Market;

public interface BaseMarketTransformer {

    BaseMarket transform(Market market);
}
