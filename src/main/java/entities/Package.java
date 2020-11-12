/*
 * CA3
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author magda
 */
@Entity
public class Package implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String car;
    private String chuckJoke;
    private String dadJoke;
    private String mentor;
    private String target;
    @Basic(optional = false)
    @NotNull
   
    @ManyToOne
    @JoinColumn(name = "user")
    private User  user;

    public Package() {
    }
    
     public void setUser(User user) {
        if(!user.getPacks().contains(this)){
            user.addPack(this);
        }
        this.user = user;
    }

    
    
    public Package(String car) {
        
        this.car = car;
        this.chuckJoke = car;
        this.dadJoke = car;
        this.mentor = car;
        this.target = car;
      
    }

   
    public User getUser() {
        return user;
    }
    
    
    
    

    public Long getId() {
        return id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getChuckJoke() {
        return chuckJoke;
    }

    public void setChuckJoke(String chuckJoke) {
        this.chuckJoke = chuckJoke;
    }

    public String getDadJoke() {
        return dadJoke;
    }

    public void setDadJoke(String dadJoke) {
        this.dadJoke = dadJoke;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

  
}
