/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Student;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author ExtremeTech
 */
public class FXMLMatriculaRetirarController implements Initializable {

    @FXML
    private Text puTxt;
    @FXML
    private TextField txtFieldStudID;
    @FXML
    private Button btnEnter;
    @FXML
    private TableView<?> tableView;
    @FXML
    private Button btnDeenrollment;
    @FXML
    private Button btnEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnEnter(ActionEvent event) throws ListException {
    String studId = this.txtFieldStudID.getText();
    boolean findStud = false;
    Student aux;
    for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++){
            aux = (Student)util.Utility.getEstudiantes().getNode(i).data;
            if(studId.equals(aux.getStudentID()))
                findStud=true;
    }
    if (txtFieldStudID.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } 
     else if(findStud==false){
         Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Estudiante con id "+studId+" no ha sido encontrado");
            a.showAndWait();
     }
     else{
         this.txtFieldStudID.setVisible(false);
         
         this.puTxt.setVisible(false);
         this.btnEnter.setVisible(false);
         this.tableView.setVisible(true);
         this.btnDeenrollment.setVisible(true);
    }
    }
    @FXML
    private void btnDeenrollment(ActionEvent event) {
        this.tableView.setVisible(false);
        this.btnDeenrollment.setVisible(false);
        this.puTxt.setVisible(true);
        this.puTxt.setText("Proceso Completado");
        this.btnEmail.setVisible(true);
    }

    @FXML
    private void btnEmail(ActionEvent event) {
    String to = "adriure11@hotmail.com";
        // Mention the Sender's email address
        String from = "xx.ucrfake.xx@gmail.com";
        // Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("xx.ucrfake.xx@gmail.com", "UCRfake123");
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("Que paaaaaaaaaaa, tooo bieeeeeeeeen");
            // Now set the actual message
            message.setText("ds2 is trash");
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

