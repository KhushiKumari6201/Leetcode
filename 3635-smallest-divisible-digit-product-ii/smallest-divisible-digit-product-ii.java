import java.util.*;

class Solution {
    static int[] EXP2 = {0,0,1,0,2,0,1,0,3,0};
    static int[] EXP3 = {0,0,0,1,0,0,1,0,0,2};
    static int[] EXP5 = {0,0,0,0,0,1,0,0,0,0};
    static int[] EXP7 = {0,0,0,0,0,0,0,1,0,0};

    int[][] dp; // dp[i][j] = min digits to get >= i twos and >= j threes

    public String smallestNumber(String num, long t) {
        int a = 0, b = 0, c = 0, d = 0;
        long tt = t;
        while (tt % 2 == 0) { a++; tt /= 2; }
        while (tt % 3 == 0) { b++; tt /= 3; }
        while (tt % 5 == 0) { c++; tt /= 5; }
        while (tt % 7 == 0) { d++; tt /= 7; }
        if (tt != 1) return "-1";

        // build dp table
        dp = new int[a + 1][b + 1];
        int[][] options = {{1,0},{2,0},{3,0},{0,1},{0,2},{1,1}};
        for (int i = 0; i <= a; i++) {
            for (int j = 0; j <= b; j++) {
                if (i == 0 && j == 0) { dp[i][j] = 0; continue; }
                int best = Integer.MAX_VALUE;
                for (int[] op : options) {
                    int ni = Math.max(0, i - op[0]);
                    int nj = Math.max(0, j - op[1]);
                    if (ni == i && nj == j) continue;
                    best = Math.min(best, 1 + dp[ni][nj]);
                }
                dp[i][j] = best;
            }
        }

        int n = num.length();
        char[] digits = num.toCharArray();
        boolean hasZero = false;
        int firstZero = -1;
        long totalA = 0, totalB = 0, totalC = 0, totalD = 0;

        for (int i = 0; i < n; i++) {
            int dv = digits[i] - '0';
            if (dv == 0) {
                hasZero = true;
                if (firstZero == -1) firstZero = i;
            } else {
                totalA += EXP2[dv]; totalB += EXP3[dv];
                totalC += EXP5[dv]; totalD += EXP7[dv];
            }
        }

        if (!hasZero && totalA >= a && totalB >= b && totalC >= c && totalD >= d) {
            return num;
        }

        int[] prefA = new int[n + 1], prefB = new int[n + 1];
        int[] prefC = new int[n + 1], prefD = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int dv = digits[i - 1] - '0';
            prefA[i] = prefA[i - 1] + EXP2[dv];
            prefB[i] = prefB[i - 1] + EXP3[dv];
            prefC[i] = prefC[i - 1] + EXP5[dv];
            prefD[i] = prefD[i - 1] + EXP7[dv];
        }

        int maxValidI = hasZero ? firstZero : n - 1;

        int foundI = -1, foundDigit = -1;
        int fra = 0, frb = 0, frc = 0, frd = 0;

        outer:
        for (int i = maxValidI; i >= 0; i--) {
            int origDigit = digits[i] - '0';
            for (int dig = origDigit + 1; dig <= 9; dig++) {
                int totA = prefA[i] + EXP2[dig];
                int totB = prefB[i] + EXP3[dig];
                int totC = prefC[i] + EXP5[dig];
                int totD = prefD[i] + EXP7[dig];
                int ra = Math.max(0, a - totA);
                int rb = Math.max(0, b - totB);
                int rc = Math.max(0, c - totC);
                int rd = Math.max(0, d - totD);
                int remPos = n - 1 - i;
                int cost = rc + rd + dp[ra][rb];
                if (cost <= remPos) {
                    foundI = i; foundDigit = dig;
                    fra = ra; frb = rb; frc = rc; frd = rd;
                    break outer;
                }
            }
        }

        if (foundI != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(num, 0, foundI);
            sb.append((char) ('0' + foundDigit));
            int L = n - 1 - foundI;
            sb.append(buildSuffix(L, fra, frb, frc, frd));
            return sb.toString();
        } else {
            int minDigitsFull = c + d + dp[a][b];
            int L = Math.max(n + 1, minDigitsFull);
            return buildSuffix(L, a, b, c, d);
        }
    }

    private String buildSuffix(int L, int ra, int rb, int rc, int rd) {
        StringBuilder sb = new StringBuilder();
        for (int pos = 0; pos < L; pos++) {
            int remPos = L - 1 - pos;
            for (int dig = 1; dig <= 9; dig++) {
                int nra = Math.max(0, ra - EXP2[dig]);
                int nrb = Math.max(0, rb - EXP3[dig]);
                int nrc = Math.max(0, rc - EXP5[dig]);
                int nrd = Math.max(0, rd - EXP7[dig]);
                int cost = nrc + nrd + dp[nra][nrb];
                if (cost <= remPos) {
                    sb.append((char) ('0' + dig));
                    ra = nra; rb = nrb; rc = nrc; rd = nrd;
                    break;
                }
            }
        }
        return sb.toString();
    }
}