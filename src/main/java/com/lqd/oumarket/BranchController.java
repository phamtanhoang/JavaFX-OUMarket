/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
*/
package com.lqd.oumarket;


import com.lqd.pojo.Branch;
import com.lqd.services.BranchService;
import com.lqd.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class BranchController implements Initializable {
   static BranchService p = new BranchService();
   /**
    * Initializes the controller class.
    */
   @FXML
   TableView<Branch> tbBranchs;

    @FXML
    private BorderPane bp;
   @FXML
   private TextField txtName;
   @FXML
   private TextField txtAdress;
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
       TableColumn colName = new TableColumn("Tên chi nhánh");
       colName.setCellValueFactory(new PropertyValueFactory("name"));
       colName.setPrefWidth(400);


       TableColumn colAdress = new TableColumn("Địa chỉ");
       colAdress.setCellValueFactory(new PropertyValueFactory("adress"));
       colAdress.setPrefWidth(350);
      
       TableColumn colDel = new TableColumn();
       colDel.setCellFactory(r -> {
           Button btn = new Button("Xóa");


           btn.setOnAction(evt -> {
               Alert a = MessageBox.getBox("Thông báo",
                       "Bạn muốn xóa chi nhánh này không?",
                       Alert.AlertType.CONFIRMATION);
               a.showAndWait().ifPresent(res -> {
                   if (res == ButtonType.OK) {
                       Button b = (Button) evt.getSource();
                       TableCell cell = (TableCell) b.getParent();
                       Branch branch = (Branch) cell.getTableRow().getItem();
                       try {
                           boolean btrue=p.deleteBranch(branch.getId());
                           if (btrue) {
                               MessageBox.getBox("Thông báo", "Xóa chi nhánh thành công", Alert.AlertType.INFORMATION).show();
                               this.loadTableData(null);
                           } else {
                               MessageBox.getBox("Thông báo", "Xóa chi nhánh thất bại", Alert.AlertType.WARNING).show();
                           }


                       } catch (SQLException ex) {
                           MessageBox.getBox("Thông báo", ex.getMessage(), Alert.AlertType.WARNING).show();
                           Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
                       }


                   }
               });


           });
           btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
           TableCell<Branch, Void> c = new TableCell<>() {
               protected void updateItem(Void item, boolean empty) {
                   super.updateItem(item, empty);
                   if (!empty) {
                       Branch branch = getTableView().getItems().get(getIndex());
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
        colDel.setPrefWidth(80);

       TableColumn colUpdate = new TableColumn();
       colUpdate.setCellFactory(r -> {
           Button btn = new Button("Sửa");
          
           btn.setOnAction(evt -> {
               TableRow<Branch> row = (TableRow<Branch>) ((Button) evt.getSource()).getParent().getParent();
               int rowIndex = row.getIndex();
               Branch branch = tbBranchs.getItems().get(rowIndex);
               txtName.setText(branch.getName());
               txtAdress.setText(branch.getAdress());
              
               btnAdd.setVisible(false);
               btnSave.setVisible(true);
               btnSave.setOnAction(event -> {
                   if(txtName.getText().isEmpty() || txtAdress.getText().isEmpty()){
                       MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
                   }
                   else{
                       branch.setName(txtName.getText());
                       branch.setAdress(txtAdress.getText());
                       try {
                           if (p.updateBranch(branch)) {
                               MessageBox.getBox("Thông báo", "Chỉnh sửa chi nhánh thành công", Alert.AlertType.INFORMATION).show();
                               loadTableData(null);
                               resetUI();
                           }
                       } catch (SQLException ex) {
                           MessageBox.getBox("Thông báo", "Chỉnh sửa chi nhánh thất bại", Alert.AlertType.ERROR).show();
                           Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
               });
           });

           btn.setStyle("-fx-background-color:  #4e73df; -fx-text-fill: white;");
           TableCell<Branch, Void> c = new TableCell<>() {
               protected void updateItem(Void item, boolean empty) {
                   super.updateItem(item, empty);
                   if (!empty) {
                       Branch branch = getTableView().getItems().get(getIndex());
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
       colUpdate.setPrefWidth(80);
       this.tbBranchs.getColumns().addAll(colName, colAdress, colUpdate, colDel);
   }


   private void loadTableData(String kw) throws SQLException {
       List<Branch> branch = p.getBranchs(kw);
       this.tbBranchs.getItems().clear();
       this.tbBranchs.setItems(FXCollections.observableList(branch));
   }
   private void resetUI(){
       MainUIController mu = new MainUIController();
       mu.loadFxml("BranchUI", bp);
   }


   public void addBranchHandler(ActionEvent event) throws SQLException {
       if (txtName.getText() == null || txtName.getText().isEmpty() || txtAdress.getText() == null || txtAdress.getText().isEmpty()) {
           MessageBox.getBox("Thông báo", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.WARNING).show();
       } else {
           Branch branch = new Branch(
                   txtName.getText(),
                   txtAdress.getText()
           );
           try {
               if (p.addBranch(branch)) {
                   MessageBox.getBox("Thông báo", "Thêm chi nhánh mới thành công", Alert.AlertType.INFORMATION).show();
                   loadTableData(null);
                   resetUI();
               }
           } catch (SQLException ex) {
               MessageBox.getBox("Thông báo", "Thêm chi nhánh mới thất bại", Alert.AlertType.ERROR).show();
               Logger.getLogger(BranchController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   public void CancelBranchHandler(ActionEvent event) throws SQLException {
       resetUI();
   }
  
}



