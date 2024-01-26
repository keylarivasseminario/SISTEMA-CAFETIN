package sistema;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author heyner.rivas
 */
public class conectar {
    public Statement sentencia;
    public ResultSet resultado;
    
     private static Connection conn;
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String user="root";
    private static final String password="";
    private static final String url="jdbc:mysql://localhost:3306/cafetin_pablo";
    
     public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    
    public conectar() {
        
        conn=null;
        try {
        Class.forName(driver); 
         conn =DriverManager.getConnection(url, user, password); 
         if(conn!=null){
             System.out.print("CONEXION ESTABLECIDA");
         }
        }catch(ClassNotFoundException | SQLException e){
            System.out.print("error al conectar"+e);}}
    
    public Connection getConnection(){
       return conn;
         }  
    
    public void desconectar(){
        conn =null;
    }

    Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }}
        
    
    

