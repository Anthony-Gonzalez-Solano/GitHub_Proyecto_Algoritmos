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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML
    private Label txtId;
    @FXML
    private Label txtNombre;
    @FXML
    private ComboBox<Course> comboCursos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt = new FileTXT();//creamos txt
        try {

            for (int i = 1; i <= util.Utility.getCursos().size(); i++) {

                comboCursos.getItems().add((Course) util.Utility.getCursos().getNode(i).data);

            }//recorremos la lista de cursos para agregarlas al comboBox, para poder modificarlas

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();

        }
    }

    @FXML
    private void btnRemover(ActionEvent event) {

        if (comboCursos.getSelectionModel().getSelectedIndex() == -1) {// validamos campos vacios
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            try {
                boolean exist = false;// variale para verificar si existe un horario asociado al curso a remover

                Course c = (Course) comboCursos.getSelectionModel().getSelectedItem();
                if (!util.Utility.getCursos().isEmpty()) { // si no esta vacia procede a hacer el ciclo y sino mandara error

                    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {

                        if (!util.Utility.getHorarios().isEmpty()) {
                            for (int j = 1; j <= util.Utility.getHorarios().size(); j++) {
                                TimeTable t = (TimeTable) util.Utility.getHorarios().getNode(j).data;
                                if (t.getCourseID().equalsIgnoreCase(c.getId())) { //verificar  si existe un horario para este curso
                                    exist = true;
                                }
                            }
                        }
                    }

                    if (exist == false) {// si no existe un horario, se remueve

                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("¿Esta seguro que quiere remover el curso?");
                        ButtonType yes = new ButtonType("Sí");
                        ButtonType no = new ButtonType("No");
                        a.getButtonTypes().clear();
                        a.getButtonTypes().addAll(yes, no);

                        Optional<ButtonType> option = a.showAndWait();
                        if (option.get() == yes) {
                            txt.removeElement("cursos.txt", comboCursos.getSelectionModel().getSelectedItem().secondToString());// se remueve en el Txt
                            util.Utility.getCursos().remove(c);// se remueve
                            int x = comboCursos.getSelectionModel().getSelectedIndex();//obtener el indice
                            comboCursos.getItems().remove(x);//removemos el elemento
                            comboCursos.getSelectionModel().clearSelection();//limpiamos el comBox

                            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                            a2.setHeaderText("El curso ha sido eliminado corectamente");
                            a2.showAndWait();

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

    @FXML
    private void comboCursos(ActionEvent event) {
        if (comboCursos.getSelectionModel().getSelectedIndex() != -1) { // evitar errores cuando se activa el evento del comboBox, porque tomaba datos vacios

            btnRemover.setVisible(true);

        }
    }
}
