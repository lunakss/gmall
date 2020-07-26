package com.atguigu.gmall.wms.api;

import com.atguigu.gmall.common.bean.ResponseVo;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lunakss
 * @create 2020-07-26 17:31
 */
public interface GmallWmsApi {
    /**
     * 根据sku_id查询sku库存
     */
    @ApiOperation("查询库存")
    @GetMapping("wms/waresku/sku/{skuId}")
    ResponseVo<List<WareSkuEntity>> queryWareSkuBySkuId(@PathVariable Long skuId);
}
