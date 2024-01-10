package com.example.demo.service;

import com.example.demo.model.Edge;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEdgeService {
    List<Edge> fetchEdges(MultipartFile file);
}
