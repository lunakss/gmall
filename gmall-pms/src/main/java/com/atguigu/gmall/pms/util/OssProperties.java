package com.atguigu.gmall.pms.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lunakss
 * @create 2020-07-21 13:14
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
}
