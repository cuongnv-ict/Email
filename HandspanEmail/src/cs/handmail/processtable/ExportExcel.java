/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.handmail.processtable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Venus-NS
 */
public class ExportExcel {
    private String _pathFolder=null;
    private JTable _tableExport;
    private String _nameFile="Table.xls";
    
    public void setFolder()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("Save");
        fileChooser.setDialogTitle("Select Folder Save");
        int idReturn = fileChooser.showOpenDialog(null);
        if(idReturn == JFileChooser.APPROVE_OPTION){
            _pathFolder = fileChooser.getSelectedFile().getAbsolutePath();
        }
        
    }
    
    public String getPathFolder()
    {
        return _pathFolder;
    }
    
    public void setNameFile(String nameFile)
    {
        this._nameFile = nameFile;
    }
    
    public void setJtableData(JTable tableExport){
        this._tableExport = tableExport;
    }
    
    public void export(){
        try {
            new WorkbookFactory();
            Workbook wb = new XSSFWorkbook(); //Excell workbook
            Sheet sheet = wb.createSheet(); //WorkSheet
            Row row = sheet.createRow(2); //Row created at line 3
            TableModel model = _tableExport.getModel();
            String temp;
            
            Row headerRow = sheet.createRow(0); //Create row at line 0
            for(int headings = 0; headings < model.getColumnCount(); headings++){ 
                headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
            }
            
            for(int rows = 0; rows < model.getRowCount(); rows++){ //For each table row
                for(int cols = 0; cols < _tableExport.getColumnCount(); cols++){ //For each table column
                    if(model.getValueAt(rows, cols)!= null)
                        temp = model.getValueAt(rows, cols).toString();
                    else
                        temp = "";
                    row.createCell(cols).setCellValue(temp); 
                }
                
                //Set the row to the next one in the sequence
                row = sheet.createRow((rows + 3));
            }
            wb.write(new FileOutputStream(_pathFolder+"/" +_nameFile));//Save the file    
            JOptionPane.showMessageDialog(null, "Save file Success");
        } catch (IOException ex) {
            Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void main(String args[]){
//        ExportExcel ex = new ExportExcel();
//        ex.setFolder();
//        ex.setJtableData(jTable1);
//        if(ex.getPathFolder()!=null)
//        ex.export();
//    }
   
}
