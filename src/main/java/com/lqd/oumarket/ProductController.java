/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;

import com.lqd.pojo.Category;
import com.lqd.pojo.Product;
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

/**
 * FXML Controller class
 *
 * @author Gol
 */
public class ProductController implements Initializable {

    static CategoryService s = new CategoryService();
    static ProductService p = new ProductService();

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
            List<Category> cates = s.getCategories();
            this.cbCategories.setItems(FXCollections.observableList(cates));
            loadTableColumns();
            loadTableData(null);

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
                MessageBox.getBox("Question", "Add question successful", Alert.AlertType.INFORMATION).show();
                loadTableData(null);
            }
        } catch (SQLException ex) {
            MessageBox.getBox("Question", "Add question failed", Alert.AlertType.ERROR).show();
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//ĐAng lỗi

    public void discardChangeHandler(ActionEvent event) {

    }

    private void loadTableColumns() {
        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(150);

        TableColumn colUnit = new TableColumn("Unit");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));

        TableColumn colPrice = new TableColumn("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn colOrigin = new TableColumn("Origin");
        colOrigin.setCellValueFactory(new PropertyValueFactory("origin"));

        TableColumn colCate = new TableColumn("CategoryID");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryID"));

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Delete");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Question",
                        "Are you sure to delete this question?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Product prod = (Product) cell.getTableRow().getItem();
                        try {
                            if ((p.deleteProduct(prod.getId()))) {
                                MessageBox.getBox("Product", "Delete successful", Alert.AlertType.INFORMATION).show();
                                this.loadTableData(null);
                            } else {
                                MessageBox.getBox("Product", "Delete failed", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

            });

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        TableColumn colUpdate = new TableColumn();
        colUpdate.setCellFactory(r -> {
            Button btn = new Button("Update");

            btn.setOnAction(evt -> {

                TableRow<Product> row = (TableRow<Product>) ((Button) evt.getSource()).getParent().getParent();
                int rowIndex = row.getIndex();
                Product prod = tbProducts.getItems().get(rowIndex);
                txtName.setText(prod.getName());
                txtUnit.setText(prod.getUnit());
                txtPrice.setText(Float.toString(prod.getPrice()));
                txtOrigin.setText(prod.getOrigin());
                cbCategories.getSelectionModel().select(prod.getCategoryID());
                btnAdd.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setOnAction(event -> {
                    Category selectedCategory = (Category) cbCategories.getValue();
           String categoryId = selectedCategory.getId();
                    prod.setName(txtName.getText());
                    prod.setUnit(txtUnit.getText());
                    prod.setPrice(Float.parseFloat(txtPrice.getText()));
                    prod.setOrigin(txtOrigin.getText());
                    prod.setCategoryID(categoryId);
                    try {

                        if (p.updateProduct(prod)) {

                            MessageBox.getBox("Sucessful", "Update Product successful", Alert.AlertType.INFORMATION).show();
                            loadTableData(null);
                        }
                    } catch (SQLException ex) {
                        MessageBox.getBox("Fail", "Update Product failed", Alert.AlertType.ERROR).show();
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        this.tbProducts.getColumns().addAll(colName, colUnit, colPrice, colOrigin, colCate, colDel, colUpdate);
    }

    private void loadTableData(String kw) throws SQLException {

        List<Product> ques = p.getProducts(kw);

        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(ques));
    }

}