import java.util.*;

class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;

        List<Integer> odds = new ArrayList<>();
        for (int i = 0; i < 26; i++) if (cnt[i] % 2 == 1) odds.add(i);

        char middle = '\0';
        boolean hasMiddle = false;
        if (n % 2 == 0) {
            if (!odds.isEmpty()) return "";
        } else {
            if (odds.size() != 1) return "";
            middle = (char) ('a' + odds.get(0));
            hasMiddle = true;
        }

        int h = n / 2;
        int[] pairs = new int[26];
        for (int i = 0; i < 26; i++) pairs[i] = cnt[i] / 2;

        String T = target.substring(0, h);

        // ---- Step 2: try L == T exactly ----
        int[] Tcount = new int[26];
        for (char c : T.toCharArray()) Tcount[c - 'a']++;
        boolean exactMatch = true;
        for (int i = 0; i < 26; i++) {
            if (Tcount[i] != pairs[i]) { exactMatch = false; break; }
        }
        if (exactMatch) {
            String full = buildPalindrome(T, hasMiddle, middle);
            if (full.compareTo(target) > 0) return full;
        }

        // If there's no left half, there is only ONE possible palindrome
        // (the exact match checked above), so nothing more to try.
        if (h == 0) return "";

        // ---- Step 3: find smallest L > T using the pair multiset ----
        int[] avail = pairs.clone();
        char[] prefixMatched = new char[h];
        int m = 0;
        while (m < h) {
            int idx = T.charAt(m) - 'a';
            if (avail[idx] > 0) {
                avail[idx]--;
                prefixMatched[m] = T.charAt(m);
                m++;
            } else {
                break;
            }
        }

        int[] cur = avail.clone();
        int j = m;
        if (m == h) {
            // backtrack from the state before the last matched position
            cur[prefixMatched[h - 1] - 'a']++;
            j = h - 1;
        }

        String resultL = null;
        while (j >= 0) {
            int foundIdx = -1;
            int tIdx = T.charAt(j) - 'a';
            for (int c = tIdx + 1; c < 26; c++) {
                if (cur[c] > 0) { foundIdx = c; break; }
            }
            if (foundIdx != -1) {
                cur[foundIdx]--;
                StringBuilder sb = new StringBuilder();
                sb.append(prefixMatched, 0, j);
                sb.append((char) ('a' + foundIdx));
                for (int c = 0; c < 26; c++) {
                    for (int k = 0; k < cur[c]; k++) sb.append((char) ('a' + c));
                }
                resultL = sb.toString();
                break;
            } else {
                if (j == 0) break;
                cur[prefixMatched[j - 1] - 'a']++;
                j--;
            }
        }

        if (resultL == null) return "";

        return buildPalindrome(resultL, hasMiddle, middle);
    }

    private String buildPalindrome(String left, boolean hasMiddle, char middle) {
        StringBuilder sb = new StringBuilder(left);
        if (hasMiddle) sb.append(middle);
        sb.append(new StringBuilder(left).reverse());
        return sb.toString();
    }
}