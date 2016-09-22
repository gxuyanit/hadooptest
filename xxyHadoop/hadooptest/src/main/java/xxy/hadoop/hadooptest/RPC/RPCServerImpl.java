/**
 * @Copyright (C) 2016 本内容属于商业秘密，易微行(北京)科技有限公司拥有版权等所有权利.
 * 本内容商业秘密，禁止转发。	
*/
package xxy.hadoop.hadooptest.RPC;

/**
 * 
 * @author xuxy
 * @date: 2016年9月22日 下午5:48:28
 */
public class RPCServerImpl implements RPCServer {

    @Override
    public String add(String name) {

        return "add" + name +"ceccess!";
    }

    @Override
    public boolean delete() {

        return true;
    }

}
