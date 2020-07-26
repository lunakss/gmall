package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.pms.entity.SpuEntity;
import com.atguigu.gmall.pms.vo.SpuVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * spu信息
 *
 * @author lunakss
 * @email 1245713204@qq.com
 * @date 2020-07-20 19:58:39
 */
public interface SpuService extends IService<SpuEntity> {

    PageResultVo queryPage(PageParamVo paramVo);

    PageResultVo querySpuInfo(PageParamVo pageParamVo, Long categoryId);

    void bigSave(SpuVo spuVo);

    void saveSku(SpuVo spuVo,Long spuId);

    //void saveBaseAttr(SpuVo spuVo,Long spuId);

    //Long saveSpu(SpuVo spuVo);
}

