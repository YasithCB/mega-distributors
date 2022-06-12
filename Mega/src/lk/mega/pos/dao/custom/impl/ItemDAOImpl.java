package lk.mega.pos.dao.custom.impl;

import lk.mega.pos.dao.SQLUtil;
import lk.mega.pos.dao.custom.ItemDAO;
import lk.mega.pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT * FROM item");
        ArrayList<Item> all = new ArrayList<>();
        while (rs.next()) {
            all.add(new Item(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getBigDecimal(4),
                    rs.getInt(5),
                    rs.getInt(6)
            ));
        }
        return all;
    }

    @Override
    public boolean insert(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO item VALUES (?,?,?,?,?,?)",
                item.getCode(),
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                0
        );
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE item SET description= ?, packSize= ?, unitPrice= ?, qtyOnHand= ? WHERE itemCode= ?",
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getCode()
        );
    }

    @Override
    public boolean exists(String s) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT code FROM Item WHERE code=?", s);
        return rs.next();
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM item WHERE itemCode= ?",code);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1");
        if (resultSet.next()) {
            String lastCode = resultSet.getString(1);
            int newCodeNo = Integer.parseInt(lastCode.replace("IT","")) + 1;
            return String.format("IT%03d",newCodeNo);
        }else {
            return "IT001";
        }
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT * FROM Item WHERE itemCode = ?", code);
        while (rs.next()) {
            return new Item(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getBigDecimal(4),
                    rs.getInt(5),
                    rs.getInt(6)
            );
        }
        return null;
    }

    @Override
    public boolean updateSoldStock(String code, int qty) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE item SET soldCount = soldCount + ? WHERE itemCode = ?",qty, code);
    }
}
