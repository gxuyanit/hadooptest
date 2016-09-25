package xxy.hadoop.hadooptest.mapReduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class wordCountMap extends Mapper<LongWritable, Text, Text, LongWritable> {
	/**
	 * map 重写map 方法，实现自己的任务
	 * @param key
	 * @param value
	 * @param context
	 * @throws IOException
	 * @throws InterruptedException
	 * @author: xuxianyan
	 * @update:[2016年9月25日 下午1:18:38] Administrator 变更描述 
	 */
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		// Text（String），longWritable（Long） 是hadoop自己实现的序列化对象，不能使用原声的。
		String line = value.toString();
		String[] words = line.split(" ");
		for (String word : words) {
			context.write(new Text(word), new LongWritable(1));
		}
	}
}
