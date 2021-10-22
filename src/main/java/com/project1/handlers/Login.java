package com.project1.handlers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.models.*;
import com.project1.services.Service;
import io.javalin.http.Context;
import org.apache.log4j.Logger;

import java.io.IOException;

public final class Login extends AbstractHandler{

    public Login(Service service, ObjectMapper jsonMapper) {
        super(service, jsonMapper);
    }

    public void login(Context ctx) {
        try {
            LoginRequest request = super.jsonMapper.readValue(ctx.body(), LoginRequest.class);
            request.setuName(request.getuName().trim());

            User user = service.validateCredentials(request);

            if(user != null) {

                ctx.sessionAttribute("logged-in-user", user);
                if(user.getType().equals(UserType.EMPLOYEE)) {
                    logger.debug("Valid employee user logged in");
                    ctx.redirect("/employee/homePage.html");
                } else if (user.getType().equals(UserType.MANAGER)) {
                    logger.debug("Valid manager user logged in");
                    ctx.redirect("/manager/homePage.html");
                }

            } else {
                logger.debug("Invalid login attempt, credentials incorrect");
                ctx.status(401);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
