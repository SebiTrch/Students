import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fisierStudenti = "students_in.txt";
        String fisierNote = "note_anon.txt";

        Map<Integer, Students> mapStudenti = citesteStudenti(fisierStudenti);
        citesteNote(fisierNote, mapStudenti);
        afisare(mapStudenti);
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
                        int id = Integer.parseInt(parts[0]);
                        String prenume = parts[1];
                        String nume = parts[2];
                        String grupa = parts[3];

                        mapStudenti.put(id, new Students(id, prenume, nume, grupa));
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Eroare");
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
                        int id = Integer.parseInt(parts[0]);
                        double nota = Double.parseDouble(parts[1]);

                        if (mapStudenti.containsKey(id)) {
                            mapStudenti.get(id).setNota(nota);
                        }
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Eroare");
        }
    }

    public static void afisare(Map<Integer, Students> mapStudenti) {
        for (Map.Entry<Integer, Students> entry : mapStudenti.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}