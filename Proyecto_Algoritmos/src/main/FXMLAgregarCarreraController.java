/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.DoublyLinkedList;
import Lists.ListException;
import domain.Career;
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLAgregarCarreraController implements Initializable {

    public static DoublyLinkedList list = new DoublyLinkedList();
    private Career career;
    private String description;
    private int id;
    private util.FileTXT txt;
    @FXML
    private TextField textFieldDescription;
    @FXML
    private TextField textFieldId;
    @FXML
    private Text txtMessage;
    @FXML
    private Button btnAgregar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt = new FileTXT(); // crear txt

    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        if (textFieldDescription.getText().isEmpty() || textFieldId.getText().isEmpty()) { // si algun campo de texto esta vacio, mandara una alerta

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
            textFieldDescription.setText("");
            textFieldId.setText("");

        }else if(util.Utility.getCarreras().isEmpty()){
            Career c = new Career(this.textFieldDescription.getText(), Integer.parseInt(this.textFieldId.getText()));
             util.Utility.getCarreras().add(c);
            txt.writeFile("carreras.txt", c.secondToString());// escribimos en los txt
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Carrera agregada correctamente");
            a.showAndWait();
            textFieldId.setText("");
            this.textFieldDescription.setText("");
        } 
        else {

            try {

                Career c = new Career(this.textFieldDescription.getText(), Integer.parseInt(this.textFieldId.getText())); // creamos objeto tipo carrera, que va a tener los valores del textfield que se ingresen
                if (util.Utility.getCarreras().contains(c) == false) { // si una carrera no esta agregada seguira el codigo, sino una alerta
                    boolean exist = false; //variable para verificar si existe una carrera con un ID especifico
                    boolean exist2 = false;//variable para verificar si existe una carrera con un nombre especifico
                    for (int i = 1; i <= util.Utility.getCarreras().size(); i++) { //recorremos la lista de carreras
                        Career c2 = (Career) util.Utility.getCarreras().getNode(i).data;  // casteamos para tener acceso a los datos de las carreras
                        if (Integer.parseInt(textFieldId.getText()) == c2.getId()) {// validamos si el id ingresado existe
                            exist = true;
                        }
                        if (textFieldDescription.getText().equalsIgnoreCase(c2.getDescription())) {// validamos si el nombre ingresado existe
                            exist2 = true;
                        }

                    }
                    if (exist == false && exist2 == false) { // si no existen se agregan
                        util.Utility.getCarreras().add(c);
                        txt.writeFile("carreras.txt", c.secondToString());// escribimos en los txt
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("Carrera agregada correctamente");
                        a.showAndWait();
                        textFieldId.setText("");
                        this.textFieldDescription.setText("");
                    } else {   // errores al ingresar una carrera
                        if (exist == true) {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("El Id ingresado ya existe para una carrera\n Ingrese uno nuevo");
                            a.showAndWait();
                            textFieldId.setText("");
                            this.textFieldDescription.setText("");
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("La carrera ingresa ya existe\n Ingrese una nueva carrera");
                            a.showAndWait();
                            textFieldId.setText("");
                            this.textFieldDescription.setText("");
                        }
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("La carrera ya esta agregada\n Ingrese una nueva carrera");
                    a.showAndWait();
                    textFieldDescription.setText("");
                    textFieldId.setText("");
                }

            } catch (NumberFormatException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Ingrese solo numeros en los campos correspondientes");
                a.showAndWait();
                textFieldDescription.setText("");
                textFieldId.setText("");

            } catch (NullPointerException ep) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado");
                a.showAndWait();
                textFieldDescription.setText("");
                textFieldId.setText("");
            } catch (ListException ex) {
//                Alert a = new Alert(Alert.AlertType.ERROR);
//                a.setHeaderText("La lista esta vacia");
//                a.showAndWait();
//                textFieldDescription.setText("");
//                textFieldId.setText("");
            }
        }
    }

    @FXML
    private void numeric(KeyEvent event) {
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
}
