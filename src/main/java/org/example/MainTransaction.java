package org.example;

import org.example.db.DB;
import org.example.db.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainTransaction {

    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;

        try{
            conn = DB.getConnection();
            st = conn.createStatement();
            conn.setAutoCommit(false);

            int rows1 = st.executeUpdate("update seller set BaseSalary = 2090 where DepartmentId = 1");

            /* Force Exception
            int x = 1;
            if(x < 2){
                throw new SQLException("Fake error");
            }*/

            int rows2 = st.executeUpdate("update seller set BaseSalary = 3090 where DepartmentId = 2");

            conn.commit();

            System.out.println("Rows 1: " + rows1);
            System.out.println("Rows 2: " + rows2);

        } catch (SQLException e){
            try {
                conn.rollback();
                throw new DbException("Transaction rolled back: " + e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Error trying to execute Rollback caused by: " + ex.getMessage());
            }
        } finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }

    }
}