/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import domain.Course;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLMostrarCursosController implements Initializable {

    private List<String> list;

    @FXML
    private TableView<List<String>> tableCourse; //tabla de tipo List String
    @FXML
    private TableColumn<List<String>, String> table1;
    @FXML
    private TableColumn<List<String>, String> table2;
    @FXML
    private TableColumn<List<String>, String> table3;
    @FXML
    private TableColumn<List<String>, String> table4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table1.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(0))); // obtener los valores por indices
        table2.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(1)));
        table3.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(2)));
        table4.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(3)));
        try {

            for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                list = new ArrayList<>();
                Course c1 = (Course) util.Utility.getCursos().getNode(i).data;// casteamos para obtener los datos de la lista Curso

                list.add(c1.getId() + ""); //agregamos en el primer indice el ID
                list.add(c1.getName() + "");// En el segundo agregamos el nombre
                list.add(c1.getCredits() + "");// En tercero agregamos los creditos del curso

                for (int j = 1; j <= util.Utility.getCarreras().size(); j++) { //recorremos lista carreras
                    Career c = (Career) util.Utility.getCarreras().getNode(j).data;//casteamos para obtener los datos de la lista carreras
                    if (c1.getCareerID() == c.getId()) { // si el Id de carrera que se escoje en el curso es igual al id de carrera se agrega a la lista en el ultimo indice la descripcion de la carrera
                        list.add(c.getDescription());

                    }

                }

                tableCourse.getItems().add(list); // se agregan los datos a la tabla
            }
        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("La lista esta vacia");
            a.showAndWait();
        } catch (NullPointerException eda) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
        }

    }
}
