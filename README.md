# Using the Graphics Engine
Add the included jar to your projects workspace. This allows you to use DGraphics and GraphicsInterface. DGraphics handles all graphics directly using Java's built in Graphics2D. It creates a fullscreen window with a 16:9 aspect ratio and adds black bars accordingly. All objects are rendered on a 1920x1080 surface and then scaled to your monitor's resolution. DGraphics calls the `tick()` and `render()` methods in GraphicsInterface at about 60 hertz.

## Example
```java
import graphics.DGraphics;
import graphics.GraphicsInterface;

import java.awt.Color;

public class GraphicsTest implements GraphicsInterface {
	public static DGraphics g = new DGraphics(new GraphicsTest());
	public static int  a = 0;
	public static void main(String[] args) {

	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(a, a, 20, 20);

	}

	@Override
	public void tick() {
		a++;
		
	}
	
	
}
```

