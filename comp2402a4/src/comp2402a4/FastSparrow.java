package comp2402a4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FastSparrow implements RevengeOfSparrow {
  List<Integer> main;
  List<List<Long>> sums;
  List<List<Integer>> maxes;

  // set, ksum - O(log n)

  public FastSparrow() {
    main = new ArrayList<>();
    sums = new ArrayList<List<Long>>();
    maxes = new ArrayList<List<Integer>>();
  }

  public void push(int x) {
    main.add(x);

    // adds a new array to store the root
    if (main.size() != 2 && (main.size() == 1 || Math.log(main.size() - 1) / Math.log(2) % 1 == 0)) {
      maxes.add(0, new ArrayList<>());
      sums.add(0, new ArrayList<>());
    }

    // max adder
    // if the added node is a left child, add it to the upper layers if neccesary
    if (main.size() % 2 == 1) {
      // add to bottommost layer
      maxes.get(maxes.size() - 1).add(x);

      // for every layer, if it cannot connect a node to the one above, make a new
      // node
      for (int i = maxes.size() - 2; i >= 0; i--) {
        // checks if the layer is unable to connect to all nodes in the next one
        if (maxes.get(i).size() * 2 < maxes.get(i + 1).size()) {
          // if the next layer has an even number of nodes, add the maximum between the
          // new node and the previous node
          if (maxes.get(i + 1).size() % 2 == 0) {
            maxes.get(i).add(Math.max(x, maxes.get(1).get(0)));
          } else {// otherwise add x
            maxes.get(i).add(x);
          }
        } else if (maxes.get(i).get(maxes.get(i).size() - 1) < x) {
          // if it is able, set the node to the max between x and its neighbor
          maxes.get(i).set(maxes.get(i).size() - 1, x);
        }
      }
    } else {// if it's a right child, check to see if the maximum is correct
      if (maxes.get(maxes.size() - 1).get(maxes.get(maxes.size() - 1).size() - 1) < x) {
        maxes.get(maxes.size() - 1).set(maxes.get(maxes.size() - 1).size() - 1, x);
      }
      for (int i = maxes.size() - 2; i >= 0; i--) {
        if (maxes.get(i).get(maxes.get(i).size() - 1) < x) {
          maxes.get(i).set(maxes.get(i).size() - 1, x);
        }
      }
    }

    // sums adder
    // if the added node is on an odd index(right child), add it to the upper layers
    if (main.size() % 2 == 1) {
      // add to bottommost layer
      sums.get(sums.size() - 1).add((long) x);

      // add a new node to above layers if neccesary
      for (int i = sums.size() - 2; i >= 0; i--) {
        // checks if the layer is unable to connect to all nodes in the next one
        if (sums.get(i).size() * 2 < sums.get(i + 1).size()) {
          // if the next layer has an even number of nodes, add the maximum between the
          // new node and the previous node
          if (sums.get(i + 1).size() % 2 == 0) {
            sums.get(i).add(((long) x + sums.get(1).get(0)));
          } else {// otherwise add x
            sums.get(i).add((long) x);
          }
        } else {
          // if it is able, set the node to the max between x and its neighbor
          sums.get(i).set(sums.get(i).size() - 1, sums.get(i).get(sums.get(i).size() - 1) + (long) x);
        }
      }
    } else {// on even nodes, check to see if the ksum is correct
      sums.get(sums.size() - 1).set(sums.get(sums.size() - 1).size() - 1,
          sums.get(sums.size() - 1).get(sums.get(sums.size() - 1).size() - 1) + (long) x);

      for (int i = sums.size() - 2; i >= 0; i--) {
        sums.get(i).set(sums.get(i).size() - 1, sums.get(i).get(sums.get(i).size() - 1) + (long) x);
      }
    }
  }

  public Integer pop() {
    if (main.size() == 0) {
      return null;
    }
    int x = main.remove(main.size() - 1);
    // if there is an even number of elements, remove the last element on the maxes
    // and sums rows
    // the removed element was odd, therefore it mist've been the only child of a
    // node
    if (main.size() % 2 == 0) {
      maxes.get(maxes.size() - 1).remove(maxes.get(maxes.size() - 1).size() - 1);
      sums.get(sums.size() - 1).remove(sums.get(sums.size() - 1).size() - 1);
    } else {
      // if the removed element was even, supdate the last element of maxes and sums
      // if the node was x, change it to the last node in the bottom layer
      if (maxes.get(maxes.size() - 1).get(maxes.get(maxes.size() - 1).size() - 1) == x) {
        maxes.get(maxes.size() - 1).set(maxes.get(maxes.size() - 1).size() - 1, main.get(main.size() - 1));
      }
      // update the last node of sums
      sums.get(sums.size() - 1).set(sums.get(sums.size() - 1).size() - 1,
          sums.get(sums.size() - 1).get(sums.get(sums.size() - 1).size() - 1) - (long) x);
    }

    // starting from the 2nd bottom layer, update the sums nodes
    for (int i = sums.size() - 2; i >= 0; i--) {
      // checks if the layer has correct amount of nodes. If not, remove the last node
      if ((sums.get(i).size() * 2 == sums.get(i + 1).size() || sums.get(i).size() * 2 == sums.get(i + 1).size() + 1)) {
        // if it has the correct amount of nodes, update the last node
        sums.get(i).set(sums.get(i).size() - 1,
            sums.get(i).get(sums.get(i).size() - 1) - (long) x);
      } else {
        sums.get(i).remove(sums.get(i).size() - 1);
        // if the array becomes empty or a extended root, delete it
        if (sums.get(i).size() == 1 && i != 0) {
          sums.remove(i);
        }
      }
    }
    if (main.size() == 1 && sums.size() > 1) {
      sums.clear();
      ArrayList<Long> tmp = new ArrayList<>();
      tmp.add((long) main.get(0));
      sums.add(tmp);
    } else if (main.size() == 2 && sums.size() > 1) {
      sums.clear();
      ArrayList<Long> tmp = new ArrayList<>();
      tmp.add((long) main.get(0) + main.get(1));
      sums.add(tmp);
    }

    // starting from the 2nd bottom layer, update the maxes nodes
    for (int i = maxes.size() - 2; i >= 0; i--) {
      // checks if the layer has correct amount of nodes. If not, remove the last node
      if ((maxes.get(i).size() * 2 == maxes.get(i + 1).size()
          || maxes.get(i).size() * 2 == maxes.get(i + 1).size() + 1)) {
        if (maxes.get(i + 1).size() % 2 == 1) {
          maxes.get(i).set(maxes.get(i).size() - 1, maxes.get(i + 1).get(maxes.get(i + 1).size() - 1));
        } else {
          maxes.get(i).set(maxes.get(i).size() - 1, Math.max(maxes.get(i + 1).get(maxes.get(i + 1).size() - 1),
              maxes.get(i + 1).get(maxes.get(i + 1).size() - 2)));
        }
      } else {
        maxes.get(i).remove(maxes.get(i).size() - 1);
      }
      // if the array becomes empty or is a extended root, delete it
      if (maxes.get(0).size() == 0 || (maxes.get(0).size() == 1 && maxes.get(1).size() == 1)) {
        maxes.remove(0);
      }
    }
    if (main.size() == 1 && maxes.size() > 1) {
      maxes.clear();
      ArrayList<Integer> tmp = new ArrayList<>();
      tmp.add(main.get(0));
      maxes.add(tmp);
    } else if (main.size() == 2 && maxes.size() > 1) {
      maxes.clear();
      ArrayList<Integer> tmp = new ArrayList<>();
      tmp.add(Math.max(main.get(0), main.get(1)));
      maxes.add(tmp);
    }
    return x;
  }

  public Integer get(int i) {
    if (i < 0 || i >= main.size()) {
      return null;
    }
    return main.get(i);
  }

  public Integer set(int i, int x) {

    if (i < 0 || i >= main.size()) {
      return null;
    }
    int removedVar = main.set(i, x);
    // updates maxes
    int index = i;
    int layer = maxes.size() - 1;

    // update the bottom row of maxes to iterate over
    if (i % 2 == 0 && i == main.size() - 1) {// if left child and end of main
      maxes.get(layer).set((int) Math.floor(i / 2), main.get(i));
    } else if (i % 2 == 1) {// if right child
      if (main.get(i) > main.get(i - 1)) {// if x is larger than the other child,
        // update the parent
        maxes.get(layer).set((int) Math.floor(index / 2), main.get(i));
      } else {// if x is not the maximum, set it to the value of the other child
        maxes.get(layer).set((int) Math.floor(index / 2), main.get(i - 1));
      }
    } else if (i % 2 == 0) {
      if (main.get(i) > main.get(i + 1)) {// if x is larger than the other child,
        // update the parent
        maxes.get(layer).set((int) Math.floor(index / 2), main.get(i));
      } else {// if x is not the maximum, set it to the value of the other child
        maxes.get(layer).set((int) Math.floor(index / 2), main.get(i + 1));
      }
    }
    layer--;
    index = (int) Math.floor(index / 2);

    while (layer >= 0) {
      if (index % 2 == 0 && index == maxes.get(layer + 1).size() - 1) {// if left child and end of the current layer
        maxes.get(layer).set((int) Math.floor(index / 2), maxes.get(layer + 1).get(index));
      } else if (index % 2 == 1) {// if right child
        if (maxes.get(layer + 1).get(index) > maxes.get(layer + 1).get(index - 1)) {// if x is larger than the other
                                                                                    // child,
          // update the parent
          maxes.get(layer).set((int) Math.floor(index / 2), maxes.get(layer + 1).get(index));
        } else {// if x is not the maximum, set it to the value of the other child
          maxes.get(layer).set((int) Math.floor(index / 2), maxes.get(layer + 1).get(index - 1));
        }
      } else if (index % 2 == 0) {
        if (maxes.get(layer + 1).get(index) > maxes.get(layer + 1).get(index + 1)) {// if x is larger than the other
                                                                                    // child,
          // update the parent
          maxes.get(layer).set((int) Math.floor(index / 2), maxes.get(layer + 1).get(index));
        } else {// if x is not the maximum, set it to the value of the other child
          maxes.get(layer).set((int) Math.floor(index / 2), maxes.get(layer + 1).get(index + 1));
        }
      }
      index = (int) Math.floor(index / 2);
      layer--;
    }

    // updates sums
    index = (int) Math.floor(i / 2);
    layer = sums.size() - 1;
    int difference = removedVar - x;// difference to be subtracted by
    while (layer >= 0) {
      sums.get(layer).set(index, sums.get(layer).get(index) - difference);
      index = (int) Math.floor(index / 2);
      layer--;
    }
    return removedVar;
  }

  public Integer max() {
    // for (List<Integer> e : maxes) {
    // System.out.println(e);
    // }
    // System.out.println(main);
    if (main.size() == 1 && maxes.size() > 1) {
      maxes.clear();
      ArrayList<Integer> tmp = new ArrayList<>();
      tmp.add(main.get(0));
      maxes.add(tmp);
    } else if (main.size() == 2 && maxes.size() > 1) {
      maxes.clear();
      ArrayList<Integer> tmp = new ArrayList<>();
      tmp.add(Math.max(main.get(0), main.get(1)));
      maxes.add(tmp);
    }

    if (main.size() == 0) {
      return null;
    } else if (main.size() == 1) {
      return main.get(0);
    }
    return maxes.get(0).get(0);
  }

  public long ksum(int k) {// errors
    if (main.size() == 1 && sums.size() > 1) {
      sums.clear();
      ArrayList<Long> tmp = new ArrayList<>();
      tmp.add((long) main.get(0));
      sums.add(tmp);
    } else if (main.size() == 2 && sums.size() > 1) {
      sums.clear();
      ArrayList<Long> tmp = new ArrayList<>();
      tmp.add((long) main.get(0) + main.get(1));
      sums.add(tmp);
    }
    long sum = 0;

    // checks for edge cases of k
    if (main.size() == 0 || k <= 0) {
      return sum;
    } else if (k >= main.size()) {
      return sums.get(0).get(0);
    } else if (k == 1) {
      return (long) main.get(main.size() - 1);
    }

    int currentLayer = 0;
    int currentIndex = 0;
    int heldNodes = main.size();
    int completeGraphNodes = (int) Math.pow(2, Math.ceil(Math.log(heldNodes) / Math.log(2)));
    boolean movedleft = false;

    while (k > 0 && currentLayer != sums.size()) {
      if (heldNodes > k) {
        // move down and to the right and update accordingly
        currentLayer++;
        currentIndex = currentIndex * 2 + 1;
        if (currentLayer != sums.size() && currentIndex > sums.get(currentLayer).size() - 1) {
          currentIndex = sums.get(currentLayer).size() - 1;
        }
        completeGraphNodes /= 2;
        // update heldNodes
        if (movedleft) {
          heldNodes = completeGraphNodes;
        } else {
          heldNodes = heldNodes % completeGraphNodes;
          if (heldNodes == 0) {
            heldNodes = completeGraphNodes;
          }
        }
      } else {
        movedleft = true;
        // move to the left and update accordingly
        // adds to the sum and adds to k
        sum += sums.get(currentLayer).get(currentIndex);
        k -= heldNodes;
        // go left
        currentIndex--;
        heldNodes = completeGraphNodes;
      }
    }
    if (k == 1) {
      sum += (long) main.get(currentIndex);
    }
    return sum;
  }

  public int size() {
    return main.size();
  }

  public Iterator<Integer> iterator() {
    return main.iterator();
  }
}