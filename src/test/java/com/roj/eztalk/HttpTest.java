package com.roj.eztalk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roj.eztalk.domain.TranslateJson;
import com.roj.eztalk.util.Http;

import org.junit.Test;

public class HttpTest {
    @Test
    public void post() throws Exception{
        String url = "https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/4c79d3b6-b846-4ca7-86fa-2a09024e43d4/v3/translate?version=2018-05-01";
        String key = "LZZVFYbcggzSeDZ2Kbh30buPxh4avQPPHuEkFRdze0Q8";

        ObjectMapper mapper = new ObjectMapper();
        TranslateJson json = new TranslateJson();
        json.model_id = "en-fr";
        json.text.add("hello");

        String jsonString = mapper.writeValueAsString(json);

        String response = Http.postAuthentication(url, jsonString, "apikey", key);
        response.length();
    }
}