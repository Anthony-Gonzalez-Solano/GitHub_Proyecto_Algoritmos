/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Course;
import domain.TimeTable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FXMLRemoverCursosController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private Button btnRemover;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt = new FileTXT();//creamos txt
    }

    @FXML
    private void btnRemover(ActionEvent event) {

        if (textFieldId.getText().isEmpty() || textFieldNombre.getText().isEmpty()) {// validamos campos vacios
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            try {
                boolean exist = false;// variale para verificar si existe un horario asociado al curso a remover
               
                Course c = new Course(textFieldId.getText(), textFieldNombre.getText(), 0, 0); // objeto curso  
                if (!util.Utility.getCursos().isEmpty()) { // si no esta vacia procede a hacer el ciclo y sino mandara error

                    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                        Course c2 = (Course) util.Utility.getCursos().getNode(i).data;

                        if (c2.equals(c)) {
                          c = (Course) util.Utility.getCursos().getNode(i).data;
                      
                        }
                        for (int j = 1; j <= util.Utility.getHorarios().size(); j++) {
                            TimeTable t = (TimeTable) util.Utility.getHorarios().getNode(j).data;
                            if (t.getCourseID().equalsIgnoreCase(c.getId())) {
                                exist = true;
                            }
                        }

                    }

                    if (exist == false) {
                        if (util.Utility.getCursos().contains(c) == true) {
                            txt.removeElement("cursos.txt", c.secondToString());
                            util.Utility.getCursos().remove(c);

                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setHeaderText("El curso ha sido eliminado corectamente");
                            a.showAndWait();
                            textFieldId.setText("");
                            textFieldNombre.setText("");
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("El curso ingresado no esta registrado");
                            a.showAndWait();
                        }
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Este curso no se puede eliminar\n Este curso ya tiene un horario establecido");
                        a.showAndWait();
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay cursos registrados\n Ingrese uno nuevo primero");
                    a.showAndWait();
                }

            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado");
                a.showAndWait();
            } catch (ListException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("La lista esta vacia");
                a.showAndWait();
            }
        }

    }
}
