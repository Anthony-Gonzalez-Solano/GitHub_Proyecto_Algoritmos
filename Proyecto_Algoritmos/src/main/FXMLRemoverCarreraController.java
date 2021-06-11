/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.CircularDoublyLinkedList;
import Lists.DoublyLinkedList;
import Lists.ListException;
import domain.Career;
import domain.Course;
import domain.Student;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
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
public class FXMLRemoverCarreraController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private Button btnRemover;
    private TextField textFieldId;
    @FXML
    private ComboBox<Career> comboCarreras;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();
        btnRemover.setVisible(false);
        try {

            for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {

                comboCarreras.getItems().add((Career) util.Utility.getCarreras().getNode(i).data);

            } //recorremos la lista de carreras para agregarlas al comboBox, para poder suprimirlas

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }

        // TODO
    }

    @FXML
    private void btnRemover(ActionEvent event) {

        if (comboCarreras.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Career c = new Career(comboCarreras.getSelectionModel().getSelectedItem().getDescription(), comboCarreras.getSelectionModel().getSelectedItem().getId());
            try {
           
                boolean student = false;// verificar el id estudiante
                boolean career = false;// verificar id carrera de cursos
                if (!util.Utility.getCarreras().isEmpty()) {

                    for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
                        Career c2 = (Career) util.Utility.getCarreras().getNode(i).data;
                        if (c2.equals(c)) {
                            c = (Career) util.Utility.getCarreras().getNode(i).data;// si los datos son iguales, c se iguala al dato de la lista
                        }

                        if (!util.Utility.getCursos().isEmpty()) {
                            for (int j = 1; j <= util.Utility.getCursos().size(); j++) {
                                Course c3 = (Course) util.Utility.getCursos().getNode(j).data;
                                if (c3.getCareerID() == c.getId()) {// si el id de carrera en el curso seleccionado es igual al ingresado lo pone en verdadero 
                                    career = true;
                                }
                            }
                        }
                    }
                    if (!util.Utility.getEstudiantes().isEmpty()) {
                        for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                            Student s2 = (Student) util.Utility.getEstudiantes().getNode(i).data;
                            if (comboCarreras.getSelectionModel().getSelectedItem().getId() == s2.getCareerID()) {//verificar si hay un estudiante en esta carrera
                                student = true;
                            }
                        }
                    }
                    if (student == false) {
                        if (career == false) {

                            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                            a.setHeaderText("¿Esta seguro que quiere remover la carrera?");
                            ButtonType yes = new ButtonType("Sí");
                            ButtonType no = new ButtonType("No");
                            a.getButtonTypes().clear();
                            a.getButtonTypes().addAll(yes, no);

                            Optional<ButtonType> option = a.showAndWait();
                            if (option.get() == yes) {
                                util.Utility.getCarreras().remove(c);
                                   txt.removeElement("carreras.txt", comboCarreras.getSelectionModel().getSelectedItem().secondToString());
                                 System.out.println("dd: "+ comboCarreras.getSelectionModel().getSelectedItem().secondToString());
                                int x = comboCarreras.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                                comboCarreras.getItems().remove(x); // se remueve
                                comboCarreras.getSelectionModel().clearSelection();//limpiamos el comboBox


                                Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                a2.setHeaderText("Carrera eliminada correctamente");
                                a2.showAndWait();
//                
                             
                            }

                        }
                     else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("No se puede eliminar esta carrera.\n Esta carrera ya tiene cursos agregados");
                        a.showAndWait();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No se puede eliminar esta carrera\n Ya hay un estudiante registrado en esta carrera");
                    a.showAndWait();
                }
                }else{
                      Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No se puede eliminar esta carrera\n  No hay carreras agregadas");
                    a.showAndWait();
                }
            } catch (ListException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("La lista esta vacia");
                a.showAndWait();
      
            } catch (NumberFormatException es) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Ingrese solo numeros en los campos correspondientes");
                a.showAndWait();
           

            } catch (NullPointerException epx) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado. Intente de nuevo");
                a.showAndWait();
             

            }
        }
    }

    private void numericID(KeyEvent event) {
        textFieldId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void comboCarreras(ActionEvent event) {
        if (comboCarreras.getSelectionModel().getSelectedIndex() != -1) { // evitar errores cuando se activa el evento del comboBox, porque tomaba datos vacios

            btnRemover.setVisible(true);
          
        }
    }
}
