package com.project1.test.mocks;

import com.project1.models.QueryStatus;
import com.project1.models.ReimbursementFull;
import com.project1.models.ReimbursementSafe;
import com.project1.services.dao.ReimbursementDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoMock implements ReimbursementDao {
    @Override
    public void insertReimbursement(ReimbursementSafe reimbursementSafe, String userName) throws SQLException {
        if(reimbursementSafe == null || userName == null) {
            throw new SQLException();
        }
    }

    @Override
    public List<ReimbursementFull> getAllReimbursement() throws SQLException {
        List<ReimbursementFull> r = new ArrayList<>();
        r.add(new ReimbursementFull());
        return r;
    }

    @Override
    public List<ReimbursementSafe> getAllReimbursementByUser(String userName) throws SQLException {
        return null;
    }

    @Override
    public List<ReimbursementSafe> getAllReimbursementByStatusAndUser(String userName, QueryStatus status) throws SQLException {
        return null;
    }

    @Override
    public List<ReimbursementFull> getAllReimbursementByStatus(QueryStatus status) throws SQLException {
        List<ReimbursementFull> r = new ArrayList<>();
        r.add(new ReimbursementFull());
        return r;
    }

    @Override
    public ReimbursementFull getFirstPending() {
        return new ReimbursementFull();
    }

    @Override
    public boolean update(ReimbursementFull reimbursement) {
        return false;
    }
}
