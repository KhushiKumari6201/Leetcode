class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();

        int ones = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') ones++;
        }

        String t = "1" + s + "1";
        int m = t.length();

        int[] zeroLen = new int[m];
        int[] oneLen = new int[m];

        int i = 0;
        while (i < m) {
            char ch = t.charAt(i);
            int j = i;
            while (j < m && t.charAt(j) == ch) j++;

            if (ch == '0')
                zeroLen[i] = j - i;
            else
                oneLen[i] = j - i;

            i = j;
        }

        int ans = ones;

        i = 0;
        while (i < m) {
            if (zeroLen[i] > 0) {
                int leftZero = zeroLen[i];
                int oneStart = i + leftZero;

                if (oneStart < m && oneLen[oneStart] > 0) {
                    int oneBlock = oneLen[oneStart];
                    int rightZeroStart = oneStart + oneBlock;

                    if (rightZeroStart < m && zeroLen[rightZeroStart] > 0) {
                        int rightZero = zeroLen[rightZeroStart];
                        ans = Math.max(ans, ones + leftZero + rightZero);
                    }
                }
                i += leftZero;
            } else {
                i += oneLen[i];
            }
        }

        return ans;
    }
}