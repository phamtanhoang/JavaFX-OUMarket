/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;

import com.lqd.pojo.*;
import com.lqd.services.*;
import com.lqd.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.Date;

/**
 * FXML Controller class
 *
 * @author Gol
 */
public class SaleController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private Button btnSubmit;
    @FXML
    private TableView tbCustomers;
    @FXML
    private TableView tbProducts;
    @FXML
    private TextField txtCusSearch;
    @FXML
    private TextField txtProdSearch;
    @FXML
    private TableView tbReceipt;
    @FXML
    private TextField txtReceive;
    @FXML
    private Text txtCus;
    @FXML
    private Text txtTemp;
    @FXML
    private Text txtPromo;
    @FXML
    private Text txtBirthDay;
    @FXML
    private Text txtChanges;
    @FXML
    private Text txtTotal;
    private float temp;
    private float promo;
    private float total;
    private float birthday = 0;
    private List<ProductPromotion> pplist;
    static PromotionService proService = new PromotionService();
    static ProductService prodService = new ProductService();
    static CustomerService cusService = new CustomerService();
    static ReceiptService repService = new ReceiptService();
    static CategoryService cateService = new CategoryService();
    static ReceiptDetailService detailService = new ReceiptDetailService();
    private Receipt receipt = new Receipt();
    private User u =LoginController.userLogin;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            receipt.setStaffID(u.getId());
            loadProductTableColumns();
            loadTableProductData(null);
            loadReceiptColumn();
            loadCustomerColumns();
            loadTableCustomerData(null);
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtCusSearch.textProperty().addListener(e -> {
            this.loadTableCustomerData(this.txtCusSearch.getText());
        });
        this.txtProdSearch.textProperty().addListener(e -> {
            try {
                this.loadTableProductData(this.txtProdSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.txtReceive.textProperty().addListener(e -> {
            if(txtReceive==null||txtReceive.getText().isEmpty()){
                txtChanges.setText("0 VNĐ");
                return;
            }
            try {
                Float x = Float.parseFloat(txtReceive.getText()) - total;
                if (x >=0)
                    txtChanges.setText(String.format("%,.0f VNĐ", Float.parseFloat(txtReceive.getText()) - total));
                else
                    txtChanges.setText("Số tiền chưa đủ!!!");
            }catch (Exception exception){
                txtChanges.setText("Số tiền không hợp lệ!!!");
            }
        });

    }
    public void loadCustomerColumns() {
        TableColumn colName = new TableColumn("Tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(100);

        TableColumn colPhone = new TableColumn("SĐT");
        colPhone.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

        TableColumn colBirthDay = new TableColumn("Ngày sinh");
        colBirthDay.setCellValueFactory(new PropertyValueFactory("dateOfBirth"));

        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colEmail.setPrefWidth(60);

        TableColumn colAdd = new TableColumn();
        colAdd.setCellFactory(r -> {
            Button btn = new Button("Chọn");

            btn.setOnAction(evt -> {
                TableCell cell = (TableCell) btn.getParent();
                Customer customer = (Customer) cell.getTableRow().getItem();
                receipt.setCustomerID(customer.getId());

                txtCus.setText(customer.getName());
                java.time.LocalDate localDate = customer.getDateOfBirth().toLocalDate();
                java.time.LocalDate now = java.time.LocalDate.now();
                if (localDate.getMonthValue() == now.getMonthValue() && localDate.getDayOfMonth() == now.getDayOfMonth()) {
                    txtBirthDay.setText("10%");
                    birthday=(float) 0.1;
                } else {
                    txtBirthDay.setText("0%");
                    birthday = 0;
                }
                setReceipt(pplist);

                if(txtReceive==null||txtReceive.getText().isEmpty()){
                    txtChanges.setText("0 VNĐ");
                    return;
                }
                try {
                    Float x = Float.parseFloat(txtReceive.getText()) - total;
                    if (x >=0)
                        txtChanges.setText(String.format("%,.0f VNĐ", Float.parseFloat(txtReceive.getText()) - total));
                    else
                        txtChanges.setText("Số tiền chưa đủ!!!");
                }catch (Exception exception){
                    txtChanges.setText("Số tiền không hợp lệ!!!");
                }
            });

            btn.setStyle("-fx-background-color:  #4e73df; -fx-text-fill: white;");
            TableCell<Customer, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Customer cus = getTableView().getItems().get(getIndex());
                        setGraphic(cus.getId() != null && !cus.getId().isEmpty() ? btn : null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });

        this.tbCustomers.getColumns().addAll(colName, colPhone, colEmail, colBirthDay, colAdd);
    }

    public void cancelCustomerHandler(){
        receipt.setCustomerID(null);
        txtCus.setText("");
        txtBirthDay.setText("0%");
        receipt.setCustomerID(null);
        birthday=0;
        setReceipt(pplist);
        try {
            Float x = Float.parseFloat(txtReceive.getText()) - total;
            if (x >=0)
                txtChanges.setText(String.format("%,.0f VNĐ", Float.parseFloat(txtReceive.getText()) - total));
            else
                txtChanges.setText("Số tiền chưa đủ!!!");
        }catch (Exception exception){
            txtChanges.setText("Số tiền không hợp lệ!!!");
        }
    }
    public void loadProductTableColumns() {
        TableColumn colName = new TableColumn("Tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(120);

        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));

        TableColumn colPrice = new TableColumn("Giá");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colPrice.setCellFactory(column -> {
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

        TableColumn colOrigin = new TableColumn("Xuất Xứ");
        colOrigin.setCellValueFactory(new PropertyValueFactory("origin"));

        TableColumn colCate = new TableColumn("Loại SP");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryID"));
        colCate.setPrefWidth(90);
        TableColumn colAdd = new TableColumn();
        colAdd.setCellFactory(r -> {
            Button btn = new Button("Thêm");

            btn.setOnAction(evt -> {
                TableCell cell = (TableCell) btn.getParent();
                Product prod = (Product) cell.getTableRow().getItem();
                if (tbReceipt.getItems().isEmpty()) {
                    try {
                        ProductPromotion pp = new ProductPromotion(prod.getId(), prod.getPrice(), prod.getUnit(), (proService.getNewPrice(prod.getId()) == null ? prod.getPrice() : proService.getNewPrice(prod.getId())), prod.getName());
                        pplist = new ArrayList<>();
                        pplist.add(pp);
                        setReceipt(pplist);
                        this.tbReceipt.setItems(FXCollections.observableList(pplist));
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    List<ProductPromotion> pplist = tbReceipt.getItems();
                    for (ProductPromotion pp : pplist) {
                        if (pp.getId().equals(prod.getId())) {
                            return;
                        }
                    }
                    try {

                        ProductPromotion pp = new ProductPromotion(prod.getId(), prod.getPrice(), prod.getUnit(), (proService.getNewPrice(prod.getId()) == null ? prod.getPrice() : proService.getNewPrice(prod.getId())), prod.getName());
                        tbReceipt.getItems().add(pp);
                        tbReceipt.refresh();

                        setReceipt(pplist);
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            btn.setStyle("-fx-background-color:  #4e73df; -fx-text-fill: white;");
            TableCell<Product, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Product prod = getTableView().getItems().get(getIndex());
                        setGraphic(prod.getId() != null && !prod.getId().isEmpty() ? btn: null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;

        });

        this.tbProducts.getColumns().addAll(colName, colUnit, colPrice, colOrigin, colCate, colAdd);
    }

    public void loadTableProductData(String kw) throws SQLException {
        try {
            List<Product> prods = prodService.getProducts(kw);
            this.tbProducts.getItems().clear();
            for (Product prod : prods) {
                String categoryID = prod.getCategoryID();
                Category cate = cateService.getCategoryByID(categoryID);
                prod.setCategoryID(cate != null ? cate.getName() : "");
            }
            this.tbProducts.setItems(FXCollections.observableList(prods));
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTableCustomerData(String kw) {

        try {
            List<Customer> cus = cusService.getCustomersByPhoneNumber(kw);
            this.tbCustomers.getItems().clear();
            this.tbCustomers.setItems(FXCollections.observableList(cus));

        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadReceiptColumn() {
        TableColumn colName = new TableColumn("Tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));


        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));
        colUnit.setPrefWidth(60);
        TableColumn colPrice = new TableColumn("Giá");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colPrice.setCellFactory(column -> {
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

        TableColumn colNewPrice = new TableColumn("Khuyến mãi");
        colNewPrice.setCellValueFactory(new PropertyValueFactory("newPrice"));
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

        TableColumn colQuantity = new TableColumn("SL");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        colQuantity.setPrefWidth(50);
        TableColumn colQuantityTxt = new TableColumn("Nhập SL ");

        colQuantityTxt.setCellFactory(r -> {
            TextField txtQuantity = new TextField();
            txtQuantity.setText("");
            txtQuantity.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    // TextField đang được focus
                    TableCell cell = (TableCell) txtQuantity.getParent();
                    ProductPromotion prod = (ProductPromotion) cell.getTableRow().getItem();
                    if (cell != null) {
                        int rowIndex = cell.getIndex();

                        if (!txtQuantity.getText().isEmpty()) {
                            try {
                                if (prod.getUnit().equalsIgnoreCase("kg")) {
                                    prod.setQuantity(Float.parseFloat(txtQuantity.getText()));
                                } else {
                                    prod.setQuantity(Integer.parseInt(txtQuantity.getText()));
                                }
                                Label dummyLabel = new Label();
                                Pane root = (Pane) cell.getScene().getRoot();
                                root.getChildren().add(dummyLabel);
                                dummyLabel.requestFocus();
                                tbReceipt.refresh();
                            } catch (NumberFormatException ex) {
                                MessageBox.getBox("Thông báo", "Sai định lượng sản phẩm!!", Alert.AlertType.ERROR).show();
                                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                    setReceipt(pplist);

                }
            });

            TableCell<ProductPromotion, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        ProductPromotion prod = getTableView().getItems().get(getIndex());
                        setGraphic(prod.getId() != null && !prod.getId().isEmpty() ? txtQuantity: null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return c;
        });

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                TableCell cell = (TableCell) btn.getParent();
                ProductPromotion prod = (ProductPromotion) cell.getTableRow().getItem();

                deleteReceiptItem(prod);
                List<ProductPromotion> pplist = tbReceipt.getItems();
                pplist.remove(prod);
                tbReceipt.refresh();

            });
            btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            TableCell<ProductPromotion, Void> c = new TableCell<>() {
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        ProductPromotion prod = getTableView().getItems().get(getIndex());
                        setGraphic(prod.getId() != null && !prod.getId().isEmpty() ? btn: null);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            c.setAlignment(Pos.CENTER);
            btn.setMaxWidth(Double.MAX_VALUE);
            return c;
        });
        colDel.setPrefWidth(50);
        this.tbReceipt.getColumns().addAll(colName, colUnit, colPrice, colNewPrice, colQuantity, colQuantityTxt, colDel);
    }

    public void setReceipt(List<ProductPromotion> ppList) {
        temp = 0;
        promo = 0;
        total = 0;

        try {
            for (int i = 0; i < ppList.size(); i++) {
                temp += ppList.get(i).getPrice() * ppList.get(i).getQuantity();
                promo += (ppList.get(i).getPrice() - ppList.get(i).getNewPrice()) * ppList.get(i).getQuantity();
                total = (temp - promo) - ((temp - promo) * birthday);
            }
        }catch(Exception exception){}


        txtTotal.setText(String.format("%,.0f VNĐ", total));
        txtTemp.setText(String.format("%,.0f VNĐ", temp));
        txtPromo.setText(String.format("%,.0f VNĐ", promo));
    }



    public void deleteReceiptItem(ProductPromotion pp) {
        temp -= pp.getPrice() * pp.getQuantity();
        txtTemp.setText(String.valueOf(temp));
        promo -= (pp.getPrice() - pp.getNewPrice()) * pp.getQuantity();
        txtPromo.setText(String.valueOf(promo));
        total -= pp.getNewPrice();
        txtTotal.setText(String.valueOf(total));
        if (txtReceive.getText().isEmpty()) {
            txtChanges.setText("Chưa nhập số tiền");
        } else {
            txtChanges.setText(String.valueOf(Float.parseFloat(txtReceive.getText()) - total));
        }

    }
    
    public void submitReceiptHandler() throws SQLException{
        try{

            if(pplist==null)
            {
                  MessageBox.getBox("Thông báo", "Chưa có sản phẩm!!!", Alert.AlertType.INFORMATION).show();
                  return;
            }
            if(Float.parseFloat(txtReceive.getText()) - total<=0){
                MessageBox.getBox("Thất bại", "Số tiền thừa từ khách chưa đủ!!", Alert.AlertType.ERROR).show();
                return;
            }
            try {
                receipt.setReceipt(birthday, temp, promo, total);
                if (repService.addReceipt(receipt)) {
                    for (ProductPromotion pp : pplist) {
                        detailService.addReceiptDetail(pp, receipt.getId());
                    }
                    MainUIController mu=new MainUIController();
                    mu.loadFxml("SaleUI", bp);
                    MessageBox.getBox("Thành công", "Giao dịch thành công", Alert.AlertType.INFORMATION).show();
                }
            } catch (SQLException ex) {
                MessageBox.getBox("Thất bại", "Giao dịch thất bại", Alert.AlertType.ERROR).show();
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }}catch (Exception exception){
            MessageBox.getBox("Thất bại", "Vui lòng nhập tiền nhận từ khách!!", Alert.AlertType.ERROR).show();
        }
      
    }
}
