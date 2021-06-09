/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Security;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLVentanaPrincipalController implements Initializable {

    @FXML
    private MenuItem MI_Carreras_Agregar;
    @FXML
    private MenuItem MI_Carreras_Modificar;
    @FXML
    private MenuItem MI_Carreras_Mostrar;
    @FXML
    private MenuItem MI_Estudiante_Agregar;
    @FXML
    private MenuItem MI_Estudiante_Modificarr;
    @FXML
    private MenuItem MI_Estudiante_Suprimir;
    @FXML
    private MenuItem MI_Estudiante_Mostrar;
    @FXML
    private MenuItem MI_Cursos_Agregar;
    @FXML
    private MenuItem MI_Cursos_Modificar;
    @FXML
    private MenuItem MI_Cursos_Suprimir;
    @FXML
    private MenuItem MI_Cursos_Mostrar;
    @FXML
    private MenuItem MI_Reportes_Matricula;
    @FXML
    private MenuItem MI_Matricula_Nueva;
    @FXML
    private MenuItem Mi_Matricula_Retiro;
    @FXML
    private MenuItem MI_Reportes_Retiros;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private MenuItem MI_Carreras_Suprimir;
    @FXML
    private MenuItem MI_Horarios;
    @FXML
    private Menu M_carreras;
    @FXML
    private Menu M_estudiantes;
    @FXML
    private Menu M_cursos;
    @FXML
    private Menu M_horarios;
    @FXML
    private Menu M_maricula;
    @FXML
    private Menu M_reportes;
    @FXML
    private Menu M_matricula_student;
    @FXML
    private MenuItem MI_Matricula_Nueva_student;
    @FXML
    private MenuItem Mi_Matricula_Retiro_student;
    @FXML
    private Menu M_reportes_student;
    @FXML
    private MenuItem MI_Reportes_Matricula_student;
    @FXML
    private MenuItem MI_Reportes_Retiros_student;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(util.Utility.getIntro()!=null){
            M_carreras.setVisible(false);
            M_cursos.setVisible(false);
            M_estudiantes.setVisible(false);
            M_horarios.setVisible(false);
            M_maricula.setVisible(false);
            M_reportes.setVisible(false);
            M_matricula_student.setVisible(true);
            M_reportes_student.setVisible(true);
        }
    }    

    @FXML
    private void MI_Carreras_Agregar(ActionEvent event) {
        loadPage("FXMLAgregarCarrera");
    }
    
        @FXML
    private void MI_Carreras_Suprimir(ActionEvent event) {
        if(util.Utility.getCarreras().isEmpty()){
                 Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay carreras agregadas\n Agregue primero una carrera");
                    a.showAndWait();
                 

        }else{
        loadPage("FXMLRemoverCarrera");
    }}

    @FXML
    private void MI_Carreras_Modificar(ActionEvent event) {
         if(util.Utility.getCarreras().isEmpty()){
                 Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay carreras agregadas\n Agregue primero una carrera");
                    a.showAndWait();
                 

        }else{
        loadPage("FXMLModificarCarrera");
    }}

    @FXML
    private void MI_Carreras_Mostrar(ActionEvent event) {
        loadPage("FXMLMostrarCarrera");
    }

    @FXML
    private void MI_Estudiante_Agregar(ActionEvent event) {
        loadPage("FXMLAgregarEstudiante");
    }

    @FXML
    private void MI_Estudiante_Modificarr(ActionEvent event) {
            if(util.Utility.getEstudiantes().isEmpty()){
                 Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay estudiantes agregados\n Agregue primero un estudiante");
                    a.showAndWait();
                 

        }else{
        loadPage("FXMLModificarEstudiante");
    }}

    @FXML
    private void MI_Estudiante_Suprimir(ActionEvent event) {
         if(util.Utility.getEstudiantes().isEmpty()){
                 Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay estudiantes agregados\n Agregue primero un estudiante");
                    a.showAndWait();
                 

        }else{
        loadPage("FXMLRemoverEstudiante");
    }}

    @FXML
    private void MI_Estudiante_Mostrar(ActionEvent event) {
        loadPage("FXMLMostrarEstudiante");
    }

    @FXML
    private void MI_Cursos_Agregar(ActionEvent event) {
        loadPage("FXMLCursosAgregar");
    }

    @FXML
    private void MI_Cursos_Modificar(ActionEvent event) {
            if(util.Utility.getCursos().isEmpty()){
                 Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay cursos agregados\n Agregue primero un curso");
                    a.showAndWait();
                 

        }else{
        loadPage("FXMLModificarCursos");
    }}

    @FXML
    private void MI_Cursos_Suprimir(ActionEvent event) {
              if(util.Utility.getCursos().isEmpty()){
                 Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("No hay cursos agregados\n Agregue primero un curso");
                    a.showAndWait();
                 

        }else{
        loadPage("FXMLRemoverCursos");
    }}

    @FXML
    private void MI_Cursos_Mostrar(ActionEvent event) {
        loadPage("FXMLMostrarCursos");
    }

    @FXML
    private void MI_Matricula_Nueva(ActionEvent event) {
        loadPage("FXMLMatriculaNueva");
    }

    @FXML
    private void Mi_Matricula_Retiro(ActionEvent event) {
        loadPage("FXMLMatriculaRetirar");
    }

    @FXML
    private void MI_Reportes_Matricula(ActionEvent event) {
        loadPage("FXMLReporteMatricula");
    }

    @FXML
    private void MI_Reportes_Retiros(ActionEvent event) {
        loadPage("FXMLReporteRetiro");
    }
    
    @FXML
    private void MI_Horarios(ActionEvent event) {
        loadPage("FXMLAgregarHorario");
    }
    
    
    private void loadPage(String page){
    Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource(page+".fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
             this.bp.setCenter(root);   
    }

//            private void idOnKeyPressedTyped(KeyEvent event) {
    //            textFieldID.textProperty().addListener(new ChangeListener<String>() {
    //                @Override
    //                public void changed(ObservableValue<? extends String> observable, String oldValue, 
    //                    String newValue) {
    //                    if (!newValue.matches("\\d*")) {
    //                        textFieldID.setText(newValue.replaceAll("[^\\d]", ""));
    //                    }
    //                }
    //             });
    //          }

    @FXML
    private void MI_Matricula_Nueva_student(ActionEvent event) {
    }

    @FXML
    private void Mi_Matricula_Retiro_student(ActionEvent event) {
    }

    @FXML
    private void MI_Reportes_Matricula_student(ActionEvent event) {
    }

    @FXML
    private void MI_Reportes_Retiros_student(ActionEvent event) {
    }


}
