package com.zynn.common.core.config.es;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;


    @Bean
    public RestHighLevelClient initRestHighLevelClient() {
        if(StringUtils.isBlank(elasticsearchProperties.getNodes())) {
            return null;
        }

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));

        String[] nodes = elasticsearchProperties.getNodes().split(",");
        HttpHost[] httpHosts = new HttpHost[nodes.length];
        for (int i =0; i<nodes.length; i++) {
            String node = nodes[i];
            String[] split = node.split(":");
            String host = split[0];
            int port = 80;
            if(split.length == 2) {
                port = Integer.parseInt(split[1]);
            }
            httpHosts[i] = new HttpHost(host, port, "http");
        }
        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);
        if(StringUtils.isNotBlank(elasticsearchProperties.getPrefix())) {
            restClientBuilder.setPathPrefix(elasticsearchProperties.getPrefix());
        }

        RestHighLevelClient client = new RestHighLevelClient(
                restClientBuilder
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        );
        return client;
    }
}
