/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.CircularLinkedList;
import Lists.ListException;
import com.sun.javafx.sg.prism.NGCanvas;
import domain.Security;
import domain.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLSecurityController implements Initializable {

    @FXML
    private Pane P_selection;
    @FXML
    private Button btn_admin;
    @FXML
    private Button btn_student;
    @FXML
    private Pane P_student;
    @FXML
    private Button btn_acept_student;
    @FXML
    private Button btn_cancel_student;
    @FXML
    private TextField tf_canet;
    @FXML
    private Pane P_admin;
    @FXML
    private TextField tf_user;
    @FXML
    private Button btn_acept;
    @FXML
    private Button btn_cancel_admin;
    @FXML
    private PasswordField tf_password;
    @FXML
    private BorderPane bp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void btn_admin(ActionEvent event) {
        P_selection.setVisible(false);
        P_admin.setVisible(true);
    }

    @FXML
    private void btn_student(ActionEvent event) {
        P_selection.setVisible(false);
        P_student.setVisible(true);
    }

    @FXML
    private void btn_acept_student(ActionEvent event) {
        try {
            for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                Student s = (Student)util.Utility.getEstudiantes().getNode(i).getData();
                if(tf_canet.getText().equals(s.getStudentID())){

                }
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btn_cancel_student(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void btn_acept_admin(ActionEvent event) {
        try {
            util.Utility.getUsers().add(new Security("admin", "123"));
            for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                
                Security s2 = (Security)util.Utility.getUsers().getNode(i).getData();
                Security s1 = new Security(tf_user.getText(), tf_password.getText());
               if(s1.equals(s2)){
                    
                    util.Utility.setSecurity("admin");
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipal.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) btn_acept.getScene().getWindow();
                    stage.setScene(scene);
                }    
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btn_cancel_admin(ActionEvent event) {
        System.exit(0);
    }
    
}
