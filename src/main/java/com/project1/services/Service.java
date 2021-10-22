package com.project1.services;

import com.project1.models.LoginRequest;
import com.project1.models.ReimbursementFull;
import com.project1.models.ReimbursementSafe;
import com.project1.models.User;

import java.util.List;

public interface Service {

    User validateCredentials(LoginRequest request);

    boolean persistReimbursement(ReimbursementSafe reimbursementSafe, User user);

    List<ReimbursementSafe> getReimbursements(String status, User user);

    List<ReimbursementFull> getReimbursements(String status);

    ReimbursementFull getFirstPendingReimbursement();

    boolean updateReimbursement(ReimbursementFull reimbursement);

    List<User> getEmployees();
}
