
package components;

import javafx.geometry.Dimension2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Tato třída uchovává předvolby animačního panelu.
 * @author Viktor
 */
public class AnimationSettings {

    public int nodeWidth;
    public int nodeHeight;
    public int durationMs;
    public Color backgroundColor;
    public Color foregroundColor;
    public Color successColor;
    public Color failColor;
    public Color highlightColor;
    public DoubleLayerPane drawPane;
	public Pane pane;
    public Dimension2D bounds;
     
    /*
    V konstruktoru se inicializují implicitní hodnoty jednotlivých položek nastavení
    */
    public AnimationSettings() {
        nodeHeight = 20;
        nodeWidth = 50;
        durationMs = 200;
        backgroundColor = Color.YELLOW;
        foregroundColor = Color.BLACK;
        successColor = Color.GREEN;
        failColor = Color.RED;
        highlightColor = Color.ROYALBLUE;
        drawPane = new DoubleLayerPane();
        //flatPane = new Pane();
        bounds = new Dimension2D(1000, 400);
    }
    
    
    
}
