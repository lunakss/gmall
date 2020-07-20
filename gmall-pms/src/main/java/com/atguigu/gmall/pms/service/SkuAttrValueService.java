package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.pms.entity.SkuAttrValueEntity;

import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author lunakss
 * @email 1245713204@qq.com
 * @date 2020-07-20 19:58:39
 */
public interface SkuAttrValueService extends IService<SkuAttrValueEntity> {

    PageResultVo queryPage(PageParamVo paramVo);
}

