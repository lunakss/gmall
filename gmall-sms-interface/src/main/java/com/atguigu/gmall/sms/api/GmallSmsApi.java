package com.atguigu.gmall.sms.api;

import com.atguigu.gmall.common.bean.ResponseVo;
import com.atguigu.gmall.sms.vo.SkuSalesVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lunakss
 * @create 2020-07-23 1:55
 */
public interface GmallSmsApi {
    @PostMapping("sms/skubounds/sku/sales")
    ResponseVo<Object> saveSkuSales(@RequestBody SkuSalesVo skuSalesVo);
}
