/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import domain.Course;
import domain.Enrollment;
import domain.Student;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author ExtremeTech
 */
public class FXMLMatriculaNuevaController implements Initializable {
    private util.FileTXT txt;
    private Student stud;
    private int studNum;
    private ComboBox<String> cBoxStud2;
    @FXML
    private Text putTxt;
    @FXML
    private ComboBox<String> cBoxStud;
    @FXML
    private TableView<Course> tableView;
    @FXML
    private Button btnEnrollment;
    @FXML
    private Button btnEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.txt = new FileTXT();
       putTxt.setText("Escoga estudiante");
       Student aux;
       String stud;
        try {

            for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                aux = (Student)util.Utility.getEstudiantes().getNode(i).data;
                cBoxStud.getItems().add("Cedula:"+aux.getId()+",Carne:"+aux.getStudentID()+",Apellido:"+aux.getLastname()+",Nombre:"+aux.getFirstname());
            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }    

    @FXML
    private void cBoxStud(ActionEvent event) throws ListException {
    putTxt.setText("");
    cBoxStud.setVisible(false);
    tableView.setVisible(true);
    btnEnrollment.setVisible(true);
    studNum = (cBoxStud.getSelectionModel().getSelectedIndex())+1;
    stud = (Student)util.Utility.getEstudiantes().getNode(studNum).data;
    if(this.tableView.getColumns().isEmpty()){
            TableColumn<Course,String>column1=new TableColumn<>("ID");
            column1.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<Course,String>column2=new TableColumn<>("Name");
            column2.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Course,String>column3=new TableColumn<>("Credits");
            column3.setCellValueFactory(new PropertyValueFactory<>("credits"));
            TableColumn<Course,String>column4=new TableColumn<>("Horario");
            column4.setCellValueFactory(new PropertyValueFactory<>("Horario"));
            TableColumn<Course, Boolean> column5 = new TableColumn<>(""); 
            column5.setCellValueFactory(cell -> {
            Course p = cell.getValue();
            return new ReadOnlyBooleanWrapper();});
            column5.setEditable(true);
            column5.setCellFactory(CheckBoxTableCell.forTableColumn(column5));
            this.tableView.getColumns().add(column1);//agregar columnas
            this.tableView.getColumns().add(column2);
            this.tableView.getColumns().add(column3);
            this.tableView.getColumns().add(column4);
            this.tableView.getColumns().add(column5);
            tableView.setEditable(true);
    }
    try{
        while(!this.tableView.getItems().isEmpty()){
            this.tableView.getItems().remove(0);
        }
        for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
            this.tableView.getItems().add((Course) util.Utility.getCursos().getNode(i).data);
               Course c = (Course)util.Utility.getCursos().getNode(i).data;
                if((stud.getCareerID()==c.getCareerID())&&!(util.Utility.getHorarios().getNode(i).data==null)){
                    
                    tableView.getItems().add((Course)util.Utility.getCursos().getNode(i).data);
//                    tableView.getItems().add((Course)util.Utility.getHorarios().getNode(i).data);
                }
                }
    } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(" La lista esta vacia");
            a.showAndWait();
    }
        catch(NullPointerException eda){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
        }
    }

    @FXML
    private void btnEnrollment(ActionEvent event) {
    this.tableView.setVisible(false);
    this.btnEnrollment.setVisible(false);
    btnEmail.setVisible(true);
    this.putTxt.setText("Proceso completado");
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

        

