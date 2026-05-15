import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface ImportStrategy {

        List<Students> incarca(String sursa);
    }

    class StudentiImport implements ImportStrategy {
        @Override
        public List<Students> incarca(String sursa) {
            List<Students> studenti = new ArrayList<>();
            try (Scanner scanner = new Scanner(new File(sursa))) {
                while (scanner.hasNextLine()) {
                    String[] p = scanner.nextLine().split(",");
                    if (p.length >= 5) {
                        studenti.add(new Students(
                                Integer.parseInt(p[0].trim()),
                                p[1].trim(), p[2].trim(), p[3].trim(),
                                Double.parseDouble(p[4].trim())
                        ));
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Fișierul TXT nu a fost găsit.");
            }
            return studenti;
        }

    }

    class StudentiDinFisierXlsx implements ImportStrategy {
        @Override
        public List<Students> incarca(String sursa) {
            List<Students> studenti = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(sursa);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    studenti.add(new Students(
                            (int) row.getCell(0).getNumericCellValue(),
                            row.getCell(1).getStringCellValue(),
                            row.getCell(2).getStringCellValue(),
                            row.getCell(3).getStringCellValue(),
                            row.getCell(4).getNumericCellValue()
                    ));
                }
            } catch (IOException e) {
                System.err.println("Eroare la citirea fișierului XLSX.");
            }
            return studenti;
        }
    }
