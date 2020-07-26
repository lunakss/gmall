package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

/**
 * @author lunakss
 * @create 2020-07-22 11:33
 */
@Data
public class GroupVo extends AttrGroupEntity {
    private List<AttrEntity> attrEntities;
}
