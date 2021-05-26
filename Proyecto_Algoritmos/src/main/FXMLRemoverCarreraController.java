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
import domain.Student;
import java.net.URL;
import java.util.ResourceBundle;
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
public class FXMLRemoverCarreraController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private Text txtDesciption;
    @FXML
    private Text txtMessage;
    @FXML
    private Button btnRemover;
    @FXML
    private TextField textFieldDescription;
    @FXML
    private TextField textFieldId;
    @FXML
    private Text txtId;
    @FXML
    private Text txtError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();

        // TODO
    }

    @FXML
    private void btnRemover(ActionEvent event) {

        if (textFieldDescription.getText().isEmpty() || textFieldId.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Career c = new Career(textFieldDescription.getText(), Integer.parseInt(textFieldId.getText()));
            try {
                boolean exist = false;
                boolean exist2 = false;
                if (!util.Utility.getCarreras().isEmpty()) {

                    for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
                        Career c2 = (Career) util.Utility.getCarreras().getNode(i).data;
                        if (c2.equals(c)) {
                            c = (Career) util.Utility.getCarreras().getNode(i).data;
                        }
                        if (Integer.parseInt(textFieldId.getText()) == c2.getId()) {
                            exist = true;
                        }
                        if (textFieldDescription.getText().equalsIgnoreCase(c2.getDescription())) {
                            exist2 = true;
                        }
                    }
                    if (exist == true && exist2 == true) {
                        util.Utility.getCarreras().remove(c);
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("Carrera eliminada correctamente");
                        a.showAndWait();
                        textFieldDescription.setText("");
                        textFieldId.setText("");

                        txt.removeElement("carreras.txt", c.secondToString());
                    } else {
                        if (exist == false && exist2 == false) {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("La carrera no esta registrada");
                            a.showAndWait();
                            textFieldId.setText("");
                            textFieldDescription.setText("");
                     
                        }
                        if (exist2 == true && exist == false) {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("Existe esta  carrera, pero no con el Id ingresado");
                            a.showAndWait();
                        }
                        if (exist == true && exist2 == false) {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("SI existe el iD, pero no con esta carrera asociada");
                            a.showAndWait();
                        }

                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("La lista  esta vacia\n Agregue primero una carrera");
                    a.showAndWait();
                    textFieldId.setText("");
                    textFieldDescription.setText("");

                }

            } catch (ListException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("La lista esta vacia");
                a.showAndWait();
                textFieldId.setText("");
                textFieldDescription.setText("");
            } catch (NumberFormatException es) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Ingrese solo numeros en los campos correspondientes");
                a.showAndWait();
                textFieldId.setText("");
                textFieldDescription.setText("");
            } catch (NullPointerException epx) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error inesperado. Intente de nuevo");
                a.showAndWait();
                textFieldId.setText("");
                textFieldDescription.setText("");
            }

        }
    }
}
