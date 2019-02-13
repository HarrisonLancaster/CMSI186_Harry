/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  MainProgLoopDemo.java
 *  Purpose       :  Demonstrates the use of input from a command line for use with Yahtzee
 *  Author        :  B.J. Johnson
 *  Date          :  2017-02-14
 *  Description   :  
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-14  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HighRoll{

   public static void main( String args[] ) {
        if(args.length == 0 || args.length == 1 || args.length > 2) {
        System.out.println("You need 2 arguments");
        System.exit(0);
      }

      System.out.println("\n   Welcome to the Die Bowl!!\n" );
      System.out.println("\n Enter '1' to ROLL ALL THE DICE \n");
      System.out.println("\n Enter '2' to ROLL A SINGLE DIE \n");
      System.out.println("\n Enter '3' to CALCULATE THE SCORE FOR THIS SET \n");
      System.out.println("\n Enter '4' to SAVE THIS SCORE AS HIGH SCORE \n");
      System.out.println("\n Enter '5' to DISPLAY THE HIGH SCORE \n");
      System.out.println("\n Or, ENTER 'Q' TO QUIT THE GAME \n" );

    

      int count = Integer.parseInt( args[0]);
      int sides = Integer.parseInt( args[1]); 
      DiceSet ds = new DiceSet(count,sides);
      int highScore = 0;

     // This line uses the two classes to assemble an "input stream" for the user to type
     // text into the program
      BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
      while( true ) {
         System.out.println(ds.toString());
         System.out.print( ">>" );
         String inputLine = null;
         try {

            inputLine = input.readLine();
            System.out.println(inputLine);
            if( inputLine.length() == 0) {
               System.out.println("Choose an option from the menu");
            } else if( '1' == inputLine.charAt(0) ) {
               ds.roll();
               System.out.println("\nYou rolled:" + ds.toString() +"\n");
            } else if( '2' == inputLine.charAt(0)) {
               System.out.println("\n Enter the index of the die you want to roll \n");
               System.out.println(">>");
               int die2Roll = Integer.parseInt(input.readLine());
               ds.rollIndividual(die2Roll);
               System.out.println("\n You rolled:" + ds.toString() + "\n" );
            } else if( '3' == inputLine.charAt(0)){
               System.out.println("\n The sum of your roll is:" + ds.sum() + "\n");
            } else if( '4' == inputLine.charAt(0)){ 
               highScore = ds.sum();
               System.out.println("\n The HIGH SCORE is:" + highScore + "\n");
            } else if ( '5' == inputLine.charAt(0)) {
               System.out.println("\nYour last saved HIGH SCORE was:" + highScore + "\n");
            } else if( 'q' == inputLine.charAt(0) ) {
               break;
            }         
         } catch( IOException ioe ) {
            System.out.println( "Caught IOException" );
         }
      }
   }
}
