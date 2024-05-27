package com.example.dllab.common.exception.handler;

import com.example.dllab.common.exception.DanlabException;
import com.example.dllab.common.exception.ExceptionMessage;

public class LabEventException extends DanlabException {
    public LabEventException(ExceptionMessage message) {
        super(message.getText());
    }
}
