/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import domain.Student;
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
public class FXMLMostrarEstudianteController implements Initializable {

    @FXML
    private TableView<Student> tableViewEstudiante;
    @FXML
    private Text txtMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
          if(this.tableViewEstudiante.getColumns().isEmpty()){
         TableColumn<Student,String>column1=new TableColumn<>("Id");
            column1.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<Student,String>column2=new TableColumn<>("ID Estudiante");
            column2.setCellValueFactory(new PropertyValueFactory<>(" studentID"));
              TableColumn<Student,String>column3=new TableColumn<>("LastName");
            column1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                TableColumn<Student,String>column4=new TableColumn<>("FirstName");
            column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                 TableColumn<Student,String>column5=new TableColumn<>("Fecha de nacimiento");
            column1.setCellValueFactory(new PropertyValueFactory<>("birthday"));
                 TableColumn<Student,String>column6=new TableColumn<>("Telefono");
            column1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                 TableColumn<Student,String>column7=new TableColumn<>("E-Mail");
            column1.setCellValueFactory(new PropertyValueFactory<>("email"));
                 TableColumn<Student,String>column8=new TableColumn<>("Direccion");
            column1.setCellValueFactory(new PropertyValueFactory<>("address"));
                 TableColumn<Student,String>column9=new TableColumn<>("ID Carrera");
            column1.setCellValueFactory(new PropertyValueFactory<>("careerID"));
       
            
            this.tableViewEstudiante.getColumns().add(column1);//agregar columnas
            this.tableViewEstudiante.getColumns().add(column2);
            this.tableViewEstudiante.getColumns().add(column3);
            this.tableViewEstudiante.getColumns().add(column4);
            this.tableViewEstudiante.getColumns().add(column5);
            this.tableViewEstudiante.getColumns().add(column6);
            this.tableViewEstudiante.getColumns().add(column7);
            this.tableViewEstudiante.getColumns().add(column8);
            this.tableViewEstudiante.getColumns().add(column9);
               
}
        //}
           try{
        while(!this.tableViewEstudiante.getItems().isEmpty()){
            this.tableViewEstudiante.getItems().remove(0);
        }
        for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
            this.tableViewEstudiante.getItems().add((Student) util.Utility.getEstudiantes().getNode(i).data);
            
        }
    } catch (ListException ex) {
       txtMessage.setText("Lista Vacia");
    }
        catch(NullPointerException eda){
            this.txtMessage.setText("Error");
        }
           tableViewEstudiante.setVisible(true);
    }
        
    }    
    

