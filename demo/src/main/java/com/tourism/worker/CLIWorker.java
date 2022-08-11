package com.tourism.worker;

import com.tourism.bootstrap.Bootstrap;
import com.tourism.core.CLIBootstrap;

public class CLIWorker {
    public CLIWorker startWork() {
//        try {
        Bootstrap.getWorkerExecutor().executeBlocking(future -> {
            CLIBootstrap.start();
        }, false, res -> {
        });//加上false并发执行
//        } catch (Throwable e) {
//            System.out.println("你已超过一分钟没操作");
//            this.startWork();
//        }
        return this;
    }
}
