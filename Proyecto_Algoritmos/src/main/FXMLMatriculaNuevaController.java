/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.CircularDoublyLinkedList;
import Lists.ListException;
import domain.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author ExtremeTech
 */
public class FXMLMatriculaNuevaController implements Initializable {
    private util.FileTXT txt;
    private CircularDoublyLinkedList list;
    private CircularDoublyLinkedList listRegistration;
    @FXML
    private ComboBox<Object> cboxListStud;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.add(txt.readFile("estudiantes.txt"));
        try {
            cboxListStud.getItems().addAll(list.getFirst());
        } catch (ListException ex) {
            Logger.getLogger(FXMLMatriculaNuevaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void cboxListStud(ActionEvent event) {
    }
    
}
