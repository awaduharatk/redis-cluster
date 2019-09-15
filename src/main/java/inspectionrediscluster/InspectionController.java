package inspectionrediscluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inspection")
public class InspectionController {

    String value = "testValue";

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String get() {

        return "hello world";

    }

    @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
    public String get(@PathVariable("key") String key) {

        String result = "";
        result = (String) redisTemplate.opsForValue().get(key);

        return result;
    }

    @RequestMapping(value = "/set/{key}", method = RequestMethod.POST)
    public String set(@PathVariable("key") String key) {

        redisTemplate.opsForValue().set(key,value);
        return "ok";
    }

}


