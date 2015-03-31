/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: RecAttDomainLayer
 * Function: Domain Layer for Recording Attendance.
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/

package attendancetracker.recordattendance;

import attendancetracker.courseregistration.CourseRegTechSerLayer;
import attendancetracker.courseregistration.CourseRegStruct;
import attendancetracker.student.StudentTechSerLayer;
import attendancetracker.student.StudentStruct;
import javax.swing.JTable;

/**
 *
 * @author SnehalSutar
 */
public class RecAttDomainLayer {
    
    RecAttStruct recAttArray[];
    public static int numOfStudents ;
    final int STUDENTID_col_pos = 0;
    final int PRESENT_col_pos = 4;
    public static int fileFound =0;
    public String saveAttendance(RecAttStruct recAtt, JTable studAttTable){
         //Local object declaration.
        recAttArray = new RecAttStruct[100]; 
        String studAttendance[][] = new String[60][2];
        int rowCount = studAttTable.getRowCount();
        int numOfStudentInCourse = 0;
        int numOfStudentPresent = 0;
        String studentID;
        Boolean present;
        for(int i = 0 ; i<=rowCount; i++)
        {
            studentID = (String) studAttTable.getValueAt(i, STUDENTID_col_pos);
            present   = (Boolean) studAttTable.getValueAt(i, PRESENT_col_pos);
            
            if(studentID == null) break;
            if(present == true) numOfStudentPresent++;
            
            studAttendance[i][0] = studentID;
            studAttendance[i][1] = present.toString();
            numOfStudentInCourse++;
        }
        
        RecAttTechSerLayer recAttDb = new RecAttTechSerLayer();
        String str = recAttDb.saveAttendance(studAttendance, recAtt,numOfStudentInCourse,numOfStudentPresent);
        return str;
    }
    
    
    
     /***************************************************************************
     * Update the Course Display table. Get all records from the Database, save
     * it in the database and return it in the type of DefaultModel.
     * @return 
     **************************************************************************/
    public Object[][] updateTable(){
        
        StudentStruct studArray[] = new StudentStruct[100];    
        //Get the list of existing students from the database.  
        StudentTechSerLayer studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();
        
        int i;
        
        
        Object data[][] = new Object[numOfStudents][5] ;
       
        int row,col;
        for (row=0; row<numOfStudents; row++) {
            //for(col = 0; col <=4; col++){
                data[row][0] = studArray[row].studentID;
                data[row][1] = studArray[row].firstName;
                data[row][2] = studArray[row].middleName;
                data[row][3] = studArray[row].lastName;
                data[row][4] = true;
            //}
        }
          
        return data;
    }
    
    /***************************************************************************
     * Return StudentStructs who have enrolled for the particular course. 
     * @param courseID
     * @return 
     **************************************************************************/
    public Object[][] getStudentsEnrolledForCourse(String courseID){
       
        StudentStruct studArray[] ;
        
        //Get the list of existing students from the database.  
        StudentTechSerLayer studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();
        Object data[][] = new Object[numOfStudents][5] ;
       
        //Get Course Registration Data from CourseRegProcessing. 
        CourseRegTechSerLayer courseRegDb = new CourseRegTechSerLayer();
        CourseRegStruct courseRegArray[];
        courseRegArray = courseRegDb.getCourseRegListForCourse(courseID);
        
        CourseRegStruct courseReg = new CourseRegStruct();
        int studForCourse = courseRegDb.getStudNumForCourse(); 
        int row,i,rowCount=0;
        for (row=0; row<numOfStudents; row++) {
            for (i=0; i<studForCourse;i++){
                courseReg = courseRegArray[i];
                if(courseReg.studentID.equals(studArray[row].studentID)){
                data[rowCount][0] = studArray[row].studentID;
                data[rowCount][1] = studArray[row].firstName;
                data[rowCount][2] = studArray[row].middleName;
                data[rowCount][3] = studArray[row].lastName;
                data[rowCount][4] = true;
                rowCount++;
                }
            }
        }
          
        return data;
    }
    
    /***************************************************************************
     * Check if Attendance is already marked for the particular course selected
     * and the date selected on the GUI Layer. If attendance is already marked, 
     * then get the values from the file and create new table model and send to 
     * the GUI Layer. 
     * @param recAtt
     * @return 
     **************************************************************************/
     public Object[][]  checkIfAttendanceMarked( RecAttStruct recAtt){
         
         Object[][] data = new Object[60][5];
         String courseDateFileName = recAtt.courseID + " - " +
                   recAtt.year + recAtt.month + recAtt.day
                   + ".txt";
         RecAttTechSerLayer recAttDb = new RecAttTechSerLayer();
         
         //Check if File exists.
         fileFound = recAttDb.checkIfFileExists(courseDateFileName);
         if(fileFound == 1){
             RecAttPerDayStruct[] recAttPerDayArray;// = new RecAttPerDayStruct[60];
             recAttPerDayArray = recAttDb.getStudAttPerDay(courseDateFileName);    
             // Merge the actual number of students in the class and the 
             // attendance marked for them previously.
             
         // Fill the table with the students who are registered for the selected 
         // course.
          data = getStudentsEnrolledForCourse(recAtt.courseID);
          int count = data.length;
          for(int row = 0; row<CourseRegTechSerLayer.courseNumForCourse; row++){
              for(int i = 0;i<count;i++){
                  RecAttPerDayStruct recAttPerDay;
                  //recAttPerDay = new RecAttPerDayStruct();
                  recAttPerDay = recAttPerDayArray[i];
                  if(data[row][0].equals(recAttPerDay.studentID) ){
                    data[row][4] = recAttPerDay.present;  
                    break;
                  }
              }//for(int i = 0;i<count;i++)
          }
         }//if(fileFound == 1)
         else{
             data = getStudentsEnrolledForCourse(recAtt.courseID);
         }
         
         return data;
     }
    /**************************************************************************/
}
