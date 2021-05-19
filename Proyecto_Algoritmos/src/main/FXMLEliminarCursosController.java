/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Course;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLEliminarCursosController implements Initializable {

    @FXML
    private Text txtEliminar;
    @FXML
    private TextField textFieldEliminar;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnEliminar(ActionEvent event) {
        try {
            util.Utility.getCursos().remove(new Course("", "", 0, 0));
        } catch (ListException ex) {
            Logger.getLogger(FXMLEliminarCursosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
