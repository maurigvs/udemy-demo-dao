package org.example.model.dao.impl;

import org.example.db.DB;
import org.example.db.DbException;
import org.example.db.DbIntegrityException;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

        PreparedStatement ps = null;

        String insertQuery = "insert into seller " +
                "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                "values " +
                "(?, ?, ?, ?, ?)";

        try{
            ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                    System.out.println("Id created: " + id);
                }
                rs.close();
            } else {
                throw new DbException("No rows affected!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Seller obj) {

        PreparedStatement ps = null;

        String updateQuery = "UPDATE seller " +
                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                "WHERE Id = ?";

        try{
            ps = conn.prepareStatement(updateQuery);

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getId());
            ps.setInt(6, obj.getId());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Done! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement ps = null;
        String deleteQuery = "delete from seller where id = ?";

        try{
            ps = conn.prepareStatement(deleteQuery);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            System.out.println("Done! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Seller findById(Integer id) {

        Seller result = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT seller.*, department.Name AS DepName " +
                "FROM seller " +
                "INNER JOIN department ON seller.DepartmentId = department.Id " +
                "WHERE seller.Id = ?";

        try{
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                result = instantiateSeller(rs, dep);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return result;
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {

        return new Seller(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getDate("BirthDate"),
                rs.getDouble("BaseSalary"),
                department);
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {

        return new Department(
                rs.getInt("DepartmentId"),
                rs.getString("DepName"));
    }

    @Override
    public List<Seller> findAll() {

        List<Seller> list;
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT seller.*, department.Name AS DepName " +
                "FROM seller " +
                "INNER JOIN department ON seller.DepartmentId = department.Id " +
                "ORDER BY Name";

        try{
            st = conn.prepareStatement(query);
            rs = st.executeQuery();

            list = new ArrayList<>();
            Map<Integer, Department> deps = new HashMap<>();

            while (rs.next()){
                Department dep = deps.get(rs.getInt("DepartmentId"));
                if(dep == null){
                    dep = instantiateDepartment(rs);
                    deps.put(rs.getInt("DepartmentId"), dep);
                }
                list.add(instantiateSeller(rs, dep));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return list;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        List<Seller> list;
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT seller.*, department.Name AS DepName " +
                "FROM seller " +
                "INNER JOIN department ON seller.DepartmentId = department.Id " +
                "WHERE department.Id = ? ORDER BY Name";

        try{
            st = conn.prepareStatement(query);
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            list = new ArrayList<>();
            Map<Integer, Department> deps = new HashMap<>();

            while (rs.next()){
                Department dep = deps.get(rs.getInt("DepartmentId"));
                if(dep == null){
                    dep = instantiateDepartment(rs);
                    deps.put(rs.getInt("DepartmentId"), dep);
                }
                list.add(instantiateSeller(rs, dep));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return list;
    }
}
