/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Career;
import domain.Student;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLAgregarEstudianteController implements Initializable {

    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldAdress;
    @FXML
    private Text txtCedula;
    @FXML
    private Text txtLastName;
    @FXML
    private Text txtPhone;
    @FXML
    private Text txtFirstName;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtDireccion;
    @FXML
    private DatePicker datePickerEstudiante;
    @FXML
    private Text txtFecha;
    @FXML
    private Button btnAgregar;
    @FXML
    private Text txtMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAgregar(ActionEvent event) {
             Calendar cal=new GregorianCalendar(this.datePickerEstudiante.getValue().getYear(),
                this.datePickerEstudiante.getValue().getMonthValue(),
                this.datePickerEstudiante.getValue().getDayOfMonth());
            try{
           util.Utility.getEstudiantes().add(new Student(Integer.parseInt(this.textFieldId.getText()), "h", this.textFieldLastName.getText(), this.textFieldFirstName.getText(),cal.getTime() , this.textFieldPhone.getText(),this.textFieldEmail.getText(),this.textFieldAdress.getText(), 2));
                        this.textFieldAdress.setText("");
                         this.textFieldEmail.setText("");
                          this.textFieldFirstName.setText("");
                           this.textFieldPhone.setText("");
                            this.textFieldId.setText("");
                             this.textFieldLastName.setText("");
                            
          //  txt.writeFile("carreras.txt",this.textFieldDescription.getText()+","+ Integer.parseInt(this.textFieldId.getText()) );
                        this.txtMessage.setVisible(true);
                     
                        this.txtMessage.setText("Estudiante agregado correctamente");
                        
                           

                }catch(Exception e){
                      this.txtMessage.setVisible(true);
                    this.txtMessage.setText("Ha ocurrido un error");
                }
        
    }
    
}
