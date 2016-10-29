/**
 * @Title:  HbaseClientTest.java
 * @Copyright (C) 2014-2015 by ywx.co.,ltd.All Rights Reserved.
 *  YWX CONFIDENTIAL AND TRADE SECRET
 * @author: Administrator 
 * @data:   2016年10月24日 下午9:34:23 
 */
package xxy.hadoop.hadooptest.hbase;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
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
	private Table table;
	
	@Before
	public void init() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "xxy03,xxy04,xxy05");
	    Connection conn = ConnectionFactory.createConnection(conf);
	    table = conn.getTable(TableName.valueOf("mygirls"));
	}
	@After
	public void end() throws IOException {
		table.close();
	}
	
	@Test
	public void save() throws IOException{
		Put put = new Put(Bytes.toBytes("xxy02"));//行键
		put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("name"), Bytes.toBytes("mimi"));
		put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes("20"));
		table.put(put);
	}
	@Test
	public void delete() throws IOException{
		Delete delete = new Delete(Bytes.toBytes("xxy01"));//行键
		delete.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
		table.delete(delete);
	}
	@Test
	public void update() throws IOException{
		//更新就是插入，老数据会成为副本，默认存3个副本
		Put put = new Put(Bytes.toBytes("xxy01"));//行键
		put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("name"), Bytes.toBytes("wang"));
		table.put(put);
	}
	@Test
	public void get() throws IOException{
		Get get = new Get(Bytes.toBytes("xxy02"));
		get.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
		Result result = table.get(get);
		List<Cell> listCells = result.listCells();
		for (Cell cell : listCells) {
			System.out.println(Bytes.toString(cell.getValueArray()));
		}
	}
	
	@Test
	public void query() throws IOException{
		Scan scan = new Scan();
		//前缀过滤器----针对行键
		Filter filter = new PrefixFilter(Bytes.toBytes("xxy02"));
		//行过滤器
		ByteArrayComparable rowComparator = new BinaryComparator(Bytes.toBytes("person_rk_bj_zhang_000001"));
		RowFilter rf = new RowFilter(CompareOp.LESS_OR_EQUAL, rowComparator);
		
		/**
         * 假设rowkey格式为：创建日期_发布日期_ID_TITLE
         * 目标：查找  发布日期  为  2014-12-21  的数据
         */
        rf = new RowFilter(CompareOp.EQUAL , new SubstringComparator("02"));
		
		
		//单值过滤器 1 完整匹配字节数组
		new SingleColumnValueFilter("base_info".getBytes(), "name".getBytes(), CompareOp.EQUAL, "zhangsan".getBytes());
		//单值过滤器2 匹配正则表达式
		ByteArrayComparable comparator = new RegexStringComparator("zhang.");
		new SingleColumnValueFilter("info".getBytes(), "NAME".getBytes(), CompareOp.EQUAL, comparator);

		//单值过滤器2 匹配是否包含子串,大小写不敏感
		comparator = new SubstringComparator("wu");
		new SingleColumnValueFilter("info".getBytes(), "NAME".getBytes(), CompareOp.EQUAL, comparator);

		//键值对元数据过滤-----family过滤----字节数组完整匹配
        FamilyFilter ff = new FamilyFilter(
                CompareOp.EQUAL , 
                new BinaryComparator(Bytes.toBytes("base_info"))   //表中不存在inf列族，过滤结果为空
                );
        //键值对元数据过滤-----family过滤----字节数组前缀匹配
        ff = new FamilyFilter(
                CompareOp.EQUAL , 
                new BinaryPrefixComparator(Bytes.toBytes("inf"))   //表中存在以inf打头的列族info，过滤结果为该列族所有行
                );
		
        
       //键值对元数据过滤-----qualifier过滤----字节数组完整匹配
        
        filter = new QualifierFilter(
                CompareOp.EQUAL , 
                new BinaryComparator(Bytes.toBytes("na"))   //表中不存在na列，过滤结果为空
                );
        filter = new QualifierFilter(
                CompareOp.EQUAL , 
                new BinaryPrefixComparator(Bytes.toBytes("na"))   //表中存在以na打头的列name，过滤结果为所有行的该列数据
        		);
		
        //基于列名(即Qualifier)前缀过滤数据的ColumnPrefixFilter
        filter = new ColumnPrefixFilter("na".getBytes());
		        
		        //基于列名(即Qualifier)多个前缀过滤数据的MultipleColumnPrefixFilter
		        byte[][] prefixes = new byte[][] {Bytes.toBytes("na"), Bytes.toBytes("me")};
		        filter = new MultipleColumnPrefixFilter(prefixes);
		scan.setFilter(rf);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			//直接从result中取到某个特定的value
			System.out.println(new String(result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"))));
			System.out.println(new String(result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"))));
		}
	}
}
