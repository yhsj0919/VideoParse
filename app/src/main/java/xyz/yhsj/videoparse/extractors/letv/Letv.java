package xyz.yhsj.videoparse.extractors.letv;

/**
 * 乐视解析
 * Created by LOVE on 2016/2/29.
 */
public class Letv {


    public static long getTkey(long stime) {
        long key = 773625421;

        long value = GenerateKeyRor(stime, key % 13);

        value = value ^ key;

        value = GenerateKeyRor(value, key % 17);


        System.err.println(">>>>>>>>>>>>>>>>>" + value);

        return value;
    }

    private static long GenerateKeyRor(long value, long key) {

        int temp = (int) (Math.pow(2, 32) - 1);
        return (((value & temp) >> key % 32) | (value << (32 - (key % 32)) & temp));
    }
}
