/**
 * @Copyright (C) 2016 本内容属于商业秘密，易微行(北京)科技有限公司拥有版权等所有权利.
 * 本内容商业秘密，禁止转发。	
*/
package xxy.hadoop.hadooptest.mapReduce;

import java.util.HashMap;

import org.apache.hadoop.mapreduce.Partitioner;


/**
 * hadoop分区实现类
 * 根据城市区分reduce Task数量（结果文件数量也不同），要一同设置wcjob.setNumReduceTasks(areaMap.size())
 * @author xuxy
 * @date: 2016年9月30日 下午3:15:48
 */
public class AreaPartition<K2, V2> extends Partitioner<K2, V2>{
    private static HashMap<String, Integer> areaMap = new HashMap<String, Integer>();
    
    static{
        areaMap.put("137",0);
        areaMap.put("138",1);
        areaMap.put("139",2);
    }
    
    @Override
    public int getPartition(K2 key, V2 value, int arg2) {
        Integer reduceId = areaMap.get(key.toString().substring(0, 3));
        return reduceId == null ? 3:reduceId;
    }

}
