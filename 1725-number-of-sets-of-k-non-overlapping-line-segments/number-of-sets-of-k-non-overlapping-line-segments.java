class Solution {
    public int numberOfSets(int n, int k) {
        final int MOD = 1_000_000_007;
        
        long[] prev = new long[n + 1]; // dp[.][j-1]
        long[] curr = new long[n + 1]; // dp[.][j]
        
        // j = 0 base case
        for (int i = 0; i <= n; i++) prev[i] = 1;
        
        for (int j = 1; j <= k; j++) {
            curr[0] = 0;
            long prefix = 0;
            for (int i = 1; i <= n; i++) {
                curr[i] = (curr[i - 1] + prefix) % MOD;
                prefix = (prefix + prev[i]) % MOD;
            }
            // move curr into prev for next iteration
            long[] tmp = prev;
            prev = curr;
            curr = tmp;
        }
        
        return (int) prev[n];
    }
}