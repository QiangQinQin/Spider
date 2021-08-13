package com.tulun.webmagic;

/**
 * @author QiangQin
 * * @date 2021/8/11
 */

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Iterator;
import java.util.List;

/**
 * 实现PageProcess的定制化开发
 */
public class SpiderDemo implements PageProcessor {
    //配置抓取网站的相关配置，编码、抓取间隔、重试次数 （模拟人的特点）
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    @Override
    public void process(Page page) { // Page表示抓取回来的页面
        //完成页面解析
        List <String> all = page.getHtml().xpath("//div[@class=lineHeight18]/a/text()").all();
        Iterator <String> iterator = all.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {  // 运行这个！！！
        //传入页面解析类
        Spider.create(new SpiderDemo())
                .addUrl("https://resume.shixiseng.com/") //爬取的种子URL
                .run();//爬虫启动
    }
}