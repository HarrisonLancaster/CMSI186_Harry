import java.util.ArrayList;

class Riemann {
  double area1 = 0;
  double area2 = 0;
  double percentage = 0.01;
  double percentDif;
  String function;
  ArrayList<Double> functionCoefficients = new ArrayList<Double>();
  double LB;
  double UB;
  double deltaX;
  int nBoxes = 1;
  double width;
  double midpt;
  double xVal;
  double yVal;
  int LBi;
  double totalArea;

public double getdx() {
  this.deltaX = (this.UB - this.LB);
  return this.deltaX;
}

public double getWidth() {
  this.width = this.deltaX/this.nBoxes;
  return width;
}

public double getMidpt(){
  this.midpt = this.width / 2;
  return this.midpt;
}

public boolean checkPercent( String[] args ) {
  boolean present = false;
  if( args[args.length-1].indexOf("%") != -1 ){
    present = true;
} 
return present;
}

// public double convertPercent(String [] args){
//   percent = args[args.length-1].trim().replace("%", "");
//   percent = Double.parseDouble(percent); 
//   percent = percent/100;
// }

public void handleArgs(String [] args){
  System.out.println("Hello and Welcome to Riemman. Let's use geometry to estimate some integrals.");
  //System.out.println(PROPER INVOCATION OF THE PROGRAM)

  this.function = args[0];

  if( this.checkPercent(args) ){

    this.percentage = Double.parseDouble( args[args.length-1]);
    this.LB = Double.parseDouble(args[args.length - 3]);
    this.UB = Double.parseDouble(args[args.length - 2]);
    if ( this.percentage <= 0  ) {
      System.out.println( " A negative or zero value for the percentage check is not reasonable/ realistic.\n" +
                          " Please enter a positive non-zero value\n");
      System.exit(1);

    }
  
  } else if ( !this.checkPercent(args)  ) {

    this.LB = Double.parseDouble(args[args.length - 2]);
    this.UB = Double.parseDouble(args[args.length - 1]);

  }

  if( this.checkPercent(args)) {
    this.LBi = Integer.parseInt(args[args.length-3]);
  } else if (!this.checkPercent(args)) {

    this.LBi = Integer.parseInt(args[args.length-2]);
  }



  for (int i = 1; i < this.LBi; i++ ) {
    double coefficient = Double.parseDouble(args[i]);
    functionCoefficients.add(coefficient);

  }

  if ( function == "poly" && functionCoefficients.size() == 0 ) {
    System.out.println(" You must enter at least one coefficient when calling the polynomial function.\n");
    System.exit(1);
  }
}

public String getFunc(){
  return this.function;
}

public ArrayList<Double> getCoeff(){
  return this.functionCoefficients;
}

public double getLB() {
  return this.LB;
}

public double getUB(){
  return this.UB;
}

public double getPercentage(){
  return this.percentage;
}

public double percentDifference(){
  Double difference = Math.abs(this.area2-this.area1)/this.area2 * 100;
  return difference;
}

public void updateX() {
  this.xVal = LB + midpt;
  for (int i = 0; i < nBoxes; i++) {
    xVal += width*i;
  }
}

public double getX() {
  return this.xVal;
}

public double getY() {
  return this.yVal;
}

public void updateY( ArrayList<Double>functionCoefficients ) {
  this.yVal = 0.0;
  for ( int i = 0; i < this.functionCoefficients.size(); i++  ) {
    yVal += this.functionCoefficients.get(i) * Math.pow( this.xVal, i);
  }
}


public void updateArea() {
  double area = 0.0;

  for (int i = 0; i < nBoxes; i++) {
    area = this.width * this.yVal;
    this.totalArea += area;
  }

}

public double getArea(){
  return this.totalArea;
}


public String toString() {
  String result = "";
  result += "The total area is " + Double.toString(this.getArea()) + "\n";
  return result;

}


public static void main(String[] args) {
  Riemann r1 = new Riemann(); 
  r1.handleArgs(args);
  switch(args[0]) {
    case "poly": 
    r1.area1 = r1.getArea();
      while(r1.percentDifference() > r1.getPercentage()) {
        r1.area2 = r1.getArea(); 
        if( r1.percentDifference() <= r1.getPercentage() ) {
          break;
        }
        r1.area1 = r1.area2;
      r1.nBoxes ++;
  }
    // case "sin":
    //   calcSin(params);
    //   break;
  }
}

}









