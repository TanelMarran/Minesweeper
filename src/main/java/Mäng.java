import javax.swing.*;
import java.io.*;
import java.util.Arrays;

public class Mäng {
    private int käikude_arv = -1; //-1 tähendab, et käike on lõpmatult (käikude arv minesweeperis on tegelikult mõtetud? pigem siis määrata aega)
    private Miiniväli mänguväli;
    private String[][] mängujärg;
    private int arvatud_miinide_arv = 0;
    private int märgistatud_miinide_arv = 0;
    private int võidetud = 0;

    public void salvestaMäng(String nimi) throws IOException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nimi))) {
            int[] üksikandmed = new int[]{mänguväli.getRead(),mänguväli.getVeerud(),mänguväli.getMiinid_arv(),arvatud_miinide_arv,märgistatud_miinide_arv};
            for(int arv : üksikandmed) {
                bufferedWriter.write(Integer.toString(arv));
                bufferedWriter.write(",");
            }
            bufferedWriter.newLine();
            for(int i = 0; i < üksikandmed[0]; i++) {
                for(int j = 0; j < üksikandmed[1]; j++) {
                    bufferedWriter.write(Integer.toString(mänguväli.getMiinid()[i][j]).charAt(0));
                }
                bufferedWriter.newLine();
            }
            for(int i = 0; i < üksikandmed[0]; i++) {
                for(int j = 0; j < üksikandmed[1]; j++) {
                    bufferedWriter.write(mängujärg[i][j]);
                }
                bufferedWriter.newLine();
            }
        }
    }

    public void laeMäng(File fail) throws IOException {
        new DataInputStream(new FileInputStream("tere.txt")).
    }

    public int getVõidetud() {
        return võidetud;
    }

    public void setVõidetud(int võidetud) {
        this.võidetud = võidetud;
    }

    // konstruktor Mäng
    // Sisend: miiniväli
    // Eesmärk: luua piiramatu arvu käikudega mäng

    public Mäng(Miiniväli mänguväli) {
        this.mänguväli = mänguväli;
        mängujärg = new String[mänguväli.getRead()][mänguväli.getVeerud()];
        for (String[] i : mängujärg) {
            for (int j = 0; j < mängujärg[0].length; j++) {
                i[j] = "?";
            }
        }
    }

    // konstruktor Mäng (hetkel implementeerimata)
    // Sisend: miiniväli ja käikude arv
    // Eesmärk: luua etteantud käikude arvu käikudega mäng

    public Mäng(Miiniväli mänguväli, int käikude_arv) {
        this.käikude_arv = käikude_arv;
        this.mänguväli = mänguväli;
    }

    public String[][] getMängujärg() {
        return mängujärg;
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

    // meetod mängijaValib(String ruut)
    // Sisend: kasutaja tekstisisend
    // Väljund: mängu võiduväärtus (-1, 0, või 1)
    // Eesmärk: sõltuvalt sisendist antud koordinaatidega ruut märgistada või seda uurida ning tagastada mängu seis

    public int mängijaValib(String ruut) {
        boolean märgista = (ruut.charAt(0) == 'f');
        if (märgista) {
            ruut = ruut.substring(1);
        }
        String[] ruudu_pos = ruut.split(",");
        System.out.println(Arrays.toString(ruudu_pos));
        int xcoord = Integer.parseInt(ruudu_pos[1])-1;
        int ycoord = Integer.parseInt(ruudu_pos[0])-1;

        //käivitada vastavalt sisendile kas märgistaRuut või uuriRuutu, tagastada mängu seis
        if (märgista) {
            return (märgistaRuut(new int[]{xcoord,ycoord}) ? 1 : 0);
        } else {
            return (uuriRuutu(new int[]{xcoord,ycoord}) ? -1 : 0);
        }
    }

    // meetod uuriRuutu(int[] ruut)
    // Sisend: X- ja Y-koordinaadid
    // Väljund: mängu jätkumise staatus:
    //          false, kui mäng jätkub (st, ei kaotatud),
    //          true, kui mäng ei jätku (st, kaotati)
    // Eesmärk: miini olemasolu antud ruudul mäng lõpetada, selle puudumisel uurida ruudu ümbrust

    private boolean uuriRuutu(int[] ruut) {
        int xcoord = ruut[0];
        int ycoord = ruut[1];

        //käivita edasine juhul, kui pakkumine asub platsil ja on veel avamata
        if(xcoord >= 0 && xcoord < mängujärg.length && ycoord >= 0 && ycoord < mängujärg[0].length && mängujärg[xcoord][ycoord].equals("?")) {
            int tulemus = mänguväli.getMiinid()[xcoord][ycoord];
            mängujärg[xcoord][ycoord] = Integer.toString(tulemus);
            
            //kui ruut oli tühi, uurida ka kõrvalolevaid (platsi avamine)
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

            //tagasta tõene, kui ruudu all on miin
            return (tulemus == -1);
        }
        return false;
    }

    // meetod märgistaRuut(int[] ruut)
    // Sisend: X- ja Y-koordinaadid
    // Väljund: mängu jätkumise staatus:
    //          false, kui mäng jätkub (st, ei võidetud),
    //          true, kui mäng ei jätku (st, võideti)
    // Eesmärk: märgistada etteantud ruut, ning õigete märgistuste korral lõpetada mäng

    private boolean märgistaRuut(int[] ruut) {
        int xcoord = ruut[0];
        int ycoord = ruut[1];

        //lipu lisamine või selle eemaldamine
        if ((mängujärg[xcoord][ycoord].equals("f") || mängujärg[xcoord][ycoord].equals("?"))) {
            boolean õigevalitu = false;
            if (mänguväli.getMiinid()[xcoord][ycoord] == -1) {
                õigevalitu = true;
            }
            if (mängujärg[xcoord][ycoord].equals("f")) {
                if (õigevalitu) {
                    arvatud_miinide_arv--;
                }
                mängujärg[xcoord][ycoord] = "?";
                märgistatud_miinide_arv--;
            } else  if (märgistatud_miinide_arv < mänguväli.getMiinid_arv()){
                if (õigevalitu) {
                    arvatud_miinide_arv++;
                }
                mängujärg[xcoord][ycoord] = "f";
                märgistatud_miinide_arv++;
            }
        }

        //kui kõik miinid on märgitud, tagasta true
        if (arvatud_miinide_arv == mänguväli.getMiinid_arv()) {
            return true;
        }
        return false;
    }

    //platsi kuvamine
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

    // meetod mängi()
    // Sisend: puudub
    // Väljund: puudub
    // Eesmärk: mängu teostav meetod, mis küsib sisendit kuni mängu lõpuni

    public void taasalusta() {
        arvatud_miinide_arv = 0;
        märgistatud_miinide_arv = 0;
        võidetud = 0;
        mängujärg = new String[mänguväli.getRead()][mänguväli.getVeerud()];

        //kuva tühi plats
        for (String[] i : mängujärg) {
            for (int j = 0; j < mängujärg[0].length; j++) {
                i[j] = "?";
            }
        }
    }

    public void mängi() {
        //arvatud_miinide_arv = 0;
        //märgistatud_miinide_arv = 0;
        int võidetud = 0;
        mängujärg = new String[mänguväli.getRead()][mänguväli.getVeerud()];

        //kuva tühi plats
        for (String[] i : mängujärg) {
            for (int j = 0; j < mängujärg[0].length; j++) {
                i[j] = "?";
            }
        }

        //kuni mäng käib, küsi sisendit
        while (võidetud == 0) {
            System.out.println(toString());
            String sisestatakse = JOptionPane.showInputDialog("Pakkumiseks sisestage x ja y koordinaadid kujul 'x,y'    Lipuga märkimiseks sisestage koordinaadid kujul 'fx,y'");
            võidetud = mängijaValib(sisestatakse);
        }
        System.out.println(toString());

        //väljasta lõpptulemus
        switch (võidetud) {
            case -1:
                System.out.println("Oled kaotaja!");
                break;
            case 1:
                System.out.println("Oled võitja!");
                break;
        }
    }
}

class test {
    public static void main(String[] args) throws IOException {
        Miiniväli m = new Miiniväli(8,8,3);
        System.out.println(m.toString());
        Mäng mäng = new Mäng(m);
        mäng.salvestaMäng("salvestus.txt");
        mäng.mängi();
    }
}