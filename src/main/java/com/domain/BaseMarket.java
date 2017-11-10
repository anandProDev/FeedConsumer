package com.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.Header;
import com.model.Outcome;

import java.util.ArrayList;
import java.util.List;

public class BaseMarket {

    @JsonProperty(value = "header", required = true)
    Header header;
    @JsonProperty(value = "eventId", required = true)
    String eventId;
    @JsonProperty(value = "marketId", required = true)
    String marketId;
    @JsonProperty(value = "name", required = true)
    String name;
    @JsonProperty(value = "displayed", required = true)
    boolean displayed;
    @JsonProperty(value = "suspended", required = true)
    boolean suspended;
    @JsonProperty(value = "outcomes", required = true)
    List<Outcome> outcomes = new ArrayList<>();

    public Header getHeader() {
        return header;
    }

    public String getEventId() {
        return eventId;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    @Override
    public String toString() {
        return "BaseMarket{" +
                "header=" + header +
                ", eventId='" + eventId + '\'' +
                ", marketId='" + marketId + '\'' +
                ", name='" + name + '\'' +
                ", displayed=" + displayed +
                ", suspended=" + suspended +
                ", outcomes=" + outcomes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseMarket market = (BaseMarket) o;

        if (eventId != null ? !eventId.equals(market.eventId) : market.eventId != null)
            return false;
        return marketId != null ? marketId.equals(market.marketId) : market.marketId == null;

    }

    @Override
    public int hashCode() {
        int result = eventId != null ? eventId.hashCode() : 0;
        result = 31 * result + (marketId != null ? marketId.hashCode() : 0);
        return result;
    }

    public static final class BaseMarketBuilder {
        Header header;
        String eventId;
        String marketId;
        String name;
        boolean displayed;
        boolean suspended;
        List<Outcome> outcomes = new ArrayList<>();

        private BaseMarketBuilder() {
        }

        public static BaseMarketBuilder aBaseMarket() {
            return new BaseMarketBuilder();
        }

        public BaseMarketBuilder withHeader(Header header) {
            this.header = header;
            return this;
        }

        public BaseMarketBuilder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public BaseMarketBuilder withMarketId(String marketId) {
            this.marketId = marketId;
            return this;
        }

        public BaseMarketBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BaseMarketBuilder withDisplayed(boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public BaseMarketBuilder withSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public BaseMarketBuilder withOutcomes(List<Outcome> outcomes) {
            this.outcomes = outcomes;
            return this;
        }

        public BaseMarket build() {
            BaseMarket baseMarket = new BaseMarket();
            baseMarket.suspended = this.suspended;
            baseMarket.header = this.header;
            baseMarket.outcomes = this.outcomes;
            baseMarket.eventId = this.eventId;
            baseMarket.displayed = this.displayed;
            baseMarket.marketId = this.marketId;
            baseMarket.name = this.name;
            return baseMarket;
        }
    }
}
