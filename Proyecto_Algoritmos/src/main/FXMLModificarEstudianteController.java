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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLModificarEstudianteController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private ComboBox<Student> comboEstudiantes;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldPhoneNumber;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldAdress;
    @FXML
    private Label txtLastName;
    @FXML
    private Label txtFirstName;
    @FXML
    private Label txtPhone;
    @FXML
    private Label txtEmail;
    @FXML
    private Label txtAdress;
    @FXML
    private Button btnModificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txt = new FileTXT();
        try {

            for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {

                comboEstudiantes.getItems().add((Student) util.Utility.getEstudiantes().getNode(i).data);

            }//recorremos la lista de estudiantes para agregarlas al comboBox, para poder modificarlas

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        if (textFieldAdress.getText().isEmpty() || textFieldEmail.getText().isEmpty() || textFieldFirstName.getText().isEmpty() || textFieldPhoneNumber.getText().isEmpty() || textFieldLastName.getText().isEmpty()) {//validamos campos vacios
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Student s = new Student(comboEstudiantes.getSelectionModel().getSelectedItem().getId(), comboEstudiantes.getSelectionModel().getSelectedItem().getStudentID(), textFieldLastName.getText(), textFieldFirstName.getText(), comboEstudiantes.getSelectionModel().getSelectedItem().getBirthday(), textFieldPhoneNumber.getText(), textFieldEmail.getText(), textFieldAdress.getText(), comboEstudiantes.getSelectionModel().getSelectedItem().getCareerID());//creamos objeto tipo Student, donde se toman los valores de los textField y los comboBox
            try {
                if (!util.Utility.getEstudiantes().isEmpty()) {// si no esta vacia la lista, recorre el ciclo for 

                    if (util.Utility.ValidarMail(this.textFieldEmail.getText()) == true) { //validacion del Email

                        for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                            Student s2 = (Student) util.Utility.getEstudiantes().getNode(i).data;//casteamos para obtener los datos de la lista
                            if (s2.equals(comboEstudiantes.getSelectionModel().getSelectedItem())) {// comparamos con el objeto del ComboBox
                                util.Utility.getEstudiantes().getNode(i).data = s;

                            }
                        }
                        txt.modifyFile("estudiantes.txt", comboEstudiantes.getSelectionModel().getSelectedItem().secondToString(), s.secondToString());
                        int x = comboEstudiantes.getSelectionModel().getSelectedIndex();//tomamos el indice del elemento
                        comboEstudiantes.getItems().remove(x);// se remueve el valor del indice
                        comboEstudiantes.getItems().add(x, s);// se agrega en ese indice el elemento modificado
                        comboEstudiantes.getSelectionModel().clearSelection();
                        textFieldAdress.setText("");
                        textFieldEmail.setText("");
                        textFieldFirstName.setText("");
                        textFieldLastName.setText("");
                        textFieldPhoneNumber.setText("");
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("El estudiante ha sido mofificada correctamente");
                        a.showAndWait();
                        textFieldAdress.setVisible(false);
                        textFieldFirstName.setVisible(false);
                        textFieldLastName.setVisible(false);
                        textFieldPhoneNumber.setVisible(false);
                        textFieldEmail.setVisible(false);
                        txtAdress.setVisible(false);
                        txtFirstName.setVisible(false);
                        txtLastName.setVisible(false);
                        txtPhone.setVisible(false);
                        txtEmail.setVisible(false);
                        btnModificar.setVisible(false);
//                
                    } else { //alerta de direccion de Email
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("La direccion de E-Mail no es valida ");
                        a.showAndWait();
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("La lista de estudiantes esta vacia\nAgregue uno primero");
                    a.showAndWait();
                }
            } catch (NullPointerException epe) {
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

    @FXML
    private void comboEstudiantes(ActionEvent event) {
        if (comboEstudiantes.getSelectionModel().getSelectedIndex() != -1) { // evitar errores cuando se activa el evento del comboBox, porque tomaba datos vacios
            textFieldLastName.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getLastname() + "");// agregamos al textField el LastName del estudiante seleccionado
            textFieldFirstName.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getFirstname() + "");// agregamos al textField el FirstName del estudiante seleccionado
            textFieldPhoneNumber.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getPhoneNumber() + "");// agregamos al textField el telefono del estudiante seleccionado
            textFieldEmail.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getEmail() + "");// agregamos al textField el Email del estudiante seleccionado
            textFieldAdress.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getAddress() + "");// agregamos al textField la direccion del estudiante seleccionado

            textFieldAdress.setVisible(true);
            textFieldFirstName.setVisible(true);
            textFieldLastName.setVisible(true);
            textFieldPhoneNumber.setVisible(true);
            textFieldEmail.setVisible(true);
            txtAdress.setVisible(true);
            txtFirstName.setVisible(true);
            txtLastName.setVisible(true);
            txtPhone.setVisible(true);
            txtEmail.setVisible(true);
            btnModificar.setVisible(true);
        }
    }

}
