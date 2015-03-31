/*******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: FileNames
 * Function: Filenames which are stored in the database are mentioned in this 
 *           class explicitly and retrieved from here. 
 ******************************************************************************/

package attendancetracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SnehalSutar
 */
public class FileNames {
     public String filePathStudent   = "Student.txt";
     public String filePathCourse    = "Course.txt";
     public String filePathCourseReg = "CourseReg.txt";
     public String filePathTemp      = "temp.txt";
     
     public void FileNames(){
        FileOutputStream oFile = null;
        File yourFile = new File(filePathStudent);
        // Check if STUDENT file exists. If it does not exist, create a new file. 
        if (!yourFile.exists()) {
            try {
                yourFile.createNewFile();
                oFile = new FileOutputStream(yourFile, false);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//if (!yourFile.exists())
        
        
        
        yourFile = new File(filePathCourseReg);
        // Check if STUDENT file exists. If it does not exist, create a new file. 
        if (!yourFile.exists()) {
            try {
                yourFile.createNewFile();
                oFile = new FileOutputStream(yourFile, false);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//if (!yourFile.exists())
        
     }
     
     /**************************************************************************
      * Return the STUDENT File name
      * @return 
      *************************************************************************/
     public String getStudFileName(){
         
        FileOutputStream oFile = null;
        File yourFile = new File(filePathStudent);
        // Check if STUDENT file exists. If it does not exist, create a new file. 
        if (!yourFile.exists()) {
            try {
                yourFile.createNewFile();
                oFile = new FileOutputStream(yourFile, false);
                oFile.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//if (!yourFile.exists())
        return this.filePathStudent;        
    }
     
     
     /**************************************************************************
      * Return the Temporary File name used. 
      * @return 
      *************************************************************************/
      public String getTempFileName(){
        return this.filePathTemp;        
    }
      
     /**************************************************************************
      * Return COURSE file name. 
      * @return 
      *************************************************************************/
     public String getCourseFileName(){
        FileOutputStream oFile = null;
        File yourFile ;
        yourFile = new File(filePathCourse);
        // Check if COURSE file exists. If it does not exist, create a new file. 
        if (!yourFile.exists()) {
            try {
                yourFile.createNewFile();
                oFile = new FileOutputStream(yourFile, false);
                oFile.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//if (!yourFile.exists())
         return this.filePathCourse;        
    }
     
     
      /**************************************************************************
      * Return COURSE REG file name. 
      * @return 
      *************************************************************************/
     public String getCourseRegFileName(){
        FileOutputStream oFile = null;
        File yourFile;
        yourFile = new File(filePathCourseReg);
        // Check if COURSE file exists. If it does not exist, create a new file. 
        if (!yourFile.exists()) {
            try {
                yourFile.createNewFile();
                oFile = new FileOutputStream(yourFile, false);
                oFile.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileNames.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//if (!yourFile.exists())
        
         
         return this.filePathCourseReg;        
    }
}
