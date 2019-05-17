package com.zgdr.compile_analyzer.main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * main.WordAnalyzer
 * �ʷ�������
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/5/16
 */
public class WordAnalyzer {

    /* �ʷ������Ľ��token�б� */
    private List<Token> tokens;
    
    /* �������ַ��� */
    private String strForAnalyze;
    
    /* �������ַ��б� */
    private LinkedList<Character> charListForAnalyze;
    
    /* �����ַ��б� */
    private LinkedList<Character> charCacheList;
    
    /* ״̬��״ֵ̬ */
    private int state = 0;
    
    /* �ؼ������� */
    private final static String keywords[] = { "abstract", "assert", "boolean", "break", "byte", "case", "catch",
            "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
            "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "thorw", "throws", "transient", "try", "void", "volatile",
            "while" };

    /**
     * ����
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param strForAnalyze �������ַ�����������һ�η����﷨Ҫ��Ĵ���
     */
    public WordAnalyzer(String strForAnalyze) {
        this.updateStringForAnalyze(strForAnalyze);
    }

    /**
     * ���������������ַ���
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param strForAnalyze �������µ��ַ���
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
     * ���������еĵ�һ���ַ����뻺��
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
     * �ӻ�����ȡ�����һ���ַ��������������
     * @author hengyumo
     * @since 2019/5/17
     */
    private void popFromCache() {
        charListForAnalyze.offerFirst(charCacheList.pollLast());
    }

    /**
     * ����������ת���ַ�������
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
     * �ж��Ƿ��ǹؼ���
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param str ���ж��ַ���
     * @return boolean ���
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
     * ��token����token�б�
     * @author hengyumo
     * @since 2019/5/17
     *
     * @param tokenType token����
     */
    private void saveTokenWithType(String tokenType){
        add(new Token(getCache().trim(), tokenType));
        charCacheList.clear();
        state = 0;
    }
    
    /**
     * �ʷ�����
     * @author hengyumo
     * @since 2019/5/17
     *
     * @return java.util.List<main.Token> token�б�
     */
    public List<Token> analyze() {

        while (!charListForAnalyze.isEmpty()) {
            char c = pollFirstToCache(); //ÿ�ζ�ȡһ����ĸ���뻺��
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
                            add(Token.error("���Ϸ��ַ�:" + c));
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
                case 2: // ��ʶ��/�ؼ�������
                    popFromCache();
                    if (isKeyword( getCache().trim())) { // ������ڹؼ��ֱ����ҵ������ǹؼ�������
                        saveTokenWithType("key");
                    }
                    else { // �����Ǳ�ʶ������
                        saveTokenWithType("id");
                    }
                    break;
                case 3:
                    if (Character.isDigit(c)) // ����
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
                            add(Token.error("������:" + getCache()));
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
                            add(Token.error("������/����:" + getCache()));
                            charCacheList.clear();
                            continue;
                        }
                    }
                    break;
                case 7:
                    if (Character.isDigit(c))
                        state = 8;
                    else {
                        add(Token.error("������:" + getCache()));
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
                case 9: // ��ֵ����
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
                    saveTokenWithType("��ֵ");
                    break;
                case 60: // ?
                    saveTokenWithType("op");
                    break;
                case 61: // :
                    saveTokenWithType("op");
                    break;
                case 62: // [
                    saveTokenWithType("���");
                    break;
                case 63: // ]
                    saveTokenWithType("���");
                    break;
                case 64: // (
                    saveTokenWithType("���");
                    break;
                case 65: // )
                    saveTokenWithType("���");
                    break;
                case 66: // .
                    saveTokenWithType("op");
                case 67: // ,
                    saveTokenWithType("���");
                    break;
                case 68: // {
                    saveTokenWithType("���");
                    break;
                case 69: // }
                    saveTokenWithType("���");
                    break;
                case 70: // ;
                    saveTokenWithType("���");
                    break;
            }
        }
        return this.tokens;
    }
}
