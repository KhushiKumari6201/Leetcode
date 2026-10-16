import java.util.*;

class Solution {
    public int sumCounts(List<Integer> nums) {
        int n = nums.size();
        int result = 0;

        for (int i = 0; i < n; i++) {
            boolean[] seen = new boolean[101];
            int distinct = 0;

            for (int j = i; j < n; j++) {
                int val = nums.get(j);
                if (!seen[val]) {
                    seen[val] = true;
                    distinct++;
                }
                result += distinct * distinct;
            }
        }

        return result;
    }
}