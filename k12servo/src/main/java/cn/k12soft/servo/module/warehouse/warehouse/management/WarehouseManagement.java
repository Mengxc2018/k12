package cn.k12soft.servo.module.warehouse.warehouse.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.warehouse.utils.HttpUtils;
import cn.k12soft.servo.module.warehouse.warehouse.domain.Warehouse;
import cn.k12soft.servo.module.warehouse.warehouse.repositopry.WarehouseRepository;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/watehouse/management")
@Transactional
public class WarehouseManagement {

    private final WarehouseRepository wareHouseRepository;

    @Autowired
    public WarehouseManagement(WarehouseRepository wareHouseRepository) {
        this.wareHouseRepository = wareHouseRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(Warehouse.class);
    private static final String HOST = "http://302301.market.alicloudapi.com";
    private static final String PATH = "/barcode/barcode";
    private static final String APP_CODE = "54521d3124c04a649da08b4ff721019c";
    private static final String APP_KEY = "25370247";
    private static final String APP_SECRET = "6521076f739bfbf61179dac5ca0387b4";

    /**
     *
     * @param actor
     * @param name
     * @param isbn
     * @return
     */
    @ApiOperation("匹配库查询")
    @GetMapping("/find")
    public Warehouse find(@Active Actor actor,
                          @RequestParam(required = false) @Valid String name,
                          @RequestParam(required = false) @Valid String isbn){
        Warehouse warehouse = isbn == null
                ? wareHouseRepository.findByName(name)
                : wareHouseRepository.findByIsbn(isbn);
        if (warehouse == null && StringUtils.isBlank(name)){
            JSONObject onLine = findOnLine(isbn);
            log.info(onLine.toString());
            if (onLine.get("code") != null && !"null".equals(onLine.get("code"))){
                log.error(onLine.toString());
            }else{
                warehouse = new Warehouse(
                        onLine.get("name").toString(),
                        onLine.get("barcode").toString(),
                        onLine.get("spec").toString(),
                        Float.valueOf(onLine.get("price").toString()),
                        onLine.get("country").toString(),
                        onLine.get("company").toString(),
                        onLine.get("prefix").toString(),
                        onLine.get("addr").toString(),
                        actor.getSchoolId(),
                        Instant.now()
                );
                this.wareHouseRepository.save(warehouse);
            }
        }
        return warehouse;
    }

    @ApiOperation("查询库所有商品")
    @GetMapping("/findAll")
    public List<Warehouse> findAll(@Active Actor actor){
        return wareHouseRepository.findAllBySchoolId(actor.getSchoolId());
    }

    public boolean findByIsbn(String isbn){
        boolean is = true;
        JSONObject jsonObject = findOnLine(isbn);
        if(jsonObject.get("code") !=null &&  !"null".equals(jsonObject.get("code"))){
            is = false;
            log.error(jsonObject.toString());
        }
        return is;
    }

    @ApiOperation("查询阿里云isbn码")
    @GetMapping("/findOnLine")
    public JSONObject findOnLine(String isbn){
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + APP_CODE);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("barcode", isbn);
        JSONObject jsonObject = new JSONObject();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(HOST, PATH, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            String s = EntityUtils.toString(response.getEntity());
            jsonObject = JSONObject.fromObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
