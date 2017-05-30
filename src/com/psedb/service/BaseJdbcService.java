package com.psedb.service;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseJdbcService {
    
private final String HOST="localhost";
private final String DATABASE="pspdb";
private final String USERNAME="root";
private final String PASSWORD="test";

    protected Connection getDbConnection() {
        Connection conn = null;
        try {
            Class.forName(Driver.class.getName());
            conn = DriverManager.getConnection("jdbc:mysql://"+HOST+":3306/"+DATABASE, USERNAME, PASSWORD);
        } catch (Exception ex) {
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    protected void sqlCleanup(ResultSet rs,PreparedStatement pstm,Connection conn) {
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(pstm != null){
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
