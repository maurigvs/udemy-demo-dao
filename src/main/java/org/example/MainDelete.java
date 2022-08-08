package org.example;

import org.example.db.DB;
import org.example.db.DbIntegrityException;

import java.sql.*;

public class MainDelete {

    public static void main(String[] args) {

        Connection conn;
        PreparedStatement ps = null;

        String deleteQuery = "delete from department where id = ?";

        try{
            conn = DB.getConnection();
            ps = conn.prepareStatement(deleteQuery);

            ps.setInt(1, 4);

            int rowsAffected = ps.executeUpdate();

            System.out.println("Done! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }
}
