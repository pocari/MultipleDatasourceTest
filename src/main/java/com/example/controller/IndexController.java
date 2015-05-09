package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.IndexService;

@Controller
@Transactional(rollbackFor=Exception.class)
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping("/")
    @ResponseBody
    public String index() throws Exception {
       return indexService.doService();
    }
}
