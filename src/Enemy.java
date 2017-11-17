//Enemy.java
//finish movement

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Circle;

import java.util.*;

public class Enemy extends Entity
{
	private Vector2f targetPos;
	public Entity target;
	
	public Enemy(Game g, GameContainer gc, Entity t, Vector2f v, String ref, String name) throws SlickException 
	{
		super(g, gc, v, ref, name);
		target = t;
	}

	@Override
	public void onInit() 
	{
		
	}

	int c = 0;
	
	public void onTick() 
	{	
		targetPos = target.getPos();
		
		float run = targetPos.getX() - getPos().getX();
		float rise = targetPos.getY() - getPos().getY();
		
		
		Vector2f pos = new Vector2f(getPos().getX()+(run/200f), getPos().getY()+(rise/200f));
		
		if(isColliding(pos))
		{
			c++;
			if(c > 60)
			{
				//we're stuck
				setPos(new Vector2f(new Random().nextInt(gc.getWidth()-100)+50, new Random().nextInt(gc.getHeight()-100)+50));
				c = 0;
			}
			//System.out.println(getName() + "has collided with something");
			pos = new Vector2f(getPos().getX()-(run/100f), getPos().getY()-(rise/100f));
			setPos(pos);
			return;
		}
		
		setPos(pos);
	}

	public boolean isColliding(Vector2f pos) 
	{	
		Rectangle temp = new Rectangle(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
		
		for(int i = 0; i < g.entities.size(); i++)
		{
			Entity e = g.entities.get(i);
			if(e.getName().indexOf("enemy") >= 0 || e.getName().equals("player"))
			{
				if(isCollidingWith(temp, e.hitbox))
				{
					return true;
				}
			}
			else if(e.getName().equals("bullet"))
			{
				if(isCollidingWith(temp, e.hitbox))
				{
					try { kill(); } 
					catch (SlickException se) {}
				}
			}
		}
		return false;
	}

	int counter = 0;
	@Override
	public void onRender() 
	{
		counter += new Random().nextInt(4);
		if(counter >= 360)
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
		return;
	}
}
