class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;
        int ans = -1;
        // consider each distinct value present in nums
        for (int val : nums) {
            if (val <= ans) continue; // no need to recheck smaller/equal values
            int count = 0;
            for (int start = 0; start + k <= n; start++) {
                boolean found = false;
                for (int j = start; j < start + k; j++) {
                    if (nums[j] == val) {
                        found = true;
                        break;
                    }
                }
                if (found) count++;
                if (count > 1) break; // early exit
            }
            if (count == 1) {
                ans = Math.max(ans, val);
            }
        }
        return ans;
    }
}