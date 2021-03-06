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
			ArrayList<Integer> blocks = myPanel.blocks;
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
							
							int around[] = {k,l,m,n,o,p,q,r};
							int count = 0;
							
							//when the user clicks a mine and loses
							
							if (mines.contains(h)) {
								
								for(gridX=1; gridX<10; gridX++ ){
									for(gridY=1; gridY<10; gridY++ ){
										int i= (gridY-1)*(10) + gridX;
										if (mines.contains(i)){
											myPanel.colorArray[gridX][gridY] = Color.BLACK;
											myPanel.repaint();
											
											JLabel j9 = new JLabel();
										    j9.setText("You lost! :( Try again!");
										    j9.setBounds(100, 400, 200, 50);
										    myPanel.add(j9);
										    
										}
										else
										{
											//Do nothing
										}
									}

								}
								
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLACK;
								myPanel.repaint();
							} 
							   
							else {
								
								// counts the mines 
								if(!mines.contains(h)){
									for (int i=0; i<8; i++){
										if(mines.contains(around[i])){
											
											count++;
										}
									}  
									
									 myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.lightGray;
									 myPanel.repaint();
										
									//displays the numbers on the cells	
									 if(count>0){
										 
										 for(int z=0; z<9; z++){
											 if(count==z){
												 JLabel j8 = new JLabel();
												  j8.setText(String.valueOf(count));
												    j8.setBounds(x, y-18, 200, 50);
												    myPanel.add(j8);
											 }
										 }
									 }
														 
										
										else{
											
											//here goes the chain reaction code for empty cells
											
											for(gridX=myPanel.mouseDownGridX-1; gridX<4; gridX++){
												for(gridY=myPanel.mouseDownGridY-1; gridY<4; gridY++){
													p= (gridY-1)*(10) + gridX;
													if(!mines.contains(p)){
														myPanel.colorArray[gridX][gridY] = Color.LIGHT_GRAY;
														
														for (int i=0; i<8; i++){
															if(mines.contains(around[i])){
																
																count++;
															}
														}  
														
														 for(int z=0; z<9; z++){
															 if(count==z){
																 JLabel j8 = new JLabel();
																  j8.setText(String.valueOf(count));
																    j8.setBounds(x, y-18, 200, 50);
																    myPanel.add(j8);
															 }
														 }
														
													}
													else{}
												}
												
											}
											
											myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.lightGray;
											myPanel.repaint();
										}
									count=0;
									int toWin=0;
									for(gridX=1; gridX<10; gridX++){
										for(gridY=1; gridY<10; gridY++){
											if(myPanel.colorArray[gridX][gridY] == Color.lightGray){
												toWin++;
												if(toWin==71){
													
													JLabel j10 = new JLabel();
												    j10.setText("Congratulations! You Won!");
												    j10.setBounds(100, 400, 200, 50);
												    myPanel.add(j10);
												}
												else{}
											}
										}
									}
									}
						
								}
							    
								
							  
							    }
							
							 break;
		
						
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
                        	else if (currentColor.equals(Color.WHITE)){
                        		myP.colorArray[myP.mouseDownGridX][myP.mouseDownGridY] = Color.RED;
                        		myP.repaint();
                        	}
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
