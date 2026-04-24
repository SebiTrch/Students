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
        citesteNote(fisierNote, mapStudenti);

        float notaM = gasesteNota("Bianca", "Popescu", mapStudenti);
        float notaN = gasesteNota("Ioan", "Popa", mapStudenti);
        System.out.printf("Notele anonime sunt:\n");
        System.out.println(notaM);
        System.out.println(notaN);

        List<StudentBursier> bursieri = new ArrayList<>();
        bursieri.add(new StudentBursier(1025, "Andrei", "Popa", "ISM141/2", 8.70, 725.50));
        bursieri.add(new StudentBursier(1024, "Ioan", "Mihalcea", "ISM141/1", 9.80, 801.10));
        bursieri.add(new StudentBursier(1026, "Anamaria", "Prodan", "TI131/1", 8.90, 745.50));
        bursieri.add(new StudentBursier(1029, "Bianca", "Popescu", "TI131/1", 9.10, 780.80));

        salvareInFisier("bursieri_out.txt", bursieri);
    }

    public static float gasesteNota(String prenume, String nume, Map<Integer, Students> tineri) {
        HashMap<String, Students> mapCautare = new HashMap<>();
        for (Students s : tineri.values()) {
            mapCautare.put(s.getPrenume().trim() + "-" + s.getNume().trim(), s);
        }

        String cheie = prenume.trim() + "-" + nume.trim();
        if (mapCautare.containsKey(cheie)) {
            return (float) mapCautare.get(cheie).getNota();
        }
        return 0.0f;
    }

    public static Map<Integer, Students> citesteStudenti(String fisier) {
        Map<Integer, Students> mapStudenti = new HashMap<>();
        try {
            File file = new File(fisier);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");

                    if (parts.length == 4) {
                        int id = Integer.parseInt(parts[0].trim());
                        String prenume = parts[1].trim();
                        String nume = parts[2].trim();
                        String grupa = parts[3].trim();

                        mapStudenti.put(id, new Students(id, prenume, nume, grupa));
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Eroare .");
        }
        return mapStudenti;
    }

    public static void citesteNote(String fisier, Map<Integer, Students> mapStudenti) {
        try {
            File file = new File(fisier);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");

                    if (parts.length == 2) {
                        int id = Integer.parseInt(parts[0].trim());
                        double nota = Double.parseDouble(parts[1].trim());

                        if (mapStudenti.containsKey(id)) {
                            mapStudenti.get(id).setNota(nota);
                        }
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Eroare.");
        }
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