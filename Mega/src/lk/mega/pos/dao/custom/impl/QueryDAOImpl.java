package lk.mega.pos.dao.custom.impl;

import lk.mega.pos.dao.SQLUtil;
import lk.mega.pos.dao.custom.QueryDAO;
import lk.mega.pos.entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomEntity> getDataToOrdersTable() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> arrayList = new ArrayList<>();
        ResultSet set = SQLUtil.executeQuery("SELECT o.orderId,o.orderDate,c.custId,c.custName,c.city,o.total FROM mega.orders o INNER JOIN mega.customer c ON c.custId = o.custId");
        while (set.next()) {
            arrayList.add(new CustomEntity(
                    set.getString(1),
                    set.getDate(2).toLocalDate(),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    set.getDouble(6)
            ));
        }
        return arrayList;
    }

}
