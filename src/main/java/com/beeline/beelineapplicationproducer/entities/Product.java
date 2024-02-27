package com.beeline.beelineapplicationproducer.entities;

import com.beeline.beelineapplicationproducer.constants.Categories;
import java.util.Date;
import java.util.UUID;

public final class Product {
    public long getPrice() {
        return this.price;
    }

    public void setPrice ( final long price ) {
        this.price = price;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount ( final int totalCount ) {
        this.totalCount = totalCount;
    }

    public int getProductWasSoldCount() {
        return this.productWasSoldCount;
    }

    public void setProductWasSoldCount ( final int productWasSoldCount ) {
        this.productWasSoldCount = productWasSoldCount;
    }

    public String getDescription() {
        return this.description;
    }

    public String getProductName() {
        return this.productName;
    }

    public UUID getId() {
        return this.id;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Categories getCategory() {
        return this.category;
    }

    public void setDescription ( final String description ) {
        this.description = description;
    }

    public void setProductName( final String productName ) {
        this.productName = productName;
    }

    public void setCategory( final Categories category ) {
        this.category = category;
    }

    public void setId ( final UUID id ) {
        this.id = id;
    }

    public void setCreatedDate ( final Date createdDate ) {
        this.createdDate = createdDate;
    }

    private long price;

    // количество оставшихся товаров в хранилице
    private int totalCount;

    // количество проданных товаров
    private int productWasSoldCount;

    private String description; // описание товара
    private String productName; // название товара

    private UUID id;

    // дата создания товара
    private Date createdDate;

    private Categories category;
}
