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

    private String uuriRuutu(String ruut) {
        String[] ruudu_pos = ruut.split(",");
        int rida = Integer.parseInt(ruudu_pos[0]);
        int veerg = Integer.parseInt(ruudu_pos[1]);
        mängujärg[rida][veerg] = mänguväli.getBommid()[rida][veerg];
        return mänguväli.getBommid()[rida][veerg];
    }

    private void märgistaRuut(String ruut) {

    }

    public boolean Mängi() {
        return false;
    }
}
