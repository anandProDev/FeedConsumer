package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "header",
        "eventId",
        "marketId",
        "name",
        "displayed",
        "suspended"
})
public class Market implements Feed{

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

    @Override
    public String toString() {
        return "Market{" +
                "header=" + header +
                ", eventId='" + eventId + '\'' +
                ", marketId='" + marketId + '\'' +
                ", name='" + name + '\'' +
                ", displayed=" + displayed +
                ", suspended=" + suspended +
                '}';
    }

    public static final class MarketBuilder {
        Header header;
        String eventId;
        String marketId;
        String name;
        boolean displayed;
        boolean suspended;

        private MarketBuilder() {
        }

        public static MarketBuilder aMarket() {
            return new MarketBuilder();
        }

        public MarketBuilder withHeader(Header header) {
            this.header = header;
            return this;
        }

        public MarketBuilder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public MarketBuilder withMarketId(String marketId) {
            this.marketId = marketId;
            return this;
        }

        public MarketBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MarketBuilder withDisplayed(boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public MarketBuilder withSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public Market build() {
            Market market = new Market();
            market.displayed = this.displayed;
            market.header = this.header;
            market.suspended = this.suspended;
            market.marketId = this.marketId;
            market.name = this.name;
            market.eventId = this.eventId;
            return market;
        }
    }
}


