/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.infrastructure.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 *
 * @author TR_NGHIA
 */

public abstract class BaseDAO<T> {
    private final DataSource dataSource;

    // ========= CONSTRUCTOR ĐỂ INJECT DATASOURCE =========
    protected BaseDAO(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "DataSource must not be null");
    }

    // ========= LẤY CONNECTION TỪ CONNECTION POOL =========
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected void close(AutoCloseable c) {
        if (c != null) try { c.close(); } catch (Exception ignored) {}//just ignore
    }

    // ========= PHƯƠNG THỨC TRỪU TƯỢNG: ÁNH XẠ ROW SANG OBJECT =========
    public abstract T mapRow(ResultSet resultSet) throws SQLException;

    // ========= THỰC THI CÂU LỆNH QUERY (SELECT) =========
    public List<T> query(String sql, Object... params) {
        List<T> results = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return results;
    }


    // ========= THỰC THI CÂU LỆNH UPDATE (INSERT, UPDATE, DELETE) =========
    public int update(String sql, Object... params) {
        try ( Connection connection = getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error executing update", e);
        }
    }

    // ---------------------------
    // INSERT (return id)
    // ---------------------------
    public int insertAndReturnId(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // return id of row inserted
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ---------------------------
    // INSERT BATCH (return list of ids)
    // ---------------------------
    public List<Integer> insertBatchAndReturnIds(String sql, List<Object[]> batchParams) {
        List<Integer> generatedIds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // start transaction

            ps = conn.prepareStatement(sql, RETURN_GENERATED_KEYS);

            for (Object[] params : batchParams) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
                // execute each insert
                ps.executeUpdate();
                // get id of row inserted
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedIds.add(rs.getInt(1));
                    }
                }
                ps.clearParameters(); // clear parameters for next insert
            }
            conn.commit(); // commit transaction
            return generatedIds;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // rollback all changes/transactions
                    System.err.println("Batch insert rolled back due to error.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return Collections.emptyList();// return an empty list if failed
        } finally {
            if (ps != null) close(ps);
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }


}
