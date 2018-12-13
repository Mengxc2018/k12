package cn.k12soft.servo.module.commmontest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

/**
 * 区域管理
 * 区域号生成规则
 */
@RestController
@RequestMapping("/mxc")
@Transactional
public class Commonmxc {

    /**
     * 根据localDate返回昨天的日历跟
     * @return
     */
    public static Map<String, Object> times(){
        Map<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        map.put("calendar", calendar);

        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(-1);
        map.put("localDate", localDate);
        return map;
    }

    // 生成四位随机数
    public static String ramdom4Num(){
        int  num = (int)(Math.random()*8999)+1000;
        BigDecimal bigInt = new BigDecimal(num);
        BigDecimal bigTwo = new BigDecimal(100);
        bigInt = bigInt.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP).setScale(2);
        return bigInt.toString();
    }

    /**
     * 生成32位MD5加密
     * @param psw
     * @return
     */
    public static String StringToMd5(String psw) {
        {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(psw.getBytes("UTF-8"));
                byte[] encryption = md5.digest();

                StringBuffer strBuf = new StringBuffer();
                for (int i = 0; i < encryption.length; i++) {
                    if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                        strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                    } else {
                        strBuf.append(Integer.toHexString(0xff & encryption[i]));
                    }
                }

                return strBuf.toString();
            } catch (NoSuchAlgorithmException e) {
                return "";
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }

}
