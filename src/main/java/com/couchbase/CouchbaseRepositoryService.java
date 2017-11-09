package com.couchbase;


import com.couchbase.client.java.document.JsonDocument;

public interface CouchbaseRepositoryService {

    JsonDocument upsertDocument(JsonDocument jsonDocument);
}
