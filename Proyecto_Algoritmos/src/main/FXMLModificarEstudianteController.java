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
    private Text txtLastName;
    @FXML
    private Text txtFirstName;
    @FXML
    private Text txtPhone;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtAdress;
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

            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        if (textFieldAdress.getText().isEmpty() || textFieldEmail.getText().isEmpty() || textFieldFirstName.getText().isEmpty() || textFieldPhoneNumber.getText().isEmpty() || textFieldLastName.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Student s = new Student(comboEstudiantes.getSelectionModel().getSelectedItem().getId(), comboEstudiantes.getSelectionModel().getSelectedItem().getStudentID(), textFieldLastName.getText(), textFieldFirstName.getText(), comboEstudiantes.getSelectionModel().getSelectedItem().getBirthday(), textFieldPhoneNumber.getText(), textFieldEmail.getText(), textFieldAdress.getText(), comboEstudiantes.getSelectionModel().getSelectedItem().getCareerID());
            try {
                if (!util.Utility.getEstudiantes().isEmpty()) {

                    if (util.Utility.ValidarMail(this.textFieldEmail.getText()) == true) {

                        for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                            Student s2 = (Student) util.Utility.getEstudiantes().getNode(i).data;
                            if (s2.equals(comboEstudiantes.getSelectionModel().getSelectedItem())) {
                                util.Utility.getEstudiantes().getNode(i).data = s;

                            }
                        }
                        txt.modifyFile("estudiantes.txt", comboEstudiantes.getSelectionModel().getSelectedItem().secondToString(), s.secondToString());
                        int x = comboEstudiantes.getSelectionModel().getSelectedIndex();
                        comboEstudiantes.getItems().remove(x);
                        comboEstudiantes.getItems().add(x, s);
                        comboEstudiantes.getSelectionModel().clearSelection();
                        textFieldAdress.setText("");
                        textFieldEmail.setText("");
                        textFieldFirstName.setText("");
                        textFieldLastName.setText("");
                        textFieldPhoneNumber.setText("");
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("El estudiante ha sido mofificada correctamente");
                        a.showAndWait();

                        textFieldAdress.setText("");
                        textFieldEmail.setText("");
                        textFieldFirstName.setText("");
                        textFieldLastName.setText("");
                        textFieldPhoneNumber.setText("");
                    } else {
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
        if (comboEstudiantes.getSelectionModel().getSelectedIndex() != -1) {
            textFieldLastName.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getLastname() + "");
            textFieldFirstName.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getFirstname() + "");
            textFieldPhoneNumber.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getPhoneNumber() + "");
            textFieldEmail.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getEmail() + "");
            textFieldAdress.setText(comboEstudiantes.getSelectionModel().getSelectedItem().getAddress() + "");
        }
    }

}
