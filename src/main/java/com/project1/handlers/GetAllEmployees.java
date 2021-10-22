package com.project1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.models.User;
import com.project1.services.Service;
import io.javalin.http.Context;

import java.util.List;

public final class GetAllEmployees extends AbstractHandler{

    public GetAllEmployees(Service service, ObjectMapper jsonMapper) {
        super(service, jsonMapper);
    }

    public void get(Context ctx) {
        List<User> users = this.service.getEmployees();

        if(users != null && !users.isEmpty()) {
            logger.debug("Retrieved all employees, sending to client");
            ctx.json(users);
        } else {
            logger.debug("No employees found, sending empty list");
            ctx.json(new String[]{});
        }
    }
}
