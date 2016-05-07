package connectionprovider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import util.PropertiesLookup;

public class ConnectionProvider {
	public static Connection getConnection() throws URISyntaxException,
			SQLException, FileNotFoundException, IOException {
		PropertiesLookup propertiesLookup = new PropertiesLookup();
        try {
        	//properties.load(new FileReader("db.properties"));
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        	String userName = propertiesLookup.getProperty("dbusername");
        	String password = propertiesLookup.getProperty("dbpassword");
			String dbUrl = "jdbc:postgresql://localhost:5432/saasApp";
			System.out.println(dbUrl);
		return DriverManager.getConnection(dbUrl, "postgres", "endless15");
	}

	public static void main(String[] args) {
		try {
			Connection connection = getConnection();
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
