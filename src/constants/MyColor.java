/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Farbe die im Spiel verwendet werden.
 */
package constants;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

public enum MyColor {
    @JsonProperty("GELB") MY_GELB(new Color(255, 210, 0)),
    @JsonProperty("GRUEN") MY_GRUEN(new Color(114, 185, 4)),
    @JsonProperty("ORANGE") MY_ORANGE(new Color(239, 135, 0)),
    @JsonProperty("BLAU") MY_BLAU(new Color(0, 126, 185)),
    @JsonProperty("LILA") MY_LILA(new Color(155, 0, 107));
    private final Color COLOR;

    MyColor(Color color) {
        this.COLOR = color;
    }

    public Color getCOLOR() {
        return COLOR;
    }
}
