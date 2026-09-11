class Solution {
    public int totalNumbers(int[] digits) {
        Set<Integer> result = new HashSet<>();
        int n = digits.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == i) continue;
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    if (digits[i] == 0) continue; // no leading zero
                    if (digits[k] % 2 != 0) continue; // must be even
                    int num = digits[i] * 100 + digits[j] * 10 + digits[k];
                    result.add(num);
                }
            }
        }
        return result.size();
    }
}