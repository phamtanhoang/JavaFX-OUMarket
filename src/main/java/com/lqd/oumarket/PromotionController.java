/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;

import com.lqd.pojo.Branch;
import com.lqd.pojo.Product;
import com.lqd.pojo.Promotion;
import com.lqd.services.ProductService;
import com.lqd.services.PromotionService;
import com.lqd.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gol
 */
public class PromotionController implements Initializable {

    static PromotionService p = new PromotionService();
    static ProductService prod = new ProductService();
    @FXML
    private TableView<Promotion> tbPromotions;
    @FXML
    private DatePicker dpFromDate;
    @FXML
    private DatePicker dpToDate;
    @FXML
    private TextField txtNewPrice;
    @FXML
    private ComboBox cbProducts;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTableColumns();
            loadTableData();
            resetUI();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void resetUI() throws SQLException {
        cbProducts.getSelectionModel().clearSelection();
        cbProducts.setItems(FXCollections.observableArrayList(prod.getProducts(null)));
        cbProducts.getSelectionModel().clearSelection();
        btnAdd.setVisible(true);
        btnSave.setVisible(false);
        dpFromDate.setValue(null);
        dpToDate.setValue(null);
        txtNewPrice.setText("");
    }

    public void addPromotionHandler(ActionEvent event) throws SQLException {
        Product selectedProduct = (Product) cbProducts.getSelectionModel().getSelectedItem();
        String id = selectedProduct.getId();
        Promotion promotion = new Promotion(
                java.sql.Date.valueOf(this.dpFromDate.getValue()),
                java.sql.Date.valueOf(this.dpToDate.getValue()),
                Float.parseFloat(txtNewPrice.getText()),
                selectedProduct.getId());

        try {
            if (p.addPromotion(promotion)) {
                MessageBox.getBox("Thông báo", "Thêm thành công", Alert.AlertType.INFORMATION).show();
                loadTableData();
                resetUI();
            }
        } catch (SQLException ex) {
            MessageBox.getBox("Thông báo", "Thêm thất bại", Alert.AlertType.ERROR).show();
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void discardSaveHandler(ActionEvent event) throws SQLException {
        loadTableData();
        resetUI();
    }


    public void loadTableColumns() {

        TableColumn colProductId = new TableColumn("Sản phẩm");
        colProductId.setCellValueFactory(new PropertyValueFactory("productID"));
        colProductId.setPrefWidth(200);

        TableColumn<Promotion, Float> colNewPrice = new TableColumn<>("Giá khuyến mãi");
        colNewPrice.setCellValueFactory(new PropertyValueFactory<>("newPrice"));
        colNewPrice.setCellFactory(column -> {
            TableCell<Promotion, Float> cell = new TableCell<Promotion, Float>() {
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
        colNewPrice.setPrefWidth(120);

        TableColumn colFromDate = new TableColumn("Từ ngày");
        colFromDate.setCellValueFactory(new PropertyValueFactory("fromDate"));
        colFromDate.setPrefWidth(150);

        TableColumn colToDate = new TableColumn("Đến ngày");
        colToDate.setCellValueFactory(new PropertyValueFactory("toDate"));
        colToDate.setPrefWidth(150);

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Thông báo",
                        "Bạn có muốn xóa khuyến mãi này?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Promotion prod = (Promotion) cell.getTableRow().getItem();
                        try {
                            if ((p.deletePromotion(prod.getId()))) {
                                MessageBox.getBox("Khuyến mãi", "Xóa thành công", Alert.AlertType.INFORMATION).show();
                                this.loadTableData();
                                this.resetUI();
                            } else {
                                MessageBox.getBox("Khuyến mãi", "Xóa thất bại", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Thông báo", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

            });

            TableCell<Promotion, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Promotion promotion = getTableView().getItems().get(getIndex());
                        setGraphic(promotion.getId() != null && !promotion.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return c;
        });
        TableColumn colUpdate = new TableColumn();
        colUpdate.setCellFactory(r -> {
            Button btn = new Button("Sửa");

            btn.setOnAction(evt -> {

                TableRow<Product> row = (TableRow<Product>) ((Button) evt.getSource()).getParent().getParent();
                int rowIndex = row.getIndex();
                Promotion promotion = tbPromotions.getItems().get(rowIndex);
                ObservableList<Product> items = cbProducts.getItems();
                cbProducts.setItems(items);
                for (Product prod : items) {
                    if (prod.getName().equals(promotion.getProductID())) {
                        this.cbProducts.getSelectionModel().select(prod);
                        break;
                    }
                }


                txtNewPrice.setText(Float.toString(promotion.getNewPrice()));

                dpFromDate.setValue(promotion.getFromDate().toLocalDate());
                dpToDate.setValue(promotion.getToDate().toLocalDate());
                btnAdd.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setOnAction(event -> {
                    Product selectedProduct = (Product) cbProducts.getSelectionModel().getSelectedItem();
                    String id = selectedProduct.getId();
                    promotion.setProductID(id);
                    promotion.setFromDate(java.sql.Date.valueOf(this.dpFromDate.getValue()));
                    promotion.setToDate(java.sql.Date.valueOf(this.dpToDate.getValue()));
                    promotion.setNewPrice(Float.parseFloat(txtNewPrice.getText()));
                    try {

                        if (p.updatePromotion(promotion)) {
                            MessageBox.getBox("Thành công", "Chỉnh sửa thành công", Alert.AlertType.INFORMATION).show();
                            loadTableData();
                            resetUI();
                        }
                    } catch (SQLException ex) {
                        btnAdd.setVisible(true);
                        btnSave.setVisible(false);
                        MessageBox.getBox("Thất bại", "Chỉnh sửa thất bại", Alert.AlertType.ERROR).show();
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
            TableCell<Promotion, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Promotion promotion = getTableView().getItems().get(getIndex());
                        setGraphic(promotion.getId() != null && !promotion.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return c;
        });
        this.tbPromotions.getColumns().addAll(colProductId, colNewPrice, colFromDate, colToDate, colDel, colUpdate);
    }


    private void loadTableData() throws SQLException {
        List<Promotion> promos = p.getPromotion();
        this.tbPromotions.getItems().clear();
        for (Promotion promo : promos) {
            String productID = promo.getProductID();
            Product product = prod.getProductbyID(productID);
            promo.setProductID(product != null ? product.getName() : "");
        }
        this.tbPromotions.setItems(FXCollections.observableList(promos));
    }
}
