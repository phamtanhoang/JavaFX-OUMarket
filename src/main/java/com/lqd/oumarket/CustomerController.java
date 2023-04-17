/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;


import com.lqd.pojo.Branch;
import com.lqd.pojo.Customer;
import com.lqd.pojo.User;
import com.lqd.services.BranchService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;


/**
 * FXML Controller class
 *
 * @author Gol
 */
public class CustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TableView<Branch> tbCustomers;

    @FXML
    private BorderPane bp;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox cbSex;
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
            ObservableList<String> sex = FXCollections.observableArrayList("Nam", "Nữ", "Khác");
            cbSex.setItems(sex);
        } catch (SQLException ex) {
            Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
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


        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        TableColumn colSex = new TableColumn("Giới tính");
        colSex.setCellValueFactory(new PropertyValueFactory("sex"));

        TableColumn colBirthday = new TableColumn("Ngày sinh");
        colBirthday.setCellValueFactory(new PropertyValueFactory("birthday"));

        TableColumn colPhoneNumber = new TableColumn("SĐT");
        colBirthday.setCellValueFactory(new PropertyValueFactory("phonenumber"));

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {

            });
            btn.setStyle("-fx-background-color:  red; -fx-text-fill: white;");
            TableCell<Customer, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Customer user = getTableView().getItems().get(getIndex());
                        setGraphic(user.getId() != null && !user.getId().isEmpty() ? btn : null);
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

            });
            btn.setStyle("-fx-background-color:  #4e73df; -fx-text-fill: white;");
            TableCell<Customer, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Customer user = getTableView().getItems().get(getIndex());
                        setGraphic(user.getId() != null && !user.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });
        this.tbCustomers.getColumns().addAll(colName, colEmail, colSex, colBirthday, colPhoneNumber,  colUpdate, colDel);
    }


    private void loadTableData(String kw) throws SQLException {

    }
    private void resetUI(){
        MainUIController mu = new MainUIController();
        mu.loadFxml("CustomerUI", bp);
    }


    public void addCustomerHandler(ActionEvent event) throws SQLException {

    }
    public void CancelCustomerHandler(ActionEvent event) throws SQLException {
        resetUI();
    }

}



