/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Course;
import domain.DeEnrollment;
import domain.Enrollment;
import domain.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class FXMLMatriculaRetirar_StudentController implements Initializable {

    private TableColumn<List<String>, String> column1;
    private TableColumn<List<String>, String> column2;
    private TableColumn<List<String>, String> column3;
    private TableColumn<List<String>, String> column4;
    private TableColumn<List<String>, String> column5;
    private util.FileTXT txt;
    private List list;
    private Student stud;
    private DeEnrollment deR;
    private int index;
    private String cursos;
    @FXML
    private Text puTxt;
    @FXML
    private TextField txtFieldStudID;
    @FXML
    private Button btnEnter;
    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private Button btnDeenrollment;
    @FXML
    private Label Label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();
        stud = util.Utility.getIntro();
        this.txtFieldStudID.setVisible(false);
        this.puTxt.setVisible(false);
        this.btnEnter.setVisible(false);
        this.tableView.setVisible(true);
        this.btnDeenrollment.setVisible(true);

        column1 = new TableColumn<>("Course");
        column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        column2 = new TableColumn<>("Course ID");
        column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        column3 = new TableColumn<>("Student ID");
        column3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        column4 = new TableColumn<>("Schedule");
        column4.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        column5 = new TableColumn<>("Date");
        column5.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        this.tableView.getColumns().addAll(column1, column2, column3, column4, column5);//agregar columnas

        Course c = null;
        Enrollment e = null;
        boolean check = false;
        if (util.Utility.getMatriculas().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No hay matriculas hechas");
            a.showAndWait();
        } else {
            try {
                for (int i = 1; i <= util.Utility.getMatriculas().size(); i++) {
                    list = new ArrayList();
                    e = (Enrollment) util.Utility.getMatriculas().getNode(i).data;
                    if (e.getStudentID().equals(stud.getStudentID())) {
                        for (int j = 1; j <= util.Utility.getCursos().size(); j++) {
                            c = (Course) util.Utility.getCursos().getNode(j).data;
                            if (e.getCourseID().equals(c.getId())) {
                                list.add(c.getName());// se recorren diferentes lista y se valida si la informacion pertenece a la carrera del estudiante y se agrega a la lista
                                list.add(e.getCourseID());
                                list.add(e.getStudentID());
                                list.add(e.getSchedule());
                                list.add(String.valueOf(util.Utility.dateFormat(e.getDate())));

                                check = true;
                            }//end if
                        }//end for
                    }
                    if (check == true) {
                        tableView.getItems().add(list);//se agregan a la tabla solamente si el curso llega a cambiar el valor de la variable "check"
                        check = false;
                    }
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLMatriculaRetirar_StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnEnter(ActionEvent event) throws ListException {
        String studId = this.txtFieldStudID.getText();
        boolean findStud = false;
        Student aux;

        if (this.tableView.getColumns().isEmpty()) {
            column1 = new TableColumn<>("Course");
            column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
            column2 = new TableColumn<>("Course ID");
            column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
            column3 = new TableColumn<>("Student ID");
            column3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
            column4 = new TableColumn<>("Schedule");
            column4.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
            column5 = new TableColumn<>("Date");
            column5.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
            this.tableView.getColumns().addAll(column1, column2, column3, column4, column5);//agregar columnas
        }

        tableView.getItems().clear();
        tableView.setPlaceholder(new Label("No hay cursos para retirar"));
        Course c = null;
        Enrollment e = null;
        boolean check = false;

    }

    @FXML
    private void btnDeenrollment(ActionEvent event) throws ListException {

        if (Label.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Necesita escoger un curso");
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("¿Esta seguro que quiere retirar el curso?");
            ButtonType yes = new ButtonType("Sí");
            ButtonType no = new ButtonType("No");
            a.getButtonTypes().clear();
            a.getButtonTypes().addAll(yes, no);

            Optional<ButtonType> option = a.showAndWait();
            if (option.get() == yes) {
                String id = "";
                for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                    Course c = (Course) util.Utility.getCursos().getNode(i).data;
                    if (c.getCareerID() == stud.getCareerID() && c.getName().equals(column1.getCellData(index))) {
                        id = c.getId();
                    }
                }
                Date date = new Date();
                this.deR = new DeEnrollment(date, stud.getStudentID(), id, column4.getCellData(index));
                Enrollment eR = null;//new Enrollment(date,stud.getStudentID(),id,column4.getCellData(index));
                for (int i = 1; i <= util.Utility.getMatriculas().size(); i++) {
                    Enrollment eR2 = (Enrollment) util.Utility.getMatriculas().getNode(i).data;
                    if (eR2.getCourseID().equals(id) && eR2.getStudentID().equals(stud.getStudentID())) {
                        eR = eR2;
                    }
                }
                //se retira y se agrega a la lista de retiros
                txt.writeFile("retiro.txt", deR.toString());
                util.Utility.getRetiros().add(deR);
                //se elimina de la lista de matricula
                txt.removeElement("matricula.txt", eR);
                util.Utility.getMatriculas().remove(eR);
                btnEmail();//se manda correo
                this.tableView.getSelectionModel().clearSelection();
                Label.setText("");
                tableView.getItems().remove(index);
            }
        }
    }

    @FXML
    private void tableViewAction(MouseEvent event) {
        Label.setText("");
        try {
            if (!list.isEmpty()) {
                index = tableView.getSelectionModel().getSelectedIndex();
            }

            cursos = column1.getCellData(index) + " " + column2.getCellData(index) + " " + column4.getCellData(index);
            if (!column1.getCellData(index).equals("")) {
                Label.setText(cursos + " ");//cuando se hace click en la table se manda el indice de la posicion del objeto seleccionado y con ello se extrae los datos de las columnas
            }

        } catch (NullPointerException npe) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Necesita escoger un curso");
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
            message.setSubject("Proceso de retiro completado " + stud.getFirstname() + " " + stud.getLastname());
            message.setText("Curso Retirado:\nId curso: " + deR.getCourseID() + "\nNombre de curso: " + cursos + "\nHorario: " + deR.getSchedule() + "\nFecha de matricula: " + util.Utility.dateFormat(deR.getDate()));
            // se manda el mensaje
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
