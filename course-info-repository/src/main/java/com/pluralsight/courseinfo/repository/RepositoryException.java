package com.pluralsight.courseinfo.repository;

import java.sql.SQLException;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String message, SQLException e) {
        super(message, e);
    }

    public RepositoryException(SQLException e) {
        super(e);
    }
}
