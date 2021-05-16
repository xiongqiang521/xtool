package com.xq.xtool.purview;

import java.util.*;

/**
 *
 */
public final class PurviewUtil {

    private final String[] levenStr;

    /**
     * leven 的数量不能超过30
     *
     * @param leven
     */
    public PurviewUtil(String[] leven) {
        if (leven == null || leven.length == 0 || leven.length > 30) {
            throw new RuntimeException("leven的数量不正确");
        }
        this.levenStr = leven;
    }

    public int get(String leven) {
        for (int i = 0; i < this.levenStr.length; i++) {
            if (Objects.equals(this.levenStr[i], leven)) {
                return 1 << i;
            }
        }
        return 0;
    }

    public List<String> get(int leven) {
        List<String> res = new ArrayList<>();
        int length = this.levenStr.length;
        for (int i = 0; i < length; i++) {
            if ((leven & (1 << i)) != 0) {
                res.add(this.levenStr[i]);
            }
        }
        return res;
    }

    public int combination(int p1, int p2) {
        return this.combination(new int[]{p1, p2});
    }

    public int combination(Collection<Integer> purviewes) {
        int res = 0;
        for (Integer integer : purviewes) {
            if (integer != null) {
                res |= integer;
            }
        }
        return res;
    }

    public int combination(int... purviewes) {
        int res = 0;
        for (int purview : purviewes) {
            res |= purview;
        }
        return res;
    }


}
