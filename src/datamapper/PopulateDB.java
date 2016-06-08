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
    
    public static void populateUseCaseTest() throws NonexistentEntityException, Exception
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(emf);
        ProfessorJpaController profJpa = new ProfessorJpaController(emf);
        
        Usuario u1 = Usuario.createUsuarioPadrao("Funcionario1");
        u1.setPermissao(Perfil.FUNCIONARIO);
        
        Usuario u2 = Usuario.createUsuarioPadrao("Professor1");
        u2.setPermissao(Perfil.PROFESSOR);
        
        Usuario u3 = Usuario.createUsuarioPadrao("Professor2");
        u3.setPermissao(Perfil.PROFESSOR);
        
        Usuario u4 = Usuario.createUsuarioPadrao("Professor3");
        u4.setPermissao(Perfil.PROFESSOR);
        
        Usuario u5 = Usuario.createUsuarioPadrao("Administrador");
        u5.setPermissao(Perfil.ADMINISTRADOR);
        
        usuarioJpa.create(u1);
        usuarioJpa.create(u2);
        usuarioJpa.create(u3);
        usuarioJpa.create(u4);
        usuarioJpa.create(u5);
        
        AulaJpaController aulaJpa = new AulaJpaController(emf);
        
        DateTime inícioPrimeiroHorário = new DateTime(1900, 01, 01, 18, 30);
        DateTime finalPrimeiroHorário = new DateTime(1900, 01, 01, 20, 00);        
        Interval primeiroHorário = new Interval(inícioPrimeiroHorário, finalPrimeiroHorário);
        
        DateTime inícioSegundoHorário = new DateTime(1900, 01, 01, 20, 00);
        DateTime finalSegundoHorário = new DateTime(1900, 01, 01, 21, 30);        
        Interval segundoHorário = new Interval(inícioSegundoHorário, finalSegundoHorário);
        
        DateTime inícioTerceiroHorário = new DateTime(1900, 01, 01, 21, 30);
        DateTime finalTerceiroHorário = new DateTime(1900, 01, 01, 23, 00);        
        Interval terceiroHorário = new Interval(inícioTerceiroHorário, finalTerceiroHorário);
        
        Aula aulaProfessor1SegundaFeiraPrimeiroHorário = new Aula("P1 - Segunda 18:30", DateTimeConstants.MONDAY, primeiroHorário);
        aulaJpa.create(aulaProfessor1SegundaFeiraPrimeiroHorário);
        
        Aula aulaProfessor1SegundaFeiraSegundoHorário = new Aula("P1 - Segunda 20:00", DateTimeConstants.MONDAY, segundoHorário);
        aulaJpa.create(aulaProfessor1SegundaFeiraSegundoHorário);
        
        Aula aulaProfessor1SegundaFeiraTerceiroHorário = new Aula("P1 - Segunda 21:30", DateTimeConstants.MONDAY, terceiroHorário);
        aulaJpa.create(aulaProfessor1SegundaFeiraTerceiroHorário);
                
        Professor prof1 = new Professor("Professor1", "Professor1");        
        prof1.adicionarAula(aulaProfessor1SegundaFeiraPrimeiroHorário);
        prof1.adicionarAula(aulaProfessor1SegundaFeiraSegundoHorário);
        prof1.adicionarAula(aulaProfessor1SegundaFeiraTerceiroHorário);        
        profJpa.create(prof1);
                
        Aula aulaProfessor2SegundaFeiraPrimeiroHorário = new Aula("P2 - Segunda 18:30", DateTimeConstants.MONDAY, primeiroHorário);
        aulaJpa.create(aulaProfessor2SegundaFeiraPrimeiroHorário);
        
        Aula aulaProfessor2TercaFeiraPrimeiroHorário = new Aula("P2 - Terca 18:30", DateTimeConstants.TUESDAY, primeiroHorário);
        aulaJpa.create(aulaProfessor2TercaFeiraPrimeiroHorário);
        
        Aula aulaProfessor2TercaFeiraSegundoHorário = new Aula("P2 - Terca 20:00", DateTimeConstants.TUESDAY, segundoHorário);
        aulaJpa.create(aulaProfessor2TercaFeiraSegundoHorário);
        
        Aula aulaProfessor2TercaFeiraTerceiroHorário = new Aula("P2 - Terca 21:30", DateTimeConstants.TUESDAY, terceiroHorário);
        aulaJpa.create(aulaProfessor2TercaFeiraTerceiroHorário);
        
        Professor prof2 = new Professor("Professor2", "Professor2");        
        prof2.adicionarAula(aulaProfessor2SegundaFeiraPrimeiroHorário);
        prof2.adicionarAula(aulaProfessor2TercaFeiraPrimeiroHorário);
        prof2.adicionarAula(aulaProfessor2TercaFeiraSegundoHorário);        
        prof2.adicionarAula(aulaProfessor2TercaFeiraTerceiroHorário);        
        profJpa.create(prof2);
                
        Aula aulaProfessor3SegundaFeiraSegundoHorário = new Aula("P3 - Segunda 20:00", DateTimeConstants.MONDAY, segundoHorário);
        aulaJpa.create(aulaProfessor3SegundaFeiraSegundoHorário);
        
        Aula aulaProfessor3SegundaFeiraTerceiroHorário = new Aula("P3 - Segunda 21:30", DateTimeConstants.MONDAY, terceiroHorário);
        aulaJpa.create(aulaProfessor3SegundaFeiraTerceiroHorário);
        
        Professor prof3 = new Professor("Professor3", "Professor3");        
        prof3.adicionarAula(aulaProfessor3SegundaFeiraSegundoHorário);
        prof3.adicionarAula(aulaProfessor3SegundaFeiraTerceiroHorário);
        profJpa.create(prof3);        
    }
    
    private static void populateProfessores(EntityManagerFactory emf){
        
        AulaJpaController aulaJpa = new AulaJpaController(emf);
        
        DateTime inícioPrimeiroHorário = new DateTime(1900, 01, 01, 18, 30);
        DateTime finalPrimeiroHorário = new DateTime(1900, 01, 01, 20, 00);        
        Interval primeiroHorário = new Interval(inícioPrimeiroHorário, finalPrimeiroHorário);
        
        DateTime inícioSegundoHorário = new DateTime(1900, 01, 01, 20, 00);
        DateTime finalSegundoHorário = new DateTime(1900, 01, 01, 21, 30);        
        Interval segundoHorário = new Interval(inícioSegundoHorário, finalSegundoHorário);
        
        DateTime inícioTerceiroHorário = new DateTime(1900, 01, 01, 21, 30);
        DateTime finalTerceiroHorário = new DateTime(1900, 01, 01, 23, 00);        
        Interval terceiroHorário = new Interval(inícioTerceiroHorário, finalTerceiroHorário);
        
        Aula testeDeSoftwareSegundaFeiraSegundoHorário = new Aula("Teste de Software", DateTimeConstants.MONDAY, segundoHorário);
        aulaJpa.create(testeDeSoftwareSegundaFeiraSegundoHorário);
        
        Aula testeDeSoftwareTerçaFeiraSegundoHorário = new Aula("Teste de Software", DateTimeConstants.TUESDAY, segundoHorário);
        aulaJpa.create(testeDeSoftwareTerçaFeiraSegundoHorário);
        
        Aula processosEPadrõesSegundaFeiraPrimeiroHorário = new Aula("Processos e Padrões", DateTimeConstants.MONDAY, primeiroHorário);
        aulaJpa.create(processosEPadrõesSegundaFeiraPrimeiroHorário);
        
        Aula processosEPadrõesQuartaFeiraTerceiroHorário = new Aula("Processos e Padrões", DateTimeConstants.WEDNESDAY, terceiroHorário);
        aulaJpa.create(processosEPadrõesQuartaFeiraTerceiroHorário);
        
        Aula sistemasConcorrentesSegundaFeiraSegundoHorário = new Aula("Sistemas Concorrentes e Distribuídos", DateTimeConstants.MONDAY, segundoHorário);
        aulaJpa.create(sistemasConcorrentesSegundaFeiraSegundoHorário);
        
        Aula programaçãoFuncionalQuartaFeiraTerceiroHorário = new Aula("Programação Funcional", DateTimeConstants.WEDNESDAY, terceiroHorário);
        aulaJpa.create(programaçãoFuncionalQuartaFeiraTerceiroHorário);
                        
        Professor prof1 = new Professor("Calebe", "calebe");        
        prof1.adicionarAula(testeDeSoftwareSegundaFeiraSegundoHorário);
        prof1.adicionarAula(testeDeSoftwareTerçaFeiraSegundoHorário);
        
        Professor prof2 = new Professor("Ana Claudia", "anarossi");
        prof2.adicionarAula(processosEPadrõesSegundaFeiraPrimeiroHorário);
        prof2.adicionarAula(processosEPadrõesQuartaFeiraTerceiroHorário);
        
        Professor prof3 = new Professor("Vilar", "vilar");        
        prof3.adicionarAula(programaçãoFuncionalQuartaFeiraTerceiroHorário);
                
        Professor prof4 = new Professor("Gaston", "gaston");
        
        Professor prof5 = new Professor("Denise", "denise");
        prof5.adicionarAula(sistemasConcorrentesSegundaFeiraSegundoHorário);
                
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


