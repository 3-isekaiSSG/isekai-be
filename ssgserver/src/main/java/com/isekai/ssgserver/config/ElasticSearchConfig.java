package com.isekai.ssgserver.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends ElasticsearchConfiguration {

	@Value("${spring.data.elasticsearch.url}")
	String esUrl;
	@Value("${spring.data.elasticsearch.username}")
	String esUser;
	@Value("${spring.data.elasticsearch.password}")
	String esPass;

	@Bean
	public RestHighLevelClient restHighLevelClient() {

		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
			.connectedTo(esUrl)
			.withBasicAuth(esUser, esPass)
			.build();

		return RestClients.create(clientConfiguration).rest();
	}

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
			.connectedTo(esUrl)
			.withBasicAuth(esUser, esPass)
			.build();
	}
}
