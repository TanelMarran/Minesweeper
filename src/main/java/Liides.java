import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class Liides extends Application {
    private ArrayList<ArrayList<Button>> r_matrix = new ArrayList<>();
    private int read = 10;
    private int veerud = 10;
    private Miiniväli miiniväli = new Miiniväli(read,veerud,9);
    private Mäng mäng = new Mäng(miiniväli);

    private Group juur = new Group();
    private GridPane mänguväli = new GridPane();

    @Override
    public void start(Stage peaLava) throws Exception {
        mänguväli.setGridLinesVisible(true);

        taasalusta(true);

        juur.getChildren().addAll(mänguväli);

        Scene scene = new Scene(juur);
        peaLava.setTitle("Minesweeper");
        peaLava.setScene(scene);
        peaLava.show();
    }

    private void taasalusta(boolean esimene) {
        if(!esimene) {
            for(int i = 0; i < read; i++) {
                for (int j = 0; j < veerud; j++) {
                    mänguväli.getChildren().remove(r_matrix.get(i).get(j));
                }
                r_matrix.get(i).clear();
            }
            read = (int)(Math.random()*10.0)+2;
            veerud = (int)(Math.random()*10.0)+2;
            miiniväli = new Miiniväli(read,veerud,9);
            mäng = new Mäng(miiniväli);
        }
        r_matrix.clear();
        mäng.taasalusta();
        for(int i = 0; i < read; i++) {
            r_matrix.add(new ArrayList<>());
            for(int j = 0; j < veerud;j++) {
                r_matrix.get(i).add(new Button(mäng.getMängujärg()[i][j]));
                r_matrix.get(i).get(j).setMinWidth(40);
                r_matrix.get(i).get(j).setMinHeight(40);
                int finalJ = j;
                int finalI = i;
                r_matrix.get(i).get(j).setOnMouseClicked(event -> {
                    mäng.setVõidetud(mäng.mängijaValib((finalJ+1) + "," + (finalI+1)));
                    uuendamänguväli();
                    if(mäng.getVõidetud() != 0) {
                        taasalusta(false);
                    }
                });
                mänguväli.add(r_matrix.get(i).get(j),i,j);
            }
        }
        uuendamänguväli();
    }

    private void uuendamänguväli() {
        //System.out.println(mäng.toString());
        for(int i = 0; i < read; i++) {
            for(int j = 0; j < veerud;j++) {
                String väärtus = mäng.getMängujärg()[i][j];
                if (väärtus.equals("0")) {
                    r_matrix.get(i).get(j).setText("");
                    r_matrix.get(i).get(j).setDisable(true);
                } else {
                    r_matrix.get(i).get(j).setText(väärtus);
                    r_matrix.get(i).get(j).setDisable(true);
                }
                if (väärtus.equals("?")) {
                    r_matrix.get(i).get(j).setText("");
                    r_matrix.get(i).get(j).setDisable(false);
                }
            }
        }
    }

}
