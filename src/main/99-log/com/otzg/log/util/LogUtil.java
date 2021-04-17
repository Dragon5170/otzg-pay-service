package com.otzg.log.util;

import com.otzg.util.CheckUtil;
import com.otzg.util.DateUtil;

import java.io.*;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

public class LogUtil {

    static String version;
    static String ServerIp;
    static String localPath = "";
    static String webRootUrl = "http://127.0.0.1";
    //服务器文件保存路径
    static String fileSavePath = "";
    //服务器文件访问路径
    static String servUrl = "";
    static Vector<String> sysLog = new Vector<String>();
    static Vector<String> webAccessLog = new Vector<String>();
    //服务器文件访问Ip
    static String serverIpPath = "";
    static boolean debug=true;


    //设置当前应用版本
    public static void setVersion(String path) {
        version = path;
    }
    //获取当前应用版本
    public static String getVersion() {
        return version;
    }

    //加载ip黑名单
    static StringBuffer blackIp=new StringBuffer();

    /**
     * 获取服务器配置文件
     * @param filepath
     * @return
     */
    public static Properties getProperty(String filepath){
        Properties p = null;
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(filepath),"UTF-8");
            p = new Properties();
            p.load(in);
        } catch (Exception e){}
        return p;
    }

    /**
     * web访问日志写入内存
     * @author G/2018/3/12 14:52
     * @param str
     */
    public final static void addWebAccessLog(String str) {
        print(str);
        webAccessLog.add(DateUtil.yearMonthDayTime()+" "+str);
    }

    /**
     * 保存网站访问日志到硬盘
     * 此方法由定时任务管理
     * @author G/2015-11-2 下午3:33:14
     */
    public final static void saveWebAccessLog(String path){
        try{
            if(webAccessLog.size()>0){
                String webAccessLogPath = path+"/log/web/access"+ DateUtil.yearMonthDay()+".log";
                //保存文件路径
                File newFile = createFileOrFolder(webAccessLogPath);
                newFile.setExecutable(true);
                newFile.setWritable(true);
                newFile.setReadable(true,false);
                if(write2File(newFile,webAccessLog)){
                    webAccessLog.clear();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 读取系统访问日志
     * @param path
     * @param date
     * @return
     */
    public final static String readWebAccessLog(String path,String date){
        if(CheckUtil.isEmpty(date)){
            date = DateUtil.yearMonthDay();
        }
        path = path+"/log/web/access"+ date + ".log";
        return readLog(path);
    }

    /**
     * 系统日志写入内存向量
     * @author G/2015-11-2 上午10:56:58
     * @param info
     */
    public final static void addSysErrorLog(Object info,String level) {
        String str = DateUtil.yearMonthDayTime()+ " " + level + ":   " + info;
        print(str);
        webAccessLog.add(str);
    }

    /**
     * 保存服务器错误日志到硬盘
     * 此方法由定时任务管理
     * @author G/2015-11-2 下午3:33:14
     */
    public final static void saveSysErrorLog(String path){
        try{
            if(sysLog.size()>0){
                //保存文件路径
                String errorLogPath = path+"/log/sys/error"+ DateUtil.yearMonthDay()+".log";
                File log = createFileOrFolder(errorLogPath);
                if(write2File(log,sysLog)){
                    sysLog.clear();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 读取系统错误日志
     * @param webRoot
     * @param date
     * @return
     */
    public final static String readSysErrorLog(String webRoot,String date){
        if(CheckUtil.isEmpty(date)){
            date = DateUtil.yearMonthDay();
        }
        String path = webRoot+"/log/sys/error"+ date + ".log";
        return readLog(path);
    }

    /**
     * 支付结果写入本地文件
     * @author G/2018年1月21日下午1:10:23
     * @param str 要写入的内容
     */
    public final static void saveTradeLog(String str) {
        try{
            print(str);
            //交易日志
            String tradeLogPath = getFileSavePath()+"/log/trade/payMentLog" + DateUtil.yearMonth() + ".log";
            //获取系统支付日志路径
            File file = createFileOrFolder(tradeLogPath);
            write2File(file, DateUtil.yearMonthDayTime() + " " + str);
        }catch (IOException e){
            addSysErrorLog("save TradeLog:"+e.toString(),"error");
        }
    }

    /**
     * 读取交易日志
     * @param date
     * @return
     */
    public final static String readTradeLog(String path,String date){
        if(CheckUtil.isEmpty(date)){
            date = DateUtil.yearMonth();
        }
        String logPath = path+"/log/trade/payMentLog"+ date + ".log";
        return readLog(logPath);
    }

    /**
     * 读取日志文件
     * @param path
     * @return
     */
    final static String readLog(String path){
        StringBuffer re=new StringBuffer();
        try{
            File file = new File(path);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                re.append(sc.nextLine());
                re.append("<br>");
            }
            sc.close();
        }catch(IOException ex){
        }
        return re.toString();
    }

    /**
     * 调试输出
     * @author G/2015-11-12 上午9:27:04
     * @param str
     */
    public final static void print(Object str){
        if(debug){
            System.out.println("===print===");
            System.out.println(DateUtil.yearMonthDayTime()+" "+str);
        }
    }


    /**
     * 内存日志向量写入硬盘
     * @author G/2015-11-12 上午9:26:02
     * @param newLogs
     * @param newLogs
     * @return
     */
    final static synchronized boolean write2File(File file,Vector<String> newLogs) {
        boolean flag=false;
        //如果文件不可写
        if(!file.exists() ||!file.canWrite()){
            return flag;
        }
        Scanner sc = null;
        PrintWriter pw = null;
        try {
            // 如果文件不存在,则新建.
            createFileOrFolder(file);

            sc = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            // 先读出旧文件内容,并暂存sb中;
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                // 换行符作为间隔,扫描器读不出来,因此要自己添加.
                sb.append("\r\n");
            }
            sc.close();

            pw = new PrintWriter(new FileWriter(file),true);

            // A.写入旧文件内容.
            pw.println(sb.toString());

            // B.写入新日志.
            for(String newLog:newLogs){
//				print("=="+newLog);
                pw.println(newLog);
            }

            pw.flush();
            /*
             * 如果先写入A,最近日志在文件最后. 如是先写入B,最近日志在文件最前.
             */
            pw.close();
            //写入成功标志
            flag=true;
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return flag;
    }

    //创建文件及路径
    public static void createFileOrFolder(File newFile) throws IOException {
        if (!newFile.exists()) {
            File parentDir = new File(newFile.getParent());
            // 如果所在目录不存在,则新建.
            if (!parentDir.exists()) {
                parentDir.mkdirs();
                parentDir.setExecutable(true);
                parentDir.setWritable(true);
                parentDir.setReadable(true,false);
            }
            newFile.createNewFile();
        }
    }

    //系统创建文件的方法
    public static File createFileOrFolder(String filePath) throws IOException {
        File newFile =new File(filePath);
        createFileOrFolder(newFile);
        return newFile;
    }

    /**
     * 写入本地文件
     * @author G/2015-11-12 上午9:26:02
     * @param log
     * @param file
     * @return
     */
    final static synchronized boolean write2File(File file,String log) {
        boolean flag=false;
        //如果文件不可写
        if(!file.exists() ||!file.canWrite()){
            return flag;
        }
        Scanner sc = null;
        PrintWriter pw = null;
        try {
            // 如果文件不存在,则新建.
            createFileOrFolder(file);

            sc = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            // 先读出旧文件内容,并暂存sb中;
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                // 换行符作为间隔,扫描器读不出来,因此要自己添加.
                sb.append("\r\n");
            }
            sc.close();

            pw = new PrintWriter(new FileWriter(file),true);

            // A.写入旧文件内容.
            pw.println(sb.toString());

            // B.写入新日志.
            pw.println(log);

            pw.flush();
            /*
             * 如果先写入A,最近日志在文件最后. 如是先写入B,最近日志在文件最前.
             */
            pw.close();
            //写入成功标志
            flag=true;
        }catch(IOException ex){}
        return flag;
    }

    /**
     * 服务器上的文件保存路径
     * @author G/2018年1月13日
     * @return
     */
    public final static String getFileSavePath() {
        return fileSavePath;
    }

    public final static void setFileSavePath(String path) {
        fileSavePath=path;
    }

    /**
     * 服务器上的本地路径
     * @author G/2018年1月13日
     * @return
     */
    public final static String getLocalPath() {
        //编译环境下的路径
        return localPath;
    }

    public final static void setLocalPath(String path) {
        //编译环境下的路径
        localPath=path;
    }

    /**
     * 获取系统路径
     * @author:G/2017年10月24日
     * @param
     * @return
     */
    public final static String getWebRootUrl() {
        return webRootUrl;
    }

    /**
     * 设置服务器WebUrlRoot 根路径
     * @author:G/2017年10月24日
     * @param
     * @return
     */
    public final static void setWebRootUrl(String path) {
        webRootUrl = path;
    }

    /**
     * 获取文件上传相对路径
     * @author G/2018/5/4 11:40
     * @param
     */
    public final static String getServUrl() {
        return servUrl;
    }

    /**
     * 设置文件上传相对路径
     * @author G/2018/5/4 11:40
     * @param
     */
    public final static void setServUrl(String path) {
        servUrl = path;
    }

    public final static String getServerIpPath() {
        return serverIpPath;
    }

    public final static void setServerIpPath(String path) {
        serverIpPath=path;
    }
    public static void setLocalServerIp(){
        ServerIp=getAllLocalHostIP()[0];
    }

    public static String getLocalServerIp() {
        return ServerIp;
    }

    /**
     * 获取本机ip
     * @return
     */
    static String[] getAllLocalHostIP() {
        String[] ret = null;
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    ret = new String[addrs.length];
                    for (int i = 0; i < addrs.length; i++) {
                        ret[i] = addrs[i].getHostAddress();
                    }
                }
            }

        } catch (Exception ex) {
            ret = null;
        }
        return ret;
    }

    public static void main(String args[]) throws IOException {
//        String realPath=System.getProperty("user.dir");
//        File directory = new File("");// 参数为空
//        String proRootPath = directory.getCanonicalPath();
//        String allClassPath = System.getProperty("java.class.path");
//        print("realPath="+realPath);
//        print("proRootPath="+proRootPath);

    }
}
