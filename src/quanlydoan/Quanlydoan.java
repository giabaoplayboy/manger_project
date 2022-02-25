/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlydoan;

import ConectDB.ConnectionUtil;
import GUI.LOGIN;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author MSI
 */
public class Quanlydoan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        LOGIN login = new LOGIN();
        login.setVisible(true);
//        Connection conn = ConnectionUtil.getMyConnection();
//        System.out.println("GetConnection:"+conn);
//        System.out.println("Connection Succcess");
    }
    
}
