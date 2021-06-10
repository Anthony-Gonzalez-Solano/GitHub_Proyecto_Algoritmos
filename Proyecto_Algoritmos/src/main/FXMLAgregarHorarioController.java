/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Career;
import domain.Course;
import domain.TimeTable;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLAgregarHorarioController implements Initializable {

    @FXML
    private ComboBox<String> CB_Date_Schedule1;
    @FXML
    private ComboBox<String> CB_HourStar_Schedule1;
    @FXML
    private ComboBox<String> CB_HourDnd_Schedule1;
    @FXML
    private ComboBox<Career> CB_Carrer;
    @FXML
    private ComboBox<Course> CB_Course;
    @FXML
    private ComboBox<String> CB_Date_Schedule2;
    @FXML
    private ComboBox<String> CB_HourStar_Schedule2;
    @FXML
    private ComboBox<String> CB_HourEnd_Schedule2;
    @FXML
    private Button BTN_aceptar;
    @FXML
    private ComboBox<String> CB_Cicle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //cargamos las careras
            for (int i = 1; i <= util.Utility.getCarreras().size(); i++) {
                CB_Carrer.getItems().add((Career)util.Utility.getCarreras().getNode(i).data);
            }
        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No hay carreras registradas");
            a.showAndWait();
        }
        //cargamos los combobox
        CB_Date_Schedule1.getItems().addAll("Lunes - L","Martes - M","Miercoles - K","Jueves - J","Viernes - V");
        CB_Date_Schedule2.getItems().addAll("Lunes - L","Martes - M","Miercoles - K","Jueves - J","Viernes - V");
        CB_HourStar_Schedule1.getItems().addAll("7,AM","8,AM","9,AM","10,AM","11,AM","12,PM","1,PM","2,PM","3,PM","4,PM","5,PM","6,PM","7,PM","8,PM","9,PM");
        CB_HourStar_Schedule2.getItems().addAll("7,AM","8,AM","9,AM","10,AM","11,AM","12,PM","1,PM","2,PM","3,PM","4,PM","5,PM","6,PM","7,PM","8,PM","9,PM");
        CB_HourDnd_Schedule1.getItems().addAll("7,AM","8,AM","9,AM","10,AM","11,AM","12,PM","1,PM","2,PM","3,PM","4,PM","5,PM","6,PM","7,PM","8,PM","9,PM");
        CB_HourEnd_Schedule2.getItems().addAll("7,AM","8,AM","9,AM","10,AM","11,AM","12,PM","1,PM","2,PM","3,PM","4,PM","5,PM","6,PM","7,PM","8,PM","9,PM");
        CB_Cicle.getItems().addAll("Ciclo I","Ciclo II","Ciclo III");
    }    

    @FXML
    private void CB_CareerSelect(ActionEvent event) {
        CB_Course.getItems().clear();
        int id = 0;
        if(CB_Carrer.getSelectionModel().getSelectedItem()!=null){
            id = CB_Carrer.getSelectionModel().getSelectedItem().getId();
        } // obetenemos el id de carrera selecionado
        try {
            for (int i = 1; i <= util.Utility.getCursos().size(); i++) { // caragamos  los cursos que esten en la carrera que no tengan horarios
                Course c = (Course)util.Utility.getCursos().getNode(i).data;
                if(c.getCareerID()==id){
                    boolean tf=false;
                    if(!util.Utility.getHorarios().isEmpty()){
                        for (int j = 1; j <= util.Utility.getHorarios().size(); j++) {
                        TimeTable tt = (TimeTable)util.Utility.getHorarios().getNode(j).data;
                        if(c.getId().equals(tt.getCourseID()))
                            tf=true;
                    }
                    }
                    if(tf==false)
                        CB_Course.getItems().add((Course)util.Utility.getCursos().getNode(i).data);
                }
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLAgregarHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(CB_Course.getItems() == null){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("La carrera seleccionada no posee cursos");
            a.setContentText("Seleccione otra carrera o\n agrege un curso a la misma");
            a.showAndWait();
        }
    }

    @FXML
    private void BTN_aceptar(ActionEvent event) {
        
        if(CB_Carrer.getSelectionModel().getSelectedItem()!=null && CB_Course.getSelectionModel().getSelectedItem()!=null && CB_Cicle.getSelectionModel().getSelectedItem()!= null){
            if(CB_Date_Schedule1.getSelectionModel().getSelectedItem()!=null && CB_HourStar_Schedule1.getSelectionModel().getSelectedItem()!=null && CB_HourDnd_Schedule1.getSelectionModel().getSelectedItem()!=null && 
            CB_Date_Schedule2.getSelectionModel().getSelectedItem()!=null && CB_HourStar_Schedule2.getSelectionModel().getSelectedItem()!=null && CB_HourEnd_Schedule2.getSelectionModel().getSelectedItem()!=null){
                 // revisamos que todo esta correctamente seleccionado   
                String schedule1 = dayswitch(CB_Date_Schedule1.getSelectionModel().getSelectedItem())+"-"+CB_HourStar_Schedule1.getSelectionModel().getSelectedItem()+"-"+
                CB_HourDnd_Schedule1.getSelectionModel().getSelectedItem();
                //usamos el metodo datswitch para crear l string de horarios
                String schedule2 = dayswitch(CB_Date_Schedule2.getSelectionModel().getSelectedItem())+"-"+CB_HourStar_Schedule2.getSelectionModel().getSelectedItem()+"-"+
                CB_HourEnd_Schedule2.getSelectionModel().getSelectedItem();
                
                TimeTable tt = new TimeTable(CB_Course.getSelectionModel().getSelectedItem().getId(), CB_Cicle.getSelectionModel().getSelectedItem(), 
                    schedule1, schedule2);
                boolean register = false;
                try {
                   if(!util.Utility.getHorarios().isEmpty()){
                        for (int i = 1; i <= util.Utility.getHorarios().size(); i++) {
                        TimeTable tt2 = (TimeTable)util.Utility.getHorarios().getNode(i).data;
                        if(tt.getCourseID().equals(tt2.getCourseID())){
                            register = true;
                        }
                    }
                   }
                } catch (ListException ex) {
                    Logger.getLogger(FXMLAgregarHorarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(register==false){ //si no esta registrado confirmamos que no haya choques de horario
                    if(!confirm(schedule1, schedule2)){//si no hay choques, registramos el horarios
                        util.Utility.getHorarios().add(tt);
                        FileTXT txt = new FileTXT();
                        txt.writeFile("horarios.txt", tt.toString());

                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("Horario registrado Correctamente");
                        a.showAndWait();   
                        
                        CB_Carrer.getSelectionModel().clearSelection();
                        CB_Course.getSelectionModel().clearSelection();
                        CB_Cicle.getSelectionModel().clearSelection();
                        CB_Date_Schedule1.getSelectionModel().clearSelection();
                        CB_Date_Schedule2.getSelectionModel().clearSelection();
                        CB_HourStar_Schedule1.getSelectionModel().clearSelection();
                        CB_HourStar_Schedule2.getSelectionModel().clearSelection();
                        CB_HourDnd_Schedule1.getSelectionModel().clearSelection();
                        CB_HourEnd_Schedule2.getSelectionModel().clearSelection();
                    }
                }else{
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("El Curso ya cuenta con un horario");
                    a.showAndWait(); 
                }
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No debe dejar espacios en blanco");
                a.setContentText("revise los espacios de las Opciones de Horario");
                a.showAndWait();   
            }
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar espacios en blanco");
            a.setContentText("revise los espacios de Carrera,\nCurso y Ciclo");
            a.showAndWait();
        }
    }
    
    public boolean confirm(String s1, String s2){
        boolean shock = false;
        String[] sc1 = s1.split("-"); //hacemos explit para tener los distintos aspectos del horario
        String[] sc2 = s2.split("-");
        
        String[] aux = sc2[1].split(",");              //rebisamos si las horas son de la tarde o mañana
        int S2star = Integer.parseInt(aux[0]);
        if(aux[1].equals("PM") && !"12".equals(aux[0])) 
            S2star = S2star*100;

        aux = sc2[2].split(",");
        int S2end = Integer.parseInt(aux[0]);
        if(aux[1].equals("PM") && !"12".equals(aux[0])) 
            S2end = S2end*100;

        aux = sc1[1].split(",");
        int S1star = Integer.parseInt(aux[0]);
        if(aux[1].equals("PM") && !"12".equals(aux[0])) 
            S1star = S1star*100;

        aux = sc1[2].split(",");
        int S1end = Integer.parseInt(aux[0]);
        if(aux[1].equals("PM") && !"12".equals(aux[0])) 
            S1end = S1end*100;

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("La hora de inicio, no puede ser mas tarde \na la misma hora, que la hora de salida");
        Alert b = new Alert(Alert.AlertType.ERROR);
        b.setHeaderText("hay un choque entre los horarios");
        if(sc1[0].equals(sc2[0])){   
            if(S2star <= S1star && S1star <= S2end){ // revisamos los casos de choques
                shock=true;}
            if(S2star <= S1end && S1end <= S2end){
                shock=true;}
            if(S1star <= S2star && S2star <= S1end){
                shock=true;}
            if(S1star <= S2end && S2end <= S1end){
                shock=true;}
            if(S1star>=S1end){
                shock=true;
                a.showAndWait();
                }
            if(S2star>=S2end){
                shock=true;
                a.showAndWait();
            }
        }else{
            if(S1star>S1end){
                shock=true;
                a.showAndWait();
                }
            if(S2star>S2end){
                shock=true;
                a.showAndWait();
            }  
        }
        if(shock==true)
            b.showAndWait();
        return shock; // si no hay choques retorna false
    }
    public String dayswitch(String s){
        switch(s){
            case "Miercoles":
                s = "K";
                break;
            case "Martes":
                s = "M";
                break;
            default:
                s = s.substring(0, 1);
        }
        return s;
    }
}
