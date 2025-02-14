package org.daniel.config;

import org.neo4j.driver.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig {
  @Bean
  public Driver neo4jDriver() {
    return GraphDatabase.driver("bolt://ongdb:7687",
        AuthTokens.basic("neo4j", "fulanoPass"));
  }
}