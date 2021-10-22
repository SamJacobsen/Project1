package com.project1.services.dao.sql;

import com.project1.models.QueryStatus;
import com.project1.models.ReimbursementFull;
import com.project1.models.ReimbursementSafe;
import com.project1.models.ReimbursementStatus;
import com.project1.services.dao.ReimbursementDao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoImpl implements ReimbursementDao {

    DBConnector dbConnector;

    public ReimbursementDaoImpl(DBConnector connector) {
        this.dbConnector = connector;
    }

    @Override
    public void insertReimbursement(ReimbursementSafe reimbursementSafe, String userName) throws SQLException {
        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "insert into reimbursement_request(rs_user, amount, request_date, transaction_date, purpose, status) " +
                    "values ((select id from rs_users where rs_users.user_name = ?), ?, ?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            statement.setString(1, userName);
            statement.setBigDecimal(2, reimbursementSafe.getAmount());
            statement.setTimestamp(3, Timestamp.valueOf(reimbursementSafe.getRequestDate()));
            statement.setDate(4, Date.valueOf(reimbursementSafe.getTransactionDate()));
            statement.setString(5, reimbursementSafe.getPurpose());
            statement.setObject(6, reimbursementSafe.getStatus(), java.sql.Types.OTHER);

            statement.execute();
        }
    }

    @Override
    public List<ReimbursementFull> getAllReimbursement() throws SQLException {
        List<ReimbursementFull> reimbursements = new ArrayList<>();

        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "select id, rs_user, amount, request_date, transaction_date, purpose, status from reimbursement_request";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("rs_user");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDateTime requestDate = rs.getTimestamp("request_date").toLocalDateTime();
                LocalDate transactionDate = rs.getDate("transaction_date").toLocalDate();
                String purpose = rs.getString("purpose");
                ReimbursementStatus status = ReimbursementStatus.valueOf(rs.getString("status"));

                ReimbursementFull reimbursement = new ReimbursementFull(id, userId, requestDate, transactionDate, amount, purpose, status);
                reimbursements.add(reimbursement);
            }
        }

        return reimbursements;
    }

    @Override
    public List<ReimbursementSafe> getAllReimbursementByUser(String userName) throws SQLException {
        List<ReimbursementSafe> reimbursementSafes = new ArrayList<>();

        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "select rs_user, amount, request_date, transaction_date, purpose, status " +
                    "from reimbursement_request " +
                    "inner join rs_users " +
                    "on rs_users.id = reimbursement_request.rs_user " +
                    "where rs_users.user_name = ?;";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            statement.setString(1, userName);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LocalDateTime requestDate = rs.getTimestamp("request_date").toLocalDateTime();
                LocalDate transactionDate = rs.getDate("transaction_date").toLocalDate();
                BigDecimal amount = rs.getBigDecimal("amount");
                String purpose = rs.getString("purpose");
                ReimbursementStatus status = ReimbursementStatus.valueOf(rs.getString("status"));

                ReimbursementSafe reimbursementSafe = new ReimbursementSafe(requestDate, transactionDate, amount, purpose, status);
                reimbursementSafes.add(reimbursementSafe);
            }
        }

        return reimbursementSafes;
    }

    @Override
    public List<ReimbursementSafe> getAllReimbursementByStatusAndUser(String userName, QueryStatus status) throws SQLException {
        List<ReimbursementSafe> reimbursementSafes = new ArrayList<>();

        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "select rs_user, amount, request_date, transaction_date, purpose, status " +
                    "from reimbursement_request " +
                    "inner join rs_users " +
                    "on rs_users.id = reimbursement_request.rs_user " +
                    "where rs_users.user_name = ? and reimbursement_request.status = ?;";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            statement.setString(1, userName);
            statement.setObject(2, status, java.sql.Types.OTHER);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LocalDateTime requestDate = rs.getTimestamp("request_date").toLocalDateTime();
                LocalDate transactionDate = rs.getDate("transaction_date").toLocalDate();
                BigDecimal amount = rs.getBigDecimal("amount");
                String purpose = rs.getString("purpose");
                ReimbursementStatus stat = ReimbursementStatus.valueOf(rs.getString("status"));

                ReimbursementSafe reimbursementSafe = new ReimbursementSafe(requestDate, transactionDate, amount, purpose, stat);
                reimbursementSafes.add(reimbursementSafe);
            }
        }

        return reimbursementSafes;
    }

    @Override
    public List<ReimbursementFull> getAllReimbursementByStatus(QueryStatus queryStatus) throws SQLException {
        List<ReimbursementFull> reimbursements = new ArrayList<>();

        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "select id, rs_user, amount, request_date, transaction_date, purpose, status " +
                    "from reimbursement_request where status = ?";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            statement.setObject(1, queryStatus, java.sql.Types.OTHER);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("rs_user");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDateTime requestDate = rs.getTimestamp("request_date").toLocalDateTime();
                LocalDate transactionDate = rs.getDate("transaction_date").toLocalDate();
                String purpose = rs.getString("purpose");
                ReimbursementStatus status = ReimbursementStatus.valueOf(rs.getString("status"));

                ReimbursementFull reimbursement = new ReimbursementFull(id, userId, requestDate, transactionDate, amount, purpose, status);
                reimbursements.add(reimbursement);
            }
        }

        return reimbursements;
    }

    @Override
    public ReimbursementFull getFirstPending() {
        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "select id, rs_user, amount, request_date, transaction_date, purpose " +
                    "from reimbursement_request " +
                    "where status = 'PENDING' limit 1;";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("rs_user");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDateTime requestDate = rs.getTimestamp("request_date").toLocalDateTime();
                LocalDate transactionDate = rs.getDate("transaction_date").toLocalDate();
                String purpose = rs.getString("purpose");

                ReimbursementFull reimbursement = new ReimbursementFull(id, userId, requestDate, transactionDate, amount, purpose, ReimbursementStatus.PENDING);
                return reimbursement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(ReimbursementFull reimbursement) {
        try(Connection connection = this.dbConnector.newConnection()) {
            String sqlStatement = "update reimbursement_request set status = ? where id = ?;";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            statement.setObject(1, reimbursement.getStatus(), java.sql.Types.OTHER);
            statement.setInt(2, reimbursement.getId());

            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
