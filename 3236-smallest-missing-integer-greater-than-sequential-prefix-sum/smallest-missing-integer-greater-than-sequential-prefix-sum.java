class Solution {
    public int missingInteger(int[] nums) {
        
        // Step 1: Find sum of longest sequential prefix
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Step 2: Find the smallest missing integer >= sum
        int x = sum;

        while (contains(nums, x)) {
            x++;
        }

        return x;
    }

    // Check whether x exists in nums
    private boolean contains(int[] nums, int x) {
        for (int num : nums) {
            if (num == x) {
                return true;
            }
        }
        return false;
    }
}