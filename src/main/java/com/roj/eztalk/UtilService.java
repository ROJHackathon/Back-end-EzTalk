package com.roj.eztalk;

import org.springframework.stereotype.Service;

@Service
public class UtilService {
    public String generateCoverUrl() {
        String ret = "https://cdn.framework7.io/placeholder/nature-1000x600-%s.jpg";
        Long index = Math.round(Math.random() * 7 + 1);
        return String.format(ret, index.toString());
    }

    public String generateAvatarUrl() {
        String ret = "http://placeimg.com/80/80/people/";
        Long index = Math.round(Math.random() * 99 + 1);
        return ret + index.toString();
    }
}