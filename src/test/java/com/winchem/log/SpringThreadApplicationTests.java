package com.winchem.log;

import com.alibaba.fastjson.JSONObject;
import com.winchem.log.device.service.DemoService;
import com.winchem.log.device.vo.DeviceLastestInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringThreadApplicationTests {

    @Autowired
    private DemoService demoService;

    @Test
    public void testZhaoDaoNvPengYou() throws InterruptedException {
        demoService.zhaoDaoNvPengYou(4, 2);
        demoService.zhaoDaoNvPengYou(1, 0);

        // sleep 1 秒，保证异步调用的执行
        Thread.sleep(1000);
    }

    @Autowired
    private RestTemplate restTemplate;
    @Test
    public void getLastest() {
//         String url = "http://api.heclouds.com/devices/647109727/datastreams?datastream_ids=39,44";
//         String rlt = restTemplate.getForObject(url, String.class);

        String url = "http://api.heclouds.com/devices/datapoints?devIds={ids}";
        String rlt = restTemplate.getForObject(url, String.class, new HashMap<String, String>(){{put("ids", "647109727");}});
        DeviceLastestInfoVo infoVo = JSONObject.parseObject(rlt, DeviceLastestInfoVo.class);
        log.info(rlt);
    }

    @Test
    public void getLastest2() {
        String url = "http://api.heclouds.com/devices/647109727/datastreams?datastream_ids=39,38";
        String apiKey = "7qzZcGVQlyGW626RXtLwPhE7RwQ=";

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(result.getBody());
    }

}
