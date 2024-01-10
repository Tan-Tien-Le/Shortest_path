package com.example.demo.service;

import com.example.demo.model.OutlineVertical;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOutlineService {
    List<OutlineVertical> fetchOutlines(MultipartFile file);

    List<OutlineVertical> getOutlines();
}
