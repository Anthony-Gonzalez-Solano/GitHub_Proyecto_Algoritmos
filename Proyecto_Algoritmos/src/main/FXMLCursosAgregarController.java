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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private Text txtError;
    @FXML
    private Text txtMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txt = new FileTXT();// creamos txt
        try {

            for (int i = 1; i <= util.Utility.getCarreras().size(); i++) { //recorremos la lista de carreras

                comboCursos.getItems().add((Career) util.Utility.getCarreras().getNode(i).data);// casteamos para poder añadir al comboBox las carreas existentes

            }

        } catch (ListException ex) { //errores de no estar llena 
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
        if (textFieldId.getText().isEmpty() || textFieldNombre.getText().isEmpty() || textFieldCreditos.getText().isEmpty() || comboCursos.getSelectionModel().isEmpty()) {//validaciones de campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else if(util.Utility.getCursos().isEmpty()){
            Course c = new Course(this.textFieldId.getText(), this.textFieldNombre.getText(), Integer.parseInt(this.textFieldCreditos.getText()), comboCursos.getSelectionModel().getSelectedItem().getId());//creamos objeto tipo curso
                
             util.Utility.getCursos().add(c);

            this.txt.writeFile("cursos.txt", c.secondToString());// escribimos en los txt
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Curso agregado correctamente");
            a.showAndWait();
            textFieldCreditos.setText("");
            textFieldId.setText("");
            textFieldNombre.setText("");
            comboCursos.getSelectionModel().clearSelection();//limpiamos
        }else{    
            try {
             
                Course c = new Course(this.textFieldId.getText(), this.textFieldNombre.getText(), Integer.parseInt(this.textFieldCreditos.getText()), comboCursos.getSelectionModel().getSelectedItem().getId());//creamos objeto tipo curso
                boolean exist = false;  // id 
                boolean exist2 = false;// variables para validar si existe un curso igual al ingresado(Nombre)
                if (util.Utility.getCursos().contains(c) == false) {// si no hay ninguno recorre el ciclo for
                    for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
                        Course s2 = (Course) util.Utility.getCursos().getNode(i).data;
                        if (textFieldId.getText().equalsIgnoreCase(s2.getId())) {// validamos
                            exist = true;
                        }
                        if (textFieldNombre.getText().equalsIgnoreCase(s2.getName())) {//validamos
                            exist2 = true;
                        }

                    }
                    if (exist == false && exist2 == false) {// si no hay ninguno igual lo agrega
                        util.Utility.getCursos().add(c);

                        this.txt.writeFile("cursos.txt", c.secondToString());// escribimos en los txt
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("Curso agregado correctamente");
                        a.showAndWait();
                        textFieldCreditos.setText("");
                        textFieldId.setText("");
                        textFieldNombre.setText("");
                        comboCursos.getSelectionModel().clearSelection();//limpiamos

                    } else { // si existe el ID en otro curso se mandara una alerta
                        if (exist == true && exist2 == false) {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("El id del curso a agregar ya existe para otro curso\n Ingrese uno nuevo");
                            a.showAndWait();
                        }
                        if (exist == false && exist2 == true) {//  si existe el curso, mandara alerta
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("El   curso a agregar, ya existe pero con Id diferente\n Ingrese uno nuevo");
                            a.showAndWait();
                        }
                    }

                } else { // si existe el curso con el mismo nombre, id
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

    @FXML
    private void numericCredits(KeyEvent event) {
        textFieldCreditos.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldCreditos.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
         });
        
    }
}
