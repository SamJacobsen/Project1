package com.project1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.services.Service;
import org.apache.log4j.Logger;

public abstract class AbstractHandler {

    protected static Logger logger = Logger.getLogger(AbstractHandler.class.getName());

    protected Service service;
    protected ObjectMapper jsonMapper;

    public AbstractHandler() {
    }

    public AbstractHandler(Service service, ObjectMapper jsonMapper) {
        this.service = service;
        this.jsonMapper = jsonMapper;
    }
}
