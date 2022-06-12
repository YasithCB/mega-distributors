package lk.mega.pos.bo.custom;

import lk.mega.pos.bo.SuperBO;
import lk.mega.pos.dto.CustomerDTO;
import lk.mega.pos.dto.OrderDTO;
import lk.mega.pos.presentation.tdm.OrderTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface OrdersBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    String generateOrderId() throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    ArrayList<OrderTM> getDataToOrderTable() throws SQLException, ClassNotFoundException;

    ArrayList<OrderTM> getDataToOrderTableOnMonth(int year, int month) throws SQLException, ClassNotFoundException;

    ArrayList<OrderTM> getDataToOrderTableOnYear(int year) throws SQLException, ClassNotFoundException;

    ArrayList<OrderTM> getDataToOrderTableOnDay(LocalDate date) throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    double getTotalIncomeOnMonth(int year, int month) throws SQLException, ClassNotFoundException;

    double getTotalIncomeOnDay(LocalDate date) throws SQLException, ClassNotFoundException;

    double getTotalIncomeOnYear(int year) throws SQLException, ClassNotFoundException;
}
