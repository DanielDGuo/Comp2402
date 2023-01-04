package comp2402a1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Part4 {

	/**
	 * Your code goes here - see Part0 for an example
	 * 
	 * @param r the reader to read from
	 * @param w the writer to write to
	 * @throws IOException
	 */
	public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
		TreeSet<String> s = new TreeSet<>();

		for (String line = r.readLine(); line != null; line = r.readLine()) {
			boolean flag = false;
			for (String word : s.tailSet(line, true)) {
				if (word.startsWith(line)) {
					flag = true;
					break;
				} else{
					break;
				}
			}
			if (!flag) {
				System.out.println(line);
				w.println(line);
				s.add(line);
			}
		}
		/*
		 * TreeSet<String> s = new TreeSet<>();
		 * for (String line = r.readLine(); line != null; line = r.readLine()) {
		 * if (!s.contains(line)) {
		 * w.println(line);
		 * for (int i = line.length(); i >= 1; i--) {
		 * s.add(line.substring(0, i));
		 * }
		 * }
		 *
		 * }
		 */
		/*
		 * ArrayList<String> s = new ArrayList<>();
		 * for (String line = r.readLine(); line != null; line = r.readLine()) {
		 * boolean flag = false;
		 * for (String word : s) {
		 * if (word.startsWith(line)) {
		 * flag = true;
		 * break;
		 * }
		 * }
		 * if (!flag) {
		 * w.println(line);
		 * s.add(line);
		 * }
		 * }
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
