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
    private int read = 5;
    private int veerud = 5;
    private Miiniväli miiniväli = new Miiniväli(read,veerud,7);
    private Mäng mäng = new Mäng(miiniväli);

    private Group juur = new Group();

    //Loo ruudustik ning peida see ära
    private GridPane mänguväli = new GridPane();

    @Override
    public void start(Stage peaLava) throws Exception {
        mänguväli.setGridLinesVisible(true);

        taasalusta();
        juur.getChildren().addAll(mänguväli);

        Scene scene = new Scene(juur);
        peaLava.setTitle("Minesweeper");
        peaLava.setScene(scene);
        peaLava.show();
    }

    private void taasalusta() {
        r_matrix.clear();
        mäng.taasalusta();
        for(int i = 0; i < read; i++) {
            r_matrix.add(new ArrayList<>());
            for(int j = 0; j < veerud;j++) {
                r_matrix.get(i).add(new Button(mäng.getMängujärg()[i][j]));
                r_matrix.get(i).get(j).setMinWidth(40);
                r_matrix.get(i).get(j).setMinHeight(40);
                mänguväli.add(r_matrix.get(i).get(j),i,j);
            }
        }
    }

}
