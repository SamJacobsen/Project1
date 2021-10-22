package com.project1.services.dao.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnector {

    Connection newConnection() throws SQLException;

}
