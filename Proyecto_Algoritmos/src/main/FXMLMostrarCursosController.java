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
    private TableView<List<String>> tableCourse;
    @FXML
    private TableColumn<List<String>, String> table1;
    @FXML
    private TableColumn<List<String>, String> table2;
    @FXML
    private TableColumn<List<String>, String>table3;
    @FXML
    private TableColumn<List<String>, String> table4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table1.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(0)));
        table2.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(1)));
        table3.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(2)));
        table4.setCellValueFactory(d->new SimpleStringProperty(d.getValue().get(3)));
        try {
//            while (!this.tableStudent.getItems().isEmpty()) {
//                this.tableStudent.getItems().remove(0);
//            }
            for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                list = new ArrayList<>();
                Course c1 = (Course) util.Utility.getCursos().getNode(i).data;

                list.add(c1.getId() + "");
                list.add(c1.getName() + "");
                list.add(c1.getCredits() + "");
             
                for (int j = 1; j <= util.Utility.getCarreras().size(); j++) {
                    Career c = (Career) util.Utility.getCarreras().getNode(j).data;
                    if (c1.getCareerID() == c.getId()) {
                        list.add(c.getDescription());
                   
                    }
                  
                }

                tableCourse.getItems().add(list);
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
