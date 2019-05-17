package com.zgdr.compile_analyzer;

import com.zgdr.compile_analyzer.main.Token;
import com.zgdr.compile_analyzer.main.WordAnalyzer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

/**
 * controller
 * web接口
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/5/17
 */
@RestController
public class CompileAnalyzerController {

    @PostMapping(value = "/word_analyze")
    public String analyze(@RequestParam(name = "strForAnalyze") String strForAnalyze){
        if (strForAnalyze == null || strForAnalyze.equals("") || Pattern.matches("\\s", strForAnalyze)){
            return "strForAnalyzer can not be null or blank!";
        }
        else if (strForAnalyze.length() > Math.pow(10, 3)){
            return "strForAnalyzer is to long!It must be less than 1000 characters!";
        }
        else{
            WordAnalyzer wordAnalyzer = new WordAnalyzer(strForAnalyze);
            List<Token> tokens = wordAnalyzer.analyze();
            return Token.tokensToString(tokens);
        }
    }
}
