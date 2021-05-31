/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLMostrarCarreraController implements Initializable {

    @FXML
    private TableView<Career> tableViewCarrera;
    @FXML
    private Text txtMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(this.tableViewCarrera.getColumns().isEmpty()){
         TableColumn<Career,String>column1=new TableColumn<>("Id"); // nombre de las columnas
            column1.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<Career,String>column2=new TableColumn<>("Carrera");
            column2.setCellValueFactory(new PropertyValueFactory<>("description"));
           
       
            
            this.tableViewCarrera.getColumns().add(column1);//agregar columnas
            this.tableViewCarrera.getColumns().add(column2);
               
}
        //}
           try{
        while(!this.tableViewCarrera.getItems().isEmpty()){
            this.tableViewCarrera.getItems().remove(0);
        }
        for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
            this.tableViewCarrera.getItems().add((Career) util.Utility.getCarreras().getNode(i).data);
            
        }
    } catch (ListException ex) {
          Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
    }
        catch(NullPointerException eda){
         Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
        }
          
    }
    }    
    

