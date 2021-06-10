/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.CircularLinkedList;
import Lists.ListException;
import com.sun.javafx.sg.prism.NGCanvas;
import domain.Career;
import domain.Security;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.icepdf.ri.common.FileExtensionUtils;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLSecurityController implements Initializable {

    private Student stud;
    private String temporalPass;
    
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
    @FXML
    private Pane P_ChangePasswors;
    @FXML
    private Pane P_ChangePassword_1;
    @FXML
    private TextField P_ChangePassword_1_carnet;
    @FXML
    private Button btn_Enviar_ChangePassword;
    @FXML
    private Button btn_Cancel_P_ChangePassword_1;
    @FXML
    private Pane P_ChangePassword_2;
    @FXML
    private TextField P_ChangePassword_2_tfTemporalPass;
    @FXML
    private TextField P_ChangePassword_2_tfConformPass;
    @FXML
    private PasswordField P_ChangePanel_2_tfNewPass;
    @FXML
    private Button P_ChangePanel_2_btnSave;
    @FXML
    private Button P_ChangePanel_2_btnCancel;

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
        if(!util.Utility.getEstudiantes().isEmpty()){ // no permite consultas si no hay estudiantes
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
            if(!tf_canet.getText().isEmpty() && !tf_Password_Student.getText().isEmpty()){ // verificamos quen ohaya espacios en blanco
                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                    Security s = (Security)util.Utility.getUsers().getNode(i).data;
                    if(tf_canet.getText().equals(s.getUser())){ // verificamos que el usuario existe
                        if(!s.getPassword().equals("-")){ // revisams si tiene contraseña si no hay que ponele una
                            if(tf_canet.getText().equals(s.getUser()) && tf_Password_Student.getText().equals(s.getPassword())){ // revisamos que los datos de usuario son correctos
                                
                                for (int j = 1; j <= util.Utility.getEstudiantes().size(); j++) {
                                    Student st = (Student)util.Utility.getEstudiantes().getNode(j).data;
                                    if(st.getStudentID().equals(tf_canet.getText())){
                                        util.Utility.setIntro(st); //guardamos el estudiante con el que se esta ingresando
                                    }
                                }
                                Parent root = null;            // cargamos el XFMLVentanaPrincipal
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
        tf_Password_Student.setText(""); //se regresa al inicio
        tf_canet.setText("");
        P_selection.setVisible(true);
        P_student.setVisible(false);
    }

    @FXML
    private void btn_acept_admin(ActionEvent event) {
        try {
            if(!tf_password.getText().isEmpty() || !tf_user.getText().isEmpty()){ // revisamos que los epacios no esten vacios
                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {

                    Security s2 = (Security)util.Utility.getUsers().getNode(i).getData(); //revisamos si el usuario ingresado es correcto
                    Security s1 = new Security(tf_user.getText(), tf_password.getText());
                   if(s1.equals(s2)){
                        util.Utility.setIntro(null);  //ponemos null indicando que entramos como admin
                        Parent root = null;  // cargamos el FXMLVentanaPrincipal
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
                !tf_password_newSudent.getText().isEmpty()){// verificamos que los espacios no esten en blanco
            try {
                boolean register=true;
                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {  // revisamos que el estudiantes exista y que no tiene contraseña como usuario
                    Security s = (Security)util.Utility.getUsers().getNode(i).data;
                    if(tf_carnet_newStudent.getText().equals(s.getUser()) && s.getPassword().equals("-")){
                        register=false;
                    }
                }
                FileTXT file = new FileTXT();
                if(register==false){ // si no esta registrado
                    if(tf_password_newSudent.getText().equals(tf_passwordConfirm_newStudent.getText())){ //confirmamos que las contraseñas coinciden
                        for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                            Security s = (Security)util.Utility.getUsers().getNode(i).data;
                            if(tf_carnet_newStudent.getText().equals(s.getUser())){ //bscamos el usuario y le agregamos la contraseña
                                file.modifyFile("Users.txt", s, new Security(tf_carnet_newStudent.getText(),tf_password_newSudent.getText())) ;
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
//-----------------------------------------------Proceso cambio de contraseña
    @FXML
    private void btn_Enviar_ChangePassword_1(ActionEvent event) {
        if(!P_ChangePassword_1_carnet.getText().isEmpty()){
            try {
                boolean tf = false;
                for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {  // revisamos que el carnet exista
                    Student s = (Student)util.Utility.getEstudiantes().getNode(i).data;
                    if(s.getStudentID().equals(P_ChangePassword_1_carnet.getText())){
                        stud=s;
                        tf=true;
                    }
                }
                if(tf==true){
                    P_ChangePassword_1.setVisible(false);  // si existe pasamos al siguiente panel y enviamos la contraseña temporal
                    P_ChangePassword_2.setVisible(true);
                    temporalPass = util.Utility.randomPass();
                    sendEmail();
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Se envio su contraseña temporal");
                    a.setContentText("revise su correo");
                    a.showAndWait(); 
                    P_ChangePassword_1_carnet.setText("");
                }else{
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("El carnet ingresado no estaregistrado");
                    a.setContentText("Verifique que sea correcto");
                    a.showAndWait();  
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No deje el espacio en blanco");
            a.showAndWait();
        }
        
    }

    @FXML
    private void btn_Cancel_P_ChangePassword_1(ActionEvent event) {
        P_ChangePasswors.setVisible(false);
        P_selection.setVisible(true);
    }

    @FXML
    private void P_ChangePanel_2_btnSave(ActionEvent event) {
        if(!P_ChangePassword_2_tfTemporalPass.getText().isEmpty() && !P_ChangePanel_2_tfNewPass.getText().isEmpty() && 
                !P_ChangePassword_2_tfConformPass.getText().isEmpty()){ //revisamos qu los espacios no esten vacios
            
            if(P_ChangePassword_2_tfTemporalPass.getText().equals(temporalPass)){ // revisamos que la contraseña temporal coincide
                if(P_ChangePanel_2_tfNewPass.getText().equals(P_ChangePassword_2_tfConformPass.getText())){ // confirmamos que las constraseñas coinciden
                    try {
                        Security s = null;
                        Security s2 = new Security(stud.getStudentID(), P_ChangePanel_2_tfNewPass.getText());
                        
                        for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                            s = (Security)util.Utility.getUsers().getNode(i).data;
                            if(s.getUser().equals(stud.getStudentID())){ //actualizamos la lista de usuarios
                                util.Utility.getUsers().getNode(i).data = s2;
                            }
                        }
                        FileTXT txt = new FileTXT();             //actualizamos el txt y regresamos al inicio
                        txt.modifyFile("Users.txt", s, s2);
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Se a cambiado su contraseña");
                        P_ChangePanel_2_tfNewPass.setText("");
                        P_ChangePassword_2_tfConformPass.setText("");
                        P_ChangePassword_2_tfTemporalPass.setText("");
                        P_selection.setVisible(true);
                        P_ChangePasswors.setVisible(false);
                        P_ChangePassword_1.setVisible(true);
                        P_ChangePassword_2.setVisible(false);
                        a.showAndWait();
                    } catch (ListException ex) {
                        Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("La confirmacion de contraseña no coincide");
                    a.showAndWait(); 
                }
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("La contraseña temporal no coincide");
                a.setContentText("Verifique en su correo que sea correcta");
                a.showAndWait(); 
            }
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No deje el espacio en blanco");
            a.showAndWait(); 
        }
    }

    @FXML
    private void P_ChangePanel_2_btnCancel(ActionEvent event) {
        P_ChangePasswors.setVisible(false);
        P_ChangePassword_1.setVisible(true);
        P_ChangePassword_2.setVisible(false);
        P_selection.setVisible(true);
    }

    @FXML
    private void lostPasword(MouseEvent event) {
        P_ChangePasswors.setVisible(true);
        P_student.setVisible(false);
    }
    
        public void sendEmail() throws ListException{
    // Recipient's email ID needs to be mentioned.
        String to = stud.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "xx.ucrfake.xx@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass 
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("xx.ucrfake.xx@gmail.com", "UCRfake123");

            }

        });
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Cambio de contraseña de "+stud.getFirstname()+" "+stud.getLastname());

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();
            
            
            try {

                File f =new File("Escudo.png");

                attachmentPart.attachFile(f);
                textPart.setText("Su contraseña temporal para el cambio de contraseña es :\n"+temporalPass);
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
