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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLMostrarEstudianteController implements Initializable {

    private List<String> list;
    @FXML
    private TableView<Student> tableViewEstudiante;

    @FXML
    private TableView<List<String>> tableStudent;

    @FXML
    private TableColumn<List<String>, String> columnCedula;
    @FXML
    private TableColumn<List<String>, String> columnLast;
    @FXML
    private TableColumn<List<String>, String> columnFirst;
    @FXML
    private TableColumn<List<String>, String> columnBirthday;
    @FXML
    private TableColumn<List<String>, String> columnPhone;
    @FXML
    private TableColumn<List<String>, String> columnEmail;
    @FXML
    private TableColumn<List<String>, String> columnAdress;
    @FXML
    private TableColumn<List<String>, String> columnCarrera;
    @FXML
    private TableColumn<List<String>, String> columnEstudiante;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        columnCedula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
        columnEstudiante.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
        columnLast.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
        columnFirst.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
        columnBirthday.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
        columnPhone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));
        columnEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(6)));
        columnAdress.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(7)));
        columnCarrera.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(8)));

        try {

            for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                list = new ArrayList<>();
                Student s = (Student) util.Utility.getEstudiantes().getNode(i).data;// casteamos para obtener los datos de la lista estudiantes

                list.add(s.getId() + "");//agregamos en el primer indice el ID
                list.add(s.getStudentID() + "");//agregamos en el segundo indice el student ID
                list.add(s.getLastname() + "");//agregamos en el tercer indice el lastName
                list.add(s.getFirstname() + "");//agregamos en el cuarto indice el firstName
                list.add(util.Utility.dateFormat(s.getBirthday()) + "");//agregamos en el quinto indice la fecha de nacimiento
                list.add(s.getPhoneNumber() + "");//agregamos en el sexto indice el telefono
                list.add(s.getEmail() + "");//agregamos en el septimo indice el Email
                list.add(s.getAddress() + "");//agregamos en el octavo indice la direccion
                for (int j = 1; j <= util.Utility.getCarreras().size(); j++) {
                    Career c = (Career) util.Utility.getCarreras().getNode(j).data;
                    if (s.getCareerID() == c.getId()) { // si el Id de carrera que se escoje en el estudiante es igual al id de carrera se agrega a la lista en el ultimo indice la descripcion de la carrera
                        list.add(c.getDescription() + "");//agregamos en el noveno indice la descripcion de la carrera
                    }
                }

                tableStudent.getItems().add(list);// se agregan los datos a la tabla
            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(" La lista esta vacia");
            a.showAndWait();
        } catch (NullPointerException eda) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
        }

//    }
    }
}
