package lk.mega.pos.dao.custom.impl;

import lk.mega.pos.dao.SQLUtil;
import lk.mega.pos.dao.custom.OrderDAO;
import lk.mega.pos.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = new ArrayList<>();
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM orders");
        while (resultSet.next()) {
            orders.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return orders;
    }

    @Override
    public boolean insert(Orders orders) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO orders VALUES (?,?,?,?)", orders.getOrderId(), orders.getOrderDate() , orders.getCustId(), orders.getTotal());
    }

    @Override
    public boolean update(Orders orders) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE mega.orders SET orderDate= ?, custId= ?, total= ? WHERE orderId= ?",
                orders.getOrderDate(),
                orders.getCustId(),
                orders.getTotal(),
                orders.getOrderId()
        );
    }

    @Override
    public boolean exists(String s) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT oid FROM `Orders` WHERE oid=?", s).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM orders WHERE orderId= ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
        return resultSet.next()? String.format("OD%03d",Integer.parseInt(resultSet.getString(1).replace("OD","")) + 1) : "OD001" ;
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Orders> getAllOrdersOnRelatedMonth(int year, int month) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = new ArrayList<>();
        ResultSet set = SQLUtil.executeQuery("SELECT * FROM orders WHERE orderDate BETWEEN ? AND ?" ,year+"-"+month+"-01", year+"-"+(month+1)+"-01" );
        while (set.next()) {
            orders.add(new Orders(
                    set.getString(1),
                    set.getDate(2).toLocalDate(),
                    set.getString(3),
                    set.getDouble(4)
            ));
        }
        return orders;
    }

    @Override
    public ArrayList<Orders> getAllOrdersOnRelatedDay(LocalDate date) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = new ArrayList<>();
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM orders WHERE orderDate = ? ", date);
        while (resultSet.next()) {
            orders.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return orders;
    }

    @Override
    public ArrayList<Orders> getAllOrdersOnRelatedYear(int year) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = new ArrayList<>();
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM orders WHERE orderDate BETWEEN ? AND ? ", year+"-01-01", year+1+"-01-01");
        while (resultSet.next()) {
            orders.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return orders;
    }
}
