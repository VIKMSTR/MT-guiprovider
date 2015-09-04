package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import components.AnimationSettings;
import components.CommonControls;


public class Main extends Application {
	AnimationSettings as;
	CommonControls cc;

	int width = 1000;
	int height = 500;

	@Override
	public void start(Stage primaryStage) {
		try {
			as = new AnimationSettings();
			Pane canvas = new Pane();
			as.pane = canvas;
			cc = new CommonControls(true, false);
			Scene s = new Scene(cc.arrangeScene(as), width, height);

			canvas.setPrefSize(4200, height);
			primaryStage.setTitle("GUI Example");
			primaryStage.setScene(s);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
