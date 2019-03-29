import java.util.HashSet;
import java.util.Set;

public class Miiniväli {
    private int read;
    private int veerud;
    private int miinid_arv;
    private int[][] miinid;

    public Miiniväli(int read, int veerud, int miinid_arv) {
        this.read = read;
        this.veerud = veerud;
        this.miinid_arv = miinid_arv;
        setMiinid_arv(miinid_arv);
    }

    int getRead() {
        return read;
    }

    int getVeerud() {
        return veerud;
    }

    public int getMiinid_arv() {
        return miinid_arv;
    }

    int[][] getMiinid() {
        return miinid;
    }

    void setMiinid_arv(int miinid) {
        double valjade_arv = veerud*read;
        int juhuarv;
        Set<Integer> valitud_valjad = new HashSet<>();
        while (valitud_valjad.size() != miinid) {
            juhuarv = (int) (Math.floor(Math.random() * valjade_arv));
            valitud_valjad.add(juhuarv);
        }
        this.miinid = new int[read][veerud];
        for(int i = 0; i < read; i++) {
            for(int j = 0; j < veerud; j++) {
                this.miinid[i][j] = 0;
            }
        }
        for(Integer jarv : valitud_valjad) {
            int veerg = (jarv-(jarv % veerud))/veerud;
            int rida = jarv % veerud;
            this.miinid[veerg][rida] = -1;
            määraOht(veerg+1,rida);
            määraOht(veerg-1,rida);
            määraOht(veerg,rida+1);
            määraOht(veerg,rida-1);
            määraOht(veerg+1,rida+1);
            määraOht(veerg-1,rida-1);
            määraOht(veerg-1,rida+1);
            määraOht(veerg+1,rida-1);
        }
        miinid_arv = miinid;
    }

    private void määraOht(int veerg, int rida) {
        if(rida >= 0 && rida < miinid[0].length && veerg >= 0 && veerg < miinid.length && miinid[veerg][rida] != -1) {
            int uus = miinid[veerg][rida]+1;
            miinid[veerg][rida] = uus;
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("    ");
        for(int j = 0; j < veerud; j++) {
            ret.append(String.format("%3d.",j+1));
        }
        ret.append("\n");
        for(int i = 0; i < read; i++) {
            ret.append(String.format("%3d.",i+1));
            for(int j = 0; j < veerud; j++) {
                ret.append(String.format("%4s", miinid[i][j]));
            }
            ret.append("\n");
        }
        return ret.toString();
    }
}

class T {
    public static void main(String[] args) {
        Miiniväli m = new Miiniväli(3,5,3);
        m.setMiinid_arv(4);
        System.out.println(m.toString());
    }
}
