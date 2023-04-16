/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;

import com.lqd.pojo.Branch;
import com.lqd.pojo.Category;
import com.lqd.pojo.User;
import com.lqd.services.BranchService;
import com.lqd.services.UserService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lqd.utils.HashPassword;
import com.lqd.utils.MessageBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gol
 */
public class UserController implements Initializable {

    UserService u = new UserService();
    BranchService p = new BranchService();

    HashPassword hash = new HashPassword();
    /**
     * Initializes the controller class.
     */
    @FXML
    TableView<User> tbUsers;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private ComboBox cbRole;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox cbBranch;
    @FXML
    private ComboBox cbSex;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField pwfPassWord;
    @FXML
    private PasswordField pwfConfirmPassWord;
    @FXML
    private TextField txtSearch;
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
            loadTableData(null);
            resetUI(LoginController.userLogin);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
        colName.setPrefWidth(155);

        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colEmail.setPrefWidth(200);

        TableColumn colBranch = new TableColumn("Chi nhánh");
        colBranch.setCellValueFactory(new PropertyValueFactory("branchID"));
        colBranch.setPrefWidth(200);

        TableColumn colRole = new TableColumn("Vai trò");
        colRole.setCellValueFactory(new PropertyValueFactory("role"));
        colRole.setPrefWidth(70);

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Thông báo",
                        "Bạn muốn xóa người dùng này không?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        User user = (User) cell.getTableRow().getItem();
                        try {
                            boolean btrue = u.deleteUser(user.getId());
                            if (btrue) {
                                MessageBox.getBox("Thông báo", "Xóa người dùng thành công", Alert.AlertType.INFORMATION).show();
                                this.loadTableData(null);
                            } else {
                                MessageBox.getBox("Thông báo", "Xóa người dùng thất bại", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Thông báo", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

            });

            TableCell<User, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        User user = getTableView().getItems().get(getIndex());
                        setGraphic(user.getId() != null && !user.getId().isEmpty() ? btn : null);
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
                TableRow<User> row = (TableRow<User>) ((Button) evt.getSource()).getParent().getParent();
                int rowIndex = row.getIndex();
                User user = tbUsers.getItems().get(rowIndex);
                txtName.setText(user.getName());
                txtAddress.setText(user.getAddress());
                txtPhoneNumber.setText(user.getPhoneNumber());
                txtEmail.setText(user.getEmail());

                List<Branch> branches = null;
                Branch branchName = null;
                try {
                    branches = p.getBranchs("");
                    branchName = p.getBranchByName(user.getBranchID());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (branchName != null) {
                    this.cbBranch.setItems(FXCollections.observableList(branches));
                    for (Branch branch : branches) {
                        if (branch.getId().equals(branchName.getId())) {
                            this.cbBranch.getSelectionModel().select(branch);
                            break;
                        }
                    }
                } else {
                    this.cbBranch.setItems(null);
                }

                String role = user.getRole().toLowerCase();
                List<String> roles;
                if ("admin".equals(role)) {
                    roles = Arrays.asList("Admin");
                    txtEmail.setDisable(true);
                    txtAddress.setDisable(true);
                    cbSex.setDisable(true);
                    txtPhoneNumber.setDisable(true);
                    dpDateOfBirth.setDisable(true);
                    cbRole.setDisable(true);
                    cbBranch.setDisable(true);
                } else {
                    roles = Arrays.asList("Quản lý", "Nhân viên");
                    txtEmail.setDisable(false);
                    txtAddress.setDisable(false);
                    cbSex.setDisable(false);
                    txtPhoneNumber.setDisable(false);
                    dpDateOfBirth.setDisable(false);
                    cbRole.setDisable(false);
                    cbBranch.setDisable(false);
                }
                this.cbRole.setItems(FXCollections.observableList(roles));
                cbRole.getSelectionModel().select(user.getRole());

                if (user.getDateOfBirth() != null) {
                    dpDateOfBirth.setValue(user.getDateOfBirth().toLocalDate());
                } else {
                    dpDateOfBirth.setValue(null);
                }
                if(LoginController.userLogin.getRole().equals("quản lý")){
                    this.cbBranch.setDisable(true);
                }
                cbSex.getSelectionModel().select(user.getSex());
                txtUserName.setText(user.getUsername());

                btnAdd.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setOnAction(event -> {
                    if (cbRole.getSelectionModel().getSelectedItem().toString().toLowerCase().equals("admin")) {
                        // Nếu người dùng là admin thì chỉ cần nhập thông tin tài khoản và mật khẩu
                        if (txtUserName.getText().isEmpty()) {
                            MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
                        } else {
                            String password = pwfPassWord.getText();
                            String confirmPassword = pwfConfirmPassWord.getText();

                            if ((password != null && password.equals(confirmPassword)) || (password == null && confirmPassword == null)) {
                                // Mật khẩu trùng khớp, tiến hành mã hóa
                                if (password != null) {
                                    String hashedPassword = hash.hashPassword(password);
                                    user.setPassword(hashedPassword);
                                }

                                user.setName(txtName.getText());

                                user.setUsername(txtUserName.getText());

                                try {
                                    if (u.updateUser(user)) {
                                        MessageBox.getBox("Thông báo", "Chỉnh sửa người dùng thành công", Alert.AlertType.INFORMATION).show();
                                        loadTableData(null);
                                        resetUI(LoginController.userLogin);
                                    }
                                } catch (SQLException ex) {
                                    MessageBox.getBox("Thông báo", "Chỉnh sửa người dùng thất bại", Alert.AlertType.ERROR).show();
                                    Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                MessageBox.getBox("Thông báo", "Mật khẩu không khớp", Alert.AlertType.ERROR).show();
                            }
                        }
                    } else {
                        // Nếu người dùng không phải là admin thì cần nhập đầy đủ thông tin
                        String password = pwfPassWord.getText();
                        String confirmPassword = pwfConfirmPassWord.getText();

                        if ((password != null && password.equals(confirmPassword)) || (password == null && confirmPassword == null)) {
                            // Mật khẩu trùng khớp, tiến hành mã hóa
                            if (password != null) {
                                String hashedPassword = hash.hashPassword(password);
                                user.setPassword(hashedPassword);
                            }

                            user.setName(txtName.getText());
                            user.setAddress(txtAddress.getText());
                            user.setPhoneNumber(txtPhoneNumber.getText());
                            user.setEmail(txtEmail.getText());
                            user.setUsername(txtUserName.getText());
                            user.setRole(cbRole.getSelectionModel().getSelectedItem().toString());

                            Branch selectedBranch = (Branch) cbBranch.getValue();
                            if (selectedBranch != null) {
                                String categoryId = selectedBranch.getId();
                                user.setBranchID(categoryId);
                            }
                            user.setSex(cbSex.getSelectionModel().getSelectedItem().toString());
                            user.setDateOfBirth(java.sql.Date.valueOf(dpDateOfBirth.getValue()));

                            try {
                                if (u.updateUser(user)) {
                                    MessageBox.getBox("Thông báo", "Chỉnh sửa người dùng thành công", Alert.AlertType.INFORMATION).show();
                                    loadTableData(null);
                                    resetUI(LoginController.userLogin);
                                }
                            } catch (SQLException ex) {
                                MessageBox.getBox("Thông báo", "Chỉnh sửa người dùng thất bại", Alert.AlertType.ERROR).show();
                                Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            MessageBox.getBox("Thông báo", "Mật khẩu không khớp", Alert.AlertType.ERROR).show();
                        }

                    }
                });
            });

            TableCell<User, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        User user = getTableView().getItems().get(getIndex());
                        setGraphic(user.getId() != null && !user.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return c;
        });
        this.tbUsers.getColumns().addAll(colName, colEmail, colBranch, colRole, colDel, colUpdate);
    }

