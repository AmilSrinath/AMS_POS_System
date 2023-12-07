package lk.ijse.bo;

import lk.ijse.bo.Custom.Impl.ItemBOImpl;
import lk.ijse.bo.Custom.Impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        ITEM,USER
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case ITEM:
                return new ItemBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
