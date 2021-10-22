package com.project1.services.dao.sql;

import com.project1.models.LoginRequest;
import com.project1.models.User;
import com.project1.models.UserType;
import com.project1.services.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    DBConnector dbConnector;

    public UserDaoImpl(DBConnector connector) {
        this.dbConnector = connector;
    }

    @Override
    public User getUserByUsername(LoginRequest request) {
        try(Connection connection = this.dbConnector.newConnection()) {

            String sqlStatement = "select first_name, last_name, user_name, user_password, rs_type " +
                    "from rs_users " +
                    "where user_name = ?";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, request.getuName());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String newUsername = rs.getString("user_name");
                String password = rs.getString("user_password");
                UserType type = UserType.valueOf(rs.getString("rs_type"));

                if(request.getPsw().equals(password)) {
                    return new User(firstName, lastName, newUsername, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserNameById(int id) {
        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "select user_name from rs_users where id = ?;";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                return rs.getString("user_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllEmployees() {
        List<User> users = new ArrayList<>();
        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "select first_name, last_name, user_name from rs_users where rs_type = 'EMPLOYEE'";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String userName = rs.getString("user_name");

                User u = new User(firstName, lastName, userName, UserType.EMPLOYEE);
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

}
