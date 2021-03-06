package lk.mega.pos.entity;
import java.time.LocalDate;

public class Orders {
    private String orderId;
    private LocalDate orderDate;
    private String custId;
    private double total;

    public Orders() {
    }

    public Orders(String orderId, LocalDate orderDate, String custId, double total) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.custId = custId;
        this.total = total;
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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", custId='" + custId + '\'' +
                ", total=" + total +
                '}';
    }
}
