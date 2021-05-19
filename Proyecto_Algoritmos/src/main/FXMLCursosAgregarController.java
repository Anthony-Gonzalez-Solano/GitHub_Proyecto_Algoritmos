/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Course;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLCursosAgregarController implements Initializable {

    @FXML
    private ComboBox<?> comboCursos;
    @FXML
    private Button btnAgregar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void comboCursos(ActionEvent event) {
        
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        
        util.Utility.getCursos().add(new Course("", "", 0, 0));
    }
    
}
