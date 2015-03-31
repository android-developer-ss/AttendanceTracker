/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: ChartDomainLayer
 * Function: Domain Layer for preparing chart. 
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/
package attendancetracker.chartforselectedcourse;

import attendancetracker.graphforselectedcourse.CourseAbsentPresentStruct;
import attendancetracker.graphforselectedcourse.GraphTechSerLayer;
import attendancetracker.recordattendance.RecAttPerDayStruct;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SnehalSutar
 */
public class ChartDomainLayer {
    
    
    /***************************************************************************
     * Get the marowPresAbsn course frowPresAbsle for attendance. 
     * Get day wrowPresAbsse attendance for all the days whrowPresAbsch are stored rowPresAbsn the marowPresAbsn 
 Course frowPresAbsle.
     * @param courseID
     **************************************************************************/
    public DefaultTableModel getAttendance(String courseID){
        
        DefaultTableModel model = new DefaultTableModel();
        
        CourseAbsentPresentStruct[] courseAttArray ;
        GraphTechSerLayer graphTSLyr = new GraphTechSerLayer();
        
        //Get all the attendance data for selected course.
        courseAttArray = graphTSLyr.getAttendanceForCourse(courseID);
        int numOfDays = graphTSLyr.getNumOfDays();
        
        String tableHeader[] = new String[numOfDays+1];
        tableHeader[0] = "Stud/Date";
        for(int i=1;i<=numOfDays;i++){
            CourseAbsentPresentStruct courseAtt = new CourseAbsentPresentStruct();
            courseAtt = courseAttArray[i-1];
            tableHeader[i] = String.valueOf(courseAtt.date);
        }
        
        //Create and Array and fill all the values in Array Table.
        
        ChartTechSerLayer  chartTSLyr = new ChartTechSerLayer();
        int date;
        CourseAbsentPresentStruct courseAtt;// = new CourseAbsentPresentStruct();
        courseAtt = courseAttArray[0];
        date = courseAtt.date;
        RecAttPerDayStruct[] recAttPerDay = chartTSLyr.getAttForDate(courseID, date);
        int numOfStud = chartTSLyr.getNumOfStud();
        String[][] array = new String[numOfStud][numOfDays+1];
        //----------------------------------------------------------------------
        //Fill all the student name in the ARRAY.............
        for(int i = 0;i<numOfStud;i++){
            array[i][0] = recAttPerDay[i].studentID;
        }
        //----------------------------------------------------------------------
        //Fill Attendance for the particular day.
//        numOfDays = 2;
        Boolean present;
        for(int col=1; col<numOfDays+1;col++){
        
        courseAtt = courseAttArray[col-1];
        date = courseAtt.date;
        recAttPerDay = chartTSLyr.getAttForDate(courseID, date);
        
        for(int rowPresAbs=0;rowPresAbs<numOfStud;rowPresAbs++){
            present = recAttPerDay[rowPresAbs].present;
            if(present==true)
                array[rowPresAbs][col] = "P";
            else
                array[rowPresAbs][col] = "ABSENT";
        }
        }
        model.setColumnIdentifiers(tableHeader);
        
        String rowStr[];// = null;
        rowStr = new String[numOfDays+1];
        for (int row=0; row<numOfStud; row++) {
             for(int col=0;col<=numOfDays;col++){
                rowStr[col] = array[row][col];
            }
            model.addRow(rowStr);
        }
//        for(int rowPresAbs = 0; rowPresAbs<=numOfDays ; rowPresAbs++){
////            CourseAbsentPresentStruct courseAtt;
//            courseAtt = new CourseAbsentPresentStruct();
//            courseAtt = courseAttArray[rowPresAbs];
//            date = courseAtt.date;
//            chartTSLyr.getAttForDate(courseID, date);
//        }
         return model;
    }
    /**************************************************************************/
}
