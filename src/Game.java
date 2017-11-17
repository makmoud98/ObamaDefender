import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Game extends BasicGame
{
	public ArrayList<Entity> entities;
	
	public Game(String gamename)
	{
		super(gamename);
		entities = new ArrayList<Entity>();
	}

	@Override
	public void init(GameContainer gc) throws SlickException 
	{	
		Player p = new Player(this, gc, new Vector2f(gc.getWidth()/2f, gc.getHeight()/2f), "/res/Player.png", "player");
		
		entities.add(p);
		
		for(int i = 0; i < 5; i++)
		{
			Enemy e = new Enemy(this, gc, p, new Vector2f(new Random().nextInt(gc.getWidth()-100)+50, new Random().nextInt(gc.getHeight()-100)+50), "/res/Enemy.png", "enemy" + i);
			entities.add(e);
		}
		
		
		
		int s = entities.size();
		for(int i = 0; i < s; i++)
		{
			entities.get(i).onInit();
		}
	}
	
	@Override
	public void update(GameContainer gc, int x) throws SlickException
	{
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			
			e.onTick();
			
			if(e.getTag().equals("kill"))
			{
				entities.remove(e);
				e = null;
			}
		}
		
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException
	{	
		new Image("/res/background.png").draw(0,0);
		
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			
			e.onRender();
			
			if(e.getTag().equals("kill"))
			{
				entities.remove(e);
				e = null;
			}
		}
	}
}