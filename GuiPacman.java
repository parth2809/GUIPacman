/**
 * GuiPacman.java
 *
 * @author      Parth Shah
 * @version     1.0
 * @since       02/27/19
 *
 */

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.io.*;


public class GuiPacman extends Application
{
  // The filename for where to save the Board
  private String outputBoard; 
  // The Game Board
  private Board board; 

  // Fill colors to choose
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);
  //declaring GridPane as an instance variable
  GridPane pane = new GridPane();
  //declaring StackPane as an instance variable
  StackPane stack = new StackPane();
  //declaring directionRec as an instance variable with default starting
  //direction as Right
  Direction directionRec = Direction.RIGHT;

 
  //main method to launch the application
  public static void main(String[] args){
  	launch(args);
  }


  /* 
   * Name:      start
   * Purpose:   Start and keep the game running. This method sets up the
   * initial pacman board with the GUIs
   * Parameter: primarystage- the stage on which the scene is displayed
   * Return:void
   */
  @Override
  public void start(Stage primaryStage)
  {
    // Process Arguments and Initialize the Game Board
    processArgs(getParameters().getRaw().toArray(new String[0]));
    //align everthing in the pane to the centre
	pane.setAlignment(Pos.CENTER); 
	// Set the padding of the pane.
	pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
	//adding horizontal spacing between the grid boxes 
	pane.setHgap(5.5); 
	//adding vertical spacing between the grid boxes 
	pane.setVgap(5.5); 
	//giving colour to the background of the pane
	pane.setStyle("-fx-background-color: rgb(0, 0, 0)");
	//declaring an object of Text
	Text txt = new Text(); 
	//giving text a string for the game title
	txt.setText("Pac-Man");
	//Setting up the font features of the text
	txt.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 35));
	//giving color to the text
	txt.setFill(Color.WHITE);
	//adding the text to the pane which spans over 3 columns
	pane.add(txt,1,0,3,1);

	//declaring another object of text
	Text txt1 = new Text();
	//creating string to tally the score
	String score="Score: "+Integer.toString(board.getScore());
	//setting the string to the new text object 
	txt1.setText(score);
	//Setting up the font features of the text
	txt1.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 20));
	//giving color to the text
	txt1.setFill(Color.WHITE);
	//adding the text to the pane which spans over 3 columns
	pane.add(txt1,5,0,3,1);
	//looping thorugh the character grid of the board to initilaise
	//the Gui version of the grid in the pane
	for(int i=1;i<=board.GRID_SIZE;i++){
		for(int j=0;j<board.GRID_SIZE;j++){
			Tile toAdd = new Tile(board.getGrid()[i-1][j]);
			pane.add(toAdd.getNode(), j , i);
		}
	}
	//adding the entire grid pane as the first layer of the stackpane
	stack.getChildren().add(pane);
	//scene that needs to be displayed
	Scene scene = new Scene(stack); 
	//calling the keyhandler class in scene to move pacman
	scene.setOnKeyPressed(new myKeyHandler());
	//title of the window(primary stage)
	primaryStage.setTitle("GuiPacman"); 
	// set what scene to show inside the window
	primaryStage.setScene(scene); 
	 //showing the stage
	primaryStage.show();
   

  }



  /** Add your own Instance Methods Here */

  /*
   * Name:       myKeyHandler
   *
   * Purpose:     
   *
   *
   */
  private class myKeyHandler implements EventHandler<KeyEvent> {

   /* 
    * Name:      handle
    * Purpose:   handle the KeyEvent of user's input.
    * Parameter: KeyEvent e-takes in keyboard input for arrow keys and 'S'
    * Return:  void  
    */
    @Override
    public void handle (KeyEvent e) {
		//if statement to check if the keyboard input matches with UP
		//and the game is not pver
    	if (e.getCode().equals(KeyCode.UP) && board.isGameOver()!=true){
    		//checking if pacman can move in that direction
        	if(board.canMove(Direction.UP)){
        		//printing in terminal
            	System.out.println("Moving up");
            	//making pacman move
            	board.move(Direction.UP);
            	//recording the direction in which move has taken place
            	//will help us in rotating the face of pacman later
            	directionRec=Direction.UP;
            }
        }
        //if statement to check if the keyboard input matches with DOWN
		//and the game is not pver
        if (e.getCode().equals(KeyCode.DOWN) && board.isGameOver()!=true){
        	//checking if pacman can move in that direction
        	if(board.canMove(Direction.DOWN)){
        		//printing in terminal
        		System.out.println("Moving down");
        		//making pacman move
            	board.move(Direction.DOWN);
            	//recording the direction in which move has taken place
            	//will help us in rotating the face of pacman later
            	directionRec=Direction.DOWN;
            }
        }
        //if statement to check if the keyboard input matches with LEFT
		//and the game is not pver
        if (e.getCode().equals(KeyCode.LEFT) && board.isGameOver()!=true){
        	//checking if pacman can move in that direction
        	if(board.canMove(Direction.LEFT)){
        		//printing in terminal
        		System.out.println("Moving left");
        		//making pacman move;
            	board.move(Direction.LEFT);
            	//recording the direction in which move has taken place
            	//will help us in rotating the face of pacman later
            	directionRec=Direction.LEFT;
            }
        }
        //if statement to check if the keyboard input matches with RIGHT
		//and the game is not pver
        if (e.getCode().equals(KeyCode.RIGHT) && board.isGameOver()!=true){
        	//checking if pacman can move in that direction
        	if(board.canMove(Direction.RIGHT)){
        		//printing in terminal
        		System.out.println("Moving right");
        		//making pacman move
            	board.move(Direction.RIGHT);
            	//recording the direction in which move has taken place
            	//will help us in rotating the face of pacman later
            	directionRec=Direction.RIGHT;
            }
        }
        if (e.getCode().equals(KeyCode.getKeyCode("S"))){
        	try{
        		board.saveBoard(outputBoard);
        		System.out.println("Saving Board to "+outputBoard);
            	System.exit(0);
        	}
        	catch(IOException ex){
        		System.out.println("saveBoard threw an Exception"); 
        	}	
        }
        //clearing pane so that it can be updated
        pane.getChildren().clear();
        //align everthing in the pane to the centre
        pane.setAlignment(Pos.CENTER); 
        // Set the padding of the pane.
		pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
		//adding horizontal spacing between the grid boxes 
		pane.setHgap(5.5); 
		//adding vertical spacing between the grid boxes 
		pane.setVgap(5.5); 
		//giving colour to the background of the pane
		pane.setStyle("-fx-background-color: rgb(0, 0, 0)");
		//create an object of the Text
		Text txt = new Text(); 
		//giving text a string for the game title
		txt.setText("Pac-Man");
		//Setting up the font features of the text
		txt.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 35));
		//giving color to the text
		txt.setFill(Color.WHITE);
		//adding the text to the pane which spans over 3 columns
		pane.add(txt,1,0,3,1);

		//declaring another object of text
		Text txt1 = new Text();
		//creating string to tally the score
		String score="Score: "+Integer.toString(board.getScore());
		//setting the string to the new text object 
		txt1.setText(score);
		//Setting up the font features of the text
		txt1.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 20));
		//giving color to the text
		txt1.setFill(Color.WHITE);
		//adding the text to the pane which spans over 3 columns
		pane.add(txt1,5,0,3,1);
		//looping thorugh the character grid of the board to initilaise
		//the Gui version of the grid in the pane
		for(int i=1;i<=board.GRID_SIZE;i++){
			for(int j=0;j<board.GRID_SIZE;j++){
				Tile toAdd = new Tile(board.getGrid()[i-1][j]);
				pane.add(toAdd.getNode(), j , i);
			}
		}
		//checking if game is over
        gameIsOver();
    	
        
    }

    /* 
     * Name:      gameIsOver
     * Purpose:   Check if the game is over and show the gameover board
     * by adding new pane to the stack to display that the game is over
     * Parameter: none
     * Return:    void
     */
    private void gameIsOver() {
      //if statement to check if game is over
      if(board.isGameOver()==true){
      	//creating second pane for game over stack
      	GridPane pane2 = new GridPane();
      	//aligning the pane to the center
      	pane2.setAlignment(Pos.CENTER); 
      	//setting padding to the pane
		pane2.setPadding(new Insets(11.5,12.5,13.5,14.5)); 
		//adding horizontal gaps between the grid boxes
		pane2.setHgap(5.5); 
		//adding vertical gaps between the boxes
		pane2.setVgap(5.5);
		//creating text object to display
		Text forGameEnd = new Text();
		//giving text in string form to the object
		forGameEnd.setText("Game Over!");
		//setting the font for the text
		forGameEnd.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 75));
		//giving color to the text
		forGameEnd.setFill(COLOR_VALUE_DARK);
		//adding the text to the pane in the centre
		pane2.add(forGameEnd,(board.GRID_SIZE)/2,(board.GRID_SIZE)/2);
		//adding color and background to the pane
		pane2.setBackground(new Background(new BackgroundFill
			(COLOR_GAME_OVER, CornerRadii.EMPTY, Insets.EMPTY)));
		//adding the pane as the second layer of the stackPane
		stack.getChildren().add(pane2);
      }
    }
  } // End of Inner Class myKeyHandler.



  /*
   * Name:        Tile
   *
   * Purpose:     This class tile helps to make the tiles in the board 
   *              presented using JavaFX. Whenever a tile is needed,
   *              the constructor taking one char parameter is called
   *              and create certain ImageView fit to the char representation
   *              of the tile.
   * 
   *
   */
  private class Tile {

    private ImageView repr;   // This field is for the Rectangle of tile.
 
    /* 
     * Constructor
     *
     * Purpose: Initialises repr with the variety of possible tile appearances 
     * possible  
     * Parameter: char tileAppearance- to record which character is read from
     * to pick the appropriate image for it  
     *
     */
    public Tile(char tileAppearance) {
    	//checking for the character G to display ghosts
    	if(tileAppearance=='G'){
    		//creating an image object with the blinky image
    		Image image = new Image("image/blinky_left.png"); 
    		//giving repr ImageView  the image 
			repr = new ImageView(image);
			//set the width and height 
			repr.setFitWidth(50);  
			repr.setFitHeight(50);
    	}
    	//checking for the character P to display pacman
    	if(tileAppearance=='P'){
    		//creating an image object with the pacman image
    		Image image = new Image("image/pacman_right.png"); 
			//giving repr ImageView  the image 
			repr = new ImageView(image);
			//set the width and height 
			repr.setFitWidth(50);  
			repr.setFitHeight(50);
			//if statements that check the direction pacman has moved in 
			// and decide how to rotate the pacman image accordingly
			//if direction is right rotate by zero degrees
			if(directionRec.equals(Direction.RIGHT)){
				repr.setRotate(0);
			}
			//if direction is left rotate by 180 degrees
			else if(directionRec.equals(Direction.LEFT)){
				repr.setRotate(180);
			}
			//if direction is up rotate by 270 degrees
			else if(directionRec.equals(Direction.UP)){
				repr.setRotate(270);
			}
			//if direction is down rotate by 90 degrees
			else if(directionRec.equals(Direction.DOWN)){
				repr.setRotate(90);
			}
			
    	}
    	//checking for the character * to display uneaten dot
    	if(tileAppearance=='*'){
    		//creating an image object with the uneaten dot image
    		Image image = new Image("image/dot_uneaten.png"); 
    		//giving repr ImageView  the image 
			repr = new ImageView(image);
			//set the width and height 
			repr.setFitWidth(50);  
			repr.setFitHeight(50);
    	}
    	//checking for the character ' ' to display eaten dot
    	if(tileAppearance==' '){
    		//creating an image object with the eaten dot image
    		Image image = new Image("image/dot_eaten.png"); 
    		//giving repr ImageView  the image 
			repr = new ImageView(image);
			//set the width and height 
			repr.setFitWidth(50);  
			repr.setFitHeight(50);
    	}
    	//checking for the character X to display dead Pacman
    	if(tileAppearance=='X'){
    		//creating an image object with the dead pacman image
    		Image image = new Image("image/pacman_dead.png");
    		//giving repr ImageView  the image 
			repr = new ImageView(image);
			//set the width and height 
			repr.setFitWidth(50);
			repr.setFitHeight(50);
    	}
    	
    }

    //returns the repr instance variable
    public ImageView getNode() {
       return repr;
    }

  }  // End of Inner class Tile




  /** DO NOT EDIT BELOW */

  // The method used to process the command line arguments
  private void processArgs(String[] args)
  {
    String inputBoard = null;   // The filename for where to load the Board
    int boardSize = 0;          // The Size of the Board

    // Arguments must come in pairs
    if((args.length % 2) != 0)
    {
      printUsage();
      System.exit(-1);
    }

    // Process all the arguments 
    for(int i = 0; i < args.length; i += 2)
    {
      if(args[i].equals("-i"))
      {   // We are processing the argument that specifies
        // the input file to be used to set the board
        inputBoard = args[i + 1];
      }
      else if(args[i].equals("-o"))
      {   // We are processing the argument that specifies
        // the output file to be used to save the board
        outputBoard = args[i + 1];
      }
      else if(args[i].equals("-s"))
      {   // We are processing the argument that specifies
        // the size of the Board
        boardSize = Integer.parseInt(args[i + 1]);
      }
      else
      {   // Incorrect Argument 
        printUsage();
        System.exit(-1);
      }
    }

    // Set the default output file if none specified
    if(outputBoard == null)
      outputBoard = "Pac-Man.board";
    // Set the default Board size if none specified or less than 2
    if(boardSize < 3)
      boardSize = 10;

    // Initialize the Game Board
    try{
      if(inputBoard != null)
        board = new Board(inputBoard);
      else
        board = new Board(boardSize);
    }
    catch (Exception e)
    {
      System.out.println(e.getClass().getName() + " was thrown while creating a " +
          "Board from file " + inputBoard);
      System.out.println("Either your Board(String, Random) " +
          "Constructor is broken or the file isn't " +
          "formated correctly");
      System.exit(-1);
    }
  }

  // Print the Usage Message 
  private static void printUsage()
  {
    System.out.println("GuiPacman");
    System.out.println("Usage:  GuiPacman [-i|o file ...]");
    System.out.println();
    System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
    System.out.println();
    System.out.println("  -i [file]  -> Specifies a Pacman board that should be loaded");
    System.out.println();
    System.out.println("  -o [file]  -> Specifies a file that should be used to save the Pac-Man board");
    System.out.println("                If none specified then the default \"Pac-Man.board\" file will be used");
    System.out.println("  -s [size]  -> Specifies the size of the Pac-Man board if an input file hasn't been");
    System.out.println("                specified.  If both -s and -i are used, then the size of the board");
    System.out.println("                will be determined by the input file. The default size is 10.");
  }
}


