package cc.taketo.entity;

import lombok.Data;

import java.awt.*;

/**
 * @author Zhangp
 * @date 2024/4/25 10:39
 */
@Data
public class DrawText {

    private String text;

    private int[] xy;

    private String font;

    private Integer style;

    private Color colour;

    private int size;

    public DrawText(String text, int[] xy, String font, Integer style, Color colour, int size) {
        this.text = text;
        this.xy = xy;
        this.font = font == null ? "SimSun" : font;
        this.style = font == null ? Font.PLAIN : style;
        this.colour = colour == null ? Color.BLACK : colour;
        this.size = size;
    }
}
