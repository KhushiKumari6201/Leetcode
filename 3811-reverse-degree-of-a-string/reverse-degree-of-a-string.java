class Solution {
    public int reverseDegree(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int reversedAlphaPos = 26 - (s.charAt(i) - 'a');
            sum += reversedAlphaPos * (i + 1);
        }
        return sum;
    }
}