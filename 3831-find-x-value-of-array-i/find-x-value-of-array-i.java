import java.util.*;

class Solution {
    public long[] resultArray(int[] nums, int k) {
        int n = nums.length;
        long[] result = new long[k];
        long[] cur = new long[k]; // represents cnt[i+1], counts of residues for subarrays starting at i+1

        for (int i = n - 1; i >= 0; i--) {
            long[] next = new long[k];
            int r0 = nums[i] % k;
            next[r0] += 1; // subarray consisting of just nums[i]

            for (int r = 0; r < k; r++) {
                if (cur[r] != 0) {
                    int nr = (r0 * r) % k;
                    next[nr] += cur[r];
                }
            }

            for (int r = 0; r < k; r++) {
                result[r] += next[r];
            }

            cur = next;
        }

        return result;
    }
}