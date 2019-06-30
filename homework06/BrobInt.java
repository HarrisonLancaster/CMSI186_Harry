/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  B.J. Johnson
 * Date       :  2017-04-04
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                     validateDigits, two reversers, and valueOf methods; revamped equals
 *                                     and compareTo methods to use the Java String methods; ready to
 *                                     start work on subtractByte and subtractInt methods
 *  1.2.0  2019-04-18  B.J. Johnson  Fixed bug in add() method that was causing errors in Collatz
 *                                     sequence.  Added some tests into the main() method at the bottom
 *                                     of the file to test construction.  Also added two tests there to
 *                                     test multiplication by three and times-3-plus-1 operations
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BrobInt {

   public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
   public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
   public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
   public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
   public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
   public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
   public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
   public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
   public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
   public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
   public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"

//   /// Some constants for other intrinsic data types
//   ///  these can help speed up the math if they fit into the proper memory space
//    public static final BrobInt MAX_INT  = new BrobInt( Integer.valueOf( Integer.MAX_VALUE ).toString() );
//    public static final BrobInt MIN_INT  = new BrobInt( Integer.valueOf( Integer.MIN_VALUE ).toString() );
//    public static final BrobInt MAX_LONG = new BrobInt( Long.valueOf( Long.MAX_VALUE ).toString() );
//    public static final BrobInt MIN_LONG = new BrobInt( Long.valueOf( Long.MIN_VALUE ).toString() );

  /// These are the internal fields
   public  String internalValue = "";        // internal String representation of this BrobInt
   public  byte   sign          = 0;         // "0" is positive, "1" is negative
   public int[] digits;

  /// You can use this or not, as you see fit.  The explanation was provided in class
   // private String reversed      = "";        // the backwards version of the internal String representation
   private static BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
   private static final boolean DEBUG_ON = false;
   private static final boolean INFO_ON  = false;

  /**
   *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
   *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
   *   for later use
   *  @param  value  String value to make into a BrobInt
   */
  public BrobInt(String internalValue) {
    this.internalValue = internalValue;
    if ( this.internalValue.charAt(0) == '-' ) {
      sign = 1;
      this.internalValue = this.internalValue.substring(1);
   } else if ( this.internalValue.charAt(0) == '+' ) {
      this.internalValue = this.internalValue.substring(1);
   }

   this.validateDigits();

    int length = internalValue.length();
    int chunks = length/9;

    int end = length - 1; 
    int start = end - 8;
    if( length <= 9) {
       start = 0;
    }

    if(length % 9 != 0) {
       chunks++;
    } 

    digits = new int[chunks];

    if( length <= 9) {
       digits[0] = Integer.parseInt(internalValue);
    } else {
       int i = 0;
         for( i = 0; i < chunks-1; i++) {
            digits[i] = Integer.parseInt(internalValue.substring(start, (end + 1))); // REVERSE internal value here bc end and start definitions
            end -= 9;
               if( i != chunks - 1 ) {
                  start = end - 8;
               } else {
                  start = 0;
               }
         }
         // System.out.println("START = " + start + " END = " + end);
         if( start < 0) {
            start = 0;
               if( start == end) {
                  digits[i] = (int) (internalValue.charAt(0) - 48);
               } else {
                  digits[i] = Integer.parseInt(internalValue.substring(start, (end + 1)));
               }
         }
      // toArray(digits);
  }
}

  private boolean isNumeric(char value) {
   //   System.out.print(value);
        return value >= '0' && value <= '9';
   
   }



  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to validate that all the characters in the value are valid decimal digits
   *  @return  boolean  true if all digits are good
   *  @throws  IllegalArgumentException if something is hinky
   *  note that there is no return false, because of throwing the exception
   *  note also that this must check for the '+' and '-' sign digits
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean validateDigits() throws IllegalArgumentException {
    int length = internalValue.length();
    int j = 0;
    if( internalValue.charAt(0) == '-' || internalValue.charAt(0) == '+' ) {
       j = 1;
    }
    for( int i = j; i < length; i++){
      if(!isNumeric(internalValue.charAt(i))){
        throw new IllegalArgumentException("Only inputs with digits 0 thru 9 are allowed.");
      }
    }
    return true;
   }


//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to add the value of a BrobIntk passed as argument to this BrobInt
//    *  @param  bint         BrobInt to add to this
//    *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt add( BrobInt bint ) {
      int carry = 0;
      int shorter;
      int longer;

      if( this.equals(ZERO)) {
         return bint;
      }

      if( bint.equals(ZERO)) {
         return this;
      }

      if( digits.length > bint.digits.length) {
         shorter = bint.digits.length;
         longer = digits.length;
      } else {
         shorter = digits.length;
         longer = bint.digits.length; 
      }
      int[] total = new int[longer];

      for( int i = 0; i < shorter; i++) {
         total[i] = digits[i] + bint.digits[i] + carry;
         if( total[i] > 999999999 ) {
            total[i] -= 1000000000; 
            carry = 1;
         } else {
            carry = 0;
         }
      }
      for ( int i = shorter; i < longer; i ++) {
         if ( digits.length == longer ) {
            total[i] = digits[i] + carry;
         } else {
            total[i] = bint.digits[i] + carry;
         }
         if( total[i] > 999999999 ) {
            total[i] -= 1000000000; 
            carry = 1;
         } else {
            carry = 0;
         }
      }

      // toArray(digits);
      // toArray(bint.digits);
      // System.out.println("TOTAL IS: ");
      // toArray(total);


      StringBuffer s = new StringBuffer();

      if ( sign == 1 ) {
         s.append("-");
      }

      for(int i = total.length - 1; i >= 0; i--) {

         s = s.append(padWithZeros(Math.abs(total[i])));
      }



      // System.out.println("THE CURRENT s IS: " + s);
      return removeLeadingZeros(new BrobInt(s.toString()));

   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt
//    *  @param  bint         BrobInt to subtract from this
//    *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtract( BrobInt bint ) { 
      //always wanna subtract shorter, use sign of longer
      int[] longer = digits;
      int[] shorter = bint.digits;
      boolean negative = false; //defines sign of result
      int borrow = 0;
      int[] total = new int[longer.length];

      if (digits.length > bint.digits.length) {
         longer = digits;
         if ( this.sign == 0 ) {
            negative = false; 
         } else {
            negative = true;
         }
         shorter = bint.digits;
      } else if ( digits.length == bint.digits.length) {
         // if the arrays are the same length, we need to make sure we are
         // subtracting the smaller absolute value from the larger absolute value
         // since this is the way we do subtraction on paper
         for ( int i = digits.length-1; i >= 0; i--) {
            if ( digits[i] > bint.digits[i] ) {
               longer = digits;
               if ( this.sign == 0 ) {
                  negative = false; 
               } else {
                  negative = true;
               }
               shorter = bint.digits;
               break;
            } else if ( digits[i] < bint.digits[i] ) {
               longer = bint.digits;
               if ( bint.sign == 0 ) {
                  negative = false; 
               } else {
                  negative = true;
               }
               shorter = digits;
               break;
            }
         }
      } else {
         longer = bint.digits;
         shorter = digits;
      }


      if( 0 > compareTo(bint) ) {
         negative = true;
      }

      if ( (1 == sign) && (1== bint.sign) && ( 0 > compareTo(bint))) {
         negative = false;
      }

      if( this.equals(ZERO) ) {
         return bint;
      }

      if( bint.equals(ZERO) ) {
         return this;
      }

      // toArray(longer);
      // toArray(shorter);

      if((0 == sign) && (0 == bint.sign) && (0 <= compareTo(bint))) { //takes care of all cases when both are + AND this > bint
         negative = false;
      } else if ((0 == sign) && ( 0 == bint.sign) && (0 > compareTo(bint))  ) {
         negative = true;
      } else if ( ( 0 == sign) && (1 == bint.sign)/* && ( 0 < compareTo(bint))*/ ) {
         return add(new BrobInt(bint.internalValue));
      }

      for (int i = 0; i < shorter.length; i++) {
         if ( longer[i] < shorter[i] ) {
            borrow = 1;
            total[i] = longer[i] + 1000000000 - shorter[i];
         } else {
            total[i] = longer[i] - borrow - shorter[i];
            borrow = 0;
         } 
      }

      for (int i = shorter.length; i < longer.length; i++) {
         total[i] = longer[i] - borrow;
      }

      // now we do the subtraction algorithm


      // 

      StringBuffer s = new StringBuffer();

      if ( negative ) {
         s.append("-");
      }

      for(int i = total.length - 1; i >= 0; i--) {

         s = s.append(padWithZeros(Math.abs(total[i])));
      }
      return removeLeadingZeros(new BrobInt(s.toString()));
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
//    *  @param  bint         BrobInt to multiply this by
//    *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt multiply( BrobInt bint ) {
      int size = this.digits.length + bint.digits.length;
      BrobInt result = new BrobInt("0");
      int multiplier = 1;
      for ( int i = 0; i < bint.digits.length; i++ ) {
         for ( int j = 0; j < bint.digits[i] * multiplier; j++) {
            // System.out.println("THE VALUE OF BINT.DIGITS[i] IS: " + bint.digits[i]);
            // System.out.println("THE VALUE OF J IS: " + j);
            result = result.add(new BrobInt(this.internalValue));
         }
         multiplier *=  1000000000; //handles cases should the parameter have an array larger than length 1 (none in tester)
      }

      // StringBuffer s = new StringBuffer();

      // for(int i = total.length -1; i >= 0; i--) {
      //    s = s.append(padWithZeros(Math.abs(total[i].internalValue)));
      // }
      // return removeLeadingZeros(new BrobInt(s.toString()));
      return result;
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
//    *  @param  bint         BrobInt to divide this by
//    *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt bint ) {
      BrobInt result = new BrobInt("0");
      BrobInt total = new BrobInt(this.internalValue);
 
      
      while ( (0 < total.compareTo(bint)) ) {
         // System.out.println("DO WE EVER ENTER THIS LOOP?");
         total = total.subtract(bint);
         result = result.add(ONE);
         // System.out.println("RIGHT NOW, TOTAL IS: " + total);
         // System.out.println("RIGHT NOW, RESULT IS: " + result);
      }

      if ( total.equals(bint)) {
         total = total.subtract(bint);
         result = result.add(ONE);
      }

      return result;
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to get the remainder of division of this BrobInt by the one passed as argument
//    *  @param  bint         BrobInt to divide this one by
//    *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt remainder( BrobInt bint ) {
      BrobInt total = new BrobInt(this.internalValue);
 
      
      while ( 0 < total.compareTo(bint) ) {
         // System.out.println("DO WE EVER ENTER THIS LOOP?");
         total = total.subtract(bint);
         // System.out.println("RIGHT NOW, TOTAL IS: " + total);
         // System.out.println("RIGHT NOW, RESULT IS: " + result);
      }

      if ( total.equals(bint)) {
         total = total.subtract(bint);
      }

      return total;
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to compare a BrobInt passed as argument to this BrobInt
//    *  @param  bint  BrobInt to compare to this
//    *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
//    *  NOTE: this method does not do a lexicographical comparison using the java String "compareTo()" method
//    *        It takes into account the length of the two numbers, and if that isn't enough it does a
//    *        character by character comparison to determine
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int compareTo( BrobInt bint ) {
     // remove any leading zeros because we will compare lengths
      String me  = removeLeadingZeros( this ).toString();
      String arg = removeLeadingZeros( bint ).toString();

     // handle the signs here
      if( 1 == sign && 0 == bint.sign ) {
         return -1;
      } else if( 0 == sign && 1 == bint.sign ) {
         return 1;
      } else if( 0 == sign && 0 == bint.sign ) {
        // the signs are the same at this point ~ both positive
        // check the length and return the appropriate value
        //   1 means this is longer than bint, hence larger positive
        //  -1 means bint is longer than this, hence larger positive
         if( me.length() != arg.length() ) {
            return (me.length() > arg.length()) ? 1 : -1;
         }
      } else {
        // the signs are the same at this point ~ both negative
         if( me.length() != arg.length() ) {
            return (me.length() > arg.length()) ? -1 : 1;
         }
      }

     // otherwise, they are the same length, so compare absolute values
      for( int i = 0; i < me.length(); i++ ) {
         Character a = Character.valueOf( me.charAt(i) );
         Character b = Character.valueOf( arg.charAt(i) );
         if( Character.valueOf(a).compareTo( Character.valueOf(b) ) > 0 ) {
            return 1;
         } else if( Character.valueOf(a).compareTo( Character.valueOf(b) ) < 0 ) {
            return (-1);
         }
      }
      return 0;
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to check if a BrobInt passed as argument is equal to this BrobInt
//    *  @param  bint     BrobInt to compare to this
//    *  @return boolean  that is true if they are equal and false otherwise
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean equals( BrobInt bint ) {
      return ( (sign == bint.sign) && (this.toString().equals( bint.toString() )) );
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to return a BrobInt given a long value passed as argument
//    *  @param  value    long type number to make into a BrobInt
//    *  @return BrobInt  which is the BrobInt representation of the long
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt valueOf( long value ) throws NumberFormatException {
      BrobInt bi = null;
      try { bi = new BrobInt( Long.valueOf( value ).toString() ); }
      catch( NumberFormatException nfe ) { throw new NumberFormatException( "\n  Sorry, the value must be numeric of type long." ); }
      return bi;
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to return a String representation of this BrobInt
//    *  @return String  which is the String representation of this BrobInt
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public String toString() {
      if ( sign == 1 ) {
         return "-" + internalValue;
      } else {
         return internalValue;
      }

   }

   public String padWithZeros(int n) {
      String s = "";
      s += Integer.valueOf(n).toString();
      for(int i = s.length(); i < 9; i++) {
         s = 0 + s;
      }
      return s;
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to remove leading zeros from a BrobInt passed as argument
//    *  @param  bint     BrobInt to remove zeros from
//    *  @return BrobInt that is the argument BrobInt with leading zeros removed
//    *  Note that the sign is preserved if it exists
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt removeLeadingZeros( BrobInt bint ) {
      Character sign = null;
      String returnString = bint.toString();
      int index = 0;

      if( allZeroDetect( bint ) ) {
         return bint;
      }
      if( ('-' == returnString.charAt( index )) || ('+' == returnString.charAt( index )) ) {
         sign = returnString.charAt( index );
         index++;
      }
      if( returnString.charAt( index ) != '0' ) {
         return bint;
      }

      while( returnString.charAt( index ) == '0' ) {
         index++;
      }
      returnString = bint.toString().substring( index, bint.toString().length() );
      if( sign != null ) {
         returnString = sign.toString() + returnString;
      }
      return new BrobInt( returnString );

   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to return a boolean if a BrobInt is all zeros
//    *  @param  bint     BrobInt to compare to this
//    *  @return boolean  that is true if the BrobInt passed is all zeros, false otherwise
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean allZeroDetect( BrobInt bint ) {
      for( int i = 0; i < bint.toString().length(); i++ ) {
         if( bint.toString().charAt(i) != '0' ) {
            return false;
         }
      }
      return true;
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to display an Array representation of this BrobInt as its bytes
//    *  @param   d  byte array from which to display the contents
//    *  NOTE: may be changed to int[] or some other type based on requirements in code above
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void toArray( int[] d ) {
      System.out.println( "Array contents: " + Arrays.toString( d ) );
   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  Method to display a prompt for the user to press 'ENTER' and wait for her to do so
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public void pressEnter() {
      String inputLine = null;
      try {
         System.out.print( "      [Press 'ENTER' to continue]: >> " );
         inputLine = input.readLine();
      }
      catch( IOException ioe ) {
         System.out.println( "Caught IOException" );
      }

   }

//   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    *  the main method redirects the user to the test class
//    *  @param  args  String array which contains command line arguments
//    *  NOTE:  we don't really care about these, since we test the BrobInt class with the BrobIntTester
//    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void main( String[] args ) {
      System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
      System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );

      BrobInt b1 = null;
      BrobInt test  = new BrobInt("1234567890");
      // test.toArray(test.digits);
      try { System.out.println( "   Making a new BrobInt: " ); b1 = new BrobInt( "147258369789456123" ); }
      catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
      try { System.out.println( "   expecting: 147258369789456123\n     and got: " + b1.toString() ); }
      catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
      System.out.println( "\n    Multiplying 82832833 by 3: " );
      try { System.out.println( "      expecting: 248498499\n        and got: " + new BrobInt("82832833").multiply( BrobInt.THREE ) ); }
      catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

      System.out.println( "\n    Multiplying 3 by 82832833 and adding 1: " );
      try { System.out.println( "      expecting: 248498500\n        and got: " + BrobInt.THREE.multiply( new BrobInt( "82832833" ) ).add( BrobInt.ONE ) ); }
      catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
      System.exit( 0 );

   }
}
