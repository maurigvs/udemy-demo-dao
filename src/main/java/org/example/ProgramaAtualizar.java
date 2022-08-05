package org.example;

import org.example.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProgramaAtualizar {

    public static void main(String[] args) {

        Connection conn;
        PreparedStatement ps = null;

        String updateQuery = "update seller " +
                "set BaseSalary = BaseSalary+? " +
                "where DepartmentId = ?";

        try{
            conn = DB.getConnection();
            ps = conn.prepareStatement(updateQuery);

            ps.setDouble(1, 200.00);
            ps.setInt(2, 2);

            int rowsAffected = ps.executeUpdate();

            System.out.println("Done! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }
}
