package graphics;

import java.awt.Graphics2D;

public abstract class GraphicsThread implements GraphicsInterface {
	public static final int PAUSED = 0;
	public static final int RUNNING = 1;
	public static final int STOPPED = 3;
	private int runState = PAUSED;

	@Override
	public abstract void render(Graphics2D g2d);

	@Override
	public abstract void tick();
	
	public int getRunState() {
		return runState;
		
	}
	
	public void setRunState(int runState) {
		this.runState = runState;
		
	}

}
