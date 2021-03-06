package com.www.day04;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MySnakeFrame extends JFrame{
	
	public mypanel mypanel;
	boolean isExit=false;
	public MySnakeFrame() {
		this.setSize(470,483);
		this.setLocationRelativeTo(null);
		mypanel=new mypanel();
		
		mypanel.move();
		
		this.add(mypanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		mypanel.requestFocus();
	}
	public static void main(String[] args) {
		new MySnakeFrame();
	}
	
	class mypanel extends JPanel{
		public Image back;
		public Image cellimg;
		public Image foodimg;
		private static final int CELL_LENGTH=10;
		//蛇
		public Snake snake;
		//食物
		public Cell food;
		
		//行数,列数
		public  static  final int ROWS=35;
		public static  final int COLS=35;
		public Timer timer;
		
		public mypanel() {
//			back=new ImageIcon("src/com/www/day04/bg.png").getImage();
			back=Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("bg.png"));
			cellimg=Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("cell.png"));
			foodimg=Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("food.png"));
			snake=new Snake();
			food=createFood();
			timer=new Timer();
		}
		//随机生成食物（生成x，y坐标）
		//注意:食物是否在蛇身上，如果在就重新生成
		private Cell createFood() {
			Random random=new Random();
			Cell cells[]=snake.getCells();
			int x;
			int y;
			do{
				x=random.nextInt(ROWS);
				y=random.nextInt(COLS);
			}while(snake.contains(x,y));
			return new Cell(x, y);
		}

		public void paint(Graphics g){
			super.paint(g);
			g.drawImage(back, 0, 0, this);
			Cell [] cells=snake.getCells(); 
			//将图形上下文的原点平移到坐标系中的某一点
			g.translate(54, 49);
		
			for(int i=0;i<cells.length;i++){
				Cell node=cells[i];
				g.drawImage(cellimg, node.getX()*CELL_LENGTH,node.getY()*CELL_LENGTH,this);
				
			}
			g.drawImage(foodimg, food.getX()*CELL_LENGTH,food.getY()*CELL_LENGTH,  this);
		}

		//蛇移动的方法
		public void move(){
			//定时器,每隔50ms执行
			
			
			timer.schedule(new TimerTask() {
				public void run() {
					//蛇移动逻辑(判断蛇是否撞到墙或者自己,或者吃到食物)
					if(snake.hit(snake.getCurrentDirection())){
						snake=new Snake();
						food=createFood();
					}else{
						//判断蛇是否吃到食物
						boolean isEat=snake.creep(food); 
						if(isEat){
							food=createFood();
						}
					}
					repaint();
				}
			},0,100);
			//请求当前组建获取输入焦点
			
			this.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key=e.getKeyCode();
					boolean isEat;
					
					switch(key){
						case KeyEvent.VK_UP:
							isEat=snake.creep(Snake.UP,food);
							if(isEat){
								food=createFood();
							}
							break;
						case KeyEvent.VK_DOWN:
							isEat=snake.creep(Snake.DOWN,food);
							if(isEat){
								food=createFood();
							}
							break;
						case KeyEvent.VK_LEFT:
							isEat=snake.creep(Snake.LEFT,food);
							if(isEat){
								food=createFood();
							}
							break;
						case KeyEvent.VK_RIGHT:
							isEat=snake.creep(Snake.RIGHT,food);
							if(isEat){
								food=createFood();
							}
							break;
						case KeyEvent.VK_SPACE:
							isExit=true;
							timer.cancel();
							
							break;
						case KeyEvent.VK_ENTER:
							if(isExit){
								isExit=false;
								timer=new Timer();
								timer.schedule(new TimerTask() {
									public void run() {
										//蛇移动逻辑(判断蛇是否撞到墙或者自己,或者吃到食物)
										if(snake.hit(snake.getCurrentDirection())){
											snake=new Snake();
											food=createFood();
										}else{
											//判断蛇是否吃到食物
											boolean isEat=snake.creep(food); 
											if(isEat){
												food=createFood();
											}
										}
										repaint();
									}
								},0,100);
							}
							break;
					}
				}
			});
		}

	}
}
