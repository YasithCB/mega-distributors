package lk.mega.pos.bo.custom;

import lk.mega.pos.bo.SuperBO;
import lk.mega.pos.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    String generateNewItemCode() throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    boolean updateItemSoldCount(String code, int qty) throws SQLException, ClassNotFoundException;

    String mostMovedItem() throws SQLException, ClassNotFoundException;

    String leastMovedItem() throws SQLException, ClassNotFoundException;

    ItemDTO leastStockItem() throws SQLException, ClassNotFoundException;
}
