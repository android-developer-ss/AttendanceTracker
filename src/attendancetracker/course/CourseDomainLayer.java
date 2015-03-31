/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: CourseDomainLayer
 * Function: Domain Layer for ADD/DELETE/MODIFY course. 
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/
package attendancetracker.course;

import attendancetracker.courseregistration.CourseRegDomainLayer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SnehalSutar
 */
public class CourseDomainLayer {
    
    CourseStruct[] courseArray;
    public static int numOfCourses;
    CourseTechSerLayer courseDb;
         
    /***************************************************************************
     * Constructor for CourseStructDomainLayer. 
     * This constructor will create the CourseStruct ArrayList once. Get all the 
 course records in it for processing for all functions add/modify/delete.
     **************************************************************************/
    public void CourseProcessing(){}    
    
    /***************************************************************************
     * Add a CourseStruct by getting values entered in the GUI Layer of the course
 named CourseStructGUI.For this first we will check if any student exists with
 the same CourseStruct ID.
     * @param course
     * @return  string: success or error message.
     **************************************************************************/
    public String addCourse(CourseStruct course){
        //Local object declaration.
        courseArray = new CourseStruct[100];    
        //Get the list of existing students from the database.  
        courseDb = new CourseTechSerLayer();
        courseArray = courseDb.getCourseList();
        numOfCourses = courseDb.getNumOfCourse();
        
        // Check if the data already exits for the current student name.
        // If the CourseStruct ID already exists then return an error msg. 
        int i;
        for (i=0; i<numOfCourses; i++){
            //Check if the CourseStruct ID already exists in the database. 
            if(courseArray[i].courseID.equals(course.courseID)){
                String errorMsg;
                errorMsg = "Course with ID '" +course.courseID+ "' already exists.";
                return errorMsg;
            } //if(courseArray[i].courseID.equals(course.courseID))
        }//for (i=0; i<=numOfCourses; i++){
        
        //If no CourseStruct exists with the same CourseStruct ID then add a new student. 
        courseArray[numOfCourses] = new CourseStruct();
        courseArray[numOfCourses].courseID      = course.courseID;
        courseArray[numOfCourses].courseName    = course.courseName;
        courseArray[numOfCourses].courseDesc    = course.courseDesc;
        courseArray[numOfCourses].creditHours   = course.creditHours;   
        courseArray[numOfCourses].courseLevel   = course.courseLevel;
        courseArray[numOfCourses].professorName = course.professorName;   
        courseArray[numOfCourses].courseGraded  = course.courseGraded;
        numOfCourses++;
        
        saveCourseData(courseArray);
        
        String successMsg;
        successMsg = "Course added.";
        return successMsg;        
    }
    
    public void saveCourseData(CourseStruct[] courseArray){
        courseDb.saveCourseArray(courseArray, numOfCourses);
    }
    
    
    
    /***************************************************************************
     * Update the CourseStruct Display table. Get all records from the Database, save
     * it in the database and return it in the type of DefaultModel.
     * @param studentDetailsTable
     * @return 
     **************************************************************************/
    public DefaultTableModel updateTable(JTable studentDetailsTable){
        
        DefaultTableModel model = new DefaultTableModel();
        
        courseArray = new CourseStruct[100];    
        //Get the list of existing students from the database.  
        courseDb = new CourseTechSerLayer();
        courseArray = courseDb.getCourseList();
        numOfCourses = courseDb.getNumOfCourse();

        model.setColumnIdentifiers(new String[]{"Course ID", "Course Name"});
        int i;
        String rowStr[];
        rowStr = new String[4];
        for (i=0; i<numOfCourses; i++) {
            rowStr[0]= courseArray[i].courseID;
            rowStr[1]= courseArray[i].courseName;
//            rowStr[2]= courseArray[i].courseDesc;
//            rowStr[3]= String.valueOf(courseArray[i].creditHours);
//            rowStr[4]= courseArray[i].courseLevel;
//            rowStr[5]= courseArray[i].professorName;
//            rowStr[6]= courseArray[i].courseGraded.toString();
            model.addRow(rowStr);
        }
          
        return model;
    }
    
    
    
