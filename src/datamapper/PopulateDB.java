/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamapper;
import dominio.Aula;
import dominio.Professor;
import dominio.Usuario;
import java.sql.*;
import java.util.Calendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author Leticia
 */
public class PopulateDB {
    
    public static void main (String[]args){
        PopulateDB.fullSetupDB("prosub", "root", "");
        //PopulateDB.recreateDB("prosub", "root", "");
    }
    
    public static void fullSetupDB(String dbName, String user, String password){
                
        dropDB(dbName, user, password);
        createDB(dbName, user, password);
        populateDB();
    }
    
    public static void recreateDB(String dbName, String user, String password){
        dropDB(dbName, user, password);
        createDB(dbName, user, password);
    }
    
    private static void dropDB(String dbName, String username, String password){
        
    Connection conn = null;
    Statement stmt = null;
    String dbURL = "jdbc:mysql://localhost";

   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(dbURL, username, password);
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Deleting database " + dbName + "...");
      stmt = conn.createStatement();
      
      String sql = "DROP DATABASE " + dbName;
      stmt.executeUpdate(sql);
      System.out.println("Database deleted successfully...");
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Finished!");
   System.out.println("========================================================================");
        
    }
    
    private static void createDB(String dbName, String username, String password){
    Connection conn = null;
    Statement stmt = null;
    String dbURL = "jdbc:mysql://localhost";

   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(dbURL, username, password);
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating database " + dbName +  "...");
      stmt = conn.createStatement();
      
      String sql = "CREATE DATABASE " + dbName;
      stmt.executeUpdate(sql);
      System.out.println("Database deleted successfully...");
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Finished!");
   System.out.println("========================================================================");
        
    }
    
    private static void populateDB(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        
        populateUsuario(emf);
        populateProfessores(emf);
        
    }
    
    private static void populateProfessores(EntityManagerFactory emf){
        
        AulaJpaController aulaJpa = new AulaJpaController(emf);
        
//        Calendar comeco1 = Calendar.getInstance();
//        Calendar fim1 = Calendar.getInstance();
//        comeco1.set(2013, 06, 10, 10, 10);
//        fim1.set(2013, 06, 10, 10, 20);
        
        DateTime comeco1 = new DateTime(2013, 06, 10, 10, 10);
        DateTime fim1 = new DateTime(2013, 06, 10, 10, 20);
        
        Interval periodo = new Interval(comeco1, fim1);
        
        Aula aula = new Aula(Calendar.MONDAY, periodo);
        aulaJpa.create(aula);
        
//        Calendar comeco2 = Calendar.getInstance();
//        Calendar fim2 = Calendar.getInstance();
//        comeco2.set(2013, 06, 10, 18, 20);
//        fim2.set(2013, 06, 10, 20, 0);
        
        DateTime comeco2 = new DateTime(2013, 06, 10, 18, 20);
        DateTime fim2 = new DateTime(2013, 06, 10, 20, 0);
        
        Interval periodo2 = new Interval(comeco2, fim2);

        
        //Periodo periodo2 = new Periodo(comeco2, fim2);
        
        Aula aula2 = new Aula(Calendar.MONDAY, periodo2);
        aulaJpa.create(aula2);
        
        Professor prof1 = new Professor("Calebe");
        prof1.adicionarAula(aula);
        
        Professor prof2 = new Professor("Ana Claudia");
        prof2.adicionarAula(aula2);
        
        
        ProfessorJpaController profJpa = new ProfessorJpaController(emf);
        
        profJpa.create(prof1);
        profJpa.create(prof2);
    }
    
    private static void populateUsuario(EntityManagerFactory emf){
        
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(emf);
        
        Usuario u1 = new Usuario("bob");
        u1.setPermissao(3);
        
        Usuario u2 = new Usuario("jane");
        u2.setPermissao(2);
        u2.setSenha("mackenzie");
        
        Usuario u3 = new Usuario("admin");
        u3.setSenha("admin");
        u3.setPermissao(1);
        
        
        usuarioJpa.create(u1);
        usuarioJpa.create(u2);
        usuarioJpa.create(u3);
        
    }
    
    public static void populateUsuario(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        PopulateDB.populateUsuario(emf);
    }
    
    public static void populateProfessores(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        PopulateDB.populateProfessores(emf);        
    }
    
}


