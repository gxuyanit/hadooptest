package xxy.hadoop.hadooptest.RPC;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;

public class MyRPCServer {
	public static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
		Builder buid = new RPC.Builder(new Configuration());
		buid.setBindAddress("127.0.0.1").setPort(8989).setInstance(new RPCServerImpl()).setProtocol(RPCServer.class);
		Server build = buid.build();
		build.start();
	}
}
