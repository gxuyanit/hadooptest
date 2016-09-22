/**
 * @Copyright (C) 2016 本内容属于商业秘密，易微行(北京)科技有限公司拥有版权等所有权利.
 * 本内容商业秘密，禁止转发。	
*/
package xxy.hadoop.hadooptest.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author xuxy
 * @date: 2016年9月22日 上午10:38:13
 */
public class RPCdemo {
    @Before
    public void serverRPC () throws HadoopIllegalArgumentException, IOException {
        Builder buid = new Builder(new Configuration());
        buid.setBindAddress("127.0.0.1").setPort(8989).setInstance(RPCServer.class).setProtocol(RPCServerImpl.class);
        Server build = buid.build();
        build.start();
    }
    
    @Test
    public void clientRPC() throws IOException {
        RPCServer proxy = RPC.getProxy(RPCServer.class, 33L, new InetSocketAddress("127.0.0.1", 8989), new Configuration());
        System.out.println(proxy.add("xxytest01"));
        System.out.println(proxy.delete());
    }
    
}
