package com.db;


import com.domain.BaseEvent;

import java.util.Optional;

public interface EventRetrieverService {

    Optional<BaseEvent> getBaseEvent(String eventId);
}
