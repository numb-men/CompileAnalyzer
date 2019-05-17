package com.zgdr.compile_analyzer.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * main.Utils
 * 词法分析和语法分析使用到的工具类
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/5/17
 */
public class Utils {
    /**
     * 从命令行读取输入
     * @author hengyumo
     * @since 2019/5/17
     *
     * @return java.lang.String 读取到的字符串
     */
    public static String readFromCmd(){
        try {
            return readFrom("", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从文件读取输入
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param fileName 文件名
     * @return java.lang.String 读取到的字符串
     */
    public static String readFromFile(String fileName) throws IOException {
        return readFrom(fileName, true);
    }

    /**
     * 从文件或命令行读取输入
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param fileName 文件名
     * @param fromFile 是否从文件读取，false为从命令行
     * @return java.lang.String 读取到的字符串
     */
    public static String readFrom(String fileName, boolean fromFile) throws IOException {
        InputStream inputStream = fromFile ? new FileInputStream(fileName):System.in;
        StringBuffer strRead = new StringBuffer();
        byte[] tempBytes = new byte[1024]; // 每次读取1024字节
        boolean fileEnd = false;
        int tempLength = 0;
        while (!fileEnd) {
            while (tempLength < tempBytes.length){
                int tempByte = inputStream.read();
                if (tempByte == -1){
                    fileEnd = true;
                    break;
                }
                tempBytes[tempLength++] = (byte)tempByte;
            }
            String str = new String(tempBytes, 0, tempLength);
            strRead.append(str);
        }
        inputStream.close();
        return strRead.toString();
    }

    /**
     * 修整字符串
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param str 待修减字符串
     * @return java.lang.String 修剪好的字符串
     */
    public static String trimString(String str){
        return str.replaceAll("\\s+", " ").trim();
    }

    /**
     * 将字符串转成字节数组并输出
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param str 字符串
     */
    public static void printStringToBytes(String str){
        byte[] bytes = str.getBytes();
        for (byte b : bytes){
            System.out.print(b);
        }
        System.out.println();
    }
}
