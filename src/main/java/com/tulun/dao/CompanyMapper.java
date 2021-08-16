package com.tulun.dao;

import com.tulun.pojo.CompanyXZ;

import java.util.List;

/**
 * @author QiangQin
 * * @date 2021/8/13
 */
public interface CompanyMapper {
    //插入数据
    int insertCompany(CompanyXZ  companyXZ);

    //获取已存储数据的MD5值
    List<String> allMd5();

    //获取所有未发送的校招信息
    List<CompanyXZ>  selectAllNotSend();

    //批量变更公司state状态
    int batchUpdateState(List<Integer> ids);

}
