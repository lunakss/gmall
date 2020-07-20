package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.sms.entity.HomeSubjectSpuEntity;

import java.util.Map;

/**
 * 专题商品
 *
 * @author lunakss
 * @email 1245713204@qq.com
 * @date 2020-07-20 20:38:37
 */
public interface HomeSubjectSpuService extends IService<HomeSubjectSpuEntity> {

    PageResultVo queryPage(PageParamVo paramVo);
}

