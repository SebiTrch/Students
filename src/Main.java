import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fisierStudenti = "students_in.txt";
        String fisierNote = "note_anon.txt";

        Map<Integer, Students> mapStudenti = citesteStudenti(fisierStudenti);
        mapStudenti = citesteNote(fisierNote, mapStudenti);

        float notaM = gasesteNota("Bianca", "Popescu", mapStudenti);
        float notaN = gasesteNota("Ioan", "Popa", mapStudenti);

        System.out.println("Notele cautate sunt:");
        System.out.println("Bianca Popescu: " + notaM);
        System.out.println("Ioan Popa: " + notaN);

        List<StudentBursier> bursieri = new ArrayList<>();
        bursieri.add(new StudentBursier(1025, "Andrei", "Popa", "ISM141/2", 8.70, 725.50));
        bursieri.add(new StudentBursier(1024, "Ioan", "Mihalcea", "ISM141/1", 9.80, 801.10));
        bursieri.add(new StudentBursier(1026, "Anamaria", "Prodan", "TI131/1", 8.90, 745.50));
        bursieri.add(new StudentBursier(1029, "Bianca", "Popescu", "TI131/1", 9.10, 780.80));

        salvareInFisier("bursieri_out.txt", bursieri);
    }

    public static float gasesteNota(String prenume, String nume, Map<Integer, Students> tineri) {
        for (Students s : tineri.values()) {
            if (s.getPrenume().equalsIgnoreCase(prenume.trim()) &&
                    s.getNume().equalsIgnoreCase(nume.trim())) {
                return (float) s.getNota();
            }
        }
        return 0.0f;
    }

    public static Map<Integer, Students> citesteStudenti(String fisier) {
        Map<Integer, Students> harta = new HashMap<>();
        try (Scanner sc = new Scanner(new File(fisier))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    harta.put(id, new Students(id, parts[1].trim(), parts[2].trim(), parts[3].trim(), 0.0));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul de studenti nu a fost gasit.");
        }
        return harta;
    }

    public static Map<Integer, Students> citesteNote(String fisier, Map<Integer, Students> mapStudenti) {
        try (Scanner sc = new Scanner(new File(fisier))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0].trim());
                    double nota = Double.parseDouble(parts[1].trim());
                    if (mapStudenti.containsKey(id)) {
                        Students vechi = mapStudenti.get(id);
                        mapStudenti.put(id, new Students(vechi.getId(), vechi.getPrenume(), vechi.getNume(), vechi.getGrupa(), nota));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul de note nu a fost gasit.");
        }
        return mapStudenti;
    }

    public static void salvareInFisier(String numeFisier, List<StudentBursier> colectie) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(numeFisier))) {
            for (StudentBursier student : colectie) {
                writer.println(student.toString());
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisier.");
        }
    }
}