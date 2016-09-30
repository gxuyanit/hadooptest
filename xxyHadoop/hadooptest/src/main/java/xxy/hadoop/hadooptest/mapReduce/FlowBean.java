/**
 * @Copyright (C) 2016 本内容属于商业秘密，易微行(北京)科技有限公司拥有版权等所有权利.
 * 本内容商业秘密，禁止转发。	
*/
package xxy.hadoop.hadooptest.mapReduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 可以传输在hadoop中的类，可以排序。
 * @author xuxy
 * @date: 2016年9月30日 下午2:39:52
 */
public class FlowBean implements WritableComparable<FlowBean>{
    private String phone;
    private long upFlow;
    private long dFlow;
    private long sumFlow;
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public long getUpFlow() {
        return upFlow;
    }
    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }
    public long getdFlow() {
        return dFlow;
    }
    public void setdFlow(long dFlow) {
        this.dFlow = dFlow;
    }
    public long getSumFlow() {
        return sumFlow;
    }
    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }
    /**
     * @param phone
     * @param upFlow
     * @param dFlow
     */
    public FlowBean(String phone, long upFlow, long dFlow) {
        super();
        this.phone = phone;
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.sumFlow = upFlow+dFlow;
    }
    @Override
    public String toString() {
        return "FlowBean [phone=" + phone + ", upFlow=" + upFlow + ", dFlow=" + dFlow + ", sumFlow=" + sumFlow + "]";
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        this.phone = in.readUTF();
        this.upFlow = in.readLong();
        this.dFlow = in.readLong();
        this.sumFlow = in.readLong();
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(phone);
        out.writeLong(upFlow);
        out.writeLong(dFlow);
        out.writeLong(sumFlow);
    }
    @Override
    public int compareTo(FlowBean o) {
        return this.sumFlow > o.getSumFlow()? -1:1;
    }
    
    
}
