package com.lqd.oumarket;

import com.lqd.pojo.*;
import com.lqd.services.ReceiptDetailService;

import javafx.fxml.Initializable;
import com.lqd.services.ReceiptService;
import com.lqd.utils.MessageBox;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptController implements Initializable{

    private BorderPane bp;
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

        colTemp.setPrefWidth(100);
        colTemp.setCellFactory(column -> {
            TableCell<Receipt, Float> cell = new TableCell<>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        String formattedPrice = String.format("%,.0f VNĐ", item);
                        setText(formattedPrice);
                    }
                }
            };
            return cell;
        });

        TableColumn colPromo = new TableColumn("Khuyến Mãi");
        colPromo.setCellValueFactory(new PropertyValueFactory("promotionTotal"));
        colPromo.setPrefWidth(100);
        colPromo.setCellFactory(column -> {
            TableCell<Receipt, Float> cell = new TableCell<>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        String formattedPrice = String.format("%,.0f VNĐ", item);
                        setText(formattedPrice);
                    }
                }
            };
            return cell;
        });

        TableColumn colBirthDay = new TableColumn("Sinh Nhật");
        colBirthDay.setCellValueFactory(new PropertyValueFactory("birthDay"));
        colBirthDay.setPrefWidth(100);
        colBirthDay.setCellFactory(column -> {
            TableCell<Receipt, Float> cell = new TableCell<>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText("Giảm " + String.format("%.0f", item*100) + "%");
                    }
                }
            };
            return cell;
        });


        TableColumn colTotal = new TableColumn("Tổng");
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colTotal.setPrefWidth(100);
        colTotal.setCellFactory(column -> {
            TableCell<Receipt, Float> cell = new TableCell<>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        String formattedPrice = String.format("%,.0f VNĐ", item);
                        setText(formattedPrice);
                    }
                }
            };
            return cell;
        });


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

                TableRow<Receipt> row = (TableRow<Receipt>) ((Button) evt.getSource()).getParent().getParent();
                try {
                    loadReceiptDetailData(row.getItem());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });


            btn.setStyle("-fx-background-color:  #4e73df; -fx-text-fill: white;");
            TableCell<Receipt, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Receipt rec = getTableView().getItems().get(getIndex());
                        setGraphic(rec.getId() != null && !rec.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Thông báo",
                        "Bạn muốn xóa hóa đơn này không?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Receipt rec = (Receipt) cell.getTableRow().getItem();
                        try {
                            boolean btrue = receiptService.deleteReceipt(rec.getId());
                            if (btrue) {
                                MessageBox.getBox("Thành công", "Xóa hóa đơn thành công", Alert.AlertType.INFORMATION).show();
                                resetUI();
                            } else {
                                MessageBox.getBox("Thất bại", "Xóa hóa đơn thất bại", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Thông báo", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
                        }


                    }
                });
            });

            btn.setStyle("-fx-background-color:  red; -fx-text-fill: white;");
            TableCell<Receipt, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Receipt rec = getTableView().getItems().get(getIndex());
                        setGraphic(rec.getId() != null && !rec.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });
        this.tbReceipt.getColumns().addAll(colCreatedDate, colTemp, colPromo, colBirthDay,colTotal, colStaff, colCus,colView, colDel);

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
    private void resetUI(){
        MainUIController mu = new MainUIController();
        mu.loadFxml("RecieptUI", bp);
    }
}

