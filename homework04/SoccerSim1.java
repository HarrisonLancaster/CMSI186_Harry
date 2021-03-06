// 1. Board
// 2. Ball
// 3. Clock
// 4. SoccerSim



//  t_0 (start)
//  all balls are at initial locations

//  t_1 (which is timeSlice seconds after t_0)
//  all balls have moved a discrete amount from their initial locations

// 1. SoccerSim:
//     main() {
//         1. Parse arguments from command line: (x, y, dx, dy, timeSlice=1) ...
//             1. Create a ball for each set of args
//         2. Create a board instance: Board board = new Board(int size);
//             1. 
//     }


// Ball.java
// ---------------------------------------

import java.util.ArrayList;
import java.util.Arrays;

class Ball {
    private double x;
    private double y;
    private double dx;
    private double dy;

    public Ball(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getDx() {
        return this.dx;
    }

    public double getDy() {
        return this.dy;
    }


    public double[] getLocation() {
        double[] location = new double[]{this.x, this.y};
        return location;
    }


    public void update(double timeSlice) {
        this.x = this.x + this.dx * timeSlice;
        this.y = this.y + this.dy * timeSlice;
        this.dx = this.dx*Math.pow(0.99, timeSlice); //accounts for Force of friction in x direc
        this.dy = this.dy*Math.pow(0.99, timeSlice); //accounts for Friciton in y direc
    }


    public String toString() {
        return "x: " + this.x + ",\ty: " + this.y + ",\tdx: " + this.dx + ",\tdy: " + this.dy;
    }

    public static void main (String [] args) {

        Ball b = new Ball(1,2,3,4);
        System.out.println(b);
    }
}

// Board.java
// -----------------------------------------
class Board {

    private Ball[] balls;
    private double boardSize; // 1000 ft by 1000 ft
    private double time;
    private double poleLoc;
    private double poleRad = 4.45;
    private double timeSlice;
    private double collisionDistance = poleRad; //inches

    // constructor for Board class
    public Board(Ball[] balls, double timeSlice) {
        this.balls = balls;
        this.boardSize = 1000; // 100 inches by 100 inches
        this.time = 0;
        this.timeSlice = timeSlice;
        this.poleLoc = Math.random()*this.boardSize;
    }

    public boolean isInBounds(Ball ball) {
        if(ball.getX() <= this.boardSize*12 && ball.getY() <= this.boardSize*12) {
         return true;
        } else {
         return false;
        }
 
     }

    public Collision checkForCollisions() {
        // Ball [] result = new Ball[this.balls.length];
        Collision c1 = new Collision(this.time);

        for ( int i = 0; i < this.balls.length; i++ ) {
            Ball ballOne = this.balls[i];
            double x1 = ballOne.getX()*12; //inches
            double y1 = ballOne.getY()*12; //inches;
            if ( this.isInBounds(ballOne) ) {
                for ( int j = i+1; j < this.balls.length; j++ ) {
                    Ball ballTwo = this.balls[j];
                    double x2 = ballTwo.getX()*12;
                    double y2 = ballTwo.getY()*12;
                    if ( this.isInBounds(ballTwo) && ballOne != ballTwo ) {
                        double distance = Math.sqrt( ((x2-x1)*(x2-x1)) + ((y2-y1)*(y2-y1)));
                
                        if ( distance <= this.collisionDistance ) {
                            System.out.println("Balls " + (i+1) + " and " + (j+1) + " collided.");
                            System.out.println("Collision time is " + getTime() + "." );
                            System.exit(0);
                            // // System.out.println("PRINTING BALLS");
                            // c1.addBall(ballOne);
                            // c1.addIndex(i + 1);
                            // // System.out.println(ballOne);
                            // c1.addBall(ballTwo);
                            // c1.addIndex(j + 1);
                            // // System.out.println(ballTwo);
                            // // System.out.println(c1);
                        }

                    
                    }
                }
            }
        }
        for ( int i = 0; i < this.balls.length; i++ ) {
            Ball poleBall = this.balls[i];
            double xp = poleBall.getX()*12; //inches;
            double yp = poleBall.getY()*12;
            if ( this.isInBounds(poleBall) ) {
                double poleDistance = Math.sqrt( (xp-this.poleLoc*12)*(xp-this.poleLoc*12) + (yp-this.poleLoc*12)*(yp-this.poleLoc*12) );
                if ( poleDistance <= this.collisionDistance ) {
                    System.out.println("Ball " + (i+1) + " and the pole collided.");
                    System.out.println("Collision time is " + getTime() + "." );
                    System.exit(0);
            }
        }
        // new for loop checking balls with pole

        }
        // return array of colliding balls, if there are none, array is empty;
        return c1;
    }

    public double getTime() {
        return this.time;
    }

