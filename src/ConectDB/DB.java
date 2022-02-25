/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MSI
 */
public class DB {
     public static Connection getSQLServerConnection()
         throws SQLException, ClassNotFoundException {
     String hostName = "localhost";
     String sqlInstanceName = "DESKTOP-E7TCU4F";
     String database = "QUANLYDOAN";
     String userName = "sa";
     String password = "12345";
 
     return getSQLServerConnection(hostName, sqlInstanceName,
             database, userName, password);
 }
public static Connection getSQLServerConnection(String hostName,
        String sqlInstanceName, String database, String userName,String password) throws ClassNotFoundException, SQLException {
     // Khai báo class Driver cho DB SQLServer
     // Việc này cần thiết với Java 5
     // Java6 tự động tìm kiếm Driver thích hợp.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 
      //Cấu trúc URL Connection dành cho SQLServer
     // Ví dụ:
       // jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=simplehr
        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433"
             + ";instance=" + sqlInstanceName + ";databaseName=" + database;
        Connection conn = DriverManager.getConnection(connectionURL, userName,password);
        return conn;
 }

}
