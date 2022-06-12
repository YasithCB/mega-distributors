package lk.mega.pos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomEntity {
    private String custId;
    private String title;
    private String name;
    private String address;
    private String city;
    private String province;
    private String postalCode;

    private String itemCode;
    private String description;
    private String packSize;
    private BigDecimal unitPrice;
    private int qtyOnHand;
    private int soldCount;

    private String orderId;
    private LocalDate orderDate;
    private double total;

    private int orderQty;
    private BigDecimal discount;

    public CustomEntity() {
    }

    public CustomEntity(String orderId, LocalDate date, String custId, String name, String city, double total) {
        this.custId = custId;
        this.name = name;
        this.city = city;
        this.orderId = orderId;
        this.orderDate = date;
        this.total = total;
    }

    public CustomEntity(String custId, String title, String name, String address, String city, String province, String postalCode, String itemCode, String description, String packSize, BigDecimal unitPrice,
                        int qtyOnHand, int soldCount, String orderId, LocalDate orderDate, double total, int orderQty, BigDecimal discount) {
        this.custId = custId;
        this.title = title;
        this.name = name;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.soldCount = soldCount;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.total = total;
        this.orderQty = orderQty;
        this.discount = discount;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "custId='" + custId + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", soldCount=" + soldCount +
                ", orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", total=" + total +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                '}';
    }
}
