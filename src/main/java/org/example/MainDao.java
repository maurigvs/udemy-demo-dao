package org.example;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainDao {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        SellerDao dao = DaoFactory.createSellerDao();

        System.out.println("Testing Seller findById: ");
        Seller s1 = dao.findById(3);
        System.out.println(s1);

        System.out.println("Testing Seller findByDepartment: ");
        Department dep = new Department(2, null);
        List<Seller> s2 = dao.findByDepartment(dep);
        s2.forEach(System.out::println);

        System.out.println("Testing Seller findAll: ");
        s2 = dao.findAll();
        s2.forEach(System.out::println);

        System.out.println("Testing Seller insert()");
        Seller seller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, dep);
        dao.insert(seller);
        System.out.println("Inserted: new Id = " + seller.getId());

        System.out.println("Testing Seller update()");
        seller = dao.findById(1);
        seller.setName("Martha Waine");
        dao.update(seller);
        System.out.println("Update completed!");

        System.out.println("Testing Seller delete()");
        System.out.println("Enter Id for deletion:");
        int id = sc.nextInt();
        dao.deleteById(id);
        System.out.println("Delete completed!");
    }
}
