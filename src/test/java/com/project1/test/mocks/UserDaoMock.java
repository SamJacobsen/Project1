package com.project1.test.mocks;

import com.project1.models.LoginRequest;
import com.project1.models.User;
import com.project1.models.UserType;
import com.project1.services.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMock implements UserDao {
    @Override
    public User getUserByUsername(LoginRequest request) {
        return new User("test", "test", "test", UserType.EMPLOYEE);
    }

    @Override
    public String getUserNameById(int id) {
        return null;
    }

    @Override
    public List<User> getAllEmployees() {
        List<User> u = new ArrayList<>();
        return u;
    }
}
