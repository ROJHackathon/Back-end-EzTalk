package com.roj.eztalk.service;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.roj.eztalk.domain.json.WeightedMaterialJson;
import com.roj.eztalk.util.Http;

import org.springframework.stereotype.Service;

@Service
public class LamService {
    public List<WeightedMaterialJson> getRelevant(long id, int num, String modelType) throws Exception {
        String urlString = "http://wp3.x5gon.org/recommendsystem/v1";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        ((ObjectNode) rootNode).put("resource_id", id);
        ((ObjectNode) rootNode).put("n_neighbors", num);
        ((ObjectNode) rootNode).put("model_type", modelType);
        String jsonString = mapper.writeValueAsString(rootNode);
        String response = Http.post(urlString, jsonString);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response).get("output").get("rec_materials");
        WeightedMaterialJson[] materialJson = objectMapper.treeToValue(node, WeightedMaterialJson[].class);
        return Arrays.asList(materialJson);
    }
}