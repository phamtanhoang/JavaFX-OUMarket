/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;


import com.lqd.pojo.*;
import com.lqd.services.BranchService;
import com.lqd.services.CustomerService;
import com.lqd.utils.MessageBox;
import java.net.URL;
import java.sql.Date;
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
    TableView<Customer> tbCustomers;

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

    CustomerService customerService= new CustomerService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTableColumns();
            loadTableData(null);
            btnSave.setVisible(false);
            ObservableList<String> sex = FXCollections.observableArrayList("Nam", "Nữ", "Khác");
            cbSex.setItems(sex);
            cbSex.getSelectionModel().selectFirst();
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
        colName.setPrefWidth(200);

        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colEmail.setPrefWidth(170);

        TableColumn colSex = new TableColumn("Giới tính");
        colSex.setCellValueFactory(new PropertyValueFactory("sex"));
        colSex.setPrefWidth(120);

        TableColumn colBirthday = new TableColumn("Ngày sinh");
        colBirthday.setCellValueFactory(new PropertyValueFactory("dateOfBirth"));

        colBirthday.setPrefWidth(150);

        TableColumn colPhoneNumber = new TableColumn("SĐT");
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        colPhoneNumber.setPrefWidth(150);


        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Thông báo",

                        "Bạn muốn xóa khách hàng này không?",

                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();

                        Customer cus = (Customer) cell.getTableRow().getItem();
                        try {
                            boolean btrue=customerService.deleteCustomer(cus.getId());
                            if (btrue) {
                                MessageBox.getBox("Thông báo", "Xóa khách hàng thành công", Alert.AlertType.INFORMATION).show();
                                this.loadTableData(null);
                            } else {
                                MessageBox.getBox("Thông báo", "Xóa khách hàng nhánh thất bại", Alert.AlertType.ERROR).show();
                            }


                        } catch (SQLException ex) {
                            MessageBox.getBox("Thông báo", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                        }



                    }
                });
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
                TableRow<Customer> row = (TableRow<Customer>) ((Button) evt.getSource()).getParent().getParent();
                int rowIndex = row.getIndex();
                Customer cus = tbCustomers.getItems().get(rowIndex);
                txtName.setText(cus.getName());
                txtPhoneNumber.setText(cus.getPhoneNumber());
                txtEmail.setText(cus.getEmail());
                cbSex.getSelectionModel().select(cus.getSex());
                dpDateOfBirth.setValue(cus.getDateOfBirth().toLocalDate());


                btnAdd.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setOnAction(event -> {
                    if (txtName.getText() == null || txtName.getText().isEmpty() || txtPhoneNumber.getText() == null || txtPhoneNumber.getText().isEmpty() || txtEmail.getText() == null || txtEmail.getText().isEmpty()  || dpDateOfBirth.getValue() == null || cbSex.getSelectionModel().getSelectedItem() == null) {
                        MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
                    }
                    else{
                        cus.setName(txtName.getText());
                        cus.setPhoneNumber(txtPhoneNumber.getText());
                        cus.setEmail(txtEmail.getText());
                        cus.setSex(cbSex.getSelectionModel().getSelectedItem().toString());
                        cus.setDateOfBirth(java.sql.Date.valueOf(dpDateOfBirth.getValue()));
                        try {
                            if (customerService.updateCustomer(cus)) {
                                MessageBox.getBox("Thành công", "Chỉnh sửa khách hàng thành công", Alert.AlertType.INFORMATION).show();
                                loadTableData(null);
                                resetUI();
                            }
                        } catch (SQLException ex) {
                            MessageBox.getBox("Thất bại", "Chỉnh sửa khách hàng thất bại", Alert.AlertType.ERROR).show();
                            Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
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
        if(LoginController.userLogin.getRole().toLowerCase().equals("admin")){
            this.tbCustomers.getColumns().addAll(colName, colEmail, colSex, colBirthday, colPhoneNumber,  colUpdate, colDel);
        }
        if(LoginController.userLogin.getRole().toLowerCase().equals("staff")){
            this.tbCustomers.getColumns().addAll(colName, colEmail, colSex, colBirthday, colPhoneNumber,  colUpdate);
        }
    }


    private void loadTableData(String kw) throws SQLException {

        List<Customer> cus = customerService.getCustomersByPhoneNumber(kw);
        this.tbCustomers.getItems().clear();
        this.tbCustomers.setItems(FXCollections.observableList(cus));

    }
    private void resetUI(){
        MainUIController mu = new MainUIController();
        mu.loadFxml("CustomerUI", bp);
    }


    public void addCustomerHandler(ActionEvent event) throws SQLException {

        if (txtName.getText() == null || txtName.getText().isEmpty() || txtPhoneNumber.getText() == null || txtPhoneNumber.getText().isEmpty() || txtEmail.getText() == null || txtEmail.getText().isEmpty() || dpDateOfBirth.getValue() == null || cbSex.getSelectionModel().getSelectedItem() == null) {
            MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
        } else {
            Customer cus = new Customer(
                    txtName.getText(),
                    java.sql.Date.valueOf(dpDateOfBirth.getValue()),
                    cbSex.getSelectionModel().getSelectedItem().toString(),
                    txtPhoneNumber.getText(),
                    txtEmail.getText()
            );
            try {
                if (customerService.addCustomer(cus)) {
                    MessageBox.getBox("Thành công", "Thêm khách hàng mới thành công", Alert.AlertType.INFORMATION).show();
                    loadTableData(null);
                    resetUI();
                }
            } catch (SQLException ex) {
                MessageBox.getBox("Thất bại", "Thêm khách hàng mới thất bại", Alert.AlertType.ERROR).show();
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    public void CancelCustomerHandler(ActionEvent event) throws SQLException {
        resetUI();
    }

}



