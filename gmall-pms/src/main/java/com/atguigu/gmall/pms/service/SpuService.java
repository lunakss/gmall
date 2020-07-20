package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.pms.entity.SpuEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author lunakss
 * @email 1245713204@qq.com
 * @date 2020-07-20 19:58:39
 */
public interface SpuService extends IService<SpuEntity> {

    PageResultVo queryPage(PageParamVo paramVo);
}

