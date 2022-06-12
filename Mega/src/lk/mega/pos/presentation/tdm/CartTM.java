package lk.mega.pos.presentation.tdm;

import java.math.BigDecimal;

public class CartTM {
    private String itemCode;
    private String description;
    private String packSize;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private int qty;
    private BigDecimal subTotal;

    public CartTM(String itemCode, String description, String packSize, BigDecimal unitPrice, BigDecimal discount, int qty, BigDecimal subTotal) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.qty = qty;
        this.subTotal = subTotal;
    }

    public CartTM() {
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", qty=" + qty +
                ", subTotal=" + subTotal +
                '}';
    }
}
