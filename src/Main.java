import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Map<Integer, Students> date = citesteStudenti("students_in.txt");
        date = citesteNote("note_anon.txt", date);
        List<Students> listaInitiala = new ArrayList<>(date.values());

        exportaStudentiExcel(listaInitiala, "laborator8_students.xls");

        List<Students> listaImportata = importaStudentiExcel("laborator8_students.xls");

        System.out.println("Studenti recuperati din Excel:");
        listaImportata.forEach(System.out::println);

        List<Students> studentiCuNote = Arrays.asList(
                new Students(1025, "Andrei", "Popa", "ISM141/2", 8.70),
                new Students(1024, "Ioan", "Mihalcea", "ISM141/1", 10.0),
                new Students(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
                new Students(1029, "Bianca", "Popescu", "TI131/1", 10.0),
                new Students(1029, "Maria", "Pana", "TI131/2", 4.10),
                new Students(1029, "Gabriela", "Mohanu", "TI131/2", 7.33),
                new Students(1029, "Marius", "Nasta", "TI131/2", 3.20),
                new Students(1029, "Marius", "Nasta", "TI131/1", 5.12),
                new Students(1029, "Andrei", "Dobrescu", "TI131/2", 2.22)
        );

        System.out.println("\na) Studenții cu nota 10:");
        studentiCuNote.stream()
                .filter(s -> s.getNota() == 10.0)
                .forEach(System.out::println);

        System.out.println("\nb) Studenții cu nota sub 5:");
        studentiCuNote.stream()
                .filter(s -> s.getNota() < 5.0)
                .forEach(System.out::println);

        System.out.println("\nc) Lista transformată (notele < 4 devin 4):");
        List<Students> studentiTransformati = studentiCuNote.stream()
                .map(s -> s.getNota() < 4.0 ?
                        new Students(s.getId(), s.getPrenume(), s.getNume(), s.getGrupa(), 4.0) : s)
                .collect(Collectors.toList());
        studentiTransformati.forEach(System.out::println);

        System.out.println("\nd) Suma notelor tuturor studenților:");
        double sumaNote = studentiCuNote.stream()
                .map(Students::getNota)
                .reduce(0.0, Double::sum);
        System.out.println("Suma este: " + sumaNote);

        System.out.println("\ne) Media notelor:");
        double media = sumaNote / studentiCuNote.size();
        System.out.println("Media este: " + media);
    }

    public static void exportaStudentiExcel(List<Students> lista, String numeFisier) {
        try (Workbook wb = new HSSFWorkbook()) {
            Sheet s = wb.createSheet("Lista Studenti");
            int i = 0;
            for (Students st : lista) {
                Row r = s.createRow(i++);
                r.createCell(0).setCellValue(st.getId());
                r.createCell(1).setCellValue(st.getPrenume());
                r.createCell(2).setCellValue(st.getNume());
                r.createCell(3).setCellValue(st.getGrupa());
                r.createCell(4).setCellValue(st.getNota());
            }
            try (FileOutputStream fos = new FileOutputStream(numeFisier)) {
                wb.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Students> importaStudentiExcel(String numeFisier) {
        List<Students> rezultat = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(numeFisier);
             Workbook wb = new HSSFWorkbook(fis)) {
            Sheet s = wb.getSheetAt(0);
            for (Row r : s) {
                if (r == null) continue;
                rezultat.add(new Students(
                        (int) r.getCell(0).getNumericCellValue(),
                        r.getCell(1).getStringCellValue(),
                        r.getCell(2).getStringCellValue(),
                        r.getCell(3).getStringCellValue(),
                        r.getCell(4).getNumericCellValue()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public static Map<Integer, Students> citesteStudenti(String f) {
        Map<Integer, Students> m = new HashMap<>();
        try (Scanner sc = new Scanner(new File(f))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (p.length >= 4) {
                    int id = Integer.parseInt(p[0].trim());
                    m.put(id, new Students(id, p[1].trim(), p[2].trim(), p[3].trim(), 0));
                }
            }
        } catch (Exception e) { }
        return m;
    }

    public static Map<Integer, Students> citesteNote(String f, Map<Integer, Students> m) {
        try (Scanner sc = new Scanner(new File(f))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                int id = Integer.parseInt(p[0].trim());
                if (m.containsKey(id)) {
                    Students s = m.get(id);
                    m.put(id, new Students(s.getId(), s.getPrenume(), s.getNume(), s.getGrupa(), Double.parseDouble(p[1].trim())));
                }
            }
        } catch (Exception e) { }
        return m;
    }
}