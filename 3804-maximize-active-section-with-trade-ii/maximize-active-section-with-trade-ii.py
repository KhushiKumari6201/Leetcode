class Solution:
    def maxActiveSectionsAfterTrade(self, s, queries):
        n = len(s)
        blocks = []
        i = 0
        while i < n:
            j = i
            while j < n and s[j] == s[i]:
                j += 1
            blocks.append((s[i], i, j - 1))
            i = j
        m = len(blocks)
        
        block_of = [0] * n
        for idx, (c, st, en) in enumerate(blocks):
            for k in range(st, en + 1):
                block_of[k] = idx
        
        NEG = -1
        full_gain = [NEG] * m
        for k in range(1, m - 1):
            c, st, en = blocks[k]
            if c == '1':
                left_len = blocks[k-1][2] - blocks[k-1][1] + 1
                right_len = blocks[k+1][2] - blocks[k+1][1] + 1
                full_gain[k] = left_len + right_len
        
        LOG = [0] * (m + 1)
        for i in range(2, m + 1):
            LOG[i] = LOG[i // 2] + 1
        
        sparse = [full_gain[:]]
        j = 1
        while (1 << j) <= m:
            prev = sparse[-1]
            half = 1 << (j - 1)
            length = 1 << j
            cur = [0] * (m - length + 1)
            for i in range(len(cur)):
                cur[i] = max(prev[i], prev[i + half])
            sparse.append(cur)
            j += 1
        
        def range_max(l, r):
            if l > r:
                return NEG
            k = LOG[r - l + 1]
            return max(sparse[k][l], sparse[k][r - (1 << k) + 1])
        
        total_ones = s.count('1')
        
        ans = []
        for l, r in queries:
            bi = block_of[l]
            bj = block_of[r]
            lo = bi + 1
            hi = bj - 1
            best = 0
            
            if lo <= hi:
                if lo + 1 <= hi - 1:
                    im = range_max(lo + 1, hi - 1)
                    if im > best:
                        best = im
                
                c, st, en = blocks[lo]
                if c == '1':
                    ZL = blocks[lo - 1]
                    left_len = ZL[2] - l + 1
                    if lo + 1 < m:
                        ZR = blocks[lo + 1]
                        if lo + 1 == bj:
                            right_len = r - ZR[1] + 1
                        else:
                            right_len = ZR[2] - ZR[1] + 1
                        gain = left_len + right_len
                        if gain > best:
                            best = gain
                
                if hi != lo:
                    c2, st2, en2 = blocks[hi]
                    if c2 == '1':
                        ZR2 = blocks[hi + 1]
                        right_len2 = r - ZR2[1] + 1
                        if hi - 1 >= 0:
                            ZL2 = blocks[hi - 1]
                            if hi - 1 == bi:
                                left_len2 = ZL2[2] - l + 1
                            else:
                                left_len2 = ZL2[2] - ZL2[1] + 1
                            gain2 = left_len2 + right_len2
                            if gain2 > best:
                                best = gain2
            
            ans.append(total_ones + best)
        
        return ans