    private void loadTableData(String kw) throws SQLException {
        List<User> users = u.getUsers(kw,LoginController.userLogin.getBranchID());
        this.tbUsers.getItems().clear();
        for (User user : users) {
            // Kiểm tra nếu giá trị của cột "branch" là trống thì gán giá trị mặc định là ""
            if (user.getBranchID() == null || user.getBranchID().isEmpty()) {
                user.setBranchID("");
            } else {
                Branch branch = p.getBranchByID(user.getBranchID());
                if (branch != null) {
                    user.setBranchID(branch.getName());
                } else {
                    user.setBranchID("");
                }
            }
        }
        this.tbUsers.setItems(FXCollections.observableList(users));
    }

    public void resetUI(User u) throws SQLException {
        try {
            List<Branch> branches = p.getBranchs("");
            this.cbBranch.setItems(FXCollections.observableList(branches));
        }catch(Exception ex) {
        }
        if(u.getRole().toLowerCase().equals("admin")){

            List<String> roles = Arrays.asList("Nhân viên");
            this.cbRole.setItems(FXCollections.observableList(roles));
            cbBranch.setDisable(false);
        }

        List<String> sex = Arrays.asList("Nam", "Nữ", "Khác");
        this.cbSex.setItems(FXCollections.observableList(sex));


        txtEmail.setDisable(false);
        txtAddress.setDisable(false);
        cbSex.setDisable(false);
        txtPhoneNumber.setDisable(false);
        dpDateOfBirth.setDisable(false);
        cbRole.setDisable(false);

        this.txtName.setText(null);
        this.txtAddress.setText(null);
        this.txtPhoneNumber.setText(null);
        this.txtEmail.setText(null);
        this.txtSearch.setText(null);
        this.txtUserName.setText(null);
        this.pwfPassWord.setText(null);
        this.pwfConfirmPassWord.setText(null);
        this.cbSex.getSelectionModel().clearSelection();
        this.cbRole.getSelectionModel().clearSelection();
        this.cbBranch.getSelectionModel().clearSelection();
        this.dpDateOfBirth.setValue(null);
        btnSave.setVisible(false);
        btnAdd.setVisible(true);
    }

