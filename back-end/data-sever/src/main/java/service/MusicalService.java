package service;

import constant.Sub_category;
import constant.URLs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
