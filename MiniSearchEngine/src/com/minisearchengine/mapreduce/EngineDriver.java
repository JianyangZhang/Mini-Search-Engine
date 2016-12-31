package com.minisearchengine.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class EngineDriver {
	public static void main(String args[]) throws Exception {
		if (args.length < 3) {
			throw new Exception("Usage: <input dir> <output dir> <stopwords dir>");
		}
		
		Configuration conf = new Configuration();
		conf.set("filePath", args[2]);
		Job job = Job.getInstance(conf);
		job.setJarByClass(EngineDriver.class);
		job.setMapperClass(EngineMapper.class);
		job.setReducerClass(EngineReducer.class);
		job.setNumReduceTasks(3);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}