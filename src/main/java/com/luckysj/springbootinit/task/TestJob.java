package com.luckysj.springbootinit.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling //启用定时任务
public class TestJob {
    @Scheduled(cron = "0 0/3 * * * ?") //三分钟执行一次
    public void exec() {
        System.out.println("job exec");
    }
}
