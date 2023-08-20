
import java.util.*;

public class chocolates_travis
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        
        // Read in the chocolately information
        long w = sc.nextLong();
        long h = sc.nextLong();
        long x = sc.nextLong();
        
        // Special case (we can buy a lot of chocolate)
        if (h <= 2 * x / h && w <= 2 * x / w)
        {
            long h2 = h * (h + 1) / 2;
            long w2 = w * (w + 1) / 2;
            if (h2 <= x / w2)
            {
                if (h2 * w2 <= x){
                    System.out.println(h * w);
                    return;
                }
            }            
        }
        
        // Prepare the bounds for an awesome binary search 
        // (better than a regular old binary search)
        long min = 1;
        long max = 2;
        
        // Find an impossible upper bound
        while (canDo(max, x, h, w))
            max *= 2;
        
        // binary search
        while (max - min >= 2)
        {
            long mid = (max + min) / 2;
            if (canDo(mid, x, h, w))
                min = mid;
            else
                max = mid;
        }
        
        // Linear search from the binary point
        while (canDo(min + 1, x, h, w))
            min++;
        
        
        // Find the left overs
        long leftover = x - usedWith(min, h, w);
        long extras = leftover / (min + 1);
        
        // Print the number of extra bars plus the number of all smaller bars
        System.out.println(extras + numBars(min, h, w));
    }
    
    public static boolean canDo(long size, long x, long h, long w)
    {
        // Find out the total number of bars required when making 
        // bars of size size with a bound on the height and width...
        long total = usedWith(size, h, w);
        
        // Return if the total used is less than or equal to the maximum number of bars
        return (total <= x);
    }
    
    

    public static long usedWith(long bound, long h, long w)
    {
        long ret = 0;
        long max = 0;
        for (long i = 1; i * i <= bound && (i <= h && i <= w); i++)
        {
            // Find out the maximum length of the of width i
            long len = bound / i;
            
            // Try the bars one direction
            if (len > h)
                ret += i * ((h) * (h + 1) / 2);
            else
                ret += i * ((len) * (len + 1) / 2);
            
            // Try the bars the other direction (we double count!!!)
            if (len > w)
                ret += i * ((w) * (w + 1) / 2);
            else
                ret += i * ((len) * (len + 1) / 2);
            
            max = i;
        }
        
        // Remove double counting by finding the largest box of double counted bars
        long ww = w;
        long hh = h;
        if (ww > max)
            ww = max;
        if (hh > max)
            hh = max;
        ret -= (hh * (hh + 1) * ww * (ww + 1)) / 4;
        
        // Return the value
        return ret;
    }
    
    
    public static long numBars(long bound, long h, long w)
    {
        long ret = 0;
        long max = 0;
        for (long i = 1; i * i <= bound && (i <= h && i <= w); i++)
        {
            long len = bound / i;
            if (len > h)
                ret += h;
            else
                ret += len;
            
            if (len > w)
                ret += w;
            else
                ret += len;
            
            max = i;
        }
        long ww = w;
        long hh = h;
        if (ww > max)
            ww = max;
        if (hh > max)
            hh = max;
        ret -= hh * ww;
        return ret;
    }
}