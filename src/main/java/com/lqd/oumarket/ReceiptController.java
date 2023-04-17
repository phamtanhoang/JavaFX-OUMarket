package com.lqd.oumarket;

import com.lqd.pojo.*;
import com.lqd.services.ReceiptDetailService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import com.lqd.services.ReceiptService;
import com.lqd.utils.MessageBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptController implements Initializable{
    @FXML
    private TableView tbReceipt;
    @FXML
    private TableView tbReceiptDetails;
    private static ReceiptService receiptService = new ReceiptService();
    private static ReceiptDetailService receiptDetailService = new ReceiptDetailService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadReceiptColumns();
            loadReceiptDetailColumns();
            loadReceiptData();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void loadReceiptColumns() {

        TableColumn colCreatedDate = new TableColumn("Ngày tạo");
        colCreatedDate.setCellValueFactory(new PropertyValueFactory("createdDate"));
        colCreatedDate.setPrefWidth(100);

        TableColumn colTemp = new TableColumn<>("Tạm tính");
        colTemp.setCellValueFactory(new PropertyValueFactory<>("tempTotal"));
        colTemp.setPrefWidth(70);

        TableColumn colPromo = new TableColumn("Khuyến Mãi");
        colPromo.setCellValueFactory(new PropertyValueFactory("promotionTotal"));
        colPromo.setPrefWidth(80);

        TableColumn colBirthDay = new TableColumn("Sinh Nhật");
        colBirthDay.setCellValueFactory(new PropertyValueFactory("birthDay"));
        colBirthDay.setPrefWidth(80);

        TableColumn colTotal = new TableColumn("Tổng");
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colTotal.setPrefWidth(80);

        TableColumn colStaff = new TableColumn("Nhân Viên");
        colStaff.setCellValueFactory(new PropertyValueFactory("staffID"));
        colStaff.setPrefWidth(100);


        TableColumn colCus = new TableColumn("Khách Hàng");
        colCus.setCellValueFactory(new PropertyValueFactory("customerID"));
        colCus.setPrefWidth(100);
        TableColumn colView = new TableColumn();
        colView.setCellFactory(r -> {
            Button btn = new Button("Xem");

            btn.setOnAction(evt -> {
                System.out.println("Hello");
                TableRow<Receipt> row = (TableRow<Receipt>) ((Button) evt.getSource()).getParent().getParent();
                try {
                    loadReceiptDetailData(row.getItem());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        this.tbReceipt.getColumns().addAll(colCreatedDate, colTemp, colPromo, colBirthDay,colTotal, colStaff, colCus,colView);
    }
    public void loadReceiptDetailColumns() {
        TableColumn colName = new TableColumn("Tên sản phẩm");
        colName.setCellValueFactory(new PropertyValueFactory("productID"));
        colName.setPrefWidth(150);

        TableColumn colQuantity = new TableColumn("Số lượng");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity.setPrefWidth(70);


       this.tbReceiptDetails.getColumns().addAll(colName,colQuantity);

    }
    private void loadReceiptData() throws SQLException {

            List<Receipt> receipts = receiptService.getReceipts();
        this.tbReceipt.getItems().clear();
        System.out.println(receipts);
        for (Receipt receipt : receipts) {
            receipt.setStaffID(receiptService.getStaffName(receipt));
            receipt.setCustomerID(receiptService.getCustomerName(receipt));
        }
        this.tbReceipt.setItems(FXCollections.observableList(receipts));
    }
    public void loadReceiptDetailData(Receipt r) throws SQLException {

        List<ReceiptDetail> receiptDetails = receiptDetailService.getReceiptDetails(r);
        this.tbReceiptDetails.getItems().clear();

        for(ReceiptDetail receiptDetail: receiptDetails){
            receiptDetail.setProductID(receiptDetailService.getProductName(receiptDetail));
        this.tbReceiptDetails.setItems(FXCollections.observableList(receiptDetails));
        }
    }
    public void testHellp(ActionEvent event){
        System.out.println("Đã vào đây");

    }

}
