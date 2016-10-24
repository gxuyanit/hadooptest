package xxy.hadoop.hadooptest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

/**
 * hadoop hdfs CRUD 入门
 * @author xuxy
 * 2016年9月21日下午6:02:13
 */
public class HadoopHDFSCRUD
{
    private FileSystem fs = null;
    @Before 
    public void init() throws IOException, InterruptedException, URISyntaxException{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://xxy01:9000/");
        conf.set("dfs.replication", "1");
        //fs = FileSystem.get(conf);
        fs = FileSystem.get(new URI("hdfs://xxy01:9000/"), conf, "xxy");
    }
    @Test
    public void upload() throws IllegalArgumentException, IOException{
        fs.copyFromLocalFile(new Path("D://hadoopTestData/unionInfo2.txt"), new Path("/"));
    }
    @Test
    public void update() throws IllegalArgumentException, IOException{
        fs.rename(new Path("/test1/unionInfo.txt"), new Path("/test1/unionRequest.txt"));
    }
    @Test
    public void delete() throws IllegalArgumentException, IOException{
        fs.delete(new Path("/test1/unionInfo.txt"), false); //是否递归
    }
    @Test
    public void query() throws FileNotFoundException, IllegalArgumentException, IOException{
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true); //文件
        while (listFiles.hasNext()) {
            LocatedFileStatus file = listFiles.next();
            System.out.println(file.getPath().getName());
        }
        System.out.println("-------------------------");
        FileStatus[] listStatus = fs.listStatus(new Path("/")); //文件夹及文件
        for (FileStatus fileStatus : listStatus) {
            System.out.println(fileStatus.getPath().getName()+"----"+ (fileStatus.isFile()?"f":"d"));
        }
    }
}
