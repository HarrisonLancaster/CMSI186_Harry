public class Collatz {
  private BrobInt brobInt;

  public Collatz(BrobInt b){
    this.brobInt = b;
  }

  public void printSequence() {
    StringBuffer sequence = new StringBuffer();
    BrobInt current = this.brobInt;
    int numSteps = 0;
    BrobInt finish = new BrobInt("1");
    while (!current.equals(finish)) {
      sequence.append(current.toString() + " ");
      BrobInt two = new BrobInt("2");
      BrobInt zero = new BrobInt("000000000");
      if (current.remainder(two).equals(zero)) {
        // even, so we divide by two
        current = current.divide(two);
      } else {
        BrobInt three = new BrobInt("3");
        BrobInt one = new BrobInt("1");
        // odd, so we multiply by three and add one
        current = current.multiply(three);
        current = current.add(one);
      }
      // add the step to the sequence
      numSteps++;
    }
    sequence.append("1");
    numSteps++;
    System.out.println("This is the Collatz sequence for the BrobInt " + this.brobInt.toString());
    System.out.println(sequence);
    System.out.println("The sequence took " + numSteps + " steps");
  }

  public static void main(String [] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("You must supply a string input to the collatz sequence");
    }
    BrobInt b = new BrobInt(args[0]);
    Collatz c = new Collatz(b);

    c.printSequence();
  }
}