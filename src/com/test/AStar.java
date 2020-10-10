package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.test.entity.Pair;

public class AStar {
	
	private static int[][] directs = {{-1, -1, 14}, {0, -1, 10}, {1, -1, 14}, {1, 0, 10}, {1, 1, 14}, {0, 1, 10}, {-1, 1, 14}, {-1, 0, 10}};
	
	public List<Pair<Integer, Integer>> run(int[][] map, int sx, int sy, int ex, int ey) {
		Node[][] nodeMap = new Node[map.length][map[0].length];
		Node start = new Node(sx, sy);
		nodeMap[sx][sy] = start;
		Node end = new Node(ex, ey);
		nodeMap[ex][ey] = end;
		List<Node> openList = new LinkedList<Node>();
		openList.add(start);
		
		end:
		while(true) {
			if(openList.isEmpty()) {
				return null;
			}
			Node next = openList.get(0);
			for(Node node : openList) {
				if(node.fVal() < next.fVal()) {
					next = node;
				}
			}
			openList.remove(next);
			next.isClose = true;
			
			for(int i = 0; i < directs.length; i++) {
				int dx = next.x + directs[i][0];
				int dy = next.y + directs[i][1];
				if(dx == ex && dy == ey) {
					end.parent = next;
					break end;
				}
				if(dx < 0 || dx == map.length || dy < 0 || dy == map[0].length) {
					continue;
				}
				Node node = nodeMap[dx][dy];
				if(map[dx][dy] == 1 
						|| (node != null && node.isClose)) {
					continue;
				}
				if(node == null) {
					node = new Node(dx, dy);
					node.parent = next;
					node.gVal = next.gVal + directs[i][2];
					node.hVal = f(dx, dy, next.x, next.y);
					nodeMap[dx][dy] = node;
					openList.add(node);
				} else {
					int newG = next.gVal + directs[i][2];
					if(newG < node.gVal) {
						node.gVal = newG;
						node.parent = next;
					}
				}
			}
		}
		
		List<Pair<Integer, Integer>> list = new ArrayList<>();
		for(Node node = end.parent; node.gVal > 0; node = node.parent) {
			Pair<Integer, Integer> pair = new Pair<Integer, Integer>(node.x, node.y);
			list.add(pair);
		}
		Collections.reverse(list);
		return list;
	}
	
	private int f(int x1, int y1, int x2, int y2) {
		int xx = Math.abs(x1 - x2) * 10;
		int yy = Math.abs(y1 - y2) * 10;
		return xx + yy;
	}
	
	static class Node {
		public int x;
		public int y;
		public Node parent;
		public int gVal;
		public int hVal;
		public boolean isClose;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int fVal() {
			return gVal + hVal;
		}
	}
}
