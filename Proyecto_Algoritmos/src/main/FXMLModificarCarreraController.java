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
    this.txt=new FileTXT();
        try {
            
            for (int i = 1; i <=util.Utility.getCarreras().size(); i++) {
           
             comboCarrera.getItems().add((Career)util.Utility.getCarreras().getNode(i).data);
              
            }
       
            
        } catch (ListException ex) {
                 Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }    

    @FXML
    private void btnModificar(ActionEvent event) {
       String dato[]= textFieldModificar.getText().split(",");
        
Career c=new Career(dato[0], Integer.parseInt(dato[1]));
      
           try {
         for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
                            Career  c2 = (Career) util.Utility.getCarreras().getNode(i).data;
                          if(c2.equals(comboCarrera.getSelectionModel().getSelectedItem())){
                           util.Utility.getCarreras().getNode(i).data=c;
                          
                  
                          }
                        }
      int x=comboCarrera.getSelectionModel().getSelectedIndex();
      comboCarrera.getItems().remove(x);
      comboCarrera.getItems().add(x, c);
                 txt.modifyFile("carreras.txt", comboCarrera.getSelectionModel().getSelectedItem(), c.toString());
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("La carrera ha sido mofificada correctamente");
            a.showAndWait();
            
            textFieldModificar.setText("");
            textFieldModificar.clear();
            comboCarrera.getSelectionModel().clearSelection();
           }catch(NullPointerException e){
                 Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
           } catch (ListException ex) {
           Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No hay carreras registradas");
            a.showAndWait();
    }
        
       
        
    }
    
    @FXML
    private void comboCarrera(ActionEvent event) {
            textFieldModificar.setText(comboCarrera.getSelectionModel().getSelectedItem()+"");
    }
    
}
