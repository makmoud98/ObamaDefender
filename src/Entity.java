import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity implements MouseListener, KeyListener
{
	private Vector2f pos;
	protected Rectangle hitbox;
	protected Image sprite;
	protected GameContainer gc;
	public Game g;
	protected Input input;
	private String tag;
	private String name;
	protected Rectangle worldBounds;
	
	public Entity(Game g, GameContainer gc, Vector2f v, String ref, String name) throws SlickException
	{
		this.g = g;
		this.gc = gc;
		this.input = gc.getInput();
		this.pos = v;
		this.tag = "";
		this.sprite = new Image(ref);
		this.name = name;
		this.worldBounds = new Rectangle(0, 0, gc.getWidth()-sprite.getWidth(), gc.getHeight()-sprite.getHeight());
		this.hitbox = new Rectangle(v.getX(), v.getY(), sprite.getWidth(), sprite.getHeight());
		input.addKeyListener(this);
		input.addMouseListener(this);
	}
	
	public abstract void onInit();
	
	public abstract void onTick();
	
	public abstract void onRender();
	
	public void setPos(Vector2f v)
	{
		Rectangle tempR = new Rectangle(v.getX(), v.getY(), hitbox.getWidth(), hitbox.getHeight());
		
		if(isOutOfBounds(tempR, worldBounds))
		{
			onOutOfBounds(pos, v);
		}
		else
		{
			pos = v;
			hitbox.setBounds(getPos().getX(), getPos().getY(), hitbox.getWidth(), hitbox.getHeight());
		}
	}
	
	protected boolean isOutOfBounds(Rectangle x, Rectangle y) 
	{
		if(y.contains(x.getX(),x.getY()))
		{
			return false;
		}
		return true;
	}

	public boolean isCollidingWith(Rectangle x, Rectangle y) 
	{
		//bad?
		
		Vector2f[] cx = getMainPoints(x);
		
		Vector2f[] cy = getMainPoints(y);
		
		for(int i = 0; i < cx.length; i++)
		{
			if(y.contains(cx[i].getX(), cx[i].getY()))
			{
				return true;
			}
		}
		
		for(int i = 0; i < cy.length; i++)
		{
			if(x.contains(cy[i].getX(), cy[i].getY()))
			{
				return true;
			}
		}
		
		return false;
	}

	
	private Vector2f[] getMainPoints(Rectangle x) 
	{
		Vector2f p[] = new Vector2f[8];
		p[0] = new Vector2f(x.getX(), x.getY());
		p[1] = new Vector2f(x.getX()+x.getWidth(), x.getY());
		p[2] = new Vector2f(x.getX()+x.getWidth(), x.getY()-x.getHeight());
		p[3] = new Vector2f(x.getX(), x.getY()-x.getHeight());
		p[4] = new Vector2f(x.getX()+(x.getWidth()/2f), x.getY());
		p[5] = new Vector2f(x.getX()+x.getWidth(), x.getY()-(x.getHeight()/2f));
		p[6] = new Vector2f(x.getX()+(x.getWidth()/2f), x.getY()-x.getHeight());
		p[7] = new Vector2f(x.getX(), x.getY()-(x.getHeight()/2f));
		
		return p;
	}

	public abstract void onOutOfBounds(Vector2f o, Vector2f n);

	public Vector2f getPos()
	{
		return pos;
	}
	
	public Vector2f getCenter()
	{
		return new Vector2f(pos.getX() + (sprite.getWidth()/2f), pos.getY() + (sprite.getHeight()/2f));
	}
	
	public void mouseClicked(int button, int x, int y, int clickCount)
	{}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
    {}
	
	public abstract void mousePressed(int button, int x, int y);//HERE
	
	public void mouseReleased(int button, int x, int y) 
    {}
	
	public void mouseWheelMoved(int change) 
    {}
	
	public abstract void keyPressed(int key, char c);//HERE
	
	public void keyReleased(int key, char c) 
	{}
	
	public void inputEnded() 
	{}
	
	public void inputStarted() 
	{}
	
	public boolean isAcceptingInput() 
	{ return true; }
	
	public void setInput(Input arg0) 
	{}
	
	public void kill() throws SlickException
	{
		input.removeAllListeners();
		this.setTag("kill");
	}
	public void setTag(String t)
	{
		tag = t;
	}
	public String getTag()
	{
		return tag;
	}
	public String getName()
	{
		return name;
	}
}
