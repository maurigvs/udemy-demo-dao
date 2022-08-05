package org.example.model.dao;

import org.example.model.entities.Seller;

import java.util.List;

public interface SellerDAO {

    void insert(Seller obj);

    void update(Seller obj);

    void deletebyId(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();
}
