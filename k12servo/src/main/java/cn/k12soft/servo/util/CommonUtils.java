package cn.k12soft.servo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

public class CommonUtils {

  private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

  public static final List<Integer> randomList = new ArrayList<>();

  static {
    randomList.add(0);
    randomList.add(1);
    randomList.add(2);
    randomList.add(3);
    randomList.add(4);
    randomList.add(5);
    randomList.add(6);
    randomList.add(7);
    randomList.add(8);
    randomList.add(9);
  }

  public static int randomGetFromZero2Ten() {
    return randomGetFromList(randomList);
  }

  public static <T> T randomGetFromList(List<T> list) {
    int size = list.size();
    if (size == 0) {
      return null;
    }
    Random random = new Random();
    int randomIndex = random.nextInt(size);
    return list.get(randomIndex);
  }

  /*生成当前UTC时间戳Time*/
  public static String generateTimestamp() {
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    df.setTimeZone(new SimpleTimeZone(0, "GMT"));
    return df.format(date);
  }

  public static String generateRandom() {
    String signatureNonce = UUID.randomUUID().toString();
    return signatureNonce;
  }

  /*对所有参数名称和参数值做URL编码*/
  public static List<String> getAllParams(Map<String, String> publicParams, Map<String, String> privateParams) {
    List<String> encodeParams = new ArrayList<String>();
    if (publicParams != null) {
      for (String key : publicParams.keySet()) {
        String value = publicParams.get(key);
        //将参数和值都urlEncode一下。
        String encodeKey = percentEncode(key);
        String encodeVal = percentEncode(value);
        encodeParams.add(encodeKey + "=" + encodeVal);
      }
    }
    if (privateParams != null) {
      for (String key : privateParams.keySet()) {
        String value = privateParams.get(key);
        //将参数和值都urlEncode一下。
        String encodeKey = percentEncode(key);
        String encodeVal = percentEncode(value);
        encodeParams.add(encodeKey + "=" + encodeVal);
      }
    }
    return encodeParams;
  }

  /*特殊字符替换为转义字符*/
  public static String percentEncode(String value) {
    try {
      String urlEncodeOrignStr = URLEncoder.encode(value, "UTF-8");
      String plusReplaced = urlEncodeOrignStr.replace("+", "%20");
      String starReplaced = plusReplaced.replace("*", "%2A");
      String waveReplaced = starReplaced.replace("%7E", "~");
      return waveReplaced;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * @param domain 请求地址
   * @param httpMethod HTTP请求方式GET，POST等
   * @param publicParams 公共参数
   * @param privateParams 接口的私有参数
   * @return 最后的url
   */
  public static String generateURL(String domain, String httpMethod, String securityToken, Map<String, String> publicParams,
                                   Map<String, String> privateParams) {
    List<String> allEncodeParams = getAllParams(publicParams, privateParams);
    String cqsString = getCQS(allEncodeParams);
    String stringToSign = httpMethod + "&" + percentEncode("/") + "&" + percentEncode(cqsString);
    String signature = hmacSHA1Signature(securityToken, stringToSign);
    return domain + "?" + cqsString + "&" + percentEncode("Signature") + "=" + percentEncode(signature);
  }

  /*获取 CanonicalizedQueryString*/
  public static String getCQS(List<String> allParams) {
    ParamsComparator paramsComparator = new ParamsComparator();
    Collections.sort(allParams, paramsComparator);
    String cqString = "";
    for (int i = 0; i < allParams.size(); i++) {
      cqString += allParams.get(i);
      if (i != allParams.size() - 1) {
        cqString += "&";
      }
    }
    return cqString;
  }

  private static String hmacSHA1Signature(String accessKeySecret, String stringtoSign) {
    try {
      String key = accessKeySecret + "&";
      try {
        SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signKey);
        byte[] rawHmac = mac.doFinal(stringtoSign.getBytes());
        //按照Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
        return new String(new BASE64Encoder().encode(rawHmac));
      } catch (Exception e) {
        throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
      }
    } catch (SignatureException e) {
      e.printStackTrace();
    }
    return "";
  }

  /*字符串参数比较器*/
  public static class ParamsComparator implements Comparator<String> {

    @Override
    public int compare(String lhs, String rhs) {
      return lhs.compareTo(rhs);
    }

  }

  public static <T> Set<T> convertContentToSet(Class<T> clazz, String content) {
    Set<T> set = new HashSet();
    if (StringUtils.isNotBlank(content)) {
      String[] var6;
      int var5 = (var6 = StringUtils.splitByWholeSeparator(String.valueOf(content), ",")).length;

      for(int var4 = 0; var4 < var5; ++var4) {
        String str = var6[var4];
        set.add(convert(clazz, str));
      }
    }

    return set;
  }

  public static <E> String convertSetToContent(Set<E> set) {
    String seperator = "";
    StringBuilder strBuilder = new StringBuilder();

    for(Iterator var4 = set.iterator(); var4.hasNext(); seperator = ",") {
      E entry = (E) var4.next();
      strBuilder.append(seperator).append(entry);
    }

    return strBuilder.toString();
  }

  public static <T> T convert(Class<T> clazz, String str) {
    if (clazz.isAssignableFrom(Integer.class)) {
      return clazz.cast(Integer.parseInt(str));
    } else if (clazz.isAssignableFrom(Short.class)) {
      return clazz.cast(Short.parseShort(str));
    } else if (clazz.isAssignableFrom(Byte.class)) {
      return clazz.cast(Byte.parseByte(str));
    } else if (clazz.isAssignableFrom(String.class)) {
      return clazz.cast(str);
    } else if (clazz.isAssignableFrom(Long.class)) {
      return clazz.cast(Long.parseLong(str));
    } else if (clazz.isAssignableFrom(Boolean.class)) {
      return clazz.cast(Boolean.parseBoolean(str));
    } else {
      throw new RuntimeException("convert error");
    }
  }

  public static <K, V> String map2String(Map<K, V> map) {
    JSONObject jSObject = new JSONObject();
    jSObject.putAll(map);
    return jSObject.toString();
  }

}
