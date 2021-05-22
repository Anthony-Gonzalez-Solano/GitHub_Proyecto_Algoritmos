/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author hhh
 */
public class Career {
    
    private String description ;
    private int id;
    public Career(String description, int id) {
        this.description = description;
        this.id=id;
    }

 
          public boolean equals(Career other){
        if(!(other instanceof Career))
            return false;
        Career c= (Career)other;
                return id==(c.id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return description+","+id;
    }
    
    
}
