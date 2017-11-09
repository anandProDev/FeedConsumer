package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.Header;
import com.model.Market;

import java.util.ArrayList;
import java.util.List;


public class BaseEvent {

    @JsonProperty(value = "header", required=true)
    Header header;
    @JsonProperty(value = "eventId", required=true)
    String eventId;
    @JsonProperty(value = "category", required=true)
    String category;
    @JsonProperty(value = "subCategory", required=true)
    String subCategory;
    @JsonProperty(value = "name", required=true)
    String name;
    @JsonProperty(value = "startTime", required=true)
    String startTime;
    @JsonProperty(value = "displayed", required=true)
    boolean displayed;
    @JsonProperty(value = "suspended", required=true)
    boolean suspended;

    @JsonProperty(value="markets")
    List<BaseMarket> markets = new ArrayList<>();

    public Header getHeader() {
        return header;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public List<BaseMarket> getMarkets() {
        return markets;
    }


    public static final class BaseEventBuilder {
        Header header;
        String eventId;
        String category;
        String subCategory;
        String name;
        String startTime;
        boolean displayed;
        boolean suspended;
        List<BaseMarket> markets = new ArrayList<>();

        private BaseEventBuilder() {
        }

        public static BaseEventBuilder aBaseEvent() {
            return new BaseEventBuilder();
        }

        public BaseEventBuilder withHeader(Header header) {
            this.header = header;
            return this;
        }

        public BaseEventBuilder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public BaseEventBuilder withCategory(String category) {
            this.category = category;
            return this;
        }

        public BaseEventBuilder withSubCategory(String subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public BaseEventBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BaseEventBuilder withStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public BaseEventBuilder withDisplayed(boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public BaseEventBuilder withSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public BaseEventBuilder withMarkets(List<BaseMarket> markets) {
            this.markets = markets;
            return this;
        }

        public BaseEvent build() {
            BaseEvent baseEvent = new BaseEvent();
            baseEvent.startTime = this.startTime;
            baseEvent.markets = this.markets;
            baseEvent.name = this.name;
            baseEvent.suspended = this.suspended;
            baseEvent.eventId = this.eventId;
            baseEvent.subCategory = this.subCategory;
            baseEvent.header = this.header;
            baseEvent.category = this.category;
            baseEvent.displayed = this.displayed;
            return baseEvent;
        }
    }
}
