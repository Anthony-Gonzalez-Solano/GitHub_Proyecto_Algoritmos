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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLCursosAgregarController implements Initializable {
   private util.FileTXT txt ;
    @FXML
    private ComboBox<String> comboCursos;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldCreditos;
    @FXML
    private TextField textFieldId;
    @FXML
    private Text txtId;
    @FXML
    private Text txtNombre;
    @FXML
    private Text txtCredits;
    @FXML
    private Text txtError;
    @FXML
    private Text txtMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.txt=new FileTXT();
        try {
            
            for (int i = 1; i <=util.Utility.getCarreras().size(); i++) {
           
                comboCursos.getItems().add(util.Utility.getCarreras().getNode(i).data+""); 
         
            }
            
        } catch (ListException ex) {
            Logger.getLogger(FXMLCursosAgregarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void comboCursos(ActionEvent event) {
        
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        
        try{
              String combo [] =     comboCursos.getSelectionModel().getSelectedItem().split(",");
        Course c=new Course(this.textFieldId.getText(), this.textFieldNombre.getText(), Integer.parseInt(this.textFieldCreditos.getText()), Integer.parseInt(combo[1]));
        util.Utility.getCursos().add(c);
        
         txtMessage.setVisible(true);
         textFieldCreditos.setText("");
         textFieldId.setText("");
         textFieldNombre.setText("");
         
          this.txt.writeFile("cursos.txt", c.toString());
         
        }catch(NullPointerException e){
            txtError.setVisible(true);
            txtError.setText("Error");
        }
           catch(NumberFormatException e){
                      this.txtError.setVisible(true);
                    this.txtError.setText("Ha ocurrido un error");
                }
        
    }
    
}
