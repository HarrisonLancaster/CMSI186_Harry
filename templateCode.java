public double UB;
public double LB;
public double dX;
public int n;
public ArrayList<double> coeffs; 
public double percentage; 
public int loopCount;

String[] temp = null;
if( args[args.length-1].indexOf("%") == -1) {
  temp = new String[args.length + 1];
  temp[args.length] = "1%";
} else { 
  temp = new String[args.length];
}

coeffs.size(temp.length-4); 

for( int i = 1; i < temp.length-3; i++) {
  coeffs(i-1) = Double.parseDouble(temp[i]); 
}

String tempPercent = temp[temp.length-1].substr(0,temp[temp.length-1)].indexOf('%');

percentage = Double.parseDouble(tempPercent)/100;


LB = Double.parseDouble(temp[temp.length-3]);
UB = Double.parseDouble(temp[temp.length-2]);

n = 1; 

dX = (UB-LB)/n;

