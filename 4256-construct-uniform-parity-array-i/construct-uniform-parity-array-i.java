class Solution {
    public boolean uniformArray(int[] nums1) {
        int n = nums1.length;
        if (n == 1) return true;

        int oddCount = 0, evenCount = 0;
        for (int x : nums1) {
            if ((x & 1) == 0) evenCount++;
            else oddCount++;
        }

        // Can we make everything EVEN?
        // - even elements keep themselves
        // - odd elements need another odd element to subtract from (odd - odd = even)
        boolean canAllEven = (oddCount == 0) || (oddCount >= 2);

        // Can we make everything ODD?
        // - odd elements keep themselves
        // - even elements need an odd element to subtract from (even - odd = odd)
        boolean canAllOdd = (oddCount >= 1);

        return canAllEven || canAllOdd;
    }
}