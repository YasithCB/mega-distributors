package lk.mega.pos.presentation.tdm;

import java.math.BigDecimal;

public class ItemSellingTM {
    private String itemCode;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
    private int soldCount;
    private double totalIncome;

    public ItemSellingTM() {
    }

    public ItemSellingTM(String itemCode, String description, BigDecimal unitPrice, int qtyOnHand, int soldCount) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.soldCount = soldCount;
        this.totalIncome = soldCount * Double.parseDouble(String.valueOf(unitPrice));
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

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "ItemSellingTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", soldCount=" + soldCount +
                ", totalIncome=" + totalIncome +
                '}';
    }
}
