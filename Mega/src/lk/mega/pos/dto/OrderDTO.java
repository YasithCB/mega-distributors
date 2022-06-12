package lk.mega.pos.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO  {
    private String orderId;
    private LocalDate orderDate;
    private String custId;
    private List<OrderDetailDTO> orderDetailsList;
    private double total;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, LocalDate orderDate, String custId, double total) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.custId = custId;
        this.total = total;
    }

    public OrderDTO(String orderId, LocalDate orderDate, String custId, List<OrderDetailDTO> orderDetailsList, double total) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.custId = custId;
        this.orderDetailsList = orderDetailsList;
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

    public List<OrderDetailDTO> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(ArrayList<OrderDetailDTO> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", custId='" + custId + '\'' +
                ", orderDetailsList=" + orderDetailsList +
                ", total=" + total +
                '}';
    }
}
