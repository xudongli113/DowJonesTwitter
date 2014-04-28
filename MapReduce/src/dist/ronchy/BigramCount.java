/*
 * Cloud9: A MapReduce Library for Hadoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package ronchy;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

/**
 * <p>
 * Simple word count demo. This Hadoop Tool counts words in flat text file, and
 * takes the following command-line arguments:
 * </p>
 * 
 * <ul>
 * <li>[input-path] input path</li>
 * <li>[output-path] output path</li>
 * <li>[num-mappers] number of mappers</li>
 * <li>[num-reducers] number of reducers</li>
 * </ul>
 * 
 * @author Jimmy Lin
 * @author Marc Sloan
 */
public class BigramCount extends Configured implements Tool {
	private static final Logger sLogger = Logger.getLogger(BigramCount.class);


	private static class MyMapper extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {


		
		private Text word = new Text();

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,
				Reporter reporter) throws IOException {
			//Convert input word into String and tokenize to find words
			String line = ((Text) value).toString();
			String[] data = line.split(",");
			double score = Double.parseDouble(data[1]);		    

            String out1 = score+","+data[2]+","+data[3];			
			
			word.set(data[0]);
			//output.collect(word, new DoubleWritable(score));
			output.collect(word, new Text(out1));
	
			
		}
	}

	/**
	 * Reducer: sums up all the counts
	 *
	 */
	private static class MyReducer extends MapReduceBase implements
			Reducer<Text, Text, Text, Text> {

		//private final static DoubleWritable SumValue = new DoubleWritable();

		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			// sum up values
			double positive_sum = 0.0;
			double negative_sum = 0.0;
		    Text out = new Text();
			int total_cn=0;
			int positive_cn=0;
			int negative_cn=0;
			int neutral_cn=0;
			double positive_retweet_sum=0;
			double negative_retweet_sum=0;
			double neutral_retweet_sum=0;
			double positive_favorite_sum=0;
			double negative_favorite_sum=0;
			double neutral_favorite_sum=0;
			while (values.hasNext()) {		

				String str1 = values.next().toString();
				String data[]= str1.split(",");
				double num= Double.parseDouble(data[0]);
				if (num>0.0){
					positive_sum+=num;
					positive_retweet_sum+=Double.parseDouble(data[1]);
					positive_favorite_sum+=Double.parseDouble(data[2]);
					positive_cn++;		
					
				}
				else if (num<0.0){
					negative_sum+=num;
					negative_retweet_sum+=Double.parseDouble(data[1]);
					negative_favorite_sum+=Double.parseDouble(data[2]);
					negative_cn++;					
				}
				else if (num==0.0){
					neutral_retweet_sum+=Double.parseDouble(data[1]);
					neutral_favorite_sum+=Double.parseDouble(data[2]);
					neutral_cn++;					
				}
				
				
				
				
				total_cn++;
			}
			String str=(""+positive_sum+","+positive_cn+","+positive_retweet_sum+","+positive_favorite_sum+","+negative_sum+","+negative_cn+","+negative_retweet_sum+","+negative_favorite_sum+","+neutral_cn+","+neutral_retweet_sum+","+neutral_favorite_sum+","+total_cn+"");
			
			output.collect(key, new Text(str));
		}
	}

	/**
	 * Creates an instance of this tool.
	 */
	public BigramCount() {
	}

	/**
	 *  Prints argument options
	 * @return
	 */
	private static int printUsage() {
		System.out.println("usage: [input-path] [output-path] [num-mappers] [num-reducers]");
		ToolRunner.printGenericCommandUsage(System.out);
		return -1;
	}

	/**
	 * Runs this tool.
	 */
	public int run(String[] args) throws Exception {
		if (args.length != 4) {
			printUsage();
			return -1;
		}

		String inputPath = args[0];
		String outputPath = args[1];

		int mapTasks = Integer.parseInt(args[2]);
		int reduceTasks = Integer.parseInt(args[3]);

		sLogger.info("Tool: BigramCount");
		sLogger.info(" - input path: " + inputPath);
		sLogger.info(" - output path: " + outputPath);
		sLogger.info(" - number of mappers: " + mapTasks);
		sLogger.info(" - number of reducers: " + reduceTasks);

		JobConf conf = new JobConf(BigramCount.class);
		conf.setJobName("BigramCount");

		conf.setNumMapTasks(mapTasks);
		conf.setNumReduceTasks(reduceTasks);

		FileInputFormat.setInputPaths(conf, new Path(inputPath));
		FileOutputFormat.setOutputPath(conf, new Path(outputPath));
		FileOutputFormat.setCompressOutput(conf, false);

		/**
		 *  Note that these must match the Class arguments given in the mapper 
		 */
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setMapperClass(MyMapper.class);
		conf.setReducerClass(MyReducer.class);

		// Delete the output directory if it exists already
		Path outputDir = new Path(outputPath);
		FileSystem.get(outputDir.toUri(), conf).delete(outputDir, true);

		long startTime = System.currentTimeMillis();
		JobClient.runJob(conf);
		sLogger.info("Job Finished in " + (System.currentTimeMillis() - startTime) / 1000.0
				+ " seconds");

		return 0;
	}

	/**
	 * Dispatches command-line arguments to the tool via the
	 * <code>ToolRunner</code>.
	 */
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new BigramCount(), args);
		System.exit(res);
	}
}
