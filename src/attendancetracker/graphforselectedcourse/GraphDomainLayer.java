/******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: GraphDomainLayer
 * Function: Domain Layer for displaying Graph.
 * Patterns: Indirection, Pure Fabrication,Low Coupling.
 ******************************************************************************/
package attendancetracker.graphforselectedcourse;

/**
 *
 * @author SnehalSutar
 */
public class GraphDomainLayer {
    
    /***************************************************************************
     * For the specified course ID get all the attendance details.
     * @param courseID 
     * @return  
     **************************************************************************/
    public CourseAbsentPresentStruct[] getCourseAtt(String courseID){
        CourseAbsentPresentStruct[] courseAttArray ;
        GraphTechSerLayer graphTSLyr = new GraphTechSerLayer();
        
        //Get all the attendance data for selected course.
        courseAttArray = graphTSLyr.getAttendanceForCourse(courseID);
        
        return courseAttArray;
    }
    
    /***************************************************************************
     * Return the number for days for which the attendance is recorded.
     * @return 
     **************************************************************************/
    public int getNumOfDays(){
      int numOfDays = 0;
      GraphTechSerLayer graphTSLyr = new GraphTechSerLayer();
      numOfDays = graphTSLyr.getNumOfDays();
      return numOfDays;
    }
    /**************************************************************************/
}
