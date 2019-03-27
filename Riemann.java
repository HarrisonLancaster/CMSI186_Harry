class Riemann {

// invocation: java Riemann poly 00 8.0 -2.0 1.0 4.0 0.5%

//commandArgs[args.length-1] = optional percentage value (defaults to 1%) --> the two preceding values are the upper and lower bounds 

// everything btwn commandArgs[0] & the final 2/3 values will be coefficients of every term of the polynomial

//the number of terms having coefficients is also equal to the order of the polynomial



//in String Class we need a substring Method that can 

//extract the percent value of type double from the last value in the command args


//MUST implement functions for: 
// 1. polynomials of degree 2+
// 2. the sin function
// 3. +1 point for every addtional function accounted for (cos, tan, log and exponentiation functions, composite functions [ie sqrt( 1 + cos(x)], etc...))])


//poly double poly(Lb, Ub, coeffs[], n)
//Lb & Ub = lower & upper bounds, respectively
// coeffs[] are coefficients of each x term
// n = number of riemann boxes to use for approximation


private double handleArgs(String [] args){
  System.out.println("Hello and Welcome to Riemman. Let's use geometry to estimate some integrals.");
  if( args.length.substring(0) == "%"  ){
    
  }
}

public static void main(String[] args) {
  //1. validate args
  switch(args[0]) {
    case "poly": while(----) {
      current = calcPoly(Lb, Ub, coeffs[],n);
      if(1-(current/previous) <= percent) {
        break;
      }
    } break;
    case "sin":
  }
}








}