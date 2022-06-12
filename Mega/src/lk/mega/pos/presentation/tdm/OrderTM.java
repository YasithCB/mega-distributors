package lk.mega.pos.presentation.tdm;

import java.time.LocalDate;

public class OrderTM {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private String customerName;
    private String customerCity;
    private double total;

    public OrderTM() {
    }

    public OrderTM(String orderId, LocalDate date, String customerId, String customerName, String customerCity, double total) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCity = customerCity;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerCity='" + customerCity + '\'' +
                ", total=" + total +
                '}';
    }
}
