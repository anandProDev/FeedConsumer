package com.model;

public enum FeedType {

    EVENT("event"), MARKET("market"), OUTCOME("outcome");

    private final String name;

    public String getName() {
        return name;
    }

    FeedType(String name){
        this.name = name;
    }
}
