package lk.mega.pos.dao.custom;

import lk.mega.pos.dao.CrudDAO;
import lk.mega.pos.dto.ItemDTO;
import lk.mega.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {

    boolean updateSoldStock(String code, int qty) throws SQLException, ClassNotFoundException;
}
