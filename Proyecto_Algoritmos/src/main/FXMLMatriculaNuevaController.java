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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author ExtremeTech
 */
public class FXMLMatriculaNuevaController implements Initializable {
    private util.FileTXT txt;
    private int studNum;
    private ComboBox<String> cBoxStud2;
    @FXML
    private Text putTxt;
    @FXML
    private ComboBox<String> cBoxStud;
    @FXML
    private TableView<Student> tableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.txt = new FileTXT();
       putTxt.setText("Escoga estudiante");
       Student aux;
       String stud;
        try {

            for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
                aux = (Student)util.Utility.getEstudiantes().getNode(i).data;
                cBoxStud.getItems().add("Cedula:"+aux.getId()+",Carne:"+aux.getStudentID()+",Apellido:"+aux.getLastname()+",Nombre:"+aux.getFirstname());
            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }    

    @FXML
    private void cBoxStud(ActionEvent event) {
    putTxt.setText("");
    cBoxStud.setVisible(false);
    tableView.setVisible(true);
    studNum = cBoxStud.getSelectionModel().getSelectedIndex();
    if(this.tableView.getColumns().isEmpty()){
            TableColumn<Student,String>column1=new TableColumn<>("Curso");
            column1.setCellValueFactory(new PropertyValueFactory<>("curso"));
            TableColumn<Student,String>column2=new TableColumn<>("horario");
            column2.setCellValueFactory(new PropertyValueFactory<>("Horario"));
            TableColumn<Student,String>column3=new TableColumn<>("");
            column3.setCellValueFactory(new PropertyValueFactory<>("matricular"));
                column3.setCellFactory(ComboBoxTableCell.forTableColumn("matricular","no matricular"));
            this.tableView.getColumns().add(column1);//agregar columnas
            this.tableView.getColumns().add(column2);
            this.tableView.getColumns().add(column3);
}
    }
    
}
