package lk.ijse.bo;

import lk.ijse.bo.Custom.Impl.ItemBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        ITEM
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case ITEM:
                return new ItemBOImpl();
            default:
                return null;
        }
    }
}
