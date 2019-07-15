package bool;

import java.util.BitSet;

/**
 * 布隆过滤器
 *
 * @author 彭方林
 * @version 1.0
 * @date 2019/7/15 15:53
 **/
public class Bool {
    /**
     * 二进制向量的位数，相当于能存储1000万条url左右，误报率为千万分之一
     */
    private static final int BIT_SIZE = 2 << 28;
    /**
     * 用于生成信息指纹的8个随机数，最好选取质数
     */
    private static final int[] seeds = new int[]{3, 5, 7, 11, 13, 17, 19, 23};
    BitSet bitSet = new BitSet(BIT_SIZE);
    /**
     * 用于存储8个随机哈希值对象
     */
    Hash[] hashes = new Hash[seeds.length];

    public Bool() {
        for (int i = 0; i < seeds.length; i++) {
            hashes[i] = new Hash(seeds[i]);
        }
    }

    public static void main(String[] args) {
        String email = "zhenlingcn@126.com";
        Bool bool = new Bool();
        System.out.println(email + "是否在列表中： " + bool.contains(email));
        bool.add(email);
        System.out.println(email + "是否在列表中： " + bool.contains(email));
        email = "zhenlingcn@163.com";
        System.out.println(email + "是否在列表中： " + bool.contains(email));
    }

    public void add(String value) {
        if(value!=null){
            for (Hash hash : hashes) {
                bitSet.set(hash.getHash(value), true);
            }
        }
    }

    public boolean contains(String value) {
        if(value==null){
            return false;
        }
        boolean have = true;
        for (Hash hash : hashes) {
            have &= bitSet.get(hash.getHash(value));
        }
        return have;
    }

    class Hash {
        private int seed;

        public Hash(int seed) {
            this.seed = seed;
        }

        public int getHash(String string) {
            int val = 0;
            int len = string.length();
            for (int i = 0; i < len; i++) {
                val = val * seed + string.charAt(i);
            }
            return val & (BIT_SIZE - 1);
        }
    }
}
