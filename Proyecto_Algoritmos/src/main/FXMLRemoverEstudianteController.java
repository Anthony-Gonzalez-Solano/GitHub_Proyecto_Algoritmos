/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Enrollment;
import domain.Student;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private Text txtLastName;
    @FXML
    private Text txtCedula;
    @FXML
    private Button btnRemover;
    @FXML
    private Text txtMessage;
    @FXML
    private Text txtError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt = new FileTXT();
    }

    @FXML
    private void btnRemover(ActionEvent event) {

        if (textFieldCedula.getText().isEmpty() || textFieldLastName.getText().isEmpty()) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
            textFieldCedula.setText("");
            textFieldLastName.setText("");

        } else {

            Date date = null;
            Student s = new Student(Integer.parseInt(this.textFieldCedula.getText()), textFieldCedula.getText(), this.textFieldLastName.getText(), textFieldCedula.getText(), date, textFieldCedula.getText(), textFieldCedula.getText(), textFieldCedula.getText(), Integer.parseInt(textFieldCedula.getText()));

            try {
                boolean exist = false;
                boolean exist2 = false;
                boolean lastName = false;
                if (!util.Utility.getEstudiantes().isEmpty()) {

                    for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                        Student s2 = (Student) util.Utility.getEstudiantes().getNode(i).data;//casteamos para obtener los datos lista estudiantes
                        if (util.Utility.equals(s2, s)) { // si son iguales se iguala s al dato de la lista
                            s = (Student) util.Utility.getEstudiantes().getNode(i).data;
                        }
                        if (s2.getId() == s.getId()) {// compara los id para ver si existen
                            exist2 = true;
                        }
                        if (s2.getLastname().equalsIgnoreCase(textFieldLastName.getText())) { //compara los nombres para ver si existen
                            lastName = true;
                        }
                            for (int j = 1; j <= util.Utility.getMatriculas().size(); j++) {
                                Enrollment e = (Enrollment) util.Utility.getMatriculas().getNode(j).data;
                                if (e.getiD() == s.getId()) {
                                    exist = true;
                                }
                            }
                    }

                    if (exist == false) {
                        if (exist2 == true && lastName == true) {

                            util.Utility.getEstudiantes().remove(s);

                            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                            a.setHeaderText("Estudiante eliminado correctamente");
                            a.showAndWait();

                            textFieldCedula.setText("");
                            textFieldLastName.setText("");

                            txt.removeElement("estudiantes.txt", s.secondToString());// se remueve en el TXT
                        } else {
                            if (exist2 == true && lastName == false) {// Existe el id , pero no con el nombre ingresado
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setHeaderText("No se puede eliminar este estudiante.\nHay alguien registrado con este id, pero no con el nombre ingresado");
                                a.showAndWait();
                            }
                            if (exist2 == false && lastName == true) {// Existe el nombre , pero no con el id ingresado
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setHeaderText("No se puede eliminar este estudiante.\nHay alguien registrado con este nombre, pero no con el Id ingresado");
                                a.showAndWait();
                            }
                            if (exist2 == false && lastName == false) {// No existe el id ni el nombre
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setHeaderText("No se puede eliminar este estudiante.\nNo hay ningun estudiante registrado con este nombre y ID");
                                a.showAndWait();
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

            } catch (NullPointerException es) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado");
                a.showAndWait();
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
}
