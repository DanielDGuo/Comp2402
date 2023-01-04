package comp2402a1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Part10 {

	/**
	 * Your code goes here - see Part0 for an example
	 * 
	 * @param r the reader to read from
	 * @param w the writer to write to
	 * @throws IOException
	 */
	public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			list.add(line);
		}

		int anchor = 0;

		while (anchor < list.size()) {
			for (int i = anchor + 1; i < list.size(); i++) {
				if (list.get(i).compareTo(list.get(anchor)) > 0) {
					anchor = i;
				}
			}
			w.println(list.get(anchor));
			anchor++;
		}
		/*
		 * int anchorIndex = 0;
		 * int currentLargestIndex = 0;
		 * String currentLargestValue = null;
		 * while (anchorIndex != list.size() - 1) {
		 * for (int i = anchorIndex; i < list.size(); i++) {
		 * if (currentLargestValue == null || list.get(i).compareTo(currentLargestValue)
		 * > 0) {
		 * currentLargestValue = list.get(i);
		 * currentLargestIndex = i;
		 * }
		 * }
		 * w.println(currentLargestValue);
		 * currentLargestValue = null;
		 * anchorIndex = currentLargestIndex + 1;
		 * currentLargestIndex = 0;
		 * }
		 * w.println(list.get(list.size() - 1));
		 */

		/*
		 * ArrayList<String> sortedList = new ArrayList<>(list);
		 * Collections.sort(sortedList);
		 * Collections.reverse(sortedList);
		 * 
		 * int currentIndex = -1;
		 * for (String s : sortedList) {
		 * int indexBig = list.indexOf(s);
		 * 
		 * if (indexBig == list.size() - 1) {
		 * w.println(s);
		 * break;
		 * }
		 * 
		 * if (indexBig > currentIndex) {
		 * currentIndex = indexBig;
		 * w.println(s);
		 * }
		 * }
		 */

		/*
		 * int currentIndex = 0;
		 * while (currentIndex < list.size() - 1) {
		 * int biggestIndex = 0;
		 * for (int i = currentIndex; i < list.size() - 1; i++) {
		 * if (list.get(i).compareTo(list.get(biggestIndex)) > 0) {
		 * biggestIndex = i;
		 * }
		 * }
		 * w.println(list.get(biggestIndex));
		 * currentIndex = biggestIndex + 1;
		 * }
		 * w.println(list.get(list.size()-1));
		 */

	}

	/**
	 * The driver. Open a BufferedReader and a PrintWriter, either from System.in
	 * and System.out or from filenames specified on the command line, then call
	 * doIt.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader r;
			PrintWriter w;
			if (args.length == 0) {
				r = new BufferedReader(new InputStreamReader(System.in));
				w = new PrintWriter(System.out);
			} else if (args.length == 1) {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(System.out);
			} else {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(new FileWriter(args[1]));
			}
			long start = System.nanoTime();
			doIt(r, w);
			w.flush();
			long stop = System.nanoTime();
			System.out.println("Execution time: " + 1e-9 * (stop - start));
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}
	}
}
