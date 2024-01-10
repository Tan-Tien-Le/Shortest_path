package com.example.demo.service;

import com.example.demo.model.Vertical;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IVerticalService {
    List<Vertical> fetchVerticals(MultipartFile file);
}
