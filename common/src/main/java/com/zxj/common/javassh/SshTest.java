package cn.zxj.utils.javassh;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SshTest {
    private final static Logger logger = LoggerFactory.getLogger(SshTest.class);
    public static void main(String[] args) throws JSchException, IOException {
        Session session = null;
        try {
            Integer port =22;
            String username = "username";
            String passwd = "passwd";
            String host = "127.0.0.1";
            session = SshUtils.getJSchSession(host,port,username,passwd);

            List<String> commands = new ArrayList<String>();
            commands.add("cd /neworiental/weixin/src");
            commands.add("./restart.sh");
            String start_result = SshUtils.exeCommand(session,commands);
            logger.info(start_result);

        } catch (JSchException e) {
            throw e;
        }finally {
            SshUtils.closeSession(session);
        }
        return;
    }
}
