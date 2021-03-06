/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Lists.CircularDoublyLinkedList;
import Lists.CircularLinkedList;
import Lists.DoublyLinkedList;
import Lists.SinglyLinkedList;
import domain.Career;
import domain.Course;
import domain.DeEnrollment;
import domain.Enrollment;
import domain.Security;
import domain.Student;
import domain.TimeTable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.ParseException;

/**
 *
 * @author Profesor Lic. Gilberth Chaves Avila
 */
public class Utility {
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
     
    public static boolean ValidarMail(String email) {

//      ^ especifica el inicio de la entrada.
//([_a-z0-9-]) primer grupo. Se refiere a la aparición de uno o más caracteres compuestos por guión bajo, letras, números y guiones.  
// \.[_a-z0-9-]) segundo grupo. Puede ser opcional y repetible, se refiere a la aparición de un punto seguido de uno o más caracteres compuestos por guión bajo, letras, números y guiones. 
// Luego la verificacion del carácter arroba @.
// Despues de repetite lo mismo, [a-z0-9-]). Tercer grupo. Especifica la aparición de uno o más caracteres compuestos por letras, números y guiones.
//(\.[a-z0-9-]) cuarto grupo. Especifica un punto seguido de uno o más caracteres compuestos por letras, números y guiones.
//(\.[a-z]{2,4}) quinto grupo. Especifica un punto seguido de entre 2 y 4 letras, con el fin de considerar dominios terminados, por ejemplo, en .com y .info
// "$" especifica el fin de la entrada.
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$");   // Patron para validar el email y se compila la expresion regular

        Matcher mather = pattern.matcher(email);
        return mather.find();// retorna si es valido o no 
    }
    
    public static String randomPass(){
        String result = "";
        for (int i = 0; i < 8; i++) {
            int rnd = (int) (Math.random() * 52); // or use Random or whatever
            char base = (rnd < 26) ? 'A' : 'a';
            result+=(char) (base + rnd % 26);
        }
        return result;
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
                  case "Career":
                Career career=(Career)a;Career career2=(Career)b;
                return career.getDescription().equalsIgnoreCase(career2.getDescription())&&career.getId()==career2.getId() ;
                  case "Student":
                  Student student1=(Student)a;Student student2=(Student)b;
                return student1.getLastname(). equalsIgnoreCase(student2.getLastname()) && student1.getId()==student2.getId() ;
                  case "Course":
                      Course course1=(Course)a;Course course2=(Course)b;
                      return course1.getId().equalsIgnoreCase(course2.getId()) && course1.getName().equalsIgnoreCase(course2.getName());
                  case "enrollment":
                      Enrollment e1 =(Enrollment)a;Enrollment e2=(Enrollment)b;
                      return e1.getiD()==e2.getiD() && e1.getCourseID().equals(e2.getCourseID()) && e1.getStudentID().equals(e2.getStudentID());
                  case "timetable":
                      TimeTable tt1 = (TimeTable)a; TimeTable tt2 = (TimeTable)b;
                      return tt1.getCourseID().equals(tt2.getCourseID()) && tt1.getPeriod().equals(tt2.getPeriod()) && 
                              tt1.getSchedule1().equals(tt2.getSchedule1()) && tt1.getSchedule2().equals(tt2.getSchedule2());
                  case "security":
                      Security sc1 = (Security)a; Security sc2 = (Security)b; 
                      return sc1.getUser().equals(sc2.getUser()) && sc1.getPassword().equals(sc2.getPassword());
        }
        
