package com.project1.services.dao;

import com.project1.models.LoginRequest;
import com.project1.models.User;

import java.util.List;

public interface UserDao {

    User getUserByUsername(LoginRequest request);

    String getUserNameById(int id);

    List<User> getAllEmployees();
}
