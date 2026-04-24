import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AplicatieCuBursa {

    static void main(String args[]) {
        AplicatieCuBursa instanta = new AplicatieCuBursa();
        List<StudentBursier> lista = instanta.genereaza();
        for (StudentBursier student : lista) {
            System.out.println(student);
        }
        System.out.println("------------------------");
        List<StudentBursier> sortata = instanta.sorteaza();
        for (StudentBursier student : sortata) {
            System.out.println(student);
        }
    }
    public List<StudentBursier> genereaza() {
        List<StudentBursier> lista = new ArrayList<>();
        lista.add(new StudentBursier(1025, "Andrei", "Popa", "ISM141/2", 8.70, 725.50));
        lista.add(new StudentBursier(1024, "Ioan", "Mihalcea", "ISM141/1", 9.80, 801.10));
        lista.add(new StudentBursier(1029, "Bianca", "Popescu", "TI131/1", 9.10, 780.80));
        lista.add(new StudentBursier(1026, "Anamaria", "Prodan", "TI131/1", 8.90, 745.50));
        lista.add(new StudentBursier(1029, "Bianca", "Popescu", "TI131/1", 9.10, 100.00));
        return lista;
    }

    public List<StudentBursier> sorteaza() {
        List<StudentBursier> lst = genereaza();

        lst.sort(Comparator.comparing(StudentBursier::getGrupa)
                .thenComparing(StudentBursier::getNume)
                .thenComparing(StudentBursier::getPrenume)
                .thenComparing(StudentBursier::getNota)
                .thenComparing(StudentBursier::getCuantumBursa));

        return lst;
    }
}
