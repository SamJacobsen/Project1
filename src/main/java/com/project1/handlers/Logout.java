package com.project1.handlers;

import io.javalin.http.Context;

public final class Logout extends AbstractHandler{

    public void logout(Context ctx) {
        logger.debug("Invalidate the user in session");
        ctx.req.getSession().invalidate();

        logger.debug("Sending redirect to client");
        ctx.redirect("/index.html");
    };
}
