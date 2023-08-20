
import java.util.*;

public class shirts_opt_backtrack
{
    public static String CAN_DO = "1";
    public static String NOT_ENOUGH_FRIENDS = "0";
    public static void main(String[] Args) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        
        String t = sc.nextLine();
        int n = Integer.parseInt(sc.nextLine());
        
        // Read in the friend's values
        String[] numbers = sc.nextLine().split(" ");
        
        if (numbers.length != n)
            throw new Exception("Not the correct number of friends " + n + " vs " + numbers.length);
        
        // Do a back Tracking solution
        if (rec(new boolean[n], 0, t, numbers, false))
        {
            System.out.println(CAN_DO);
        }
        else 
        {
            System.out.println(NOT_ENOUGH_FRIENDS);
        }
    }
    
    public static boolean rec(boolean[] used, int index, String target, String[] friends, boolean chosen)
    {
        // Check if the first character left to satisfy is a zero
        if (index < target.length() && target.charAt(index) == '0')
            return false;
        
        // Check if we can complete the number with or without our number
        if (index + (chosen ? 0 : 2) >= target.length())
            return true;
        
        // Check if we can choose a 1 digit number now to create the number
        if (!chosen && rec(used, index + 1, target, friends, true))
            return true;
        
        // Check if we can choose a 2 digit number now to create the number
        if (!chosen && rec(used, index + 2, target, friends, true))
            return true;
        
        // Flag that we have not tried a two digit friend
        boolean twoDig = false;
        
        // Flag that we have not tried a one digit friend
        boolean oneDig = false;
        
        // Try all possible friends
        for (int i = 0; i < friends.length; i++)
        {
            if (!used[i] && target.substring(index).startsWith(friends[i]))
            {
                // Handle two digit friend
                if (friends[i].length() == 2)
                {
                    if (twoDig)
                        continue;
                    twoDig = true;
                    used[i] = true;
                    if (rec(used, index + 2, target, friends, chosen))
                        return true;
                    
                    // backtrack
                    used[i] = false;
                }
                
                // Handle one digit friend
                if (friends[i].length() == 1)
                {
                    if (oneDig)
                        continue;
                    oneDig = true;
                    used[i] = true;
                    if (rec(used, index + 1, target, friends, chosen))
                        return true;
                    
                    // backtrack
                    used[i] = false;
                }
            }
        }
        
        // No solution was found
        return false;
    }
}