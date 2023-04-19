/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;

import com.lqd.pojo.Branch;
import com.lqd.pojo.Category;
import com.lqd.pojo.Product;
import com.lqd.pojo.Promotion;
import com.lqd.services.CategoryService;
import com.lqd.services.ProductService;
import com.lqd.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Gol
 */
public class ProductController implements Initializable {

    static CategoryService s = new CategoryService();
    static ProductService p = new ProductService();

    @FXML
    private BorderPane bp;
    @FXML
     private TableView<Product> tbProducts;
    @FXML
    private ComboBox cbCategories;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUnit;
    @FXML
    private TextField txtOrigin;
   
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Category> cates = s.getCategories(null);
            this.cbCategories.setItems(FXCollections.observableList(cates));
            loadTableColumns();
            loadTableData(null);
            btnSave.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
            this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadTableData(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addProductHandler(ActionEvent event) throws SQLException {
        if (txtName.getText() == null || txtName.getText().isEmpty() || txtUnit.getText() == null || txtUnit.getText().isEmpty() || txtOrigin.getText() == null || txtOrigin.getText().isEmpty() || txtPrice.getText() == null || txtPrice.getText().isEmpty()|| cbCategories.getSelectionModel().getSelectedItem() == null ) {
            MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
        } else {
            Category selectedCategory = (Category) cbCategories.getValue();
            String categoryId = selectedCategory.getId();
            Product prod = new Product(
                    this.txtName.getText(),
                    this.txtUnit.getText(),
                    Float.parseFloat(this.txtPrice.getText()),
                    this.txtOrigin.getText(),
                    categoryId
            );
            try {
                if (p.addProduct(prod)) {
                    MessageBox.getBox("Thành công", "Thêm sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                    loadTableData(null);
                }
            } catch (SQLException ex) {
                MessageBox.getBox("Thất bại", "Thêm sản phẩm thất bại", Alert.AlertType.ERROR).show();
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    private void loadTableColumns() {
        TableColumn colName = new TableColumn("Tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(250);
        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));
        colUnit.setPrefWidth(120);
        TableColumn colPrice = new TableColumn("Giá");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colPrice.setCellFactory(column -> {
            TableCell<Product, Float> cell = new TableCell<Product, Float>() {
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
        colPrice.setPrefWidth(140);

        TableColumn colOrigin = new TableColumn("Xuất xứ");
        colOrigin.setCellValueFactory(new PropertyValueFactory("origin"));
        colOrigin.setPrefWidth(140);

        TableColumn colCate = new TableColumn("Loại sản phẩm");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryID"));
        colCate.setPrefWidth(140);

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Thông báo",
                        "Bạn có muốn xóa sản phẩm này không?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Product prod = (Product) cell.getTableRow().getItem();
                        try {
                            if ((p.deleteProduct(prod.getId()))) {
                                MessageBox.getBox("Thành công", "Xóa sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                                this.loadTableData(null);
                            } else {
                                MessageBox.getBox("Thất bại", "Xóa sản phẩm thất bại", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Thất bại", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

            });

            btn.setStyle("-fx-background-color:  red; -fx-text-fill: white;");
            TableCell<Product, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Product branch = getTableView().getItems().get(getIndex());
                        setGraphic(branch.getId() != null && !branch.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });

        TableColumn colUpdate = new TableColumn();
        colUpdate.setCellFactory(r -> {
            Button btn = new Button("Sửa");

            btn.setOnAction(evt -> {

                TableRow<Product> row = (TableRow<Product>) ((Button) evt.getSource()).getParent().getParent();
                int rowIndex = row.getIndex();
                Product prod = tbProducts.getItems().get(rowIndex);
                txtName.setText(prod.getName());
                txtUnit.setText(prod.getUnit());
                txtPrice.setText(Float.toString(prod.getPrice()));
                txtOrigin.setText(prod.getOrigin());

                ObservableList<Category> items = cbCategories.getItems();
                cbCategories.setItems(items);
                for (Category cate : items) {
                    if (cate.getName().equals(prod.getCategoryID())) {
                        this.cbCategories.getSelectionModel().select(cate);
                        break;
                    }
                }
                btnAdd.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setOnAction(event -> {
                    if (txtName.getText() == null || txtName.getText().isEmpty() || txtUnit.getText() == null || txtUnit.getText().isEmpty() || txtOrigin.getText() == null || txtOrigin.getText().isEmpty() || txtPrice.getText() == null || txtPrice.getText().isEmpty()|| cbCategories.getSelectionModel().getSelectedItem() == null ) {
                        MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
                    } else {
                        Category selectedCategory = (Category) cbCategories.getValue();
                        String categoryId = selectedCategory.getId();
                        prod.setName(txtName.getText());
                        prod.setUnit(txtUnit.getText());
                        prod.setPrice(Float.parseFloat(txtPrice.getText()));
                        prod.setOrigin(txtOrigin.getText());
                        prod.setCategoryID(categoryId);
                        try {

                            if (p.updateProduct(prod)) {
                                resetUI();
                                MessageBox.getBox("Thành công", "Sửa sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                                loadTableData(null);
                            }
                        }
                        catch (SQLException ex) {
                            MessageBox.getBox("Thất bại", "Sửa sản phẩm thất bại", Alert.AlertType.ERROR).show();
                            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });

            btn.setStyle("-fx-background-color:  #4e73df; -fx-text-fill: white;");
            TableCell<Product, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Product branch = getTableView().getItems().get(getIndex());
                        setGraphic(branch.getId() != null && !branch.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });
        this.tbProducts.getColumns().addAll(colName, colUnit, colPrice, colOrigin, colCate,colUpdate, colDel);
    }

    private void loadTableData(String kw) throws SQLException {

        List<Product> prods = p.getProducts(kw);
        for (Product prod : prods) {
            String CategoryID = prod.getCategoryID();
            Category cate = s.getCategoryByID(CategoryID);
            prod.setCategoryID(cate != null ? cate.getName() : "");
        }
        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(prods));
    }

    private void resetUI(){
        MainUIController mu = new MainUIController();
        mu.loadFxml("ProductUI", bp);
    }
    public void CancelProductHandler(ActionEvent event) throws SQLException {
        resetUI();
    }
}