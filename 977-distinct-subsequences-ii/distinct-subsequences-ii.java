class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;

        // dp[i] = number of distinct subsequences including empty subsequence
        long dp = 1;

        // Stores dp value before the previous occurrence of each character
        long[] last = new long[26];

        for (char ch : s.toCharArray()) {
            int index = ch - 'a';

            long newDp = (2 * dp % MOD - last[index] + MOD) % MOD;

            // Save current dp before updating
            last[index] = dp;

            dp = newDp;
        }

        // Remove the empty subsequence
        return (int) ((dp - 1 + MOD) % MOD);
    }
}