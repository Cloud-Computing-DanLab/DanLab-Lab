package com.example.dllab.common.exception.handler;

import com.example.dllab.common.exception.DanlabException;
import com.example.dllab.common.exception.ExceptionMessage;

public class LabException extends DanlabException {
    public LabException(ExceptionMessage message) {
        super(message.getText());
    }
}
