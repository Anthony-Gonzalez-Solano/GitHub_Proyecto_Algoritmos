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
import domain.Student;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
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
                Logger.getLogger(FXMLReporteRetiroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }    
    public void createPDF(File newPDF) throws DocumentException, FileNotFoundException, ListException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("ReporteRetiros.pdf"));
    String content = "";
    document.open();
    // AQUÍ COMPLETAREMOS NUESTRO CÓDIGO PARA GENERAR EL PDF
        if(util.Utility.getMatriculas().isEmpty())
                    content ="No existe matricula para este estudiante";
        else{
                for(int i=1;i<util.Utility.getCarreras().size();i++)
                content +=util.Utility.getMatriculas().getNode(i).data+"\n";
        }
    Paragraph retiro = new Paragraph("Lista de personas \n\n"+content,
                FontFactory.getFont("arial",
                        22,
                        Font.BOLD,
                        BaseColor.BLUE
                        ));
    document.add(retiro);
    System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");
    document.addTitle("Lista de cursos retirados");
    document.addKeywords("Java, PDF, Lista de Cursos Retirados");
    document.addAuthor("Projecto Algoritmos");
    document.addCreator("Grupo No.3");
    document.close();
}

    private void createViewer(BorderPane borderPane) throws InterruptedException {
    try {
        SwingUtilities.invokeAndWait(() -> {
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
            viewerPanel.revalidate();
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(viewerPanel);
            borderPane.setCenter(swingNode);
            swingController.setToolBarVisible(false);
            swingController.setUtilityPaneVisible(false);
        });
    } catch (InvocationTargetException ex) {
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
        System.out.println("In load PDf");
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
    String studId = this.txtFieldId.getText();
    boolean findStud = false;
    Student aux;
    for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++){
            aux = (Student)util.Utility.getEstudiantes().getNode(i).data;
            if(studId.equals(aux.getStudentID()))
                findStud=true;
    }
    if (txtFieldId.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } 
     else if(findStud==false){
         Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Estudiante con id "+studId+" no ha sido encontrado");
            a.showAndWait();
     }
     else{
         this.txtFieldId.setVisible(false);
         this.bp.setVisible(true);
         this.putTxt.setVisible(false);
         this.btnEnter.setVisible(false);
         File stud = new File("estudiantes.txt");
        try {
            
            createPDF(stud);
//            createViewer(bp);
            openDocument("ReporteRetiros.pdf");
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
