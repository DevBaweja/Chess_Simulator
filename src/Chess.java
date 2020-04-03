import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Chess extends JPanel implements ActionListener {

	int rows,cols;
	GridLayout g;
	int width,height;
	JButton[][] grid;
	Piece[][] pieces;
	boolean[][] valid;
	int turn;
	boolean clicked;
	int previ,prevj;
	final int BLACK= -1;
	final int WHITE = 1;
	JButton temp;
	String loc = "F:/Eclipse/Workspace/Chess/src/";
	Color cWhite,cBlack;
	Object[] PiecesArray;
	public Chess()
	{
		width = 800;
		height = 800;
		setSize(width,height);
		//setSize(new Dimension(width, height));
		//setPreferredSize(new Dimension(width, height));

		rows = 8;
		cols = 8;
		g = new GridLayout(rows,cols);
		setLayout(g);
		
		setBackground(Color.WHITE);
		grid = new JButton[rows][cols];
		pieces = new Piece[rows][cols];
		valid = new boolean[rows][cols];
		temp = new JButton();
		createGrid();
		cWhite = new Color(232,235,239);
		cBlack = new Color(125,135,150);
		styleGrid();
		addGrid();
		addListener();
	
		setup();
		validate();
		//revalidate();
	}

	private void setup() {
		// TODO Auto-generated method stub
		
		// Filling up occupied spaces
		int i;
		// WHITE
		i=0;
		do {
			for(int j=0;j<cols;j++)
				{
					pieces[i][j].setAlive(true);
					pieces[i][j].setType(BLACK);
				}
		i++;
		}while(i<=1);
		// BLACK
		i=cols-1;
		do {
			for(int j=0;j<cols;j++)
				{
					pieces[i][j].
					setAlive(true);
					pieces[i][j].setType(WHITE);
				}
		i--;
		}while(i>=cols-2);
		
		// Creating pawns
		createPawn();
		// Creating Piece
		createPiece();

		turn = WHITE;
		
		PiecesArray = new Object[4];
		PiecesArray[0] = "Queen";
		PiecesArray[1] = "Rook";
		PiecesArray[2] = "Bishop";
		PiecesArray[3] = "Knight";
	}

	private void createPiece() {
		// TODO Auto-generated method stub
		// For Rook,Bishop,Knight
		int i,j;
		i=0;
	
		// Black
		j=0;makeRook(i,j);
		j++;makeKnight(i,j);
		j++;makeBishop(i,j);
		j++;makeQueen(i,j);
		
		// Black
		j=cols-1;makeRook(i,j);
		j--;makeKnight(i,j);
		j--;makeBishop(i,j);
		j--;makeKing(i,j);
		
		i=rows-1;
		
		// White
		/*pieces[4][3].setType(WHITE);
		j=0;makeRook(4,3);
		pieces[3][5].setType(WHITE);
		j++;makeKnight(3,5);
		pieces[3][4].setType(WHITE);
		j++;makeBishop(3,4);
		pieces[3][2].setType(WHITE);
		j++;makeQueen(3,2);
		*/
		
		j=0;makeRook(i,j);
		j++;makeKnight(i,j);
		j++;makeBishop(i,j);
		j++;makeQueen(i,j);
		
		// White
		j=cols-1;makeRook(i,j);
		j--;makeKnight(i,j);
		j--;makeBishop(i,j);
		j--;makeKing(i,j);
		
		
		// TRIAL
		
	}

	private void createPawn() {
		// TODO Auto-generated method stub
		int i;
		i=1;
		// Black
		for(int j=0;j<cols;j++)
			makePawn(i,j);
		// White
		i=6;
		for(int j=0;j<cols;j++)
			makePawn(i,j);
	}

	private void createGrid() {
		// TODO Auto-generated method stub
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				grid[i][j] = new JButton();
				pieces[i][j] = new Piece();
			}
		}
	}

	private void addGrid() {
		// TODO Auto-generated method stub
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
				add(grid[i][j]);
		}
	}

	private void changeGrid() {
		// TODO Auto-generated method stub
		for(int i=0;i<rows;i++)
			{
				for(int j=0;j<cols;j++)
					 remove(grid[i][j]);
			}
		
		if(turn==BLACK)
			for(int i=rows-1;i>=0;i--)
				{
					for(int j=cols-1;j>=0;j--)
						 add(grid[i][j]);
				}
		if(turn==WHITE)
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<cols;j++)
					add(grid[i][j]);
			}
	}
	private void styleGrid() {
		// TODO Auto-generated method stub
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if((i+j)%2==0)
					grid[i][j].setBackground(cWhite);
				else
					grid[i][j].setBackground(cBlack);
				 grid[i][j].setFocusPainted(false);
				 //grid[i][j].setBorderPainted(false);
			}	
		}		
	}
	
