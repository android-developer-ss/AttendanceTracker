/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: CourseRegTechSerLayer
 * Function: Technical Service Layer for ADD/DELETE/MODIFY course Registration. 
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/

package attendancetracker.courseregistration;

import attendancetracker.FileNames;
import attendancetracker.student.StudentTechSerLayer;
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
public class CourseRegTechSerLayer {
    //Counter for the count of student.
    int courseNum;
    public static int courseNumForCourse;
            
    /***************************************************************************
     * Get all the course records in the DATABASE and return it to the 
     * Application Layer in to process the data. 
     * @return 
     */
    public CourseRegStruct[] getCourseRegList(){
        //Declare local array of Courses.
        CourseRegStruct[] courseRegArray;
        courseRegArray = new CourseRegStruct[100];
        
        
        try {
            
            //Get the File name where Course files are saved.
            FileNames fileName = new FileNames();
            String filePath = fileName.getCourseRegFileName();
            
            //To open FILE for Reading Student file.
            BufferedReader bReader;
            bReader = new BufferedReader(new FileReader(filePath));
            String line;
            String courseRegLine[];
            courseRegLine = new String[4];
            //Looping the read block until all lines in the file are read.
            courseNum = 0; //initialize the student counter. 
            while ((line = bReader.readLine()) != null) {
              courseRegLine = line.split("\t");
              CourseRegStruct tempCourseReg = new CourseRegStruct();
              tempCourseReg.studentID    = courseRegLine[0];
              tempCourseReg.courseID1    = courseRegLine[1];
              tempCourseReg.courseID2    = courseRegLine[2];
              tempCourseReg.courseID3    = courseRegLine[3];   
              
              courseRegArray[courseNum]  = tempCourseReg;
              courseNum++;
              tempCourseReg = null;
            }
            bReader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseRegArray;
    }
    
     /**************************************************************************
     * Get all the course records for the specific COURSEID mentioned from the 
     * DATABASE and return it to the  Application Layer in to process the data. 
     * @param courseID
     * @return 
     **************************************************************************/
    public CourseRegStruct[] getCourseRegListForCourse(String courseID){
        //Declare local array of Courses.
        CourseRegStruct[] courseRegArray;
        courseRegArray = new CourseRegStruct[100];
        courseNumForCourse = 0;
        
        try {
            
            //Get the File name where Course files are saved.
            FileNames fileName = new FileNames();
            String filePath = fileName.getCourseRegFileName();
            
            //To open FILE for Reading Student file.
            BufferedReader bReader;
            bReader = new BufferedReader(new FileReader(filePath));
            String line;
            String courseRegLine[];
            courseRegLine = new String[4];
            //Looping the read block until all lines in the file are read.
            courseNum = 0; //initialize the student counter. 
            while ((line = bReader.readLine()) != null) {
              courseRegLine = line.split("\t");
              //Check of student is registered for course sent as parameter to 
              //this method. 
              if(courseID.equals(courseRegLine[1]) ||
                 courseID.equals(courseRegLine[2]) ||
                 courseID.equals(courseRegLine[3])){
              CourseRegStruct tempCourseReg = new CourseRegStruct();
              tempCourseReg.studentID    = courseRegLine[0];
              tempCourseReg.courseID1    = courseRegLine[1];
              tempCourseReg.courseID2    = courseRegLine[2];
              tempCourseReg.courseID3    = courseRegLine[3];   
                            
              courseRegArray[courseNumForCourse]  = tempCourseReg;
              courseNumForCourse++;
              tempCourseReg = null;
              }
            }
            bReader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseRegArray;
    }
    
    /***************************************************************************
     * Return the total Number of Courses maintained in the Database.
     * @return 
     **************************************************************************/
    public int getNumOfCourse(){
        return courseNum;
    }
     /***************************************************************************
     * Return the total Number of students for Course maintained in the Database
     * @return 
     **************************************************************************/
    public int getStudNumForCourse(){
        return courseNumForCourse;
    }
    
     /***************************************************************************
     * Save all the data in array into Local File.
     * @param courseRegArray
     * @param numOfCourses
    **************************************************************************/
    public void saveCourseRegArray(CourseRegStruct[] courseRegArray, int numOfCourses){
        BufferedWriter writer;
        FileNames fileName = new FileNames();
        String filePath = fileName.getCourseRegFileName();
        String pathTemp = fileName.getTempFileName();
        File inputFile = new File(filePath);
        File tempFile = new File(pathTemp);
        
        try {
            //Get the File name where Student files are saved.
            
            writer = new BufferedWriter(new FileWriter(tempFile));
            String courseRegRecord;
            for(int i=0; i<numOfCourses;i++){
                CourseRegStruct tempCourseReg;
                tempCourseReg = new CourseRegStruct();
                tempCourseReg = courseRegArray[i];
                if(tempCourseReg.studentID.equals("")) continue;
                courseRegRecord = tempCourseReg.studentID + "\t" +
                                  tempCourseReg.courseID1 + "\t" +
                                  tempCourseReg.courseID2 + "\t" +
                                  tempCourseReg.courseID3 + "\t" ;
                writer.write(courseRegRecord);
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
