class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];
        for (int s : stones) {
            cnt[s % 3]++;
        }
        
        if (cnt[0] % 2 == 0) {
            // Alice wins only if both residue-1 and residue-2 stones exist
            return cnt[1] >= 1 && cnt[2] >= 1;
        } else {
            // Odd number of residue-0 stones flips the parity advantage
            return Math.abs(cnt[1] - cnt[2]) > 2;
        }
    }
}