package org.example;

import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.Date;

public class MainDAO {

    public static void main(String[] args){

        Department dep = new Department(1, "Departamento");
        System.out.println(dep);

        Seller sel = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.00, dep);
        System.out.println(sel);
    }
}
