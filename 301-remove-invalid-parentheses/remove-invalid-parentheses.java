import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        int leftRem = 0, rightRem = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                leftRem++;
            } else if (c == ')') {
                if (leftRem > 0) leftRem--;
                else rightRem++;
            }
        }

        List<String> result = new ArrayList<>();
        dfs(s, 0, leftRem, rightRem, result);
        return result;
    }

    private void dfs(String s, int start, int leftRem, int rightRem, List<String> result) {
        if (leftRem == 0 && rightRem == 0) {
            if (isValid(s)) result.add(s);
            return;
        }

        for (int i = start; i < s.length(); i++) {
            // skip duplicates: only remove the first of consecutive identical chars
            if (i > start && s.charAt(i) == s.charAt(i - 1)) continue;

            char c = s.charAt(i);
            String next = s.substring(0, i) + s.substring(i + 1);

            if (c == '(' && leftRem > 0) {
                dfs(next, i, leftRem - 1, rightRem, result);
            } else if (c == ')' && rightRem > 0) {
                dfs(next, i, leftRem, rightRem - 1, result);
            }
        }
    }

    private boolean isValid(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                if (--count < 0) return false;
            }
        }
        return count == 0;
    }
}