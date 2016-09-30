/**
 * @Title:  wordCountRunner.java
 * @Copyright (C) 2014-2015 by ywx.co.,ltd.All Rights Reserved.
 *  YWX CONFIDENTIAL AND TRADE SECRET
 * @author: Administrator 
 * @data:   2016年9月25日 下午1:34:21 
 */
package xxy.hadoop.hadooptest.mapReduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**  
* @project：hadooptest  
* @Title: wordCountRunner.java 
* 用来猫叔一个作业job（使用那个mapper类，哪个reduce类，输入文件在哪，出入结果在哪） 
* 然后提交这个job给hadoop集群
* @author xuxianyan
* @date 2016年9月25日 下午1:34:21 
*/
//xxy.hadoop.hadooptest.mapReduce.wordCountRunner
public class wordCountRunner {

	/**
	 * TODO 用一句话描述该方法做什么 
	 * @param args
	 * @throws: 
	 * @author: xuxianyan
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 * @update:[2016年9月25日 下午1:34:21] Administrator 变更描述
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		//conf.set("fs.defaultFS", "hdfs://xxy01:9000/");
		Job wcjob = Job.getInstance(conf);
		//设置jbo中的资源所在的jar包，告诉它启动类，他就能找到其他类
		wcjob.setJarByClass(wordCountRunner.class);
		
		// wcjob 要使用那个map类
		wcjob.setMapperClass(wordCountMap.class);
		// wcjob 要使用那个reduce
		wcjob.setReducerClass(wordCountReduce.class);
		//指定Map端局部汇总，因为wordCount逻辑一样，所以直接使用reduce，节省网络带宽
		wcjob.setCombinerClass(wordCountReduce.class);
		
		//mapper类输出的KV数据类型
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(LongWritable.class);
		//wcjob的reduce输出的KV数据类型
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(LongWritable.class);
		//指定要处理的原始数据所在路径,不是文件
		FileInputFormat.setInputPaths(wcjob, "F:/hadoopTestData/data");
		
		//指定处理之后的结果输出到哪里，路径不是文件
		FileOutputFormat.setOutputPath(wcjob, new Path("F:/hadoopTestData/"));
		
		//commit
		boolean res = wcjob.waitForCompletion(false);//boolean 是否在输出台显示进度信息
		
		
	}

}
