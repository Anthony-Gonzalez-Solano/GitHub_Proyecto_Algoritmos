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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLRemoverCarreraController implements Initializable {
private static DoublyLinkedList list= new DoublyLinkedList();
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
        util.Utility.getCarreras();
       
        // TODO
    }    

    @FXML
    private void btnRemover(ActionEvent event) {
        
                     try{
                         
        util.Utility.getCarreras().remove(new Career(this.textFieldDescription.getText(),Integer.parseInt(this.textFieldId.getText())));
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Carrera agregada correctamente");
            a.showAndWait();
                     textFieldDescription.setText("");
                     textFieldId.setText("");
                }catch(ListException e){
             Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("La lista esta vacia");
            a.showAndWait();
                }catch(NumberFormatException es){
                       Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Ingrese solo numeros en los campos correspondientes");
            a.showAndWait();   
                    }
                    textFieldId.setText("");
                    textFieldDescription.setText("");
                }
    }   
