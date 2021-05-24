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
public class FXMLAgregarCarreraController implements Initializable {

    public static DoublyLinkedList list = new DoublyLinkedList();
    private Career career;
    private String description;
    private int id;
    private util.FileTXT txt;
    @FXML
    private TextField textFieldDescription;
    @FXML
    private Text txtDescription;
    @FXML
    private TextField textFieldId;
    @FXML
    private Text txtId;
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
        txt = new FileTXT();

    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        if (textFieldDescription.getText().isEmpty() || textFieldId.getText().isEmpty()) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
            textFieldDescription.setText("");
            textFieldId.setText("");

        } else {

            try {

                Career c = new Career(this.textFieldDescription.getText(), Integer.parseInt(this.textFieldId.getText()));
                if (util.Utility.getCarreras().contains(c) == false ) {

                    util.Utility.getCarreras().add(c);
                    txt.writeFile("carreras.txt", c.toString());
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("Carrera agregada correctamente");
                    a.showAndWait();
                    textFieldId.setText("");
                    this.textFieldDescription.setText("");

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

            } catch (NullPointerException ep) {
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
