class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        
        // suf[i] = longest suffix of word2 matchable (exact subsequence) using word1[i:]
        int[] suf = new int[n + 1];
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            suf[i] = suf[i + 1];
            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                suf[i]++;
                j--;
            }
        }
        
        int[] ans = new int[m];
        int idx = 0;      // pointer into ans
        j = 0;             // pointer into word2
        boolean mismatched = false;
        
        for (int i = 0; i < n && j < m; i++) {
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[idx++] = i;
                j++;
            } else if (!mismatched && suf[i + 1] >= m - j - 1) {
                ans[idx++] = i;
                mismatched = true;
                j++;
            }
        }
        
        return j == m ? ans : new int[0];
    }
}