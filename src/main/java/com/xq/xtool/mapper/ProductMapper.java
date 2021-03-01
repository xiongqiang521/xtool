package com.xq.xtool.mapper;

import com.xq.xtool.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper extends JpaRepository<ProductEntity, Long> {


//    @Select("select * from product")
//    public List<ProductEntity> productEntityList();
//
//    @Insert("insert into product(name) values( #{name})")
//    public void save(ProductEntity productEntity);
}
