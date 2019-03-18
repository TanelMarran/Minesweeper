import java.util.HashSet;
import java.util.Set;

public class Miinivali {
    private int read;
    private int veerud;
    private int miinid_arv;
    private int[][] bommid;

    public Miinivali(int read, int veerud, int miinid_arv) {
        this.read = read;
        this.veerud = veerud;
        this.miinid_arv = miinid_arv;
    }

    public void uuendaValiMiinidega(int miinid) {
        double valjade_arv = veerud+read;
        int juhuarv;
        Set<Integer> valitud_valjad = new HashSet<>();
        while (valitud_valjad.size() != miinid) {
            juhuarv = (int) (Math.floor(Math.random() * valjade_arv));
            valitud_valjad.add(juhuarv);
        }
        bommid = new int[read][veerud];
        for(int i = 0; i < read; i++) {
            for(int j = 0; j < veerud; j++) {
                bommid[i][j] = 0;
            }
        }
        for(Integer jarv : valitud_valjad) {
            bommid[(jarv-(jarv % veerud))/veerud][jarv % veerud] = -1;
        }
    }

    public void prnt() {
        for(int i = 0; i < read; i++) {
            for(int j = 0; j < veerud; j++) {
                System.out.print(bommid[i][j]);
            }
            System.out.println();
        }
    }
}

class T {
    public static void main(String[] args) {
        Miinivali m = new Miinivali(3,5,3);
        m.uuendaValiMiinidega(4);
        m.prnt();
    }
}
