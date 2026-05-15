import java.util.Arrays;
import java.util.List;

public class AplicatieCuStrategy {
    private ExportStrategy exportStrategy;
    private ImportStrategy importStrategy;

    public void setExportStrategy(ExportStrategy strategy) {
        this.exportStrategy = strategy;
    }

    public void setImportStrategy(ImportStrategy strategy) {
        this.importStrategy = strategy;
    }

    public void executaExport(List<Students> lista, String destinatie) {
        exportStrategy.prelucreaza(lista, destinatie);
    }

    public List<Students> executaImport(String sursa) {
        return importStrategy.incarca(sursa);
    }

    public static void main(String[] args) {
        AplicatieCuStrategy app = new AplicatieCuStrategy();

        List<Students> studenti = Arrays.asList(
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

        app.setExportStrategy(new StudentiInConsola());
        app.executaExport(studenti, null);

        app.setExportStrategy(new StudentiInFisierText());
        app.executaExport(studenti, "StudentiInFisier.txt");

        app.setExportStrategy(new StudentiInFisierXlsx());
        app.executaExport(studenti, "StudentiInExcel.xlsx");

      System.out.println("\n Citire din TXT");
        app.setImportStrategy(new StudentiImport());
        List<Students> dinTxt = app.executaImport("StudentiInFisier.txt");
        dinTxt.forEach(System.out::println);

        System.out.println("\n Citire din Excel");
        app.setImportStrategy(new StudentiDinFisierXlsx());
        List<Students> dinXlsx = app.executaImport("StudentiInExcel.xlsx");
        dinXlsx.forEach(System.out::println);

    }
}