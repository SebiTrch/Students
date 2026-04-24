import java.util.Objects;

public class Students {
    private final int id;
    private final String prenume;
    private final String nume;
    private final String grupa;
    private final double nota;

    public Students(int id, String prenume, String nume, String grupa, double nota) {
        this.id = id;
        this.prenume = prenume;
        this.nume = nume;
        this.grupa = grupa;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getGrupa() {
        return grupa;
    }

    public double getNota() {
        return nota;
    }

    public Students mutaInGrupa(String nouaGrupa) {
        return new Students(this.id, this.prenume, this.nume, nouaGrupa, this.nota);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return id == students.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + "," + prenume + "," + nume + "," + grupa + "," + nota;
    }
}