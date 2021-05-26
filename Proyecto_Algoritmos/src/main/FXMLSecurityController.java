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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.FileTXT;

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
    @FXML
    private Button btn_newStudent;
    @FXML
    private Pane P_NewSudent;
    @FXML
    private Button btn_acept_newStudent;
    @FXML
    private Button btn_cancel_newStudent;
    @FXML
    private TextField tf_carnet_newStudent;
    @FXML
    private PasswordField tf_password_newSudent;
    @FXML
    private PasswordField tf_passwordConfirm_newStudent;
    @FXML
    private TextField tf_Password_Student;

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
        if(!util.Utility.getEstudiantes().isEmpty()){
            P_selection.setVisible(false);
            P_student.setVisible(true);
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("no hay estudiantes registrados\npara usar esta opcion");
            a.showAndWait();

        }
    }

    @FXML
    private void btn_acept_student(ActionEvent event) {
        try {
            if(!tf_canet.getText().isEmpty() && !tf_Password_Student.getText().isEmpty()){ // este lo cambie para que no diera error
                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                    Security s = (Security)util.Utility.getUsers().getNode(i).data;
                    if(tf_canet.getText().equals(s.getUser())){
                        if(!s.getPassword().equals("-")){
                            if(tf_canet.getText().equals(s.getUser()) && tf_Password_Student.getText().equals(s.getPassword())){
                                util.Utility.setSecurity("student");
                                Parent root = null;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipal.fxml"));
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) btn_acept.getScene().getWindow();
                                stage.setScene(scene);
                            }else{
                                Alert a = new Alert(Alert.AlertType.INFORMATION);
                                a.setHeaderText("La contrseña o Carnet es incorrecto");
                                a.showAndWait();  
                            }
                        }else{
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("El Carnet ingresado esta registrado, pero sin contraseña");
                            a.setContentText("Cancele e ingrese ¨Primero Ingreso¨ y registre su contraseña");
                            a.showAndWait();  
                        }
                    }
                }
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No debe dejar espacios en blanco");
                a.showAndWait(); 
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btn_cancel_student(ActionEvent event) {
        tf_Password_Student.setText("");
        tf_canet.setText("");
        P_selection.setVisible(true);
        P_student.setVisible(false);
    }

    @FXML
    private void btn_acept_admin(ActionEvent event) {
        try {
            if(!tf_password.getText().isEmpty() || !tf_user.getText().isEmpty()){ // esto lo cambie por is empty , luego cambiar por blank
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
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No deje espacios en blanco");
                a.showAndWait();
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btn_cancel_admin(ActionEvent event) {
        tf_user.setText("");
        tf_password.setText("");
        P_selection.setVisible(true);
        P_admin.setVisible(false);
    }

    @FXML
    private void btn_newStudent(ActionEvent event) {
        if(!util.Utility.getEstudiantes().isEmpty()){
            P_selection.setVisible(false);
            P_NewSudent.setVisible(true);
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("no hay estudiantes registrados\npara usar esta opcion");
            a.showAndWait();

        }
    }

    @FXML
    private void btn_acept_newStudent(ActionEvent event) {
        if(!tf_carnet_newStudent.getText().isEmpty() && !tf_passwordConfirm_newStudent.getText().isEmpty() && 
                !tf_password_newSudent.getText().isEmpty()){// esto lo cambie
            try {
                boolean register=true;
                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                    Security s = (Security)util.Utility.getUsers().getNode(i).data;
                    if(tf_carnet_newStudent.getText().equals(s.getUser()) && s.getPassword().equals("-")){
                        register=false;
                    }
                }
                FileTXT file = new FileTXT();
                if(register==false){
                    if(tf_password_newSudent.getText().equals(tf_passwordConfirm_newStudent.getText())){
                        for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                            Security s = (Security)util.Utility.getUsers().getNode(i).data;
                            if(tf_carnet_newStudent.getText().equals(s.getUser())){
                                file.modifyFile("Users.txt", s.toString(), new Security(tf_carnet_newStudent.getText(),tf_password_newSudent.getText()).toString() ) ;
                                s.setPassword(tf_password_newSudent.getText());
                                util.Utility.getUsers().getNode(i).data = s;
                                
                                P_NewSudent.setVisible(false);
                                P_selection.setVisible(true);
                                Alert a = new Alert(Alert.AlertType.INFORMATION);
                                a.setHeaderText("Se a guardado con exito la contraseña");
                                a.setContentText("ahora puede ingresar a hacer consultas");
                                a.showAndWait();
                            }
                        }   
                    }else{
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("La contrseña y su confirmación no coinciden");
                        a.showAndWait();
                    }
                }else{
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("El estudiante indicado no se a podido encontrar\no ya tiene una contraseña registrada");
                    a.showAndWait();
                }  
            } catch (ListException ex) {
                Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar espacios en blanco");
            a.showAndWait();
        }
    }

    @FXML
    private void btn_cancel_newStudent(ActionEvent event) {
        tf_carnet_newStudent.setText("");
        tf_password_newSudent.setText("");
        tf_passwordConfirm_newStudent.setText("");
        P_selection.setVisible(true);
        P_NewSudent.setVisible(false);
    }
    
}
