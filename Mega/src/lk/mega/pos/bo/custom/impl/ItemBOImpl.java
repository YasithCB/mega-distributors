package lk.mega.pos.bo.custom.impl;

import lk.mega.pos.bo.custom.ItemBO;
import lk.mega.pos.dao.DAOFactory;
import lk.mega.pos.dao.custom.ItemDAO;
import lk.mega.pos.dto.ItemDTO;
import lk.mega.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allDTO = new ArrayList<>();
        for (Item  item : all) {
            allDTO.add(new ItemDTO(
                    item.getCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getSoldCount()
            ));
        }
        return allDTO;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(
                itemDTO.getCode(),
                itemDTO.getDescription(),
                itemDTO.getPackSize(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand(),
                itemDTO.getSoldCount()
        ));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.insert(new Item(
                itemDTO.getCode(),
                itemDTO.getDescription(),
                itemDTO.getPackSize(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand(),
                itemDTO.getSoldCount()
        ));
    }

    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewId();
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDTO(
                item.getCode(),
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getSoldCount()
        );
    }

    @Override
    public boolean updateItemSoldCount(String code,int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateSoldStock(code, qty);
    }

    @Override
    public String mostMovedItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        int max = 0;
        String itemCode = "";
        for (Item item : all) {
            if (max < item.getSoldCount()){
                max = item.getSoldCount();
                itemCode = item.getCode();
            }
        }
        return itemCode;
    }

    @Override
    public String leastMovedItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        int min = 9999999;
        String itemCode = "";
        for (Item item : all) {
            if (min > item.getSoldCount()){
                min = item.getSoldCount();
                itemCode = item.getCode();
            }
        }
        return itemCode;
    }

    @Override
    public ItemDTO leastStockItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ItemDTO leastItem = null;
        int leastQty = 999999;
        for (Item item : all) {
            if (item.getQtyOnHand() < leastQty){
                leastQty = item.getQtyOnHand();
                leastItem = new ItemDTO(item.getCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand());
            }
        }
        return leastItem;
    }
}
