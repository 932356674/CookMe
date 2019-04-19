package com.gss.dto;

import com.gss.entity.AliOrder;

public class AliOrderdto extends AliOrder {
    private Long itemsId;
    private Long proItm;
    private Integer productId;
    private Long productNum;

    public AliOrderdto(String orderNum, Integer usId, Long amount, String remark) {
        super(orderNum, usId, amount, remark);
    }

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

    public Integer getProductId() {
        return productId;
    }

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
