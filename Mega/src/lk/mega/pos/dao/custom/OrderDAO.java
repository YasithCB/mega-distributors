package lk.mega.pos.dao.custom;

import lk.mega.pos.dao.CrudDAO;
import lk.mega.pos.dto.OrderDTO;
import lk.mega.pos.entity.Orders;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Orders,String> {
    ArrayList<Orders> getAllOrdersOnRelatedMonth(int year, int month) throws SQLException, ClassNotFoundException;

    ArrayList<Orders> getAllOrdersOnRelatedDay(LocalDate date) throws SQLException, ClassNotFoundException;

    ArrayList<Orders> getAllOrdersOnRelatedYear(int year) throws SQLException, ClassNotFoundException;
}
