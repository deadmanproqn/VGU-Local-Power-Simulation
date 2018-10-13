package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

 
public class Run extends Application {
  
 
   @Override
   public void start(Stage primaryStage) {
       try {
           Parent root = FXMLLoader.load(getClass()
                   .getResource("/gui/InputData.fxml"));
 
           primaryStage.setTitle("My Application");
           primaryStage.setScene(new Scene(root));
           primaryStage.show();
        
       } catch(Exception e) {
           e.printStackTrace();
       }
   }
  
   public static void main(String[] args) {
       launch(args);
   }
  
}