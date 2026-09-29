import java.util.*;

class Solution {
    private int m, n;
    private char[][] g;
    private byte[][][] memo; // 0 = unvisited, 1 = false, 2 = true

    public boolean hasValidPath(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        g = grid;

        // Odd path length can never be balanced
        if (((m + n - 1) & 1) == 1) return false;
        // Must start with '(' and end with ')'
        if (g[0][0] == ')' || g[m - 1][n - 1] == '(') return false;

        int maxBal = (m + n - 1) / 2;
        memo = new byte[m][n][maxBal + 2];
        return dfs(0, 0, 0, maxBal);
    }

    private boolean dfs(int i, int j, int bal, int maxBal) {
        bal += (g[i][j] == '(') ? 1 : -1;

        if (bal < 0 || bal > maxBal) return false;

        // Prune: balance can't be closed with the remaining steps
        int remaining = (m - 1 - i) + (n - 1 - j);
        if (bal > remaining) return false;

        if (i == m - 1 && j == n - 1) return bal == 0;

        if (memo[i][j][bal] != 0) return memo[i][j][bal] == 2;

        boolean res = false;
        if (i + 1 < m && dfs(i + 1, j, bal, maxBal)) res = true;
        else if (j + 1 < n && dfs(i, j + 1, bal, maxBal)) res = true;

        memo[i][j][bal] = (byte) (res ? 2 : 1);
        return res;
    }
}