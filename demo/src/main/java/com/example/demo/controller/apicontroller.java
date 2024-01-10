package com.example.demo.controller;

import com.example.demo.algorithm.DijkstraAlg;
import com.example.demo.model.Edge;
import com.example.demo.model.OutlineVertical;
import com.example.demo.model.Vertical;
import com.example.demo.request.Input;
import com.example.demo.service.IEdgeService;
import com.example.demo.service.IOutlineService;
import com.example.demo.service.IVerticalService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/")
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class apicontroller {
    private final IVerticalService verticalService;
    private final IEdgeService edgeService;
    private final DijkstraAlg dijkstraAlg;
    private final IOutlineService outlineService;

  @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/data")
    public String getvertical() {
        return "getvertical";
    }
    @PostMapping("api/fetch-verticals")
    public ResponseEntity<?> fetchVerticals(@RequestParam MultipartFile file) {
        if(file == null || file.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file is not exists");
        System.out.println("Nhap du lieu dinh");
        List<Vertical> verticalList= verticalService.fetchVerticals(file);
        if(verticalList.size()>0) return ResponseEntity.ok(verticalList);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List vertial not found");
    }
    @PostMapping("api/fetch-edges")
    public ResponseEntity<?> fetchEdges(@RequestParam MultipartFile file) {

        if(file == null || file.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file is not exists");
        System.out.println("Nhap du lieu canh");
        List<Edge> edgeList=edgeService.fetchEdges(file);
        if(edgeList.size()>0) return ResponseEntity.ok(edgeList);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List vertial not found");
    }

    @PostMapping("api/fetch-outlines")
    public ResponseEntity<?> fetchOutlines(@RequestParam MultipartFile file) {
        if(file == null || file.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file is not exists");
        System.out.println("Nhap du lieu bien ");
        List<OutlineVertical> outlineVerticalList = outlineService.fetchOutlines(file);
        if(outlineVerticalList.size()>0) return ResponseEntity.ok(outlineVerticalList);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Outline not found");
    }

    @GetMapping("api/get-outlines")
    public ResponseEntity<?> getOutlines() {
        System.out.println("Lay du lieu duong bien");
        List<OutlineVertical> outlineVerticalList= outlineService.getOutlines();
        if(outlineVerticalList.size()>0) return ResponseEntity.ok(outlineVerticalList);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found outlines");
    }

    @PostMapping("api/find-path")
    public ResponseEntity<?> findPath(@RequestBody Input input) {
        System.out.println(input.toString());
        System.out.println("Tim duong di ngan nhat");
        Vertical head= dijkstraAlg.findNearestVertex(input.getHead());
        Vertical end=dijkstraAlg.findNearestVertex(input.getEnd());
        System.out.println("Head: " + head.toString());
        System.out.println("End: " + end.toString());
        List<Vertical> verticalofshortestpath = dijkstraAlg.findPath(head,end);
        if(verticalofshortestpath.size()>0) return ResponseEntity.ok(verticalofshortestpath);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found find path");
    }

}
