package org.example;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.Date;

public class MainDAO {

    public static void main(String[] args){

        SellerDao dao = DaoFactory.createSellerDao();

        Seller seller = dao.findById(3);

        System.out.println(seller);

    }
}
