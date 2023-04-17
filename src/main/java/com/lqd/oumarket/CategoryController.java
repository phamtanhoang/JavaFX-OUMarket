package com.lqd.oumarket;


import com.lqd.pojo.Category;
import com.lqd.services.CategoryService;
import com.lqd.utils.MessageBox;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryController implements Initializable {

    CategoryService p = new CategoryService();
    /**
     * Initializes the controller class.
     */
    @FXML
    TableView<Category> tbCategories;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSave;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTableColumns();
            loadTableData(null);
            btnSave.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadTableData(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void loadTableColumns() {
        TableColumn colName = new TableColumn("Tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(200);


        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");


            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Thông báo",
                        "Bạn muốn xóa loại sản phẩm này không này không?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Category cate = (Category) cell.getTableRow().getItem();
                        try {
                            boolean btrue=p.deleteCategory(cate.getId());
                            if (btrue) {
                                MessageBox.getBox("Thông báo", "Xóa loại sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                                this.loadTableData(null);
                            } else {
                                MessageBox.getBox("Thông báo", "Xóa loại sản phẩm nhánh thất bại", Alert.AlertType.WARNING).show();
                            }
                        } catch (SQLException ex) {
                            MessageBox.getBox("Thông báo", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
                        }


                    }
                });


            });
            btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            TableCell<Category, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Category cate = getTableView().getItems().get(getIndex());
                        setGraphic(cate.getId() != null && !cate.getId().isEmpty() ? btn : null);
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
                TableRow<Category> row = (TableRow<Category>) ((Button) evt.getSource()).getParent().getParent();
                int rowIndex = row.getIndex();
                Category cate = tbCategories.getItems().get(rowIndex);
                txtName.setText(cate.getName());

                btnAdd.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setOnAction(event -> {
                    if(txtName.getText().isEmpty()){
                        MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
                    }
                    else{
                        cate.setName(txtName.getText());
                        try {
                            if (p.updateCategory(cate)) {
                                MessageBox.getBox("Thông báo", "Chỉnh sửa loại sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                                loadTableData(null);
                                resetUI();
                            }
                        } catch (SQLException ex) {
                            MessageBox.getBox("Thông báo", "Chỉnh sửa loại sản phẩm thất bại", Alert.AlertType.ERROR).show();
                            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });

            btn.setStyle("-fx-background-color:  #4e73df; -fx-text-fill: white;");
            TableCell<Category, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Category cate = getTableView().getItems().get(getIndex());
                        setGraphic(cate.getId() != null && !cate.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });
        this.tbCategories.getColumns().addAll(colName, colUpdate, colDel);
    }


    private void loadTableData(String kw) throws SQLException {
        List<Category> cate = p.getCategories(kw);
        this.tbCategories.getItems().clear();
        this.tbCategories.setItems(FXCollections.observableList(cate));
    }
    private void resetUI(){
        MainUIController mu = new MainUIController();
        mu.loadFxml("CategoryUI", bp);
    }


    public void addCategoryHandler(ActionEvent event) throws SQLException {
        if (txtName.getText() == null || txtName.getText().isEmpty()){
            MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
        } else {
            Category cate = new Category(txtName.getText());
            try {
                if (p.addCategory(cate)) {
                    MessageBox.getBox("Thông báo", "Thêm loại sản phẩm mới thành công", Alert.AlertType.INFORMATION).show();
                    loadTableData(null);
                    resetUI();
                }
            } catch (SQLException ex) {
                MessageBox.getBox("Thông báo", "Thêm loại sản phẩm mới thất bại", Alert.AlertType.ERROR).show();
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void CancelCategoryHandler(ActionEvent event) throws SQLException {
        resetUI();
    }

}
