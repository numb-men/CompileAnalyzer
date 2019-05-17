package com.zgdr.compile_analyzer.main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * main.WordAnalyzer
 * 词法分析器
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/5/16
 */
public class WordAnalyzer {

    /* 词法分析的结果token列表 */
    private List<Token> tokens;
    
    /* 待分析字符串 */
    private String strForAnalyze;
    
    /* 待分析字符列表 */
    private LinkedList<Character> charListForAnalyze;
    
    /* 缓存字符列表 */
    private LinkedList<Character> charCacheList;
    
    /* 状态机状态值 */
    private int state = 0;
    
    /* 关键词数组 */
    private final static String keywords[] = { "abstract", "assert", "boolean", "break", "byte", "case", "catch",
            "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
            "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "thorw", "throws", "transient", "try", "void", "volatile",
            "while" };

    /**
     * 构造
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param strForAnalyze 待分析字符串，可以是一段符合语法要求的代码
     */
    public WordAnalyzer(String strForAnalyze) {
        this.updateStringForAnalyze(strForAnalyze);
    }

    /**
     * 更新用来分析的字符串
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param strForAnalyze 用来更新的字符串
     */
    public void updateStringForAnalyze(String strForAnalyze) {
        this.tokens = new ArrayList<>();
        this.strForAnalyze = strForAnalyze;
        this.charListForAnalyze = new LinkedList<>();
        char[] chars = this.strForAnalyze.toCharArray();
        for (char c : chars){
            this.charListForAnalyze.offer(c);
        }

//        StringBuffer stringBuffer = new StringBuffer();
//        charListForAnalyze.forEach(stringBuffer::append);
//        System.out.println(stringBuffer.toString());

        this.charCacheList = new LinkedList<>();
    }

    /**
     * 将分析队列的第一个字符存入缓存
     * @author hengyumo
     * @since 2019/5/17
     *
     * @return char
     */
    private char pollFirstToCache() {
        char c = charListForAnalyze.pollFirst();
        charCacheList.offerLast(c);
        return c;
    }

    /**
     * 从缓存中取出最后一个字符放入分析队列中
     * @author hengyumo
     * @since 2019/5/17
     */
    private void popFromCache() {
        charListForAnalyze.offerFirst(charCacheList.pollLast());
    }

    /**
     * 将缓存内容转成字符串返回
     * @author hengyumo
     * @since 2019/5/17
     *
     * @return java.lang.String
     */
    private String getCache() {
        StringBuffer stringBuffer = new StringBuffer();
        charCacheList.forEach(stringBuffer::append);
        return stringBuffer.toString();
    }

    /**
     * 判断是否是关键词
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param str 待判断字符串
     * @return boolean 结果
     */
    private boolean isKeyword(String str){
        for (String key : keywords){
            if (str.equals(key)){
                return true;
            }
        }
        return false;
    }

    private void add(Token token){
//        System.out.println(token);
        tokens.add(token);
    }
    
    /**
     * 将token存入token列表
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param tokenType token类型
     */
    private void saveTokenWithType(String tokenType){
        add(new Token(getCache().trim(), tokenType));
        charCacheList.clear();
        state = 0;
    }
    
