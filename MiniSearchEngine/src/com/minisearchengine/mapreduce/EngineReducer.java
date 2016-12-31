package com.minisearchengine.mapreduce;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class EngineReducer extends Reducer<Text, Text, Text, Text> {
	private final int THRESHOLD = 10; // threshold for this word to be a key word of a file
	
	@Override
	public void reduce(final Text key, final Iterable<Text> values, final Context context) throws IOException, InterruptedException {
		// word => [file1, file1, file1, file2, file2, ...]
		String prevFile = null;
		int weight = 0; // how many times this word occurs a file		
		StringBuilder sb = new StringBuilder();
		for (Text value : values) {
			if (prevFile == null) {
				prevFile = value.toString().trim();
				weight++;
				continue;
			}
			if (prevFile != null && value.toString().trim().equals(prevFile)) {
				weight++;				
				continue;
			}
			if (prevFile != null && weight < THRESHOLD) {
				weight = 1;
				prevFile = value.toString().trim();
				continue;
			}			
			// this word has a weight that is greater than the threshold in a file
			sb.append(prevFile);
			sb.append("\t");			
			weight = 1;
			prevFile = value.toString().trim();			
		}
		if (weight > THRESHOLD) {
			sb.append(prevFile);
		}
		if (!sb.toString().trim().equals("")) {
			context.write(key, new Text(sb.toString()));
		}		
	}
}
