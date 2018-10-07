package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DGraphics implements Runnable {
	private GraphicsInterface gi;
	private ArrayList<GraphicsInterface> gis;
	
	public JFrame frame = new JFrame();
	public int width, height, nativeWidth, nativeHeight;
	public double scale;
	
	private int mode;
	
	private static final int SINGLE = 0;
	private static final int MULTI = 1;
	private static final int RECORD = 2;
	private boolean restart = false;
	
	public DGraphics(GraphicsInterface gi) {
		this.gi = gi;
		this.mode = SINGLE;
		this.createWindow();
		
	}
	
	public DGraphics(ArrayList<GraphicsInterface> gis) {
		this.gis = gis;
		this.mode = MULTI;
		this.createWindow();
		
	}
	
	public DGraphics(ArrayList<GraphicsInterface> gis, int frames, String path, int width, int height) throws IOException {
		this.gis = gis;
		this.mode = RECORD;
		this.startFrameEngine(frames, path, width, height);
	}
	
	private void createWindow() {
		nativeWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		nativeHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		double aspectRatio = (double)nativeWidth / (double)nativeHeight;
		if(aspectRatio < 1920d/1080d) {
			height = (int)((double)nativeWidth / (1920d/1080d));
			width = nativeWidth;
		} else if(aspectRatio > 1920d/1080d) {
			width = (int)((double)nativeHeight * (1920d/1080d));
			height = nativeHeight;
		} else if(aspectRatio == 1920d/1080d) {
			width = nativeWidth;
			height = nativeHeight;
		}
		
		System.out.println(width);
		System.out.println(height);
		
		scale = (double)width / 1920;
		
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(mode == SINGLE) {
			Panel panel = new Panel();
			frame.add(panel);
		} else if(mode == MULTI) {
			PanelMulti panel = new PanelMulti();
			frame.add(panel);
		} else if(mode == RECORD) {
			
		}
		
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		gd.setFullScreenWindow(frame);
		frame.setVisible(true);

		new Thread(() -> {
			start();
		}).start();

	}
	
	public void startFrameEngine(int frames, String path, int width, int height) throws IOException {
		for(int i = 0; i < frames; i++) {
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for(GraphicsInterface gi : gis) {
				gi.tick();
				gi.render(bi.createGraphics());
			}
			ImageIO.write(bi, "png", new File(path + Integer.toString(i) + ".png"));
			
		}
	}
	
	public void update(ArrayList<GraphicsInterface> gis) {
		restart = true;
		this.gis = gis;
		restart = false;
		
			start();
		
	}
	
	private synchronized void start() {
		if(mode == SINGLE) new Thread(this).start();
		else	 if(mode == MULTI) {
			for(GraphicsInterface gi : gis) {
					new Thread(() -> {
						int runState = -1;
						Method getRunState;

						boolean running = true;
						while(running && !restart) {
							try {
								getRunState = gi.getClass().getMethod("getRunState");
								runState = (int)getRunState.invoke(gi);
							} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								
							}
							
							if(runState == GraphicsThread.STOPPED) running = false;
							else if(runState == GraphicsThread.PAUSED);
							else if(runState == GraphicsThread.RUNNING || runState == -1) {
								gi.tick();
								render();
								
							}
							
							try {
								Thread.sleep(16);
								
							} catch(InterruptedException e) {
								
							}
							
						}
						
					}).start();
				
			}
			
		}
		
	}
	
	public void run() {
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(16);
				
			} catch(InterruptedException e) {
				
			}
		}
	}
	
	private class Panel extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			gi.render(g2d);
			
		}
		
	}
	
	private class PanelMulti extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			for(GraphicsInterface gi : gis) {
				gi.render(g2d);
			}
		}
	}
	
	public void render() {
		frame.repaint();
		
	}
	
	public void tick() {
		gi.tick();
		
	}
	
}
