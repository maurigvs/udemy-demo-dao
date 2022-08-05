package org.example;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.List;

public class MainDao {

    public static void main(String[] args){

        SellerDao dao = DaoFactory.createSellerDao();

        System.out.println("Testing Seller findbyId: ");
        Seller s1 = dao.findById(3);
        System.out.println(s1);

        System.out.println("Testing Seller findByDepartment: ");
        Department dep = new Department(2, null);
        List<Seller> s2 = dao.findbyDepartment(dep);
        s2.forEach(System.out::println);

    }
}
