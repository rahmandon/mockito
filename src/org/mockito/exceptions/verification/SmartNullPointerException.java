package org.mockito.exceptions.verification;

import org.mockito.exceptions.base.MockitoException;

public class SmartNullPointerException extends MockitoException {

    public SmartNullPointerException(String message, Throwable t) {
        super(message, t);
    }

    public SmartNullPointerException(String message) {
        super(message);
    }
}
