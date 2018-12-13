package cn.k12soft.servo.configuration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liubing on 2018/7/3
 */
@Component
public class ChinaAreaConfigManager {
    private Map<String, String> name2CodeMap = new HashMap<>();
    @PostConstruct
    public void init() throws Exception {
        load();
    }

    private void load() throws Exception {
        File file = ResourceUtils.getFile("classpath:china-area.bin");
        List<String> contentList = FileUtils.readLines(file, "UTF-8");
        Map<String, String> tmpMap = new HashMap<>();
        for (String content : contentList) {
            String[] conArr = StringUtils.splitByWholeSeparator(content, "\t");//0=地区码	1=区域名称	2=上级区域编码	3=区域类型（1国家2省3市4区5街道）
            tmpMap.put(conArr[1], conArr[0]);
        }
    }

    public String getCode(String name){
        return this.name2CodeMap.get(name);
    }

}
