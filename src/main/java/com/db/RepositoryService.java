package com.db;


import com.couchbase.client.java.document.JsonDocument;

public interface RepositoryService {

    void insertDocument(JsonDocument jsonDocument);
    JsonDocument getDocument(String eventId);
    void updateDocument(JsonDocument jsonDocument);
}
