package lk.mega.pos.dao;

import lk.mega.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO{
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    boolean insert(T dto) throws SQLException, ClassNotFoundException;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    boolean exists(ID id) throws SQLException,ClassNotFoundException;
    boolean delete(ID id) throws SQLException,ClassNotFoundException;
    String generateNewId() throws SQLException,ClassNotFoundException;
    T search(ID id) throws SQLException,ClassNotFoundException;
}
