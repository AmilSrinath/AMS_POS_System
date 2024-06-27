package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.ijse.dao.Custom.Impl.HomeDAOImpl;
import lk.ijse.dao.Custom.Impl.ItemDAOImpl;
import lk.ijse.dao.DAOFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashbordFormController implements Initializable {

    public Label lblHighestItemCount;
    public Label lblLowestItemCount;
    public Label lblLowestItemName;
    public Label lblHighestItemName;
    @FXML
    private Label lblProfit;

    @FXML
    private Label lblSellItemTypeCount;

    @FXML
    private Label lblTodayOrderCount;

    @FXML
    private Label lblYesterdayProfit;

    @FXML
    private Label lblYesterdaySellItemTypeCount;

    @FXML
    private Label lblYesterdayOrderCount;

    HomeDAOImpl homeDAO = (HomeDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.HOME);
    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    String lblDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblDate = String.valueOf(LocalDate.now());
            setOrderCount();
            setYesterdayOrderCount();
            setSellItemTypeCount();
            setYesterdaySellItemTypeCount();
            setProfit();
            setYesterdayProfit();
            setItemCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setOrderCount() throws IOException {
        int orderCount = homeDAO.setOrderCount(lblDate);
        lblTodayOrderCount.setText(String.valueOf(orderCount));
    }

    public void setYesterdayOrderCount() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(lblDate);
        LocalDate newDate = date.minusDays(1);
        String result = newDate.format(formatter);

        int yesterdayOrderCount = homeDAO.setYesterdayOrderCount(result);
        lblYesterdayOrderCount.setText(String.valueOf(yesterdayOrderCount));
    }

    public void setSellItemTypeCount() throws IOException {
        int itemTypeCount = homeDAO.setSellItemTypeCount(lblDate);
        lblSellItemTypeCount.setText(String.valueOf(itemTypeCount));
    }

    public void setYesterdaySellItemTypeCount() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(lblDate, formatter);
        LocalDate newDate = date.minusDays(1);
        String result = newDate.format(formatter);

        int itemTypeCount = homeDAO.setYesterdaySellItemTypeCount(result);
        lblYesterdaySellItemTypeCount.setText(String.valueOf(itemTypeCount));
    }

    public void setProfit() throws IOException {
        try {
            double setProfit = homeDAO.setProfit(lblDate);
            lblProfit.setText(String.valueOf(setProfit));
        }catch (NullPointerException e){
            lblProfit.setText("0");
        }
    }

    public void setYesterdayProfit() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(lblDate, formatter);
        LocalDate newDate = date.minusDays(1);
        String result = newDate.format(formatter);

        try {
            double setProfit = homeDAO.setYesterdayProfit(result);
            lblYesterdayProfit.setText(String.valueOf(setProfit));
        }catch (NullPointerException e){
            lblYesterdayProfit.setText("0");
        }
    }

    public void setItemCount() throws IOException {
        lblHighestItemCount.setText(String.valueOf(itemDAO.setHighestItemCount()));
        lblLowestItemCount.setText(String.valueOf(itemDAO.setLowestItemCount()));

        lblHighestItemName.setText(itemDAO.setHighestItemName());
        lblLowestItemName.setText(itemDAO.setLowestItemName());
    }
}