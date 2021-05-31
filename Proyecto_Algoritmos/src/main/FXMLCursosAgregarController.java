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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FXMLCursosAgregarController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private ComboBox<Career> comboCursos;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldCreditos;
    @FXML
    private TextField textFieldId;
    @FXML
    private Text txtId;
    @FXML
    private Text txtNombre;
    @FXML
    private Text txtCredits;
    @FXML
    private Text txtError;
    @FXML
    private Text txtMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txt = new FileTXT();
        try {

            for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {

                comboCursos.getItems().add((Career)util.Utility.getCarreras().getNode(i).data );

            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

    @FXML
    private void comboCursos(ActionEvent event) {

    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        if (textFieldId.getText().isEmpty() || textFieldNombre.getText().isEmpty() || textFieldCreditos.getText().isEmpty() || comboCursos.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            try {
               // String combo[] = comboCursos.getSelectionModel().getSelectedItem().;
                Course c = new Course(this.textFieldId.getText(), this.textFieldNombre.getText(), Integer.parseInt(this.textFieldCreditos.getText()),comboCursos.getSelectionModel().getSelectedItem().getId());
                boolean exist = false;
                boolean exist2 = false;
                if (util.Utility.getCursos().contains(c) == false) {
                    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                        Course s2 = (Course) util.Utility.getCursos().getNode(i).data;
                        if (textFieldId.getText().equalsIgnoreCase(s2.getId())) {
                            exist = true;
                        }
                        if (textFieldNombre.getText().equalsIgnoreCase(s2.getName())) {
                            exist2 = true;
                        }

                    }
                    if (exist == false && exist2 == false) {
                   util.Utility.getCursos().add(c);
                    
                    this.txt.writeFile("cursos.txt", c.secondToString());
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("Curso agregado correctamente");
                    a.showAndWait();
                    textFieldCreditos.setText("");
                    textFieldId.setText("");
                    textFieldNombre.setText("");
                    comboCursos.getSelectionModel().clearSelection();
       
                    }else{
                        if(exist==true &&exist2==false){
                            Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("El id del curso a agregar ya existe para otro curso\n Ingrese uno nuevo");
                    a.showAndWait();
                        }
                        if(exist==false && exist2==true){
                                  Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("El   curso a agregar, ya existe pero con Id diferente\n Ingrese uno nuevo");
                    a.showAndWait();
                        }
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText(" El curso ingresado ya existe\n Ingrese uno nuevo");
                    a.showAndWait();
                }
            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(" Error inesperado");
                a.showAndWait();
            } catch (NumberFormatException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Formato incorrecto\n Coloque correctamente los numeros en los campos correspondientes");
                a.showAndWait();
            } catch (ListException ex) {
                         Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Lista Vacia");
                a.showAndWait();
            }

        }
    }
}
