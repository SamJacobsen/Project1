package com.project1.services;

import com.project1.models.*;
import com.project1.services.dao.ReimbursementDao;
import com.project1.services.dao.UserDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class ServiceImpl implements Service {

    UserDao userDao;
    ReimbursementDao reimbursementDao;

    public ServiceImpl(UserDao userDao, ReimbursementDao reimbursementDao) {
        this.userDao = userDao;
        this.reimbursementDao = reimbursementDao;
    }

    /**
     * Validate if loginRequest has valid credentials
     *
     * @param request
     * @return The valid user
     */
    @Override
    public User validateCredentials(LoginRequest request) {
        User user = userDao.getUserByUsername(request);
        return user;
    }

    /**
     * Persist a reimbursement
     *
     * @param reimbursementSafe
     * @param user
     * @return if successfully persisted
     */
    @Override
    public boolean persistReimbursement(ReimbursementSafe reimbursementSafe, User user) {
        try {
            reimbursementDao.insertReimbursement(reimbursementSafe, user.getUserName());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Gets all reimbursements made by a user
     *
     * @param queryStatus
     * @param user
     * @return List of reimbursements
     */
    @Override
    public List<ReimbursementSafe> getReimbursements(String queryStatus, User user) {
        try {
            String username = user.getUserName();
            QueryStatus status = QueryStatus.valueOf(queryStatus.toUpperCase(Locale.ROOT));
            if(status.equals(QueryStatus.ALL)) {
                return this.reimbursementDao.getAllReimbursementByUser(username);
            } else {
                return this.reimbursementDao.getAllReimbursementByStatusAndUser(username, status);
            }
        } catch (NullPointerException | IllegalArgumentException | SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all reimbursements in dao
     *
     * @param queryStatus
     * @return List of reimbursements
     */
    @Override
    public List<ReimbursementFull> getReimbursements(String queryStatus) {
        try {
            List<ReimbursementFull> reimbursements;
            QueryStatus status = QueryStatus.valueOf(queryStatus.toUpperCase(Locale.ROOT));
            if(status.equals(QueryStatus.ALL)) {
                reimbursements = this.reimbursementDao.getAllReimbursement();
            } else {
                reimbursements = this.reimbursementDao.getAllReimbursementByStatus(status);
            }

            reimbursements.forEach(r -> {
                String userName = this.userDao.getUserNameById(r.getUserId());
                r.setUserName(userName);
            });

            return reimbursements;
        } catch (IllegalArgumentException | SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Get the first pending reimbursement
     *
     * @return pending reimbursement
     */
    @Override
    public ReimbursementFull getFirstPendingReimbursement() {
        ReimbursementFull reimbursement = this.reimbursementDao.getFirstPending();
        if(reimbursement != null) {
            String userName = this.userDao.getUserNameById(reimbursement.getUserId());
            reimbursement.setUserName(userName);
        }
        return reimbursement;
    }

    /**
     * Update a reimbursement in dao
     *
     * @param reimbursement
     * @return if successfully updated
     */
    @Override
    public boolean updateReimbursement(ReimbursementFull reimbursement) {
        return this.reimbursementDao.update(reimbursement);
    }

    /**
     * Gets all the employees
     *
     * @return List of all the employees, returns empty list if none
     */
    @Override
    public List<User> getEmployees() {
        return this.userDao.getAllEmployees();
    }

}