/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Date;

/**
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class DeEnrollment {
    private int iD;
    private Date date;
    private String studentID;
    private String courseID;
    private String schedule;
    private static int autoDeEnrollmentID;
    public DeEnrollment(Date date, String studentID, String courseID, String schedule){
        this.iD = autoDeEnrollmentID;
        this.date = date;
        this.courseID=courseID;
        this.schedule = schedule;
        this.studentID = studentID;
        autoDeEnrollmentID++;
    }
    public DeEnrollment(int iD,Date date, String studentID, String courseID, String schedule){
        this.iD = iD;
        this.date = date;
        this.courseID=courseID;
        this.schedule = schedule;
        this.studentID = studentID;
    }
    public int getAutoDeEnrollmentID() {
        return autoDeEnrollmentID;
    }

    public void setAutoDeEnrollmentID(int autoEnrollmentID) {
        DeEnrollment.autoDeEnrollmentID = autoEnrollmentID;
    }
    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public static int getAutoEnrollmentID() {
        return autoDeEnrollmentID;
    }

    public static void setAutoEnrollmentID(int autoEnrollmentID) {
        DeEnrollment.autoDeEnrollmentID = autoEnrollmentID;
    }

    @Override
    public String toString() {
        return iD + "," + util.Utility.dateFormat(date)+ "," + studentID + "," + courseID + "," + schedule;
    }
    
}