        return false; //en cualquier otro caso
    }

    private static String instanceOf(Object a, Object b) {
        if(a instanceof Integer && b instanceof Integer) return "integer";
        if(a instanceof String && b instanceof String) return "string";
        if(a instanceof Career && b instanceof Career)return "Career";
        if(a instanceof Student && b instanceof Student)return "Student";
        if(a instanceof Course && b instanceof Course)return "Course";
        if(a instanceof Enrollment && b instanceof Enrollment)return "enrollment";
        if(a instanceof TimeTable && b instanceof TimeTable)return "timetable";
        if(a instanceof Security && b instanceof Security)return "security";
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

    //-------------------------------------------Metodos para las listas -------------------------------------------------------
    private static CircularLinkedList listUsers = new CircularLinkedList();
    private static DoublyLinkedList listCarreras = new DoublyLinkedList();
    private static SinglyLinkedList listEstudiantes = new SinglyLinkedList(); 
    private static CircularDoublyLinkedList listCursos = new CircularDoublyLinkedList();
    private static SinglyLinkedList listHorarios = new SinglyLinkedList();
    private static CircularDoublyLinkedList listMatricula = new CircularDoublyLinkedList(); 
    private static CircularDoublyLinkedList listRetiro = new CircularDoublyLinkedList();
    
    public static CircularLinkedList getUsers(){ return listUsers;}
    public void setUsers(CircularLinkedList list){listUsers=list;}
    public static DoublyLinkedList getCarreras(){ return listCarreras;}
    public void setCarreras(DoublyLinkedList list){listCarreras=list;}
    public static SinglyLinkedList getEstudiantes(){ return listEstudiantes;}
    public void setEstudiantes(SinglyLinkedList list){listEstudiantes=list;}
    public static CircularDoublyLinkedList getCursos(){ return listCursos;}
    public void setCursos(CircularDoublyLinkedList list){listCursos=list;}
    public static SinglyLinkedList getHorarios(){ return listHorarios;}
    public void setHorarios(SinglyLinkedList list){listHorarios=list;}
    public static CircularDoublyLinkedList getMatriculas(){ return listMatricula;}
    public void setMatriculas(CircularDoublyLinkedList list){listMatricula=list;}
    public static CircularDoublyLinkedList getRetiros(){ return listRetiro;}
    public void setRetiros(CircularDoublyLinkedList list){listRetiro=list;}
    
    private static Student introStudent = null;
    public static void setIntro(Student s){introStudent = s;}
    public static Student getIntro(){return introStudent;}
    
    public static void fillList(){  //metodo para cargar todas las listas necesarios al iniciar
        FileTXT file = new FileTXT();
        ArrayList<String> list = new ArrayList<>();
        
        if(file.existFile("estudiantes.txt")){
            list = file.readFile("estudiantes.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                String[] date = datos[4].split("/");
                Calendar c = new GregorianCalendar(Integer.parseInt(date[2]),Integer.parseInt(date[1])-1,Integer.parseInt(date[0]));
                
                Student s = new Student(Integer.parseInt(datos[0]),
                        datos[1], datos[2], datos[3], c.getTime(), datos[5], datos[6], datos[7], Integer.parseInt(datos[8]));
                getEstudiantes().add(s);
                s.setAutoStudentID(Integer.parseInt(s.getStudentID())+1);
            }
        }
        if(file.existFile("Users.txt")){
            list = file.readFile("Users.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                getUsers().add(new Security(datos[0], desBinaryCodify(datos[1])));
            }
        }
        if(file.existFile("carreras.txt")){
            list = file.readFile("carreras.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                getCarreras().add(new Career(datos[0], Integer.parseInt(datos[1])));
            }
        }
        if(file.existFile("cursos.txt")){
            list = file.readFile("cursos.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                getCursos().add(new Course(datos[0], datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3])));
            }
        }
        if(file.existFile("horarios.txt")){
            list = file.readFile("horarios.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(";");
                getHorarios().add(new TimeTable(datos[0], datos[1], datos[2], datos[3]));
            }
        }
        if(file.existFile("matricula.txt")){
            list = file.readFile("matricula.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                String[] date = datos[1].split("/");
                Calendar c = new GregorianCalendar(Integer.parseInt(date[2]),Integer.parseInt(date[1])-1,Integer.parseInt(date[0]));
                Enrollment e=new Enrollment(Integer.parseInt(datos[0]), c.getTime(),datos[2],datos[3],datos[4]+","+datos[5]+","+datos[6]);
                getMatriculas().add(e);
                e.setAutoEnrollmentID(Integer.parseInt(datos[0]));
            }
        }
        if(file.existFile("retiro.txt")){
            list = file.readFile("retiro.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                String[] date = datos[1].split("/");
                Calendar c = new GregorianCalendar(Integer.parseInt(date[2]),Integer.parseInt(date[1])-1,Integer.parseInt(date[0]));
                DeEnrollment e=new DeEnrollment(Integer.parseInt(datos[0]), c.getTime(),datos[2],datos[3],datos[4]+","+datos[5]+","+datos[6]);
                getRetiros().add(e);
                e.setAutoEnrollmentID(Integer.parseInt(datos[0]));
            }
    }
}
    public static String binaryCodify(String dato){ //codifica un string
        String code="";
        int[] numberCode=new int[dato.length()];//hacemos un vector del tamaño del string
        for (int i = 0; i < dato.length(); i++) {
            numberCode[i] = dato.charAt(i); //llenamos el vector con los chars del string 
        }
        for (int i = 0; i < numberCode.length; i++) { // convertimos el codigo ASCCI de cada char a un numero binario invertido
            while(numberCode[i]!=0){
                    code += numberCode[i]%2+"";
                    numberCode[i]=numberCode[i]/2;
            }
            code+="0@";//agregamos la divicion entre los numeros binarios
        }
        code+="end";
        return code;
    }
    
    public static String desBinaryCodify(String binary){
        String text="";
        String[] binaryText = binary.split("@"); // dividimos el string para cada binario
        int[] decimalText = new int[binaryText.length-1];
        
        
        for (int i = 0; i < binaryText.length-1; i++) {
            String aux = "";
            for (int j = 0; j < binaryText[i].length(); j++) {
                decimalText[i] += Integer.parseInt(binaryText[i].charAt(j)+"")*(Math.pow(2, j));
                //convertimos el binario en un numero decimal
            }
        }
        for (int i = 0; i < decimalText.length; i++) {
            text += (char)decimalText[i]; // de decimal lo pasamos a char
        }
        return text;
    }
}
