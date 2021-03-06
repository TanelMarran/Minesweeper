import java.util.HashSet;
import java.util.Set;

public class Miiniväli {
    private int read;
    private int veerud;
    private int miinid_arv;
    private int[][] miinid;

    // konstruktor Miiniväli
    // Sisend: ridade, veergude ja miinide arv
    // Eesmärk: luua etteantud näitajatega miiniväli

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

    public void setMiinid(int[][] miinid) {
        this.miinid = miinid;
    }

    // meetod setMiinid_arv(int miinid)
    // Sisend: miinide arv
    // Väljund: puudub
    // Eesmärk: genereerida etteantud arv juhuslikus asukohas miine,
    //          nende asukohas salvestada ning ümbritsevate ruutude loendurit suurendada

    void setMiinid_arv(int miinid) {
        double valjade_arv = veerud*read;
        int juhuarv;
        Set<Integer> valitud_valjad = new HashSet<>();
        
        //genereeri juhuslikud miinide asukohad
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

        //muuda miini all olevat väärtust ja suurenda ümbritsevate ruutude loendurit ühe võrra
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

    // meetod määraOht(int veerg, int rida)
    // Sisend: X- ja Y-koordinaat
    // Väljund: puudub
    // Eesmärk: suurendada etteantud ruudu loendurit ühe võrra

    private void määraOht(int veerg, int rida) {
        if(rida >= 0 && rida < miinid[0].length && veerg >= 0 && veerg < miinid.length && miinid[veerg][rida] != -1) {
            int uus = miinid[veerg][rida]+1;
            miinid[veerg][rida] = uus;
        }
    }

    //platsi kuvamine
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
        System.out.println(m.toString());
    }
}
