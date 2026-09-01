import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = classroom[i].charAt(j);
            }
        }

        int sr = -1, sc = -1;
        List<int[]> litterPositions = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = grid[i][j];
                if (c == 'S') {
                    sr = i;
                    sc = j;
                } else if (c == 'L') {
                    litterPositions.add(new int[]{i, j});
                }
            }
        }

        int k = litterPositions.size();
        if (k == 0) return 0;

        // map (r,c) -> litter index
        int[][] litterIndex = new int[m][n];
        for (int[] row : litterIndex) Arrays.fill(row, -1);
        for (int idx = 0; idx < k; idx++) {
            int[] pos = litterPositions.get(idx);
            litterIndex[pos[0]][pos[1]] = idx;
        }

        int fullMask = (1 << k) - 1;
        int E = energy;          // max capacity
        int sizeE = E + 1;
        int sizeMask = 1 << k;

        // state encoding: ((r * n + c) * sizeE + e) * sizeMask + mask
        int totalStates = m * n * sizeE * sizeMask;
        boolean[] visited = new boolean[totalStates];

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        // state array: {r, c, e, mask, dist}
        int startCode = encode(sr, sc, E, 0, n, sizeE, sizeMask);
        visited[startCode] = true;
        queue.add(new int[]{sr, sc, E, 0, 0});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], e = cur[2], mask = cur[3], dist = cur[4];

            if (mask == fullMask) {
                return dist;
            }
            if (e == 0) {
                continue; // stuck unless standing on R (handled when we arrived)
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;

                char cell = grid[nr][nc];
                if (cell == 'X') continue;

                int newE = e - 1;
                if (cell == 'R') {
                    newE = E;
                }

                int newMask = mask;
                if (cell == 'L') {
                    int idx = litterIndex[nr][nc];
                    if (idx != -1) {
                        newMask = mask | (1 << idx);
                    }
                }

                int code = encode(nr, nc, newE, newMask, n, sizeE, sizeMask);
                if (!visited[code]) {
                    visited[code] = true;
                    queue.add(new int[]{nr, nc, newE, newMask, dist + 1});
                }
            }
        }

        return -1;
    }

    private int encode(int r, int c, int e, int mask, int n, int sizeE, int sizeMask) {
        return ((r * n + c) * sizeE + e) * sizeMask + mask;
    }
}