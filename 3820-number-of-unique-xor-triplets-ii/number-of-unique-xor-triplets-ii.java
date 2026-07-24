class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        // Keep only distinct values
        boolean[] present = new boolean[MAX];
        for (int x : nums) present[x] = true;

        boolean[] dp0 = new boolean[MAX];
        boolean[] dp1 = new boolean[MAX];
        boolean[] dp2 = new boolean[MAX];
        boolean[] dp3 = new boolean[MAX];

        dp0[0] = true;

        for (int v = 0; v < MAX; v++) {
            if (!present[v]) continue;

            for (int x = 0; x < MAX; x++) {
                if (dp2[x]) dp3[x ^ v] = true;
            }
            for (int x = 0; x < MAX; x++) {
                if (dp1[x]) dp2[x ^ v] = true;
            }
            for (int x = 0; x < MAX; x++) {
                if (dp0[x]) dp1[x ^ v] = true;
            }
        }

        // Every value itself is achievable (a,a,a) or (a,a,b)
        for (int v = 0; v < MAX; v++) {
            if (present[v]) dp3[v] = true;
        }

        int ans = 0;
        for (boolean b : dp3) {
            if (b) ans++;
        }
        return ans;
    }
}