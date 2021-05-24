/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import domain.Student;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
    private Text txtCedula;
    @FXML
    private Text txtLastName;
    @FXML
    private Text txtPhone;
    @FXML
    private Text txtFirstName;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtDireccion;
    @FXML
    private DatePicker datePickerEstudiante;
    @FXML
    private Text txtFecha;
    @FXML
    private Button btnAgregar;
    @FXML
    private Text txtMessage;
    @FXML
    private ComboBox<String> comboCarrera;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            txt = new FileTXT();

            for (int i = 1; i < util.Utility.getCarreras().size(); i++) {
                comboCarrera.getItems().add(util.Utility.getCarreras().getNode(i).data + "");
            }
        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("La lista esta vacia");
            a.showAndWait();
        }

    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        Calendar cal = new GregorianCalendar(this.datePickerEstudiante.getValue().getYear(),
                this.datePickerEstudiante.getValue().getMonthValue(),
                this.datePickerEstudiante.getValue().getDayOfMonth());
        if (textFieldAdress.getText().isEmpty() || textFieldEmail.getText().isEmpty() || textFieldId.getText().isEmpty() || textFieldLastName.getText().isEmpty() || textFieldPhone.getText().isEmpty() || textFieldFirstName.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Student s = new Student(Integer.parseInt(this.textFieldId.getText()), this.textFieldLastName.getText(), this.textFieldFirstName.getText(), cal.getTime(), this.textFieldPhone.getText(), this.textFieldEmail.getText(), this.textFieldAdress.getText(), Integer.parseInt(new DecimalFormat("0000").format(autoID)));
            try {
                if (util.Utility.getEstudiantes().contains(s) == false ) {
                    util.Utility.getEstudiantes().add(s);
                    this.textFieldAdress.setText("");
                    this.textFieldEmail.setText("");
                    this.textFieldFirstName.setText("");
                    this.textFieldPhone.setText("");
                    this.textFieldId.setText("");
                    this.textFieldLastName.setText("");

                    txt.writeFile("estudiantes.txt", s.toString());
                    txt.writeFile("Users.txt", s.getStudentID() + "," + util.Utility.binaryCodify("-"));

                    this.txtMessage.setVisible(true);

                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("Estudiante agregado correctamente");
                    a.showAndWait();
                    autoID++;
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("El estudiante ya esta registrado\n  Ingrese uno nuevo");
                    a.showAndWait();
                }

            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado");
                a.showAndWait();
            } catch (ListException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Lista vacia");
                a.showAndWait();
            }
        }
    }

}
