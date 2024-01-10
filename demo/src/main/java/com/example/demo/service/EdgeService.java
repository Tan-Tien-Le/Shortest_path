package com.example.demo.service;

import com.example.demo.model.Edge;
import com.example.demo.model.Vertical;
import com.example.demo.repository.EdgeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EdgeService implements IEdgeService {
    private final EdgeRepository edgeRepository;
    @Override
    public List<Edge> fetchEdges(MultipartFile file) {
        List<Edge> edges = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet in the Excel file

            Iterator<Row> rowIterator = sheet.iterator();
            // Skip the header row if exists


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Edge edge =new Edge();

                // Assuming the first column is label, second is latitude (vĩ độ), and third is longitude (kinh độ)
                Cell startCell = row.getCell(0);
                Cell endCell = row.getCell(1);

                edge.setStart((int) startCell.getNumericCellValue());
                edge.setEnd((int) endCell.getNumericCellValue());

                edges.add(edge);
            }


            // Save the data to the database if needed
            edgeRepository.saveAll(edges);

        } catch (IOException | EncryptedDocumentException e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }

        return edges;
    }
}
