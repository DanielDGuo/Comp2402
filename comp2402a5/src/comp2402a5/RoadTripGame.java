package comp2402a5;
// Thanks to Pat Morin for this file!

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class RoadTripGame {

	/**
	 * Your code goes here
	 * 
	 * @param r the reader to read from
	 * @param w the writer to write to
	 * @throws IOException
	 */
	// code goes here
	public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
		String line;
		String firstLine = "";
		String lastLine = "";
		char firstLineLastChar = 0;
		char lastLineFirstChar = 0;
		int lineCount = 0;
		boolean firstLineLastCharInitialized = false;

		AdjacencyLists edges = new AdjacencyLists(0);
		// maps a character to a vertex index
		HashMap<Character, Integer> converter = new HashMap<>();

		// creates a map with the given strings
		while ((line = r.readLine()) != null) {
			// removes white spaces at the ends
			// line.strip();
			lineCount++;

			// sets the starting character
			if (!firstLineLastCharInitialized) {
				firstLineLastChar = line.charAt(line.length() - 1);
				firstLine = line;
				firstLineLastCharInitialized = true;
			}
			// sets the finishing character
			lastLineFirstChar = line.charAt(0);
			lastLine = line;

			// get the first and last character
			char firstChar = line.charAt(0);
			char lastChar = line.charAt(line.length() - 1);

			// if the firstchar isn't in the map, add it. Map it to the index in
			if (!converter.containsKey(firstChar)) {
				converter.put(firstChar, edges.addVertex());
			}
			// do the same for lastChar
			if (!converter.containsKey(lastChar)) {
				converter.put(lastChar, edges.addVertex());
			}

			// if there isn't already an edge, add one
			if (!edges.hasEdge(converter.get(firstChar), converter.get(lastChar))) {
				edges.addEdge(converter.get(firstChar), converter.get(lastChar));
			}
			System.out.println(converter);
		}
		// checks if the first and last are the same
		System.out.println(firstLine + "," + lastLine);
		System.out.println(firstLineLastChar + "," + lastLineFirstChar);

		if (firstLine == lastLine && lineCount != 0) {
			w.println(1);
		} else if (lineCount != 0) {
			int length = Algorithms.bfs(edges, converter.get(firstLineLastChar), converter.get(lastLineFirstChar));
			if (length == -1) {
				w.println(0);
			} else {
				length += 2;
				w.println(length);
			}
		}
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
