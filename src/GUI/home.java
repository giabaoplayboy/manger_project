/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import ConectDB.ConnectionUtil;
import static GUI.GV_HV_CN_GUI.txtmsgvcu;
import static GUI.insertsv.txtmssvcu;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Cell;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.DeTai;
import model.GiaoVien;
import model.Gv_HDDT;
import model.SinhVien;
import model.GV_HV_CN;
import model.Gv_PBDT;
import model.Gv_UVDT;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author MSI
 */
public final class home extends javax.swing.JFrame {

    /**
     * Creates new form home
     */
    
    CardLayout cardLayout;
    CardLayout cardLayout1;
    CardLayout cardLayout2;
    public ArrayList<SinhVien> sinhvien;
    public ArrayList<GiaoVien> listgiaovien;
    public ArrayList<GV_HV_CN> listgiaovienhvcn;
    public ArrayList<Gv_HDDT> listgiaovienhddt;
    public ArrayList<Gv_PBDT> listgiaovienpbdt;
    public ArrayList<Gv_UVDT> listgiaovienuvdt;
    public ArrayList<DeTai> listdetai;
    public home() throws SQLException, ClassNotFoundException {
        initComponents();
        cardLayout = (CardLayout)(cardlayout.getLayout());
        cardLayout1 = (CardLayout)(menutab.getLayout());
        cardLayout2 = (CardLayout)(cardqldtsv.getLayout());
        labelxoa.setVisible(false);
        cbmssv.setVisible(false);
        btndelete.setVisible(false);
        btnreturn.setVisible(false);
        btnrefresh.setVisible(false);
        sinhvien = new ArrayList();
        listgiaovien = new ArrayList();
        listgiaovienhvcn = new ArrayList();
        listgiaovienhddt = new ArrayList();
        listgiaovienpbdt = new ArrayList();
        listgiaovienuvdt = new ArrayList();
        listdetai = new ArrayList();
        loadbangsinhvien(tablesinhvien);
        loadbanggiaovien(tablegv);
        showGVcomboboxmssv(cbmssv);
        showGVcomboboxmsgv(cbmsgv);
    }
    public void loadbangdetai(JTable table, ArrayList list)
    {
        list.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select * from DETAI";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                DeTai dt = new DeTai(rs.getString("MSDT"),rs.getString("TENDT"));
                list.add(dt);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[2];
            columnsName[0] = "MSDT";
            columnsName[1] = "DETAI";
            
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[3];
            for(int i=0;i<listdetai.size();i++)
            {
                row[0]= listdetai.get(i).getMsdt().trim();
                row[1]= listdetai.get(i).getTendt().trim();
                
                model.addRow(row);
            }
            table.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadbanggv_uvdt(JTable table, ArrayList list)
    {
        list.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select DISTINCT GV.TENGV, DT.TENDT, DIEMUVDT from GIAOVIEN GV, DETAI DT, GV_UVDT"
                               + " Where GV_UVDT.MSGV = GV.MSGV AND DT.MSDT =  GV_UVDT.MSDT ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                Gv_UVDT gvhddt = new  Gv_UVDT(rs.getString("TENGV"),rs.getString("TENDT"),rs.getFloat("DIEMUVDT"));
                list.add(gvhddt);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[3];
            columnsName[0] = "TENGV";
            columnsName[1] = "DETAI";
            columnsName[2] = "DIEM";
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[3];
            for(int i=0;i<listgiaovienuvdt.size();i++)
            {
                row[0]= listgiaovienuvdt.get(i).getMsgv().trim();
                row[1]= listgiaovienuvdt.get(i).getMsdt().trim();
                row[2]= listgiaovienuvdt.get(i).getDiemuvdt();
                model.addRow(row);
            }
            tablegvuvdt.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadbanggv_pbdt(JTable table, ArrayList list)
    {
        list.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select DISTINCT GV.TENGV, DT.TENDT, DIEMPBDT from GIAOVIEN GV, DETAI DT, GV_PBDT"
                               + " Where GV_PBDT.MSGV = GV.MSGV AND DT.MSDT =  GV_PBDT.MSDT ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                Gv_PBDT gvhddt = new  Gv_PBDT(rs.getString("TENGV"),rs.getString("TENDT"),rs.getFloat("DIEMPBDT"));
                list.add(gvhddt);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[3];
            columnsName[0] = "TENGV";
            columnsName[1] = "DETAI";
            columnsName[2] = "DIEM";
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[3];
            for(int i=0;i<listgiaovienpbdt.size();i++)
            {
                row[0]= listgiaovienpbdt.get(i).getMsgv().trim();
                row[1]= listgiaovienpbdt.get(i).getMsdt().trim();
                row[2]= listgiaovienpbdt.get(i).getDiemhddt();
                model.addRow(row);
            }
            tablegv_pb_dt.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadbanggv_hddt(JTable table, ArrayList list)
    {
        list.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select DISTINCT GV.TENGV, DT.TENDT, DIEMHDDT from GIAOVIEN GV, DETAI DT, GV_HDDT"
                               + " Where GV_HDDT.MSGV = GV.MSGV AND DT.MSDT =  GV_HDDT.MSDT ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                Gv_HDDT gvhddt = new  Gv_HDDT(rs.getString("TENGV"),rs.getString("TENDT"),rs.getFloat("DIEMHDDT"));
                list.add(gvhddt);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[3];
            columnsName[0] = "TENGV";
            columnsName[1] = "DETAI";
            columnsName[2] = "DIEM";
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[3];
            for(int i=0;i<listgiaovienhddt.size();i++)
            {
                row[0]= listgiaovienhddt.get(i).getMsgv().trim();
                row[1]= listgiaovienhddt.get(i).getMsdt().trim();
                row[2]= listgiaovienhddt.get(i).getDiemhddt();
                model.addRow(row);
            }
            tablegvhddt.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadbanggv_hv_cn(JTable table)
    {
        listgiaovienhvcn.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select GV.TENGV, CN.TENCN, HV.TENHV, NAM from GIAOVIEN GV, CHUYENNGANH CN, HOCVI HV, GV_HV_CN"
                               + " Where GV_HV_CN.MSGV = GV.MSGV AND GV_HV_CN.MSCN = CN.MSCN AND GV_HV_CN.MSHV = HV.MSHV";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                GV_HV_CN gvhvcn = new GV_HV_CN(rs.getString("TENGV"),rs.getString("TENCN"),rs.getString("TENHV"), rs.getString("NAM"));
                listgiaovienhvcn.add(gvhvcn);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[4];
            columnsName[0] = "TENGV";
            columnsName[1] = "TENCN";
            columnsName[2] = "TENHV";
            columnsName[3] = "NAM";
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[4];
            for(int i=0;i<listgiaovienhvcn.size();i++)
            {
                row[0]= listgiaovienhvcn.get(i).getMsgv().trim();
                row[1]= listgiaovienhvcn.get(i).getMscn().trim();
                row[2]= listgiaovienhvcn.get(i).getMshv().trim();
                row[3]= listgiaovienhvcn.get(i).getNam().trim();
                model.addRow(row);
            }
            tablegv_hv_cn.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadbanggiaovien(JTable table)
    {
        listgiaovien.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select * from GIAOVIEN ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                GiaoVien gv = new GiaoVien(rs.getString("MSGV"),rs.getString("MSHH"),rs.getString("TENGV"),rs.getString("DIACHI_GV"),rs.getString("SODT_GV"),rs.getString("NAMHH"));
                listgiaovien.add(gv);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[6];
            columnsName[0] = "MSGV";
            columnsName[1] = "MSHH";
            columnsName[2] = "TENGV";
            columnsName[3] = "DIACHI_GV";
            columnsName[4] = "SODT_GV";
            columnsName[5] = "NAMHH";
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[6];
            for(int i=0;i<listgiaovien.size();i++)
            {
                row[0]= listgiaovien.get(i).getMsgv().trim();
                row[1]= listgiaovien.get(i).getMshh().trim();
                row[2]= listgiaovien.get(i).getTengv().trim();
                row[3]= listgiaovien.get(i).getDiachi_gv().trim();
                row[4]= listgiaovien.get(i).getSodt_gv().trim();
                row[5]= listgiaovien.get(i).getNamhh().trim();
                model.addRow(row);
            }
            tablegv.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadbangsinhvien(JTable table)
    {
        sinhvien.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select * from SINHVIEN ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                SinhVien sv = new SinhVien(rs.getString("MSSV"),rs.getString("TENSV"),rs.getString("LOP"),rs.getString("SODT"),rs.getString("DIACHI"));
                sinhvien.add(sv);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[5];
            columnsName[0] = "MSSV";
            columnsName[1] = "TENSV";
            columnsName[2] = "LOP";
            columnsName[3] = "SODT";
            columnsName[4] = "DIACHI";
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[5];
            for(int i=0;i<sinhvien.size();i++)
            {
                row[0]= sinhvien.get(i).getMssv().trim();
                row[1]= sinhvien.get(i).getTensv().trim();
                row[2]= sinhvien.get(i).getLop().trim();
                row[3]= sinhvien.get(i).getSodt().trim();
                row[4]= sinhvien.get(i).getDiachi().trim();
                model.addRow(row);
            }
            tablesinhvien.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showGVcombobotxtmsgvxoa(JComboBox tenbien) throws SQLException, ClassNotFoundException
    {
        tenbien.removeAllItems();
        Connection conn =  ConnectionUtil.getMyConnection();
        Statement st = conn.createStatement();
        String sqlcheck="select MSGV from GV_HV_CN";
        ResultSet rs=st.executeQuery(sqlcheck);
        while(rs.next()){
            cbmsgvxoa.addItem(rs.getString("MSGV"));
        } 
    }
    private void showGVcombobogvhddt(JComboBox tenbien) throws SQLException, ClassNotFoundException
    {
        tenbien.removeAllItems();
        Connection conn =  ConnectionUtil.getMyConnection();
        Statement st = conn.createStatement();
        String sqlcheck="select MSGV from GV_HDDT";
        ResultSet rs=st.executeQuery(sqlcheck);
        while(rs.next()){
            cbmsgvhddt.addItem(rs.getString("MSGV"));
        } 
    }
    private void showGVcombobogvpbdt(JComboBox tenbien) throws SQLException, ClassNotFoundException
    {
        tenbien.removeAllItems();
        Connection conn =  ConnectionUtil.getMyConnection();
        Statement st = conn.createStatement();
        String sqlcheck="select MSGV from GV_PBDT";
        ResultSet rs=st.executeQuery(sqlcheck);
        while(rs.next()){
            cbmsgvpbdt.addItem(rs.getString("MSGV"));
        } 
    }
    private void showGVcombobogvuvdt(JComboBox tenbien) throws SQLException, ClassNotFoundException
    {
        tenbien.removeAllItems();
        Connection conn =  ConnectionUtil.getMyConnection();
        Statement st = conn.createStatement();
        String sqlcheck="select MSGV from GV_UVDT";
        ResultSet rs=st.executeQuery(sqlcheck);
        while(rs.next()){
            cbmsgvuvdt.addItem(rs.getString("MSGV"));
        } 
    }
    public void exportToExcel() throws FileNotFoundException
    {
        sinhvien.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select * from SINHVIEN ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                SinhVien sv = new SinhVien(rs.getString("MSSV"),rs.getString("TENSV"),rs.getString("LOP"),rs.getString("SODT"),rs.getString("DIACHI"));
                sinhvien.add(sv);
            }
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("SinhVien");
            int rowNum = 0;
            XSSFRow firstRow = sheet.createRow(rowNum++);
            XSSFRow seccondRow = sheet.createRow(rowNum++);
            XSSFCell firstCell = firstRow.createCell(0);
            firstCell.setCellValue("List of Sinh Viên");
            XSSFCell col1 = seccondRow.createCell(0);
            XSSFCell col2 = seccondRow.createCell(1);
            XSSFCell col3 = seccondRow.createCell(2);
            XSSFCell col4 = seccondRow.createCell(3); 
            XSSFCell col5 = seccondRow.createCell(4);
            col1.setCellValue("MSSV");  
            col2.setCellValue("TENSV");
            col3.setCellValue("LOP");
            col4.setCellValue("SODT");
            col5.setCellValue("DIACHI");
            for (SinhVien sv : sinhvien) {
                XSSFRow row = sheet.createRow(rowNum++);
                XSSFCell cell1 = row.createCell(0);
                cell1.setCellValue(sv.getMssv());
                XSSFCell cell2 = row.createCell(1);
                cell2.setCellValue(sv.getTensv());
                XSSFCell cell3 = row.createCell(2);
                cell3.setCellValue(sv.getLop());
                XSSFCell cell4 = row.createCell(3);
                cell4.setCellValue(sv.getSodt());
                XSSFCell cell5 = row.createCell(4);
                cell5.setCellValue(sv.getDiachi());
            }
            try {
               
                FileOutputStream outputStream = new FileOutputStream("F:DSSV.xlsx");
                wb.write(outputStream);
                wb.close();
                JOptionPane.showMessageDialog(null, "Đã export thành công!!!!!");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Export không thành công!!!!!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public void exportDSGV() throws FileNotFoundException
    {
        listgiaovien.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select MSGV,TENHH,TENGV,DIACHI_GV,SODT_GV,NAMHH from GIAOVIEN, HOCHAM WHERE GIAOVIEN.MSHH = HOCHAM.MSHH ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                GiaoVien gv = new GiaoVien(rs.getString("MSGV"),rs.getString("TENHH"),rs.getString("TENGV"),rs.getString("DIACHI_GV"),rs.getString("SODT_GV"),rs.getString("NAMHH"));
                listgiaovien.add(gv);
            }
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("DSGV");
            int rowNum = 0;
            XSSFRow firstRow = sheet.createRow(rowNum++);
            XSSFRow seccondRow = sheet.createRow(rowNum++);
            XSSFCell firstCell = firstRow.createCell(0);
            firstCell.setCellValue("Danh Sách Giáo Viên");
            XSSFCell col1 = seccondRow.createCell(0);
            XSSFCell col2 = seccondRow.createCell(1);
            XSSFCell col3 = seccondRow.createCell(2);
            XSSFCell col4 = seccondRow.createCell(3); 
            XSSFCell col5 = seccondRow.createCell(4);
            XSSFCell col6 = seccondRow.createCell(5);
            col1.setCellValue("MSGV");  
            col2.setCellValue("HOCHAM");
            col3.setCellValue("TENGV");
            col4.setCellValue("DIACHI_GV");
            col5.setCellValue("SODT_GV");
            col6.setCellValue("NAMHH");
            for (GiaoVien gv : listgiaovien) {
                XSSFRow row = sheet.createRow(rowNum++);
                XSSFCell cell1 = row.createCell(0);
                cell1.setCellValue(gv.getMsgv());
                XSSFCell cell2 = row.createCell(1);
                cell2.setCellValue(gv.getMshh());
                XSSFCell cell3 = row.createCell(2);
                cell3.setCellValue(gv.getTengv());
                XSSFCell cell4 = row.createCell(3);
                cell4.setCellValue(gv.getDiachi_gv());
                XSSFCell cell5 = row.createCell(4);
                cell5.setCellValue(gv.getSodt_gv());
                XSSFCell cell6 = row.createCell(5);
                cell6.setCellValue(gv.getNamhh());
            }
            try {
               
                FileOutputStream outputStream = new FileOutputStream("F:DSGV.xlsx");
                wb.write(outputStream);
                wb.close();
                JOptionPane.showMessageDialog(null, "Đã export thành công!!!!!");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Export không thành công!!!!!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void exportDSGV_HV_CN() throws FileNotFoundException
    {
        listgiaovienhvcn.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select GV.TENGV, CN.TENCN, HV.TENHV, NAM from GIAOVIEN GV, CHUYENNGANH CN, HOCVI HV, GV_HV_CN"
                               + " Where GV_HV_CN.MSGV = GV.MSGV AND GV_HV_CN.MSCN = CN.MSCN AND GV_HV_CN.MSHV = HV.MSHV";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                GV_HV_CN gvhvcn = new GV_HV_CN(rs.getString("TENGV"),rs.getString("TENCN"),rs.getString("TENHV"), rs.getString("NAM"));
                listgiaovienhvcn.add(gvhvcn);
            }
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("DSGV_HV_CN");
            int rowNum = 0;
            XSSFRow firstRow = sheet.createRow(rowNum++);
            XSSFRow seccondRow = sheet.createRow(rowNum++);
            XSSFCell firstCell = firstRow.createCell(0);
            firstCell.setCellValue("Danh Sách Giáo Viên");
            XSSFCell col1 = seccondRow.createCell(0);
            XSSFCell col2 = seccondRow.createCell(1);
            XSSFCell col3 = seccondRow.createCell(2);
            XSSFCell col4 = seccondRow.createCell(3); 
            col1.setCellValue("TENGV");  
            col2.setCellValue("TENCN");
            col3.setCellValue("TENHV");
            col4.setCellValue("NAM");
            for (GV_HV_CN gvhvcn : listgiaovienhvcn) {
                XSSFRow row = sheet.createRow(rowNum++);
                XSSFCell cell1 = row.createCell(0);
                cell1.setCellValue(gvhvcn.getMsgv());
                XSSFCell cell2 = row.createCell(1);
                cell2.setCellValue(gvhvcn.getMscn());
                XSSFCell cell3 = row.createCell(2);
                cell3.setCellValue(gvhvcn.getMshv());
                XSSFCell cell4 = row.createCell(3);
                cell4.setCellValue(gvhvcn.getNam());
            }
            try {
                FileOutputStream outputStream = new FileOutputStream("F:DSGV_HV_CN.xlsx");
                wb.write(outputStream);
                wb.close();
                JOptionPane.showMessageDialog(null, "Đã export thành công!!!!!");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Export không thành công!!!!!");
            } catch (IOException e) {
            }
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Export không thành công!!!!!");
        }
    }
    private void showGVcomboboxmssv(JComboBox tenbien) throws SQLException, ClassNotFoundException
    {
        tenbien.removeAllItems();
        Connection conn =  ConnectionUtil.getMyConnection();
        Statement st = conn.createStatement();
        String sqlcheck="select mssv from SINHVIEN";
        ResultSet rs=st.executeQuery(sqlcheck);
        while(rs.next()){
            cbmssv.addItem(rs.getString("MSSV"));
        } 
    }
    private void showGVcomboboxmsgv(JComboBox tenbien) throws SQLException, ClassNotFoundException
    {
        tenbien.removeAllItems();
        Connection conn =  ConnectionUtil.getMyConnection();
        Statement st = conn.createStatement();
        String sqlcheck="select msgv from GIAOVIEN";
        ResultSet rs=st.executeQuery(sqlcheck);
        while(rs.next()){
            cbmsgv.addItem(rs.getString("MSGV"));
        } 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        cardtracuu = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        bg = new javax.swing.JPanel();
        sidepane = new javax.swing.JPanel();
        btnqlsv = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnhome = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnqlgv = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnqldt = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnqlhd = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btntracuu = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtaccount = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        btnexit = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txttenuser = new javax.swing.JLabel();
        cardlayout = new javax.swing.JPanel();
        cardhome = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cardqlsv = new javax.swing.JPanel();
        btnexportsv = new javax.swing.JLabel();
        btndelete = new javax.swing.JLabel();
        btnthemsv = new javax.swing.JLabel();
        btnrefreshsv = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablesinhvien = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnsuasv1 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        cbmssv = new javax.swing.JComboBox();
        btnxoa1 = new javax.swing.JLabel();
        labelxoa = new javax.swing.JLabel();
        btnreturn = new javax.swing.JLabel();
        btnrefresh = new javax.swing.JLabel();
        cardqlgv = new javax.swing.JPanel();
        menutab = new javax.swing.JPanel();
        gv_uvdt = new javax.swing.JPanel();
        btnexportsv7 = new javax.swing.JLabel();
        btndelete7 = new javax.swing.JLabel();
        btnthemsv7 = new javax.swing.JLabel();
        btnrefreshsv7 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablegvuvdt = new javax.swing.JTable();
        jSeparator9 = new javax.swing.JSeparator();
        btnsuasv8 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        cbmsgvuvdt = new javax.swing.JComboBox();
        btnxoa8 = new javax.swing.JLabel();
        labelxoa7 = new javax.swing.JLabel();
        btnreturn7 = new javax.swing.JLabel();
        btnrefresh7 = new javax.swing.JLabel();
        gv_pbdt = new javax.swing.JPanel();
        btnexportsv6 = new javax.swing.JLabel();
        btndelete6 = new javax.swing.JLabel();
        btnthemsv6 = new javax.swing.JLabel();
        btnrefreshsv6 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablegv_pb_dt = new javax.swing.JTable();
        jSeparator8 = new javax.swing.JSeparator();
        btnsuasv7 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        cbmsgvpbdt = new javax.swing.JComboBox();
        btnxoa7 = new javax.swing.JLabel();
        labelxoa6 = new javax.swing.JLabel();
        btnreturn6 = new javax.swing.JLabel();
        btnrefresh6 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        gv_hhdt = new javax.swing.JPanel();
        btnexportsv5 = new javax.swing.JLabel();
        btndelete5 = new javax.swing.JLabel();
        btnthemsv5 = new javax.swing.JLabel();
        btnrefreshsv5 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablegvhddt = new javax.swing.JTable();
        jSeparator7 = new javax.swing.JSeparator();
        btnsuasv6 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        cbmsgvhddt = new javax.swing.JComboBox();
        btnxoa6 = new javax.swing.JLabel();
        labelxoa5 = new javax.swing.JLabel();
        btnreturn5 = new javax.swing.JLabel();
        btnrefresh5 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        gv_hv_cn = new javax.swing.JPanel();
        btnexportsv4 = new javax.swing.JLabel();
        btndelete4 = new javax.swing.JLabel();
        btnthemsv4 = new javax.swing.JLabel();
        btnrefreshsv4 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablegv_hv_cn = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        btnsuasv5 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        cbmsgvxoa = new javax.swing.JComboBox();
        btnxoa5 = new javax.swing.JLabel();
        labelxoa4 = new javax.swing.JLabel();
        btnreturn4 = new javax.swing.JLabel();
        btnrefresh4 = new javax.swing.JLabel();
        tabqlgv = new javax.swing.JPanel();
        btnexportsv1 = new javax.swing.JLabel();
        btndelete1 = new javax.swing.JLabel();
        btnthemsv1 = new javax.swing.JLabel();
        btnrefreshsv1 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablegv = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        btnsuasv2 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        cbmsgv = new javax.swing.JComboBox();
        btnxoa2 = new javax.swing.JLabel();
        labelxoa1 = new javax.swing.JLabel();
        btnreturn1 = new javax.swing.JLabel();
        btnrefresh1 = new javax.swing.JLabel();
        navigator = new javax.swing.JPanel();
        btngv_hv_cn = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        btngv = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        btngv_hddt = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        btngv_pbdt = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        btngv_uvdt = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        cardqldt = new javax.swing.JPanel();
        navigator1 = new javax.swing.JPanel();
        btnsv_detai = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        btndetai = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        cardqldtsv = new javax.swing.JPanel();
        card1 = new javax.swing.JPanel();
        btnexportdt = new javax.swing.JLabel();
        btndelete8 = new javax.swing.JLabel();
        btnthemdt = new javax.swing.JLabel();
        btnrefreshdt = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabledt = new javax.swing.JTable();
        jSeparator10 = new javax.swing.JSeparator();
        btnsuadt = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        cbmsdt = new javax.swing.JComboBox();
        btnxoadt = new javax.swing.JLabel();
        labelxoa8 = new javax.swing.JLabel();
        btnreturn8 = new javax.swing.JLabel();
        btnrefresh8 = new javax.swing.JLabel();
        card2 = new javax.swing.JPanel();
        btnexportsv9 = new javax.swing.JLabel();
        btndelete9 = new javax.swing.JLabel();
        btnthemsv9 = new javax.swing.JLabel();
        btnrefreshsv9 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablegvuvdt2 = new javax.swing.JTable();
        jSeparator11 = new javax.swing.JSeparator();
        btnsuasv10 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        cbmsgvuvdt2 = new javax.swing.JComboBox();
        btnxoa10 = new javax.swing.JLabel();
        labelxoa9 = new javax.swing.JLabel();
        btnreturn9 = new javax.swing.JLabel();
        btnrefresh9 = new javax.swing.JLabel();
        cardqlhd = new javax.swing.JPanel();
        btnexportsv3 = new javax.swing.JLabel();
        btndelete3 = new javax.swing.JLabel();
        btnthemsv3 = new javax.swing.JLabel();
        btnrefreshsv3 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablesinhvien3 = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();
        btnsuasv4 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        cbmssv3 = new javax.swing.JComboBox();
        btnxoa4 = new javax.swing.JLabel();
        labelxoa3 = new javax.swing.JLabel();
        btnreturn3 = new javax.swing.JLabel();
        btnrefresh3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        cardtracuu.setBackground(new java.awt.Color(102, 102, 255));
        cardtracuu.setLayout(new java.awt.GridLayout(2, 2));

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/find_user_100pxdttn.png"))); // NOI18N
        cardtracuu.add(jLabel41);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/find_user_100pxdiem.png"))); // NOI18N
        cardtracuu.add(jLabel39);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/find_user_100pxed.png"))); // NOI18N
        cardtracuu.add(jLabel40);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidepane.setBackground(new java.awt.Color(255, 204, 204));
        sidepane.setPreferredSize(new java.awt.Dimension(370, 720));
        sidepane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnqlsv.setBackground(new java.awt.Color(255, 204, 204));
        btnqlsv.setPreferredSize(new java.awt.Dimension(370, 70));
        btnqlsv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnqlsvFocusGained(evt);
            }
        });
        btnqlsv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnqlsvMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnqlsvMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnqlsvMouseReleased(evt);
            }
        });
        btnqlsv.setLayout(new java.awt.CardLayout());

        jLabel4.setBackground(new java.awt.Color(255, 153, 255));
        jLabel4.setFont(new java.awt.Font("UTM Times", 1, 25)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Quản Lý Người Dùng");
        btnqlsv.add(jLabel4, "card2");
        jLabel4.getAccessibleContext().setAccessibleDescription("");

        sidepane.add(btnqlsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, -1, 70));
        btnqlsv.getAccessibleContext().setAccessibleName(""); // NOI18N
        btnqlsv.getAccessibleContext().setAccessibleDescription("");

        btnhome.setBackground(new java.awt.Color(255, 242, 242));
        btnhome.setPreferredSize(new java.awt.Dimension(370, 70));
        btnhome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhomeMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnhomeMouseReleased(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setBackground(new java.awt.Color(0, 0, 51));
        jLabel2.setFont(new java.awt.Font("UTM Times", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 102));
        jLabel2.setText("Trang chủ");

        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout btnhomeLayout = new javax.swing.GroupLayout(btnhome);
        btnhome.setLayout(btnhomeLayout);
        btnhomeLayout.setHorizontalGroup(
            btnhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnhomeLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(370, 370, 370))
        );
        btnhomeLayout.setVerticalGroup(
            btnhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnhomeLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(btnhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sidepane.add(btnhome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, 70));

        btnqlgv.setBackground(new java.awt.Color(255, 204, 204));
        btnqlgv.setPreferredSize(new java.awt.Dimension(370, 70));
        btnqlgv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnqlgvMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnqlgvMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnqlgvMouseReleased(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel6.setFont(new java.awt.Font("UTM Times", 1, 25)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Quản Trị Hệ Thống");

        javax.swing.GroupLayout btnqlgvLayout = new javax.swing.GroupLayout(btnqlgv);
        btnqlgv.setLayout(btnqlgvLayout);
        btnqlgvLayout.setHorizontalGroup(
            btnqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnqlgvLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel5)
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        btnqlgvLayout.setVerticalGroup(
            btnqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnqlgvLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(btnqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnqlgvLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnqlgvLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap())))
        );

        sidepane.add(btnqlgv, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, -1, 70));

        btnqldt.setBackground(new java.awt.Color(255, 204, 204));
        btnqldt.setPreferredSize(new java.awt.Dimension(370, 70));
        btnqldt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnqldtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnqldtMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnqldtMouseReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("UTM Times", 1, 25)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 0, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Thống kê báo cáo");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout btnqldtLayout = new javax.swing.GroupLayout(btnqldt);
        btnqldt.setLayout(btnqldtLayout);
        btnqldtLayout.setHorizontalGroup(
            btnqldtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnqldtLayout.createSequentialGroup()
                .addGroup(btnqldtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnqldtLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel7))
                    .addGroup(btnqldtLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel8)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        btnqldtLayout.setVerticalGroup(
            btnqldtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnqldtLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        sidepane.add(btnqldt, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, -1, 70));

        btnqlhd.setBackground(new java.awt.Color(255, 204, 204));
        btnqlhd.setPreferredSize(new java.awt.Dimension(370, 70));
        btnqlhd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnqlhdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnqlhdMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnqlhdMouseReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("UTM Times", 1, 25)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 0, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("In Ấn");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout btnqlhdLayout = new javax.swing.GroupLayout(btnqlhd);
        btnqlhd.setLayout(btnqlhdLayout);
        btnqlhdLayout.setHorizontalGroup(
            btnqlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnqlhdLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel9)
                .addGap(106, 106, 106)
                .addComponent(jLabel10)
                .addContainerGap(161, Short.MAX_VALUE))
        );
        btnqlhdLayout.setVerticalGroup(
            btnqlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnqlhdLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel9)
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnqlhdLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        sidepane.add(btnqlhd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, -1, 70));

        btntracuu.setBackground(new java.awt.Color(255, 204, 204));
        btntracuu.setPreferredSize(new java.awt.Dimension(370, 70));
        btntracuu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btntracuuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btntracuuMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btntracuuMouseReleased(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel12.setFont(new java.awt.Font("UTM Times", 1, 25)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 0, 102));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tra cứu");

        javax.swing.GroupLayout btntracuuLayout = new javax.swing.GroupLayout(btntracuu);
        btntracuu.setLayout(btntracuuLayout);
        btntracuuLayout.setHorizontalGroup(
            btntracuuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btntracuuLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel12)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        btntracuuLayout.setVerticalGroup(
            btntracuuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btntracuuLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(btntracuuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        sidepane.add(btntracuu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, -1, 70));

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jLabel13.setFont(new java.awt.Font("UTM Helve", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 204));
        jLabel13.setText("QUẢN LÝ THƯ VIỆN");

        jLabel14.setFont(new java.awt.Font("UTM Helve", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("VÀ MƯỢN TRẢ SÁCH");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel13))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel14)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        sidepane.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 130));

        txtaccount.setText("account");
        sidepane.add(txtaccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 700, -1, -1));

        bg.add(sidepane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 720));

        header.setBackground(new java.awt.Color(0, 153, 153));

        btnexit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit_100px.png"))); // NOI18N
        btnexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexitMouseClicked(evt);
            }
        });
        btnexit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexitKeyPressed(evt);
            }
        });

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_male_circle_100px.png"))); // NOI18N

        txttenuser.setFont(new java.awt.Font("UTM Helve", 1, 24)); // NOI18N
        txttenuser.setForeground(new java.awt.Color(255, 255, 255));
        txttenuser.setText("Nhóm 01 ( PHẦN MỀM QUẢN LÝ THƯ VIỆN )");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(txttenuser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(31, 31, 31))
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(txttenuser, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bg.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 910, 130));

        cardlayout.setLayout(new java.awt.CardLayout());

        cardhome.setBackground(new java.awt.Color(255, 242, 242));
        cardhome.setLayout(new java.awt.GridLayout(3, 4, -20, 5));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nguoidung.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
        });
        cardhome.add(jLabel15);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ht.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        cardhome.add(jLabel16);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/baocao.png"))); // NOI18N
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        cardhome.add(jLabel17);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/inan.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        cardhome.add(jLabel18);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tracuu.png"))); // NOI18N
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        cardhome.add(jLabel19);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/caidat.png"))); // NOI18N
        cardhome.add(jLabel20);
        cardhome.add(jLabel21);
        cardhome.add(jLabel22);
        cardhome.add(jLabel23);

        cardlayout.add(cardhome, "cardhome");

        cardqlsv.setBackground(new java.awt.Color(153, 255, 204));

        btnexportsv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsvMouseClicked(evt);
            }
        });
        btnexportsv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsvKeyPressed(evt);
            }
        });

        btndelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndeleteMouseClicked(evt);
            }
        });

        btnthemsv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsvMouseClicked(evt);
            }
        });

        btnrefreshsv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsvMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Thêm");

        jLabel30.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Xuất excel");

        jLabel31.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Xóa");

        jLabel32.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Refresh");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablesinhvien.setAutoCreateRowSorter(true);
        tablesinhvien.setBackground(new java.awt.Color(255, 242, 242));
        tablesinhvien.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablesinhvien.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablesinhvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "TenDangNhap", "HoTen", "ChucDanh", "GioiTinh", "Email", "SĐT"
            }
        ));
        tablesinhvien.setCellSelectionEnabled(true);
        tablesinhvien.setGridColor(new java.awt.Color(255, 242, 242));
        tablesinhvien.setInheritsPopupMenu(true);
        tablesinhvien.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablesinhvien.setRowHeight(25);
        tablesinhvien.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane1.setViewportView(tablesinhvien);
        tablesinhvien.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv1MouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Sửa");

        cbmssv.setBackground(new java.awt.Color(255, 242, 242));
        cbmssv.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbmssv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmssvActionPerformed(evt);
            }
        });

        btnxoa1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa1MouseClicked(evt);
            }
        });

        labelxoa.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa.setForeground(new java.awt.Color(51, 51, 255));
        labelxoa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa.setText("Cần Xóa Người Dùng: ");

        btnreturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturnMouseClicked(evt);
            }
        });

        btnrefresh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout cardqlsvLayout = new javax.swing.GroupLayout(cardqlsv);
        cardqlsv.setLayout(cardqlsvLayout);
        cardqlsvLayout.setHorizontalGroup(
            cardqlsvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardqlsvLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(cardqlsvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(cardqlsvLayout.createSequentialGroup()
                        .addComponent(btnthemsv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29)
                        .addGap(35, 35, 35)
                        .addComponent(btnsuasv1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addGap(77, 77, 77)
                        .addComponent(btnxoa1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel31)
                        .addGap(65, 65, 65)
                        .addComponent(btnrefreshsv)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)
                        .addGap(41, 41, 41)
                        .addComponent(btnexportsv)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cardqlsvLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(labelxoa)
                        .addGap(18, 18, 18)
                        .addComponent(cbmssv, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnrefresh)
                        .addGap(18, 18, 18)
                        .addComponent(btnreturn))
                    .addComponent(jSeparator1))
                .addGap(46, 46, 46))
        );
        cardqlsvLayout.setVerticalGroup(
            cardqlsvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardqlsvLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(cardqlsvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel32)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel29)
                    .addComponent(btnthemsv)
                    .addComponent(btnrefreshsv, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv1)
                    .addComponent(jLabel33)
                    .addComponent(btnxoa1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardqlsvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa)
                    .addComponent(cbmssv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        cardlayout.add(cardqlsv, "cardqlsv");

        cardqlgv.setBackground(new java.awt.Color(255, 242, 242));
        cardqlgv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menutab.setBackground(new java.awt.Color(204, 204, 255));
        menutab.setLayout(new java.awt.CardLayout());

        gv_uvdt.setBackground(new java.awt.Color(251, 147, 172));

        btnexportsv7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsv7MouseClicked(evt);
            }
        });
        btnexportsv7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsv7KeyPressed(evt);
            }
        });

        btndelete7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete7MouseClicked(evt);
            }
        });

        btnthemsv7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsv7MouseClicked(evt);
            }
        });

        btnrefreshsv7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsv7MouseClicked(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("Thêm");

        jLabel66.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("Xuất excel");

        jLabel67.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("Xóa");

        jLabel68.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setText("Refresh");

        jScrollPane8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablegvuvdt.setAutoCreateRowSorter(true);
        tablegvuvdt.setBackground(new java.awt.Color(155, 199, 104));
        tablegvuvdt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablegvuvdt.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablegvuvdt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MAPHAT", "SOTHE", "LIDOVIPHAM", "HINHTHUCXULY", "NGAYXULY", "NGUOIXULY"
            }
        ));
        tablegvuvdt.setCellSelectionEnabled(true);
        tablegvuvdt.setGridColor(new java.awt.Color(255, 242, 242));
        tablegvuvdt.setInheritsPopupMenu(true);
        tablegvuvdt.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablegvuvdt.setRowHeight(25);
        tablegvuvdt.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane8.setViewportView(tablegvuvdt);
        tablegvuvdt.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv8MouseClicked(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("Sửa");

        cbmsgvuvdt.setBackground(new java.awt.Color(255, 242, 242));
        cbmsgvuvdt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        btnxoa8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa8MouseClicked(evt);
            }
        });

        labelxoa7.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa7.setForeground(new java.awt.Color(255, 255, 255));
        labelxoa7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa7.setText("Người vi phạm cần xóa: ");

        btnreturn7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn7MouseClicked(evt);
            }
        });

        btnrefresh7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout gv_uvdtLayout = new javax.swing.GroupLayout(gv_uvdt);
        gv_uvdt.setLayout(gv_uvdtLayout);
        gv_uvdtLayout.setHorizontalGroup(
            gv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gv_uvdtLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(gv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(gv_uvdtLayout.createSequentialGroup()
                            .addComponent(btnthemsv7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel65)
                            .addGap(35, 35, 35)
                            .addComponent(btnsuasv8)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel69)
                            .addGap(77, 77, 77)
                            .addComponent(btnxoa8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel67)
                            .addGap(65, 65, 65)
                            .addComponent(btnrefreshsv7)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel68)
                            .addGap(41, 41, 41)
                            .addComponent(btnexportsv7)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel66))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gv_uvdtLayout.createSequentialGroup()
                            .addGap(95, 95, 95)
                            .addComponent(labelxoa7)
                            .addGap(18, 18, 18)
                            .addComponent(cbmsgvuvdt, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btndelete7)
                            .addGap(18, 18, 18)
                            .addComponent(btnrefresh7)
                            .addGap(18, 18, 18)
                            .addComponent(btnreturn7))
                        .addComponent(jSeparator9))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        gv_uvdtLayout.setVerticalGroup(
            gv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gv_uvdtLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(gv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel68)
                    .addComponent(jLabel66)
                    .addComponent(jLabel67)
                    .addComponent(jLabel65)
                    .addComponent(btnthemsv7)
                    .addComponent(btnrefreshsv7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv8)
                    .addComponent(jLabel69)
                    .addComponent(btnxoa8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa7)
                    .addComponent(cbmsgvuvdt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        menutab.add(gv_uvdt, "cardgv_uvdt");

        gv_pbdt.setBackground(new java.awt.Color(253, 216, 76));

        btnexportsv6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsv6MouseClicked(evt);
            }
        });
        btnexportsv6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsv6KeyPressed(evt);
            }
        });

        btndelete6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete6MouseClicked(evt);
            }
        });

        btnthemsv6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsv6MouseClicked(evt);
            }
        });

        btnrefreshsv6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsv6MouseClicked(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(102, 0, 0));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Thêm");

        jLabel61.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(102, 0, 0));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("Xuất excel");

        jLabel62.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(102, 0, 0));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Xóa");

        jLabel63.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(102, 0, 0));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Refresh");

        jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablegv_pb_dt.setAutoCreateRowSorter(true);
        tablegv_pb_dt.setBackground(new java.awt.Color(253, 216, 76));
        tablegv_pb_dt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablegv_pb_dt.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablegv_pb_dt.setForeground(new java.awt.Color(153, 0, 0));
        tablegv_pb_dt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MACABIET", "MATL", "NGAYCN", "NGUOICN", "TINHTRANG"
            }
        ));
        tablegv_pb_dt.setCellSelectionEnabled(true);
        tablegv_pb_dt.setGridColor(new java.awt.Color(255, 242, 242));
        tablegv_pb_dt.setInheritsPopupMenu(true);
        tablegv_pb_dt.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablegv_pb_dt.setRowHeight(25);
        tablegv_pb_dt.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane7.setViewportView(tablegv_pb_dt);
        tablegv_pb_dt.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv7MouseClicked(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(102, 0, 0));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Sửa");

        cbmsgvpbdt.setBackground(new java.awt.Color(255, 242, 242));
        cbmsgvpbdt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        btnxoa7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa7MouseClicked(evt);
            }
        });

        labelxoa6.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa6.setForeground(new java.awt.Color(102, 0, 0));
        labelxoa6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa6.setText("Cần Xóa Tài Liệu Chi Tiết");

        btnreturn6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn6MouseClicked(evt);
            }
        });

        btnrefresh6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh6MouseClicked(evt);
            }
        });

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/change_employee_male_100px.png"))); // NOI18N
        jLabel43.setText("PB>>HD");
        jLabel43.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout gv_pbdtLayout = new javax.swing.GroupLayout(gv_pbdt);
        gv_pbdt.setLayout(gv_pbdtLayout);
        gv_pbdtLayout.setHorizontalGroup(
            gv_pbdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gv_pbdtLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(gv_pbdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane7)
                    .addGroup(gv_pbdtLayout.createSequentialGroup()
                        .addComponent(btnthemsv6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel60)
                        .addGap(35, 35, 35)
                        .addComponent(btnsuasv7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel64)
                        .addGap(77, 77, 77)
                        .addComponent(btnxoa7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel62)
                        .addGap(65, 65, 65)
                        .addComponent(btnrefreshsv6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel63)
                        .addGap(41, 41, 41)
                        .addComponent(btnexportsv6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel61))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gv_pbdtLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(labelxoa6)
                        .addGap(18, 18, 18)
                        .addComponent(cbmsgvpbdt, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete6)
                        .addGap(18, 18, 18)
                        .addComponent(btnrefresh6)
                        .addGap(18, 18, 18)
                        .addComponent(btnreturn6))
                    .addComponent(jSeparator8))
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gv_pbdtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        gv_pbdtLayout.setVerticalGroup(
            gv_pbdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gv_pbdtLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(gv_pbdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel63)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62)
                    .addComponent(jLabel60)
                    .addComponent(btnthemsv6)
                    .addComponent(btnrefreshsv6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv7)
                    .addComponent(jLabel64)
                    .addComponent(btnxoa7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gv_pbdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa6)
                    .addComponent(cbmsgvpbdt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel43)
                .addGap(98, 98, 98))
        );

        menutab.add(gv_pbdt, "cardgv_pbdt");

        gv_hhdt.setBackground(new java.awt.Color(187, 157, 207));

        btnexportsv5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsv5MouseClicked(evt);
            }
        });
        btnexportsv5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsv5KeyPressed(evt);
            }
        });

        btndelete5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete5MouseClicked(evt);
            }
        });

        btnthemsv5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsv5MouseClicked(evt);
            }
        });

        btnrefreshsv5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsv5MouseClicked(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(102, 0, 0));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Thêm");

        jLabel56.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(102, 0, 0));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Xuất excel");

        jLabel57.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(102, 0, 0));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Xóa");

        jLabel58.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(102, 0, 0));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Refresh");

        jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablegvhddt.setAutoCreateRowSorter(true);
        tablegvhddt.setBackground(new java.awt.Color(155, 199, 104));
        tablegvhddt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablegvhddt.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablegvhddt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_MUONTRA", "SOTHE", "MACABIET", "KIEUMUON", "NAGYMUON", "NGUOICHOMUON", "HANTRA", "NGAYTRA", "NGUOINHAN"
            }
        ));
        tablegvhddt.setCellSelectionEnabled(true);
        tablegvhddt.setGridColor(new java.awt.Color(255, 242, 242));
        tablegvhddt.setInheritsPopupMenu(true);
        tablegvhddt.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablegvhddt.setRowHeight(25);
        tablegvhddt.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane6.setViewportView(tablegvhddt);
        tablegvhddt.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv6MouseClicked(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(102, 0, 0));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Sửa");

        cbmsgvhddt.setBackground(new java.awt.Color(255, 242, 242));
        cbmsgvhddt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        btnxoa6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa6MouseClicked(evt);
            }
        });

        labelxoa5.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa5.setForeground(new java.awt.Color(102, 0, 0));
        labelxoa5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa5.setText("Cần Xóa Người Mượn Trả:");

        btnreturn5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn5MouseClicked(evt);
            }
        });

        btnrefresh5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh5MouseClicked(evt);
            }
        });

        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/change_employee_male_100px.png"))); // NOI18N
        jLabel42.setText("HD>>PB");
        jLabel42.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout gv_hhdtLayout = new javax.swing.GroupLayout(gv_hhdt);
        gv_hhdt.setLayout(gv_hhdtLayout);
        gv_hhdtLayout.setHorizontalGroup(
            gv_hhdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gv_hhdtLayout.createSequentialGroup()
                .addGroup(gv_hhdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gv_hhdtLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(gv_hhdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gv_hhdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(gv_hhdtLayout.createSequentialGroup()
                                    .addComponent(btnthemsv5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel55)
                                    .addGap(35, 35, 35)
                                    .addComponent(btnsuasv6)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel59)
                                    .addGap(77, 77, 77)
                                    .addComponent(btnxoa6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel57)
                                    .addGap(65, 65, 65)
                                    .addComponent(btnrefreshsv5)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel58)
                                    .addGap(41, 41, 41)
                                    .addComponent(btnexportsv5)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel56))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gv_hhdtLayout.createSequentialGroup()
                                    .addGap(95, 95, 95)
                                    .addComponent(labelxoa5)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbmsgvhddt, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btndelete5)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnrefresh5)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnreturn5))
                                .addComponent(jSeparator7))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(gv_hhdtLayout.createSequentialGroup()
                        .addGap(703, 703, 703)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        gv_hhdtLayout.setVerticalGroup(
            gv_hhdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gv_hhdtLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(gv_hhdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel58)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57)
                    .addComponent(jLabel55)
                    .addComponent(btnthemsv5)
                    .addComponent(btnrefreshsv5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv6)
                    .addComponent(jLabel59)
                    .addComponent(btnxoa6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gv_hhdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa5)
                    .addComponent(cbmsgvhddt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel42)
                .addGap(97, 97, 97))
        );

        menutab.add(gv_hhdt, "cardgv_hddt");

        gv_hv_cn.setBackground(new java.awt.Color(255, 0, 255));

        btnexportsv4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsv4MouseClicked(evt);
            }
        });
        btnexportsv4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsv4KeyPressed(evt);
            }
        });

        btndelete4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btndelete4MouseEntered(evt);
            }
        });

        btnthemsv4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsv4MouseClicked(evt);
            }
        });

        btnrefreshsv4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsv4MouseClicked(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 0, 0));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Thêm");

        jLabel51.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 0, 0));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Xuất excel");

        jLabel52.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 0, 0));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Xóa");

        jLabel53.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(102, 0, 0));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Refresh");

        jScrollPane5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablegv_hv_cn.setAutoCreateRowSorter(true);
        tablegv_hv_cn.setBackground(new java.awt.Color(253, 199, 212));
        tablegv_hv_cn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablegv_hv_cn.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablegv_hv_cn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "SoThe", "HoTen", "NgaySinh", "GioiTinh", "MaChucNang", "TaiKhoan"
            }
        ));
        tablegv_hv_cn.setCellSelectionEnabled(true);
        tablegv_hv_cn.setGridColor(new java.awt.Color(255, 242, 242));
        tablegv_hv_cn.setInheritsPopupMenu(true);
        tablegv_hv_cn.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablegv_hv_cn.setRowHeight(25);
        tablegv_hv_cn.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane5.setViewportView(tablegv_hv_cn);
        tablegv_hv_cn.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv5MouseClicked(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(102, 0, 0));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Sửa");

        cbmsgvxoa.setBackground(new java.awt.Color(255, 242, 242));
        cbmsgvxoa.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        btnxoa5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa5MouseClicked(evt);
            }
        });

        labelxoa4.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa4.setForeground(new java.awt.Color(102, 0, 0));
        labelxoa4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa4.setText("Độc Giả cần xóa: ");

        btnreturn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn4MouseClicked(evt);
            }
        });

        btnrefresh4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout gv_hv_cnLayout = new javax.swing.GroupLayout(gv_hv_cn);
        gv_hv_cn.setLayout(gv_hv_cnLayout);
        gv_hv_cnLayout.setHorizontalGroup(
            gv_hv_cnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gv_hv_cnLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(gv_hv_cnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5)
                    .addGroup(gv_hv_cnLayout.createSequentialGroup()
                        .addComponent(btnthemsv4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel50)
                        .addGap(35, 35, 35)
                        .addComponent(btnsuasv5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel54)
                        .addGap(77, 77, 77)
                        .addComponent(btnxoa5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel52)
                        .addGap(65, 65, 65)
                        .addComponent(btnrefreshsv4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel53)
                        .addGap(41, 41, 41)
                        .addComponent(btnexportsv4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel51))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gv_hv_cnLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(labelxoa4)
                        .addGap(18, 18, 18)
                        .addComponent(cbmsgvxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete4)
                        .addGap(18, 18, 18)
                        .addComponent(btnrefresh4)
                        .addGap(18, 18, 18)
                        .addComponent(btnreturn4))
                    .addComponent(jSeparator6))
                .addGap(46, 46, 46))
        );
        gv_hv_cnLayout.setVerticalGroup(
            gv_hv_cnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gv_hv_cnLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(gv_hv_cnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel53)
                    .addComponent(jLabel51)
                    .addComponent(jLabel52)
                    .addComponent(jLabel50)
                    .addComponent(btnthemsv4)
                    .addComponent(btnrefreshsv4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv5)
                    .addComponent(jLabel54)
                    .addComponent(btnxoa5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gv_hv_cnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa4)
                    .addComponent(cbmsgvxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        menutab.add(gv_hv_cn, "cardgv_hv_cn");

        tabqlgv.setBackground(new java.awt.Color(255, 204, 255));

        btnexportsv1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsv1MouseClicked(evt);
            }
        });
        btnexportsv1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsv1KeyPressed(evt);
            }
        });

        btndelete1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete1MouseClicked(evt);
            }
        });

        btnthemsv1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsv1MouseClicked(evt);
            }
        });

        btnrefreshsv1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsv1MouseClicked(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 0, 0));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Thêm");

        jLabel35.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 0, 0));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Xuất excel");

        jLabel36.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(102, 0, 0));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Xóa");

        jLabel37.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(102, 0, 0));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Refresh");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablegv.setAutoCreateRowSorter(true);
        tablegv.setBackground(new java.awt.Color(155, 199, 104));
        tablegv.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablegv.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablegv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MaTL", "TenTL", "MaKhoa", "MaPL", "MaTG", "MaNXB", "NamXB", "MaNgonNgu", "SoTrang", "MaViTri", "MaTang", "MatGiaSach", "GiaBia", "SoLuong", "MaTK"
            }
        ));
        tablegv.setCellSelectionEnabled(true);
        tablegv.setGridColor(new java.awt.Color(255, 242, 242));
        tablegv.setInheritsPopupMenu(true);
        tablegv.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablegv.setRowHeight(25);
        tablegv.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane2.setViewportView(tablegv);
        tablegv.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv2MouseClicked(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 0, 0));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Sửa");

        cbmsgv.setBackground(new java.awt.Color(255, 242, 242));
        cbmsgv.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbmsgv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmsgvActionPerformed(evt);
            }
        });

        btnxoa2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa2MouseClicked(evt);
            }
        });

        labelxoa1.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa1.setForeground(new java.awt.Color(102, 0, 0));
        labelxoa1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa1.setText("Tài Liệu Cần Xóa:");

        btnreturn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn1MouseClicked(evt);
            }
        });

        btnrefresh1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tabqlgvLayout = new javax.swing.GroupLayout(tabqlgv);
        tabqlgv.setLayout(tabqlgvLayout);
        tabqlgvLayout.setHorizontalGroup(
            tabqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabqlgvLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(tabqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(tabqlgvLayout.createSequentialGroup()
                        .addComponent(btnthemsv1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel34)
                        .addGap(35, 35, 35)
                        .addComponent(btnsuasv2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel38)
                        .addGap(77, 77, 77)
                        .addComponent(btnxoa2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36)
                        .addGap(65, 65, 65)
                        .addComponent(btnrefreshsv1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)
                        .addGap(41, 41, 41)
                        .addComponent(btnexportsv1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel35))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tabqlgvLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(labelxoa1)
                        .addGap(18, 18, 18)
                        .addComponent(cbmsgv, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete1)
                        .addGap(18, 18, 18)
                        .addComponent(btnrefresh1)
                        .addGap(18, 18, 18)
                        .addComponent(btnreturn1))
                    .addComponent(jSeparator2))
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabqlgvLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
        );
        tabqlgvLayout.setVerticalGroup(
            tabqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabqlgvLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(tabqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel37)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel34)
                    .addComponent(btnthemsv1)
                    .addComponent(btnrefreshsv1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv2)
                    .addComponent(jLabel38)
                    .addComponent(btnxoa2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabqlgvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa1)
                    .addComponent(cbmsgv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        menutab.add(tabqlgv, "cardgv");

        cardqlgv.add(menutab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 910, 520));

        navigator.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btngv_hv_cn.setBackground(new java.awt.Color(253, 199, 212));
        btngv_hv_cn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngv_hv_cnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngv_hv_cnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngv_hv_cnMouseExited(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("UTM Aptima", 1, 25)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 0, 51));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("QL_ĐOCGIA");

        javax.swing.GroupLayout btngv_hv_cnLayout = new javax.swing.GroupLayout(btngv_hv_cn);
        btngv_hv_cn.setLayout(btngv_hv_cnLayout);
        btngv_hv_cnLayout.setHorizontalGroup(
            btngv_hv_cnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngv_hv_cnLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );
        btngv_hv_cnLayout.setVerticalGroup(
            btngv_hv_cnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btngv_hv_cnLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(16, 16, 16))
        );

        navigator.add(btngv_hv_cn, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 0, 182, 70));

        btngv.setBackground(new java.awt.Color(155, 199, 104));
        btngv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngvMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngvMouseExited(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("UTM Aptima", 1, 25)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("TAI LIEU");

        javax.swing.GroupLayout btngvLayout = new javax.swing.GroupLayout(btngv);
        btngv.setLayout(btngvLayout);
        btngvLayout.setHorizontalGroup(
            btngvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btngvLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        btngvLayout.setVerticalGroup(
            btngvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btngvLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(16, 16, 16))
        );

        navigator.add(btngv, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 182, 70));

        btngv_hddt.setBackground(new java.awt.Color(187, 157, 207));
        btngv_hddt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngv_hddtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngv_hddtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngv_hddtMouseExited(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("UTM Aptima", 1, 25)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("QL_MUONTRA");

        javax.swing.GroupLayout btngv_hddtLayout = new javax.swing.GroupLayout(btngv_hddt);
        btngv_hddt.setLayout(btngv_hddtLayout);
        btngv_hddtLayout.setHorizontalGroup(
            btngv_hddtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngv_hddtLayout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        btngv_hddtLayout.setVerticalGroup(
            btngv_hddtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btngv_hddtLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addContainerGap())
        );

        navigator.add(btngv_hddt, new org.netbeans.lib.awtextra.AbsoluteConstraints(364, 0, 182, 70));

        btngv_pbdt.setBackground(new java.awt.Color(253, 216, 76));
        btngv_pbdt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngv_pbdtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngv_pbdtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngv_pbdtMouseExited(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("UTM Aptima", 1, 25)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(204, 0, 51));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Tiem TL");

        javax.swing.GroupLayout btngv_pbdtLayout = new javax.swing.GroupLayout(btngv_pbdt);
        btngv_pbdt.setLayout(btngv_pbdtLayout);
        btngv_pbdtLayout.setHorizontalGroup(
            btngv_pbdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngv_pbdtLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        btngv_pbdtLayout.setVerticalGroup(
            btngv_pbdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngv_pbdtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        navigator.add(btngv_pbdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 0, 182, 70));

        btngv_uvdt.setBackground(new java.awt.Color(251, 147, 172));
        btngv_uvdt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngv_uvdtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngv_uvdtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngv_uvdtMouseExited(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("UTM Aptima", 1, 25)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("XU LY VI PHAM");

        javax.swing.GroupLayout btngv_uvdtLayout = new javax.swing.GroupLayout(btngv_uvdt);
        btngv_uvdt.setLayout(btngv_uvdtLayout);
        btngv_uvdtLayout.setHorizontalGroup(
            btngv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btngv_uvdtLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel49))
        );
        btngv_uvdtLayout.setVerticalGroup(
            btngv_uvdtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngv_uvdtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        navigator.add(btngv_uvdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(728, 0, 182, 70));

        cardqlgv.add(navigator, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 70));

        cardlayout.add(cardqlgv, "cardqlgv");

        cardqldt.setBackground(new java.awt.Color(255, 255, 204));
        cardqldt.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navigator1.setBackground(new java.awt.Color(255, 242, 242));
        navigator1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnsv_detai.setBackground(new java.awt.Color(255, 255, 102));
        btnsv_detai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsv_detaiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnsv_detaiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnsv_detaiMouseExited(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("UTM Aptima", 1, 25)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Phiếu nhập");

        javax.swing.GroupLayout btnsv_detaiLayout = new javax.swing.GroupLayout(btnsv_detai);
        btnsv_detai.setLayout(btnsv_detaiLayout);
        btnsv_detaiLayout.setHorizontalGroup(
            btnsv_detaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsv_detaiLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnsv_detaiLayout.setVerticalGroup(
            btnsv_detaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnsv_detaiLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel70)
                .addGap(16, 16, 16))
        );

        navigator1.add(btnsv_detai, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 0, 182, 70));

        btndetai.setBackground(new java.awt.Color(204, 255, 255));
        btndetai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndetaiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btndetaiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btndetaiMouseExited(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("UTM Aptima", 1, 25)); // NOI18N
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("Nhà cung cấp");

        javax.swing.GroupLayout btndetaiLayout = new javax.swing.GroupLayout(btndetai);
        btndetai.setLayout(btndetaiLayout);
        btndetaiLayout.setHorizontalGroup(
            btndetaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btndetaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel71)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btndetaiLayout.setVerticalGroup(
            btndetaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btndetaiLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel71)
                .addGap(16, 16, 16))
        );

        navigator1.add(btndetai, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 182, 70));

        cardqldt.add(navigator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 70));

        cardqldtsv.setBackground(new java.awt.Color(255, 255, 204));
        cardqldtsv.setLayout(new java.awt.CardLayout());

        card1.setBackground(new java.awt.Color(204, 255, 255));

        btnexportdt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportdt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportdt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportdtMouseClicked(evt);
            }
        });
        btnexportdt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportdtKeyPressed(evt);
            }
        });

        btndelete8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete8MouseClicked(evt);
            }
        });

        btnthemdt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemdt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemdt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemdtMouseClicked(evt);
            }
        });

        btnrefreshdt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshdt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshdt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshdtMouseClicked(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("Thêm");

        jLabel73.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("Xuất excel");

        jLabel74.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("Xóa");

        jLabel75.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("Refresh");

        jScrollPane9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tabledt.setAutoCreateRowSorter(true);
        tabledt.setBackground(new java.awt.Color(155, 199, 104));
        tabledt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tabledt.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tabledt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MANHACC", "TENNHACC", "ĐIACHI", "SĐT"
            }
        ));
        tabledt.setCellSelectionEnabled(true);
        tabledt.setGridColor(new java.awt.Color(255, 242, 242));
        tabledt.setInheritsPopupMenu(true);
        tabledt.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabledt.setRowHeight(25);
        tabledt.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane9.setViewportView(tabledt);
        tabledt.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        btnsuadt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuadt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuadtMouseClicked(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setText("Sửa");

        cbmsdt.setBackground(new java.awt.Color(255, 242, 242));
        cbmsdt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        btnxoadt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoadt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoadt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoadtMouseClicked(evt);
            }
        });

        labelxoa8.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa8.setText("Nhà cung cấp cần xóa: ");

        btnreturn8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn8MouseClicked(evt);
            }
        });

        btnrefresh8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane9)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addComponent(btnthemdt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel72)
                        .addGap(35, 35, 35)
                        .addComponent(btnsuadt)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel76)
                        .addGap(77, 77, 77)
                        .addComponent(btnxoadt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel74)
                        .addGap(65, 65, 65)
                        .addComponent(btnrefreshdt)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel75)
                        .addGap(41, 41, 41)
                        .addComponent(btnexportdt)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel73))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, card1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(labelxoa8)
                        .addGap(18, 18, 18)
                        .addComponent(cbmsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete8)
                        .addGap(18, 18, 18)
                        .addComponent(btnrefresh8)
                        .addGap(18, 18, 18)
                        .addComponent(btnreturn8))
                    .addComponent(jSeparator10))
                .addGap(46, 46, 46))
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel75)
                    .addComponent(jLabel73)
                    .addComponent(jLabel74)
                    .addComponent(jLabel72)
                    .addComponent(btnthemdt)
                    .addComponent(btnrefreshdt, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportdt, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuadt)
                    .addComponent(jLabel76)
                    .addComponent(btnxoadt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa8)
                    .addComponent(cbmsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        cardqldtsv.add(card1, "cardqldt1");

        card2.setBackground(new java.awt.Color(255, 255, 102));

        btnexportsv9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsv9MouseClicked(evt);
            }
        });
        btnexportsv9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsv9KeyPressed(evt);
            }
        });

        btndelete9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete9MouseClicked(evt);
            }
        });

        btnthemsv9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsv9MouseClicked(evt);
            }
        });

        btnrefreshsv9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsv9MouseClicked(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("Thêm");

        jLabel78.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("Xuất excel");

        jLabel79.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setText("Xóa");

        jLabel80.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("Refresh");

        jScrollPane10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablegvuvdt2.setAutoCreateRowSorter(true);
        tablegvuvdt2.setBackground(new java.awt.Color(155, 199, 104));
        tablegvuvdt2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablegvuvdt2.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablegvuvdt2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MAPHIEUNHAP", "MATAILIEU", "MANHACC", "NGUOINHAP", "NGAYNHAP", "SOLUONG"
            }
        ));
        tablegvuvdt2.setCellSelectionEnabled(true);
        tablegvuvdt2.setGridColor(new java.awt.Color(255, 242, 242));
        tablegvuvdt2.setInheritsPopupMenu(true);
        tablegvuvdt2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablegvuvdt2.setRowHeight(25);
        tablegvuvdt2.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane10.setViewportView(tablegvuvdt2);
        tablegvuvdt2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv10MouseClicked(evt);
            }
        });

        jLabel81.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setText("Sửa");

        cbmsgvuvdt2.setBackground(new java.awt.Color(255, 242, 242));
        cbmsgvuvdt2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        btnxoa10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa10MouseClicked(evt);
            }
        });

        labelxoa9.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa9.setForeground(new java.awt.Color(255, 255, 255));
        labelxoa9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa9.setText("Phiếu nhập cần xóa: ");

        btnreturn9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn9MouseClicked(evt);
            }
        });

        btnrefresh9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout card2Layout = new javax.swing.GroupLayout(card2);
        card2.setLayout(card2Layout);
        card2Layout.setHorizontalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane10)
                    .addGroup(card2Layout.createSequentialGroup()
                        .addComponent(btnthemsv9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel77)
                        .addGap(35, 35, 35)
                        .addComponent(btnsuasv10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel81)
                        .addGap(77, 77, 77)
                        .addComponent(btnxoa10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel79)
                        .addGap(65, 65, 65)
                        .addComponent(btnrefreshsv9)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel80)
                        .addGap(41, 41, 41)
                        .addComponent(btnexportsv9)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel78))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, card2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(labelxoa9)
                        .addGap(18, 18, 18)
                        .addComponent(cbmsgvuvdt2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete9)
                        .addGap(18, 18, 18)
                        .addComponent(btnrefresh9)
                        .addGap(18, 18, 18)
                        .addComponent(btnreturn9))
                    .addComponent(jSeparator11))
                .addGap(46, 46, 46))
        );
        card2Layout.setVerticalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel80)
                    .addComponent(jLabel78)
                    .addComponent(jLabel79)
                    .addComponent(jLabel77)
                    .addComponent(btnthemsv9)
                    .addComponent(btnrefreshsv9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv10)
                    .addComponent(jLabel81)
                    .addComponent(btnxoa10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa9)
                    .addComponent(cbmsgvuvdt2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        cardqldtsv.add(card2, "cardqlsvdt");

        cardqldt.add(cardqldtsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 910, 530));

        cardlayout.add(cardqldt, "cardqldt");

        cardqlhd.setBackground(new java.awt.Color(0, 51, 51));
        cardqlhd.setForeground(new java.awt.Color(255, 255, 51));

        btnexportsv3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnexportsv3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microsoft_excel_2019_100px.png"))); // NOI18N
        btnexportsv3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexportsv3MouseClicked(evt);
            }
        });
        btnexportsv3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnexportsv3KeyPressed(evt);
            }
        });

        btndelete3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btndelete3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        btndelete3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndelete3MouseClicked(evt);
            }
        });

        btnthemsv3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnthemsv3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_row_100px.png"))); // NOI18N
        btnthemsv3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnthemsv3MouseClicked(evt);
            }
        });

        btnrefreshsv3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefreshsv3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_left_rotation_filled_100px.png"))); // NOI18N
        btnrefreshsv3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefreshsv3MouseClicked(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 51, 255));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Thêm");

        jLabel45.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(51, 51, 255));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Xuất excel");

        jLabel46.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(51, 51, 255));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Xóa");

        jLabel47.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(51, 51, 255));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Refresh");

        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablesinhvien3.setAutoCreateRowSorter(true);
        tablesinhvien3.setBackground(new java.awt.Color(255, 242, 242));
        tablesinhvien3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablesinhvien3.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tablesinhvien3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MATHANHLY", "MACABIET", "LYDOTHANHLY", "NGAYTHANHLY", "NGUOITHANHLY", "TINHTRANG"
            }
        ));
        tablesinhvien3.setCellSelectionEnabled(true);
        tablesinhvien3.setGridColor(new java.awt.Color(255, 242, 242));
        tablesinhvien3.setInheritsPopupMenu(true);
        tablesinhvien3.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablesinhvien3.setRowHeight(25);
        tablesinhvien3.setSelectionForeground(new java.awt.Color(102, 102, 255));
        jScrollPane4.setViewportView(tablesinhvien3);
        tablesinhvien3.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));

        btnsuasv4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnsuasv4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsuasv4MouseClicked(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(51, 51, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Sửa");

        cbmssv3.setBackground(new java.awt.Color(255, 242, 242));
        cbmssv3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbmssv3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmssv3ActionPerformed(evt);
            }
        });

        btnxoa4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnxoa4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_60px.png"))); // NOI18N
        btnxoa4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxoa4MouseClicked(evt);
            }
        });

        labelxoa3.setFont(new java.awt.Font("UTM Neo Sans Intel", 1, 18)); // NOI18N
        labelxoa3.setForeground(new java.awt.Color(51, 51, 255));
        labelxoa3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelxoa3.setText("Thanh lý tài liệu cần xóa:");

        btnreturn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnreturn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return_30px.png"))); // NOI18N
        btnreturn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreturn3MouseClicked(evt);
            }
        });

        btnrefresh3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnrefresh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/synchronize_30px.png"))); // NOI18N
        btnrefresh3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrefresh3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout cardqlhdLayout = new javax.swing.GroupLayout(cardqlhd);
        cardqlhd.setLayout(cardqlhdLayout);
        cardqlhdLayout.setHorizontalGroup(
            cardqlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardqlhdLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(cardqlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4)
                    .addGroup(cardqlhdLayout.createSequentialGroup()
                        .addComponent(btnthemsv3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel44)
                        .addGap(35, 35, 35)
                        .addComponent(btnsuasv4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel48)
                        .addGap(77, 77, 77)
                        .addComponent(btnxoa4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel46)
                        .addGap(65, 65, 65)
                        .addComponent(btnrefreshsv3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel47)
                        .addGap(41, 41, 41)
                        .addComponent(btnexportsv3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel45))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cardqlhdLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(labelxoa3)
                        .addGap(18, 18, 18)
                        .addComponent(cbmssv3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete3)
                        .addGap(18, 18, 18)
                        .addComponent(btnrefresh3)
                        .addGap(18, 18, 18)
                        .addComponent(btnreturn3))
                    .addComponent(jSeparator4))
                .addGap(46, 46, 46))
        );
        cardqlhdLayout.setVerticalGroup(
            cardqlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardqlhdLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(cardqlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel47)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46)
                    .addComponent(jLabel44)
                    .addComponent(btnthemsv3)
                    .addComponent(btnrefreshsv3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexportsv3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsuasv4)
                    .addComponent(jLabel48)
                    .addComponent(btnxoa4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardqlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelxoa3)
                    .addComponent(cbmssv3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnrefresh3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreturn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        cardlayout.add(cardqlhd, "cardqlhd");

        bg.add(cardlayout, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 910, 590));

        jMenuBar1.setBackground(new java.awt.Color(255, 204, 204));
        jMenuBar1.setBorder(null);
        jMenuBar1.setAlignmentY(0.5F);
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuBar1.setOpaque(false);
        jMenuBar1.setPreferredSize(new java.awt.Dimension(101, 25));

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(35, 20));

        jMenuItem1.setText("Import");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator5);

        jMenuItem2.setText("Export");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("About");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu3.setPreferredSize(new java.awt.Dimension(50, 19));

        jMenuItem3.setText("Info");
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnqlsvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnqlsvFocusGained
        this.setBackground(new Color(255,242,242));
        btnhome.setBackground(new Color(255,204,204));
    }//GEN-LAST:event_btnqlsvFocusGained

    private void btnqlsvMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlsvMouseReleased
        btnqlsv.setBackground(new Color(255,242,242));
        btnhome.setBackground(new Color(255,204,204));
        btnqldt.setBackground(new Color(255,204,204));
        btnqlgv.setBackground(new Color(255,204,204));
        btnqlhd.setBackground(new Color(255,204,204));
        btntracuu.setBackground(new Color(255,204,204));
    }//GEN-LAST:event_btnqlsvMouseReleased

    private void btnhomeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhomeMouseReleased
        btnqlsv.setBackground(new Color(255,204,204));
        btnhome.setBackground(new Color(255,242,242));
        btnqldt.setBackground(new Color(255,204,204));
        btnqlgv.setBackground(new Color(255,204,204));
        btnqlhd.setBackground(new Color(255,204,204));
        btntracuu.setBackground(new Color(255,204,204));
    }//GEN-LAST:event_btnhomeMouseReleased

    private void btnqlgvMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlgvMouseReleased
        btnqlsv.setBackground(new Color(255,204,204));
        btnhome.setBackground(new Color(255,204,204));
        btnqldt.setBackground(new Color(255,204,204));
        btnqlgv.setBackground(new Color(255,242,242));
        btnqlhd.setBackground(new Color(255,204,204));
        btntracuu.setBackground(new Color(255,204,204));
    }//GEN-LAST:event_btnqlgvMouseReleased

    private void btnqldtMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqldtMouseReleased
        btnqlsv.setBackground(new Color(255,204,204));
        btnhome.setBackground(new Color(255,204,204));
        btnqldt.setBackground(new Color(255,242,242));
        btnqlgv.setBackground(new Color(255,204,204));
        btnqlhd.setBackground(new Color(255,204,204));
        btntracuu.setBackground(new Color(255,204,204));
    }//GEN-LAST:event_btnqldtMouseReleased

    private void btnqlhdMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlhdMouseReleased
        btnqlsv.setBackground(new Color(255,204,204));
        btnhome.setBackground(new Color(255,204,204));
        btnqldt.setBackground(new Color(255,204,204));
        btnqlgv.setBackground(new Color(255,204,204));
        btnqlhd.setBackground(new Color(255,242,242));
        btntracuu.setBackground(new Color(255,204,204));
    }//GEN-LAST:event_btnqlhdMouseReleased

    private void btntracuuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntracuuMouseReleased
        btnqlsv.setBackground(new Color(255,204,204));
        btnhome.setBackground(new Color(255,204,204));
        btnqldt.setBackground(new Color(255,204,204));
        btnqlgv.setBackground(new Color(255,204,204));
        btnqlhd.setBackground(new Color(255,204,204));
        btntracuu.setBackground(new Color(255,242,242));
    }//GEN-LAST:event_btntracuuMouseReleased

    private void btnhomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhomeMouseClicked
        cardLayout.show(cardlayout, "cardhome");
    }//GEN-LAST:event_btnhomeMouseClicked

    private void btnqlsvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlsvMouseClicked
        cardLayout.show(cardlayout, "cardqlsv");
    }//GEN-LAST:event_btnqlsvMouseClicked

    private void btnqlgvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlgvMouseClicked
        cardLayout.show(cardlayout, "cardqlgv");
        cardLayout1.show(menutab, "cardgv");
        labelxoa1.setVisible(false);
        cbmsgv.setVisible(false);
        btndelete1.setVisible(false);
        btnreturn1.setVisible(false);
        btnrefresh1.setVisible(false);
    }//GEN-LAST:event_btnqlgvMouseClicked

    private void btnqldtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqldtMouseClicked
        cardLayout.show(cardlayout, "cardqldt");
        cardLayout2.show(cardqldtsv,"cardqldt1");
        labelxoa8.setVisible(false);
        cbmsdt.setVisible(false);
        btndelete8.setVisible(false);
        btnreturn8.setVisible(false);
        btnrefresh8.setVisible(false);
        loadbangdetai(tabledt, listdetai);
    }//GEN-LAST:event_btnqldtMouseClicked

    private void btnqlhdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlhdMouseClicked
        cardLayout.show(cardlayout, "cardqlhd");
    }//GEN-LAST:event_btnqlhdMouseClicked

    private void btntracuuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntracuuMouseClicked
        cardLayout.show(cardlayout, "cardtracuu");
    }//GEN-LAST:event_btntracuuMouseClicked

    private void btnexitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexitKeyPressed

    private void btnexitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexitMouseClicked
        int input = JOptionPane.showConfirmDialog(null, "Do you want to exit?");
        // 0=yes, 1=no, 2=cancel
        System.out.println(input);
        if(0 != input)
        {
            
        } else {
            System.exit(0);
        }
        
        
    }//GEN-LAST:event_btnexitMouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        btnqlsvMouseReleased(evt);
        btnqlsvMouseClicked(evt);
        
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        btnqlgvMouseReleased(evt);
        btnqlgvMouseClicked(evt);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        btnqldtMouseReleased(evt);
        btnqldtMouseClicked(evt);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        btnqlhdMouseReleased(evt);
        btnqlhdMouseClicked(evt);
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        btntracuuMouseReleased(evt);
        btntracuuMouseClicked(evt);
    }//GEN-LAST:event_jLabel19MouseClicked

    private void btnrefreshsvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsvMouseClicked
        sinhvien.clear();
        Connection conn;
        try {
            conn = ConnectionUtil.getMyConnection();
            Statement st = conn.createStatement();
            String sqlload = "Select * from SINHVIEN ";
            ResultSet rs = st.executeQuery(sqlload);
            while(rs.next()){
                SinhVien sv = new SinhVien(rs.getString("MSSV"),rs.getString("TENSV"),rs.getString("LOP"),rs.getString("SODT"),rs.getString("DIACHI"));
                sinhvien.add(sv);
            }
            DefaultTableModel model = new DefaultTableModel();
            Object[] columnsName = new Object[5];
            columnsName[0] = "MSSV";
            columnsName[1] = "TENSV";
            columnsName[2] = "LOP";
            columnsName[3] = "SODT";
            columnsName[4] = "DIACHI";
            model.setColumnIdentifiers(columnsName);
            Object[] row = new Object[5];
            for(int i=0;i<sinhvien.size();i++)
            {
                row[0]= sinhvien.get(i).getMssv().trim();
                row[1]= sinhvien.get(i).getTensv().trim();
                row[2]= sinhvien.get(i).getLop().trim();
                row[3]= sinhvien.get(i).getSodt().trim();
                row[4]= sinhvien.get(i).getDiachi().trim();
                model.addRow(row);
            }
            tablesinhvien.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnrefreshsvMouseClicked

    private void btnthemsvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsvMouseClicked
        insertsv sv = new insertsv();
        sv.setVisible(true);
        insertsv.btneditsv.setVisible(false);
        insertsv.nhan2.setVisible(false);
        insertsv.txtmssvcu.setVisible(false);
    }//GEN-LAST:event_btnthemsvMouseClicked

    private void btnhomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhomeMouseEntered
        btnhomeMouseReleased(evt);
    }//GEN-LAST:event_btnhomeMouseEntered

    private void btnqlsvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlsvMouseEntered
        btnqlsvMouseReleased(evt);
    }//GEN-LAST:event_btnqlsvMouseEntered

    private void btnqlgvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlgvMouseEntered
        btnqlgvMouseReleased(evt);
    }//GEN-LAST:event_btnqlgvMouseEntered

    private void btnqldtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqldtMouseEntered
        btnqldtMouseReleased(evt);
    }//GEN-LAST:event_btnqldtMouseEntered

    private void btnqlhdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnqlhdMouseEntered
        btnqlhdMouseReleased(evt);
    }//GEN-LAST:event_btnqlhdMouseEntered

    private void btntracuuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntracuuMouseEntered
        btntracuuMouseReleased(evt);
    }//GEN-LAST:event_btntracuuMouseEntered

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        
    }//GEN-LAST:event_jLabel15MouseEntered

    private void btndeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteMouseClicked
        try {
            String mssv = cbmssv.getSelectedItem().toString();
            System.out.print(mssv);
            Connection conn;
            conn = ConnectionUtil.getMyConnection();
            String sql = "{call xoa_sinhvien(?,?)}";
            CallableStatement cstm = conn.prepareCall(sql);
            cstm.setString(1, mssv);
            cstm.registerOutParameter(2, java.sql.Types.INTEGER);
            cstm.executeUpdate();
            int kq= cstm.getInt(2);//gọi kết quả trả về
            if(kq==1){
                JOptionPane.showMessageDialog(null,"Delete Complete!!!!");
            }else{
                JOptionPane.showMessageDialog(null,"Fail! Please check and delete againt!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndeleteMouseClicked

    private void btnexportsvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsvKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsvKeyPressed

    private void btnexportsvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsvMouseClicked
        try {
            exportToExcel();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnexportsvMouseClicked

    private void btnsuasv1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv1MouseClicked
        insertsv sv = new insertsv();
        sv.setVisible(true);
        insertsv.btninsertsv1.setVisible(false);
        insertsv.nhan1.setVisible(false);
        insertsv.labelmssv.setVisible(false);
    }//GEN-LAST:event_btnsuasv1MouseClicked

    private void btnxoa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa1MouseClicked
        labelxoa.setVisible(true);
        cbmssv.setVisible(true);
        btndelete.setVisible(true);
        btnreturn.setVisible(true);
        btnrefresh.setVisible(true);
    }//GEN-LAST:event_btnxoa1MouseClicked

    private void btnreturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturnMouseClicked
        labelxoa.setVisible(false);
        cbmssv.setVisible(false);
        btndelete.setVisible(false);
        btnrefresh.setVisible(false);
        btnreturn.setVisible(false);      
    }//GEN-LAST:event_btnreturnMouseClicked

    private void btnrefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshMouseClicked
        try {
            showGVcomboboxmssv(cbmssv);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnrefreshMouseClicked

    private void btngvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngvMouseClicked
        btngv.setBackground(new Color(155,199,104));
        cardLayout1.show(menutab, "cardgv");
        labelxoa1.setVisible(false);
        cbmsgv.setVisible(false);
        btndelete1.setVisible(false);
        btnreturn1.setVisible(false);
        btnrefresh1.setVisible(false);
    }//GEN-LAST:event_btngvMouseClicked

    private void btngv_hv_cnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_hv_cnMouseClicked
        btngv_hv_cn.setBackground(new Color(253,199,212));
        cardLayout1.show(menutab, "cardgv_hv_cn");
        labelxoa4.setVisible(false);
        cbmsgvxoa.setVisible(false);
        btndelete4.setVisible(false);
        btnreturn4.setVisible(false);
        btnrefresh4.setVisible(false);
        loadbanggv_hv_cn(tablegv_hv_cn);
    }//GEN-LAST:event_btngv_hv_cnMouseClicked

    private void btngv_hddtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_hddtMouseClicked
        try {
            btngv_hddt.setBackground(new Color(187,157,207));
            cardLayout1.show(menutab, "cardgv_hddt");
            labelxoa5.setVisible(false);
            cbmsgvhddt.setVisible(false);
            btndelete5.setVisible(false);
            btnreturn5.setVisible(false);
            btnrefresh5.setVisible(false);
            loadbanggv_hddt(tablegvhddt, listgiaovienhddt);
            showGVcombobogvhddt(cbmsgvhddt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngv_hddtMouseClicked

    private void btngv_pbdtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_pbdtMouseClicked
        try {
            btngv_pbdt.setBackground(new Color(253,216,76));
            cardLayout1.show(menutab, "cardgv_pbdt");
            labelxoa6.setVisible(false);
            cbmsgvpbdt.setVisible(false);
            btndelete6.setVisible(false);
            btnreturn6.setVisible(false);
            btnrefresh6.setVisible(false);
            showGVcombobogvpbdt(cbmsgvpbdt);
            loadbanggv_pbdt(tablegv_pb_dt, listgiaovienpbdt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngv_pbdtMouseClicked

    private void btngv_uvdtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_uvdtMouseClicked
        try {
            btngv_uvdt.setBackground(new Color(251,147,172));
            cardLayout1.show(menutab, "cardgv_uvdt");
            labelxoa7.setVisible(false);
            cbmsgvuvdt.setVisible(false);
            btndelete7.setVisible(false);
            btnreturn7.setVisible(false);
            btnrefresh7.setVisible(false);
            showGVcombobogvuvdt(cbmsgvuvdt);
            loadbanggv_uvdt(tablegvuvdt, listgiaovienuvdt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngv_uvdtMouseClicked

    private void btnexportsv1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsv1MouseClicked
        try {
            exportDSGV();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnexportsv1MouseClicked

    private void btnexportsv1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsv1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv1KeyPressed

    private void btndelete1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete1MouseClicked
        try {
            String msgv = cbmsgv.getSelectedItem().toString();
            System.out.print(msgv);
            Connection conn;
            conn = ConnectionUtil.getMyConnection();
            String sql = "{call xoagiaovien(?,?)}";
            CallableStatement cstm = conn.prepareCall(sql);
            cstm.setString(1,msgv );
            cstm.registerOutParameter(2, java.sql.Types.INTEGER);
            cstm.executeUpdate();
            int kq= cstm.getInt(2);//gọi kết quả trả về
            if(kq==1){
                JOptionPane.showMessageDialog(null,"Delete Complete!!!!");
            }else{
                JOptionPane.showMessageDialog(null,"Fail! Please check and delete againt!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndelete1MouseClicked

    private void btnthemsv1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsv1MouseClicked
        try {
            giaovien gv = new giaovien();
            gv.setVisible(true);
            giaovien.nhan2.setVisible(false);
            giaovien.btneditgv.setVisible(false);
            giaovien.txtmsgvcu.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnthemsv1MouseClicked

    private void btnrefreshsv1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsv1MouseClicked
        loadbanggiaovien(tablegv);
    }//GEN-LAST:event_btnrefreshsv1MouseClicked

    private void btnsuasv2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv2MouseClicked
        try {
            giaovien gv = new giaovien();
            gv.setVisible(true);
            giaovien.nhan1.setVisible(false);
            giaovien.btninsertgv.setVisible(false);
            giaovien.labelmsgv.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnsuasv2MouseClicked

    private void btnxoa2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa2MouseClicked
        labelxoa1.setVisible(true);
        cbmsgv.setVisible(true);
        btndelete1.setVisible(true);
        btnreturn1.setVisible(true);
        btnrefresh1.setVisible(true);
    }//GEN-LAST:event_btnxoa2MouseClicked

    private void btnreturn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn1MouseClicked
        labelxoa1.setVisible(false);
        cbmsgv.setVisible(false);
        btndelete1.setVisible(false);
        btnrefresh1.setVisible(false);
        btnreturn1.setVisible(false);  
    }//GEN-LAST:event_btnreturn1MouseClicked

    private void btnrefresh1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh1MouseClicked
        try {
            cbmsgv.removeAllItems();
            showGVcomboboxmsgv(cbmsgv);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnrefresh1MouseClicked

    private void btnexportsv3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsv3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv3MouseClicked

    private void btnexportsv3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsv3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv3KeyPressed

    private void btndelete3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btndelete3MouseClicked

    private void btnthemsv3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsv3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemsv3MouseClicked

    private void btnrefreshsv3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsv3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnrefreshsv3MouseClicked

    private void btnsuasv4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsuasv4MouseClicked

    private void btnxoa4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnxoa4MouseClicked

    private void btnreturn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnreturn3MouseClicked

    private void btnrefresh3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnrefresh3MouseClicked

    private void btngvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngvMouseEntered
       btngv.setBackground(new Color(116,38,57));
    }//GEN-LAST:event_btngvMouseEntered

    private void btngvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngvMouseExited
       btngv.setBackground(new Color(155,199,104)); 
    }//GEN-LAST:event_btngvMouseExited

    private void btngv_hv_cnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_hv_cnMouseEntered
        btngv_hv_cn.setBackground(new Color(116,38,57));
    }//GEN-LAST:event_btngv_hv_cnMouseEntered

    private void btngv_hv_cnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_hv_cnMouseExited
        btngv_hv_cn.setBackground(new Color(253,199,212));
    }//GEN-LAST:event_btngv_hv_cnMouseExited

    private void btngv_hddtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_hddtMouseEntered
        btngv_hddt.setBackground(new Color(116,38,57));
    }//GEN-LAST:event_btngv_hddtMouseEntered

    private void btngv_hddtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_hddtMouseExited
         btngv_hddt.setBackground(new Color(187,157,207));
    }//GEN-LAST:event_btngv_hddtMouseExited

    private void btngv_pbdtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_pbdtMouseEntered
        btngv_pbdt.setBackground(new Color(116,38,57));
    }//GEN-LAST:event_btngv_pbdtMouseEntered

    private void btngv_pbdtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_pbdtMouseExited
        btngv_pbdt.setBackground(new Color(253,216,76));
    }//GEN-LAST:event_btngv_pbdtMouseExited

    private void btngv_uvdtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_uvdtMouseEntered
       btngv_uvdt.setBackground(new Color(116,38,57));
    }//GEN-LAST:event_btngv_uvdtMouseEntered

    private void btngv_uvdtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngv_uvdtMouseExited
        btngv_uvdt.setBackground(new Color(251,147,172));
    }//GEN-LAST:event_btngv_uvdtMouseExited

    private void btnexportsv4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsv4MouseClicked
        try {
            exportDSGV_HV_CN();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnexportsv4MouseClicked

    private void btnexportsv4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsv4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv4KeyPressed

    private void btndelete4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete4MouseClicked
        try {
            String msgv = cbmsgvxoa.getSelectedItem().toString();
            System.out.print(msgv);
            Connection conn;
            conn = ConnectionUtil.getMyConnection();
            String sql = "{call xoagiaovien_hv_cn(?,?)}";
            CallableStatement cstm = conn.prepareCall(sql);
            cstm.setString(1,msgv );
            cstm.registerOutParameter(2, java.sql.Types.INTEGER);
            cstm.executeUpdate();
            int kq= cstm.getInt(2);//gọi kết quả trả về
            if(kq==1){
                JOptionPane.showMessageDialog(null,"Delete Complete!!!!");
            }else{
                JOptionPane.showMessageDialog(null,"Fail! Please check and delete againt!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndelete4MouseClicked

    private void btnthemsv4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsv4MouseClicked
        try {
            GV_HV_CN_GUI s1 = new GV_HV_CN_GUI();
            s1.setVisible(true);
            GV_HV_CN_GUI.nhan2.setVisible(false);
            GV_HV_CN_GUI.btneditgv.setVisible(false);
            GV_HV_CN_GUI.cbmscn.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnthemsv4MouseClicked

    private void btnrefreshsv4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsv4MouseClicked
        loadbanggv_hv_cn(tablegv_hv_cn);
    }//GEN-LAST:event_btnrefreshsv4MouseClicked

    private void btnsuasv5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv5MouseClicked
        try {
            GV_HV_CN_GUI s1 = new GV_HV_CN_GUI();
            s1.setVisible(true);
            GV_HV_CN_GUI.nhan1.setVisible(false);
            GV_HV_CN_GUI.btninsertgv.setVisible(false);
            GV_HV_CN_GUI.labelmsgv.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnsuasv5MouseClicked

    private void btnxoa5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa5MouseClicked
        try {
            labelxoa4.setVisible(true);
            cbmsgvxoa.setVisible(true);
            btndelete4.setVisible(true);
            btnreturn4.setVisible(true);
            btnrefresh4.setVisible(true);
            showGVcombobotxtmsgvxoa(cbmsgvxoa);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnxoa5MouseClicked
    
    private void btnreturn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn4MouseClicked
        labelxoa4.setVisible(false);
        cbmsgvxoa.setVisible(false);
        btndelete4.setVisible(false);
        btnrefresh4.setVisible(false);
        btnreturn4.setVisible(false); 
    }//GEN-LAST:event_btnreturn4MouseClicked

    private void btnrefresh4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh4MouseClicked
        try {
            showGVcombobotxtmsgvxoa(cbmsgvxoa);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnrefresh4MouseClicked

    private void btnexportsv5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsv5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv5MouseClicked

    private void btnexportsv5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsv5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv5KeyPressed

    private void btndelete5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete5MouseClicked
        try {
            String msgv = cbmsgvhddt.getSelectedItem().toString();
            System.out.print(msgv);
            Connection conn;
            conn = ConnectionUtil.getMyConnection();
            String sql = "{call xoagiaovien_hddt(?,?)}";
            CallableStatement cstm = conn.prepareCall(sql);
            cstm.setString(1,msgv );
            cstm.registerOutParameter(2, java.sql.Types.INTEGER);
            cstm.executeUpdate();
            int kq= cstm.getInt(2);//gọi kết quả trả về
            if(kq==1){
                JOptionPane.showMessageDialog(null,"Delete Complete!!!!");
            }else{
                JOptionPane.showMessageDialog(null,"Fail! Please check and delete againt!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndelete5MouseClicked

    private void btnthemsv5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsv5MouseClicked
        try {
            GV_HDDT_GUI gv = new  GV_HDDT_GUI();
            gv.setVisible(true);
            GV_HDDT_GUI.nhan2.setVisible(false);
            GV_HDDT_GUI.btneditgv.setVisible(false);
            GV_HDDT_GUI.txtmsgvcu.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnthemsv5MouseClicked

    private void btnrefreshsv5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsv5MouseClicked
        loadbanggv_hddt(tablegvhddt, listgiaovienhddt);
    }//GEN-LAST:event_btnrefreshsv5MouseClicked

    private void btnsuasv6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv6MouseClicked
        try {
            GV_HDDT_GUI gv = new  GV_HDDT_GUI();
            gv.setVisible(true);
            GV_HDDT_GUI.nhan1.setVisible(false);
            GV_HDDT_GUI.btninsertgv.setVisible(false);
            GV_HDDT_GUI.labelmsgv .setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnsuasv6MouseClicked

    private void btnxoa6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa6MouseClicked
        labelxoa5.setVisible(true);
        cbmsgvhddt.setVisible(true);
        btndelete5.setVisible(true);
        btnreturn5.setVisible(true);
        btnrefresh5.setVisible(true);
    }//GEN-LAST:event_btnxoa6MouseClicked

    private void btnreturn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn5MouseClicked
        labelxoa5.setVisible(false);
        cbmsgvhddt.setVisible(false);
        btndelete5.setVisible(false);
        btnreturn5.setVisible(false);
        btnrefresh5.setVisible(false);
    }//GEN-LAST:event_btnreturn5MouseClicked

    private void btnrefresh5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh5MouseClicked
        try {
            showGVcombobogvhddt(cbmsgvhddt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnrefresh5MouseClicked

    private void btnexportsv6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsv6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv6MouseClicked

    private void btnexportsv6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsv6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv6KeyPressed

    private void btndelete6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete6MouseClicked
        try {
            String msgv = cbmsgvpbdt.getSelectedItem().toString();
            System.out.print(msgv);
            Connection conn;
            conn = ConnectionUtil.getMyConnection();
            String sql = "{call xoagiaovien_pbdt(?,?)}";
            CallableStatement cstm = conn.prepareCall(sql);
            cstm.setString(1,msgv );
            cstm.registerOutParameter(2, java.sql.Types.INTEGER);
            cstm.executeUpdate();
            int kq= cstm.getInt(2);//gọi kết quả trả về
            if(kq==1){
                JOptionPane.showMessageDialog(null,"Delete Complete!!!!");
            }else{
                JOptionPane.showMessageDialog(null,"Fail! Please check and delete againt!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndelete6MouseClicked

    private void btnthemsv6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsv6MouseClicked
        try {
            GV_PBDT_GUI gv = new GV_PBDT_GUI();
            gv.setVisible(true);
            GV_PBDT_GUI.nhan2.setVisible(false);
            GV_PBDT_GUI.btneditgv.setVisible(false);
            GV_PBDT_GUI.txtmsgvcu.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnthemsv6MouseClicked

    private void btnrefreshsv6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsv6MouseClicked
        loadbanggv_pbdt(tablegv_pb_dt, listgiaovienpbdt);
    }//GEN-LAST:event_btnrefreshsv6MouseClicked

    private void btnsuasv7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv7MouseClicked
        try {
            GV_PBDT_GUI gv = new  GV_PBDT_GUI();
            gv.setVisible(true);
            GV_PBDT_GUI.nhan1.setVisible(false);
            GV_PBDT_GUI.btninsertgv.setVisible(false);
            GV_PBDT_GUI.labelmsgv .setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnsuasv7MouseClicked

    private void btnxoa7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa7MouseClicked
        labelxoa6.setVisible(true);
        cbmsgvpbdt.setVisible(true);
        btndelete6.setVisible(true);
        btnreturn6.setVisible(true);
        btnrefresh6.setVisible(true);
    }//GEN-LAST:event_btnxoa7MouseClicked

    private void btnreturn6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn6MouseClicked
        labelxoa6.setVisible(false);
        cbmsgvpbdt.setVisible(false);
        btndelete6.setVisible(false);
        btnreturn6.setVisible(false);
        btnrefresh6.setVisible(false);
    }//GEN-LAST:event_btnreturn6MouseClicked

    private void btnrefresh6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh6MouseClicked
        try {
            showGVcombobogvpbdt(cbmsgvpbdt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnrefresh6MouseClicked

    private void btnexportsv7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsv7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv7MouseClicked

    private void btnexportsv7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsv7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv7KeyPressed

    private void btndelete7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete7MouseClicked
        try {
            String msgv = cbmsgvuvdt.getSelectedItem().toString();
            System.out.print(msgv);
            Connection conn;
            conn = ConnectionUtil.getMyConnection();
            String sql = "{call xoagiaovien_uvdt(?,?)}";
            CallableStatement cstm = conn.prepareCall(sql);
            cstm.setString(1,msgv );
            cstm.registerOutParameter(2, java.sql.Types.INTEGER);
            cstm.executeUpdate();
            int kq= cstm.getInt(2);//gọi kết quả trả về
            if(kq==1){
                JOptionPane.showMessageDialog(null,"Delete Complete!!!!");
            }else{
                JOptionPane.showMessageDialog(null,"Fail! Please check and delete againt!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndelete7MouseClicked

    private void btnthemsv7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsv7MouseClicked
        try {
            GV_UVDT_GUI gv = new GV_UVDT_GUI();
            gv.setVisible(true);
            GV_UVDT_GUI.nhan2.setVisible(false);
            GV_UVDT_GUI.btneditgv.setVisible(false);
            GV_UVDT_GUI.txtmsgvcu.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnthemsv7MouseClicked

    private void btnrefreshsv7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsv7MouseClicked
        loadbanggv_uvdt(tablegvuvdt, listgiaovienuvdt);
    }//GEN-LAST:event_btnrefreshsv7MouseClicked

    private void btnsuasv8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv8MouseClicked
        try {
            GV_UVDT_GUI gv = new  GV_UVDT_GUI();
            gv.setVisible(true);
            GV_UVDT_GUI.nhan1.setVisible(false);
            GV_UVDT_GUI.btninsertgv.setVisible(false);
            GV_UVDT_GUI.labelmsgv .setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnsuasv8MouseClicked

    private void btnxoa8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa8MouseClicked
        labelxoa7.setVisible(true);
        cbmsgvuvdt.setVisible(true);
        btndelete7.setVisible(true);
        btnreturn7.setVisible(true);
        btnrefresh7.setVisible(true);
    }//GEN-LAST:event_btnxoa8MouseClicked

    private void btnreturn7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn7MouseClicked
        labelxoa7.setVisible(false);
        cbmsgvuvdt.setVisible(false);
        btndelete7.setVisible(false);
        btnreturn7.setVisible(false);
        btnrefresh7.setVisible(false);
    }//GEN-LAST:event_btnreturn7MouseClicked

    private void btnrefresh7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh7MouseClicked
        try {
            showGVcombobogvuvdt(cbmsgvuvdt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnrefresh7MouseClicked

    private void btndelete4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btndelete4MouseEntered

    private void btnsv_detaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsv_detaiMouseClicked
        cardLayout2.show(cardqldtsv,"cardqlsvdt");
    }//GEN-LAST:event_btnsv_detaiMouseClicked

    private void btnsv_detaiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsv_detaiMouseEntered
        btnsv_detai.setBackground(new Color(187,157,207));
    }//GEN-LAST:event_btnsv_detaiMouseEntered

    private void btnsv_detaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsv_detaiMouseExited
        btnsv_detai.setBackground(new Color(102,102,0));
    }//GEN-LAST:event_btnsv_detaiMouseExited

    private void btndetaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndetaiMouseClicked
        cardLayout2.show(cardqldtsv,"cardqldt1");
        loadbangdetai(tabledt, listdetai);
    }//GEN-LAST:event_btndetaiMouseClicked

    private void btndetaiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndetaiMouseEntered
         btndetai.setBackground(new Color(102,0,102));
    }//GEN-LAST:event_btndetaiMouseEntered

    private void btndetaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndetaiMouseExited
        btndetai.setBackground(new Color(255,255,204));
    }//GEN-LAST:event_btndetaiMouseExited

    private void btnexportdtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportdtMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportdtMouseClicked

    private void btnexportdtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportdtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportdtKeyPressed

    private void btndelete8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete8MouseClicked
        try {
            String msdt = cbmsdt.getSelectedItem().toString();
            System.out.print(msdt);
            Connection conn;
            conn = ConnectionUtil.getMyConnection();
            String sql = "{call xoagdt(?,?)}";
            CallableStatement cstm = conn.prepareCall(sql);
            cstm.setString(1,msdt );
            cstm.registerOutParameter(2, java.sql.Types.INTEGER);
            cstm.executeUpdate();
            int kq= cstm.getInt(2);//gọi kết quả trả về
            if(kq==1){
                JOptionPane.showMessageDialog(null,"Delete Complete!!!!");
            }else{
                JOptionPane.showMessageDialog(null,"Fail! Please check and delete againt!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insertsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndelete8MouseClicked

    private void btnthemdtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemdtMouseClicked
        try {
            DETAI_GUI gv = new DETAI_GUI();
            gv.setVisible(true);
            DETAI_GUI.nhan2.setVisible(false);
            DETAI_GUI.btneditgv.setVisible(false);
            DETAI_GUI.cbmsdtcu.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnthemdtMouseClicked

    private void btnrefreshdtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshdtMouseClicked
        loadbangdetai(tabledt, listdetai);
    }//GEN-LAST:event_btnrefreshdtMouseClicked

    private void btnsuadtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuadtMouseClicked
        try {
            DETAI_GUI gv = new DETAI_GUI();
            gv.setVisible(true);
            DETAI_GUI.nhan1.setVisible(false);
            DETAI_GUI.btninsertgv.setVisible(false);
            DETAI_GUI.labelmsgv.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnsuadtMouseClicked

    private void btnxoadtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoadtMouseClicked
        try {
            labelxoa8.setVisible(true);
            cbmsdt.setVisible(true);
            btndelete8.setVisible(true);
            btnreturn8.setVisible(true);
            btnrefresh8.setVisible(true);
            DETAI_GUI.showGVcombobotxtmsgvcu(cbmsdt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnxoadtMouseClicked

    private void btnreturn8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn8MouseClicked
        labelxoa8.setVisible(false);
        cbmsdt.setVisible(false);
        btndelete8.setVisible(false);
        btnreturn8.setVisible(false);
        btnrefresh8.setVisible(false);
    }//GEN-LAST:event_btnreturn8MouseClicked

    private void btnrefresh8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh8MouseClicked
        try {
            DETAI_GUI.showGVcombobotxtmsgvcu(cbmsdt);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnrefresh8MouseClicked

    private void btnexportsv9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexportsv9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv9MouseClicked

    private void btnexportsv9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnexportsv9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnexportsv9KeyPressed

    private void btndelete9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndelete9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btndelete9MouseClicked

    private void btnthemsv9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemsv9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemsv9MouseClicked

    private void btnrefreshsv9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshsv9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnrefreshsv9MouseClicked

    private void btnsuasv10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsuasv10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsuasv10MouseClicked

    private void btnxoa10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoa10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnxoa10MouseClicked

    private void btnreturn9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreturn9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnreturn9MouseClicked

    private void btnrefresh9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefresh9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnrefresh9MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void cbmsgvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmsgvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbmsgvActionPerformed

    private void cbmssvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmssvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbmssvActionPerformed

    private void cbmssv3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmssv3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbmssv3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new home().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JLabel btndelete;
    private javax.swing.JLabel btndelete1;
    private javax.swing.JLabel btndelete3;
    private javax.swing.JLabel btndelete4;
    private javax.swing.JLabel btndelete5;
    private javax.swing.JLabel btndelete6;
    private javax.swing.JLabel btndelete7;
    private javax.swing.JLabel btndelete8;
    private javax.swing.JLabel btndelete9;
    private javax.swing.JPanel btndetai;
    private javax.swing.JLabel btnexit;
    private javax.swing.JLabel btnexportdt;
    private javax.swing.JLabel btnexportsv;
    private javax.swing.JLabel btnexportsv1;
    private javax.swing.JLabel btnexportsv3;
    private javax.swing.JLabel btnexportsv4;
    private javax.swing.JLabel btnexportsv5;
    private javax.swing.JLabel btnexportsv6;
    private javax.swing.JLabel btnexportsv7;
    private javax.swing.JLabel btnexportsv9;
    private javax.swing.JPanel btngv;
    private javax.swing.JPanel btngv_hddt;
    private javax.swing.JPanel btngv_hv_cn;
    private javax.swing.JPanel btngv_pbdt;
    private javax.swing.JPanel btngv_uvdt;
    private javax.swing.JPanel btnhome;
    private javax.swing.JPanel btnqldt;
    private javax.swing.JPanel btnqlgv;
    private javax.swing.JPanel btnqlhd;
    private javax.swing.JPanel btnqlsv;
    private javax.swing.JLabel btnrefresh;
    private javax.swing.JLabel btnrefresh1;
    private javax.swing.JLabel btnrefresh3;
    private javax.swing.JLabel btnrefresh4;
    private javax.swing.JLabel btnrefresh5;
    private javax.swing.JLabel btnrefresh6;
    private javax.swing.JLabel btnrefresh7;
    private javax.swing.JLabel btnrefresh8;
    private javax.swing.JLabel btnrefresh9;
    private javax.swing.JLabel btnrefreshdt;
    private javax.swing.JLabel btnrefreshsv;
    private javax.swing.JLabel btnrefreshsv1;
    private javax.swing.JLabel btnrefreshsv3;
    private javax.swing.JLabel btnrefreshsv4;
    private javax.swing.JLabel btnrefreshsv5;
    private javax.swing.JLabel btnrefreshsv6;
    private javax.swing.JLabel btnrefreshsv7;
    private javax.swing.JLabel btnrefreshsv9;
    private javax.swing.JLabel btnreturn;
    private javax.swing.JLabel btnreturn1;
    private javax.swing.JLabel btnreturn3;
    private javax.swing.JLabel btnreturn4;
    private javax.swing.JLabel btnreturn5;
    private javax.swing.JLabel btnreturn6;
    private javax.swing.JLabel btnreturn7;
    private javax.swing.JLabel btnreturn8;
    private javax.swing.JLabel btnreturn9;
    private javax.swing.JLabel btnsuadt;
    private javax.swing.JLabel btnsuasv1;
    private javax.swing.JLabel btnsuasv10;
    private javax.swing.JLabel btnsuasv2;
    private javax.swing.JLabel btnsuasv4;
    private javax.swing.JLabel btnsuasv5;
    private javax.swing.JLabel btnsuasv6;
    private javax.swing.JLabel btnsuasv7;
    private javax.swing.JLabel btnsuasv8;
    private javax.swing.JPanel btnsv_detai;
    private javax.swing.JLabel btnthemdt;
    private javax.swing.JLabel btnthemsv;
    private javax.swing.JLabel btnthemsv1;
    private javax.swing.JLabel btnthemsv3;
    private javax.swing.JLabel btnthemsv4;
    private javax.swing.JLabel btnthemsv5;
    private javax.swing.JLabel btnthemsv6;
    private javax.swing.JLabel btnthemsv7;
    private javax.swing.JLabel btnthemsv9;
    private javax.swing.JPanel btntracuu;
    private javax.swing.JLabel btnxoa1;
    private javax.swing.JLabel btnxoa10;
    private javax.swing.JLabel btnxoa2;
    private javax.swing.JLabel btnxoa4;
    private javax.swing.JLabel btnxoa5;
    private javax.swing.JLabel btnxoa6;
    private javax.swing.JLabel btnxoa7;
    private javax.swing.JLabel btnxoa8;
    private javax.swing.JLabel btnxoadt;
    private javax.swing.JPanel card1;
    private javax.swing.JPanel card2;
    private javax.swing.JPanel cardhome;
    private javax.swing.JPanel cardlayout;
    private javax.swing.JPanel cardqldt;
    private javax.swing.JPanel cardqldtsv;
    private javax.swing.JPanel cardqlgv;
    private javax.swing.JPanel cardqlhd;
    private javax.swing.JPanel cardqlsv;
    private javax.swing.JPanel cardtracuu;
    private javax.swing.JComboBox cbmsdt;
    private javax.swing.JComboBox cbmsgv;
    private javax.swing.JComboBox cbmsgvhddt;
    private javax.swing.JComboBox cbmsgvpbdt;
    private javax.swing.JComboBox cbmsgvuvdt;
    private javax.swing.JComboBox cbmsgvuvdt2;
    private javax.swing.JComboBox cbmsgvxoa;
    private javax.swing.JComboBox cbmssv;
    private javax.swing.JComboBox cbmssv3;
    private javax.swing.JPanel gv_hhdt;
    private javax.swing.JPanel gv_hv_cn;
    private javax.swing.JPanel gv_pbdt;
    private javax.swing.JPanel gv_uvdt;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel labelxoa;
    private javax.swing.JLabel labelxoa1;
    private javax.swing.JLabel labelxoa3;
    private javax.swing.JLabel labelxoa4;
    private javax.swing.JLabel labelxoa5;
    private javax.swing.JLabel labelxoa6;
    private javax.swing.JLabel labelxoa7;
    private javax.swing.JLabel labelxoa8;
    private javax.swing.JLabel labelxoa9;
    private javax.swing.JPanel menutab;
    private javax.swing.JPanel navigator;
    private javax.swing.JPanel navigator1;
    private javax.swing.JPanel sidepane;
    private javax.swing.JTable tabledt;
    private javax.swing.JTable tablegv;
    private javax.swing.JTable tablegv_hv_cn;
    private javax.swing.JTable tablegv_pb_dt;
    private javax.swing.JTable tablegvhddt;
    private javax.swing.JTable tablegvuvdt;
    private javax.swing.JTable tablegvuvdt2;
    private javax.swing.JTable tablesinhvien;
    private javax.swing.JTable tablesinhvien3;
    private javax.swing.JPanel tabqlgv;
    public static javax.swing.JLabel txtaccount;
    public static javax.swing.JLabel txttenuser;
    // End of variables declaration//GEN-END:variables
}
