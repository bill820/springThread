package com.winchem.log.common.math;

/**
 * @program: aftersale
 * @description: 进制转换utils
 * @author: zhanglb
 * @create: 2020-10-29 09:45
 */
public class BinaryTransfer {
    /**
     *  设置字符数组
     *     可以添加任意不重复字符，提高能转换的进制的上限
     */
    private static char chs[] = new char[36];
    static {
        for(int i = 0; i < 10 ; i++) {
            chs[i] = (char)('0' + i);
        }
        for(int i = 10; i < chs.length; i++) {
            chs[i] = (char)('A' + (i - 10));
        }
    }

    /**
     * 转换方法
     * @param num		元数据字符串
     * @param fromRadix	元数据的进制类型
     * @param toRadix	目标进制类型
     * @return
     */
    public static String transRadix(String num, int fromRadix, int toRadix) {
        Long number = Long.valueOf(num, fromRadix);
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append(chs[(int) (number%toRadix)]);
            number = number / toRadix;
        }
        return sb.reverse().toString();

    }

    /**
     * LONG 10 进制转换 32
     * @param numL
     * @return
     */
    public static String trans10To32(Long numL) {
        return transRadix(String.valueOf(numL), 10, 32);
    }
    //测试
    public static void main(String[] args) {
        System.out.println(trans10To32(1321627635047919618L));
    }
}
