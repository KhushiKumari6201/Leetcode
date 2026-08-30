class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIdx = 0, maxIdx = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }
        
        int i = Math.min(minIdx, maxIdx);
        int j = Math.max(minIdx, maxIdx);
        
        // Option 1: remove both from the front (up to the later index)
        int fromFront = j + 1;
        
        // Option 2: remove both from the back (from the earlier index onward)
        int fromBack = n - i;
        
        // Option 3: remove one from front (up to i) and one from back (from j onward)
        int fromBoth = (i + 1) + (n - j);
        
        return Math.min(fromFront, Math.min(fromBack, fromBoth));
    }
}