import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        int[] dp = new int[1000003]; // values go up to 10^6 + 1
        int ans = 1;

        for (int x : nums) {
            // Use the element as x + 1 (computed first, from the old dp[x])
            dp[x + 1] = Math.max(dp[x + 1], dp[x] + 1);
            // Use the element as x
            dp[x] = Math.max(dp[x], dp[x - 1] + 1);

            ans = Math.max(ans, Math.max(dp[x], dp[x + 1]));
        }
        return ans;
    }
}