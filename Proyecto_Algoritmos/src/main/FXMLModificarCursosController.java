/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Course;
import domain.Enrollment;
import domain.Student;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class FXMLModificarCursosController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private Button btnModificar;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldCredits;
    @FXML
    private Label txtNombre;
    @FXML
    private Label txtCredits;
    @FXML
    private ComboBox<Course> comboCursos;
    @FXML
    private Label txtId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        this.txt = new FileTXT();
        try {

            for (int i = 1; i <= util.Utility.getCursos().size(); i++) {

                comboCursos.getItems().add((Course) util.Utility.getCursos().getNode(i).data);

            }//recorremos la lista de cursos para agregarlas al comboBox, para poder modificarlas

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
            textFieldNombre.setText("");
            textFieldCredits.setText("");
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        if (textFieldNombre.getText().isEmpty() || textFieldCredits.getText().isEmpty()) {//validamos campos vacios
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Course c = new Course(comboCursos.getSelectionModel().getSelectedItem().getId(), textFieldNombre.getText(), Integer.parseInt(textFieldCredits.getText()), comboCursos.getSelectionModel().getSelectedItem().getCareerID());//creamos objeto tipo Course, donde se toman los valores de los textField y los comboBox
            try {
                boolean curso = false;
                if (!util.Utility.getCursos().isEmpty()) { // si no esta vacia la lista recorre el ciclo for

                    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                        Course c2 = (Course) util.Utility.getCursos().getNode(i).data;//casteamos para obtener los datos de la lista
                        if (c2.equals(comboCursos.getSelectionModel().getSelectedItem())) {// comparamos con el objeto del ComboBox
                            util.Utility.getCursos().getNode(i).data = c;

                        }
                        for (int j = 1; j <= util.Utility.getMatriculas().size(); j++) {//recorremos lista de matriculas
                            Enrollment e = (Enrollment) util.Utility.getMatriculas().getNode(j).data;// casteamos para obtener los datos de la lista
                            if (comboCursos.getSelectionModel().getSelectedItem().getId().equalsIgnoreCase(e.getCourseID())) {// condicion para si existe una matricula hecha con este curso
                                curso = true;
                            }
                        }
                    }
                    if (curso == false) { // si no existe una matricula lo modifica
                        txt.modifyFile("cursos.txt", comboCursos.getSelectionModel().getSelectedItem().secondToString(), c.secondToString());// modifica el txt
                        int x = comboCursos.getSelectionModel().getSelectedIndex();//obtener el indice
                        comboCursos.getItems().remove(x);//removemos el elemento
                        comboCursos.getItems().add(x, c); // se agrega en el indice el curso modificado
                        comboCursos.getSelectionModel().clearSelection();//limpiamos el comBox
                        textFieldNombre.setText("");
                        textFieldCredits.setText("");

                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("El curso ha sido mofificado correctamente");
                        a.showAndWait();
                        txtCredits.setVisible(false);
                        txtId.setVisible(false);
                        txtNombre.setVisible(false);

                        textFieldCredits.setVisible(false);
                        textFieldNombre.setVisible(false);
                        btnModificar.setVisible(false);
                        textFieldNombre.setText("");
                        textFieldCredits.setText("");

                    } else {//alerta de si ya hay un estudiante que hizo matricula con el curso a  modificar
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("No se puede modificar este curso.\nYa hay un estudiante que ha hecho un proceso de matricula con este curso");
                        a.showAndWait();
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("La lista de estudiantes esta vacia\nAgregue uno primero");
                    a.showAndWait();
                    textFieldNombre.setText("");
                    textFieldCredits.setText("");
                }
//            } catch (NullPointerException epe) {
//                Alert a = new Alert(Alert.AlertType.ERROR);
//                a.setHeaderText("Error inesperado");
//                a.showAndWait();
            } catch (ListException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Lista vacia");
                a.showAndWait();
            }
        }
    }

    @FXML
    private void comboCursos(ActionEvent event) {
        if (comboCursos.getSelectionModel().getSelectedIndex() != -1) { // evitar errores cuando se activa el evento del comboBox, porque tomaba datos vacios
            txtCredits.setVisible(true);

            txtNombre.setVisible(true);

            textFieldCredits.setVisible(true);
            textFieldNombre.setVisible(true);
            btnModificar.setVisible(true);
            textFieldCredits.setText(comboCursos.getSelectionModel().getSelectedItem().getCredits() + "");// agregamos al textField los creditos del curso seleccionado

            textFieldNombre.setText(comboCursos.getSelectionModel().getSelectedItem().getName() + "");//agregamos el nombre del curso al textField

        }
    }

    @FXML
    private void numericCredits(KeyEvent event) {
        textFieldCredits.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldCredits.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
         });
    }

}
