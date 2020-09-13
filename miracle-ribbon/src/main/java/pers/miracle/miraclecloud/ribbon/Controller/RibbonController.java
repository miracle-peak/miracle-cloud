package pers.miracle.miraclecloud.ribbon.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pers.miracle.miraclecloud.common.utils.R;

/**
 * @author: 蔡奇峰
 * @date: 2020/9/12 下午5:20
 **/
@RestController
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.system-service}")
    private String systemServiceUrl;

    @GetMapping("/system/{module}/{url}")
    public R getMethodSystem(@PathVariable("module") String module, @PathVariable("url") String url) {

        url = module + "/" + url;

        return restTemplate.getForObject(systemServiceUrl + "/system/" + url, R.class);
    }

}
