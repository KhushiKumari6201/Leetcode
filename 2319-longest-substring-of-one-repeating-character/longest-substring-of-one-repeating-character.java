class Solution {
    private char[] s;
    private int n;
    // segment tree arrays
    private int[] length, prefixLen, suffixLen, maxLen;
    private char[] leftChar, rightChar;

    public int[] longestRepeating(String sInput, String queryCharacters, int[] queryIndices) {
        this.s = sInput.toCharArray();
        this.n = s.length;

        length = new int[4 * n];
        prefixLen = new int[4 * n];
        suffixLen = new int[4 * n];
        maxLen = new int[4 * n];
        leftChar = new char[4 * n];
        rightChar = new char[4 * n];

        build(1, 0, n - 1);

        int k = queryCharacters.length();
        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);
            update(1, 0, n - 1, idx, c);
            result[i] = maxLen[1];
        }

        return result;
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            length[node] = 1;
            prefixLen[node] = 1;
            suffixLen[node] = 1;
            maxLen[node] = 1;
            leftChar[node] = s[l];
            rightChar[node] = s[l];
            return;
        }
        int mid = (l + r) / 2;
        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        build(leftNode, l, mid);
        build(rightNode, mid + 1, r);
        merge(node, leftNode, rightNode);
    }

    private void merge(int node, int leftNode, int rightNode) {
        length[node] = length[leftNode] + length[rightNode];
        leftChar[node] = leftChar[leftNode];
        rightChar[node] = rightChar[rightNode];

        // prefix
        if (prefixLen[leftNode] == length[leftNode] && leftChar[leftNode] == leftChar[rightNode]) {
            prefixLen[node] = length[leftNode] + prefixLen[rightNode];
        } else {
            prefixLen[node] = prefixLen[leftNode];
        }

        // suffix
        if (suffixLen[rightNode] == length[rightNode] && rightChar[rightNode] == rightChar[leftNode]) {
            suffixLen[node] = length[rightNode] + suffixLen[leftNode];
        } else {
            suffixLen[node] = suffixLen[rightNode];
        }

        // max
        maxLen[node] = Math.max(maxLen[leftNode], maxLen[rightNode]);
        if (rightChar[leftNode] == leftChar[rightNode]) {
            maxLen[node] = Math.max(maxLen[node], suffixLen[leftNode] + prefixLen[rightNode]);
        }
    }

    private void update(int node, int l, int r, int idx, char c) {
        if (l == r) {
            s[idx] = c;
            leftChar[node] = c;
            rightChar[node] = c;
            // length, prefixLen, suffixLen, maxLen remain 1
            return;
        }
        int mid = (l + r) / 2;
        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        if (idx <= mid) {
            update(leftNode, l, mid, idx, c);
        } else {
            update(rightNode, mid + 1, r, idx, c);
        }
        merge(node, leftNode, rightNode);
    }
}