/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
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
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLModificarCarreraController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private ComboBox<Career> comboCarrera;
    @FXML
    private TextField textFieldModificar;
    @FXML
    private Button btnModificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txt = new FileTXT();
        try {

            for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {

                comboCarrera.getItems().add((Career) util.Utility.getCarreras().getNode(i).data);

            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
   

        if (textFieldModificar.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Debe ingresar una carrera para poder modificarla");
            a.showAndWait();
        } else {

            Career c = new Career(textFieldModificar.getText(), comboCarrera.getSelectionModel().getSelectedItem().getId());
            boolean career=false;
            try {
                if (!util.Utility.getCarreras().isEmpty()) {
                    for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
                        Career c2 = (Career) util.Utility.getCarreras().getNode(i).data;
                        if (c2.equals(comboCarrera.getSelectionModel().getSelectedItem())) {
                            util.Utility.getCarreras().getNode(i).data = c;

                        }
                        for (int j = 1; j <=util.Utility.getEstudiantes().size(); j++) {
                            Student s=(Student)util.Utility.getEstudiantes().getNode(i).data;
                              if(comboCarrera.getSelectionModel().getSelectedItem().getId()==(s.getCareerID())){
                                  career=true;
                            
                              }
                        }
                      
                    }
                    if(career==false){
                    txt.modifyFile("carreras.txt", comboCarrera.getSelectionModel().getSelectedItem().secondToString(), c.secondToString());
                    int x = comboCarrera.getSelectionModel().getSelectedIndex();
                    comboCarrera.getItems().remove(x);
                    comboCarrera.getItems().add(x, c);
                    comboCarrera.getSelectionModel().clearSelection();
                    textFieldModificar.setText("");

                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("La carrera ha sido mofificada correctamente");
                    a.showAndWait();
                    textFieldModificar.setVisible(false);
                    btnModificar.setVisible(false);
                    textFieldModificar.setText("");
                    }else{
                             Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No se puede modificar esta carrera.\n Ya existe un estudiante registrado en esta carrera");
                    a.showAndWait();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No se puede modificar ninguna carrera, porque no hay ninguna agregada\n Agregue una primero");
                    a.showAndWait();
                }
            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado");
                a.showAndWait();
            } catch (ListException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No hay carreras registradas");
                a.showAndWait();
            }
        }

    }

    @FXML
    private void comboCarrera(ActionEvent event) {
        if (comboCarrera.getSelectionModel().getSelectedIndex() != -1) {
            textFieldModificar.setVisible(true);
            btnModificar.setVisible(true);
            textFieldModificar.setText(comboCarrera.getSelectionModel().getSelectedItem() + "");
            
        }
    }

}
