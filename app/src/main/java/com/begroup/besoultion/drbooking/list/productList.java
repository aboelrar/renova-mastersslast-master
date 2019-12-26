package com.begroup.besoultion.drbooking.list;

public class productList {
    String producrName,ProductPrice;
    String productImage;
    int id;

    public productList(String producrName, String productPrice, String productImage,int id) {
        this.producrName = producrName;
        ProductPrice = productPrice;
        this.productImage = productImage;
        this.id=id;
    }

    public String getProducrName() {
        return producrName;
    }

    public void setProducrName(String producrName) {
        this.producrName = producrName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
