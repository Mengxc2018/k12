package cn.k12soft.servo.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liubing on 2018/9/6
 */
public class ConcurrentLock {

    private ConcurrentLock() {
        this.lock = new ConcurrentHashMap<>();
    }

    private static class LazyHolder {
        final private static ConcurrentLock INSTANCE = new ConcurrentLock();
    }

    public static ConcurrentLock getInstance(){
        return LazyHolder.INSTANCE;
    }

    public final static String WX_PAY_ORDER = "WX_PAY_ORDER_";

    private ConcurrentHashMap<String, Object> lock = new ConcurrentHashMap<>();

    public Object getLock(String key, String subKey) {
        String lockKey = key+subKey;
        lock.putIfAbsent(lockKey,new Object());
        return lock.get(lockKey);
    }
}
