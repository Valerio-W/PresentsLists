package be.walbert.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import be.walbert.Connection.ProjectConnection;

public class ProjectConnection {
	private static Connection instance = null;
	
	private ProjectConnection(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String ip = "193.190.64.10";
			String port = "1522";
			String service_name = "XEPDB1";
			String chaineConnexion = "jdbc:oracle:thin:@//"+ip+":"+port+"/"+service_name;
			String username = "student03_03";
			String password = "changeme";
			 
			instance = DriverManager.getConnection(
					chaineConnexion,
					username,
					password
					);
		}
		catch(ClassNotFoundException ex){
			JOptionPane.showMessageDialog(null, "Driver class not found" + ex.getMessage());
			System.exit(0);	
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "JDBC Error: " + ex.getMessage());
		}
		if (instance == null) {
            JOptionPane.showMessageDialog(null, "The database is inaccessible, closing the program.");
            System.exit(0);
        }
	}
	
	public static Connection getInstance() {
		if(instance == null){
			new ProjectConnection();
		}
		return instance;
	}
	
 
}
