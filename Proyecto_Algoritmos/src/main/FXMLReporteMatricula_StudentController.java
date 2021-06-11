/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;
import Lists.ListException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import domain.Course;
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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javax.swing.JComponent;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */


public class FXMLReporteMatricula_StudentController implements Initializable {

    private static String pdfPath;
    private Student stud;
    private SwingController swingController;
    private JComponent viewerPanel;
    private util.FileTXT txt;
    @FXML
    private AnchorPane ap;
    @FXML
    private BorderPane bp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bp.setVisible(true);
        try {
            createViewer(bp);//se genera el lector, se pone aqui ya que en otro lado genera una exception
            File stud = new File("estudiantes.txt");

            createPDF(stud);//se crea el pdf
//            createViewer(bp);
            openDocument("ReporteMatricula.pdf");

        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createPDF(File newPDF) throws DocumentException, FileNotFoundException, ListException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("ReporteMatricula.pdf"));
        String content = "";
        document.open();
        //aqui se genera el contenido del pdf
        Enrollment eR = null;
        Student st = util.Utility.getIntro();
        boolean found = false;
        //si no hay matriculas o estudiantes se pone un mensaje pero se espera que el usuario no lo vea
        if (util.Utility.getMatriculas().isEmpty() == true) {
            content += "No hay matriculas hechas por estudiantes\n";
        } else if (util.Utility.getEstudiantes().isEmpty() == true) {
            content += "No estudiantes matriculados\n";
        } else {
                content += "\n" + st.getFirstname() + " " + st.getLastname()+" ("+st.getStudentID()+")"+ "\n";
                for (int i = 1; i <= util.Utility.getMatriculas().size(); i++) {
                    eR = (Enrollment) util.Utility.getMatriculas().getNode(i).data;
                    if (eR.getStudentID().equals(st.getStudentID())) {
                        //se recorren las listas validando que la matriculas pertenesca al estudiante y se agrega al String
                        for (int j = 1; j <= util.Utility.getCursos().size(); j++) {
                            Course c = (Course) util.Utility.getCursos().getNode(j).data;
                            if (c.getId().equals(eR.getCourseID())) {
                                content += c.getName()+ ","+eR.getSchedule()+"   "+ util.Utility.dateFormat(eR.getDate())+"\n";
                                found = true;
                            }
                        }
                    }
                }
                if (found == false) {
                    content += "Este estudiante no tiene matriculas\n";
                    //si no tiene matriculas se pone este mensaje
                }
                found = false;
        }
        //se controla el tamaño,tipo y color de la letra
        Paragraph retiro = new Paragraph("Lista de matriculas \n\n" + content,
                FontFactory.getFont("arial",
                        12,
                        Font.BOLD,
                        BaseColor.BLACK
                ));
        document.add(retiro);//se agrega el contenido
        //metadatos
        document.addTitle("Lista de cursos retirados");
        document.addKeywords("Java, PDF, Lista de Cursos Retirados");
        document.addAuthor("Projecto Algoritmos");
        document.addCreator("Grupo No.3");
        document.close();
    }

    private void createViewer(BorderPane borderPane) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        //se usa LookAndFeel para establecer el estilo y color ya que es un componente Java Swing
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
        for (LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
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
                    //se genera el lector
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
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLReporteMatricula_StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp.getAbsolutePath();
    }
}


