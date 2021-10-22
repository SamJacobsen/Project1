package com.project1.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.models.ReimbursementSafe;
import com.project1.models.User;
import com.project1.services.Service;
import io.javalin.http.Context;

import java.util.List;

public final class GetReimbursementByUser extends AbstractHandler{

    public GetReimbursementByUser(Service service, ObjectMapper jsonMapper) {
        super(service, jsonMapper);
    }

    public void get(Context ctx) {
        List<ReimbursementSafe> reimbursementSafes;

        try {
            String status = jsonMapper.readValue(ctx.body(), String.class);
            User currentUser = ctx.sessionAttribute("logged-in-user");

            reimbursementSafes = service.getReimbursements(status, currentUser);

            if(reimbursementSafes != null && !reimbursementSafes.isEmpty()) {
                String json = jsonMapper.writeValueAsString(reimbursementSafes);
                logger.debug("Retrieved all reimbursements by session user, sending to client");
                ctx.result(json);
            } else {
                logger.debug("Failed to retrieve any reimbursements, sending empty list");
                ctx.json(new String[]{});
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
        }

    }

}
