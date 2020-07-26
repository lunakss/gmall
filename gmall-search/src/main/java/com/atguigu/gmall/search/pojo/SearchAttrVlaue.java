package com.atguigu.gmall.search.pojo;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author lunakss
 * @create 2020-07-26 15:07
 */
public class SearchAttrVlaue {
    @Field(type = FieldType.Long)
    private Long attrId;
    @Field(type = FieldType.Keyword)
    private String attrName;
    @Field(type = FieldType.Keyword)
    private String attrValue;
}
