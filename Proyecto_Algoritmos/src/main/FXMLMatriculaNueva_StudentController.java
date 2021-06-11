/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Course;
import domain.Enrollment;
import domain.Student;
import domain.TimeTable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
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
                        list.add(c.getName());//se consigue el nombre del curso
                    for (int j = 1; j <= util.Utility.getHorarios().size(); j++) {
                        TimeTable tt=(TimeTable)util.Utility.getHorarios().getNode(j).data;
                        if(tt.getCourseID().equals(c.getId())){
                            list.add(tt.getPeriod()); // se recorren diferentes lista y se valida si la informacion pertenece a la carrera del estudiante y se agrega a la lista
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
                    tableView.getItems().add(list);//se llena la talba
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
            labelCursos.setText(cursos+" "+scheduleSelect);//se pone el el label el curso seleccionado en el combobox
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
                 TimeTable tt=null;
             for (int j = 1; j <= util.Utility.getHorarios().size(); j++) {
                    tt = (TimeTable)util.Utility.getHorarios().getNode(j).data;
                    if(e.getCourseID().equals(tt.getCourseID()))
                        break;
                 }
                        if(column2.getCellData(tableView.getSelectionModel().getSelectedIndex()).equals(tt.getPeriod())){//se valida que el curso este en el periodo           
                        String split[]=cBoxCourse.getSelectionModel().getSelectedItem().split("-");//se consigue el horario desde el combobobox y se divide entre tres: 1. dia. 2.primer horario. 3. segundo horario 
                        String split2[]=e.getSchedule().split("-");//se consigue el horario desde la lista y se divide entre tres: 1. dia. 2.primer horario. 3. segundo horario 
                        if(split[0].equals(split2[0])){//esta posicion del arreglo tiene el dia y se asegura que en el dia no hayan choches de horario
                        String crashSchedule=split[1];
                        String crashSchedule2=split[2];
                        String crashSchedule3=split2[1];
                        String crashSchedule4=split2[2];
                        int hour1=0;
                        int hour2=0;
                        int hour3=0;
                        int hour4=0;
                        String cS[]=crashSchedule.split(",");//los horarios se dividen entre la hora de entrada y salida
                        String cS2[]=crashSchedule2.split(",");
                        String cS3[]=crashSchedule3.split(",");
                        String cS4[]=crashSchedule4.split(",");
                        
                        hour1=Integer.parseInt(cS[0]);
                        hour2=Integer.parseInt(cS2[0]);
                        hour3=Integer.parseInt(cS3[0]);
                        hour4=Integer.parseInt(cS4[0]);
                          
                        
                        if(cS[1].equals("PM")&&!(Integer.parseInt(cS[0])==12))//se pasan las horas a 24 horas
                            hour1=Integer.parseInt(cS[0])+12;
                        if(cS2[1].equals("PM")&&!(Integer.parseInt(cS2[0])==12))
                            hour2=Integer.parseInt(cS2[0])+12;
                        if(cS3[1].equals("PM")&&!(Integer.parseInt(cS3[0])==12))
                            hour3=Integer.parseInt(cS3[0])+12;
                        if(cS4[1].equals("PM")&&!(Integer.parseInt(cS4[0])==12))
                            hour4=Integer.parseInt(cS4[0])+12;
                        
                        if(hour1<=hour3 && hour3<=hour2)//se valida que las horas no chocen
                            crash=true;
                        if(hour1<=hour4 && hour4<=hour2)
                            crash=true;
                        if(hour3<=hour1 && hour1<=hour4)
                            crash=true;
                        if(hour3<=hour2 && hour2<=hour4)
                            crash=true;
                            }
                        }
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
    //se matricula
    this.eR=new Enrollment(date,stud.getStudentID(),id,cBoxCourse.getSelectionModel().getSelectedItem());
    util.Utility.getMatriculas().add(eR);
    txt.writeFile("matricula.txt", eR.toString());
    btnEmail();//se manda correo
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
            
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Escoga un curso");
            a.showAndWait(); 
        }

    }
    private void btnEmail() {
        String to = stud.getEmail();
        // cuenta desde la cual se manda el correo
        String from = "xx.ucrfake.xx@gmail.com";
        //se usa SMTP ya que para mandar un mensaje se necesita un servidor, y este es gratuito
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        // Setup server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // autentifica la cuenta que va a mandar el mensaje
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("xx.ucrfake.xx@gmail.com", "UCRfake123");
            }});
        try {
            //instancia del mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // contenido del mensaje
            message.setSubject("Proceso de matricula completado "+stud.getFirstname()+" "+stud.getLastname());
            String content = "Curso matriculado:\nId curso: "+eR.getCourseID()+"\nNombre de curso: "+cursos+"\nHorario: " +eR.getSchedule()+"\nFecha de matricula: "+util.Utility.dateFormat(eR.getDate());
            // se pone el contenido en el mensaje
            message.setText(content);
            // se manda el mensaje
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
}   
}


        

