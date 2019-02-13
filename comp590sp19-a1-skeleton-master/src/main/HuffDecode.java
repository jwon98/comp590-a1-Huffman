package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws IOException, InsufficientBitsLeftException {
		String input_file_name = "data/compressed.dat";
		String output_file_name = "data/uncompressed.txt";
		
		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();
		
		System.out.println("Decoding " + input_file_name + " --> " + output_file_name);
		
		//Compressed entropy calculation
		File file = new File(input_file_name);
		long numberOfBits = 8*(file.length() - 260);

		// Read in huffman code lengths from input file and associate them with each symbol as a 
		// SymbolWithCodeLength object and add to the list symbols_with_length.
		for (int i=0; i<256; i++) {
			int isbs = bit_source.next(8);
			symbols_with_length.add(new SymbolWithCodeLength(i, isbs));
		}
		// Then sort they symbols.
		symbols_with_length.sort(null);

		// Now construct the canonical huffman tree

		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(symbols_with_length);

		int num_symbols = bit_source.next(32);
		
		//System.out.println("total symbols decoded: " + num_symbols);

		// Read in the next 32 bits from the input file  the number of symbols

		try {

			// Open up output file.
			
			FileOutputStream fos = new FileOutputStream(output_file_name);

			for (int i=0; i<num_symbols; i++) {
				// Decode next symbol using huff_tree and write out to file.
				fos.write(huff_tree.decode(bit_source));
			}

			// Flush output and close files.
			
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Compressed entropy
		System.out.println("Compressed entropy: " + ((double)(numberOfBits))/num_symbols);
		
		System.out.println("done");
	}
}