package com.gmail.lairmartes.shyftlab.common.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(final String entityNane, final long id) {
        super(String.format("%s with id %d not found.", entityNane, id));
    }
}
