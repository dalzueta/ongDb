package org.daniel.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GraphService {
  private final Driver driver;

  public GraphService(Driver driver) {
    this.driver = driver;
  }

  public String processFile(MultipartFile file) {
    try (Session session = driver.session()) {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(",");
          if (parts.length == 2) {
            String node1 = parts[0].trim();
            String node2 = parts[1].trim();

            session.run("MERGE (a:Node {name: $node1}) " +
                    "MERGE (b:Node {name: $node2}) " +
                    "MERGE (a)-[:RELATED_TO]->(b)",
                Values.parameters("node1", node1, "node2", node2));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();  // Esto imprimirá la excepción en los logs
      return "Error al procesar el archivo: " + e.getMessage();
    }
    return "Archivo procesado correctamente.";
  }

}