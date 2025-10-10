package io.github.SpaceNav;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	
	private Nave nave;
	private  ArrayList<Asteroid> asteroids1 = new ArrayList<>();
	private  ArrayList<Asteroid> asteroids2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();


	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		
	    // cargar imagen de la nave, 64x64   
	    nave = new Nave(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("Rocket2.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
        nave.setVidas(vidas);
        //crear asteroides
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            int size = 20 + r.nextInt(10); // radio
            int ancho = size * 2;
            int alto = size * 2;

            int x = r.nextInt(Gdx.graphics.getWidth() - ancho);
            int y = 50 + r.nextInt(Gdx.graphics.getHeight() - 50 - alto);

            Ball2 bb = new Ball2(x, y, size,
                    velXAsteroides + r.nextInt(4),
                    velYAsteroides + r.nextInt(4),
                    new Texture(Gdx.files.internal("aGreyMedium4.png")));

            asteroids1.add(bb);
            asteroids2.add(bb);
            for (Asteroid b : asteroids1) {
                float bx = MathUtils.clamp(b.getX(), 0, Gdx.graphics.getWidth() - b.getWidth());
                float by = MathUtils.clamp(b.getY(), 0, Gdx.graphics.getHeight() - b.getHeight());
                b.setPosition(bx, by);
            }
            
        }
        for (int i = 0; i < cantAsteroides/4; i++) {
            int size = 20 + r.nextInt(10); // radio
            int ancho = size * 2;
            int alto = size * 2;

            int x = r.nextInt(Gdx.graphics.getWidth() - ancho);
            int y = 50 + r.nextInt(Gdx.graphics.getHeight() - 50 - alto);

            BallStrong bb = new BallStrong(x, y, size,
                    velXAsteroides + r.nextInt(4),
                    velYAsteroides + r.nextInt(4),
                    new Texture(Gdx.files.internal("libgdx.png")));

            asteroids1.add(bb);
            asteroids2.add(bb);
            for (Asteroid b : asteroids1) {
                float bx = MathUtils.clamp(b.getX(), 0, Gdx.graphics.getWidth() - b.getWidth());
                float by = MathUtils.clamp(b.getY(), 0, Gdx.graphics.getHeight() - b.getHeight());
                b.setPosition(bx, by);
            }
            
        }
        
        
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	@Override
	public void render(float delta) {
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          batch.begin();
          long explosionId;
		  dibujaEncabezado();
	      if (!nave.estaHerido()) {
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < asteroids1.size(); j++) {
		            	Asteroid asteroide = asteroids1.get(j);
		            	if (b.checkCollision(asteroide)) {          
		            		explosionId = explosionSound.play();
		            		explosionSound.setVolume(explosionId,0.2f);
		            	if(asteroide instanceof Ball2) {
		            		asteroids1.remove(j);
		            		asteroids2.remove(j);
		            		j--;
		            	 }
		            	else if(asteroide instanceof BallStrong) {
		            		BallStrong ball = (BallStrong) asteroide;
		            		ball.getHit();
		            		if (ball.getHp() <= 0) {
		            			asteroids1.remove(j);
		            			asteroids2.remove(j);
		            			j--;
		            		}
		            	}
		            	score +=10;
		              }   	  
		            }
		                
		         //   b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
		      //actualizar movimiento de asteroides dentro del area
		      for (Asteroid ball : asteroids1) {
		          ball.update();
		      }
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<asteroids1.size();i++) {
		    	Asteroid ball1 = asteroids1.get(i);   
		        for (int j=0;j<asteroids2.size();j++) {
		          Asteroid ball2 = asteroids2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
	      }
	      //dibujar balas
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
	      //dibujar asteroides y manejar colision con nave
	      for (int i = 0; i < asteroids1.size(); i++) {
	    	    Asteroid b=asteroids1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 asteroids1.remove(i);
	            	 asteroids2.remove(i);
	            	 i--;
              }   	  
  	        }
	      
	      if (nave.estaDestruido()) {
  			if (score > game.getHighScore())
  				game.setHighScore(score);
	    	Screen ss = new PantallaGameOver(game);
  			ss.resize(1200, 800);
  			game.setScreen(ss);
  			dispose();
  		  }
	      batch.end();
	      //nivel completado
	      if (asteroids1.size()==0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score, 
					velXAsteroides+3, velYAsteroides+3, cantAsteroides+6);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		  }
	    	 
	}
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		this.explosionSound.dispose();
		
	}
   
}
