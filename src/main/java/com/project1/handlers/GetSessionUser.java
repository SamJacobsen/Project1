package com.project1.handlers;

import com.project1.models.User;
import io.javalin.http.Context;

public final class GetSessionUser extends AbstractHandler{

    public void get(Context ctx) {

        User u = ctx.sessionAttribute("logged-in-user");

        if(u != null) {
            ctx.status(200);
            logger.debug("Successfully retrieved user from session, sending to client");
            ctx.json(u);
        } else {
            logger.debug("No user in session, sending 401");
            ctx.status(401);
        }
    }

}
