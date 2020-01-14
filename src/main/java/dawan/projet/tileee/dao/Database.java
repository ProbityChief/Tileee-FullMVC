package dawan.projet.tileee.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	    public static Connection getConnection() throws ClassNotFoundException, SQLException {
	        Connection connection = null;
	        
	        Properties p = new Properties();
	        try {
	                p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/application.properties"));
	                Class.forName( p.getProperty("driver"));
	                connection = DriverManager.getConnection(
	                                p.getProperty("url"), 
	                                p.getProperty("user"), 
	                                p.getProperty("psw"));
	                        return connection;
	        } catch(ClassNotFoundException e) {
	                return null;
	        } catch(Exception e) {
	                return null;
	        }
	    }
}