package graphics;

import java.awt.Graphics2D;

public interface GraphicsInterface {
	public void render(Graphics2D g2d);
	public void tick();
	public String id = "default";
}
