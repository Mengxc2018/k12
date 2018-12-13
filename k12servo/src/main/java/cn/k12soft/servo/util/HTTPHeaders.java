package cn.k12soft.servo.util;

import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public final class HTTPHeaders {

  private static final Logger log = LoggerFactory.getLogger(HTTPHeaders.class);
  public static final String BEARER_PREFIX = "Bearer ";

  private HTTPHeaders() {
  }

  public static HttpHeaders createNotice(String message, @Nonnull Object param) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-K12-Notice", message);
    headers.add("X-K12-Params", param.toString());
    return headers;
  }

  public static HttpHeaders createEntityCreatedNotice(Class<?> entityType, Object param) {
    return createEntityCreatedNotice(entityType.getSimpleName(), param);
  }

  public static HttpHeaders createEntityCreatedNotice(String entityName, Object param) {
    return createNotice("K12." + entityName + ".created", param);
  }

  public static HttpHeaders createEntityUpdatedNotice(Class<?> entityType, Object param) {
    return createEntityUpdatedNotice(entityType.getSimpleName(), param);
  }

  public static HttpHeaders createEntityUpdatedNotice(String entityName, Object param) {
    return createNotice("K12." + entityName + ".updated", param);
  }

  public static HttpHeaders createEntityDeletedNotice(String entityName, Object param) {
    return createNotice("K12." + entityName + ".deleted", param);
  }

  public static HttpHeaders createFailure(Class<?> entityName, String errorKey,
                                          String defaultMessage) {
    return createFailure(entityName.getSimpleName(), errorKey, defaultMessage);
  }

  public static HttpHeaders createFailure(String entityName, String errorKey,
                                          String defaultMessage) {
    log.error("Entity operation failed, {}", defaultMessage);
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-K12-Error", "error." + errorKey);
    headers.add("X-K12-Params", entityName);
    return headers;
  }
}
