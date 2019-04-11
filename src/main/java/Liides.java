import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Liides extends Application {
    @Override
    public void start(Stage peaLava) throws Exception {
        Group juur = new Group();
        Rectangle rectangle = new Rectangle(300,300, Color.RED);
        juur.getChildren().addAll(rectangle);

        Scene scene = new Scene(juur);
        peaLava.setTitle("Minesweeper");
        peaLava.setScene(scene);
        peaLava.show();
    }
}
