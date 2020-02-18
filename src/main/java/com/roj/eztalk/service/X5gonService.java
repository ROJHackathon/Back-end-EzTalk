package com.roj.eztalk.service;

import com.roj.eztalk.util.*;
import com.roj.eztalk.service.UtilService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roj.eztalk.domain.Material;
import com.roj.eztalk.domain.MaterialItem;
import com.roj.eztalk.dao.MaterialRepository;
import com.roj.eztalk.domain.json.MaterialJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class X5gonService {
    // for searching, getting recommendation and retrieving material by ID
    @Autowired
    private UtilService utilService;
    @Autowired
    private MaterialRepository materialRepository;

    private String base = "https://platform.x5gon.org/api/v1";

    public List<MaterialItem> searchMaterial(String text) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        String result = Http.get(base + "/search", params);
        return toMaterialList(result);
    }

    public List<MaterialItem> recommendMaterial(String text, Integer page) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("page", page.toString());
        String result = Http.get(base + "/recommend/oer_materials", params);
        return toMaterialList(result);
    }

    public MaterialItem getMaterialById(Long id) throws Exception {
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
        MaterialJson materialJson = objectMapper.treeToValue(node, MaterialJson.class);
        MaterialItem materialAdd = new MaterialItem(materialJson, material);
        return materialAdd;
    }

    private List<MaterialItem> toMaterialList(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(json);
        JsonNode rec_materials = root.get("rec_materials");
        List<MaterialJson> mList = objectMapper.readValue(rec_materials.toString(),
                new TypeReference<List<MaterialJson>>() {
                });
        List<MaterialItem> retval = new ArrayList<>();
        for(MaterialJson ma : mList){
            Long id = Long.parseLong(ma.material_id);
            Optional<Material> opMaterial = materialRepository.findById(id);
            if(opMaterial.isPresent()){
                retval.add(new MaterialItem(ma, opMaterial.get()));
            } else {
                Material material = new Material(id, utilService.generateCoverUrl(), 0);
                materialRepository.save(material);
                retval.add(new MaterialItem(ma, material));
            }
        }
        return retval;
    }
}