package xxy.hadoop.hadooptest.mapReduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class wordCountReduce extends Reducer<Text, LongWritable, Text, LongWritable>{
	/**
	 * TODO 用一句话描述该方法做什么
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws IOException
	 * @throws InterruptedException
	 * @author: xuxianyan
	 * @update:[2016年9月25日 下午1:26:12] Administrator 变更描述 
	 */
	
	//key hello, values:{1,1,1,1...}
	@Override
	protected void reduce(Text key, Iterable<LongWritable> values,
			Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
		long count = 0;
		for (LongWritable longWritable : values) {
			count += longWritable.get();
		}
		context.write(key, new LongWritable(count));
	}
}
