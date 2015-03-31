/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: RecAttTechSerLayer
 * Function: Technical Service Layer for Recording Attendance.
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/
package attendancetracker.recordattendance;

import attendancetracker.FileNames;
import attendancetracker.student.StudentTechSerLayer;
import attendancetracker.student.StudentStruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SnehalSutar
 */
public class RecAttTechSerLayer {
    
    public class Record {
        int date;
        int studPresent;
        public int totalStud;
    }
    
    /***************************************************************************
     * Save all the data in array into Local File.
     * @param studArray
     * @param numOfStudents
    **************************************************************************/
    public void saveCourseAtt(StudentStruct[] studArray, int numOfStudents){
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
    
    /***************************************************************************
     * 
     * @param studAttendance
     * @param recAtt
     * @param numOfStudents
     * @param numOfStudentsPresent
     * @return 
     **************************************************************************/
    public String saveAttendance(String[][] studAttendance,RecAttStruct recAtt,
            int numOfStudents, int numOfStudentsPresent){
       
        BufferedWriter writer = null, writerCourseTotal = null;
        BufferedReader readerCourseTotal = null;
        FileNames fileName = new FileNames();
        String filePath;
        //Create FILEPATH with Course ID and Date.
        filePath = recAtt.courseID + " - " +
                   recAtt.year + recAtt.month + recAtt.day
                   + ".txt";
        String courseTotalFilePath = recAtt.courseID + ".txt";
        
        
        String pathTemp = fileName.getTempFileName();
        File inputFile = new File(filePath);
        File courseTotalFile = new File(courseTotalFilePath);
        File tempFile = new File(pathTemp);
        
                
        try {
            //Get the File name where StudentStruct files are saved.
            
            if (!courseTotalFile.exists()) {
                courseTotalFile.createNewFile();
            }
            if (!inputFile.exists()) {
                inputFile.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(tempFile));
            String studRecord;
            for(int i=0; i<numOfStudents;i++){
                studRecord = studAttendance[i][0] + "\t" +studAttendance[i][1];
                writer.write(studRecord);
                writer.write("\r\n");
            }
            writer.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
            
            //------------------------------------------------------------------
            // Write into COURSE total file which contains date wise data of 
            // present and total number of students.
            //Check if record with current date alreeady exists.
            readerCourseTotal = new BufferedReader(new FileReader(courseTotalFile));
            String currLine;
            String splitData[];
            String currDate = recAtt.year + recAtt.month + recAtt.day;
            Record recArray[] = new Record[60];
            int count = 0;
            Boolean flagRecWithSameDateFound = false;
            while((currLine = readerCourseTotal.readLine()) != null){
                  Record rec = new Record();
                  splitData = currLine.split("\t");
                  rec.date = Integer.parseInt(splitData[0]);
                  rec.studPresent = Integer.parseInt(splitData[1]);
                  rec.totalStud =Integer.parseInt(splitData[2]);
                  if(rec.date == Integer.parseInt(currDate)){
                      rec.studPresent = numOfStudentsPresent;
                      rec.totalStud = numOfStudents;
                      flagRecWithSameDateFound = true;
                  }
                  recArray[count] = rec;
                  count++;
            }
            //If record with current date was not already present in the file, 
            // then add the record to the end of the Array.
            Record rec = new Record();
            if(flagRecWithSameDateFound ==false){
                rec.date = Integer.parseInt(currDate);
                rec.studPresent = numOfStudentsPresent;
                rec.totalStud = numOfStudents;
                recArray[count] = rec;
                count++;
            }
            readerCourseTotal.close();
            //Sort the file date wise.
            Record recTemp;
            for(int i =0; i<count;i++){
                for(int j=0;j<count;j++){
                    if(recArray[i].date > recArray[j].date){
                        //swap the records.
                        recTemp = recArray[i];
                        recArray[i] = recArray[j];
                        recArray[j] = recTemp;
                    }
                }
            }
            //------------------------------------------------------------------
            //Write the sorted file into database.
            writerCourseTotal = new BufferedWriter(new FileWriter(courseTotalFile));
            String total;
            for(int i=0; i<count;i++){
                recTemp = recArray[i];
                total = String.valueOf(recTemp.date) + "\t" +
                        String.valueOf(recTemp.studPresent) + "\t" +
                        String.valueOf(recTemp.totalStud);
                writerCourseTotal.write(total);
                writerCourseTotal.write("\r\n");
            }
//            total = recAtt.year + recAtt.month + recAtt.day + "\t" +
//                    String.valueOf(numOfStudentsPresent) +"\t" + String.valueOf(numOfStudents);
//            writerCourseTotal.write(total);
//            writerCourseTotal.write("\r\n");
            writerCourseTotal.close();
            //------------------------------------------------------------------
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        
        return "Attendance saved for course " + recAtt.courseID + "for date " +
                recAtt.year + " "+ recAtt.month +" " + recAtt.day;
    }
    /***************************************************************************
     * Check if the Attendance is already marked for the particular day.
     * @param courseDateFileName
     **************************************************************************/
    public int checkIfFileExists(String courseDateFileName){
        FileOutputStream oFile = null;
        File yourFile = new File(courseDateFileName);
        // Check if STUDENT file exists. If it does not exist, create a new file. 
        if (!yourFile.exists()) {
            return 0;
        }//if (!yourFile.exists())
        return 1;
    }
    /***************************************************************************
     * Return the Attendance data for the given date and course. i.e. per day 
     * Attendance record.
     * @param courseDateFileName
     * @return 
     **************************************************************************/
    public RecAttPerDayStruct[] getStudAttPerDay(String courseDateFileName){
        try {
            //Local objects declaration.
            RecAttPerDayStruct[] recAttPerDay = new RecAttPerDayStruct[60];
            
            //Open file and get the file contents locally.
            BufferedReader readerCourseTotal;
            readerCourseTotal = new BufferedReader(new FileReader(courseDateFileName));
            String currLine;
            String splitData[];
            Boolean flagRecWithSameDateFound = false;
            int count = 0;
            while((currLine = readerCourseTotal.readLine()) != null){
                RecAttPerDayStruct recAttPerDayRow = new RecAttPerDayStruct();
                splitData = currLine.split("\t");
                recAttPerDayRow.studentID = splitData[0];
                recAttPerDayRow.present   = Boolean.valueOf(splitData[1]);
                recAttPerDay[count] = recAttPerDayRow;
                count++;
            }
            readerCourseTotal.close();
            return recAttPerDay;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecAttTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecAttTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    /**************************************************************************/
}
