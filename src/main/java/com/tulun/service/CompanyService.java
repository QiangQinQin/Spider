package com.tulun.service;

import com.tulun.dao.CompanyMapper;
import com.tulun.pojo.CompanyXZ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author QiangQin
 * * @date 2021/8/13
 */
@Service
public class CompanyService {
    @Autowired
    CompanyMapper companyMapper;

    public int addCompany(CompanyXZ companyXZ){
        return  companyMapper.insertCompany(companyXZ);
    }

    //获取所有的md5值
    public List<String> allMd5(){
        return  companyMapper.allMd5();
    }

//    获取新数据
    public List<CompanyXZ> selectNewCompanies(){
        return  companyMapper.selectAllNotSend();
    }


    // 提供 变更状态 的方法
    public int batchUpdateState(List<Integer> ids){
        return companyMapper.batchUpdateState(ids);
    }
}
