package comp2402a2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class WhatTheFast implements WhatTheDeque {

  protected Deque<Integer> basicDeque;
  protected ArrayList<Integer> basicFront;
  protected ArrayList<Integer> basicBack;
  protected ArrayList<Integer> maxFront;
  protected ArrayList<Integer> maxBack;
  protected ArrayList<Long> sumFront;
  protected ArrayList<Long> sumBack;

  public WhatTheFast() {
    basicDeque = new LinkedList<>();
    basicFront = new ArrayList<>();
    basicBack = new ArrayList<>();
    maxFront = new ArrayList<>();
    maxBack = new ArrayList<>();
    sumFront = new ArrayList<>();
    sumBack = new ArrayList<>();
  }

  public void addFirst(Integer x) {
    if (maxFront.size() == 0 || x > maxFront.get(maxFront.size() - 1)) {
      maxFront.add(x);
    } else {
      maxFront.add(maxFront.get(maxFront.size() - 1));
    }

    if (sumFront.size() == 0) {
      sumFront.add(Long.valueOf(x.longValue()));
    } else {
      sumFront.add(Long.valueOf(x.longValue()) + sumFront.get(sumFront.size() - 1));
    }
    basicDeque.addFirst(x);
    basicFront.add(x);

    // ArrayList<Integer> tmp = new ArrayList<>(basicFront);
    // Collections.reverse(tmp);
    // System.out.println("dualarraydeque: " + tmp + basicBack);

    // ArrayList<Long> tmp2 = new ArrayList<>(sumFront);
    // Collections.reverse(tmp2);
    // System.out.println("sums: " + tmp2 + sumBack);
  }

  public void addLast(Integer x) {
    if (maxBack.size() == 0 || x > maxBack.get(maxBack.size() - 1)) {
      maxBack.add(x);
    } else {
      maxBack.add(maxBack.get(maxBack.size() - 1));
    }

    if (sumBack.size() == 0) {
      sumBack.add(Long.valueOf(x.longValue()));
    } else {
      sumBack.add(Long.valueOf(x.longValue()) + sumBack.get(sumBack.size() - 1));
    }
    basicDeque.addLast(x);
    basicBack.add(x);

    // ArrayList<Integer> tmp = new ArrayList<>(basicFront);
    // Collections.reverse(tmp);
    // System.out.println("dualarraydeque: " + tmp + basicBack);

    // ArrayList<Long> tmp2 = new ArrayList<>(sumFront);
    // Collections.reverse(tmp2);
    // System.out.println("sums: " + tmp2 + sumBack);

  }

  public Integer removeFirst() {
    balance();
    if (size() == 0) {
      return null;
    }

    if (maxFront.size() != 0) {
      maxFront.remove(maxFront.size() - 1);
    } else {
      maxBack.remove(0);
    }

    if (sumFront.size() != 0) {
      sumFront.remove(sumFront.size() - 1);
    } else {
      sumBack.remove(0);
    }
    if (basicFront.size() != 0) {
      basicFront.remove(basicFront.size() - 1);
    } else {
      basicBack.remove(0);
    }

    // ArrayList<Integer> tmp = new ArrayList<>(basicFront);
    // Collections.reverse(tmp);
    // System.out.println("dualarraydeque: " + tmp + basicBack);

    // ArrayList<Long> tmp2 = new ArrayList<>(sumFront);
    // Collections.reverse(tmp2);
    // System.out.println("sums: " + tmp2 + sumBack);
    return basicDeque.removeFirst();
  }

  public Integer removeLast() {
    balance();
    if (size() == 0) {
      return null;
    }

    if (maxBack.size() != 0) {
      maxBack.remove(maxBack.size() - 1);
    } else {
      maxFront.remove(0);
    }

    if (sumBack.size() != 0) {
      sumBack.remove(sumBack.size() - 1);
    } else {
      sumFront.remove(0);
    }

    if (basicBack.size() != 0) {
      basicBack.remove(basicBack.size() - 1);
    } else {
      basicFront.remove(0);
    }
    // ArrayList<Integer> tmp = new ArrayList<>(basicFront);
    // Collections.reverse(tmp);
    // System.out.println("dualarraydeque: " + tmp + basicBack);

    // ArrayList<Long> tmp2 = new ArrayList<>(sumFront);
    // Collections.reverse(tmp2);
    // System.out.println("sums: " + tmp2 + sumBack);

    return basicDeque.removeLast();
  }

  public Integer max() {
    if (maxFront.size() == 0 && maxBack.size() == 0) {
      return null;
    } else if (maxFront.size() == 0 && maxBack.size() != 0) {
      return maxBack.get(maxBack.size() - 1);
    } else if (maxFront.size() != 0 && maxBack.size() == 0) {
      return maxFront.get(maxFront.size() - 1);
    } else if (maxFront.get(maxFront.size() - 1) > maxBack.get(maxBack.size() - 1)) {
      return maxFront.get(maxFront.size() - 1);
    } else {
      return maxBack.get(maxBack.size() - 1);
    }
  }

  public long ksumFirst(int k) {
    balance();
    if (k <= 0 || size() == 0) {
      return Long.valueOf(0);
    } else if (sumFront.size() == 0) {
      if (k > sumBack.size()) {
        return sumBack.get(sumBack.size() - 1);
      } else {
        return sumBack.get(k - 1);
      }
    } else if (k >= size()) {
      if (sumFront.size() == 0) {
        return sumBack.get(sumBack.size() - 1);
      } else if (sumBack.size() == 0) {
        return sumFront.get(sumFront.size() - 1);
      } else {
        return sumFront.get(sumFront.size() - 1) + sumBack.get(sumBack.size() - 1);
      }
    } else if (k < sumFront.size()) {
      return sumFront.get(sumFront.size() - 1) - sumFront.get(sumFront.size() - 1 - k);
    } else if (k == sumFront.size()) {
      return sumFront.get(sumFront.size() - 1);
    } else if (k > sumFront.size()) {
      return sumFront.get(sumFront.size() - 1) + sumBack.get(k - sumFront.size() - 1);
    }
    return 0;
  }

  public long ksumLast(int k) {
    balance();
    if (k <= 0 || size() == 0) {
      return Long.valueOf(0);
    } else if (sumBack.size() == 0) {
      if (k > sumFront.size()) {
        return sumFront.get(sumFront.size() - 1);
      } else {
        return sumFront.get(k - 1);
      }
    } else if (k >= size()) {
      if (sumBack.size() == 0) {
        return sumFront.get(sumFront.size() - 1);
      } else if (sumFront.size() == 0) {
        return sumBack.get(sumBack.size() - 1);
      } else {
        return sumBack.get(sumBack.size() - 1) + sumFront.get(sumFront.size() - 1);
      }
    } else if (k < sumBack.size()) {
      return sumBack.get(sumBack.size() - 1) - sumBack.get(sumBack.size() - 1 - k);
    } else if (k == sumBack.size()) {
      return sumBack.get(sumBack.size() - 1);
    } else if (k > sumBack.size()) {
      return sumBack.get(sumBack.size() - 1) + sumFront.get(k - sumBack.size() - 1);
    }
    return 0;
  }

  public int size() {
    return basicDeque.size();
  }

  public Iterator<Integer> iterator() {
    return basicDeque.iterator();
  }

  public void balance() {
    if (basicFront.size() == 0 && basicBack.size() >= 2) {
      // System.out.println("Balancing");
      int s = size() / 2;

      ArrayList<Integer> tmpFront = new ArrayList<>();
      ArrayList<Integer> tmpBack = new ArrayList<>();

      tmpFront.addAll(basicBack.subList(0, s));
      tmpBack.addAll(basicBack.subList(s, basicBack.size()));
      Collections.reverse(tmpFront);

      basicFront = tmpFront;
      basicBack = tmpBack;

      balanceSums();
      balanceMaxs();
    } else if (basicBack.size() == 0 && basicFront.size() >= 2) {
      // System.out.println("Balancing");
      int s = size()/2;

      ArrayList<Integer> tmpFront = new ArrayList<>();
      ArrayList<Integer> tmpBack = new ArrayList<>();

      tmpBack.addAll(basicFront.subList(0, s));
      tmpFront.addAll(basicFront.subList(s, basicFront.size()));
      Collections.reverse(tmpBack);

      basicFront = tmpFront;
      basicBack = tmpBack;

      balanceSums();
      balanceMaxs();
    }
  }

  public void balanceSums() {
    sumBack = new ArrayList<>();
    sumFront = new ArrayList<>();
    for (Integer i : basicFront) {
      if (sumFront.size() == 0) {
        sumFront.add(Long.valueOf(i.longValue()));
      } else {
        sumFront.add(Long.valueOf(i.longValue()) + sumFront.get(sumFront.size() - 1));
      }
    }

    for (Integer i : basicBack) {
      if (sumBack.size() == 0) {
        sumBack.add(Long.valueOf(i.longValue()));
      } else {
        sumBack.add(Long.valueOf(i.longValue()) + sumBack.get(sumBack.size() - 1));
      }
    }
  }

  public void balanceMaxs() {
    maxFront = new ArrayList<>();
    maxBack = new ArrayList<>();
    for (Integer i : basicFront) {
      if (maxFront.size() == 0 || maxFront.get(maxFront.size() - 1) < i) {
        maxFront.add(i);
      } else {
        maxFront.add(maxFront.get(maxFront.size() - 1));
      }
    }

    for (Integer i : basicBack) {
      if (maxBack.size() == 0 || maxBack.get(maxBack.size() - 1) < i) {
        maxBack.add(i);
      } else {
        maxBack.add(maxBack.get(maxBack.size() - 1));
      }
    }
  }
}