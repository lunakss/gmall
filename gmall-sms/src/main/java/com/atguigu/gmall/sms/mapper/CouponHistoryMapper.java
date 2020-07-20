package com.atguigu.gmall.sms.mapper;

import com.atguigu.gmall.sms.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author lunakss
 * @email 1245713204@qq.com
 * @date 2020-07-20 20:38:37
 */
@Mapper
public interface CouponHistoryMapper extends BaseMapper<CouponHistoryEntity> {
	
}
