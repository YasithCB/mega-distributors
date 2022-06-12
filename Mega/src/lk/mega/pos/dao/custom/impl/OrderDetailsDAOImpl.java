package lk.mega.pos.dao.custom.impl;

import lk.mega.pos.dao.SQLUtil;
import lk.mega.pos.dao.custom.OrderDetailsDAO;
import lk.mega.pos.entity.OrderDetails;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean insert(OrderDetails orderDetail) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO orderdetails VALUES (?,?,?,?)",orderDetail.getOrderId() ,orderDetail.getItemCode(), orderDetail.getOrderQty(), orderDetail.getDiscount());
    }

    @Override
    public boolean update(OrderDetails od) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exists(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetails search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
