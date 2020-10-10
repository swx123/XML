package com.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.test.entity.Pair;

public class Test extends JFrame {

	private int rows = 20;
	private int cols = 20;
	private int size = 40;
	private int[][] map = new int[rows][cols];
	private int sx = 1;
	private int sy = 1;
	private int ex = rows - 2;
	private int ey = cols - 2;
	
	public static void main(String[] args) {
		new Test();
	}
	
	public Test() {
		setSize(rows * size, cols * size);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				for(int i = 0; i < rows; i++) {
					for(int j = 0; j < cols; j++) {
						if(map[i][j] == 10) {
							map[i][j] = 0;
						}
					}
				}
				int x = e.getX() / size;
				int y = e.getY() / size;
				if(map[x][y] == 0) {
					map[x][y] = 1;
				} else if(map[x][y] == 1) {
					map[x][y] = 0;
				}
				repaint();
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				List<Pair<Integer,Integer>> list = new AStar().run(map, sx, sy, ex, ey);
				if(list == null) {
					JOptionPane.showMessageDialog(null, "±»×èµ²", "´íÎóÌáÊ¾", JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int i = 0; i < list.size(); i++) {
					Pair<Integer, Integer> pair = list.get(i);
					map[pair.key][pair.value] = 10;
				}
				repaint();
			}
		});
		
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				g.setColor(Color.white);
				g.drawRect(i * size, j * size, size, size);
				if(i == sx && j == sy) {
					g.setColor(Color.green);
					g.fillRect(i * size, j * size, size, size);
				} else if(i == ex && j == ey) {
					g.setColor(Color.red);
					g.fillRect(i * size, j * size, size, size);
				} else if(map[i][j] == 1) {
					g.setColor(Color.blue);
					g.fillRect(i * size, j * size, size, size);
				} else if(map[i][j] == 10) {
					g.setColor(Color.orange);
					g.fillRect(i * size, j * size, size, size);
				}
			}
		}
	}
}
