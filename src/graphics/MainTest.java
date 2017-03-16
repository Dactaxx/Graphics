package graphics;

import java.awt.Color;

public class MainTest implements GraphicsInterface {
	public static DGraphics g;
	public static void main(String[] args) {
		g = new DGraphics();
		g.createWindow(new MainTest());

		
	}

	@Override
	public void render() {
		g.g2d.setColor(new Color(0, 255, 0));
		g.g2d.drawLine(0, 0, 500, 500);
		g.g2d.setColor(new Color(0, 0, 255));
		g.g2d.fillRect(500, 500, 50, 50);
	}
}
