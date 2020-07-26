package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.gmall.pms.mapper.AttrGroupMapper;
import com.atguigu.gmall.pms.mapper.AttrMapper;
import com.atguigu.gmall.pms.service.AttrGroupService;
import com.atguigu.gmall.pms.vo.GroupVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrMapper attrMapper;

    @Override
    public PageResultVo queryPage(PageParamVo paramVo) {
        IPage<AttrGroupEntity> page = this.page(
                paramVo.getPage(),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageResultVo(page);
    }

    @Override
    public List<GroupVo> queryByCid(Long cid) {
        //查询所有分组
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("category_id", cid));
        //查询每组下的规格参数
        if(CollectionUtils.isEmpty(groupEntities)){
            return null;
        }
        List<GroupVo> groupVoList = groupEntities.stream().map(groupEntity -> {
            GroupVo groupVo = new GroupVo();
            BeanUtils.copyProperties(groupEntity, groupVo);
            //查询规格参数，只需查询出每个分组下的通用属性即可(不需要销售属性)
            List<AttrEntity> attrEntities = attrMapper.selectList(new QueryWrapper<AttrEntity>().eq("group_id", groupEntity.getId()).eq("type", 1));
            groupVo.setAttrEntities(attrEntities);
            return groupVo;
        }).collect(Collectors.toList());
        return groupVoList;
    }
}