package rest;

import PackageFetch.PackFetcher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.TamplePackDTO;
import entities.User;
import errorhandling.API_Exception;
import facades.UserFacade;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("demoPackage")

    public String getDemoPackage() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("<Get demo pack");
        // String thisuser = securityContext.getUserPrincipal().getName();
        return gson.toJson(PackFetcher.returnPackage(gson, threadPool, new TamplePackDTO("random", "1", "2", "3")));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("newpack")

    public String newPack(String jsonString) throws InterruptedException, ExecutionException, TimeoutException, API_Exception, AuthenticationException {
        System.out.println("New pack end point");
           String username;
        String category;
        String mentor;
        String target;
        String car;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
            category = json.get("category").getAsString();
            mentor = json.get("mentor").getAsString();
            target = json.get("target").getAsString();
            car = json.get("car").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        
        System.out.println(category+car+target+mentor);
      //  return gson.toJson(USER_FACADE.newPack(tmpl, username, gson, threadPool))
        
        return gson.toJson(USER_FACADE.newPack(new TamplePackDTO(category, car, mentor, target), username, gson, threadPool));
    }

}
