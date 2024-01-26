/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heyner.rivas
 */
class SqlUsuarios extends conectar{
    public boolean registrar(usuarios usr)
    {
        PreparedStatement ps = null;
        Connection con =  getConnection();
        
        String sql ="INSERT INTO usuarios (id, usuario, clave, tipo) VALUES (?,?,?,?)";
        
        try {
            ps =con.prepareStatement(sql);
            ps.setString(1, usr.getId());
            ps.setString(2, usr.getUsuario());
            ps.setString(3, usr.getClave());
            ps.setString(4, usr.getTipo());
            ps.execute();
            return true;

            
        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
        
    
}
 public int existeUsuario(String usuarios){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();
        
        String sql ="SELECT count(id) FROM usuarios WHERE usuario = ?";
        
        try {
            ps =con.prepareStatement(sql);
            
            ps.setString(1, usuarios);
            rs = ps.executeQuery();
            
         
            if(rs.next())
            {
                return rs.getInt(1);
            }   
            return 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            
            return 1;
        }
        
        
        
    }
 
  public boolean login(usuarios usr){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();
        
        String sql ="SELECT id, usuario, clave, tipo FROM usuarios WHERE usuario = ?";
        
        try {
            ps =con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            rs = ps.executeQuery();
            
         
            if(rs.next())
            {
                //if(usr.getClave().equals(rs.getString(3))){
                if(usr.getClave().equals(rs.getString(4))){
                 
                    usr.setId(rs.getString(1));
                    usr.setUsuario(rs.getString(2));
                    usr.setClave(rs.getString(3));
                    usr.setTipo(rs.getString(4));
                    return true; 
                }else {
                return false;
                }
            }   
            return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
        
        
        
    }
    
}
