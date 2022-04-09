package com.example.giuaki.model;

public class DetailTimekeeping {
    private String nameProduct;
    private String price;
    private String value;
    private String productErr;
    private int sum;

    public DetailTimekeeping(String nameProduct, String price, String value, String productErr, int sum) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.value = value;
        this.productErr = productErr;
        this.sum = sum;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProductErr() {
        return productErr;
    }

    public void setProductErr(String productErr) {
        this.productErr = productErr;
    }

    public int getSum() {
        return (Integer.parseInt(price)*Integer.parseInt(value));
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
