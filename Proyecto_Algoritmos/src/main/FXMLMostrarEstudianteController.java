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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
public class FXMLMostrarEstudianteController implements Initializable {

    private List<String> list;
    @FXML
    private TableView<Student> tableViewEstudiante;
    @FXML
    private Text txtMessage;
    @FXML
    private TableView<List<String>> tableStudent;

    @FXML
    private TableColumn<List<String>, String> columnCedula;
    @FXML
    private TableColumn<List<String>, String> columnLast;
    @FXML
    private TableColumn<List<String>, String> columnFirst;
    @FXML
    private TableColumn<List<String>, String> columnBirthday;
    @FXML
    private TableColumn<List<String>, String> columnPhone;
    @FXML
    private TableColumn<List<String>, String> columnEmail;
    @FXML
    private TableColumn<List<String>, String> columnAdress;
    @FXML
    private TableColumn<List<String>, String> columnCarrera;
    @FXML
    private TableColumn<List<String>, String> columnEstudiante;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        columnCedula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
        columnEstudiante.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
        columnLast.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
        columnFirst.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
        columnBirthday.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
        columnPhone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));
        columnEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(6)));
        columnAdress.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(7)));
        columnCarrera.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(8)));

//          if(this.tableViewEstudiante.getColumns().isEmpty()){
//             TableColumn<Student,String>column1=new TableColumn<>("Id");
//            column1.setCellValueFactory(new PropertyValueFactory<>("id"));
//            TableColumn<Student,String>column2=new TableColumn<>("ID Estudiante");
//            column2.setCellValueFactory(new PropertyValueFactory<>("studentID"));
//              TableColumn<Student,String>column3=new TableColumn<>("LastName");
//            column3.setCellValueFactory(new PropertyValueFactory<>("lastname"));
//                TableColumn<Student,String>column4=new TableColumn<>("FirstName");
//            column4.setCellValueFactory(new PropertyValueFactory<>("firstname"));
//                 TableColumn<Student,String>column5=new TableColumn<>("Fecha de nacimiento");
//            column5.setCellValueFactory(new PropertyValueFactory<>("birthday"));
//                 TableColumn<Student,String>column6=new TableColumn<>("Telefono");
//            column6.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
//                 TableColumn<Student,String>column7=new TableColumn<>("E-Mail");
//            column7.setCellValueFactory(new PropertyValueFactory<>("email"));
//                 TableColumn<Student,String>column8=new TableColumn<>("Direccion");
//            column8.setCellValueFactory(new PropertyValueFactory<>("address"));
////                 TableColumn<Student,String>column9=new TableColumn<>("ID Carrera");
////            column9.setCellValueFactory(new PropertyValueFactory<>("careerID"));
//       
//            
//            this.tableViewEstudiante.getColumns().add(column1);//agregar columnas
//            this.tableViewEstudiante.getColumns().add(column2);
//            this.tableViewEstudiante.getColumns().add(column3);
//            this.tableViewEstudiante.getColumns().add(column4);
//            this.tableViewEstudiante.getColumns().add(column5);
//            this.tableViewEstudiante.getColumns().add(column6);
//            this.tableViewEstudiante.getColumns().add(column7);
//            this.tableViewEstudiante.getColumns().add(column8);
//            this.tableViewEstudiante.getColumns().add(column9);
//
//     Career c=new Career(combo, 0)
        try {
//            while (!this.tableStudent.getItems().isEmpty()) {
//                this.tableStudent.getItems().remove(0);
//            }
            for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {
                list = new ArrayList<>();
                Student s = (Student) util.Utility.getEstudiantes().getNode(i).data;
                // this.tableViewEstudiante.getItems().add((Student) util.Utility.getEstudiantes().getNode(i).data);
//            if(i==util.Utility.getEstudiantes().size())
                list.add(s.getId() + "");
                list.add(s.getStudentID() + "");
                list.add(s.getLastname() + "");
                list.add(s.getFirstname() + "");
                list.add(s.getBirthday() + "");
                list.add(s.getPhoneNumber() + "");
                list.add(s.getEmail() + "");
                list.add(s.getAddress() + "");
                for (int j = 1; j <= util.Utility.getCarreras().size(); j++) {
                    Career c = (Career) util.Utility.getCarreras().getNode(j).data;
                    if (s.getCareerID() == c.getId()) {
                        list.add(c.getDescription() + "");
                    }
                }
//                tableStudent.getItems().add((Career)util.Utility.getCarreras().)
                tableStudent.getItems().add(list);
            }

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(" La lista esta vacia");
            a.showAndWait();
        } catch (NullPointerException eda) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
        }

//    }
    }
}
