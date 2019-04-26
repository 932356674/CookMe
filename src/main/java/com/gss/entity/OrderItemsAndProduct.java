package com.gss.entity;

public class OrderItemsAndProduct extends Product {
    private Long itemsId;
    private Long proItm;
    private Integer productId;
    private Long productNum;

    public Long getItemsId() {
        return itemsId;
    }

    public void setItemsId(Long itemsId) {
        this.itemsId = itemsId;
    }

    public Long getProItm() {
        return proItm;
    }

    public void setProItm(Long proItm) {
        this.proItm = proItm;
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    @Override
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getProductNum() {
        return productNum;
    }

    public void setProductNum(Long productNum) {
        this.productNum = productNum;
    }
}