    public void CancelUserHandler(ActionEvent event) throws SQLException {
        loadTableData(null);
        resetUI(LoginController.userLogin);
    }

    public void addUserHandler(ActionEvent event) throws SQLException {
        if (txtName.getText() == null || txtName.getText().isEmpty() || txtAddress.getText() == null || txtAddress.getText().isEmpty() || txtPhoneNumber.getText() == null || txtPhoneNumber.getText().isEmpty() || txtEmail.getText() == null || txtEmail.getText().isEmpty() || txtAddress.getText() == null || txtAddress.getText().isEmpty() || txtUserName.getText() == null || txtUserName.getText().isEmpty() || pwfPassWord.getText() == null || pwfPassWord.getText().isEmpty() || pwfConfirmPassWord.getText() == null || pwfConfirmPassWord.getText().isEmpty() || dpDateOfBirth.getValue() == null || cbSex.getSelectionModel().getSelectedItem() == null || cbRole.getSelectionModel().getSelectedItem() == null || cbBranch.getSelectionModel().getSelectedItem() == null) {
            MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
        } else {
            String password = pwfPassWord.getText();
            String confirmPassword = pwfConfirmPassWord.getText();

            if ((password != null && password.equals(confirmPassword)) || (password == null && confirmPassword == null)) {
                // Mật khẩu trùng khớp, tiến hành mã hóa
                if (password != null) {
                    String hashedPassword = hash.hashPassword(password);
                    Branch selectedBranch = (Branch) cbBranch.getValue();
                    String categoryId = selectedBranch.getId();
                    User user = new User(
                            txtName.getText(),
                            java.sql.Date.valueOf(dpDateOfBirth.getValue()),
                            cbSex.getSelectionModel().getSelectedItem().toString(),
                            txtPhoneNumber.getText(),
                            txtAddress.getText(),
                            cbRole.getSelectionModel().getSelectedItem().toString(),
                            txtEmail.getText(),
                            txtUserName.getText(),
                            hashedPassword,
                            categoryId
                    );
                    try {
                        if (u.addUser(user)) {
                            MessageBox.getBox("Thông báo", "Thêm người dùng mới thành công", Alert.AlertType.INFORMATION).show();
                            loadTableData(null);
                            resetUI(LoginController.userLogin);
                        }
                    } catch (SQLException ex) {
                        MessageBox.getBox("Thông báo", "Thêm người dùng mới thất bại", Alert.AlertType.ERROR).show();
                        Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                MessageBox.getBox("Thông báo", "Mật khẩu không khớp", Alert.AlertType.ERROR).show();
            }

        }
    }
}
