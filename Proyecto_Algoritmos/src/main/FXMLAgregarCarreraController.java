/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.DoublyLinkedList;
import domain.Career;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
   public static DoublyLinkedList list=new DoublyLinkedList();
private Career career;
        private String description;
        private int id;
        private util.FileTXT txt ;
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
        txt=new FileTXT();
        
    }  
    @FXML
    private void btnAgregar(ActionEvent event) {
                             try{
                        Career c=new Career(this.textFieldDescription.getText(),Integer.parseInt(this.textFieldId.getText()));
           util.Utility.getCarreras().add(c);
                        this.textFieldDescription.setText("");
            txt.writeFile("carreras.txt",c.toString() );
                        this.txtMessage.setVisible(true);
                     
                        this.txtMessage.setText("Carrera agregada correctamente");
                           textFieldId.setText("");
                     
                           

                }catch(Exception e){
                      this.txtMessage.setVisible(true);
                    this.txtMessage.setText("Ha ocurrido un error");
                
             
                }
           }
    }
    
     

