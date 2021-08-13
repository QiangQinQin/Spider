package com.tulun.controller;

import com.tulun.service.MailService;
import com.tulun.webmagic.SXSPipeline;
import com.tulun.webmagic.SXSProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

/**
 * @author QiangQin
 * * @date 2021/8/13
 */
@Controller
public class SpiderController {

    @Autowired
    private SXSProcessor sxsProcessor;
    @Autowired
    private SXSPipeline sxsPipeline;
    @Autowired
    private MailService mailService;
    /**
     * 通过URL触发爬虫爬取数据
     */
    @RequestMapping("/spider")
    @ResponseBody //以json的格式 返回不同类型的对象
    public  String spider(){

        System.out.println("爬虫开始爬取数据...");
        Spider.create(sxsProcessor)
                .addPipeline(sxsPipeline)// 添加pipeline
                .addUrl(SXSProcessor.getURL())
                .run();

        return "success";
    }

    @RequestMapping("/mail")
    @ResponseBody //以json的格式 返回不同类型的对象
    public  String mail(){
        mailService.sendMail();
        return "success";
    }
}