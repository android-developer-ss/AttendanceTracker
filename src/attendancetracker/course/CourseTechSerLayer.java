/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: CourseTechSerLayer
 * Function: Technical Service Layer for ADD/DELETE/MODIFY course. 
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/

package attendancetracker.course;

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
public class CourseTechSerLayer {
    
    //Counter for the count of student.
    int courseNum;
            
    /***************************************************************************
     * Get all the course records in the DATABASE and return it to the 
     * Application Layer in to process the data. 
     * @return 
     */
    public CourseStruct[] getCourseList(){
        //Declare local array of Courses.
        CourseStruct[] courseArray;
        courseArray = new CourseStruct[100];
        
        
        try {
            
            //Get the File name where CourseStruct files are saved.
            FileNames fileName = new FileNames();
            String filePath = fileName.getCourseFileName();
            
            //To open FILE for Reading Student file.
            BufferedReader bReader;
            bReader = new BufferedReader(new FileReader(filePath));
            String line;
            String courseRecLine[];
            courseRecLine = new String[7];
            //Looping the read block until all lines in the file are read.
            courseNum = 0; //initialize the student counter. 
            while ((line = bReader.readLine()) != null) {
              courseRecLine = line.split("\t");
              CourseStruct tempCourse = new CourseStruct();
              tempCourse.courseID      = courseRecLine[0];
              tempCourse.courseName    = courseRecLine[1];
              tempCourse.courseDesc    = courseRecLine[2];
              tempCourse.creditHours   = Integer.parseInt(courseRecLine[3]);   
              tempCourse.courseLevel   = courseRecLine[4];   
              tempCourse.professorName = courseRecLine[5];   
              tempCourse.courseGraded  = Boolean.parseBoolean(courseRecLine[6]);   
              
              courseArray[courseNum]  = tempCourse;
              courseNum++;
              tempCourse = null;
            }
            bReader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentTechSerLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseArray;
    }
    
    /***************************************************************************
     * Return the total Number of CourseStructs maintained in the Database.
     * @return 
     **************************************************************************/
    public int getNumOfCourse(){
        return courseNum;
    }
    
     /***************************************************************************
     * Save all the data in array into Local File.
     * @param courseArray
     * @param numOfCourses
    **************************************************************************/
    public void saveCourseArray(CourseStruct[] courseArray, int numOfCourses){
        BufferedWriter writer;
        FileNames fileName = new FileNames();
        String filePath = fileName.getCourseFileName();
        String pathTemp = fileName.getTempFileName();
        File inputFile = new File(filePath);
        File tempFile = new File(pathTemp);
        
        try {
            //Get the File name where Student files are saved.
            
            writer = new BufferedWriter(new FileWriter(tempFile));
            String courseRecord;
            for(int i=0; i<numOfCourses;i++){
                CourseStruct tempCourse;
                tempCourse = new CourseStruct();
                courseRecord = null;
                tempCourse = courseArray[i];
                courseRecord = tempCourse.courseID + "\t" +tempCourse.courseName + "\t" +
                             tempCourse.courseDesc +"\t" + tempCourse.creditHours + "\t" +
                             tempCourse.courseLevel +"\t" + tempCourse.professorName + "\t" +
                             tempCourse.courseGraded;
                writer.write(courseRecord);
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
