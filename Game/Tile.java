package sameGame;

import java.awt.Color;

import graphics.ColorDef;

public class Tile {

	public Color color;
	public int score;
	
	
	public Tile(Color color, int score) {
		this.color = color;
		this.score = score;
	}
	
	public boolean equals(Tile other) {
		if (other == null)
			return false;
		return this.color.equals(other.color);
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		if (this.color.equals(Color.decode(ColorDef.BLUE)))
			return "blue";
		if (this.color.equals(Color.decode(ColorDef.PURPLE)))
			return "purple";
		if (this.color.equals(Color.decode(ColorDef.YELLOW)))
			return "yellow";
		if (this.color.equals(Color.decode(ColorDef.GREEN)))
			return "green";
		return color.toString();
	}
}
