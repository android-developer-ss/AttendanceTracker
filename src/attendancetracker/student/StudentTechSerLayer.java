/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: StudentGUI
 * Function: Technical Service Layer for ADD/DELETE/MODIFY student records.
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/
package attendancetracker.student;

import attendancetracker.FileNames;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SnehalSutar
 */
public class StudentTechSerLayer {
    
    //Counter for the count of student.
    int studNum;
            
    /***************************************************************************
     * Get all the student records in the DATABASE and return it to the 
     * Application Layer in to process the data. 
     * @return 
     */
    public StudentStruct[] getStudentList(){
        //Declare local array of Students.
        StudentStruct[] studArray;
        studArray = new StudentStruct[100];        
        
        try {            
            //Get the File name where StudentStruct files are saved.
            FileNames fileName = new FileNames();
            String filePath = fileName.getStudFileName();
            
            //To open FILE for Reading StudentStruct file.
            BufferedReader bReader;
            bReader = new BufferedReader(new FileReader(filePath));
            String line;
            String studRecLine[];
            studRecLine = new String[9];
            //Looping the read block until all lines in the file are read.
            studNum = 0; //initialize the student counter. 
            while ((line = bReader.readLine()) != null) {
              studRecLine = line.split("\t");
              StudentStruct tempStud = new StudentStruct();
              tempStud.studentID  = studRecLine[0];
              tempStud.firstName  = studRecLine[1];
              tempStud.middleName = studRecLine[2];
              tempStud.lastName   = studRecLine[3];   
              tempStud.addrLine1  = studRecLine[4];   
              tempStud.addrLine2  = studRecLine[5];
              tempStud.phoneNum   = Integer.parseInt(studRecLine[6]);
              tempStud.ssnNum     = Integer.parseInt(studRecLine[7]);
              tempStud.emailID    = studRecLine[8];
              studArray[studNum]  = tempStud;
              studNum++;
              tempStud = null;
            }
            bReader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studArray;
    }
    
    /***************************************************************************
     * Return the total Number of students maintained in the Database.
     * @return 
     **************************************************************************/
    public int getNumOfStud(){
        return studNum;
    }
    
    /***************************************************************************
     * Save all the data in array into Local File.
     * @param studArray
     * @param numOfStudents
    **************************************************************************/
    public void saveStudArray(StudentStruct[] studArray, int numOfStudents){
        BufferedWriter writer = null;
        FileNames fileName = new FileNames();
        String filePath = fileName.getStudFileName();
        String pathTemp = fileName.getTempFileName();
        File inputFile = new File(filePath);
        File tempFile = new File(pathTemp);
        
        try {
            //Get the File name where StudentStruct files are saved.
            
            writer = new BufferedWriter(new FileWriter(tempFile));
            String studRecord;
            for(int i=0; i<numOfStudents;i++){
                StudentStruct tempStud = new StudentStruct();
                studRecord = null;
                tempStud = studArray[i];
                studRecord = tempStud.studentID + "\t" + tempStud.firstName +"\t" +
                             tempStud.middleName +"\t" + tempStud.lastName +"\t"+
                             tempStud.addrLine1 +"\t" + tempStud.addrLine2 +"\t"+
                             tempStud.phoneNum +"\t" + tempStud.ssnNum +"\t"+
                             tempStud.emailID;
                writer.write(studRecord);
                writer.write("\r\n");
            }
            writer.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
    /**************************************************************************/
    
    
    
    
}
