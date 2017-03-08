/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.google.api.services.youtube.model.SearchResult;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Mihai
 */
@PropertySource("classpath:application.properties")

public class channel {

    String name;
    List<Video> videos = new ArrayList<> ();

    public channel(String name) {
        this.name = name;
    }

    public List<SearchResult> getVideos() {

        return YoutubeClient.executeRequest (name);
    }
}
