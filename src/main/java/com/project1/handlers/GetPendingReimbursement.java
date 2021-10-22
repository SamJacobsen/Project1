package com.project1.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.models.ReimbursementFull;
import com.project1.services.Service;
import io.javalin.http.Context;

public final class GetPendingReimbursement extends AbstractHandler{

    public GetPendingReimbursement(Service service, ObjectMapper jsonMapper) {
        super(service, jsonMapper);
    }

    public void get(Context ctx) {
        ReimbursementFull r = service.getFirstPendingReimbursement();

        try {
            if(r != null) {
                String json = jsonMapper.writeValueAsString(r);
                logger.debug("Retrieved the first pending reimbursement, sending it to client");
                ctx.result(json);
            } else {
                logger.debug("Failed to retrieve any pending reimbursements, status code 404");
                ctx.status(404);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
        }

    }

}