    /**
     * 词法分析
     * @author hengyumo
     * @since 2019/5/17
     *
     * @return java.util.List<main.Token> token列表
     */
    public List<Token> analyze() {

        while (!charListForAnalyze.isEmpty()) {
            char c = pollFirstToCache(); //每次都取一个字母放入缓存
//            System.out.println(String.format("%c: %d", c, state));
            switch (state) {
                case 0:
                    if (Character.isLetter(c))
                        state = 1;
                    else if (Character.isDigit(c))
                        state = 3;
                    else if (c == '+')
                        state = 10;
                    else if (c == '-')
                        state = 14;
                    else if (c == '*')
                        state = 18;
                    else if (c == '/')
                        state = 21;
                    else if (c == '%')
                        state = 24;
                    else if (c == '&')
                        state = 27;
                    else if (c == '|')
                        state = 31;
                    else if (c == '!')
                        state = 35;
                    else if (c == '^')
                        state = 38;
                    else if (c == '<')
                        state = 41;
                    else if (c == '>')
                        state = 47;
                    else if (c == '=')
                        state = 57;
                    else if (c == '?') {
                        state = 60;
                        popFromCache();
                    } else if (c == ':') {
                        state = 61;
                        popFromCache();
                    } else if (c == '[') {
                        state = 62;
                        popFromCache();
                    } else if (c == ']') {
                        state = 63;
                        popFromCache();
                    } else if (c == '(') {
                        state = 64;
                        popFromCache();
                    } else if (c == ')') {
                        state = 65;
                        popFromCache();
                    } else if (c == '.') {
                        state = 66;
                        popFromCache();
                    } else if (c == ',') {
                        state = 67;
                        popFromCache();
                    } else if (c == '{') {
                        state = 68;
                        popFromCache();
                    } else if (c == '}') {
                        state = 69;
                        popFromCache();
                    } else if (c == ';') {
                        state = 70;
                        popFromCache();
                    } else {
                        if (c != ' ') {
                            add(Token.error("不合法字符:" + c));
                            charCacheList.clear();
                            continue;
                        }
                    }
                    break;
                case 1:
                    if (Character.isLetter(c) || Character.isDigit(c))
                        state = 1;
                    else {
                        popFromCache();
                        state = 2;
                    }
                    break;
                case 2: // 标识符/关键字类型
                    popFromCache();
                    if (isKeyword( getCache().trim())) { // 如果能在关键字表中找到，则是关键字类型
                        saveTokenWithType("key");
                    }
                    else { // 否则，是标识符类型
                        saveTokenWithType("id");
                    }
                    break;
                case 3:
                    if (Character.isDigit(c)) // 数字
                        state = 3;
                    else if (c == '.')
                        state = 4;
                    else if (c == 'e' || c == 'E')
                        state = 6;
                    else {
                        state = 9;
                        popFromCache();
                    }
                    break;
                case 4:
                    if (Character.isDigit(c))
                        state = 5;
                    else {
                        if (c != ' ') {
                            add(Token.error("非数字:" + getCache()));
                            charCacheList.clear();
                            continue;
                        }
                    }
                    break;
                case 5:
                    if (c == 'e' || c == 'E')
                        state = 6;
                    else {
                        state = 9;
                        popFromCache();
                    }
                    break;
                case 6:
                    if (c == '+' || c == '-')
                        state = 7;
                    else if (Character.isDigit(c))
                        state = 8;
                    else {
                        if (c != ' ') {
                            add(Token.error("非数字/运算:" + getCache()));
                            charCacheList.clear();
                            continue;
                        }
                    }
                    break;
                case 7:
                    if (Character.isDigit(c))
                        state = 8;
                    else {
                        add(Token.error("非数字:" + getCache()));
                        charCacheList.clear();
                        continue;
                    }
                    break;
                case 8:
                    if (Character.isDigit(c))
                        state = 8;
                    else {
                        state = 9;
                        popFromCache();
                    }
                    break;
                case 9: // 数值类型
                    popFromCache();
                    saveTokenWithType("num");
                    break;
                case 10:
                    if (c == '+') {
                        state = 11;
                    } else {
                        state = (c == '=' ? 12:13);
                    }
                    popFromCache();
                    break;
                case 11: // ++
                    saveTokenWithType("op");
                    break;
                case 12: // +=
                    saveTokenWithType("op");
                    break;
                case 13: // +
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 14:
                    // if (Character.isDigit(c))
                    // state = 3;
                    // else
                    if (c == '-') {
                        state = 15;
                    } else {
                        state = (c == '=' ? 16:17);
                    };
                    popFromCache();
                    break;
                case 15: // --
                    saveTokenWithType("op");
                    break;
                case 16: // -=
                    saveTokenWithType("op");
                    break;
                case 17: // -
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 18:
                    state = (c == '*' ? 19:20);
                    popFromCache();
                    break;
                case 19: // *=
                    saveTokenWithType("op");
                    break;
                case 20: // *
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 21:
                    state = (c == '=' ? 22:23);
                    popFromCache();
                    break;
                case 22: // /=
                    saveTokenWithType("op");
                    break;
                case 23: // /
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 24:
                    state = (c == '=' ? 25:26);
                    popFromCache();
                    break;
                case 25: // %=
                    saveTokenWithType("op");
                    break;
                case 26: // %
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 27:
                    if (c == '&') {
                        state = 28;
                    } else {
                        state = (c == '=' ? 29:30);
                    }
                    popFromCache();
                    break;
                case 28: // &&
                    saveTokenWithType("op");
                    break;
                case 29: // &=
                    saveTokenWithType("op");
                    break;
                case 30: // &
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 31:
                    if (c == '|') {
                        state = 32;
                    } else {
                        state = (c == '=' ? 33:34);
                    }
                    popFromCache();
                    break;
                case 32: // ||
                    saveTokenWithType("op");
                    break;
                case 33: // |=
                    saveTokenWithType("op");
                    break;
                case 34: // |
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 35:
                    state = (c == '=' ? 36:37);
                    popFromCache();
                    break;
                case 36: // !=
                    saveTokenWithType("op");
                    break;
                case 37: // !
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 38:
                    state = (c == '=' ? 39:40);
                    popFromCache();
                    break;
                case 39: // ^=
                    saveTokenWithType("op");
                    break;
                case 40: // ^
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 41:
                    if (c == '=') {
                        state = 42;
                        popFromCache();
                    } else if (c == '<') {
                        state = 43;
                    } else {
                        state = 46;
                        popFromCache();
                    }
                    break;
                case 42: // <=
                    saveTokenWithType("op");
                    break;
                case 43:
                    state = (c == '=' ? 44:45);
                    popFromCache();
                    break;
                case 44: // <<=
                    saveTokenWithType("op");
                    break;
                case 45: // <<
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 46: // <
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 47:
                    if (c == '=') {
                        state = 48;
                        popFromCache();
                    } else if (c == '>')
                        state = 49;
                    else {
                        state = 55;
                        popFromCache();
                    }
                    break;
                case 48: // >=
                    saveTokenWithType("op");
                    break;
                case 49:
                    if (c == '>')
                        state = 50;
                    else {
                        state = (c == '=' ? 53:54);
                        popFromCache();
                    }
                    break;
                case 50:
                    state = (c == '=' ? 51:52);
                    popFromCache();
                    break;
                case 51: // >>>=
                    saveTokenWithType("op");
                    break;
                case 52: // >>>
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 53: // >>=
                    saveTokenWithType("op");
                    break;
                case 54: // >>
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 55: // >
                    popFromCache();
                    saveTokenWithType("op");
                    break;
                case 56:
                    break;
                case 57:
                    state = (c == '=' ? 58:59);
                    popFromCache();
                    break;
                case 58: // ==
                    saveTokenWithType("op");
                    break;
                case 59: // =
                    popFromCache();
                    saveTokenWithType("赋值");
                    break;
                case 60: // ?
                    saveTokenWithType("op");
                    break;
                case 61: // :
                    saveTokenWithType("op");
                    break;
                case 62: // [
                    saveTokenWithType("界符");
                    break;
                case 63: // ]
                    saveTokenWithType("界符");
                    break;
                case 64: // (
                    saveTokenWithType("界符");
                    break;
                case 65: // )
                    saveTokenWithType("界符");
                    break;
                case 66: // .
                    saveTokenWithType("op");
                case 67: // ,
                    saveTokenWithType("界符");
                    break;
                case 68: // {
                    saveTokenWithType("界符");
                    break;
                case 69: // }
                    saveTokenWithType("界符");
                    break;
                case 70: // ;
                    saveTokenWithType("界符");
                    break;
            }
        }
        return this.tokens;
    }
}
