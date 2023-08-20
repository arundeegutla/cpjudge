
// Import the scanner
import java.util.Scanner;

// The class for the window program
public class window_travis
{
    // The main method for the program
    public static void main(String[] Args) 
    {
        // Set up the scanner so we can read from standard input
        Scanner sc = new Scanner(System.in);
        
        // Read in the height of the wall
        int h = sc.nextInt();
        
        // Read in the width of the wall
        int w = sc.nextInt();
        
        // Read in the distance required from wall to window
        int d = sc.nextInt();
        
        // Compute the window's dimensions
        int windHeight = h - 2 * d;
        int windWidth = w - 2 * d;
        
        /* 
        Assume that the window can exist
        Alternatively you could use the following assignment
        boolean possible = (windHeight > 0) && (windWidth > 0); 
        */
        boolean possible = true;
        
        // Check if the border is too big for the height of the wall
        if (windHeight <= 0)
            possible = false;
        
        // Check if the border is too big for the width of the wall
        if (windWidth <= 0)
            possible = false;
        
        // Print the answer based on the previous checks
        if (possible)
        {
            // The window can exist print the maximum resulting area
            System.out.println(windWidth * windHeight);
        }
        else
        {
            // The window can't exist, so print 0 area
            System.out.println(0);
        }
    }
}