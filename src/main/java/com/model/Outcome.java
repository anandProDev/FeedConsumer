package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "header",
        "marketId",
        "outcomeId",
        "name",
        "price",
        "displayed",
        "suspended"
})
public class Outcome implements Feed{

    @JsonProperty(value = "header", required = true)
    Header header;
    @JsonProperty(value = "marketId", required = true)
    String marketId;
    @JsonProperty(value = "outcomeId", required = true)
    String outcomeId;
    @JsonProperty(value = "name", required = true)
    String name;
    @JsonProperty(value = "price", required = true)
    String price;
    @JsonProperty(value = "displayed", required = true)
    boolean displayed;
    @JsonProperty(value = "suspended", required = true)
    boolean suspended;

    public Header getHeader() {
        return header;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "header=" + header +
                ", marketId='" + marketId + '\'' +
                ", outcomeId='" + outcomeId + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", displayed=" + displayed +
                ", suspended=" + suspended +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Outcome outcome = (Outcome) o;

        return outcomeId != null ? outcomeId.equals(outcome.outcomeId) : outcome.outcomeId == null;
    }

    @Override
    public int hashCode() {
        return outcomeId != null ? outcomeId.hashCode() : 0;
    }

    public static final class OutcomeBuilder {
        Header header;
        String marketId;
        String outcomeId;
        String name;
        String price;
        boolean displayed;
        boolean suspended;

        private OutcomeBuilder() {
        }

        public static OutcomeBuilder anOutcome() {
            return new OutcomeBuilder();
        }

        public OutcomeBuilder withHeader(Header header) {
            this.header = header;
            return this;
        }

        public OutcomeBuilder withMarketId(String marketId) {
            this.marketId = marketId;
            return this;
        }

        public OutcomeBuilder withOutcomeId(String outcomeId) {
            this.outcomeId = outcomeId;
            return this;
        }

        public OutcomeBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public OutcomeBuilder withPrice(String price) {
            this.price = price;
            return this;
        }

        public OutcomeBuilder withDisplayed(boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public OutcomeBuilder withSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public Outcome build() {
            Outcome outcome = new Outcome();
            outcome.outcomeId = this.outcomeId;
            outcome.header = this.header;
            outcome.displayed = this.displayed;
            outcome.marketId = this.marketId;
            outcome.suspended = this.suspended;
            outcome.price = this.price;
            outcome.name = this.name;
            return outcome;
        }
    }
}
