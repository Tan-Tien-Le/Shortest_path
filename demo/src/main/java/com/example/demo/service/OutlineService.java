package com.example.demo.service;

import com.example.demo.model.Edge;
import com.example.demo.model.OutlineVertical;
import com.example.demo.repository.OutlineVerticalRepository;
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
public class OutlineService implements IOutlineService {
    private final OutlineVerticalRepository outlineVerticalRepository;
    @Override
    public List<OutlineVertical> fetchOutlines(MultipartFile file) {
        List<OutlineVertical> outlines = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet in the Excel file

            Iterator<Row> rowIterator = sheet.iterator();
            // Skip the header row if exists


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                OutlineVertical outline =new OutlineVertical();

                // Assuming the first column is label, second is latitude (vĩ độ), and third is longitude (kinh độ)
                Cell pos = row.getCell(0);
                outline.setPos(pos.getStringCellValue());

                outlines.add(outline);
            }


            // Save the data to the database if needed
            outlineVerticalRepository.saveAll(outlines);

        } catch (IOException | EncryptedDocumentException e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }

        return outlines;
    }

    @Override
    public List<OutlineVertical> getOutlines() {
        return outlineVerticalRepository.findAll();
    }
}
