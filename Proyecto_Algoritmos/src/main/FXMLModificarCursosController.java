/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Course;
import domain.Student;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private Text txtNombre;
    @FXML
    private Text txtCredits;
    @FXML
    private ComboBox<Course> comboCursos;
    @FXML
    private Text txtId;
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
                if (!util.Utility.getCursos().isEmpty()) {

                    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                        Course c2 = (Course) util.Utility.getCursos().getNode(i).data;
                        if (c2.equals(comboCursos.getSelectionModel().getSelectedItem())) {
                            util.Utility.getCursos().getNode(i).data = c;

                        }
                    }
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

                    textFieldNombre.setText("");
                    textFieldCredits.setText("");
                    textFieldId.setText("");

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
            textFieldCredits.setText(comboCursos.getSelectionModel().getSelectedItem().getCredits() + "");
            textFieldId.setText(comboCursos.getSelectionModel().getSelectedItem().getId() + "");
            textFieldNombre.setText(comboCursos.getSelectionModel().getSelectedItem().getName() + "");
        }
    }

}
