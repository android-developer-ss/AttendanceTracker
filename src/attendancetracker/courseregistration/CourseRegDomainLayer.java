/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: CourseRegDomainLayer
 * Function: Domain Layer for ADD/DELETE/MODIFY course Registration. 
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/
package attendancetracker.courseregistration;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SnehalSutar
 */
public class CourseRegDomainLayer {
    
    CourseRegStruct[] courseRegArray;
    public static int numOfCoursesReg;
    CourseRegTechSerLayer courseRegDb;
    final static String noCourse = "No Course";
    final static String coursesOK = "courses Ok";
    
      /***************************************************************************
     * Add a Course by getting values entered in the GUI Layer of the course
     * named CourseGUI.For this first we will check if any student exists with
     * the same Course ID.
     * @param courseReg
     * @return  string: success or error message.
     **************************************************************************/
    public String addCourseReg(CourseRegStruct courseReg){
        //Local object declaration.
        courseRegArray = new CourseRegStruct[100];    
        //Get the list of existing students from the database.  
        courseRegDb = new CourseRegTechSerLayer();
        courseRegArray = courseRegDb.getCourseRegList();
        numOfCoursesReg = courseRegDb.getNumOfCourse();
        
        // Check if the data already exits for the current student name.
        // If the Course ID already exists then return an error msg. 
        int i;
        for (i=0; i<numOfCoursesReg; i++){
            //Check if the Course ID already exists in the database. 
            if(courseRegArray[i].studentID.equals(courseReg.studentID)){
                String errorMsg;
                errorMsg = "Registration for Student ID: '" +courseReg.studentID+ "' already done.";
                return errorMsg;
            } //if(courseArray[i].courseID.equals(course.courseID))
        }//for (i=0; i<=numOfCoursesReg; i++){
        
        
        //Check if the student has registeres for any similar courses. 
        String errorMsg;
        errorMsg = checkIfSameCoursesRegistered(courseReg);
        if(!errorMsg.equals(coursesOK))
            return errorMsg;

        //If no Course exists with the same Student ID then add a new student. 
        courseRegArray[numOfCoursesReg] = new CourseRegStruct();
        courseRegArray[numOfCoursesReg].studentID  = courseReg.studentID;
        courseRegArray[numOfCoursesReg].courseID1  = courseReg.courseID1;
        courseRegArray[numOfCoursesReg].courseID2  = courseReg.courseID2;
        courseRegArray[numOfCoursesReg].courseID3  = courseReg.courseID3;
        numOfCoursesReg++;
        
        saveCourseData(courseRegArray);
        
        String successMsg;
        successMsg = "Registration done.";
        return successMsg;        
    }
    
    public void saveCourseData(CourseRegStruct[] courseRegArray){
        courseRegDb.saveCourseRegArray(courseRegArray, numOfCoursesReg);
    }
    
    
    /***************************************************************************
     * Update the Course Display table. Get all records from the Database, save
     * it in the database and return it in the type of DefaultModel.
     * @param studentDetailsTable
     * @return 
     **************************************************************************/
    public DefaultTableModel updateTable(JTable studentDetailsTable){
        
        DefaultTableModel model = new DefaultTableModel();
        
        courseRegArray = new CourseRegStruct[100];    
        //Get the list of existing students from the database.  
        courseRegDb = new CourseRegTechSerLayer();
        courseRegArray = courseRegDb.getCourseRegList();
        numOfCoursesReg = courseRegDb.getNumOfCourse();

        model.setColumnIdentifiers(new String[]{"Student ID", "Course 1","Course 2", "Course 3"});
        int i;
        String rowStr[];
        rowStr = new String[4];
        for (i=0; i<numOfCoursesReg; i++) {
            rowStr[0]= courseRegArray[i].studentID;
            rowStr[1]= courseRegArray[i].courseID1;
            rowStr[2]= courseRegArray[i].courseID2;
            rowStr[3]= courseRegArray[i].courseID3;
            model.addRow(rowStr);
        }
          
        return model;
    }
    
    /***************************************************************************
     * Get the record at particular ROW. Save it in a type of Course object.
     * @param row
     * @return 
     **************************************************************************/
    public CourseRegStruct getValuesAtRow(int row){
        CourseRegStruct courseReg;
        courseRegArray = new CourseRegStruct[100];    
        //Get the list of existing students from the database.  
        courseRegDb = new CourseRegTechSerLayer();
        courseRegArray = courseRegDb.getCourseRegList();
        numOfCoursesReg = courseRegDb.getNumOfCourse();
        
        courseReg = new CourseRegStruct();
        if (row<=numOfCoursesReg){
        courseReg = courseRegArray[row];
        }
        return courseReg;
    }
    
