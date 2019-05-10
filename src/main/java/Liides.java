import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Liides extends Application {
    private ArrayList<ArrayList<Button>> r_matrix = new ArrayList<>();

    private int read = 10;
    private int veerud = 10;
    private int miinide_arv = 9;
    private Miiniväli miiniväli = new Miiniväli(read,veerud,9);
    private Mäng mäng = new Mäng(miiniväli);
    private boolean märgista_lipuga = false;

    private Group juur = new Group();
    private Button lipp = new Button("Uuri");
    private MenuButton failimenüü = new MenuButton("Mäng");
    private GridPane mänguväli = new GridPane();
    private Stage peaLava;

    @Override
    public void start(Stage peaLava) throws Exception {
        mänguväli.setGridLinesVisible(true);
        this.peaLava = peaLava;
        Scene scene = new Scene(juur);

        taasalusta();
        juur.getChildren().addAll(mänguväli,lipp,failimenüü);


        MenuItem mb_uusmäng = new MenuItem("Uus Mäng");
        mb_uusmäng.setOnAction(event -> uusMängAken());

        MenuItem mb_salvesta = new MenuItem("Salvesta");
        mb_salvesta.setOnAction(event -> salvestaMäng());

        MenuItem mb_lae = new MenuItem("Lae");
        mb_lae.setOnAction(event -> laeMäng());


        failimenüü.getItems().addAll(mb_uusmäng, mb_salvesta, mb_lae);
        peaLava.setTitle("Minesweeper");
        peaLava.setScene(scene);
        peaLava.show();
        uuendaAknaSuurus();

        mänguväli.setTranslateY(80);
        failimenüü.setTranslateY(40);
        lipp.setOnMouseClicked(event -> lipp_vajutatud());
    }

    private void uuendaAknaSuurus() {
        peaLava.setWidth(mänguväli.getColumnCount()*30+15.2);
        peaLava.setHeight(mänguväli.getRowCount()*30+80+39.2);
        for(Control c : new Control[]{lipp,failimenüü}) {
            c.setTranslateX((peaLava.getWidth()-c.getWidth())/2);
        }
    }

    private void uusMängAken() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        TextField readT = new TextField();
        TextField veerudT = new TextField();
        TextField miinide_arvT = new TextField();
        VBox hBox = new VBox(new Text("Read: "),readT);
        VBox hBox1 = new VBox(new Text("Veerud: "),veerudT);
        VBox hBox2 = new VBox(new Text("Miinide arv: "),miinide_arvT);
        for(TextField t : new TextField[]{readT,veerudT,miinide_arvT}) {
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
        Button button = new Button("Alusta");
        button.setOnMouseClicked(event1 -> {
            stage.close();
            tühjenda();
            read = Integer.parseInt(readT.getText());
            veerud = Integer.parseInt(veerudT.getText());
            miinide_arv = Math.min(Integer.parseInt(miinide_arvT.getText()),read*veerud-1);
            taasalusta();
        });

        VBox vBox = new VBox(2,hBox,hBox1,hBox2,button);
        Scene s1 = new Scene(new Group(vBox));
        stage.setScene(s1);
        stage.showAndWait();
    }

    private void salvestaMäng() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Valige sobilik .txt fail");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        String nimi = "";
        try {
            nimi = fileChooser.showSaveDialog(peaLava).getAbsolutePath();
        } catch (NullPointerException ignored) {}

        if (!nimi.equals("")) {
            try {
                mäng.salvestaMäng(nimi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void laeMäng() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Valige sobilik .txt fail");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        String nimi = "";
        try {
            nimi = fileChooser.showOpenDialog(peaLava).getAbsolutePath();
        } catch (NullPointerException ignored) {}

        if (!nimi.equals("")) {
            try {
                tühjenda();
                mäng.laeMäng(nimi);
                read = mäng.getMänguväli().getRead();
                veerud = mäng.getMänguväli().getVeerud();
                miinide_arv = mäng.getMänguväli().getMiinid_arv();
                taasalusta();
                mäng.laeMäng(nimi);
                uuendamänguväli();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void lipp_vajutatud() {
        märgista_lipuga = !märgista_lipuga;
        if(märgista_lipuga) {
            lipp.setText("Märgista");
        } else {
            lipp.setText("Uuri");
        }
    }
    private void tühjenda() {
        for(int i = 0; i < read; i++) {
            for (int j = 0; j < veerud; j++) {
                mänguväli.getChildren().remove(r_matrix.get(i).get(j));
            }
        }
        r_matrix = new ArrayList<>();
    }

    private void taasalusta() {
        miiniväli = new Miiniväli(read,veerud,miinide_arv);
        mäng = new Mäng(miiniväli);
        mäng.taasalusta();
        for(int i = 0; i < read; i++) {
            r_matrix.add(new ArrayList<>());
            for(int j = 0; j < veerud;j++) {
                r_matrix.get(i).add(new Button(mäng.getMängujärg()[i][j]));
                r_matrix.get(i).get(j).setMinWidth(30);
                r_matrix.get(i).get(j).setMinHeight(30);
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
                        tühjenda();
                        taasalusta();
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
        uuendaAknaSuurus();
    }

}
