package org.example;

import org.example.db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainInsert {

    public static void main(String[] args) {

        Connection conn;
        PreparedStatement ps = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String insertQuery = "insert into seller " +
                "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                "values " +
                "(?, ?, ?, ?, ?)";

        try{
            conn = DB.getConnection();
            ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, "Carl Purple");
            ps.setString(2, "carl@gmail.com");
            ps.setDate(3, new Date(sdf.parse("04/08/2022").getTime()));
            ps.setDouble(4, 3000.00);
            ps.setInt(5, 4);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = ps.getGeneratedKeys();
                while(rs.next()){
                    int id = rs.getInt(1);
                    System.out.println("Id created: " + id);
                }
            } else {
                System.out.println("No rows affected!");
            }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }
}
