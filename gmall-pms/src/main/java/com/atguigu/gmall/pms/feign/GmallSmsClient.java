package com.atguigu.gmall.pms.feign;

import com.atguigu.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author lunakss
 * @create 2020-07-23 0:33
 */
@FeignClient(value = "sms-service")
public interface GmallSmsClient extends GmallSmsApi {
}
