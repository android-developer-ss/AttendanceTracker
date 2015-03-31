/*******************************************************************************
 * Developed By: Snehal V Sutar. 
 * Net ID: svs130130
 * Class Name: AttendanceTracker
 * Function: Main starting point of the project. This will call the MainScreen
 *           developed as the first screen, from where the rest of the screens 
 *           can be accessed. 
 ******************************************************************************/

package attendancetracker;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author SnehalSutar
 */
public class AttendanceTracker {

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        MainScreen mainScr = new MainScreen();
        mainScr.setLocation(dim.width/2-mainScr.getSize().width/2, dim.height/2-mainScr.getSize().height/2);
        mainScr.setVisible(true);
    }
    
    
}
