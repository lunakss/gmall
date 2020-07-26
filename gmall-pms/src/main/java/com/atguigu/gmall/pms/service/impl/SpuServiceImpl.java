package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.pms.entity.SkuAttrValueEntity;
import com.atguigu.gmall.pms.entity.SkuImagesEntity;
import com.atguigu.gmall.pms.entity.SpuAttrValueEntity;
import com.atguigu.gmall.pms.entity.SpuEntity;
import com.atguigu.gmall.pms.feign.GmallSmsClient;
import com.atguigu.gmall.pms.mapper.SkuMapper;
import com.atguigu.gmall.pms.mapper.SpuMapper;
import com.atguigu.gmall.pms.service.*;
import com.atguigu.gmall.pms.vo.SkuVo;
import com.atguigu.gmall.pms.vo.SpuAttrValueVo;
import com.atguigu.gmall.pms.vo.SpuVo;
import com.atguigu.gmall.sms.vo.SkuSalesVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service("spuService")
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity> implements SpuService {

    @Autowired
    SpuDescService spuDescService;

    @Autowired
    SpuAttrValueService spuAttrValueService;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SkuAttrValueService skuAttrValueService;

    @Autowired
    GmallSmsClient gmallSmsClient;

    @Override
    public PageResultVo queryPage(PageParamVo paramVo) {
        IPage<SpuEntity> page = this.page(
                paramVo.getPage(),
                new QueryWrapper<SpuEntity>()
        );

        return new PageResultVo(page);
    }

    @Override
    public PageResultVo querySpuInfo(PageParamVo pageParamVo, Long categoryId) {
        IPage<SpuEntity> page = pageParamVo.getPage();
        QueryWrapper<SpuEntity> wrapper = new QueryWrapper<>();
        if(categoryId != 0){
            wrapper.eq("category_id",categoryId);
        }
        if(!StringUtils.isEmpty(pageParamVo.getKey())){
            wrapper.and(t->t.like("id",pageParamVo.getKey()).or().like("name",pageParamVo.getKey()));
        }
        IPage<SpuEntity> iPage = this.page(page, wrapper);
        return new PageResultVo(iPage);
    }

    //@Transactional(readOnly = true)
    //@Transactional(timeout = 3)超时时间（timeout）是指数据库超时,不是业务超时
    //@Transactional(rollbackFor = FileNotFoundException.class,noRollbackFor = ArithmeticException.class)
    @GlobalTransactional
    @Override
    public void bigSave(SpuVo spuVo) {
        //1.保存spu相关信息
        Long spuId = saveSpu(spuVo);

        //2.保存spu描述信息
        this.spuDescService.saveSpuDesc(spuVo,spuId);

//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //3.保存spu的基本属性
        saveBaseAttr(spuVo,spuId);

        //4.保存sku相关信息
        saveSku(spuVo,spuId);
//        int i = 12/0;
    }

    @Transactional
    @Override
    public void saveSku(SpuVo spuVo, Long spuId) {
        List<SkuVo> skus = spuVo.getSkus();
        if(CollectionUtils.isEmpty(skus)){
            return;
        }

        skus.forEach(skuVo->{
            //4.1保存sku表
            skuVo.setSpuId(spuId);
            skuVo.setBrandId(spuVo.getBrandId());
            skuVo.setCatagoryId(spuVo.getCategoryId());
            List<String> images = skuVo.getImages();
            //如果页面没有传递默认图片取第一张图片作为默认图片
            if(!CollectionUtils.isEmpty(images)){
                skuVo.setDefaultImage(StringUtils.isNotBlank(skuVo.getDefaultImage()) ? skuVo.getDefaultImage() : images.get(0));
            }
            this.skuMapper.insert(skuVo);
            Long skuId = skuVo.getId();

            //4.2保存sku图片表
            if(!CollectionUtils.isEmpty(images)){
                List<SkuImagesEntity> skuImagesEntities = images.stream().map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setUrl(image);
                    //设置图片的默认状态，通过图片的地址是否为默认图片地址即可
                    skuImagesEntity.setDefaultStatus(StringUtils.equals(image, skuVo.getDefaultImage()) ? 1 : 0);
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                this.skuImagesService.saveBatch(skuImagesEntities);
            }

            //4.3保存销售属性
            List<SkuAttrValueEntity> saleAttrs = skuVo.getSaleAttrs();
            if(!CollectionUtils.isEmpty(saleAttrs)){
                saleAttrs.forEach(saleAttr-> saleAttr.setSkuId(skuId));
                this.skuAttrValueService.saveBatch(saleAttrs);
            }

            //5.营销sku相关信息
            //5.1保存积分信息

            //5.2保存满减信息

            //5.3保存打折信息
            SkuSalesVo skuSalesVo = new SkuSalesVo();
            BeanUtils.copyProperties(skuVo,skuSalesVo);
            skuSalesVo.setSkuId(skuId);
            gmallSmsClient.saveSkuSales(skuSalesVo);
        });
    }


    public void saveBaseAttr(SpuVo spuVo, Long spuId) {
        List<SpuAttrValueVo> baseAttrs = spuVo.getBaseAttrs();
        if(!CollectionUtils.isEmpty(baseAttrs)){
            List<SpuAttrValueEntity> entities = baseAttrs.stream().map(spuAttrValueVo -> {
                SpuAttrValueEntity spuAttrValueEntity = new SpuAttrValueEntity();
                BeanUtils.copyProperties(spuAttrValueVo, spuAttrValueEntity);
                spuAttrValueEntity.setSpuId(spuId);
                return spuAttrValueEntity;
            }).collect(Collectors.toList());
            spuAttrValueService.saveBatch(entities);
        }
    }


    public Long saveSpu(SpuVo spuVo) {
        spuVo.setCreateTime(new Date());
        spuVo.setUpdateTime(spuVo.getCreateTime());
        this.save(spuVo);
        Long spuId = spuVo.getId();
        return spuId;
    }
}