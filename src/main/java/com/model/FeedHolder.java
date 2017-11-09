package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "type",
        "feed"
})
public class FeedHolder{

    @JsonProperty(value = "type", required=true)
    private FeedType type;

    @JsonProperty(value = "feed", required=true)
    Feed feed;

    public FeedType getType() {
        return type;
    }

    public Feed getFeed() {
        return feed;
    }

    public FeedHolder(){

    }

    @Override
    public String toString() {
        return "FeedHolder{" +
                "type=" + type +
                ", feed=" + feed +
                '}';
    }
}
