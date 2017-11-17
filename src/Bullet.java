import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class Bullet extends Entity
{
	private Vector2f targetPos;
	private Vector2f originalPos;
	private float rise;
	private float run;
	
	public Bullet(Game g, GameContainer gc, Vector2f v, Vector2f v2, String ref, String name) throws SlickException 
	{
		super(g, gc, v, ref, name);
		targetPos = v2;
		originalPos = v;
		run = targetPos.getX() - originalPos.getX(); 
		rise = targetPos.getY() - originalPos.getY();
	}

	@Override
	public void onInit() 
	{
		
	}
	
	int timer = 0;
	
	@Override
	public void onTick() 
	{		
		Vector2f f = new Vector2f(run, rise);
		f.normalise();
		f.set(new Vector2f(f.getX()*4f, f.getY()*4f));
		
		setPos(new Vector2f(getPos().getX() + f.getX(), getPos().getY() + f.getY()));
	}

	int counter = 0;
	
	@Override
	public void onRender() 
	{
		counter++;
		if(counter == 360)
			counter = 0;
		sprite.setRotation((float)counter);
		sprite.draw(getPos().getX(), getPos().getY());
	}

	@Override
	public void mousePressed(int button, int x, int y) 
	{
		
	}

	@Override
	public void keyPressed(int key, char c) 
	{
		
	}
	
	public void onOutOfBounds(Vector2f o, Vector2f n)
	{
		System.out.println("bullet is kill");
		
		try { kill(); } 
		catch (SlickException e) {}
	}
}
