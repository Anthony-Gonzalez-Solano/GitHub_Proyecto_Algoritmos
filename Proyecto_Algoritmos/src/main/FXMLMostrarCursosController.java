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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLMostrarCursosController implements Initializable {

    @FXML
    private TableView<Course> tableViewCursos;
    @FXML
    private Text txtError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(this.tableViewCursos.getColumns().isEmpty()){
         TableColumn<Course,String>column1=new TableColumn<>("Id");
            column1.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<Course,String>column2=new TableColumn<>("Nombre");
            column2.setCellValueFactory(new PropertyValueFactory<>("name"));
             TableColumn<Course,String>column3=new TableColumn<>("Creditos");
            column3.setCellValueFactory(new PropertyValueFactory<>("credits"));
             TableColumn<Course,String>column4=new TableColumn<>("ID Carrera");
            column4.setCellValueFactory(new PropertyValueFactory<>("careerID"));
           
       
            
            this.tableViewCursos.getColumns().add(column1);//agregar columnas
            this.tableViewCursos.getColumns().add(column2);
              this.tableViewCursos.getColumns().add(column3);
               this.tableViewCursos.getColumns().add(column4);
}
        //}
           try{
        while(!this.tableViewCursos.getItems().isEmpty()){
            this.tableViewCursos.getItems().remove(0);
        }
        for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
            this.tableViewCursos.getItems().add((Course) util.Utility.getCursos().getNode(i).data);
            
        }
    } catch (ListException ex) {
       txtError.setText("Lista Vacia");
    }
        catch(NullPointerException eda){
            txtError.setVisible(true);
            this.txtError.setText("Error");
        }
              }
    }    
    

