import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        // children lists using arrays
        int[] head = new int[n];
        int[] next = new int[n];
        Arrays.fill(head, -1);
        for (int i = 1; i < n; i++) {
            next[i] = head[parent[i]];
            head[parent[i]] = i;
        }

        int[] newParent = parent.clone();
        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] order = new int[n];     // preorder of the original tree
        int cnt = 0;

        int[] stack = new int[n];
        int[] iter = new int[n];      // current child pointer per node
        int[] saved = new int[n];     // saved last[c] to restore on exit
        int sp = 0;

        // enter root
        stack[sp++] = 0;
        iter[0] = head[0];
        order[cnt++] = 0;
        saved[0] = last[s.charAt(0) - 'a'];
        last[s.charAt(0) - 'a'] = 0;

        while (sp > 0) {
            int u = stack[sp - 1];
            int v = iter[u];
            if (v != -1) {
                iter[u] = next[v];
                int c = s.charAt(v) - 'a';
                if (last[c] != -1) newParent[v] = last[c];
                saved[v] = last[c];
                last[c] = v;
                iter[v] = head[v];
                order[cnt++] = v;
                stack[sp++] = v;
            } else {
                // exit u, restore
                last[s.charAt(u) - 'a'] = saved[u];
                sp--;
            }
        }

        int[] ans = new int[n];
        Arrays.fill(ans, 1);
        // reverse preorder: every node is processed before its new parent
        for (int i = n - 1; i >= 1; i--) {
            int v = order[i];
            ans[newParent[v]] += ans[v];
        }
        return ans;
    }
}