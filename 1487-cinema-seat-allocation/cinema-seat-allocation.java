import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> rowMasks = new HashMap<>();
        
        for (int[] rs : reservedSeats) {
            int row = rs[0];
            int seat = rs[1];
            int mask = rowMasks.getOrDefault(row, 0);
            mask |= (1 << seat);
            rowMasks.put(row, mask);
        }
        
        int leftMask  = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5); // seats 2,3,4,5
        int midMask   = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7); // seats 4,5,6,7
        int rightMask = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9); // seats 6,7,8,9
        
        long totalGroups = 0;
        
        for (int mask : rowMasks.values()) {
            if ((mask & leftMask) == 0 && (mask & rightMask) == 0) {
                totalGroups += 2;
            } else if ((mask & leftMask) == 0 || (mask & midMask) == 0 || (mask & rightMask) == 0) {
                totalGroups += 1;
            }
        }
        
        long emptyRows = n - rowMasks.size();
        totalGroups += emptyRows * 2;
        
        return (int) totalGroups;
    }
}