package lk.mega.pos.dao.custom.impl;

import lk.mega.pos.dao.SQLUtil;
import lk.mega.pos.dao.custom.CustomerDAO;
import lk.mega.pos.entity.Customer;
import lk.mega.pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT * FROM customer");
        ArrayList<Customer> all = new ArrayList<>();
        while (rs.next()) {
            all.add(new Customer(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            ));
        }
        return all;
    }

    @Override
    public boolean insert(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO customer VALUES (?,?,?,?,?,?,?)",
                customer.getCustId(),
                customer.getCustTitle(),
                customer.getCustName(),
                customer.getCustAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode()
        );
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE customer SET custTitle= ?, custName = ?, custAddress = ?, city= ?, province= ?, postalCode= ? WHERE custId= ?",
                customer.getCustTitle(),
                customer.getCustName(),
                customer.getCustAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode(),
                customer.getCustId()
        );
    }

    @Override
    public boolean exists(String s) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT id FROM customer WHERE custId = ?", s);
        return rs.next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM customer WHERE custId = ?",id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT custId FROM customer ORDER BY custId DESC LIMIT 1");
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            int newIdNo = Integer.parseInt(lastId.replace("C","")) + 1;
            return String.format("C%03d",newIdNo);
        }else {
            return "C001";
        }

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT * FROM Customer WHERE custId = ?", id);
        while (rs.next()) {
            return new Customer(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
        }
        return null;
    }
}
