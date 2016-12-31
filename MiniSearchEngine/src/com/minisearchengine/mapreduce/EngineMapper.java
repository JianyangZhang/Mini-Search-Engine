package com.minisearchengine.mapreduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;

public class EngineMapper extends Mapper<LongWritable, Text, Text, Text> {
	Set<String> stopWords = new HashSet<String>();
	
	// read and save all stop words
	@Override 
	public void setup(Context context) throws IOException {
		Configuration conf = context.getConfiguration();
		String filePath = conf.get("filePath");
		Path path = new Path("hdfs:" + filePath); // get the path of the "stopwords.txt" in HDFS
		FileSystem fs = FileSystem.get(new Configuration());
		BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(path)));
		String line;
		line = br.readLine();
		while(line != null) {
			stopWords.add(line.toLowerCase().trim()); 
			line = br.readLine();
		}		
	}
	
	// split file into words and filter stop words
	@Override	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String sFileName = ((FileSplit) context.getInputSplit()).getPath().getName();
		Text fileName = new Text(sFileName);
		StringTokenizer tokenizer = new StringTokenizer(value.toString());
		while (tokenizer.hasMoreTokens()) {
			String chunk = tokenizer.nextToken().toString();
			String word = chunk.toLowerCase().replaceAll("[^a-zA-Z]", ""); // remove all non-letter characters
			if (!stopWords.contains(word)) {
				context.write(new Text(word), fileName); // key:word => value:fileName
			}
		}
	}
}