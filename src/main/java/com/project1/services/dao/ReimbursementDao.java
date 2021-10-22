package com.project1.services.dao;

import com.project1.models.QueryStatus;
import com.project1.models.ReimbursementFull;
import com.project1.models.ReimbursementSafe;
import com.project1.models.ReimbursementStatus;

import java.sql.SQLException;
import java.util.List;

public interface ReimbursementDao {

    void insertReimbursement(ReimbursementSafe reimbursementSafe, String userName) throws SQLException;

    List<ReimbursementFull> getAllReimbursement() throws SQLException;

    List<ReimbursementSafe> getAllReimbursementByUser(String userName) throws SQLException;

    List<ReimbursementSafe> getAllReimbursementByStatusAndUser(String userName, QueryStatus status) throws SQLException;

    List<ReimbursementFull> getAllReimbursementByStatus(QueryStatus status) throws SQLException;

    ReimbursementFull getFirstPending();

    boolean update(ReimbursementFull reimbursement);
}
