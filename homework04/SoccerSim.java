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

    // public double getXLocation() {
    // 	this.x = this.x + this.dx * this.timeSlice;
    //     return this.x;
    // }

    // public double getYLocation() {
    // 	this.y = this.y + this.dy * this.timeSlice;
    //     return this.y;
    // }

    public double[] getLocation() {
        double[] location = new double[]{this.x, this.y};
        return location;
    }

    // public boolean isInBounds() {
    //    if(this.x <= 100 && this.y <= 100) {
    //     return true;
    //    } else {
    // 	return false;
    //    }

    // }

    public void update(double timeSlice) {
        this.x = this.x + this.dx * timeSlice;
        this.y = this.y + this.dy * timeSlice;
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
    private double boardSize; // 100 inches by 100 inches
    private double[] pole;
    private double radius;
    private double time;
    private double timeSlice;
    private ArrayList<Ball> collisions = new ArrayList<Ball>();
    private double collisionDistance = 9.9;

    // constructor for Board class
    public Board(Ball[] balls, double timeSlice) {
        this.balls = balls;

        this.boardSize = 100; // 100 inches by 100 inches
        this.pole = new double[] {10, 10};
        this.radius = 4.45;

        this.time = 0;
        this.timeSlice = timeSlice;
    }

    public boolean isInBounds(Ball ball) {
        if(ball.getX() <= this.boardSize && ball.getY() <= this.boardSize) {
         return true;
        } else {
         return false;
        }
 
     }

    public Ball[] checkForCollisions() {
        // do some math
        // check for collisions

        Ball [] result = new Ball[this.balls.length];

        for ( int i = 0; i < this.balls.length; i++ ) {
            Ball ballOne = this.balls[i];
            double x1 = ballOne.getX();
            double y1 = ballOne.getY();
            if ( this.isInBounds(ballOne) ) {
                for ( int j = i+1; j < this.balls.length; j++ ) {
                    Ball ballTwo = this.balls[j];
                    double x2 = ballTwo.getX();
                    double y2 = ballTwo.getY();
                    if ( this.isInBounds(ballTwo) ) {
                    	

                    
                    }
                    // if other_ball.isInBounds() {
                    //     check if ball is colliding with other_ball
                    //     do some math with the two balls' locations:
                    //         ball.getLocation()
                    //         other_ball.getLocation()

                    // }



                }
            }
        }
        // return array of colliding balls, if there are none, array is empty;
        return result;
    }

    public double getTime() {
        return this.time;
    }

    public boolean checkForAllBallsStopped() {
        // iterate over the balls and check if they're all come to a stop
        // if so, return True other wise return False
        
        return false;
    }

    public void update() {
        // update ball locations based on speed
        this.time += this.timeSlice;

        for (int i = 0; i < this.balls.length; i++) {
            this.balls[i].update(this.timeSlice);
        }



    }

    public String toString() {
        String result = "Time: " + this.time + "\n";
        // result += Arrays.toString(this.balls);

        for (int i = 0; i < this.balls.length; i++) {
            result += "Ball " + Integer.toString( i + 1 ) + ": ";
            result += this.balls[i].toString() + "\n";
        }

        return result;
    }
}

// // Timer.java
// // ---------------------------------------
// class Timer {
//     private double elapsedTime;
//     private double timeSlice;

//     public Timer(double timeSlice) {
//         this.timeSlice = timeSlice;
//         this.elapsedTime = 0;
//     }

//     public double getTime() {
//         this.elapsedTime += timeSlice;
//         return this.elapsedTime;
//     }


// }


// SoccerSim.java
// ----------------------------------------
public class SoccerSim {

    public SoccerSim() {
        super();
    }

    // private void handleArguments(String [] args) {

    // }
    
    public static void main( String [] args ) {
        SoccerSim sim = new SoccerSim();
        double timeSlice = 0;
        Ball[] balls = new Ball[(int)(args.length / 4)];

        // double time = 0;

        if (args.length % 2 != 0) {
            // args is odd length means we have a timeSlice
            timeSlice = Double.parseDouble(args[args.length]);
        } else {
            timeSlice = 1;
        }

        int ballIndex = 0;
        for (int i = 0; i < args.length; i+=4) {
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


        System.out.println(board);
        while (!board.checkForAllBallsStopped() || collisions.length != 0) {
            Ball[] checkCollisions = board.checkForCollisions();

            // System.out.println("time: " + time)
            board.update();
            System.out.println(board);

            if (board.getTime() >= 10) {
                System.exit(0);
            }

            // if (board.hasCollision()) {
            //     // print something
            //     System.exit(0);
            // }
        }


        if (collisions.length == 0) {
            System.out.println("NO COLLISIONS POSSIBLE");
        }else {
            System.out.println(collisions);
        }

        System.exit(0);





    }
}

// i = 0              i=4              i=8            i=12
// (x, y, x_v, y_v) (x, y, x_v, y_v) (x, y, x_v, y_v) .... (timeSlice)