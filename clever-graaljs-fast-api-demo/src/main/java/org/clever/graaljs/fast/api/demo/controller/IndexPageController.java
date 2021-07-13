package org.clever.graaljs.fast.api.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/13 21:28 <br/>
 */
@RequestMapping("/")
@Controller
public class IndexPageController {

    @GetMapping
    public String indexPage() {
        // noinspection SpringMVCViewInspection
        return "/webjars/fast-api/index.html";
    }
}
