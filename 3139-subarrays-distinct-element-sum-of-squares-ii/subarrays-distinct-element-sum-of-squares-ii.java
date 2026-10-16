import java.util.*;

class Solution {
    static final long MOD = 1_000_000_007L;
    long[] sum, sq, lazy;

    private void apply(int o, int len, long v) {
        sq[o] = (sq[o] + 2 * v % MOD * sum[o] % MOD + v * v % MOD * len) % MOD;
        sum[o] = (sum[o] + v * len) % MOD;
        lazy[o] = (lazy[o] + v) % MOD;
    }

    private void pushDown(int o, int l, int r) {
        if (lazy[o] != 0) {
            int m = (l + r) >> 1;
            apply(o * 2, m - l + 1, lazy[o]);
            apply(o * 2 + 1, r - m, lazy[o]);
            lazy[o] = 0;
        }
    }

    private void update(int o, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            apply(o, r - l + 1, 1);
            return;
        }
        pushDown(o, l, r);
        int m = (l + r) >> 1;
        if (ql <= m) update(o * 2, l, m, ql, qr);
        if (qr > m) update(o * 2 + 1, m + 1, r, ql, qr);
        sum[o] = (sum[o * 2] + sum[o * 2 + 1]) % MOD;
        sq[o] = (sq[o * 2] + sq[o * 2 + 1]) % MOD;
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long ans = 0;
        for (int j = 0; j < n; j++) {
            int val = nums[j];
            update(1, 0, n - 1, last[val] + 1, j);
            last[val] = j;
            ans = (ans + sq[1]) % MOD;
        }
        return (int) ans;
    }
}