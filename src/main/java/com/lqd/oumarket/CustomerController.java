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
    private CustomerService customerService = new CustomerService();
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


        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        TableColumn colSex = new TableColumn("Giới tính");
        colSex.setCellValueFactory(new PropertyValueFactory("sex"));

        TableColumn colBirthday = new TableColumn("Ngày sinh");
        colBirthday.setCellValueFactory(new PropertyValueFactory("dateOfBirth"));

        TableColumn colPhoneNumber = new TableColumn("SĐT");
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

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
                        Customer customer = (Customer) cell.getTableRow().getItem();
                        try {
                            if ((customerService.deleteCustomer(customer.getId()))) {
                                MessageBox.getBox("Thành công", "Xóa sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                                this.loadTableData(null);
                            } else {
                                MessageBox.getBox("Thất bại", "Xóa sản phẩm thất bại", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Thất bại", ex.getMessage(), Alert.AlertType.WARNING).show();
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
                txtEmail.setText(cus.getEmail());
                txtPhoneNumber.setText(cus.getPhoneNumber());
                dpDateOfBirth.setValue(cus.getDateOfBirth().toLocalDate());
                int sex;
                if (cus.getSex().equals("Nam"))
                sex= 0;
                else if (cus.getSex().equals("Nữ"))
                sex= 1;
                else sex= 2;
                cbSex.getSelectionModel().select(sex);
                System.out.println( cus.getSex());

                btnAdd.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setOnAction(event -> {

                    String  sexString =  cbSex.getValue().toString();

                    cus.setName(txtName.getText());
                    cus.setEmail(txtEmail.getText());
                    cus.setPhoneNumber(txtPhoneNumber.getText());
                    cus.setDateOfBirth(Date.valueOf(dpDateOfBirth.getValue()));
                    cus.setSex(sexString);

                    try {

                        if (customerService.updateCustomer(cus)) {
                            resetUI();
                            MessageBox.getBox("Thành công", "Sửa thông tin thành công", Alert.AlertType.INFORMATION).show();
                            loadTableData(null);
                        }
                    } catch (SQLException ex) {
                        MessageBox.getBox("Thất bại", "Sửa thông tin thất bại", Alert.AlertType.ERROR).show();
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
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
        this.tbCustomers.getColumns().addAll(colName, colEmail, colSex, colBirthday, colPhoneNumber,  colUpdate, colDel);
    }


    private void loadTableData(String kw) throws SQLException {
        List<Customer> customers = customerService.getCustomers(null);
        this.tbCustomers.getItems().clear();

        this.tbCustomers.setItems(FXCollections.observableList(customers));
    }
    private void resetUI(){
        MainUIController mu = new MainUIController();
        mu.loadFxml("CustomerUI", bp);
    }


    public void addCustomerHandler(ActionEvent event) throws SQLException {
       String sex = cbSex.getValue().toString();
        Customer customer = new Customer(
                this.txtName.getText(),
                Date.valueOf(dpDateOfBirth.getValue()),
                sex,
                (this.txtPhoneNumber.getText()),
                this.txtEmail.getText());
        try {
            if (customerService.addCustomer(customer)) {
                MessageBox.getBox("Thành công", "Thêm khách hàng thành công", Alert.AlertType.INFORMATION).show();
                loadTableData(null);
            }
        } catch (SQLException ex) {
            MessageBox.getBox("Thất bại", "Thêm khách hàng thất bại", Alert.AlertType.ERROR).show();
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CancelCustomerHandler(ActionEvent event) throws SQLException {
        resetUI();
    }

}



