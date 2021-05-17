/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Lists.CircularDoublyLinkedList;
import Lists.DoublyLinkedList;
import Lists.SinglyLinkedList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Profesor Lic. Gilberth Chaves Avila
 */
public class Utility {
    
    private static DoublyLinkedList listCarreras = new DoublyLinkedList();
    private static SinglyLinkedList listEstudiantes = new SinglyLinkedList(); 
    private static CircularDoublyLinkedList listCursos = new CircularDoublyLinkedList();
    private static SinglyLinkedList listHorarios = new SinglyLinkedList();
    private static CircularDoublyLinkedList listMatricula = new CircularDoublyLinkedList(); 
    
    public DoublyLinkedList getCarreras(){ return listCarreras;}
    public void setCarreras(DoublyLinkedList list){listCarreras=list;}
    public SinglyLinkedList getEstudiantes(){ return listEstudiantes;}
    public void setEstudiantes(SinglyLinkedList list){listEstudiantes=list;}
    public CircularDoublyLinkedList getCursos(){ return listCursos;}
    public void setCursos(CircularDoublyLinkedList list){listCursos=list;}
    public SinglyLinkedList getHorarios(){ return listHorarios;}
    public void setHorarios(SinglyLinkedList list){listHorarios=list;}
    public CircularDoublyLinkedList getMatriculas(){ return listMatricula;}
    public void setMatriculas(CircularDoublyLinkedList list){listMatricula=list;}

    public static String dateFormat(Date date){
        
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static int random(){
        return 1+(int) Math.floor(Math.random()*99); 
    }
    
    public static int random(int bound){
        //return 1+random.nextInt(bound);
        return 1+(int) Math.floor(Math.random()*bound); 
    }
    
        public static int random(int low,int higth){
        //return 1+random.nextInt(bound);
        return low+(int) Math.floor(Math.random()*(higth-low)); 
    }
    
    public static String format(double value){
        return new DecimalFormat("###,###,###,###.##")
                .format(value);
    }
    
    public static String $format(double value){
        return new DecimalFormat("$###,###,###,###.##")
                .format(value);
    }
     public static String perFormat(double value){
         //#,##0.00 '%'
        return new DecimalFormat("#,##0.00'%'")
                .format(value);
    }

    public static boolean equals(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "integer":
               Integer x =(Integer)a; Integer y = (Integer)b;
               return x.equals(y);
            case "string":
                String s1 =(String)a; String s2 = (String)b;
               //return s1.compareTo(s2)==0; //OPCION 1
               return s1.equalsIgnoreCase(s2); //OPCION 2              
        }
        
        return false; //en cualquier otro caso
    }

    private static String instanceOf(Object a, Object b) {
        if(a instanceof Integer && b instanceof Integer) return "integer";
        if(a instanceof String && b instanceof String) return "string";
        return "unknown"; //desconocido
    }
    
     public static boolean lessT(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "integer":
               Integer x =(Integer)a; Integer y = (Integer)b;
               return x<y;
            case "string":
                String s1 =(String)a; String s2 = (String)b;
               return s1.compareToIgnoreCase(s2)<0;
        }
        return false; //en cualquier otro caso
    }
     

    public static boolean greaterT(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "integer":
               Integer x =(Integer)a; Integer y = (Integer)b;
               return x>y;
            case "string":
                String s1 =(String)a; String s2 = (String)b;
               return s1.compareToIgnoreCase(s2)>0;
        }
        return false; //en cualquier otro caso
    }

    public static int getAge(Date birthday) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(birthday);
        return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
    }
}
