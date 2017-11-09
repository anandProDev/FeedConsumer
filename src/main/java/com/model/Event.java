package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "header",
        "eventId",
        "category",
        "subCategory",
        "name",
        "startTime",
        "displayed",
        "suspended"
})
public class Event implements Feed {

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

    @Override
    public String toString() {
        return "Event{" +
                "header=" + header +
                ", eventId='" + eventId + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", displayed=" + displayed +
                ", suspended=" + suspended +
                '}';
    }

    public static final class EventBuilder {
        Header header;
        String eventId;
        String category;
        String subCategory;
        String name;
        String startTime;
        boolean displayed;
        boolean suspended;

        private EventBuilder() {
        }

        public static EventBuilder anEvent() {
            return new EventBuilder();
        }

        public EventBuilder withHeader(Header header) {
            this.header = header;
            return this;
        }

        public EventBuilder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public EventBuilder withCategory(String category) {
            this.category = category;
            return this;
        }

        public EventBuilder withSubCategory(String subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public EventBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public EventBuilder withStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public EventBuilder withDisplayed(boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public EventBuilder withSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public Event build() {
            Event event = new Event();
            event.startTime = this.startTime;
            event.eventId = this.eventId;
            event.category = this.category;
            event.name = this.name;
            event.header = this.header;
            event.displayed = this.displayed;
            event.subCategory = this.subCategory;
            event.suspended = this.suspended;
            return event;
        }
    }
}
