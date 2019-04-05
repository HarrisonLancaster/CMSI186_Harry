import java.util.ArrayList;

class Riemann1 {
  double previous = 0;
  double current = 0;
  double percentage = 0.01;
  String tempPerc;
  String function;
  ArrayList<Double> coeffs = new ArrayList<Double>();
  double LB;
  double UB;
  int nBoxes = 1;
  double width;
  double midpt;
  double yVal;
  int LBi;
  double totalArea;
  String func;

public boolean checkPercent( String[] args ) {
  boolean present = false;
  if( args[args.length-1].indexOf("%") != -1 ){
    present = true;
} 
return present;
}

public void handleArgs(String [] args){
  System.out.println("Hello and Welcome to Riemman. Let's use geometry to estimate some integrals. \n");
  System.out.println("PRINTING ARGS.LENGTH: " + args.length);
  //System.out.println(PROPER INVOCATION OF THE PROGRAM)

  function = args[0];

  if(args[0] == "poly" && args.length <= 3) {
    System.out.println(" You must have at least one coefficient for a polynomial function.");
    System.exit(1);
  }


  if( this.checkPercent(args) ){

    tempPerc = args[args.length-1].trim().replace("%","");
    percentage = Double.parseDouble(tempPerc);
    percentage = percentage / 100;
    LBi = args.length-3;
    System.out.println("PRINTING ARGS.LENGTH: " + args.length);
    // System.out.println("PRINTING LBI: " + LBi);
    LB = Double.parseDouble(args[args.length - 3]);
    UB = Double.parseDouble(args[args.length - 2]);
    if ( percentage <= 0  ) {
      System.out.println( " A negative or zero value for the percentage check is not reasonable/ realistic.\n" +
                          " Please enter a positive non-zero value\n");
      System.exit(1);

    }
  
  } else if ( !this.checkPercent(args)  ) {
    LBi = args.length-2;
    LB = Double.parseDouble(args[args.length - 2]);
    UB = Double.parseDouble(args[args.length - 1]);

  }
  //  if( this.checkPercent(args)) {
  //   LBi = Integer.parseInt(args[args.length-3]);
  // } else if (!this.checkPercent(args)) {

  //   LBi = Integer.parseInt(args[args.length-2]);
  // }



  for (int i = 1; i < this.LBi; i++ ) {
    double coefficient = Double.parseDouble(args[i]);
    coeffs.add(coefficient);

  }

  if ( function == "poly" && coeffs.size() == 0 ) {
    System.out.println(" You must enter at least one coefficient when calling the polynomial function.\n");
    System.exit(1);
  }
}


  public double integratePoly(ArrayList<Double> coeffs, double LB, double UB, int nBoxes){
    totalArea = 0.0;
    width = (UB-LB)/(double)nBoxes;
    // System.out.println("UB, LB, and nBoxes are " + UB + " " + LB + " " + nBoxes);
    // System.out.println("the width is ! " + width);
    midpt = width/2.0;
    // yVal = 0.0;
    for ( int j = 0; j < nBoxes; j++){
      yVal = 0.0;
      double xVal = LB + (midpt) + j * width;
      for ( int i = 0; i < coeffs.size(); i++){
        // System.out.println("PRINTING LBi: " + LBi);
        // System.out.println("PRINT xVal: " + xVal);
        // System.out.println("PRINT WIDTH: " + width);
        // System.out.println("PRINT COEFFICIENTS:" + coeffs);
        // System.out.println("PRINT Coefficient: " + coeffs.get(i));
        // System.out.println("PRINT Math.pow(XVals): " + Math.pow(xVal, i));

        yVal += coeffs.get(i)* Math.pow(xVal, i);
        // System.out.println("\nPRINT yVals: " + yVal + "\n");
      }
      // System.out.println("yval and width are " + yVal + " " + width);
      // System.out.println("we are adding ___ to the area: " + (yVal*width));
      totalArea += yVal*width;
    } 

    return totalArea;
  }

  public double integrateSin(ArrayList<Double> coeffs, double LB, double UB, int nBoxes){
    totalArea = 0.0;
    width = (UB-LB)/nBoxes;
    midpt = width/2.0;
    yVal = 0.0;
    for ( int j = 0; j < nBoxes; j++){
      yVal = 0.0;
      double xVal = LB + (midpt) + j * width;
      for( int i = 0; i < coeffs.size(); i++){
        System.out.println("FOR LOOP: " + coeffs.get(i)* Math.pow(xVal, i));
        yVal += Math.sin(coeffs.get(i)* Math.pow(xVal, i));
      }
      // System.out.println("PRINT OUT YVAL: " + yVal);
      totalArea += yVal*width;
    }
    return totalArea;
  }

  public double percentDifference(double current, double previous){
    double difference = Math.abs(current-previous)/previous * 100;
  return difference;
  }

  public String funcPoly(){
    func = "y = ";

        for ( int i = 0; i < coeffs.size(); i++){
           if( i == coeffs.size()-1) {
            func += coeffs.get(i) + "x^" + i;
        } else {
            func += coeffs.get(i) + "x^" + i + " + ";
          }
    }
    return func;

  }

  public String toString() {
    String result = "";
    result += "The function type is " + this.function + "\n";
    result += "The function is " + this.funcPoly() + "\n"; 
    result += "The lower bound is " + this.LB + "\n";
    result += "The upper bound is " + this.UB + "\n";
    result += "The percentage is " + this.percentage*100 + "%" + "\n";
    result += "The previous area is " + this.previous + "\n";
    result += "The current area is " + this.current + "\n";
    result += "The number of boxes being used is " + this.nBoxes + "\n";
    return result;
  }
  

  public static void main(String[] args){
    Riemann1 r1 = new Riemann1();
    r1.handleArgs(args);
    switch(args[0]){
      case "poly":
       
       
        while (true) {

          r1.current = r1.integratePoly(r1.coeffs,r1.LB,r1.UB,r1.nBoxes);
       
          System.out.println(r1);

          System.out.println("The current percentage difference: " + r1.percentDifference(r1.current, r1.previous)*100 + "%\n");

          if (r1.nBoxes > 1 && r1.percentDifference(r1.current, r1.previous) <= r1.percentage){
            System.out.println("It took " + r1.nBoxes + " boxes to calculate a Riemann Sum accurate  within the percentage specified.\n" + "The estimated area under the curve is " + r1.totalArea + "\n");
            
            break;
          }
          
          r1.previous = r1.current;
          r1.nBoxes++;
          
        } 
        break;
    case "sin": 

    while (true) {

          r1.current = r1.integrateSin(r1.coeffs,r1.LB,r1.UB,r1.nBoxes);
       
          System.out.println(r1);

          System.out.println("The current percentage difference: " + r1.percentDifference(r1.current, r1.previous)*100 + "%\n");

          if (r1.nBoxes > 1 && Math.abs(r1.percentDifference(r1.current, r1.previous)) <= r1.percentage){
            System.out.println("It took " + r1.nBoxes + " boxes to calculate a Riemann Sum accurate  within the percentage specified.\n" + "The estimated area under the curve is " + r1.totalArea + "\n");
            
            break;
          }
          
          r1.previous = r1.current;
          r1.nBoxes++;
          
        } 
        break;

    }

  }
}