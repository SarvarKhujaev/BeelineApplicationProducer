package com.beeline.beelineapplicationproducer.entities;

import com.beeline.beelineapplicationproducer.constants.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class Order {
    public int getTotalOrderSum() {
        return this.totalOrderSum;
    }

    public void setTotalOrderSum( final int totalOrderSum ) {
        this.totalOrderSum = totalOrderSum;
    }

    public int getTotalCountOfProductsInOrder() {
        return this.totalCountOfProductsInOrder;
    }

    public void setTotalCountOfProductsInOrder( final int totalCountOfProductsInOrder ) {
        this.totalCountOfProductsInOrder = totalCountOfProductsInOrder;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public UUID getId() {
        return this.id;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus( final OrderStatus orderStatus ) {
        this.orderStatus = orderStatus;
    }

    public List< Product > getProductList() {
        return this.productList;
    }

    public void setId ( final UUID id ) {
        this.id = id;
    }

    public void setUserId ( final UUID userId ) {
        this.userId = userId;
    }

    public void setCreatedDate ( final Date createdDate ) {
        this.createdDate = createdDate;
    }

    public void setProductList ( final List< Product > productList ) {
        this.productList = productList;
    }

    // общая стоимость заказа
    private int totalOrderSum;

    // общее количество товаров в заказе
    private int totalCountOfProductsInOrder;

    private UUID id;

    // id пользователя который сделал заказ
    private UUID userId;

    // дата создания заказа
    private Date createdDate;

    private OrderStatus orderStatus;

    private List< Product > productList;
}
