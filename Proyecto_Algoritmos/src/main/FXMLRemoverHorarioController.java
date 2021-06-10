/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Course;
import domain.Enrollment;
import domain.TimeTable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLRemoverHorarioController implements Initializable {

    private List<String> list;
    private TimeTable pTT;
    private int index;
    private FileTXT txt;
    @FXML
    private Button btnRemove;
    @FXML
    private TableView<List<String>> T_horarios;
    @FXML
    private TableColumn<List<String>, String> Tcol_course;
    @FXML
    private TableColumn<List<String>, String> Tcol_Cicle;
    @FXML
    private TableColumn<List<String>, String> Tcol_schedule1;
    @FXML
    private TableColumn<List<String>, String> Tcol_schedule2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        index=-1;
        txt = new FileTXT();
       Tcol_course.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
       Tcol_Cicle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
       Tcol_schedule1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
       Tcol_schedule2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
       
       if(util.Utility.getHorarios().isEmpty()){ // verificamos si hay horarios
           Alert a = new Alert(Alert.AlertType.ERROR);
           a.setHeaderText("no hay horarios registrados");
           a.showAndWait();
       }else{
           try {
               for (int i = 1; i <= util.Utility.getHorarios().size(); i++) { //si los hay lso cargamos en la tabla
                   list = new ArrayList<>();
                   TimeTable tt = (TimeTable)util.Utility.getHorarios().getNode(i).data;
                   for (int j = 1; j <= util.Utility.getCursos().size(); j++) {
                       Course c = (Course)util.Utility.getCursos().getNode(j).data;
                       if(tt.getCourseID().equals(c.getId())){
                           list.add(c.getName());
                       }
                   }
                   
                   list.add(tt.getPeriod());
                   list.add(tt.getSchedule1());
                   list.add(tt.getSchedule2());
                   T_horarios.getItems().add(list);
               }
           } catch (ListException ex) {
               Logger.getLogger(FXMLRemoverHorarioController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }    

    @FXML
    private void btnRemove(ActionEvent event) {
        if(!util.Utility.getHorarios().isEmpty()){
            boolean tf = false;
            if(index>=0){
                try {
                    if(!util.Utility.getMatriculas().isEmpty()){ // revisamos que el curso seleccionado, no tenga una matricula
                        for (int i = 1; i <= util.Utility.getMatriculas().size(); i++) {
                            Enrollment e = (Enrollment)util.Utility.getMatriculas().getNode(i).data;
                            if(e.getCourseID().equals(pTT.getCourseID())){
                                tf=true;
                            }
                        }
                    }
                } catch (ListException ex) {
                    Logger.getLogger(FXMLRemoverHorarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(tf==false){ //si no la tiene, se suprime el horario
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("¿Esta seguro que quiere remover el estudiante?");
                    ButtonType yes = new ButtonType("Sí");
                    ButtonType no = new ButtonType("No");
                    a.getButtonTypes().clear();
                    a.getButtonTypes().addAll(yes,no);

                    Optional<ButtonType> option = a.showAndWait(); 
                    if (option.get() == yes) {
                        try {
                            txt.removeElement("horarios.txt", pTT); //removemos del txt
                            util.Utility.getHorarios().remove(pTT);//removemos de la lista
                            T_horarios.getItems().remove(index-1);// y removemos de la tabla
                            index=-1;

                            a = new Alert(Alert.AlertType.CONFIRMATION);
                            a.setHeaderText("Se a rmovido el horario correctamente");
                            a.showAndWait(); 
                        } catch (ListException ex) {
                            Logger.getLogger(FXMLRemoverHorarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }else{
                   Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("NO se puede retirar el horario\n por que se ya se realizaron matriulas");
                    a.showAndWait(); 
                }
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Debe seleccionar un horario en la tabla");
                a.showAndWait();
            }
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("NO hay cursos con horarios");
            a.showAndWait();
        }
    }

    @FXML
    private void T_horarios(MouseEvent event) {
        index = T_horarios.getSelectionModel().getSelectedIndex()+1;
        //tomamos el indice de la tabla + 1, que corresponde al indice del mismo objeto en la lista
        try {
            if(index>0){
                pTT = (TimeTable)util.Utility.getHorarios().getNode(index).data;
                //cargamos el objeto para removerlo despues
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLRemoverHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
