package com.xq.xtool.service;

import com.xq.xtool.entity.ProductEntity;
import com.xq.xtool.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class ProductService implements Supplier<ProductEntity> {
    private static int index = 0;
    @Autowired
    private ProductMapper productMapper;

    public ProductEntity save(ProductEntity entity) {
        ProductEntity save = productMapper.save(entity);
        return save;
    }

    @Override
    public ProductEntity get(){
        index++;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("name" + index);
        return productEntity;
    }
}