    /***************************************************************************
     * Modify Record with Row number which is clicked. 
     * @param clickedRowNumber
     * @param courseClicked
     * @return 
     **************************************************************************/
    public String modifyRecordWithRow(int clickedRowNumber,CourseRegStruct courseClicked){
        courseRegArray = new CourseRegStruct[100]; 
        
        //Get the list of existing students from the database.  
        courseRegDb = new CourseRegTechSerLayer();
        courseRegArray = courseRegDb.getCourseRegList();
        numOfCoursesReg = courseRegDb.getNumOfCourse();
        String str;
        //str = "Record with student ID " + courseArray[clickedRowNumber].studentID+ " deleted.";
        
        //Check if the student has registeres for any similar courses. 
        String errorMsg;
        errorMsg = checkIfSameCoursesRegistered(courseClicked);
        if(!errorMsg.equals(coursesOK))
            return errorMsg;
        
        //If all courses for which the student was registeredd is dropped, then
        // delete that student from registration. 
        if(allCoursesDeleted(courseClicked)){
          str = deleteStudentFromReg(clickedRowNumber,courseRegArray);
          numOfCoursesReg--;
          //saveCourseData(courseRegArray);
          return str;
        }
        
        //Delete the record Number which is clicked.
        for(int i = clickedRowNumber; i<numOfCoursesReg; i++){
            courseRegArray[i] = courseRegArray[i+1];
        }
        numOfCoursesReg--;
        
        //Add the modified data at the end of the array list. 
        courseRegArray[numOfCoursesReg] = courseClicked;
        numOfCoursesReg++;
        saveCourseData(courseRegArray); 
        
//        str = "Course Registration modified for Student ID: " + courseClicked.studentID;
        return "OK";
    }
    
    /***************************************************************************
     * Delete Record with Row number which is clicked. 
     * @param clickedRowNumber
     * @param courseRegArray
     * @return 
     **************************************************************************/
    public String deleteStudentFromReg(int clickedRowNumber,
                                       CourseRegStruct[] courseRegArray){
        
        String str;
        str = "Record with student ID " + courseRegArray[clickedRowNumber].studentID+ " deleted.";
        
        //Delete the record Number which is clicked.
        for(int i = clickedRowNumber; i<numOfCoursesReg; i++){
            courseRegArray[i] = courseRegArray[i+1];
        }
        numOfCoursesReg--;
        saveCourseData(courseRegArray); 
        
        return str;
    }
    /***************************************************************************
     * Check if all the courses registered for a student are dropped.
     * @param courseReg
     * @return 
     **************************************************************************/
    public Boolean allCoursesDeleted(CourseRegStruct courseReg){
        if((courseReg.courseID1.equals(noCourse)) &&
           (courseReg.courseID2.equals(noCourse)) &&
           (courseReg.courseID3.equals(noCourse)) ){
        return true;
        }
        
        return false;
    }
    /***************************************************************************
     * Check if student is registering for same courses. IF yes the return an 
     * error message else return success msg. 
     * @param courseReg
     * @return 
     **************************************************************************/
    public String checkIfSameCoursesRegistered(CourseRegStruct courseReg){
        
        if(courseReg.courseID1.equals(courseReg.courseID2) && 
                !courseReg.courseID1.equals(noCourse))
            return "Course 1 and Course 2 are same.";
        
        if(courseReg.courseID2.equals(courseReg.courseID3) &&
                !courseReg.courseID2.equals(noCourse))
            return "Course 2 and Course 3 are same.";
        
        if(courseReg.courseID1.equals(courseReg.courseID3) &&
                !courseReg.courseID1.equals(noCourse))
            return "Course 1 and Course 3 are same.";
        
        return coursesOK;
    }
    /***************************************************************************
     * If the Course is deleted from Course Database, then the course should be
     * dropped for all the Students who have registered for that course. 
     **************************************************************************/
    public void dropCoursesWhenCourseDeleted(String courseID){
        //Get the list of existing students from the database.  
        courseRegDb = new CourseRegTechSerLayer();
        courseRegArray = courseRegDb.getCourseRegList();
        numOfCoursesReg = courseRegDb.getNumOfCourse();
        CourseRegStruct courseReg;
        for(int i=0;i<numOfCoursesReg;i++){
            courseReg = new CourseRegStruct();
            courseReg = courseRegArray[i];
            if(courseID.equals(courseRegArray[i].courseID1)){
                courseRegArray[i].courseID1 = noCourse;
            }
            if(courseID.equals(courseRegArray[i].courseID2)){
                courseRegArray[i].courseID2 = noCourse;
            }
            if(courseID.equals(courseRegArray[i].courseID3)){
                courseRegArray[i].courseID3 = noCourse;
            }
            
        //If all courses for which the student was registeredd is dropped, then
        // delete that student from registration. 
            if((courseReg.courseID1.equals(noCourse)) &&
           (courseReg.courseID2.equals(noCourse)) &&
           (courseReg.courseID3.equals(noCourse)) ){ 
            courseRegArray[i].studentID = "";
            }
        }//for(int i=0;i<numOfCoursesReg;i++)
        
        
        //Save the modified array to Database.
         saveCourseData(courseRegArray);
        
    }
    /***************************************************************************
     * Delete Student from registration table when student is deleted from the 
     * STUDENT database. 
     * @param studentID
     **************************************************************************/
    public void deleteStudentWhenStudDeleted(String studentID){
         //Get the list of existing students from the database.  
        courseRegDb = new CourseRegTechSerLayer();
        courseRegArray = courseRegDb.getCourseRegList();
        numOfCoursesReg = courseRegDb.getNumOfCourse();
        CourseRegStruct courseReg;
        for(int i=0;i<numOfCoursesReg;i++){
            courseReg = new CourseRegStruct();
            courseReg = courseRegArray[i];
            if(studentID.equals(courseRegArray[i].studentID)){
                courseRegArray[i].studentID = "";
            }
        }
        
        //Save the modified array to Database.
         saveCourseData(courseRegArray);
    }
    /**************************************************************************/
    
}
