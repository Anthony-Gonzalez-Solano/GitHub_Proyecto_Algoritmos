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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField textFieldId;

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

            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        if (textFieldNombre.getText().isEmpty() || textFieldCredits.getText().isEmpty() || textFieldId.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Course c = new Course(textFieldId.getText(), textFieldNombre.getText(), Integer.parseInt(textFieldCredits.getText()), comboCursos.getSelectionModel().getSelectedItem().getCareerID());
            try {
                boolean curso = false;
                if (!util.Utility.getCursos().isEmpty()) {

                    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                        Course c2 = (Course) util.Utility.getCursos().getNode(i).data;
                        if (c2.equals(comboCursos.getSelectionModel().getSelectedItem())) {
                            util.Utility.getCursos().getNode(i).data = c;

                        }
//                        for (int j = 1; j <= util.Utility.getMatriculas().size(); j++) {
//                            Enrollment e = (Enrollment) util.Utility.getMatriculas().getNode(i).data;
//                            if (comboCursos.getSelectionModel().getSelectedItem().getId().equalsIgnoreCase(e.getCourseID())) {
//                                curso = true;
//                            }
//                        }// No puede hacerse todavia
                    }
                    if (curso == false) {
                        txt.modifyFile("cursos.txt", comboCursos.getSelectionModel().getSelectedItem().secondToString(), c.secondToString());
                        int x = comboCursos.getSelectionModel().getSelectedIndex();
                        comboCursos.getItems().remove(x);
                        comboCursos.getItems().add(x, c);
                        comboCursos.getSelectionModel().clearSelection();
                        textFieldNombre.setText("");
                        textFieldCredits.setText("");
                        textFieldId.setText("");
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("El curso ha sido mofificado correctamente");
                        a.showAndWait();
                        txtCredits.setVisible(false);
                        txtId.setVisible(false);
                        txtNombre.setVisible(false);
                        textFieldId.setVisible(false);
                        textFieldCredits.setVisible(false);
                        textFieldNombre.setVisible(false);
                        btnModificar.setVisible(false);
                        textFieldNombre.setText("");
                        textFieldCredits.setText("");
                        textFieldId.setText("");
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("No se puede eliminar este curso.\nYa hay un estudiante que ha hecho un proceso de matricula con este curso");
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
    private void comboCursos(ActionEvent event) {
        if (comboCursos.getSelectionModel().getSelectedIndex() != -1) {
            txtCredits.setVisible(true);
            txtId.setVisible(true);
            txtNombre.setVisible(true);
            textFieldId.setVisible(true);
            textFieldCredits.setVisible(true);
            textFieldNombre.setVisible(true);
            btnModificar.setVisible(true);
            textFieldCredits.setText(comboCursos.getSelectionModel().getSelectedItem().getCredits() + "");
            textFieldId.setText(comboCursos.getSelectionModel().getSelectedItem().getId() + "");
            textFieldNombre.setText(comboCursos.getSelectionModel().getSelectedItem().getName() + "");

        }
    }

}
