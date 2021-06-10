/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;// librerias para manejar expresiones regulares
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
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
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLAgregarEstudianteController implements Initializable {

    private static int autoID;

    private util.FileTXT txt;
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldAdress;
    @FXML
    private DatePicker datePickerEstudiante;
    @FXML
    private Button btnAgregar;
    @FXML
    private Text txtMessage;
    @FXML
    private ComboBox<Career> comboCarrera;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            txt = new FileTXT();

            for (int i = 1; i < util.Utility.getCarreras().size(); i++) {//recorremos lista carreras para agregarlos al comboBox
                comboCarrera.getItems().add((Career) util.Utility.getCarreras().getNode(i).data); //casteamos para agregarlas
            }
        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("La lista esta vacia");
            a.showAndWait();
        }

    }

    @FXML
    private void btnAgregar(ActionEvent event) {
// validacion de ho haber campos vacios
        if (textFieldAdress.getText().isEmpty() || comboCarrera.getSelectionModel().isEmpty() || textFieldEmail.getText().isEmpty() || textFieldId.getText().isEmpty() || textFieldLastName.getText().isEmpty() || textFieldPhone.getText().isEmpty() || textFieldFirstName.getText().isEmpty() || datePickerEstudiante.getEditor().getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Calendar cal = new GregorianCalendar(this.datePickerEstudiante.getValue().getYear(), // obtener la fecha ingresada en el datePicker
                    this.datePickerEstudiante.getValue().getMonthValue(),//obtener mes
                    this.datePickerEstudiante.getValue().getDayOfMonth());//dia
            Student s = new Student(Integer.parseInt(this.textFieldId.getText()), this.textFieldLastName.getText(), this.textFieldFirstName.getText(), cal.getTime(), this.textFieldPhone.getText(), this.textFieldEmail.getText(), this.textFieldAdress.getText(), comboCarrera.getSelectionModel().getSelectedItem().getId());// objeto estudiante, pasamos los valores de los texfield y coboBox
            try {
                boolean exist = false;// validar si existe el dato en la lista
                boolean exist2 = false;
                if (util.Utility.ValidarMail(this.textFieldEmail.getText()) == true) { // validar que la direccion de correo sea valida 
                    if (util.Utility.getEstudiantes().contains(s) == false) { // si no esta en la lista procedera a hacer el for
                        for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                            Student s2 = (Student) util.Utility.getEstudiantes().getNode(i).data;//casteamos para obtener los datos de la lista estudiantes
                            if (Integer.parseInt(textFieldId.getText()) == (s2.getId())) { //validar no haya estudiantes iguales
                                exist = true;
                            }
                            if (textFieldEmail.getText().equals(s2.getEmail())) { // validar que no haya corres iguales
                                exist2 = true;
                            }

                        }

                        if (exist == false && exist2 == false) { // si no existen se agregan a la lista
                            
                            util.Utility.getEstudiantes().add(s);
                            this.textFieldAdress.setText("");
                            this.textFieldEmail.setText("");
                            this.textFieldFirstName.setText("");
                            this.textFieldPhone.setText("");
                            this.textFieldId.setText("");
                            this.textFieldLastName.setText("");
                            datePickerEstudiante.getEditor().clear(); //limpiamos los comboBox
                            comboCarrera.getSelectionModel().clearSelection();
                            sendEmail(s);
                            txt.writeFile("estudiantes.txt", s.secondToString()); // escribimos en el TXT
                            txt.writeFile("Users.txt", s.getStudentID() + "," + util.Utility.binaryCodify("-"));

                           // this.txtMessage.setVisible(true);

                            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                            a.setHeaderText("Estudiante agregado correctamente");
                            a.showAndWait();
                            autoID++; // aumenta el id
                        } else { //errores
                            if (exist == true) {// si existe la cedula manda alerta
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setHeaderText("La cedula ingresada ya existe para otra persona\n Ingrese un id diferente");
                                a.showAndWait();
                                this.textFieldAdress.setText("");
                                this.textFieldEmail.setText("");
                                this.textFieldFirstName.setText("");
                                this.textFieldPhone.setText("");
                                this.textFieldId.setText("");
                                this.textFieldLastName.setText("");
                            } else {//verifica el Email
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setHeaderText("La direccion de E-Mail ingresada ya existe");
                                a.showAndWait();
                                this.textFieldAdress.setText("");
                                this.textFieldEmail.setText("");
                                this.textFieldFirstName.setText("");
                                this.textFieldPhone.setText("");
                                this.textFieldId.setText("");
                                this.textFieldLastName.setText("");
                            }
                        }
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("El estudiante ya esta registrado\n  Ingrese uno nuevo");
                        a.showAndWait();
                        this.textFieldAdress.setText("");
                        this.textFieldEmail.setText("");
                        this.textFieldFirstName.setText("");
                        this.textFieldPhone.setText("");
                        this.textFieldId.setText(""); // limpiamos campos de texto
                        this.textFieldLastName.setText("");
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("La direccion de E-Mail no es correcta,");
                    a.showAndWait();
                }

            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado");
                a.showAndWait();
                this.textFieldAdress.setText("");
                this.textFieldEmail.setText("");
                this.textFieldFirstName.setText("");
                this.textFieldPhone.setText("");
                this.textFieldId.setText(""); // limpiamos campos de texto
                this.textFieldLastName.setText("");
            } catch (ListException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Lista vacia");
                a.showAndWait();
                this.textFieldAdress.setText(""); // limpiamos campos de texto
                this.textFieldEmail.setText("");
                this.textFieldFirstName.setText("");
                this.textFieldPhone.setText("");
                this.textFieldId.setText("");
                this.textFieldLastName.setText("");
            }
        }
    }

    @FXML
    private void numericCed(KeyEvent event) {
        textFieldId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
         });
    }

    @FXML
    private void numericAddress(KeyEvent event) {
        textFieldPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldPhone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
         });
    }

    @FXML
    private void numericDate(KeyEvent event) {
        datePickerEstudiante.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    datePickerEstudiante.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
         });
    }
    public void sendEmail(Student s) throws ListException{
    // Recipient's email ID needs to be mentioned.
        String to = "adriure11@hotmail.com";

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
            message.setSubject("Bienvenido "+s.getFirstname()+" "+s.getLastname()+
                                " a la universidad de Costa Rica!");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();
            String careerName="";
            for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
                Career c = (Career) util.Utility.getCarreras().getNode(i).data;
                 if(c.getId()==s.getCareerID())
                        careerName=c.getDescription();
                     }
            
            try {

                File f =new File("C:\\Users\\ExtremeTech\\Documents\\NetBeansProjects\\GitHub_Proyecto_Algoritmos\\Proyecto_Algoritmos\\Escudo.png");

                attachmentPart.attachFile(f);
                textPart.setText("Carne: "+s.getStudentID()+"\nId: "+s.getId()+"\nNombre: "+s.getFirstname()+" "+s.getLastname()
                        + "\nNumero de telefono: "+s.getPhoneNumber()+"\nEmail: "+s.getEmail()+"\nDireccion: "+s.getAddress()+"\nCarrera: "+careerName);
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

