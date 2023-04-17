/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lqd.oumarket;

import com.lqd.pojo.Branch;
import com.lqd.pojo.User;
import com.lqd.services.BranchService;
import com.lqd.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lqd.utils.MessageBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gol
 */
public class MainUIController implements Initializable {

    @FXML
    private VBox vbxUI;
    @FXML
    private Button btnSale;
    @FXML
    private Button btnUser;
    @FXML
    private Button btnProd;
    @FXML
    private Button btnBra;
    @FXML
    private Button btnCus;
    @FXML
    private Button btnRe;
    @FXML
    private Button btnCate;
    @FXML
    private Button btnPro;
    @FXML
    private  Button btnLogout;
    @FXML
    private Label hi;

    private User u = LoginController.userLogin;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnCate.setOnAction(evt -> {
            loadFxml("CategoryUI", vbxUI);
        });
        btnProd.setOnAction(evt -> {
            loadFxml("ProductUI", vbxUI);
        });
        btnSale.setOnAction(evt -> {
            loadFxml("SaleUI", vbxUI);
        });
        btnUser.setOnAction(evt -> {
            loadFxml("UserUI", vbxUI);
        });
        btnBra.setOnAction(evt -> {
            loadFxml("BranchUI", vbxUI);
        });
        btnCus.setOnAction(evt -> {
            loadFxml("CustomerUI", vbxUI);
        });
        btnPro.setOnAction(evt -> {
            loadFxml("PromotionUI", vbxUI);
        });
        btnRe.setOnAction(evt -> {
            loadFxml("RecieptUI", vbxUI);
        });
        hi.setText("Xin chào "+ u.getName() + "!!!");

        btnLogout.setOnAction(evt -> {
            Alert a = MessageBox.getBox("Thông báo",
                    "Bạn muốn đăng xuất không?",
                    Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    // Đóng cửa sổ hiện tại
                    Stage currentStage = (Stage) btnLogout.getScene().getWindow();
                    currentStage.close();
                    LoginController.userLogin = null;
                    // Mở lại giao diện đăng nhập
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    public void loadFxml(String fxmlFile, VBox UI) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile + ".fxml"));
            Node productNode = loader.load();
            UI.getChildren().clear();
            UI.getChildren().add(productNode);
        } catch (IOException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void loadFxml(String fxmlFile, BorderPane UI) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile + ".fxml"));
            Node productNode = loader.load();
            UI.getChildren().clear();
            UI.getChildren().add(productNode);
        } catch (IOException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadMainUI(User u) {
        if (u.getRole().toLowerCase().equals("admin")) {
            btnSale.setVisible(false);
            btnSale.setManaged(false);
        }
        else {
            btnBra.setVisible(false);
            btnBra.setManaged(false);
            btnPro.setVisible(false);
            btnPro.setManaged(false);
            btnProd.setVisible(false);
            btnProd.setManaged(false);
            btnUser.setVisible(false);
            btnUser.setManaged(false);
            btnCate.setVisible(false);
            btnCate.setManaged(false);
            btnRe.setVisible(false);
            btnRe.setManaged(false);
        }
    }
}



