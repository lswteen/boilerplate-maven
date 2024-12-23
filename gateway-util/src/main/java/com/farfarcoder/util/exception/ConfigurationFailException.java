package com.farfarcoder.util.exception;


import com.farfarcoder.domain.exception.BusinessException;

public class ConfigurationFailException extends BusinessException {
    public ConfigurationFailException(String message) {
        super(message);
    }
}
