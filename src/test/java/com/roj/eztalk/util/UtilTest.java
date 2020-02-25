package com.roj.eztalk.util;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.roj.eztalk.domain.MaterialItem;
import com.roj.eztalk.domain.json.MaterialJson;
import com.roj.eztalk.domain.json.WeightedMaterialJson;
import com.roj.eztalk.service.LamService;
import com.roj.eztalk.service.X5gonService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UtilTest {
    @TestConfiguration
    static class UtilServiceTestContextConfiguration {
        @Bean
        public LamService lamService() {
            return new LamService();
        }
        // @Bean
        // public X5gonService x5gonService() {
        //     return new X5gonService();
        // }
    }

    @Autowired
    LamService lamService;
    // @Autowired
    // X5gonService x5gonService;
    @Test
    public void post() throws Exception {
        String urlString = "http://wp3.x5gon.org/recommendsystem/v1";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        ((ObjectNode) rootNode).put("resource_id", 65478);
        ((ObjectNode) rootNode).put("n_neighbors", 2);
        ((ObjectNode) rootNode).put("model_type", "doc2vec");
        String jsonString = mapper. writeValueAsString(rootNode);
        String response = Http.post(urlString, jsonString);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response).get("output").get("rec_materials");
        WeightedMaterialJson[] materialJson = objectMapper.treeToValue(node, WeightedMaterialJson[].class);
        int length = materialJson.length;
        //MaterialItem materialAdd = new MaterialItem(materialJson, material);
    }

    @Test
    public void relevant() throws Exception {
        List<WeightedMaterialJson> materials = lamService.getRelevant(65478L, 2, "doc2vec");
        materials.size();
    }

    // @Test
    // public void feed2() throws Exception {
    //     List<MaterialItem> materials = x5gonService.getFeed(3L);
    //     materials.size();
    // }
}