/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Career;
import java.net.URL;
import java.util.ResourceBundle;
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
public class FXMLRemoverCarreraController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnRemover(ActionEvent event) {
        
                     try{
        util.Utility.getCarreras().remove(new Career(this.textFieldDescription.getText(),Integer.parseInt(this.textFieldId.getText())));
                     this.txtMessage.setVisible(true);
                     this.txtMessage.setText("La carrera fue eliminada correctamente");
                 
                  
                     
                 
                 
                 
                   
                 
                
                }catch(Exception e){
                   //   this.txtMessage.setVisible(true);
                    this.txtMessage.setText("La lista no está llena");
                }
    }

    @FXML
    private void textFieldId(ActionEvent event) {
    }
    
}
