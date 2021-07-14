package org.clever.graaljs.fast.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/13 21:28 <br/>
 */
@Controller
public class IndexPageController {

    @GetMapping("/fast-api.html")
    public String indexPage() {
        // noinspection SpringMVCViewInspection
        return "/webjars/fast-api/index.html";
    }
}
