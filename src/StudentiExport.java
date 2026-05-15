import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

interface ExportStrategy {
    void prelucreaza(List<Students> lista, String destinatie);
}

class StudentiInConsola implements ExportStrategy {
    @Override
    public void prelucreaza(List<Students> lista, String destinatie) {
        System.out.println(" Lista Studenti");
        lista.forEach(System.out::println);
    }
}

class StudentiInFisierText implements ExportStrategy {
    @Override
    public void prelucreaza(List<Students> lista, String destinatie) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destinatie))) {
            for (Students s : lista) {
                writer.println(s.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class StudentiInFisierXlsx implements ExportStrategy {
    @Override
    public void prelucreaza(List<Students> lista, String destinatie) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Studenti");
            int rowNum = 0;
            for (Students st : lista) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(st.getId());
                row.createCell(1).setCellValue(st.getPrenume());
                row.createCell(2).setCellValue(st.getNume());
                row.createCell(3).setCellValue(st.getGrupa());
                row.createCell(4).setCellValue(st.getNota());
            }
            try (FileOutputStream fileOut = new FileOutputStream(destinatie)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}