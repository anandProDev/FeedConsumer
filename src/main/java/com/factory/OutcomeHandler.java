package com.factory;

import com.model.Outcome;

public interface OutcomeHandler {

    void handle(Outcome outcome, String eventId);
}
