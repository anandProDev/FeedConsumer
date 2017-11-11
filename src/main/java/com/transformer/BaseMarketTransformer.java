package com.transformer;


import com.domain.BaseMarket;
import com.model.Market;

import java.util.Optional;

public interface BaseMarketTransformer {

    Optional<BaseMarket> transform(Market market);
}
