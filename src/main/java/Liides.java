import com.sun.javafx.scene.control.IntegerField;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Liides extends Application {
    private ArrayList<ArrayList<Button>> r_matrix = new ArrayList<>();
    private int read = 10;
    private int veerud = 10;
    private Miiniväli miiniväli = new Miiniväli(read,veerud,9);
    private Mäng mäng = new Mäng(miiniväli);
    private boolean märgista_lipuga = false;

    private Group juur = new Group();
    private Button lipp = new Button("Uuri");
    private MenuButton failimenüü = new MenuButton("Mäng");
    private GridPane mänguväli = new GridPane();

    @Override
    public void start(Stage peaLava) throws Exception {
        mänguväli.setGridLinesVisible(true);

        taasalusta(true);
        juur.getChildren().addAll(mänguväli,lipp,failimenüü);

        MenuItem mb_uusmäng = new MenuItem("Uus Mäng");
        MenuItem mb_salvesta = new MenuItem("Salvesta");
        MenuItem mb_lae = new MenuItem("Lae");
        failimenüü.getItems().addAll(mb_uusmäng, mb_salvesta, mb_lae);
        mb_uusmäng.setOnAction(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            TextField textField = new TextField();
            TextField textField2 = new TextField();
            for(TextField t : new TextField[]{textField,textField2}) {
                t.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            t.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
            }
            VBox vBox = new VBox(2,textField,textField2);
            Scene s1 = new Scene(new Group(vBox));
            stage.setScene(s1);
            stage.showAndWait();
        });

        Scene scene = new Scene(juur);
        peaLava.setTitle("Minesweeper");
        peaLava.setScene(scene);
        peaLava.show();

        mänguväli.setTranslateY(80);
        failimenüü.setTranslateY(40);
        for(Control c : new Control[]{lipp,failimenüü}) {
            c.setTranslateX((peaLava.getWidth()-c.getWidth())/2);
        }
        lipp.setOnMouseClicked(event -> lipp_vajutatud());
    }

    private void lipp_vajutatud() {
        märgista_lipuga = !märgista_lipuga;
        if(märgista_lipuga) {
            lipp.setText("Märgista");
        } else {
            lipp.setText("Uuri");
        }
    }
    private void taasalusta(boolean esimene) {
        if(!esimene) {
            for(int i = 0; i < read; i++) {
                for (int j = 0; j < veerud; j++) {
                    mänguväli.getChildren().remove(r_matrix.get(i).get(j));
                }
                r_matrix.get(i).clear();
            }
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
                    if(märgista_lipuga) {
                        mäng.setVõidetud(mäng.mängijaValib("f" + (finalJ + 1) + "," + (finalI + 1)));
                    } else {
                        mäng.setVõidetud(mäng.mängijaValib((finalJ + 1) + "," + (finalI + 1)));
                    }
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
                if (väärtus.equals("f")) {
                    r_matrix.get(i).get(j).setDisable(false);
                }
            }
        }
    }

}
