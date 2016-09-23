package xxy.hadoop.hadooptest.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class MyRPCClient {

	public static void main(String[] args) throws IOException {
		RPCServer proxy = RPC.getProxy(RPCServer.class, 33L, new InetSocketAddress("127.0.0.1", 8989),
				new Configuration());
		System.out.println(proxy.add("xxytest01"));
		System.out.println(proxy.delete());
	}

}
