import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> nums[a] - nums[b]);

        int[] result = new int[n];
        List<Integer> group = new ArrayList<>();
        List<Integer> groupIdx = new ArrayList<>();

        for (int k = 0; k < n; k++) {
            int i = idx[k];
            if (group.isEmpty() || nums[i] - nums[idx[k - 1]] <= limit) {
                group.add(nums[i]);
                groupIdx.add(i);
            } else {
                assignGroup(result, group, groupIdx);
                group.clear();
                groupIdx.clear();
                group.add(nums[i]);
                groupIdx.add(i);
            }
        }
        // assign the last group
        assignGroup(result, group, groupIdx);

        return result;
    }

    private void assignGroup(int[] result, List<Integer> group, List<Integer> groupIdx) {
        List<Integer> sortedIdx = new ArrayList<>(groupIdx);
        Collections.sort(sortedIdx);
        for (int t = 0; t < group.size(); t++) {
            result[sortedIdx.get(t)] = group.get(t);
        }
    }
}