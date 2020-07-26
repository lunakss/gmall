package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.SpuEntity;
import lombok.Data;

import java.util.List;

/**
 * @author lunakss
 * @create 2020-07-22 13:21
 */
@Data
public class SpuVo extends SpuEntity {
    // 图片信息
    private List<String> spuImages;

    // 基本属性信息
    private List<SpuAttrValueVo> baseAttrs;

    // sku信息
    private List<SkuVo> skus;

}
