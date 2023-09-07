package com.xq.xtool.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "product_revert")
public class ProductRevertEntity extends BaseEntity {
    private Date deleteTime;

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
