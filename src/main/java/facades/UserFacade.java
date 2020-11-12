package facades;

import dto.PackageDTO;
import dto.TamplePackDTO;
import entities.Role;
import entities.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User addUser(String name, String password) throws AuthenticationException{
         EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, name);
            if (user != null) {
                throw new AuthenticationException("Username allready exist, try different one");
            } else {
                user = new User(name, password);
                user.addRole(em.find(Role.class, "user"));
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        return user;
    }
    
    public String deleteUser(String name) throws AuthenticationException{
         EntityManager em = emf.createEntityManager();
        User user;
        String status="Not deleted";
        try {
            user = em.find(User.class, name);
            if (user == null) {
                throw new AuthenticationException("User does not exist.");
            } else {
                
                em.getTransaction().begin();
                user = em.find(User.class, name);              
                em.remove(user);
                em.getTransaction().commit();
                status="User "+name+" DELETED";
            }
        } finally {
            em.close();
        }
        return status;
    }
    
    
     public String changePass(String name, String oldPass, String newPass, UserFacade facade) throws AuthenticationException {
         EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = facade.getVeryfiedUser(name, oldPass);
        } catch (AuthenticationException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Password not updated - wrong current passwor");
            
        }
        String status="Not updated";
        try {
           
                
                em.getTransaction().begin();
                user = em.find(User.class, name); 
                user.setUserPass(newPass);
                em.persist(user);
                em.getTransaction().commit();
                status="Password updated";
            
        } finally {
            em.close();
        }
        return status;
    }
     public PackageDTO newPack(TamplePackDTO tmpl, String user){
         
         
          EntityManager em = emf.createEntityManager();
        
       
        
        try {                
                em.getTransaction().begin();
                
                
               
                em.getTransaction().commit();
                
        } finally {
            em.close();
        }
         
         
         
         PackageDTO result= new PackageDTO();
         return result;
     }
   
         public static void main(String[] args) throws AuthenticationException {
        emf = EMF_Creator.createEntityManagerFactory();
        UserFacade uf=UserFacade.getUserFacade(emf);
             System.out.println(uf.changePass("Ola", "manola", "99999", uf));
        
    }
    
    
}
