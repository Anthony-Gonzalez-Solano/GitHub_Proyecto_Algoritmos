/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author Dell 7470
 */
public class Student {
    
    
    private int id;
    private static int autoStudentID;
    private String studentID;
    private String lastname;
    private String firstname;
    private Date birthday;
    private String phoneNumber; 
    private String email;
    private String address;
    private int careerID;

    public Student(int id, String lastname, String firstname, Date birthday, String phoneNumber, String email, String address, int careerID) {
        this.id = id;
        this.studentID = new DecimalFormat("0000").format(autoStudentID);
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.careerID = careerID;
        autoStudentID++;
    }
    public Student(int id,String studentID, String lastname, String firstname, Date birthday, String phoneNumber, String email, String address, int careerID) {
        this.id = id;
        this.studentID = studentID;
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.careerID = careerID;
    }
    
          public boolean equals(Student other){
        if(!(other instanceof Student))
            return false;
        Student c= (Student)other;
                return id==(c.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCareerID() {
        return careerID;
    }

    public void setCareerID(int careerID) {
        this.careerID = careerID;
    }

    public static int getAutoStudentID() {
        return autoStudentID;
    }

    public void setAutoStudentID(int autoStudentID) {
        this.autoStudentID = autoStudentID;
    }
    

    @Override
    public String toString() {
        return  id+"," + studentID+","+lastname+","+firstname+","+util.Utility.dateFormat(birthday)+","+phoneNumber+","+email+","+address+","+careerID ;
    }
    
}
