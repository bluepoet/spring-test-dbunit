package com.github.springtestdbunit.sample.service;

import com.github.springtestdbunit.sample.entity.Product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by daumkakao on 2017. 1. 16..
 */
@Service
@Transactional
public class ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    public void remove(int productId) {
        Product product = this.entityManager.find(Product.class, productId);
        this.entityManager.remove(product);
    }
}
