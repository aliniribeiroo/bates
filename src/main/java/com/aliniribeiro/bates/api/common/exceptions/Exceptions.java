package com.aliniribeiro.bates.api.common.exceptions;

import com.google.gson.Gson;
import org.hibernate.service.spi.ServiceException;

import java.util.UUID;

/**
 * Exceções que devem ser lançadas pela aplicação.
 */
public class Exceptions {

    public static ServiceException entityNotFound(String id) {
        Gson gson = new Gson();
        String message = new String(gson.toJson(new InternalExceptions(ExceptionType.ENTITY_NOT_FOUND)));
        throw new IllegalArgumentException(String.format(message, id));
    }

    public static ServiceException customerHasCheckin(String id) {
        Gson gson = new Gson();
        String message = new String(gson.toJson(new InternalExceptions(ExceptionType.CUSTOMER_HAVE_CHECKIN)));
        throw new IllegalArgumentException(String.format(message, id));
    }

    public static ServiceException customerNotFound(UUID id) {
        Gson gson = new Gson();
        String message = new String(gson.toJson(new InternalExceptions(ExceptionType.CUSTOMER_NOT_FOUND)));
        throw new IllegalArgumentException(String.format(message, id));
    }
}
