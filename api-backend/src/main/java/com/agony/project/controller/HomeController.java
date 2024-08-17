package com.agony.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Agony
 * @create 2024/4/1 23:08
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToDoc() {
        // 当访问根URL时，自动重定向到/doc.html
        return "redirect:/doc.html";
    }
}
