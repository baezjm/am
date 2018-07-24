package com.appointment.manager.api.core;

/**
 * Custom exception to any fail of injection instance
 *
 * @see RuntimeException
 */
public class InstanceNotFoundException extends RuntimeException {

    public InstanceNotFoundException(Class clazz) {
        super(clazz.getName() + " was not injected.");
    }
}
