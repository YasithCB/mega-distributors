package lk.mega.pos.dao.custom;

import lk.mega.pos.dao.SuperDAO;
import lk.mega.pos.dto.CustomDTO;
import lk.mega.pos.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<CustomEntity> getDataToOrdersTable() throws SQLException, ClassNotFoundException;
}
