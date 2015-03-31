/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: GraphTechSerLayer
 * Function: Technical Service Layer for displaying Graph.
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/

package attendancetracker.graphforselectedcourse;

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
public class GraphTechSerLayer {
    
    public static int numOfDays = 0;
    
    
    /***************************************************************************
     * Return the Attendance marked for Course courseID by reading the file 
     * named "courseID" from the Database.
     * @param courseID
     * @return 
     **************************************************************************/
    public CourseAbsentPresentStruct[] getAttendanceForCourse(String courseID){
        CourseAbsentPresentStruct[] courseAttArray;
        courseAttArray = new CourseAbsentPresentStruct[60];
        
        
        try {
           //Open file and get the file contents locally.
            BufferedReader readerCourseTotal;
            String courseIDPath = courseID + ".txt";
            File courseIDFile = new File(courseIDPath);
            readerCourseTotal = new BufferedReader(new FileReader(courseIDFile));
            String currLine;
            String splitData[];
            //Boolean flagRecWithSameDateFound = false;
            numOfDays=0;
            while((currLine = readerCourseTotal.readLine()) != null){
                CourseAbsentPresentStruct courseAtt = new CourseAbsentPresentStruct();
                splitData = currLine.split("\t");
                courseAtt.date = Integer.parseInt(splitData[0]);
                courseAtt.presentStud   = Integer.parseInt(splitData[1]);
                courseAtt.totalStud   = Integer.parseInt(splitData[2]);
                courseAttArray[numOfDays] = courseAtt;
                numOfDays++;
            }
            readerCourseTotal.close();
            return courseAttArray;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecAttTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecAttTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return courseAttArray;
    }
    
    /***************************************************************************
     * Return the total number of records saved in the database for the 
     * selected course ID. 
     * 
     **************************************************************************/
     public int getNumOfDays(){
         return numOfDays;
     }
     /*************************************************************************/
}