    public boolean checkForBallsMoving() {
        for (int i = 0; i < this.balls.length; i++) {
            Ball ball = balls[i];
            if(Math.abs(ball.getDx()) > 0.083 || Math.abs(ball.getDy()) > 0.083 ){
                return false;
            }
        }
        System.out.println("NO COLLISIONS POSSIBLE.");
        return true;
    }

    public void update() {
        // update ball locations based on speed
        this.time += this.timeSlice;

        for (int i = 0; i < this.balls.length; i++) {
            this.balls[i].update(this.timeSlice);
        }



    }

    public String toString() {
        String result = "Time: " + this.time + "\n" + "Board Size: " + "(" + this.boardSize + " ft , " + this.boardSize + " ft ) \n" + "Pole Location: (" + this.poleLoc + " ft ," + this.poleLoc + " ft ) \n" + "Pole Size: " + this.poleRad + " in \n";

        for (int i = 0; i < this.balls.length; i++) {
            result += "Ball " + Integer.toString( i + 1 ) + " (Located in inches) : ";
            result += this.balls[i].toString() + "\n";
        }

        return result;
    }
}

//Collision.java
//--------------------------------------

class Collision {
    private double colTime;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private ArrayList<Integer> ballIndex = new ArrayList<Integer>();

    public Collision(double time) {
        this.colTime = time;
    }

    public void addBall(Ball b) {
        this.balls.add(b);
    }

    public void addIndex(int apple) {
        this.ballIndex.add(apple);
    }

    public ArrayList<Ball> getBalls() {
        return this.balls;
    }

    public String toString() {
        String result = "";
        // result += Arrays.toString(this.balls);

        for (int i = 0; i < this.balls.size(); i++) {
            result += "Ball " + Integer.toString( this.ballIndex.get(i) ) + ": ";
            result += this.balls.get(i).toString() + "\n";
        }

        return result;
    }

}


// SoccerSim.java
// ----------------------------------------
public class SoccerSim1 {
    private double timeSlice;
    public SoccerSim1() {
        super();
    }

    private double handleArgumentsAndGetTimeSlice(String [] args) {

        System.out.println( "\n   Hello, welcome to the SoccerSim program!!\n\n" ) ;
        if( 4 > args.length ) {
           System.out.println( "   Sorry you must enter at least four arguments\n" +
                               "   Usage: java SoccerSim <x> <y> <dx> <dy> and the optional [timeSlice]\n" +
                               "   Please try again...........\n" );
           System.exit( 1 );
        } else if ( args.length % 4 != 0 && args.length % 4 != 1) {
            System.out.println ( " Sorry, you must enter some multiple of four arguments\n" +
                                 " Or, you can enter a multiple of four arguments plus an extra argument on the end for the optional timeSlice\n" +
                                 " Please try again...........\n");
            System.exit( 1 );
        } else if ( args.length % 4 == 0) {
            timeSlice = 1;
            return timeSlice;
        } else if ( args.length % 4 == 1) {
            timeSlice = Double.parseDouble(args[args.length-1]);
            return timeSlice;
        }
        return 0;

    }

    public static void main( String [] args ) {
        SoccerSim1 sim = new SoccerSim1();
        double timeSlice = sim.handleArgumentsAndGetTimeSlice(args);
        // sim.handleArguments(args);
        Ball[] balls = new Ball[(int)(args.length / 4)];

        // double time = 0;

        // if (args.length % 2 != 0) {
        //     // args is odd length means we have a timeSlice
        //     timeSlice = Double.parseDouble(args[args.length]);
        // } else {
        //     timeSlice = 1;
        // }

        int ballIndex = 0;
        for (int i = 0; i < args.length-1; i+=4) {
            double x = Double.parseDouble(args[i]);
            double y = Double.parseDouble(args[i + 1]);
            double dx = Double.parseDouble(args[i + 2]);
            double dy = Double.parseDouble(args[i + 3]);

            Ball ball = new Ball(x, y, dx, dy);
            balls[ballIndex] = ball;
            ballIndex++;
        }

        Board board = new Board(balls, timeSlice);
        // Clock clock = new Clock(timeSlice);

        Ball[] collisions = new Ball[(int)(args.length / 4)];

        System.out.println("PRINTING BOARD");
        System.out.println(board);

        Collision checkCollisions = board.checkForCollisions();
        // System.out.println("PRINTING BOARD FIRST");
        // System.out.println(board);
        while (!board.checkForBallsMoving() || collisions.length == 0) {
            checkCollisions = board.checkForCollisions();
            ArrayList<Ball> collidingBalls = checkCollisions.getBalls();

            System.out.println("PRINTING BOARD");
            board.update();
            System.out.println(board);

            // System.out.println("COLLISIONS:");
            System.out.println(checkCollisions);

            for (int i = 0; i < collidingBalls.size(); i++) {
                System.out.println("PRINTING COLLIDING BALLS");
            }
            
        }
        System.exit(0);
    }
}