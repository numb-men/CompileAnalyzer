package com.zgdr.compile_analyzer.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * main.Token
 * 用于生成验证<name, type>
 *
 * @author hengyumo
 * @since 2019/5/17
 * @version 1.0
 */
public class Token {

    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Token(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public static Token error(String errorMessage){
        return new Token("error", errorMessage);
    }

    @Override
    public String toString() {
        return String.format(this.name.equals("error") ? "%s: %s":"<%s, %s>", this.name, this.type);
    }

    /**
     * 将Token列表转为字符串
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param tokens 待输出tokens
     * @return java.utils.String
     */
    public static String tokensToString(List<Token> tokens) {
        StringBuffer stringBuffer = new StringBuffer();
        tokens.forEach(str -> stringBuffer.append(str + "\r\n"));
        return stringBuffer.toString();
    }

    /**
     * 命令行输出Token列表
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param tokens 待输出tokens
     */
    public static void writeTokensToCmd(List<Token> tokens){
        tokens.forEach(System.out::println);
    }

    /**
     * 文件输出Token列表
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param fileName 待输出文件名
     * @param tokens 待输出tokens
     */
    public static void writeTokensToFile(String fileName, List<Token> tokens) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        for (Token token : tokens){
            fileWriter.write(token.toString() + "\r\n");
        }
        fileWriter.close();
    }
}
