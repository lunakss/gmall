package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.sms.entity.SkuBoundsEntity;
import com.atguigu.gmall.sms.vo.SkuSalesVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品spu积分设置
 *
 * @author lunakss
 * @email 1245713204@qq.com
 * @date 2020-07-20 20:38:37
 */
public interface SkuBoundsService extends IService<SkuBoundsEntity> {

    PageResultVo queryPage(PageParamVo paramVo);

    void saveSkuSales(SkuSalesVo skuSalesVo);
}

