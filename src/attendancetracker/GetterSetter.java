 /*******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: GetterSetter
 * Function: For Student Object the values on the UI are stored in the object in
 *           UI Layer and then passed on to the Domain Layer. 
 ******************************************************************************/

package attendancetracker;

/**
 * @author SnehalSutar
 * This Class gets the values from the GUI Layer and helps them to pass to the 
 * Domain Layer which in turn passes data to the Technical Services Layer. 
 * The Getter and Setter methods are written for each and every field. 
 */
public class GetterSetter {
    
    //Declare Private variables. 
    String firstName  = "";
    String lastName   = "";
    String middleName = "";
    String studentID  = "";

   
    //get and set methods for the fields declared 
    /***************************************************************************
     * FIRST NAME
     * @return 
    ***************************************************************************/
    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    /***************************************************************************
     * LAST NAME
     * @return 
    ***************************************************************************/
    public String getLastName() 
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    /***************************************************************************
     * Middle Name
     * @return 
    ***************************************************************************/
    public String getMiddleName() 
    {
        return lastName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }
    /***************************************************************************
     * Student ID
     * @return 
    ***************************************************************************/
    public String getStudentID() 
    {
        return studentID;
    }

    public void setStudentID(String studentID)
    {
        this.studentID = studentID;
    }
    /***************************************************************************
     **************************************************************************/
}
/******************************************************************************/
