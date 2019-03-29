import javax.swing.*;
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
        int xcoord = Integer.parseInt(ruudu_pos[1])-1;
        int ycoord = Integer.parseInt(ruudu_pos[0])-1;
        if (märgista) {
            märgistaRuut(new int[]{xcoord,ycoord});
        } else {
            uuriRuutu(new int[]{xcoord,ycoord});
        }
    }

    private int uuriRuutu(int[] ruut) {
        int xcoord = ruut[0];
        int ycoord = ruut[1];
        if(xcoord >= 0 && xcoord < mängujärg.length && ycoord >= 0 && ycoord < mängujärg[0].length && mängujärg[xcoord][ycoord].equals("?")) {
            int tulemus = mänguväli.getMiinid()[xcoord][ycoord];
            mängujärg[xcoord][ycoord] = Integer.toString(tulemus);
            if (tulemus == 0) {
                uuriRuutu(new int[]{xcoord+1,ycoord});
                uuriRuutu(new int[]{xcoord-1,ycoord});
                uuriRuutu(new int[]{xcoord,ycoord+1});
                uuriRuutu(new int[]{xcoord,ycoord-1});
                uuriRuutu(new int[]{xcoord+1,ycoord+1});
                uuriRuutu(new int[]{xcoord-1,ycoord-1});
                uuriRuutu(new int[]{xcoord-1,ycoord+1});
                uuriRuutu(new int[]{xcoord+1,ycoord-1});
            }
            return tulemus;
        }
        return -2;
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
                String väärtus = mängujärg[i][j];
                if (mängujärg[i][j].equals("0")) {
                    väärtus = ".";
                }
                ret.append(String.format("%4s",väärtus));
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    public void mängi() {
        mängujärg = new String[mänguväli.getRead()][mänguväli.getVeerud()];
        for (String[] i : mängujärg) {
            for (int j = 0; j < mängujärg[0].length; j++) {
                i[j] = "?";
            }
        }
        while(true) {
            System.out.println(toString());
            String sisestatakse = JOptionPane.showInputDialog("Sisestage x ja y kordinaat kujul 'x,y': ");
            mängijaValib(sisestatakse);
        }
        //return false;
    }
}

class test {
    public static void main(String[] args) {
        Miiniväli m = new Miiniväli(16,16,10);
        Mäng mäng = new Mäng(m);
        mäng.mängi();
    }
}