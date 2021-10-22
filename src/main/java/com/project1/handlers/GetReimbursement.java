package com.project1.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.models.ReimbursementFull;
import com.project1.services.Service;
import io.javalin.http.Context;

import java.util.List;

public final class GetReimbursement extends AbstractHandler{

    public GetReimbursement(Service service, ObjectMapper jsonMapper) {
        super(service, jsonMapper);
    }

    public void get(Context ctx) {
        List<ReimbursementFull> reimbursements;
        try {
            String queryStatus = jsonMapper.readValue(ctx.body(), String.class);
            reimbursements = this.service.getReimbursements(queryStatus);

            if(reimbursements != null && !reimbursements.isEmpty()) {
                String json = jsonMapper.writeValueAsString(reimbursements);
                logger.debug("Retrieved all reimbursements, and sending to client");
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
