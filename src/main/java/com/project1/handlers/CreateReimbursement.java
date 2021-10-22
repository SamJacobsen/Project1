package com.project1.handlers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.models.ReimbursementSafe;
import com.project1.models.ReimbursementStatus;
import com.project1.services.Service;
import io.javalin.http.Context;

import java.io.IOException;

public final class CreateReimbursement extends AbstractHandler{

    public CreateReimbursement(Service service, ObjectMapper jsonMapper) {
        super(service, jsonMapper);
    }

    public void create(Context ctx) {
        try {
            ReimbursementSafe reimbursementSafe = jsonMapper.readValue(ctx.body(), ReimbursementSafe.class);
            reimbursementSafe.setStatus(ReimbursementStatus.PENDING);

            boolean result = service.persistReimbursement(reimbursementSafe, ctx.sessionAttribute("logged-in-user"));

            if(result == true) {
                logger.debug("Successfully created new reimbursement, sending status code 200");
                ctx.status(200);
            } else {
                logger.debug("Service failed to create new reimbursement, sending status code 500");
                ctx.status(500);
            }

        } catch (JsonMappingException e) {
            ctx.status(400);
            e.printStackTrace();
        } catch (IOException e) {
            ctx.status(400);
            e.printStackTrace();
        } catch (Exception e) {
            ctx.status(400);
            e.printStackTrace();
        }
    }
}
