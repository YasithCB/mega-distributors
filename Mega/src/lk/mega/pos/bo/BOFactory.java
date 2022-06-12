package lk.mega.pos.bo;

import lk.mega.pos.bo.custom.impl.CustomerBOImpl;
import lk.mega.pos.bo.custom.impl.ItemBOImpl;
import lk.mega.pos.bo.custom.impl.OrdersBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){
    }

    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMER, ITEM, ORDER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrdersBOImpl();
            default:
                return null;
        }
    }
}
