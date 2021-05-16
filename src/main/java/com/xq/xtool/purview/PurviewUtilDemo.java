package com.xq.xtool.purview;

import java.util.ArrayList;

/**
 *
 */
public final class PurviewUtilDemo {

    //  采用二进制计算，不能简单的相加                          1        2         4          8
    private static final String[] leven = new String[]{"read", "insert", "update", "delete"};
    // 建议将PurviewUtil对象设为单例，后面的合并、取值等操作使用同一个对象
    private static final PurviewUtil purviewUtil = new PurviewUtil(leven);

    public static void main(String[] args) {

        System.out.println(purviewUtil.get(15));
        System.out.println(purviewUtil.get("update"));
        System.out.println(purviewUtil.get(purviewUtil.combination(5, 8)));

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(5);
        integers.add(null);
        System.out.println(purviewUtil.get(purviewUtil.combination(integers)));

    }



}
