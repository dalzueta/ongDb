package org.daniel.controller;

import org.daniel.services.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/graph")
public class GraphController {
  private final GraphService graphService;

  public GraphController(GraphService graphService) {
    this.graphService = graphService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Archivo vacío.");
    }
    String result = graphService.processFile(file);
    return ResponseEntity.ok(result);
  }
}