package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.pms.entity.CommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author lunakss
 * @email 1245713204@qq.com
 * @date 2020-07-20 19:58:38
 */
public interface CommentService extends IService<CommentEntity> {

    PageResultVo queryPage(PageParamVo paramVo);
}

