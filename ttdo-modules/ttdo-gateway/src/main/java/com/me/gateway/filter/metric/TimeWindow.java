package com.me.gateway.filter.metric;

import java.util.concurrent.atomic.AtomicInteger;

public class TimeWindow {

    private Long start;

    private Long end;

    private AtomicInteger requestTimes = new AtomicInteger(0);

    private AtomicInteger failedRequestTimes = new AtomicInteger(0);

    public TimeWindow(int timeWindowSize) {
        this.start = System.currentTimeMillis();
        this.end = this.start + 1000 * timeWindowSize;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > end;
    }

    public int incrRequestTimes(){
        return requestTimes.incrementAndGet();
    }

    public int incrFailedRequestTimes(){
        return failedRequestTimes.incrementAndGet();
    }

    public int getRequestTimes() {
        return requestTimes.get();
    }

    public int getFailedRequestTimes() {
        return failedRequestTimes.get();
    }
}
