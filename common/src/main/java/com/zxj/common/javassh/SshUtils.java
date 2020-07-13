package cn.zxj.utils.javassh;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SshUtils {
    private static final String ENCODING = "UTF-8";
    public static Session getJSchSession(String host, int port, String user, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");  // 第一次访问服务器时不用输入yes
        session.setPassword(password);
        session.connect();
        return session;
    }
    public static String exeCommand(Session session,List<String> commands) throws JSchException, IOException {
        ChannelShell channel = null;
        String execInfo = "";
        InputStream instream = null;
        OutputStream outstream = null;
        try {
            //创建sftp通信通道
            channel = (ChannelShell) session.openChannel("shell");
            channel.connect(1000);

            //获取输入流和输出流
            instream = channel.getInputStream();
            outstream = channel.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outstream);

            //发送需要执行的SHELL命令，需要用\n结尾，表示回车
            for (String command:commands){
                printWriter.println(command);
            }
            printWriter.println("exit");//加上个就是为了，结束本次交互
            printWriter.flush();
            //获取命令执行的结果

            BufferedReader in = new BufferedReader(new InputStreamReader(instream));
            String msg = null;
            while((msg = in.readLine())!=null){
                execInfo = execInfo+msg+"\n";
            }
//            while (true){
//                try{
//                    Thread.sleep(1000);
//                }catch(Exception ee){}
//
//                if (instream.available()>0){
//                    byte[] data = new byte[instream.available()];
//                    int nLen = instream.read(data);
//                    if(nLen<=0)
//                        return "network error";
//                    execInfo =execInfo+ new String(data, 0, nLen);
//                    break;
//                }
//            }

//            StringBuilder result = new StringBuilder();
//            byte[] tmp=new byte[1024];
//            while(true){
//                while(instream.available()>0){
//                    int i=instream.read(tmp, 0, 1024);
//                    if(i<0)break;
//                    // 将字节流转换成为字符串并拼接到StringBuilder中
//                    result.append(new String(tmp, 0, i));
//                }
//                if(channel.isClosed()){
//                    if(instream.available()>0) continue;
//                    break;
//                }
//                try{Thread.sleep(1000);}catch(Exception ee){}
//            }
//            execInfo = result.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (outstream!=null){
                outstream.close();
            }
            if (instream!=null){
                instream.close();
            }
            if (channel!=null){
                channel.disconnect();
            }

        }

        return execInfo;
    }
    public static void closeSession(Session session){
        if (session==null){
            return;
        }
        session.disconnect();
    }

}
