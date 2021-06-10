/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Enrollment;
import domain.Security;
import domain.Student;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLRemoverEstudianteController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldCedula;
    @FXML
    private Button btnRemover;
    @FXML
    private Label txtFirst;
    @FXML
    private Label txtId;
    @FXML
    private ComboBox<Student> comboStudent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt = new FileTXT();
        try {

            for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {

                comboStudent.getItems().add((Student) util.Utility.getEstudiantes().getNode(i).data);

            }//recorremos la lista de estudiantes para agregarlas al comboBox, para poder modificarlas

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

    @FXML
    private void btnRemover(ActionEvent event) {

        if (comboStudent.getSelectionModel().getSelectedIndex()==-1) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
     

        } else {

            Date date = null;
           // Student s = new Student(comboStudent.getSelectionModel().getSelectedItem().getId(), "", "", comboStudent.getSelectionModel().getSelectedItem().getFirstname(), date, "", "", "", Integer.parseInt(textFieldCedula.getText()));
            Student s= (Student)comboStudent.getSelectionModel().getSelectedItem();
            try {
                boolean exist = false;
                boolean exist2 = false;
                boolean firstName = false;
                if (!util.Utility.getEstudiantes().isEmpty()) {
                
                    for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                        Student s2 = (Student) util.Utility.getEstudiantes().getNode(i).data;//casteamos para obtener los datos lista estudiantes
//                        if (util.Utility.equals(s2, s)) { // si son iguales se iguala s al dato de la lista
//                            s = (Student) util.Utility.getEstudiantes().getNode(i).data;
//                        }
//                    
                        if (!util.Utility.getMatriculas().isEmpty()) {
                            for (int j = 1; j <= util.Utility.getMatriculas().size(); j++) {
                                Enrollment e = (Enrollment) util.Utility.getMatriculas().getNode(j).data;
                                if (e.getStudentID().equalsIgnoreCase(s.getStudentID())) {
                                    exist = true;
                                }
                            }
                        }
                    }

                    if (exist == false) {
                       

                            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                            a.setHeaderText("¿Esta seguro que quiere remover el estudiante?");
                            ButtonType yes = new ButtonType("Sí");
                            ButtonType no = new ButtonType("No");
                            a.getButtonTypes().clear();
                            a.getButtonTypes().addAll(yes, no);

                            Optional<ButtonType> option = a.showAndWait();
                            if (option.get() == yes) {

                                util.Utility.getEstudiantes().remove(s);
                                txt.removeElement("estudiantes.txt", comboStudent.getSelectionModel().getSelectedItem().secondToString());// se remueve en el TXT
                                int x = comboStudent.getSelectionModel().getSelectedIndex();//tomamos el indice del elemento
                                comboStudent.getItems().remove(x);// se remueve el valor del indice
                                comboStudent.getSelectionModel().clearSelection();
                                textFieldCedula.setText("");
                                textFieldLastName.setText("");
                                Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                a2.setHeaderText("Estudiante eliminado correctamente");
                                a2.showAndWait();

                         
                                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                                    Security sc = (Security) util.Utility.getUsers().getNode(i).data;
                                    if (sc.getUser().equals(s.getStudentID())) {
                                        txt.removeElement("Users.txt", sc);
                                    }
                                }

                            }

                       

                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("No se puede eliminar este estudiante\n Este estudiante ya hizo un proceso de matricula");
                        a.showAndWait();
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay estudiantes registrados\n Registre primero un estudiante");
                    a.showAndWait();
                }

////            } catch (NullPointerException es) {
////                Alert a = new Alert(Alert.AlertType.ERROR);
////                a.setHeaderText("Error inesperado");
////                a.showAndWait();
            } catch (NumberFormatException en) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Ingrese numeros en los campos que solo lo requieran");
                a.showAndWait();
            } catch (ListException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No hay estudiantes registrados");
                a.showAndWait();
            }
//     
        }
    }

    @FXML
    private void numericCel(KeyEvent event) {
        textFieldCedula.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldCedula.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void initialize(KeyEvent event) {
    }

    @FXML
    private void comboStudent(ActionEvent event) {
        if (comboStudent.getSelectionModel().getSelectedIndex() != -1) { // evitar errores cuando se activa el evento del comboBox, porque tomaba datos vacios
//            textFieldLastName.setText(comboStudent.getSelectionModel().getSelectedItem().getFirstname());// agregamos al textField el LastName del estudiante seleccionado
//            textFieldCedula.setText(comboStudent.getSelectionModel().getSelectedItem().getId() + "");
//
//            textFieldCedula.setVisible(true);
//            textFieldLastName.setVisible(true);
//            txtFirst.setVisible(true);
//            txtId.setVisible(true);
            btnRemover.setVisible(true);
        }
    }
}
