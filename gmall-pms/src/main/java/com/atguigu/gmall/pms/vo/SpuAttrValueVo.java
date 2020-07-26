package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.SpuAttrValueEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;


import java.util.List;

/**
 * @author lunakss
 * @create 2020-07-22 13:45
 */
public class SpuAttrValueVo extends SpuAttrValueEntity {
    public void setValueSelected(List<Object> valueSelected){
        if(CollectionUtils.isEmpty(valueSelected)){
            return;
        }
        setAttrValue(StringUtils.join(valueSelected,","));
    }
}
