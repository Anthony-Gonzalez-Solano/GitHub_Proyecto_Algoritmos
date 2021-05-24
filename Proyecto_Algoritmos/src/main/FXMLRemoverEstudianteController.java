/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import domain.Student;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLRemoverEstudianteController implements Initializable {
  private util.FileTXT txt ;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldCedula;
    @FXML
    private Text txtLastName;
    @FXML
    private Text txtCedula;
    @FXML
    private Button btnRemover;
    @FXML
    private Text txtMessage;
    @FXML
    private Text txtError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          txt=new FileTXT();
    }    

    @FXML
    private void btnRemover(ActionEvent event) {
//         Calendar cal=new GregorianCalendar(this.datePickerEstudiante.getValue().getYear(),
//                this.datePickerEstudiante.getValue().getMonthValue(),
//                this.datePickerEstudiante.getValue().getDayOfMonth());
    Date date = null;
            Student s=new Student(Integer.parseInt(this.textFieldCedula.getText()),textFieldCedula.getText(), this.textFieldLastName.getText(), textFieldCedula.getText(), date, textFieldCedula.getText(), textFieldCedula.getText(), textFieldCedula.getText(), Integer.parseInt(textFieldCedula.getText()));
       
            try{
         //  util.Utility.getEstudiantes().remove(new Student(Integer.parseInt(this.textFieldCedula.getText()), "", this.textFieldLastName.getText(), "",date, "","","", 0));
            for (int i = 1; i <util.Utility.getEstudiantes().size(); i++) {
                   // util.Utility.getEstudiantes().getNode(i).data;
                    if(util.Utility.getEstudiantes().getNode(i).data.equals(s.getId()) && util.Utility.getEstudiantes().getNode(i).data.equals(s.getLastname()))
                        s=(Student) util.Utility.getEstudiantes().getNode(i).data;
                } 
         util.Utility.getEstudiantes().remove(s);
           
             
             txtMessage.setVisible(true);
           txtMessage.setText("Estudiante eliminado");
          
           textFieldCedula.setText("");
           textFieldLastName.setText("");
           txt.removeElement("estudiantes.txt", s.toString());
                   
        } catch(ListException e){
                      this.txtError.setVisible(true);
                    this.txtError.setText("Ha ocurrido un error");
                }
            catch(NullPointerException es){
                txtError.setVisible(true);
                txtError.setText("Error");
            }
            catch(NumberFormatException en){
                 txtError.setVisible(true);
                txtError.setText("Error");
            }
    
}
}
