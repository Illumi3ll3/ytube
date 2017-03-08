/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mihai
 */
@Component
@PropertySource("classpath:application.properties")
public class Scheduler {

    private static final Logger log = LoggerFactory.getLogger (Scheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat ("HH:mm:ss");
    @Value("${*}")
    List<String> asdasd;
    List<channel> channelList = new ArrayList<> ();

    {
        channelList.add (new channel ("The thinking Atheist"));
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println (asdasd);
        for (channel c : channelList) {
            System.out.println (c.getVideos ());

        }
        System.out.println ("The time is now {}" + dateFormat.format (new Date ()));
        log.info ("The time is now {}", dateFormat.format (new Date ()));
    }

}
