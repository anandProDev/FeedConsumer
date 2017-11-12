package com.db;


import com.couchbase.client.java.document.JsonDocument;

import java.util.Optional;

public interface RepositoryService {

    void insertDocument(JsonDocument jsonDocument);
    JsonDocument getDocument(String eventId);
    void updateDocument(JsonDocument jsonDocument);
}
