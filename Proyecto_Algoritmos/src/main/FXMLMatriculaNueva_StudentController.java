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
import javafx.scene.control.Label;
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
import javafx.scene.input.MouseEvent;
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
public class FXMLMatriculaNueva_StudentController implements Initializable {
    private util.FileTXT txt;
    private Enrollment eR;
    private Student stud;
    private int studNum;
    private List matricula;
    private String cursos;
    private int index;
    private TableColumn <List<String>,String>column1;
    private TableColumn <List<String>,String>column2;
    private TableColumn <List<String>,String>column3;
    private TableColumn <List<String>,String>column4;
    @FXML
    private Text putTxt;
    @FXML
    private ComboBox<String> cBoxStud;
    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private Button btnEnrollment;
    @FXML
    private ComboBox<String> cBoxCourse;
    @FXML
    private Label labelCursos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        index=-1;
        column1=new TableColumn<>("Course");
            column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
            column2=new TableColumn<>("Period");
            column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
            column3=new TableColumn<>("Schedule 1");
            column3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
            column4=new TableColumn<>("Schedule 2");
            column4.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
            
            this.tableView.getColumns().addAll(column1,column2,column3,column4);//agregar columnas
            
        try{
        putTxt.setText("");
        cBoxStud.setVisible(false);
        cBoxCourse.setVisible(true);
        btnEnrollment.setVisible(true);
        tableView.setVisible(true);
        stud = util.Utility.getIntro();
        tableView.getItems().clear();
       
        Course  c=null;
        boolean check=false;
        for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
             List list = new ArrayList();
            c = (Course)util.Utility.getCursos().getNode(i).data;
                if(stud.getCareerID()==c.getCareerID()){
                        list.add(c.getName());
                    for (int j = 1; j <= util.Utility.getHorarios().size(); j++) {
                        TimeTable tt=(TimeTable)util.Utility.getHorarios().getNode(j).data;
                        if(tt.getCourseID().equals(c.getId())){
                            list.add(tt.getPeriod()); 
                            list.add(tt.getSchedule1());
                            list.add(tt.getSchedule2());
                            check=true;
                        }//end if 
                    }//end for
                    if(!util.Utility.getMatriculas().isEmpty()){
                    for (int j = 1; j <= util.Utility.getMatriculas().size(); j++) {
                       Enrollment m = (Enrollment)util.Utility.getMatriculas().getNode(j).data;
                        if(m.getCourseID().equals(c.getId())&&m.getStudentID().equals(stud.getStudentID()))
                            check=false;
                        }
                    }
                }
                if(check==true){
                    tableView.getItems().add(list);
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
    
        
       this.txt = new FileTXT();
    }  
    @FXML
    private void tableViewAction(MouseEvent event) throws ListException {
        cBoxCourse.getItems().clear();
        labelCursos.setText("");
        index = tableView.getSelectionModel().getSelectedIndex();
        
           cBoxCourse.getItems().add(column3.getCellData(index));
           cBoxCourse.getItems().add(column4.getCellData(index));
           cursos=column1.getCellData(index);
    }
    @FXML
    private void cBoxCourse(ActionEvent event) throws ListException {
        String scheduleSelect = cBoxCourse.getSelectionModel().getSelectedItem();
        if(index>=0){
            labelCursos.setText(cursos+" "+scheduleSelect);
        }
        
    }
    @FXML
    private void cBoxStud(ActionEvent event) throws ListException {
    
 
    }

    
    @FXML
    private void btnEnrollment(ActionEvent event) throws ListException {
        boolean crash=false;
        if(cBoxCourse.getSelectionModel().getSelectedIndex()!=-1){
        if(!util.Utility.getMatriculas().isEmpty()){
         for (int i = 1; i <= util.Utility.getMatriculas().size(); i++){
             Enrollment e=(Enrollment)util.Utility.getMatriculas().getNode(i).data;
             if(e.getStudentID().equals(stud.getStudentID())){
                        String split[]=cBoxCourse.getSelectionModel().getSelectedItem().split("-");
                        String split2[]=e.getSchedule().split("-");
                        if(split[0].equals(split2[0])){
                        String crashSchedule=split[1];
                        String crashSchedule2=split[2];
                        String crashSchedule3=split2[1];
                        String crashSchedule4=split2[2];
                        int hour1=0;
                        int hour2=0;
                        int hour3=0;
                        int hour4=0;
                        String cS[]=crashSchedule.split(",");
                        String cS2[]=crashSchedule2.split(",");
                        String cS3[]=crashSchedule3.split(",");
                        String cS4[]=crashSchedule4.split(",");
                        if(cS[1].equals("PM")&&!(Integer.valueOf(cS[0])==12))
                            hour1=Integer.valueOf(cS[0])+12;
                        else if(cS2[1].equals("PM")&&!(Integer.valueOf(cS[0])==12))
                            hour2=Integer.valueOf(cS2[0])+12;
                        else if(cS3[1].equals("PM")&&!(Integer.valueOf(cS[0])==12))
                            hour3=Integer.valueOf(cS3[0])+12;
                        else if(cS4[1].equals("PM")&&!(Integer.valueOf(cS[0])==12))
                            hour4=Integer.valueOf(cS4[0])+12;
                        {
                            hour1=Integer.valueOf(cS[0]);
                            hour2=Integer.valueOf(cS2[0]);
                            hour3=Integer.valueOf(cS3[0]);
                            hour4=Integer.valueOf(cS4[0]);
                        }
                        if(hour1<=hour3||hour2>=hour4)
                            crash=true;
             }
             }                    
         }
        }
        if(crash==false){
        if(labelCursos.getText().isEmpty()){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Necesita escoger un curso");
        a.showAndWait();
        }else{
    String id="";
    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                Course c = (Course)util.Utility.getCursos().getNode(i).data;
                if(c.getCareerID()==stud.getCareerID()&&c.getName().equals(this.cursos)){
                    id=c.getId();
                }
    }
    Date date = new Date();
    
    this.eR=new Enrollment(date,stud.getStudentID(),id,cBoxCourse.getSelectionModel().getSelectedItem());
    util.Utility.getMatriculas().add(eR);
    txt.writeFile("matricula.txt", eR.toString());
    btnEmail();
    this.tableView.getSelectionModel().clearSelection();
    cBoxCourse.getSelectionModel().clearSelection();
    labelCursos.setText("");
    tableView.getItems().remove(index);
    
               Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Se a matriculado correctamente");
            a.setContentText("Se le a emviado un correo al estudiante");
            a.showAndWait(); 
            index=-1;
            this.cBoxCourse.getItems().clear();
            this.cBoxCourse.setPromptText("Horario");
            
            }
         }else if(crash==true){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Horario chochan. Escoga otro horario");
            a.showAndWait(); 
         }   
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Escoga un curso");
            a.showAndWait(); 
        }
}
    
    private void btnEmail() {
        String to = stud.getEmail();
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
            String content = "Curso matriculado:\nId curso: "+eR.getCourseID()+"\nNombre de curso: "+cursos+"\nHorario: " +eR.getSchedule()+"\nFecha de matricula: "+util.Utility.dateFormat(eR.getDate());
            
            // Now set the actual message
            message.setText(content);
            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
}   
}


        

