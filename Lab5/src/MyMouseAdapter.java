import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyMouseAdapter extends MouseAdapter {
	
	
	private Random generator = new Random();
	
	public void mousePressed(MouseEvent e) {

		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			
			e.translatePoint(-x1, -y1);
			
			int x = e.getX();
			int y = e.getY();
			
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			
			myPanel.repaint();
			break;
		
		case 3:		//Right mouse button
			Component d = e.getComponent();
            while (!(d instanceof JFrame)) {
                d = d.getParent();
                if (d == null) {
                    return;
                }
            }
            
        	JFrame frame = (JFrame) d;
        	MyPanel myP = (MyPanel) frame.getContentPane().getComponent(0);
        	Insets myInsts = frame.getInsets();
        	
            int w1 = myInsts.left;
            int z1 = myInsts.top;
            
            e.translatePoint(-w1, -z1);
            
            int w = e.getX();
            int z = e.getY();
            
            myP.x = w;
            myP.y = z;
            myP.mouseDownGridX = myP.getGridX(w, z);
            myP.mouseDownGridY = myP.getGridY(w, z);
            
            myP.repaint();
            break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			ArrayList<Integer> mines = myPanel.mines;

			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if ((gridX == 0) || (gridY == 0)) {
							//On the left column and on the top row... do nothing
						} else {
							//On the grid other than on the left column and on the top row:

							int h = (myPanel.mouseDownGridY-1)*(10) + myPanel.mouseDownGridX;
							
							// Around the selected block
							int k = h - 11;  //Top left of mouse down
							int l = h - 1;  //Center left of mouse down
							int m = h + 9;  //Bottom left of mouse down
							int n = h - 10;  //Top Center of mouse down
							int o = h + 10;  //Bottom Center of mouse down
							int p = h - 9;  //Top Right of mouse down
							int q = h + 1;  //Center right of mouse down
							int r = h + 11;  //Bottom right of mouse down
							

							if (mines.contains(h)) {
							    myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLACK;
								myPanel.repaint();
							} 
							    
							else {
								
//								if ((mines.contains(k)&&(mines.contains(m)||mines.contains(n)
//								    	||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r))&&
//										(mines.contains(l))) ||
//
//									(mines.contains(l)&&(mines.contains(m)||mines.contains(n)
//										||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r))&&
//											(mines.contains(k))) ||
//									
//									(mines.contains(m)&&(mines.contains(k)||mines.contains(l)
//										||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r))&&
//											(mines.contains(n))) ||
//									
//									(mines.contains(n)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
//										||mines.contains(p)||mines.contains(q)||mines.contains(r))&&
//											(mines.contains(o))) ||
//									
//									(mines.contains(o)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
//										||mines.contains(n)||mines.contains(p)||mines.contains(q)||mines.contains(r))&&
//											(mines.contains(q))) ||
//									
//									(mines.contains(p)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
//										||mines.contains(n)||mines.contains(o)||mines.contains(q))&&
//											(mines.contains(r))) ||
//									
//									(mines.contains(q)&&(mines.contains(k)||mines.contains(l)
//										||mines.contains(n)||mines.contains(o)||mines.contains(p)||mines.contains(r))&&
//											(mines.contains(m))) ||
//									
//									(mines.contains(r)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
//										||mines.contains(n)||mines.contains(o)||mines.contains(q))&&
//												(mines.contains(p))))
//							    	
//							    	{
//									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.YELLOW;
//									myPanel.repaint();
//									
//								}
							    
								//green for two mines
								if((mines.contains(k)&&(mines.contains(l)||mines.contains(m)||mines.contains(n)
								    	||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r))) ||
										
							    	(mines.contains(l)&&(mines.contains(k)||mines.contains(m)||mines.contains(n)
										||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r))) ||
							    	
							    	(mines.contains(m)&&(mines.contains(k)||mines.contains(l)||mines.contains(n)
										||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r))) ||
							    	
							    	(mines.contains(n)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
										||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r))) ||
							    	
							    	(mines.contains(o)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
										||mines.contains(n)||mines.contains(p)||mines.contains(q)||mines.contains(r))) ||
							    	
							    	(mines.contains(p)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
										||mines.contains(n)||mines.contains(o)||mines.contains(q)||mines.contains(r))) ||
							    	
							    	(mines.contains(q)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
										||mines.contains(n)||mines.contains(o)||mines.contains(p)||mines.contains(r))) ||
							    	
							    	(mines.contains(r)&&(mines.contains(k)||mines.contains(l)||mines.contains(m)
										||mines.contains(n)||mines.contains(o)||mines.contains(p)||mines.contains(q))) 
							    		){
								    	
								    	myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.green;
								    	myPanel.repaint();
//								    	  JLabel j1 = new JLabel();
//										    j1.setText("2");
//										    j1.setBounds(myPanel.mouseDownGridX, myPanel.mouseDownGridY, 200, 50);
//										    myPanel.add(j1);
//										    
										 
								    	}
							    //blue 1 mine
							    else if(mines.contains(k)||mines.contains(l)||mines.contains(m)||mines.contains(n)
							    	||mines.contains(o)||mines.contains(p)||mines.contains(q)||mines.contains(r)){
							    	
							    	myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLUE;
									myPanel.repaint();
							    		
							    	} 
							  
							    }
							
							 break;
		
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
        	Component d = e.getComponent();
            while (!(d instanceof JFrame)) {
                d = d.getParent();
                if (d == null) {
                    return;
                }
            }
            JFrame frame = (JFrame)d;
            MyPanel myP = (MyPanel) frame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
            Insets myInsts = frame.getInsets();
            int w1 = myInsts.left;
            int z1 = myInsts.top;
            e.translatePoint(-w1, -z1);
            int w = e.getX();
            int z = e.getY();
           
            myP.x = w;
            myP.y = z;
            int gridW = myP.getGridX(w, z);
            int gridZ = myP.getGridY(w, z);
            if ((myP.mouseDownGridX == -1) || (myP.mouseDownGridY == -1)) {
                //Had pressed outside
                //Do nothing
            } else {
                if ((gridW == -1) || (gridZ == -1)) {
                    //Is releasing outside
                    //Do nothing
                } else {
                    if ((myP.mouseDownGridX != gridW) || (myP.mouseDownGridY != gridZ)) {
                        //Released the mouse button on a different cell where it was pressed
                        //Do nothing
                    } else {
                        //Released the mouse button on the same cell where it was pressed
                        if ((gridW == 0) || (gridZ == 0)) {
                        }
                        
                        else {
                        	//Color newColor = null;
                        	Color currentColor = myP.colorArray[myP.mouseDownGridX][myP.mouseDownGridY];
                        	if (currentColor == Color.RED){
                        		myP.colorArray[myP.mouseDownGridX][myP.mouseDownGridY] = Color.WHITE;
                            	myP.repaint();
                        	}
                        	else
                        		myP.colorArray[myP.mouseDownGridX][myP.mouseDownGridY] = Color.RED;
                        		myP.repaint();
        	}
        	break;
                    }
                }
            }
        
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}
