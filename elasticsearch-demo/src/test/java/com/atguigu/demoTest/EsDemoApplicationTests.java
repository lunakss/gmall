package com.atguigu.demoTest;


import com.atguigu.demo.ElasticsearchDemoApplication;
import com.atguigu.demo.pojo.User;
import com.atguigu.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author lunakss
 * @create 2020-07-25 13:59
 */
@SpringBootTest(classes = ElasticsearchDemoApplication.class)
public class EsDemoApplicationTests {
    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads(){
        restTemplate.createIndex(User.class);
        restTemplate.putMapping(User.class);
    }

    @Test
    void testSave(){
//        User user = new User((long) 1, "yuyu", 22, "333");
//        userRepository.save(user);

        List<User> users = new ArrayList<>();
        users.add(new User(1l, "柳岩", 18, "123456"));
        users.add(new User(2l, "范冰冰", 19, "123456"));
        users.add(new User(3l, "李冰冰", 20, "123456"));
        users.add(new User(4l, "锋哥", 21, "654321"));
        users.add(new User(5l, "小鹿", 22, "654321"));
        users.add(new User(6l, "韩红", 23, "654321"));
        this.userRepository.saveAll(users);
    }

    @Test
    void testDelete(){
        userRepository.deleteById(1l);
    }

    @Test
    void testFind(){
//        List<User> lists = userRepository.findByAgeBetween(19, 22);
//        lists.forEach(System.out::println);

        userRepository.fingUserByAge(19,22).forEach(System.out::println);
    }

    @Test
    void testSearch(){
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "冰冰");
        Iterable<User> users = userRepository.search(queryBuilder);
        users.forEach(System.out::println);
    }

    @Test
    void testNativeSearch(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.rangeQuery("age").gte(19).lte(22));
        queryBuilder.withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC));
        queryBuilder.withPageable(PageRequest.of(0,4));
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","name"},null));
        queryBuilder.addAggregation(AggregationBuilders.terms("passwordAgg").field("password").subAggregation(AggregationBuilders.avg("aggAge").field("age")));//聚合+嵌套子聚合
        AggregatedPage<User> search = (AggregatedPage<User>)userRepository.search(queryBuilder.build());
        search.forEach(System.out::println);

        System.out.println(search.getTotalPages());
        System.out.println(search.getTotalElements());
        System.out.println(search.getContent());

        System.out.println("====================================");
        ParsedStringTerms passwordAgg = (ParsedStringTerms) search.getAggregation("passwordAgg");
        List<? extends Terms.Bucket> buckets = passwordAgg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            Map<String, Aggregation> map = bucket.getAggregations().asMap();
            ParsedAvg aggAge = (ParsedAvg) map.get("aggAge");
            System.out.println(aggAge.getValue());
        }
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void testHighLight() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.matchQuery("name", "冰冰").operator(Operator.AND));
        searchSourceBuilder.sort("age",SortOrder.DESC);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        searchSourceBuilder.highlighter(highlightBuilder.field("name").preTags("<em>").postTags("</em>"));
        searchSourceBuilder.fetchSource(new String[]{"id","name"},null);//排除字段
        searchSourceBuilder.aggregation(AggregationBuilders.terms("passwordAgg").field("password"));//聚合
        System.out.println(query);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search);

        SearchHits hits = search.getHits();
        System.out.println("命中了" + hits.getTotalHits() + "条");
        SearchHit[] hitsHits = hits.getHits();
        List<User> list = Stream.of(hitsHits).map(hit -> {
            User user = null;
            try {
                String sourceAsString = hit.getSourceAsString();
                user = MAPPER.readValue(sourceAsString, User.class);
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField name = highlightFields.get("name");
                Text[] fragments = name.getFragments();
                user.setName(fragments[0].string());
                return user;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        System.out.println(list);


        Map<String, Aggregation> aggregationMap = search.getAggregations().asMap();
        ParsedStringTerms passwordAgg = (ParsedStringTerms)aggregationMap.get("passwordAgg");
        List<? extends Terms.Bucket> buckets = passwordAgg.getBuckets();
        buckets.forEach(bucket->{
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        });
    }
}
