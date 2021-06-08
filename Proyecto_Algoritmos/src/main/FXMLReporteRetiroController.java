/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Lists.ListException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import domain.DeEnrollment;
import domain.Enrollment;
import domain.Student;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLReporteRetiroController implements Initializable {
    private static String pdfPath;
    private SwingController swingController;
    private JComponent viewerPanel;
    private util.FileTXT txt ;
    private Student stud;
    @FXML
    private TextField txtFieldId;
    @FXML
    private Text putTxt;
    @FXML
    private BorderPane bp;
    @FXML
    private Button btnEnter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bp.setVisible(false);
        try {
            createViewer(bp);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
     
public void createPDF(File newPDF) throws DocumentException, FileNotFoundException, ListException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("ReporteRetiro.pdf"));
    String content = "";
    document.open();
    // AQUÍ COMPLETAREMOS NUESTRO CÓDIGO PARA GENERAR EL PDF
    DeEnrollment deR=null;
    Student st=null;
    boolean found=false;
    for (int k = 1; k <= util.Utility.getEstudiantes().size(); k++) {
        st = (Student)util.Utility.getEstudiantes().getNode(k).data;
        content+=st.getFirstname()+" "+st.getLastname()+"\n";
        for (int i = 1; i <= util.Utility.getRetiros().size(); i++) {
            deR = (DeEnrollment)util.Utility.getRetiros().getNode(i).data;
                if(deR.getStudentID().equals(st.getStudentID())){
                        content+=util.Utility.getRetiros().getNode(i).data+"\n";
                        found=true;
                }   
        }
        if(found==false)
        content+="Este estudiante no tiene retiro\n";
        found=false;
    }

    Paragraph retiro = new Paragraph("Lista de retiro \n\n"+content,
                FontFactory.getFont("arial",
                        12,
                        Font.BOLD,
                        BaseColor.BLACK
                        ));
    document.add(retiro);
    document.addTitle("Lista de cursos retirados");
    document.addKeywords("Java, PDF, Lista de Cursos Retirados");
    document.addAuthor("Projecto Algoritmos");
    document.addCreator("Grupo No.3");
    document.close();
}

    
private void createViewer(BorderPane borderPane) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
    ColorUIResource backgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource textUI = new ColorUIResource(0xFFFAFA);
        ColorUIResource controlBackgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource infoBackgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource infoUI = new ColorUIResource(0x023c4f);
        ColorUIResource lightBackgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource focusUI = new ColorUIResource(0x023c4f);

        UIManager.put("control", backgroundUI);
        UIManager.put("text", textUI);
        UIManager.put("nimbusLightBackground", lightBackgroundUI);
        UIManager.put("info", infoUI);
        UIManager.put("nimbusInfoBlue", infoBackgroundUI);
        UIManager.put("nimbusBase", controlBackgroundUI);
        
        UIManager.put("nimbusBlueGrey", controlBackgroundUI);
        UIManager.put("nimbusFocus", focusUI);
          for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(lafInfo.getName())) {
            UIManager.setLookAndFeel(lafInfo.getClassName());
            break;
        }
        
          }
    try {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                swingController = new SwingController();
                swingController.setIsEmbeddedComponent(true);
                PropertiesManager properties = new PropertiesManager(System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "false");
                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE, Boolean.FALSE);
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, "false");
                ResourceBundle messageBundle = ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);
               
                swingController.getDocumentViewController().setAnnotationCallback(
                        new org.icepdf.ri.common.MyAnnotationCallback(swingController.getDocumentViewController()));
                SwingViewBuilder factory = new SwingViewBuilder(swingController, properties);
                viewerPanel = factory.buildViewerPanel();
                viewerPanel.setForeground(Color.red);
                factory.buildToolToolBar().setOpaque(true);    
                viewerPanel.revalidate();
                SwingNode swingNode = new SwingNode();
                swingNode.setContent(viewerPanel);
                borderPane.setCenter(swingNode);
                swingController.setToolBarVisible(false);
                swingController.setUtilityPaneVisible(false);
            }
        });
        
    } catch (InvocationTargetException   ex) {
        Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
    }

}
     private void openDocument(String document) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingController.openDocument(document);
                viewerPanel.revalidate();
            }
        });
    }
    public String loadPDF(String adresse) throws IOException {
        if (!adresse.toLowerCase().endsWith("pdf")) {
            return null;
        }
        String fileName = adresse.substring(adresse.lastIndexOf("/") + 1,
                adresse.lastIndexOf("."));
        String suffix = adresse.substring(adresse.lastIndexOf("."),
                adresse.length());
        File temp = null;
    try {
            InputStream input = new URL(adresse).openStream();
            temp = File.createTempFile(fileName, suffix);
            temp.deleteOnExit();
            Files.copy(input, Paths.get(temp.toURI()),
                    StandardCopyOption.REPLACE_EXISTING);
    } catch (MalformedURLException ex) {
  Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
    }
        return temp.getAbsolutePath();
    }

    @FXML
    private void btnEnter(ActionEvent event) throws ListException {
    boolean findStud = false;
    boolean findEnrollment=false;
    Student aux;
    
    Alert a = new Alert(Alert.AlertType.ERROR);
      DialogPane dialogPane = a.getDialogPane();
// root
    dialogPane.setStyle("-fx-background-color: #02475e;");

// 1. Grid
    // remove style to customize header
    dialogPane.getStyleClass().remove("alert");

    GridPane grid = (GridPane)dialogPane.lookup(".header-panel"); 
    grid.setStyle("-fx-background-color: cadetblue; "
            + "-fx-font-style: italic;"+"-fx-font-size: 24px; "+"-fx-font-color: #e2e2e2;");

// 2. ContentText with just a Label
    dialogPane.lookup(".content.label").setStyle("-fx-font-size: 24px; "
            + "-fx-font-weight: bold; -fx-fill: blue;");

// 3- ButtonBar
    ButtonBar buttonBar = (ButtonBar)a.getDialogPane().lookup(".button-bar");
    buttonBar.setStyle("-fx-font-size: 24px;"
            + "-fx-background-color: 687980;");
    buttonBar.getButtons().forEach(b->b.setStyle("-fx-font-family: \"Andalus\";"));
    
    for (int i = 1; i <= util.Utility.getRetiros().size(); i++) {
            DeEnrollment m=(DeEnrollment)util.Utility.getRetiros().getNode(i).data;
                if(this.txtFieldId.getText().equals(m.getStudentID()))
                    findEnrollment=true;
        }
        for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++){
            aux = (Student)util.Utility.getEstudiantes().getNode(i).data;
            if(this.txtFieldId.getText().equals(aux.getStudentID())){
                findStud=true;
                stud=aux;
            }
        }
    if(util.Utility.getRetiros().isEmpty()){
            a.setHeaderText("No hay matriculas");
            a.showAndWait();
    }else if(txtFieldId.getText().isEmpty()){
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
    }else if(findStud==false){
            a.setHeaderText("Estudiante con id "+txtFieldId.getText()+" no ha sido encontrado");
            a.showAndWait();
     }else if(findEnrollment==false){
            a.setHeaderText("No hay matricula para este estudiante");
            a.showAndWait();
     }else{
         this.txtFieldId.setVisible(false);
         this.bp.setVisible(true);
         this.putTxt.setVisible(false);
         this.btnEnter.setVisible(false);
         File stud = new File("estudiantes.txt");
        try {
            
            createPDF(stud);
//            createViewer(bp);
            openDocument("ReporteRetiro.pdf");
        } catch (DocumentException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLReporteMatriculaController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
    }
