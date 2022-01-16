package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;




/** Inventory Management System by Nicole Mau.
 * C:\Program Files\C482 Nicole Mau\javadoc
 *
 */
/** *<p><b>* FUTURE_ENHANCEMENT
 *</b></p>
 * I want to add images to each Part and Product along with specs.
 * I also want to add dates of addition/modification to make sure pricing stays relevant in changing markets.
 *
 */

 /**<p><b> RUNTIME_ERROR
  </b></p>
  I was confused how to send the sending data across screens in Modify Part but I realized I needed to change the controller so
  the main screen table could talk to the modify part screen. I also was instructed to use selectedPart instanceof InHouse/OutSourced to properly
  set data from main screen to modify screen.

 <p><b>
  In the Modify Part and Modify Product forms I was attempting to use updatePart and updateProduct in the Inventory class.
  They were pushing errors because I never added the actual part or product. I instead went back to addPart and addProduct in the modify screens with success.
 </b></p>
  In the Modify Product form I had issues properly saving the associated parts to the bottom table. I realized I had a
  value pointing to the wrong getter. I also realized I had to add associated parts and then populate them into the lower table.
  I was only setting the text prior to fixing the issue.

 <p><b>
     Had issues adding proper error coding- fixed by adding new FXML doc and controller for alert window- watched the webinar and solved it.
</b></p>
  <p><b>
  After first submission of my PA I received feedback that I needed to add a custom exit button,  fix min>max validation check, and also not removing products that had associated parts attached.
  I added exit button, and worked on validations.
  </b></p>
 */

/**this is the Main Method.
 This is the first method that is called in our java program.**/

public class Main extends Application {

    @Override
/**this is the Main Method.
 This is the first method that is called in our java program.
 */
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 800,600));
        primaryStage.show();
    }


    public static void main(String[] args)

    {

        Inventory.addTestData();
        launch(args);
    }
/**this is the Main Method.
 This is the first method that is called in our java program.**/

}
