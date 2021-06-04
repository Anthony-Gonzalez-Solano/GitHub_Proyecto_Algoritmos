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
import domain.TimeTable;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
    private Enrollment eR;
    private Student stud;
    private int studNum;
    private ComboBox<String> cBoxStud2;
    @FXML
    private Text putTxt;
    @FXML
    private ComboBox<String> cBoxStud;
    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private Button btnEnrollment;
    @FXML
    private Button btnEmail;
    @FXML
    private ComboBox<?> cBoxCourse;
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
                cBoxStud.getItems().add(aux.toString());
            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }    
    @FXML
    private void cBoxCourse(ActionEvent event) {
        
    }
    @FXML
    private void cBoxStud(ActionEvent event) throws ListException {
    putTxt.setText("");
    cBoxStud.setVisible(false);
    btnEnrollment.setVisible(true);
    tableView.setVisible(true);
    studNum = (cBoxStud.getSelectionModel().getSelectedIndex())+1;
    stud = (Student)util.Utility.getEstudiantes().getNode(studNum).data;
    System.out.print(stud.secondToString());
    if(this.tableView.getColumns().isEmpty()){
            TableColumn<List<String>,String>column1=new TableColumn<>("courseID");
            column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
            TableColumn<List<String>,String>column2=new TableColumn<>("period");
            column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
            TableColumn<List<String>,String>column3=new TableColumn<>("schedule1");
            column3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
            TableColumn<List<String>,String>column4=new TableColumn<>("schedule2");
            column4.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
            
            this.tableView.getColumns().addAll(column1,column2,column3,column4);//agregar columnas

    }
    try{
        tableView.getItems().clear();
        List list = new ArrayList();
        Course  c=null;
        boolean check=false;
        for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
            c = (Course)util.Utility.getCursos().getNode(i).data;
                if(stud.getCareerID()==c.getCareerID()){
                        list.add(c.getName());
                        System.out.print("\nadasd");
              for (int j = 1; j <= util.Utility.getHorarios().size(); j++) {
                  TimeTable tt=(TimeTable)util.Utility.getHorarios().getNode(j).data;
                  if(tt.getCourseID().equals(c.getId())){
                      list.add(tt.getPeriod()); 
                      list.add(tt.getSchedule1());
                      list.add(tt.getSchedule2());
                      check=true;
                      System.out.print("\nadasd");
                  }//end if 
            }//end for
            
                    
//                if(c.getCareerID()==stud.getCareerID())
//                        tableView.getItems().add(util.Utility.getHorarios().getNode(i).data);
                
//        for (int i = 1; i <= util.Utility.getHorarios().size(); i++) {
//            this.tableView.getItems().add((TimeTable) util.Utility.getHorarios().getNode(i).data);
//               TimeTable c = (TimeTable)util.Utility.getCursos().getNode(i).data;
//                if((stud.getCareerID()==c.getCourseID())&&!(util.Utility.getHorarios().getNode(i).data==null)){
//                    
//                    tableView.getItems().add((Course)util.Utility.getCursos().getNode(i).data);
////                    tableView.getItems().add((Course)util.Utility.getHorarios().getNode(i).data);
//                }
                }
                if(check==true){

                    tableView.getItems().add(list);
                    list.clear();
                    check=false;
                }else{
                    list.clear();
                    check=false;
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
    private void btnEnrollment(ActionEvent event) throws ListException {
    this.tableView.setVisible(false);
    this.btnEnrollment.setVisible(false);
    btnEmail.setVisible(true);
    this.putTxt.setText("Proceso completado");
     
    Date date = new Date();
    date.getTime();

//    TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(5);
    
    
//    for(int row =0; row<tableView.getItems().size();row++){
////       int id=tableView.getSelectionModel().getSelectedItem().getCourseID();
        
//            for (int i = 1; i <=tableView.getItems().size() ; i++) {
//                System.out.print((Boolean)column5.getCellData(i));
//            if((Boolean)column5.getCellData(i)==true){
//                   eR = new Enrollment(stud.getId(),date,stud.getStudentID(),tableView.getSelectionModel().getSelectedItem().getCourseID(),(tableView.getSelectionModel().getSelectedItem().getSchedule1()+tableView.getSelectionModel().getSelectedItem().getSchedule1())) ; 
//                }
        
    
    }
//     util.Utility.getMatriculas().add(eR);
//    txt.writeFile("matricula.txt", eR.toString());       
//       
        
    


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
            message.setSubject("Proceso de matricula completado "+stud.getFirstname()+" "+stud.getLastname());
            String content = "Id curso:"+eR.getCourseID()+",Horario: "+eR.getSchedule()+", Fecha de matricula:"+eR.getDate();
            // Now set the actual message
            message.setText(content);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
}

    
}


        

