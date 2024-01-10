package com.example.demo.service;

import com.example.demo.model.Vertical;
import com.example.demo.repository.VerticalRepository;
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
public class VerticalService implements IVerticalService{
    private final VerticalRepository verticalRepository;
    @Override
    public List<Vertical> fetchVerticals(MultipartFile file) {
        List<Vertical> verticals = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet in the Excel file

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Vertical vertical = new Vertical();

                // Assuming the first column is label, second is latitude (vĩ độ), and third is longitude (kinh độ)
                Cell labelCell = row.getCell(0);
                Cell latitudeCell = row.getCell(1);
                Cell longitudeCell = row.getCell(2);

                vertical.setLabel((int) labelCell.getNumericCellValue());
                vertical.setX(Double.parseDouble(latitudeCell.getStringCellValue()));
                vertical.setY(Double.parseDouble(longitudeCell.getStringCellValue()));


                verticals.add(vertical);
                }


            // Save the data to the database if needed
            verticalRepository.saveAll(verticals);

        } catch (IOException | EncryptedDocumentException e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }

        return verticals;
    }

}
