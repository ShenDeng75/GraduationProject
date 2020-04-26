package shendeng.graduation.show;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Controller
public class ShowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowApplication.class, args);
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/getURL")
    @ResponseBody
    public JSONObject getURL(@RequestParam("keys") String keys){
        Map<String, List<String>> result = new HashMap<>();
        List<String> urls = JDBCHelper.search(keys);
        result.put("result", urls);
        return new JSONObject().fluentPutAll(result);
    }
}
