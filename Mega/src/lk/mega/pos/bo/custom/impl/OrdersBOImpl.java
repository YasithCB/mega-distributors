package lk.mega.pos.bo.custom.impl;

import lk.mega.pos.bo.custom.OrdersBO;
import lk.mega.pos.dao.DAOFactory;
import lk.mega.pos.dao.custom.*;
import lk.mega.pos.data.DBConnection;
import lk.mega.pos.dto.CustomerDTO;
import lk.mega.pos.dto.OrderDTO;
import lk.mega.pos.dto.OrderDetailDTO;
import lk.mega.pos.entity.*;
import lk.mega.pos.presentation.tdm.OrderTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrdersBOImpl implements OrdersBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allDTO = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            allDTO.add(new CustomerDTO(
                    customer.getCustId(),
                    customer.getCustTitle(),
                    customer.getCustName(),
                    customer.getCustAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            ));
        }
        return allDTO;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        // transaction
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);

        // save order
        boolean save = orderDAO.insert(new Orders(orderDTO.getOrderId(), orderDTO.getOrderDate(), orderDTO.getCustId(), orderDTO.getTotal()));
        if (!save){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        // save OrderDetails
        for (OrderDetailDTO orderDetails : orderDTO.getOrderDetailsList()) {

            // update soldCount of items
            itemDAO.updateSoldStock(orderDetails.getItemCode(), orderDetails.getOrderQty());

            boolean save1 = orderDetailsDAO.insert(new OrderDetails(
                    orderDetails.getOrderId(),
                    orderDetails.getItemCode(),
                    orderDetails.getOrderQty(),
                    orderDetails.getDiscount()
            ));
            if (!save1){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        // if successfully saved
        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> orders = new ArrayList<>();
        ArrayList<Orders> all = orderDAO.getAll();
        for (Orders order : all) {
            orders.add(new OrderDTO(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getCustId(),
                    null,
                    order.getTotal()
            ));
        }
        return orders;
    }

    @Override
    public ArrayList<OrderTM> getDataToOrderTable() throws SQLException, ClassNotFoundException {
        ArrayList<OrderTM> orderTMS = new ArrayList<>();
        ArrayList<CustomEntity> customEntities = queryDAO.getDataToOrdersTable();
        for (CustomEntity entity : customEntities) {
            orderTMS.add(new OrderTM(
                    entity.getOrderId(),
                    entity.getOrderDate(),
                    entity.getCustId(),
                    entity.getName(),
                    entity.getCity(),
                    entity.getTotal()
            ));
        }
        return orderTMS;
    }

    @Override
    public ArrayList<OrderTM> getDataToOrderTableOnMonth(int year,int month) throws SQLException, ClassNotFoundException {
        ArrayList<OrderTM> orderTMS = new ArrayList<>();
        ArrayList<CustomEntity> customEntities = queryDAO.getDataToOrdersTable();
        for (CustomEntity entity : customEntities) {
            if (entity.getOrderDate().getMonthValue() == month && entity.getOrderDate().getYear() == year){
                orderTMS.add(new OrderTM(
                        entity.getOrderId(),
                        entity.getOrderDate(),
                        entity.getCustId(),
                        entity.getName(),
                        entity.getCity(),
                        entity.getTotal()
                ));
            }
        }
        return orderTMS;
    }

    @Override
    public ArrayList<OrderTM> getDataToOrderTableOnYear(int year) throws SQLException, ClassNotFoundException {
        ArrayList<OrderTM> orderTMS = new ArrayList<>();
        ArrayList<CustomEntity> customEntities = queryDAO.getDataToOrdersTable();
        for (CustomEntity entity : customEntities) {
            if (entity.getOrderDate().getYear() == year){
                orderTMS.add(new OrderTM(
                        entity.getOrderId(),
                        entity.getOrderDate(),
                        entity.getCustId(),
                        entity.getName(),
                        entity.getCity(),
                        entity.getTotal()
                ));
            }
        }
        return orderTMS;
    }

    @Override
    public ArrayList<OrderTM> getDataToOrderTableOnDay(LocalDate date) throws SQLException, ClassNotFoundException {
        ArrayList<OrderTM> orderTMS = new ArrayList<>();
        ArrayList<CustomEntity> customEntities = queryDAO.getDataToOrdersTable();
        for (CustomEntity entity : customEntities) {
            if (entity.getOrderDate() == date){
                orderTMS.add(new OrderTM(
                        entity.getOrderId(),
                        entity.getOrderDate(),
                        entity.getCustId(),
                        entity.getName(),
                        entity.getCity(),
                        entity.getTotal()
                ));
            }
        }
        return orderTMS;
    }

    @Override
    public boolean updateOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Orders(
                orderDTO.getOrderId(),
                orderDTO.getOrderDate(),
                orderDTO.getCustId(),
                orderDTO.getTotal()
        ));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public double getTotalIncomeOnMonth(int year, int month) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = orderDAO.getAllOrdersOnRelatedMonth(year, month);
        double total = 0;
        for (Orders order : orders) {
                total += order.getTotal();
        }
        return total;
    }

    @Override
    public double getTotalIncomeOnDay(LocalDate date) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = orderDAO.getAllOrdersOnRelatedDay(date);
        double total = 0;
        for (Orders order : orders) {
            total += order.getTotal();
        }
        return total;
    }

    @Override
    public double getTotalIncomeOnYear(int year) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = orderDAO.getAllOrdersOnRelatedYear(year);
        double total = 0;
        for (Orders order : orders) {
            total += order.getTotal();
        }
        return total;
    }


}
