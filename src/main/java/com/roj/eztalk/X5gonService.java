package com.roj.eztalk;

import com.roj.eztalk.util.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.roj.eztalk.data.MaterialAdd;
import com.roj.eztalk.data.MaterialRepository;
import com.roj.eztalk.data.Material;
import com.roj.eztalk.data.json.MaterialJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class X5gonService {

    @Autowired
    private UtilService utilService;
    @Autowired
    private MaterialRepository materialRepository;

    private String base = "https://platform.x5gon.org/api/v1";

    public List<MaterialAdd> searchMaterial(String text) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        String result = Http.get(base + "/search", params);
        return toMaterialList(result);
    }

    public List<MaterialAdd> recommendMaterial(String text, Integer page) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("page", page.toString());
        String result = Http.get(base + "/recommend/oer_materials", params);
        return toMaterialList(result);
    }

    public MaterialAdd getMaterialById(Long id) throws Exception {
        // the material already exists in eztalk database
        Material material;
        Optional<Material> opMaterial = materialRepository.findById(id);
        if (opMaterial.isPresent()) {
            material = opMaterial.get();
        } else {
            material = new Material(id, utilService.generateCoverUrl(), 0);
            materialRepository.save(material);
        }
        // the material does not exist in eztalk database
        String response = Http.get(base + "/oer_materials/" + id, new HashMap<>());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response).get("oer_materials");

        Long material_id = node.get("material_id").asLong();
        String title = node.get("title").toString();
        String description = node.get("description").toString();
        String language = node.get("language").toString();
        String provider = node.get("provider").toString();
        String url = node.get("url").toString();
        
        MaterialAdd retval = new MaterialAdd();
        retval.setId(material_id);
        retval.setTitle(title);
        retval.setTitle(description);
        retval.setLanguage(language);;
        retval.setProvider(provider);
        retval.setUrl(url);

        retval.setLove(material.getLove());
        retval.setCoverUrl(material.getCoverUrl());
        return retval;
    }

    private List<MaterialAdd> toMaterialList(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(json);
        JsonNode rec_materials = root.get("rec_materials");
        List<MaterialJson> mList = objectMapper.readValue(rec_materials.toString(),
                new TypeReference<List<MaterialJson>>() {
                });
        List<MaterialAdd> retval = new ArrayList<>();
        for(MaterialJson ma : mList){
            Long id = Long.parseLong(ma.material_id);
            Optional<Material> opMaterial = materialRepository.findById(id);
            if(opMaterial.isPresent()){
                retval.add(new MaterialAdd(ma, opMaterial.get()));
            } else {
                Material material = new Material(id, utilService.generateCoverUrl(), 0);
                materialRepository.save(material);
                retval.add(new MaterialAdd(ma, material));
            }
        }
        return retval;
    }
}