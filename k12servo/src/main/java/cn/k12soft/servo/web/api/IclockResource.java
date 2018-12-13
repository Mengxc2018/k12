package cn.k12soft.servo.web.api;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import cn.k12soft.servo.domain.enumeration.IclockTable;
import cn.k12soft.servo.domain.iclock.ATTLog;
import cn.k12soft.servo.domain.iclock.IclockDevice;
import cn.k12soft.servo.repository.iclock.ATTLogRepository;
import cn.k12soft.servo.repository.iclock.IclockDeviceRepository;
import com.google.common.io.LineReader;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@Controller
@RequestMapping("/iclock")
public class IclockResource {

  public static final DateTimeFormatter DATE_TIME;

  static {
    DATE_TIME = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(ISO_LOCAL_DATE)
      .appendLiteral(' ')
      .append(ISO_LOCAL_TIME)
      .toFormatter();
  }

  public static final String PARAM_SN = "SN";
  public static final String PARAM_OPTIONS = "options";
  public static final String PARAM_PUSHVER = "pushver";
  public static final String PARAM_LANGUAGE = "language";
  public static final String PARAM_PUSHCOMMONKEY = "pushcommonkey";

  public static final String PARAM_GET_OPTION_FROM = "GET OPTION FROM";
  public static final String PARAM_STAMP = "Stamp";
  public static final String PARAM_OPSTAMP = "OpStamp";
  public static final String PARAM_PHOTOSTAMP = "PhotoStamp";
  public static final String PARAM_ERRORDELAY = "ErrorDelay";
  public static final String PARAM_DELAY = "Delay";
  public static final String PARAM_TRANSTIMES = "TransTimes";
  public static final String PARAM_TRANSINTERVAL = "TransInterval";
  public static final String PARAM_TRANSFLAG = "TransFlag";
  public static final String PARAM_REALTIME = "Realtime";
  public static final String PARAM_ENCRYPT = "Encrypt";
  public static final String PARAM_SERVERVER = "ServerVer";
  public static final String PARAM_TABLENAMESTAMP = "TableNameStamp";

  private static final String GET_DATA_RESPONSE_COMMON
    = PARAM_ERRORDELAY + "=30\r\n"
    + PARAM_DELAY + "=60\r\n"
    + PARAM_TRANSFLAG + "=1000000000\r\n" //only check in
    + PARAM_TRANSTIMES + "=00:00;14:05\r\n"
    + PARAM_TRANSINTERVAL + "=1\r\n"
    + PARAM_REALTIME + "=1\r\n"
    + PARAM_ENCRYPT + "=0\r\n";

  public static final ResponseEntity<String> OK
    = ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("OK");

  private final IclockDeviceRepository deviceRepository;
  private final ATTLogRepository attLogRepository;

  @Autowired
  public IclockResource(IclockDeviceRepository deviceRepository,
                        ATTLogRepository attLogRepository) {
    this.deviceRepository = deviceRepository;
    this.attLogRepository = attLogRepository;
  }

  @ApiOperation("考勤机初始请求")
  @GetMapping(value = "/cdata")
  public ResponseEntity<String> getData(@RequestParam Map<String, String> params) {
    String sn = params.get("SN");
    String options = params.get("options");
    Integer languageKey = Integer.parseInt(params.getOrDefault("language", "83"));
    Optional<String> pushCommonKey = Optional.ofNullable(params.get("pushcommonkey"));
    long stamp = Instant.now().toEpochMilli() / 1000;
    String body = PARAM_GET_OPTION_FROM + ":" + sn + "\r\n"
      + GET_DATA_RESPONSE_COMMON
      + PARAM_STAMP + "=" + stamp + "\r\n"
      + PARAM_OPSTAMP + "=" + stamp + "\r\n"
      + "ATTLOGStamp=" + stamp + "\r\n";
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(body);
  }

  @ApiOperation("考勤机上传数据接口")
  @PostMapping(value = "/cdata", params = {"SN", "table", "Stamp"})
  @Transactional
  public ResponseEntity<String> pushData(@RequestParam("SN") String sn,
                                         @RequestParam("table") IclockTable table,
                                         @RequestParam("Stamp") Integer stamp,
                                         @RequestBody String content) throws IOException {
    IclockDevice device = deviceRepository.findOne(sn);
    if (table == IclockTable.ATTLOG) {
      LineReader lineReader = new LineReader(new StringReader(content));
      String record;
      List<ATTLog> attLogs = new ArrayList<>();
      while ((record = lineReader.readLine()) != null) {
        if (!(record = record.trim()).equals("")) {
          String[] fields = record.split("\t");
          ATTLog attLog = new ATTLog(
            device,
            Integer.parseInt(fields[0]),
            DATE_TIME.parse(fields[1], LocalDateTime::from).toInstant(ZoneOffset.ofHours(8)),
            Integer.parseInt(fields[2]),
            Integer.parseInt(fields[3]));
          attLogs.add(attLog);
        }
      }
      attLogRepository.save(attLogs);
    }
    return OK;
  }

  @ApiOperation("请求服务器命令")
  @GetMapping(value = "/getrequest")
  public ResponseEntity<String> getRequest(@RequestParam Map<String, String> params) {
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).build();
  }
}
