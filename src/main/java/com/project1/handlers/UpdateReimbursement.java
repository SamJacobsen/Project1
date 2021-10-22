package com.project1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.models.ReimbursementFull;
import com.project1.services.Service;
import io.javalin.http.Context;

public final class UpdateReimbursement extends AbstractHandler{

    public UpdateReimbursement(Service service, ObjectMapper jsonMapper) {
        super(service, jsonMapper);
    }

    public void update(Context ctx) {
        try {
            ReimbursementFull r = this.jsonMapper.readValue(ctx.body(), ReimbursementFull.class);

            boolean result = this.service.updateReimbursement(r);
            if(result) {
                logger.debug("Successfully updated reimbursement, status code 200");
                ctx.status(200);
            } else {
                logger.debug("Failed to update reimbursement, status code 400");
                ctx.status(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
