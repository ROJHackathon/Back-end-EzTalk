package com.roj.eztalk;

import com.roj.eztalk.util.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roj.eztalk.data.Material;
import com.roj.eztalk.data.json.MaterialJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class X5gonService implements X5gonInterface {

    @Autowired
    private UtilService utilService;

    private String base = "https://platform.x5gon.org/api/v1";

    @Override
    public List<Material> searchMaterial(String text) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        String result = this.get(base + "/search", params);
        List<Material> list = toMaterialList(result);
        return list;
    }

    public List<Material> recommendMaterial(String text, String page) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("page", page);
        String result = this.get(base + "/recommend/oer_materials", params);
        List<Material> list = toMaterialList(result);
        return list;
    }

    public Material getMaterialById(String id) throws Exception {
        String response = this.get(base + "/oer_materials/" + id, new HashMap<>());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response).get("oer_materials");

        Integer material_id = node.get("material_id").asInt();
        String title = node.get("title").toString();
        String description = node.get("description").toString();
        String language = node.get("language").toString();
        String provider = node.get("provider").toString();
        String url = node.get("url").toString();
        return new Material(material_id.toString(), title, description, language, provider, url, "false", 0, utilService.generateCoverUrl());
    }

    public String get(String base, Map<String, String> params) throws Exception {
        String urlStr = params.isEmpty() ? base : base + "?" + ParameterStringBuilder.getParameterString(params);
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("text", "french");

        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("connection", "Keep-Alive");
        con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        con.connect();

        Map<String, List<String>> map = con.getHeaderFields();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    private List<Material> toMaterialList(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(json);
        JsonNode rec_materials = root.get("rec_materials");
        List<MaterialJson> mList = objectMapper.readValue(rec_materials.toString(),
                new TypeReference<List<MaterialJson>>() {
                });
        List<Material> ret = new ArrayList<>();
        for (MaterialJson m : mList) {
            Material newMaterial = new Material(m.material_id, m.title, m.description, m.language, m.provider, m.url,
                    "false", 0, utilService.generateCoverUrl());
            ret.add(newMaterial);
        }
        return ret;
    }
}