package comp2402a2;

import java.util.ArrayList;
import java.util.Iterator;

public class FastSparrow implements CapnStackSparrow {
  protected ArrayList<Integer> basicStack;
  protected ArrayList<Integer> maxStack;
  protected ArrayList<Long> bottomSumStack;

  public FastSparrow() {
    basicStack = new ArrayList<>();
    maxStack = new ArrayList<>();
    bottomSumStack = new ArrayList<>();
  }

  public void push(Integer x) {

    if (size() == 0 || x > maxStack.get(size() - 1)) {
      // if the stack is empty or the added element is greater than the previous, add
      // the element as the new max
      maxStack.add(x);
    } else {
      // if not, add the previous max element
      maxStack.add(maxStack.get(size() - 1));
    }

    if (size() == 0) {
      // if the bottomSumStack is empty, add the element
      bottomSumStack.add(Long.valueOf(x.longValue()));
    } else {
      // if not, add the sum of all elements(current elements + sum of all previous
      // elements)
      bottomSumStack.add(Long.valueOf(x.longValue()) + bottomSumStack.get(size() - 1));
    }

    basicStack.add(x);
  }

  public Integer pop() {
    if (size() == 0) {
      return null;
    } else {
      maxStack.remove(size() - 1);
      bottomSumStack.remove(size() - 1);
      return basicStack.remove(size() - 1);
    }
  }

  public Integer max() {
    if (size() == 0) {
      return null;
    }
    return maxStack.get(size() - 1);
  }

  public long ksum(int k) {
    if (size() == 0 || k == 0) {
      return 0;
    }
    //gets the sum of the top k numbers
    long max = bottomSumStack.get(size() - 1);
    long bottomSum;
    if (size() - k - 1 < 0) {
      bottomSum = 0;
    } else {
      //gets the sum of the bottom n-k numbers
      bottomSum = bottomSumStack.get(size() - k - 1);
    }
    return (max - bottomSum);
  }

  public int size() {
    return basicStack.size();
  }

  public Iterator<Integer> iterator() {
    return basicStack.iterator();
  }
}