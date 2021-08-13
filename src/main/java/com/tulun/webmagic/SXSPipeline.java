package com.tulun.webmagic;

import com.tulun.pojo.CompanyXZ;
import com.tulun.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author QiangQin
 * * @date 2021/8/13
 */
//webmagic 持久化数据  需要实现pipeline接口
@Component  // 交给spring进行管理
public class SXSPipeline implements Pipeline {
    @Autowired
    CompanyService companyService;  // 注入service层

    @Override
    public void process(ResultItems resultItems, Task task) {
        Set<Map.Entry <String, Object>> entries = resultItems.getAll().entrySet(); //getAll()拿到  所有公司数据（以键值对形式存储）

        for (Map.Entry <String, Object> entry : entries) {
            if (entry.getKey().equals("companies")) { //companies 对应 所有公司数据
                List<CompanyXZ> companyXZS = (List <CompanyXZ>) entry.getValue();
                //将结果存储到数据库
                for (CompanyXZ c:companyXZS) {
                    companyService.addCompany(c);
                }
            }
        }
    }
}
