import java.util.Arrays;

public class Mäng {
    private int käikude_arv = -1; //-1 tähendab, et käike on lõpmatult
    private Miiniväli mänguväli;
    private String[][] mängujärg;

    public Mäng(Miiniväli mänguväli) {
        this.mänguväli = mänguväli;
    }

    public Mäng(int käikude_arv, Miiniväli mänguväli) {
        this.käikude_arv = käikude_arv;
        this.mänguväli = mänguväli;
    }

    public int getKäikude_arv() {
        return käikude_arv;
    }

    public void setKäikude_arv(int käikude_arv) {
        this.käikude_arv = käikude_arv;
    }

    public Miiniväli getMänguväli() {
        return mänguväli;
    }

    public void setMänguväli(Miiniväli mänguväli) {
        this.mänguväli = mänguväli;
    }

    private void mängijaValib(String ruut) {
        boolean märgista = (ruut.charAt(0) == 'f');
        if (märgista) {
            ruut = ruut.substring(1);
        }
        String[] ruudu_pos = ruut.split(",");
        System.out.println(Arrays.toString(ruudu_pos));
        int rida = Integer.parseInt(ruudu_pos[1]);
        int veerg = Integer.parseInt(ruudu_pos[0]);
        if (märgista) {
            märgistaRuut(new int[]{rida,veerg});
        } else {
            uuriRuutu(new int[]{rida,veerg});
        }
    }

    private String uuriRuutu(int[] ruut) {
        int rida = ruut[0];
        int veerg = ruut[1];
        if(rida >= 0 && rida < mängujärg.length && veerg >= 0 && veerg < mängujärg[0].length && mängujärg[rida][veerg].equals("?")) {
            String tulemus = mänguväli.getBommid()[rida][veerg];
            mängujärg[rida][veerg] = tulemus;
            if (tulemus.equals("0")) {
                uuriRuutu(new int[]{rida+1,veerg});
                uuriRuutu(new int[]{rida-1,veerg});
                uuriRuutu(new int[]{rida,veerg+1});
                uuriRuutu(new int[]{rida,veerg-1});
            }
            return tulemus;
        }
        return "-2";
    }

    private void märgistaRuut(int[] ruut) {
        int rida = ruut[0];
        int veerg = ruut[1];
        mängujärg[rida][veerg] = "f";
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("    ");
        for(int j = 0; j < mänguväli.getVeerud(); j++) {
            ret.append(String.format("%3d.",j+1));
        }
        ret.append("\n");
        for(int i = 0; i < mänguväli.getRead(); i++) {
            ret.append(String.format("%3d.",i+1));
            for(int j = 0; j < mänguväli.getVeerud(); j++) {
                ret.append(String.format("%4s",mängujärg[i][j]));
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    public boolean mängi() {
        mängujärg = new String[mänguväli.getRead()][mänguväli.getVeerud()];
        for(String[] i : mängujärg) {
            for(int j = 0; j < mängujärg[0].length; j++) {
                i[j] = "?";
            }
        }
        mängijaValib("2,5");
        System.out.println(toString());
        return false;
    }
}

class test {
    public static void main(String[] args) {
        Miiniväli m = new Miiniväli(7,11,20);
        Mäng mäng = new Mäng(m);
        mäng.mängi();
    }
}