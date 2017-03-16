# Using the Graphics Engine
	Add the included jar to your projects workspace. This allows you to use DGraphics and GraphicsInterface. DGraphics handles all graphics directly using Java's built in Graphics2D. It creates a fullscreen window with a 16:9 aspect ratio and adds black bars accordingly. All objects are rendered on a 1920x1080 surface and then scaled to your monitor's resolution. DGraphics calls the render() method in GraphicsInterface at about 60 hertz.

## Example
```java
import graphics.DGraphics;
import graphics.GraphicsInterface;

import java.awt.Color;

public class GraphicsTest implements GraphicsInterface {
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
```

