package com.project1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project1.handlers.*;
import com.project1.services.Service;
import com.project1.services.ServiceImpl;
import com.project1.services.dao.ReimbursementDao;
import com.project1.services.dao.UserDao;
import com.project1.services.dao.sql.DBConnector;
import com.project1.services.dao.sql.PostgreConnectorImpl;
import com.project1.services.dao.sql.ReimbursementDaoImpl;
import com.project1.services.dao.sql.UserDaoImpl;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

    private Javalin app;
    private Service service;
    private ObjectMapper jsonMapper;

    public App() {
        this.app = Javalin.create(config ->
        config.addStaticFiles("/client", Location.CLASSPATH))
        .start(7000);

        DBConnector sqlConnector = new PostgreConnectorImpl();
        UserDao userDao = new UserDaoImpl(sqlConnector);
        ReimbursementDao reimbursementDao = new ReimbursementDaoImpl(sqlConnector);
        this.service = new ServiceImpl(userDao, reimbursementDao);

        this.jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
    }

    public void run() {
        //Build Handlers
        app.post("/api/login", new Login(service, jsonMapper)::login);
        app.post("/api/logout", new Logout()::logout);
        app.get("/api/SessionUser", new GetSessionUser()::get);
        app.post("/api/employee/submitReimbursement", new CreateReimbursement(service, jsonMapper)::create);
        app.post("/api/employee/queryReimbursement", new GetReimbursementByUser(service, jsonMapper)::get);

        app.get("/api/manager/pending", new GetPendingReimbursement(service, jsonMapper)::get);
        app.put("/api/manager/updateReimbursement", new UpdateReimbursement(service, jsonMapper)::update);
        app.post("/api/manager/queryReimbursement", new GetReimbursement(service, jsonMapper)::get);
        app.get("/api/manager/viewEmployees", new GetAllEmployees(service, jsonMapper)::get);
    }
}
