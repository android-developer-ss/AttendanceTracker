/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: StudentDomainLayer
 * Function: Domain Layer for ADD/DELETE/MODIFY student records. 
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/

package attendancetracker.student;

import attendancetracker.courseregistration.CourseRegDomainLayer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SnehalSutar
 */
public class StudentDomainLayer {
    
    //private final int MAX_STUD = 100;
    StudentStruct[] studArray;
    public int numOfStudents;
    StudentTechSerLayer studDb;
         
    /***************************************************************************
     * Constructor for StudentStructDomainLayer. 
     * This constructor will create the StudentStruct ArrayList once. Get all the 
 student records in it for processing for all functions add/modify/delete.
     **************************************************************************/
    public void StudentProcessing(){}    
    
    /***************************************************************************
     * Add a student by getting values entered in the GUI Layer of the student
 named StudentStructGUI.For this first we will check if any student exists with
 the same First Name, Last Name, Middle Name OR StudentStruct ID.
     * @param getset 
     * @return  string: success or error message.
     **************************************************************************/
    public String addStudent(StudentStruct student){
        
        //Get the details of the current student.
//        String firstName  = getset.getFirstName();
//        String lastName   = getset.getLastName();
//        String middleName = getset.getMiddleName();
//        String studentID  = getset.getStudentID();
       
        studArray = new StudentStruct[100];    
        //Get the list of existing students from the database.  
        studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();
        
        // Check if the data already exits for the current student name.
        // If the StudentStruct ID already exists then return an error msg. 
        int i;
        for (i=0; i<numOfStudents; i++){
            //Check if the StudentStruct ID already exists in the database. 
            if(studArray[i].studentID.equals(student.studentID)){
                String errorMsg;
                errorMsg = "Student with ID '" +student.studentID+ "' already exists.";
                return errorMsg;
            } //if(studArray[i].studentID.equals(studentID))
        }//for (i=0; i<=numOfStudents; i++){
        
        //If no student exists with the same StudentStruct ID then add a new student. 
        studArray[numOfStudents] = new StudentStruct();
        studArray[numOfStudents].firstName  = student.firstName;
        studArray[numOfStudents].middleName = student.middleName;
        studArray[numOfStudents].lastName   = student.lastName;
        studArray[numOfStudents].studentID  = student.studentID;
        studArray[numOfStudents].addrLine1  = student.addrLine1;
        studArray[numOfStudents].addrLine2  = student.addrLine2;
        studArray[numOfStudents].ssnNum     = student.ssnNum;
        studArray[numOfStudents].phoneNum   = student.phoneNum;
        studArray[numOfStudents].emailID    = student.emailID;
        numOfStudents++;
        
        saveStudentData(studArray);
        
        String successMsg;
        successMsg = "Student added.";
        return successMsg;        
    }
    
    public void saveStudentData(StudentStruct[] studArray){
        studDb.saveStudArray(studArray, numOfStudents);
    }
    
    
    
    /***************************************************************************
     * Update the StudentStruct Display table. Get all records from the Database, save
     * it in the database and return it in the type of DefaultModel.
     * @param studentDetailsTable
     * @return 
     **************************************************************************/
    public DefaultTableModel updateTable(JTable studentDetailsTable){
        
        DefaultTableModel model = new DefaultTableModel();
        
        studArray = new StudentStruct[100];    
        //Get the list of existing students from the database.  
        studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();

        model.setColumnIdentifiers(new String[]{"Student ID", "First Name", "MiddleName", "LastName"});
        int i;
        String rowStr[] = null;
        rowStr = new String[4];
        for (i=0; i<numOfStudents; i++) {
            rowStr[0]= studArray[i].studentID;
            rowStr[1]= studArray[i].firstName;
            rowStr[2]= studArray[i].middleName;
            rowStr[3]= studArray[i].lastName;
            model.addRow(rowStr);
            //model.addRow(studArray[i].studentID,studArray[i].studentID,studArray[i].studentID,studArray[i].studentID);
        }
          
        //studentDetailsTable.setModel(model);
        return model;
    }
    
    
    
    /***************************************************************************
     * Get the record at particular ROW. Save it in a type of StudentStruct object.
     * @param row
     * @return 
     **************************************************************************/
    public StudentStruct getValuesAtRow(int row){
        StudentStruct stud = null;
        studArray = new StudentStruct[100];    
        //Get the list of existing students from the database.  
        studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();
        
        stud = new StudentStruct();
        if (row<=numOfStudents){
        stud = studArray[row];
        }
        return stud;
    }
    
    /***************************************************************************
     * Delete Record with Row number which is clicked. 
     * @param clickedRowNumber
     * @return 
     **************************************************************************/
    public String deleteRecordWithRow(int clickedRowNumber){
        studArray = new StudentStruct[100]; 
        
        //Get the list of existing students from the database.  
        studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();
        String str;
        str = "Record with student ID " + studArray[clickedRowNumber].studentID+ " deleted.";
        String tempStudentID = studArray[clickedRowNumber].studentID;
        //Delete the record Number which is clicked.
        for(int i = clickedRowNumber; i<numOfStudents; i++){
            studArray[i] = studArray[i+1];
        }
        numOfStudents--;
        saveStudentData(studArray); 
        
        //Delete StudentStruct from Registration table once he is deleted from StudentStruct
        // table.
        CourseRegDomainLayer courseRegProc = new CourseRegDomainLayer();
        courseRegProc.deleteStudentWhenStudDeleted(tempStudentID);
        
        return str;
    }
    
    /***************************************************************************
     * Modify Record with Row number which is clicked. 
     * @param clickedRowNumber
     * @return 
     **************************************************************************/
    public String modifyRecordWithRow(int clickedRowNumber,StudentStruct studClicked){
        studArray = new StudentStruct[100]; 
        
        //Get the list of existing students from the database.  
        studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();
        String str;
        //str = "Record with student ID " + studArray[clickedRowNumber].studentID+ " deleted.";
        
        //Delete the record Number which is clicked.
        for(int i = clickedRowNumber; i<numOfStudents; i++){
            studArray[i] = studArray[i+1];
        }
        numOfStudents--;
        
        // Check if the data already exits for the current student name.
        // If the StudentStruct ID already exists then return an error msg. 
        int i;
        for (i=0; i<numOfStudents; i++){
            //Check if the StudentStruct ID already exists in the database. 
            if(studArray[i].studentID.equals(studClicked.studentID)){
                String errorMsg;
                errorMsg = "Student with ID '" +studClicked.studentID+ "' already exists.";
                return errorMsg;
            } //if(studArray[i].studentID.equals(studentID))
        }//for (i=0; i<=numOfStudents; i++){
        
        //Add the modified data at the end of the array list. 
        
        studArray[numOfStudents] = studClicked;
        numOfStudents++;
        saveStudentData(studArray); 
        
        str = "Record with student ID " + studClicked.studentID+ " modified.";
        return str;
    }
    
    /***************************************************************************
     * Return the Course List to populate into the Combo Box.
     * @return 
     **************************************************************************/
    public StudentStruct[] getStudentList(){
        String studentList[];
        studentList = new String[100];
        
        //Local object declaration.
        studArray = new StudentStruct[100];    
        //Get the list of existing students from the database.  
        studDb = new StudentTechSerLayer();
        studArray = studDb.getStudentList();
        numOfStudents = studDb.getNumOfStud();
        return studArray;
    }
    /**************************************************************************/
    /**************************************************************************/
}
