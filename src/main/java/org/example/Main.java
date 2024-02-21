package org.example;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String url = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        List<CatFact> catFacts = mapper.readValue(json, new TypeReference<List<CatFact>>(){});

        List<CatFact> filteredCatFacts = catFacts.stream()
                .filter(fact -> fact.getUpvotes() != null && fact.getUpvotes() > 0)
                .collect(Collectors.toList());

        filteredCatFacts.forEach(System.out::println);
    }

    // Предполагается, что класс CatFact уже создан и соответствует структуре JSON
    public static class CatFact {
        private String id;
        private String text;
        private String type;
        private String user;
        private Integer upvotes;

        // Геттеры и сеттеры
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public Integer getUpvotes() {
            return upvotes;
        }

        public void setUpvotes(Integer upvotes) {
            this.upvotes = upvotes;
        }

        @Override
        public String toString() {
            return "CatFact{" +
                    "id='" + id + '\'' +
                    ", text='" + text + '\'' +
                    ", type='" + type + '\'' +
                    ", user='" + user + '\'' +
                    ", upvotes=" + upvotes +
                    '}';
        }
    }

}
