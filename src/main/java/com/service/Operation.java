package com.service;


public enum Operation {

    CREATE("create"), UPDATE("update");

    private final String name;

    public String getName() {
        return name;
    }

    Operation(String name){
        this.name = name;
    }
}
