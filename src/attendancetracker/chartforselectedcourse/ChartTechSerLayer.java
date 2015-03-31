/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: ChartTechSerLayer
 * Function: Technical Service Layer for preparing chart.
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/
package attendancetracker.chartforselectedcourse;

import attendancetracker.recordattendance.RecAttPerDayStruct;
import attendancetracker.recordattendance.RecAttTechSerLayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SnehalSutar
 */
public class ChartTechSerLayer {
    
    public int numOfStud=0;
//    public CourseAbsentPresentStruct[] getCourseFile(String courseID){
////        String fileName =
//        
//        return null;
//    }
    
    /***************************************************************************
     * Return individual date attendance files. 
     * @param courseID
     * @param date
     * @return 
     **************************************************************************/
     public RecAttPerDayStruct[] getAttForDate(String courseID,int date){
         
         RecAttPerDayStruct recAttPerDayArray[] = new RecAttPerDayStruct[60];
         
         try {
           //Open file and get the file contents locally.
            BufferedReader readerCourseTotal;
            String courseIDPath = courseID + " - " + String.valueOf(date) +".txt";
            File courseIDFile = new File(courseIDPath);
            readerCourseTotal = new BufferedReader(new FileReader(courseIDFile));
            String currLine;
            String splitData[];
            //Boolean flagRecWithSameDateFound = false;
            numOfStud=0;
            while((currLine = readerCourseTotal.readLine()) != null){
                RecAttPerDayStruct recAttPerDay = new RecAttPerDayStruct();
                splitData = currLine.split("\t");
                recAttPerDay.studentID = splitData[0];
                recAttPerDay.present   = Boolean.parseBoolean(splitData[1]);
                recAttPerDayArray[numOfStud] = recAttPerDay;
                numOfStud++;
            }
            readerCourseTotal.close();
            return recAttPerDayArray;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecAttTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecAttTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
    /**************************************************************************/
     
     
      /***************************************************************************
     * Return the total number of records saved in the database for the 
     * selected course ID. 
     * 
     **************************************************************************/
     public int getNumOfStud(){
         return numOfStud;
     }
     /*************************************************************************/
}
