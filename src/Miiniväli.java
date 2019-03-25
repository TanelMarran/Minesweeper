import java.util.HashSet;
import java.util.Set;

public class Miiniväli {
    private int read;
    private int veerud;
    private int miinid_arv;
    private String[][] bommid;

    public Miiniväli(int read, int veerud, int miinid_arv) {
        this.read = read;
        this.veerud = veerud;
        this.miinid_arv = miinid_arv;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getVeerud() {
        return veerud;
    }

    public void setVeerud(int veerud) {
        this.veerud = veerud;
    }

    public int getMiinid_arv() {
        return miinid_arv;
    }

    public String[][] getBommid() {
        return bommid;
    }

    public void setMiinid_arv(int miinid) {
        double valjade_arv = veerud*read;
        int juhuarv;
        Set<Integer> valitud_valjad = new HashSet<>();
        while (valitud_valjad.size() != miinid) {
            juhuarv = (int) (Math.floor(Math.random() * valjade_arv));
            valitud_valjad.add(juhuarv);
        }
        bommid = new String[read][veerud];
        for(int i = 0; i < read; i++) {
            for(int j = 0; j < veerud; j++) {
                bommid[i][j] = "0";
            }
        }
        for(Integer jarv : valitud_valjad) {
            bommid[(jarv-(jarv % veerud))/veerud][jarv % veerud] = "-1";
        }
        miinid_arv = miinid;
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
                ret.append(String.format("%4s",bommid[i][j]));
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
