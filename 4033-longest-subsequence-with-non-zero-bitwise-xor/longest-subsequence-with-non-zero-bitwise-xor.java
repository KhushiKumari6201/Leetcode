class Solution {
    public int longestSubsequence(int[] nums) {
        int n = nums.length;
        int xor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            xor ^= num;

            if (num != 0) {
                hasNonZero = true;
            }
        }

        // Entire array has non-zero XOR
        if (xor != 0) {
            return n;
        }

        // XOR is zero, but there is at least one non-zero element
        if (hasNonZero) {
            return n - 1;
        }

        // All elements are zero
        return 0;
    }
}