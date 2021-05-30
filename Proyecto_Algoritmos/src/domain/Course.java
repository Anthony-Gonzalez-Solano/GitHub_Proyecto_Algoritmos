/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Dell 7470
 */
public class Course {
   private String id;
   private String name;
   private int credits;
   private int careerID;

    public Course(String id, String name, int credits, int careerID) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.careerID = careerID;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getCareerID() {
        return careerID;
    }

    @Override
    public String toString() {
        return name;
    }

 
    public String secondToString() {
        return id+","+name+ ","+ credits+","+careerID;
    }
   
   
    
}
