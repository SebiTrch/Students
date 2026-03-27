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

        float notaM = gasesteNota("Bianca", "Popescu", mapStudenti);
        float notaN = gasesteNota("Ioan", "Popa", mapStudenti);

        System.out.println(notaM);
        System.out.println(notaN);
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
}