package com.sauceservice;

public interface ServerProbeService {

    void monitor(int interval) throws InterruptedException, Exception;
}
