/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamapper;
import auxiliar.Perfil;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Aula;
import dominio.Ausencia;
import dominio.Professor;
import dominio.Usuario;
import java.sql.*;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;

/**
 *
 * @author Leticia
 */
public class PopulateDB {
    
    public static void main (String[]args) throws NonexistentEntityException, Exception{
        PopulateDB.fullSetupDB("prosub", "root", "");
        //PopulateDB.recreateDB("prosub", "root", "");
    }
    
    public static void fullSetupDB(String dbName, String user, String password) throws NonexistentEntityException, Exception{
                
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
    
    private static void populateDB() throws NonexistentEntityException, Exception{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        
        populateUsuario(emf);
        populateProfessores(emf);        
    }
    
    private static void populateProfessores(EntityManagerFactory emf){
        
        AulaJpaController aulaJpa = new AulaJpaController(emf);
        
        DateTime comeco1 = new DateTime(2013, 06, 10, 10, 10);
        DateTime fim1 = new DateTime(2013, 06, 10, 10, 20);
        
        Interval periodo = new Interval(comeco1, fim1);
        
        Aula aula = new Aula("Aula1", DateTimeConstants.MONDAY, periodo);
        aulaJpa.create(aula);
        
        DateTime comeco2 = new DateTime(2013, 06, 10, 18, 20);
        DateTime fim2 = new DateTime(2013, 06, 10, 20, 0);
        
        Interval periodo2 = new Interval(comeco2, fim2);

        
        //Periodo periodo2 = new Periodo(comeco2, fim2);
        
        Aula aula2 = new Aula("Aula2", DateTimeConstants.MONDAY, periodo2);
        aulaJpa.create(aula2);
        
        Professor prof1 = new Professor("Calebe", "calebe");
        
        prof1.adicionarAula(aula);
        
        Professor prof2 = new Professor("Ana Claudia", "anarossi");
        prof2.adicionarAula(aula2);
        
        Professor prof3 = new Professor("Vilar", "vilar");
        
        DateTime comeco3 = new DateTime(2013, 06, 10, 9, 0);
        DateTime fim3 = new DateTime(2013, 06, 10, 11, 0);
        
        Interval periodo3 = new Interval(comeco3, fim3);
        
        Aula aula3 = new Aula("Aula3", DateTimeConstants.MONDAY, periodo3);
        aulaJpa.create(aula3);
        
        prof3.adicionarAula(aula3);
        
        //prof1.adicionarAula(aula);
        
        Professor prof4 = new Professor("Gaston", "gaston");
        
        
        //prof2.adicionarAula(aula2);
        
        Professor prof5 = new Professor("Denise", "denise");
        //prof1.adicionarAula(aula);

        DateTime comeco4 = new DateTime(2013, 06, 10, 8, 0);
        DateTime fim4 = new DateTime(2013, 06, 10, 10, 20);
        
        Interval periodo4 = new Interval(comeco4, fim4);
        
        Aula aula4 = new Aula("Aula4", DateTimeConstants.MONDAY, periodo4);
        aulaJpa.create(aula4);
        
        prof5.adicionarAula(aula4);
        
        
        ProfessorJpaController profJpa = new ProfessorJpaController(emf);
        
        profJpa.create(prof1);
        profJpa.create(prof2);
        profJpa.create(prof3);
        profJpa.create(prof4);
        profJpa.create(prof5);
    }
    
    private static void populateUsuario(EntityManagerFactory emf){
        
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(emf);
        
        Usuario u1 = Usuario.createUsuarioPadrao("calebe");
        u1.setPermissao(Perfil.PROFESSOR);
                
        Usuario u2 = Usuario.createUsuarioPadrao("jane");
        u2.setPermissao(Perfil.FUNCIONARIO);
        u2.setSenha("mackenzie");
        
        Usuario u3 = Usuario.createUsuarioPadrao("admin");
        u3.setSenha("admin");
        u3.setPermissao(Perfil.ADMINISTRADOR);
        
        Usuario u4 = Usuario.createUsuarioPadrao("anarossi");
        u4.setPermissao(Perfil.PROFESSOR);
        
        Usuario u5 = Usuario.createUsuarioPadrao("vilar");
        u5.setPermissao(Perfil.PROFESSOR);
        
        Usuario u6 = Usuario.createUsuarioPadrao("gaston");
        u6.setPermissao(Perfil.PROFESSOR);
        
        
        Usuario u7 = Usuario.createUsuarioPadrao("denise");
        u7.setPermissao(Perfil.PROFESSOR);
                
        usuarioJpa.create(u1);
        usuarioJpa.create(u2);
        usuarioJpa.create(u3);
        usuarioJpa.create(u4);
        usuarioJpa.create(u5);
        usuarioJpa.create(u6);
        usuarioJpa.create(u7);
        
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


