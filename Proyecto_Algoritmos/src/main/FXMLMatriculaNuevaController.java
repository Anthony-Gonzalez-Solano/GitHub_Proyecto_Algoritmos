/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import domain.Course;
import domain.Enrollment;
import domain.Student;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
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
    private TableView<Course> tableView;
    @FXML
    private Button btnEnrollment;

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

            for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
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
    private void cBoxStud(ActionEvent event) throws ListException {
    putTxt.setText("");
    cBoxStud.setVisible(false);
    tableView.setVisible(true);
    btnEnrollment.setVisible(true);
    studNum = (cBoxStud.getSelectionModel().getSelectedIndex())+1;
    Student studAux = (Student)util.Utility.getEstudiantes().getNode(studNum).data;
    if(this.tableView.getColumns().isEmpty()){
            TableColumn<Course,String>column1=new TableColumn<>("ID");
            column1.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<Course,String>column2=new TableColumn<>("Name");
            column2.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Course,String>column3=new TableColumn<>("Credits");
            column3.setCellValueFactory(new PropertyValueFactory<>("credits"));
            TableColumn<Course,String>column4=new TableColumn<>("CareerId");
            column4.setCellValueFactory(new PropertyValueFactory<>("careerid"));
            TableColumn<Course, Boolean> column5 = new TableColumn<>(""); 
            column5.setCellValueFactory(cell -> {
            Course p = cell.getValue();
            return new ReadOnlyBooleanWrapper();});
            column5.setCellFactory(CheckBoxTableCell.forTableColumn(column5));
            this.tableView.getColumns().add(column1);//agregar columnas
            this.tableView.getColumns().add(column2);
            this.tableView.getColumns().add(column3);
            this.tableView.getColumns().add(column4);
            this.tableView.getColumns().add(column5);
    }
    try{
        while(!this.tableView.getItems().isEmpty()){
            this.tableView.getItems().remove(0);
        }
        for (int i = 1; i <= util.Utility.getCursos().size(); i++) {
            this.tableView.getItems().add((Course) util.Utility.getCursos().getNode(i).data);
                Course c = (Course)util.Utility.getCursos().getNode(i).data;
                if(studAux.getCareerID()==c.getCareerID())
                    tableView.getItems().add((Course)util.Utility.getCursos().getNode(i).data);
        }
    } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(" La lista esta vacia");
            a.showAndWait();
    }
        catch(NullPointerException eda){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
        }
    }

    @FXML
    private void btnEnrollment(ActionEvent event) {
    }
    
}
