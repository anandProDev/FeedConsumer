package com.db;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.factory.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class CouchbaseRepositoryServiceImplTest {

    RepositoryService couchbaseRepositoryServiceImpl;

    @Mock
    Bucket bucket;

    String replicas = "NONE";

    String eventId = "eventId";


    private final JsonDocument emptyDocument = JsonDocument.create("default");


    @Before
    public void setUp() throws Exception {
        couchbaseRepositoryServiceImpl = new CouchbaseRepositoryServiceImpl(bucket, replicas);
    }

    @Test
    public void insertDocument() throws Exception {

        couchbaseRepositoryServiceImpl.insertDocument(emptyDocument);
        verify(bucket, times(1)).upsert(emptyDocument);
    }

    @Test
    public void getDocument() throws Exception {

        couchbaseRepositoryServiceImpl.getDocument(eventId);
        verify(bucket, times(1)).get(eventId);
    }

    @Test
    public void updateDocument() throws Exception {
        couchbaseRepositoryServiceImpl.updateDocument(emptyDocument);
        verify(bucket, times(1)).replace(emptyDocument);
    }
}