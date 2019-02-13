package main;

import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws IOException {
		String input_file_name = "data/uncompressed.txt";
		String output_file_name = "data/recompressed.dat";

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] symbol_counts = new int[256];
		int num_symbols = 0;
		
		System.out.println("Encoding " + input_file_name + " --> " + output_file_name);

		// Read in each symbol (i.e. byte) of input file and 
		// update appropriate count value in symbol_counts
		// Should end up with total number of symbols 
		// (i.e., length of file) as num_symbols
		int readbyte = fis.read();
		while (readbyte != -1) {
			num_symbols++;
			symbol_counts[readbyte]++;
			readbyte = fis.read();
		}
	
		//ENTROPY CALCULATIONS FOR PART 3
		double[] probs = new double[256];
		for (int i=0; i<256; i++) {
			probs[i] = ((double) symbol_counts[i]/num_symbols);
		}
		
		double entropy = 0;
		for (int i=0; i<256; i++) {
			if (probs[i] > 0) {
				entropy += (double) probs[i] * Math.log(probs[i])/Math.log(2) * -1;
			}
		}
		System.out.println("Theoretical entropy: " + entropy + " bits/symbol");
		//end of entropy calculations
		
		// Close input file
		fis.close();

		// Create array of symbol values
		int[] symbols = new int[256];
		for (int i=0; i<256; i++) {
			symbols[i] = i;
		}
		
		// Create encoder using symbols and their associated counts from file.
		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);
		
		// Open output stream.
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i=0; i<256; i++) {
			encoder.encode(symbols[i], bit_sink);
		}
		
		// Write out total number of symbols as 32 bit value.
		bit_sink.write(num_symbols, 32);

		// Reopen input file.
		fis = new FileInputStream(input_file_name);

		// Go through input file, read each symbol (i.e. byte),
		// look up code using encoder.getCode() and write code
        // out to output file.
		
		while (fis.available() > 0) {
			bit_sink.write(encoder.getCode(fis.read()));
		}

		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
		
		System.out.println("done");
	}
}

