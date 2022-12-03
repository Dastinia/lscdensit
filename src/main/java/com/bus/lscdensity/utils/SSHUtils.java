package com.bus.lscdensity.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
/**
 * @author JJJY
 */
@Slf4j
public class SSHUtils {
    private  String userName;
    private  String passWord;
    private  String serverIp;
    private  Connection connection;

    private Charset charset = StandardCharsets.UTF_8;

public SSHUtils(String userName,String passWord,String serverIp){
    this.userName=userName;
    this.passWord=passWord;
    this.serverIp=serverIp;
}

    /**
     * ssh连接
     * @return
     * @throws IOException
     */
    public boolean sshConnect() throws IOException {
        connection = new Connection(serverIp);
        connection.connect();
        if (connection.authenticateWithPassword(userName, passWord)){
            log.info("连接成功");
            return true;
        }
        return false;
    }

    public StringBuilder exec(String cmds) throws IOException {
        InputStream in = null;
        StringBuilder result = new StringBuilder();
        try {
            if (this.sshConnect()) {
                // 打开一个会话
                Session session = connection.openSession();
                log.info("执行cmd：{}",cmds);
                session.execCommand(cmds);
                in = session.getStdout();
                result = this.processStdout(in, this.charset);
                connection.close();
            }
        } finally {
            if (null != in) {
                in.close();
            }
        }
        return result;
    }

    /**
     * 解析流获取字符串信息
     *
     * @param in      输入流对象
     * @param charset 字符集
     * @return 脚本输出结果
     */
    public StringBuilder processStdout(InputStream in, Charset charset) throws FileNotFoundException {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        try {
            // 此方法是休息10秒后最后一次性输出2行数据
            int length;
            while ((length = in.read(buf)) != -1) {
                sb.append(new String(buf, 0, length));
            }

            // 这个会按照脚本一步一步执行，中途有休息10秒。
            BufferedReader reader = null;
            String result = null;
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while ((result = reader.readLine()) != null) {
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }
}
