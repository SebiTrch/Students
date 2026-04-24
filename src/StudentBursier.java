import java.util.Objects;

public class StudentBursier extends Students {
    private double cuantumBursa;

    public StudentBursier(int id, String prenume, String nume, String grupa, double nota, double cuantumBursa) {
        super(id, prenume, nume, grupa);
        super.nota= nota;
        this.cuantumBursa = cuantumBursa;
    }
public double getCuantumBursa() {
        return cuantumBursa;
}
    @Override
    public boolean equals(Object o) {
        if(o==null || getClass()!=o.getClass()){
            return false;
        }
        if(!super.equals(o)){
            return false;
        }
        StudentBursier that = (StudentBursier) o;
        return Double.compare(cuantumBursa, that.cuantumBursa) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cuantumBursa);
    }

    @Override
    public String toString() {
        String s =super.toString();
        s+=String.format("[%6.2f]", cuantumBursa);
        return s;
    }
}