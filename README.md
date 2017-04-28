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

# Multithreading
This graphics library has simple multithreading support. Simply declare a `GraphicsInterface` inline and put it in an `ArrayList<GraphicsInterface>` then put that array list in the `DGraphics` constructor.

## Example
```java
package graphicstest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import graphics.DGraphics;
import graphics.GraphicsInterface;

public class GraphicsTest {
	public static DGraphics g;
	public static int a = 0;
	public static ArrayList<GraphicsInterface> gis = new ArrayList<GraphicsInterface>();
	public static void main(String[] args) {
		gis.add(new GraphicsInterface() {
			@Override
			public void render(Graphics2D g2d) {
				g2d.setColor(new Color(0, 0, 0));
				g2d.fillRect(a, a, 20, 20);
				
			}

			@Override
			public void tick() {
				a++;
				
			}
			
		});
		
		gis.add(new GraphicsInterface() {
			@Override
			public void render(Graphics2D g2d) {
				g2d.setColor(new Color(255, 0, 0));
				g2d.fillRect(a, 1080 - a, 20, 20);
				
			}

			@Override
			public void tick() {
				a++;
				
			}
			
		});
		
		g = new DGraphics(gis);
		
	}

}
```

