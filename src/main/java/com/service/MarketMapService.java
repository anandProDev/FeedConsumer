package com.service;


import java.util.Map;
import java.util.Optional;

public interface MarketMapService {

    Optional<Map<String, String>> load();

    void save(Map<String, String> marketEvent);
}
