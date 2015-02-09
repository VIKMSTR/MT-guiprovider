
package components;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/** Tento panel obsahuje dvě vrstvy - obecně využito k oddělení uzlů a spojnic.
 *
 * @author Viktor
 */
public class DoubleLayerPane {

private StackPane sp;    
private Pane topLayer; 
private Pane bottomLayer; 


public DoubleLayerPane(){
    sp = new StackPane();
    bottomLayer = new Pane();
    sp.getChildren().add(bottomLayer);
    topLayer = new Pane();
    topLayer.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0)");
    sp.getChildren().add(topLayer);
}



/***
 * Získání celého dvouvrstvého panelu
 * @return panel ke vložení do scény
 */
public StackPane getDLPane(){
    return sp;
}
/***
 * Zpřístupnění horní vrstvy panelu
 * @return horní vrstva panelu
 */
public Pane topLayer(){
    return topLayer;
}
/***
 * Zpřístupnění spodní vrstvy panelu
 * @return spodní vrstva panelu
 */
public Pane bottomLayer(){
    return bottomLayer;
}
}
