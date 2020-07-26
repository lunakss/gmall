package com.atguigu.gmall.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * @author lunakss
 * @create 2020-07-26 14:35
 */
@Data
@Document(indexName = "goods",type = "info",shards = 3,replicas = 2)
public class Goods {
//    商品详情列表中需要的字段
    @Id
    private Long skuId;
    @Field(type = FieldType.Keyword,index = false)
    private String image;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Keyword,index = false)
    private String subTitle;

//    排序过滤字段
    @Field(type = FieldType.Integer)
    private Integer sales;//销量
    @Field(type = FieldType.Date)
    private Date createTime;//新品
    @Field(type = FieldType.Boolean)
    private Boolean store;//是否有货

//    品牌
    @Field(type = FieldType.Long)
    private Long brandId;
    @Field(type = FieldType.Keyword)
    private String brandName;
    @Field(type = FieldType.Keyword)
    private String logo;

//    分类
    @Field(type = FieldType.Long)
    private Long categoryId;
    @Field(type = FieldType.Keyword)
    private String categoryName;

    @Field(type = FieldType.Nested)
    private List<SearchAttrVlaue> searchAttrs;
}
