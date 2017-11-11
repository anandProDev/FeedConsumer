package com.db;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import rx.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configurable
@Repository
public class CouchbaseRepositoryServiceImpl implements RepositoryService {
    private static final Logger LOGGER = LogManager.getLogger(CouchbaseRepositoryServiceImpl.class);

    protected Bucket bucket;
    private ReplicateTo replicateTo;
    private final static int COUCHBASE_OPERATION_TIME = 2000;


    @Autowired
    public CouchbaseRepositoryServiceImpl(@Value("#{'${couchbase.seed.nodes}'.split(',')}") List<String> seedNodes,
                                          @Value("${couchbase.bucket.name}") String bucketName,
                                          @Value("${couchbase.bucket.pw}") String bucketPassword,
                                          @Value("${couchbase.replicas}") String replicas
    ) throws Exception {
        try {
            Cluster cluster = CouchbaseCluster.create(DefaultCouchbaseEnvironment.builder().connectTimeout(10000).build(), seedNodes);

            this.bucket = cluster.openBucket(bucketName, bucketPassword);
            this.replicateTo = ReplicateTo.valueOf(replicas);
        } catch (Exception e) {
            LOGGER.error("Error starting up couchbase", e);
            throw e;
        }
    }

//    private Observable<JsonDocument> retrieveCouchbaseDocument( String documentId) {
//        long startTime = System.nanoTime();
//        return bucket
//                .async()
//                .get(documentId)
//                .timeout(COUCHBASE_OPERATION_TIME, TimeUnit.MILLISECONDS)
//                .firstOrDefault(emptyDocument)
//                .flatMap(jsonDocument -> {
//
//                    if (jsonDocument.equals(emptyDocument)) {
//                        return Observable.empty();
//                    }
//                    return Observable.just(jsonDocument);
//                });
//    }

    @Override
    public void insertDocument(JsonDocument jsonDocument) {
        bucket.upsert(jsonDocument);
    }

    @Override
    public JsonDocument getDocument(String eventId) {
        return bucket.get(eventId);
    }

    @Override
    public void updateDocument(JsonDocument jsonDocument) {
        bucket.replace(jsonDocument);
    }



    public CouchbaseRepositoryServiceImpl(Bucket mockBucket,
                                          String replicas) throws Exception {
        try {
            this.bucket = mockBucket;
            this.replicateTo = ReplicateTo.valueOf(replicas);
        } catch (Exception e) {
        }
    }
}
