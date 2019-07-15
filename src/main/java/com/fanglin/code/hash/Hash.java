package com.fanglin.code.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性哈希(无虚拟节点)
 *
 * @author 彭方林
 * @version 1.0
 * @date 2019/6/28 14:13
 **/
public class Hash {
    /**
     * 待添加入Hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111", "192.168.0.4:111"};
    /**
     * key表示服务器的hash值，value表示服务器
     */
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

    static {
        for (String server : servers) {
            int hash = getHash(server);
            System.out.printf("[%s]加入集合中, 其Hash值为%s%n", server, hash);
            sortedMap.put(hash, server);
        }
    }

    /**
     * 得到应当路由到的结点
     *
     * @param key
     * @return
     */
    private static String getServer(String key) {
        int hash = getHash(key);
        //得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = sortedMap.firstKey();
            //返回对应的服务器
            return sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     *
     * @param str
     * @return
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        String[] keys = {"太阳", "月亮", "星星"};
        for (String key : keys) {
            System.out.printf("[%s]的hash值为[%s], 被路由到结点[%s]%n", key, getHash(key), getServer(key));
        }
    }
}
