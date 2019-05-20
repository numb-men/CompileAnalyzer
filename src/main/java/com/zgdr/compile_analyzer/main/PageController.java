package com.zgdr.compile_analyzer.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PageController
 * 页面跳转控制
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/5/20
 */
@Controller
public class PageController {

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index";
    }
}
