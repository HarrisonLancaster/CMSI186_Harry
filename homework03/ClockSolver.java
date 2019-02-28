/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  ClockSolver.java
 *  Purpose       :  The main program for the ClockSolver class
 *  @see
 *  @author       :  B.J. Johnson
 *  Date written  :  2017-02-28
 *  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
 *                   for Homework 4, part 1.  Includes the following:
  *
 *  Notes         :  None right now.  I'll add some as they occur.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class ClockSolver {
  /**
   *  Class field definintions go here
   */
   private static double MAX_TIME_SLICE_IN_SECONDS  = 1800.00;
   private static double DEFAULT_TIME_SLICE_SECONDS = 60.0;
   private static double EPSILON_VALUE              = 0.1;      // small value for double-precision comparisons
   private static double timeSlice = DEFAULT_TIME_SLICE_SECONDS;
   private static double targetAngle = 0.0;
  /**
   *  Constructor
   *  This just calls the superclass constructor, which is "Object"
   */
   public ClockSolver() {
      super();
   }

  /**
   *  Method to handle all the input arguments from the command line
   *   this sets up the variables for the simulation
   */
   public void handleInitialArguments( String[] args ) {
     // args[0] specifies the angle for which you are looking
     //  your simulation will find all the angles in the 12-hour day at which those angles occur
     // args[1] if present will specify a time slice value; if not present, defaults to 60 seconds
     // you may want to consider using args[2] for an "angle window"

      System.out.println( "\n   Hello world, from the ClockSolver program!!\n\n" ) ;
      if( 0 == args.length ) {
         System.out.println( "   Sorry you must enter at least one argument\n" +
                             "   Usage: java ClockSolver <angle> [timeSlice]\n" +
                             "   Please try again...........\n" );
         System.exit( 1 );
      }
      else if ( 1 == args.length ) {
       targetAngle = Double.parseDouble(args[0]);
       if( targetAngle >= 360 || targetAngle < 0) {
          throw new IllegalArgumentException("Angle must be greater than or equal to 0 degrees, and less than 360 degrees.");
          }
       timeSlice = 60.0;
      }
      else if ( 2 == args.length ) {
       targetAngle = Double.parseDouble(args[0]);
       timeSlice = Double.parseDouble(args[1]);
     }
      if( targetAngle >= 360 || targetAngle < 0) {
          throw new IllegalArgumentException("Angle must be greater than or equal to 0 degrees, and less than 360 degrees.");
          }
      if ( timeSlice > MAX_TIME_SLICE_IN_SECONDS || timeSlice < 0){
        throw new IllegalArgumentException("Please enter a non-negative value for time that is less than 1800 seconds.");
      }
    }
  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  @param  args  String array of the arguments from the command line
   *                args[0] is the angle for which we are looking
   *                args[1] is the time slice; this is optional and defaults to 60 seconds
   */
   public static void main( String[] args ) {
      ClockSolver cse = new ClockSolver();    
      cse.handleInitialArguments( args );
      Clock clock    = new Clock( timeSlice );

      // double totalTicksNeeded = 60*60*24*(60/timeSlice);

      double[] timeValues = new double[3];


      while( clock.getTotalSeconds() < 43200) {
        // System.out.println("HAND: " + clock.getHandAngle());
        clock.tick();
        // System.out.println("=====================");
        // System.out.println(clock.getTotalSeconds() + " // " + targetAngle + " // " + clock.getHandAngle());
        // System.out.println(clock.toString());
        // System.out.println(clock.getHandAngle());
        if ( Math.abs(targetAngle - clock.getHandAngle()) <= EPSILON_VALUE) {
          // System.out.println("*********************");
          System.out.println(clock.toString());
          // timeValues[0] = clock.toString();
        }
        // clock.tick += 1;
         // break;
      }
      // System.out.println(timeValues[0]);
      // System.out.println(timeValues[1]);
      // System.out.println(timeValues[2]);

      System.exit( 0 );
   // }
  }
}