    /***************************************************************************
     * Get the record at particular ROW. Save it in a type of CourseStruct object.
     * @param row
     * @return 
     **************************************************************************/
    public CourseStruct getValuesAtRow(int row){
        CourseStruct course;
        courseArray = new CourseStruct[100];    
        //Get the list of existing students from the database.  
        courseDb = new CourseTechSerLayer();
        courseArray = courseDb.getCourseList();
        numOfCourses = courseDb.getNumOfCourse();
        
        course = new CourseStruct();
        if (row<=numOfCourses){
        course = courseArray[row];
        }
        return course;
    }
    
    /***************************************************************************
     * Delete Record with Row number which is clicked. 
     * @param clickedRowNumber
     * @return 
     **************************************************************************/
    public String deleteRecordWithRow(int clickedRowNumber){
        courseArray = new CourseStruct[100]; 
        
        //Get the list of existing students from the database.  
        courseDb = new CourseTechSerLayer();
        courseArray = courseDb.getCourseList();
        numOfCourses = courseDb.getNumOfCourse();
        String str;
        str = "Record with Course ID " + courseArray[clickedRowNumber].courseID+ " deleted.";
        String courseIdTemp = courseArray[clickedRowNumber].courseID;
        
        //Delete the record Number which is clicked.
        for(int i = clickedRowNumber; i<numOfCourses; i++){
            courseArray[i] = courseArray[i+1];
        }
        numOfCourses--;
        saveCourseData(courseArray);
        
        // Drop courses from Student Registration. CourseStruct will be deleted from 
        // all the Students list for whom the course was registered.
        CourseRegDomainLayer courseRegProc = new CourseRegDomainLayer();
        courseRegProc.dropCoursesWhenCourseDeleted(courseIdTemp);
        
        return str;
    }
    
    /***************************************************************************
     * Modify Record with Row number which is clicked. 
     * @param clickedRowNumber
     * @param courseClicked
     * @return 
     **************************************************************************/
    public String modifyRecordWithRow(int clickedRowNumber,CourseStruct courseClicked){
        courseArray = new CourseStruct[100]; 
        
        //Get the list of existing students from the database.  
        courseDb = new CourseTechSerLayer();
        courseArray = courseDb.getCourseList();
        numOfCourses = courseDb.getNumOfCourse();
        String str;
        //str = "Record with student ID " + courseArray[clickedRowNumber].studentID+ " deleted.";
        
        //Delete the record Number which is clicked.
        for(int i = clickedRowNumber; i<numOfCourses; i++){
            courseArray[i] = courseArray[i+1];
        }
        numOfCourses--;
        
        // Check if the data already exits for the current student name.
        // If the CourseStruct ID already exists then return an error msg. 
        int i;
        for (i=0; i<numOfCourses; i++){
            //Check if the CourseStruct ID already exists in the database. 
            if(courseArray[i].courseID.equals(courseClicked.courseID)){
                String errorMsg;
                errorMsg = "Course with ID '" +courseClicked.courseID+ "' already exists.";
                return errorMsg;
            } //if(courseArray[i].studentID.equals(studentID))
        }//for (i=0; i<=numOfCourses; i++){
        
        //Add the modified data at the end of the array list. 
        courseArray[numOfCourses] = courseClicked;
        numOfCourses++;
        saveCourseData(courseArray); 
        
        str = "Record with Course ID " + courseClicked.courseID+ " modified.";
        return str;
    }
    
    
    /***************************************************************************
     * Return the CourseStruct List to populate into the Combo Box.
     * @return 
     **************************************************************************/
    public CourseStruct[] getCourseList(){
        String courseList[];
        courseList = new String[100];
        
        //Local object declaration.
        courseArray = new CourseStruct[100];    
        //Get the list of existing students from the database.  
        courseDb = new CourseTechSerLayer();
        courseArray = courseDb.getCourseList();
        numOfCourses = courseDb.getNumOfCourse();
//        CourseStruct course;
//        for(int i=0; i<numOfCourses;i++){
//            course = new CourseStruct();
//            course = courseArray[i];
//            courseList[i] = course.courseID;
//        }
        
        return courseArray;
    }
    /**************************************************************************/
}
