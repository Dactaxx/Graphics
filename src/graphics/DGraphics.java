package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DGraphics implements Runnable {
	public Graphics2D g2d;
	private GraphicsInterface gi;
	public JFrame frame = new JFrame();
	public int width, height, nativeWidth, nativeHeight;
	public double scale;
	
	public DGraphics(GraphicsInterface gi) {
		this.gi = gi;
		this.createWindow();
		
	}
	
	public void createWindow() {
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
		
		Panel panel = new Panel();
		frame.add(panel);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		gd.setFullScreenWindow(frame);
		frame.setVisible(true);

		start();
		
	}
	
	private synchronized void start() {
		new Thread(this).start();
		
	}
	
	public void run() {
		while(true) {
			frame.repaint();		
			try {
				Thread.sleep(16);
				
			} catch(InterruptedException e) {
				
			}
		}
	}
	
	private class Panel extends JPanel {
		public void paintComponent(Graphics g) {
			g2d = (Graphics2D)g;
			g2d.setColor(new Color(0, 0, 0));
			tick();
			render();
			
		}
		
	}
	
	public void render() {
		gi.render(this.g2d);
		
	}
	
	public void tick() {
		
	}
	
}
