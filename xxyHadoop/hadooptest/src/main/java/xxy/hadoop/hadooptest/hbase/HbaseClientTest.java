/**
 * @Title:  HbaseClientTest.java
 * @Copyright (C) 2014-2015 by ywx.co.,ltd.All Rights Reserved.
 *  YWX CONFIDENTIAL AND TRADE SECRET
 * @author: Administrator 
 * @data:   2016年10月24日 下午9:34:23 
 */
package xxy.hadoop.hadooptest.hbase;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.httpclient.Header;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableMultiplexer;
import org.junit.Before;
import org.junit.Test;

/**  
* @project：hadooptest  
* @Title: HbaseClientTest.java 
* hbase java 客户端demo
* @author xuxianyan
* @date 2016年10月24日 下午9:34:23 
*/
public class HbaseClientTest {
	private Admin admin;
	
	@Before
	public void init() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "weekend05,weekend06,weekend07");
	    Connection conn = ConnectionFactory.createConnection(conf);
	    admin = conn.getAdmin();
	}
	
	@Test
	public void save(){
	}
	@Test
	public void delete(){
		
	}
	@Test
	public void update(){
		
	}
	@Test
	public void query(){
		
	}
}
