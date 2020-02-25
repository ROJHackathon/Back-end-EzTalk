package com.roj.eztalk.service;

import com.roj.eztalk.util.*;
import com.roj.eztalk.service.UtilService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roj.eztalk.domain.Material;
import com.roj.eztalk.domain.MaterialItem;
import com.roj.eztalk.domain.Rating;
import com.roj.eztalk.domain.User;
import com.roj.eztalk.dao.MaterialRepository;
import com.roj.eztalk.domain.json.MaterialJson;
import com.roj.eztalk.domain.json.WeightedMaterialJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class X5gonService {
    // for searching, getting recommendation and retrieving material by ID
    @Autowired
    private UtilService utilService;
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private LamService lamService;

    @Autowired
    private MaterialService materialService;

    private String base = "https://platform.x5gon.org/api/v1";
    private String lamBase = "http://wp3.x5gon.org";

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

    public List<MaterialItem> recommendMaterialNum(String text, Integer num) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("limit", num.toString());
        String response = Http.get(base + "/recommend/oer_materials", params);
        return toMaterialList(response);
    }

    public MaterialItem getMaterialById(Long id) throws Exception {
        String response = Http.get(base + "/oer_materials/" + id, new HashMap<>());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response).get("oer_materials");
        MaterialJson materialJson = objectMapper.treeToValue(node, MaterialJson.class);
        Material material = materialService.insertIfNotExists(materialJson);
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
    
    public List<MaterialItem> getFeed(Long token){
        User user = sessionService.getUserByToken(token);
        if(user == null) return null;
        
        List<MaterialItem> retMaterial = new ArrayList<>();
        List<MaterialItem> related = new ArrayList<>();
        List<MaterialItem> preference = new ArrayList<>();

        List<Rating> ratings = user.getRatings();
        for(Rating r : ratings){
            if(r.getRating() < 4) continue;
            long id = r.getMaterial().getId();
            try{
                List<WeightedMaterialJson> weightedMaterials = lamService.getRelevant(id, r.getRating() * 2, "doc2vec");
                // weightedMaterials.forEach(x->materialService.insertIfNotExists(x));
                weightedMaterials.forEach(x->related.add(new MaterialItem(x, materialService.insertIfNotExists(x))));
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        try{
            preference = recommendMaterialNum(user.getPreference(), 100);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        Random rand = new Random();
        
        for(int num = 0;num < 5 && !related.isEmpty();num++){
            int index = rand.nextInt(related.size());
            retMaterial.add(related.get(index));
            related.remove(index);
        }

        for(int num = retMaterial.size(); num < 10; num++){
            int index = rand.nextInt(preference.size());
            retMaterial.add(preference.get(index));
            preference.remove(index);
        }

        return retMaterial;
    }
}