/*
	public void paint(Graphics g)
	{
		
	}
*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		removeListener();
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(obj==grid[i][j])
				{
					System.out.println(i+" "+j);
					Piece p = new Piece(pieces[i][j]);
					if(p.getType()==turn && !clicked)
					{
						previ = i; prevj = j;
						System.out.println(i+","+j);
						System.out.println(pieces[i][j].getType() + " "+ pieces[i][j].getName());
						if(p.line)
							makeLine(i,j);					
						if(p.diagonal)
							makeDiagonal(i,j);
						if(p.unusual)
							makeUnusal(i,j);
						if(p.forward)
							makeForward(i,j,p);
						if(p.onestep)
							makeOnestep(i,j);
						
						// Do Here
						clicked = true;
					}// Else is Needed
					else if(clicked)
					{
							Piece pnew = new Piece(pieces[i][j]); // New Point
							Piece prev = new Piece(pieces[previ][prevj]);
							if(pnew.getType()==turn)
							{
								System.out.println("My Piece");
								//grid[i][j].addActionListener(this);
								//clicked = false;
								//grid[i][j].doClick();
							}
							else
							{
								if(valid[i][j])
								{
									// For Pawn Promotion
									Object pawnpro = null;
										if(prev.forward)
											{
												int first = 0;
												int last = 7;
												if(prev.getType()==WHITE && i==first)
												{
			                                		do {
			                                			pawnpro = JOptionPane.showInputDialog(null, "Choose your piece", "Pawn Promotion", JOptionPane.QUESTION_MESSAGE,null,PiecesArray, 0);
			                                		}while(pawnpro == null);
												}
												
												if(prev.getType()==BLACK && i==last)
												{
			                                		do {
			                                			pawnpro = JOptionPane.showInputDialog(null, "Choose your piece", "Pawn Promotion", JOptionPane.QUESTION_MESSAGE,null,PiecesArray, 0);
			                                		}while(pawnpro == null);
												}
											}
																			
									maintainBoard(previ,prevj,i,j,pawnpro);							                    
								}
								else
									System.out.println("No Valid Square");
							}
							styleGrid();
							clicked = false;
					}
					
				}
			}
		}
		addListener();
	}

	private void maintainBoard(int previ, int prevj, int i, int j,Object pawnpro) {
		// TODO Auto-generated method stub
		Piece pnew = new Piece(pieces[i][j]);
		Piece prev = new Piece(pieces[previ][prevj]);
		String name = prev.getName();
		String tname = prev.getTypeName();
		
		Piece next = new Piece(pieces[i][j]);
		String pname = next.getName();
		String tpname = next.getTypeName();
		char r = (char)(i+'a');
		int c = j+1;
		if(pnew.getType()==0)
			System.out.println(tname+" "+name+" moved to "+r+c);
		if(pnew.getOppType()==turn)
			System.out.println(tname+" "+name+" takes "+tpname+" "+pname+" on "+r+c);
		
		//
		if(pawnpro!=null)
		{
			pieces[i][j].setType(prev.getType());
			selectPiece(pawnpro,i,j);	
		}
		else
			pieces[i][j] = new Piece(pieces[previ][prevj]); // Previous Piece(previ,prevj) to New Place(i,j)
		
		pieces[previ][prevj].reset(); // Previous Piece Reset
		
		setIcon(i,j); // Previous Piece Icon to New Place(i,j)
		setIcon(previ,prevj);
		//
		
		 turn = getOppTurn();
		// changeGrid(); // Grid must be changed after turn change
		initialValid(valid);	          	
	}

	private void selectPiece(Object pawnpro, int i, int j) {
		// TODO Auto-generated method stub
		if(pawnpro=="Queen")
		makeQueen(i,j);
		if(pawnpro=="Rook")
		makeRook(i,j);
		if(pawnpro=="Bishop")
		makeBishop(i,j);
		if(pawnpro=="Knight")
		makeKnight(i,j);
	}

	private void makeForward(int i, int j, Piece p) {
		// TODO Auto-generated method stub
		int tempi,tempj,init;
		
		if(p.getType()==WHITE)
		{
			tempi = i-1;tempj = j;
			init = 6;
			if(i == init)
				validMovePawn(tempi,tempj,p,true);
			else
				validMovePawn(tempi,tempj,p,false);
		}
		
		if(p.getType()==BLACK)
		{
			tempi = i+1;tempj = j;
			init = 1;
			if(i== init)
				validMovePawn(tempi,tempj,p,true);
			else
				validMovePawn(tempi,tempj,p,false);		}
	}

	private void validMovePawn(int tempi, int tempj,Piece p,boolean initial) {
		// TODO Auto-generated method stub
			if(pieces[tempi][tempj].getType()==0)
			{
					grid[tempi][tempj].setBackground(Color.green);
					valid[tempi][tempj] = true;			
			}
			if(isValid(tempi,tempj+1))
			if(p.getOppType()==pieces[tempi][tempj+1].getType())
					{
							grid[tempi][tempj+1].setBackground(Color.red);
							valid[tempi][tempj+1] = true;
					}
			if(isValid(tempi,tempj-1))
				if(p.getOppType()==pieces[tempi][tempj-1].getType())
						{
							grid[tempi][tempj-1].setBackground(Color.red);
							valid[tempi][tempj-1] = true;
						}
			// Use type here 
		if(initial)
		{
				if(pieces[tempi][tempj].getType()==0 && pieces[tempi+p.getOppType()][tempj].getType()==0)
				{
						grid[tempi+p.getOppType()][tempj].setBackground(Color.green);
						valid[tempi+p.getOppType()][tempj] = true;			
				}
		}
	}

	private void makeOnestep(int i, int j) {
		// TODO Auto-generated method stub
		int tempi,tempj;
		for(tempi=i-1;tempi<=i+1;tempi++)
			for(tempj=j-1;tempj<=j+1;tempj++)
			{
				if(isValid(tempi,tempj))
					validMove(tempi,tempj);
			}
	}

	private void makeUnusal(int i, int j) {
		// TODO Auto-generated method stub
		int tempi,tempj,k;
		for(tempi=i-2;tempi<=i+2;tempi+=4)
		{
			for(tempj=j-1;tempj<=j+1;tempj+=2)
			{
				if(isValid(tempi,tempj))
					validMove(tempi,tempj);
			}
		}
		for(tempj=j-2;tempj<=j+2;tempj+=4)
		{
			for(tempi=i-1;tempi<=i+1;tempi+=2)
			{
				if(isValid(tempi,tempj))
					validMove(tempi,tempj);
			}
		}
	}

	private void makeDiagonal(int i, int j) {
		// TODO Auto-generated method stub
		int tempi,tempj;
		tempi = i-1;tempj = j-1;
		while(isValid(tempi,tempj))
			if(!validMove(tempi--,tempj--))break;
		
		tempi = i+1;tempj = j+1;
		while(isValid(tempi,tempj))
			if(!validMove(tempi++,tempj++))break;
		
		tempi = i+1;tempj = j-1;
		while(isValid(tempi,tempj))
			if(!validMove(tempi++,tempj--))break;
		
		tempi = i-1;tempj = j+1;
		while(isValid(tempi,tempj))
			if(!validMove(tempi--,tempj++))break;
	}

	private void makeLine(int i, int j) {
		// TODO Auto-generated method stub
		int tempi,tempj;
		tempi = i-1;tempj = j;
		while(isValid(tempi,tempj))
			if(!validMove(tempi--,tempj))break;
		
		tempi = i+1;tempj = j;
		while(isValid(tempi,tempj))
			if(!validMove(tempi++,tempj))break;
		
		tempi = i;tempj = j-1;
		while(isValid(tempi,tempj))
			if(!validMove(tempi,tempj--))break;
		
		tempi = i;tempj = j+1;
		while(isValid(tempi,tempj))
			if(!validMove(tempi,tempj++))break;
	}

	private void initialValid(boolean[][] valid2) {
		// TODO Auto-generated method stub
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				valid[i][j] = false;
	}

	int getOppTurn()
	{
		return -turn;
	}
	private boolean validMove(int tempi, int tempj) {
		// TODO Auto-generated method stub
			if(pieces[tempi][tempj].getType()==0)
			{
					// grid[tempi][tempj].setIcon(getImageIcon());
					grid[tempi][tempj].setBackground(Color.green);
					valid[tempi][tempj] = true;
			}
			else
			{
				if(pieces[tempi][tempj].getOppType()==turn)
					{
							grid[tempi][tempj].setBackground(Color.red);
							valid[tempi][tempj] = true;
					}
				return false;
			}
			return true;
	}

	private ImageIcon getImageIcon() {
		// TODO Auto-generated method stub
		String name = "Dots.png";
		ImageIcon imgicon = new ImageIcon(loc+name);
		Image img = imgicon.getImage() ;  
		Image newimg = img.getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH ) ; 
		validate();
		imgicon = new ImageIcon( newimg );
		return imgicon;
	}

	private boolean isValid(int i, int j) {
		// TODO Auto-generated method stub
		if(i<0 || j<0 || i>rows-1 || j>cols-1)
			return false;
		
		return true;
	}
	
	private void addListener() {
		// TODO Auto-generated method stub
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				grid[i][j].addActionListener(this);
			}
		}
	}

	private void removeListener() {
		// TODO Auto-generated method stub
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				grid[i][j].removeActionListener(this);
			}
		}
	}


	private void makeRook(int i,int j)
	{
		pieces[i][j].setParameter("Rook", false,false,true,false,false);
		setIcon(i,j);
	}

	private void makeBishop(int i,int j)
	{
		pieces[i][j].setParameter("Bishop", false,false,false,true,false);
		setIcon(i,j);
	}
	private void makeKnight(int i,int j)
	{
		pieces[i][j].setParameter("Knight", false,false,false,false,true);
		setIcon(i,j);
	}
	private void makeKing(int i,int j)
	{
		pieces[i][j].setParameter("King",true,false,false,false,false);
		setIcon(i,j);
	}
	private void makePawn(int i,int j)
	{
		pieces[i][j].setParameter("Pawn",false,true,false,false,false);
		setIcon(i,j);
	}
	private void makeQueen(int i,int j)
	{
		pieces[i][j].setParameter("Queen",false,false,true,true,false);
		setIcon(i,j);
	}
	
	private void setIcon(int i, int j) {
		// TODO Auto-generated method stub
		if(pieces[i][j].getName() != "null")
			{	
				String type = pieces[i][j].getTypeName() + pieces[i][j].getName() + ".png";
				ImageIcon imgicon = new ImageIcon(loc+type);
				Image img = imgicon.getImage() ;  
				Image newimg = img.getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ) ; 
				validate();
				imgicon = new ImageIcon( newimg );
				grid[i][j].setIcon(imgicon);
				// grid[i][j].setText(pieces[i][j].getTypeName() +" "+ pieces[i][j].getName());
			}
		else
			grid[i][j].setIcon(null);
	}
}

/*

setRolloverIcon
setDisabledIcon
setSelectedIcon
setEnabled
setSelected

	
grid[i][j].addActionListener(this);
clicked = false;
grid[i][j].doClick();
grid[i][j].removeActionListener(this);
		
											
if(validMove(tempi--,tempj) && i==6)
	{
		System.out.println("First");
		validMovePawn(tempi,tempj);
	}
				
if(!isValid(tempi-1,tempj) && validMovePawn(tempi,tempj))
{
	System.out.println("Promote");
}
*/
