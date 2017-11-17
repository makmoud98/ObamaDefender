import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class Player extends Entity
{
	private Vector2f targetPos;
	private Vector2f bulletSpawn;
	private Image hpBar;
	private float hp;
	
	public Player(Game g, GameContainer gc, Vector2f v, String ref, String name) throws SlickException 
	{
		super(g, gc, v, ref, name);
		targetPos = v;
		bulletSpawn = getCenter();
		input.enableKeyRepeat();
		hpBar = new Image("/res/Health.png");
		hp = 20;
	}

	@Override
	public void onInit() 
	{
		
	}
	
	boolean x = false;
	int timer = 0;
	
	@Override
	public void onTick() 
	{		
		float run = targetPos.getX() - getPos().getX(); 
		float rise = targetPos.getY() - getPos().getY();
		
		setPos(new Vector2f(getPos().getX() + (run/25f), getPos().getY() + (rise/25f)));
		
		move();
		
		timer++;
		
		if(timer >= 10)
		{
			shoot();
			timer = 0;
		}
	}

	@Override
	public void onRender() 
	{
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
		System.out.println("out of bounds!");
		
		float run = n.getX() - o.getX();
		float rise = n.getX() - o.getY();
		
		targetPos.set(targetPos.getX() + run, targetPos.getY() + rise);
	}
	
	public void move()
	{
		float TxOffset = 0;
		float TyOffset = 0;
		float xOffset = 0;
		float yOffset = 0;
		
		targetPos.set(getPos().getX(), getPos().getY());
		
		if(input.isKeyDown(Input.KEY_W))
		{
			TyOffset = -30;
			TxOffset = 0;
			if(!isOutOfBounds(new Rectangle(targetPos.getX() + TxOffset, targetPos.getY() + TyOffset, sprite.getWidth(), sprite.getHeight()), worldBounds))
				yOffset -= 30;
			else
			{
				System.out.println("top");
			}
		}
		if(input.isKeyDown(Input.KEY_A))
		{
			TxOffset = -30;
			TyOffset = 0;
			if(!isOutOfBounds(new Rectangle(targetPos.getX() + TxOffset, targetPos.getY() + TyOffset, sprite.getWidth(), sprite.getHeight()), worldBounds))
				xOffset -= 30;
			else
			{
				System.out.println("left");
			}
		}
		if(input.isKeyDown(Input.KEY_S))
		{
			TyOffset = 30;
			TxOffset = 0;
			if(!isOutOfBounds(new Rectangle(targetPos.getX() + TxOffset, targetPos.getY() + TyOffset, sprite.getWidth(), sprite.getHeight()), worldBounds))
				yOffset += 30;
			else
			{
				System.out.println("bottom");
			}
		}
		if(input.isKeyDown(Input.KEY_D))
		{
			TxOffset = 30;
			TyOffset = 0;
			if(!isOutOfBounds(new Rectangle(targetPos.getX() + TxOffset, targetPos.getY() + TyOffset, sprite.getWidth(), sprite.getHeight()), worldBounds))
				xOffset += 30;
			else
			{
				System.out.println("right");
			}
		}
	
		targetPos.set(targetPos.getX() + xOffset, targetPos.getY() + yOffset);
	}
	
	public void shoot()
	{
		if(input.isKeyDown(Input.KEY_SPACE))
		{
			try { g.entities.add(new Bullet(g, gc, getCenter().sub(new Vector2f(25,25)), new Vector2f(input.getMouseX(), input.getMouseY()), "/res/Bullet.png", "bullet")); } 
			catch (SlickException e) {}
		}
	}
}
