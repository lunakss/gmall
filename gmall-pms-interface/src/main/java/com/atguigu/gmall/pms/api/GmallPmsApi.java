package com.atguigu.gmall.pms.api;

import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.common.bean.ResponseVo;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuEntity;
import com.atguigu.gmall.pms.entity.SpuEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author lunakss
 * @create 2020-07-26 17:06
 */
public interface GmallPmsApi {
    @PostMapping("pms/spu/json")
    @ApiOperation("分页查询")
    ResponseVo<List<SpuEntity>> querySpuByPageJson(@RequestBody PageParamVo paramVo);

    /**
     * 根据spu_id查询sku信息
     */
    @GetMapping("pms/sku/spu/{spuId}")
    ResponseVo<List<SkuEntity>> querySkuBySpuId(@PathVariable Long spuId);

    /**
     * 品牌信息
     */
    @GetMapping("pms/brand/{id}")
    @ApiOperation("详情查询")
    ResponseVo<BrandEntity> queryBrandById(@PathVariable("id") Long id);

    /**
     * 分类信息
     */
    @GetMapping("pms/category{id}")
    @ApiOperation("详情查询")
    ResponseVo<CategoryEntity> queryCategoryById(@PathVariable("id") Long id);
}
