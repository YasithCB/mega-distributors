package lk.mega.pos.bo.custom.impl;

import lk.mega.pos.bo.custom.CustomerBO;
import lk.mega.pos.dao.DAOFactory;
import lk.mega.pos.dao.custom.CustomerDAO;
import lk.mega.pos.dto.CustomerDTO;
import lk.mega.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allDTO = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer customer: all) {
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
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.insert(new Customer(
                customerDTO.getId(),
                customerDTO.getTitle(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getCity(),
                customerDTO.getProvince(),
                customerDTO.getPostalCode()
        ));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewId();
    }

    @Override
    public CustomerDTO findCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(
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
    public boolean isCustomerExists(String id) throws SQLException, ClassNotFoundException {
         return customerDAO.exists(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(
                customerDTO.getId(),
                customerDTO.getTitle(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getCity(),
                customerDTO.getProvince(),
                customerDTO.getPostalCode()
        ));
    }
}
