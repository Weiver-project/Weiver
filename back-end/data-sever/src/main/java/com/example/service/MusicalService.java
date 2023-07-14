package com.example.service;

import com.example.constant.Sub_category;
import com.example.constant.URLs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MusicalService {
    public void task(){

    }

    public void musicalCrawling(){
        int page = 1;
        int maxPage = 0;
        String MUSICAL_URL = URLs.MUSICAL_URL + URLs.SUB_CATEGORY + Sub_category.LICENSES + URLs.PAGE;

        try {
            Document doc = Jsoup.connect(MUSICAL_URL + page).get();
            Elements pageNumber = doc.select("");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
