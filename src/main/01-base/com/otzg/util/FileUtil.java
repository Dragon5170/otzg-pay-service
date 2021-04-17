package com.otzg.util;

import com.otzg.log.util.LogUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

/**
 * @author G./2018/8/2 14:05
 */
public class FileUtil {


    /**
     * 按照给定的位置，通过文件名获取文件集合
     * fileName 指文件名的头部 临时文件文件名的头部应该相同
     * @author G/2018-08-16 9:21
     * @param directoryPath 文件夹
     * @param fileName 文件名
     */
    public static void clearTmpFiles(String directoryPath,String fileName){
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return;
        }

        //获取已经上传文件
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (!file.isDirectory()
                    && file.getName().indexOf(fileName)>=0) {
                deleteFromDisk(file.getAbsolutePath());
            }
        }
    }

    /**
     * 按照给定的位置，通过文件名获取文件集合
     * @author G/2018-08-16 9:21
     * @param name
     * @param directoryPath
     */
    public static List<File> findByfileName(String name,String directoryPath){
        List<File> fileList = new ArrayList<>();

        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return null;
        }

        //获取已经上传文件
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (!file.isDirectory()
                    && file.getName().indexOf(name)>=0
                    ) {
                fileList.add(file);
            }
        }

        return fileList;
    }

    public static List<File> findByDirectory(String directoryPath){
        List<File> fileList = new ArrayList<>();

        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return null;
        }

        //获取已经上传文件
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFiles(String directoryPath, boolean isAddDirectory) {
        List<String> fileList = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return fileList;
        }

        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    fileList.add(file.getAbsolutePath());
                }
                fileList.addAll(getAllFiles(file.getAbsolutePath(),isAddDirectory));
            } else {
                fileList.add(file.getAbsolutePath());
            }
        }
        return fileList;
    }

    /**
     * 获取文件名去除路径名
     * @author G/2018/8/13 12:32
     * @param filePath
     */
    public static String getFileNameWithoutDirect(String filePath) {
        if(CheckUtil.isEmpty(filePath)){
            return "";
        }else if(filePath.lastIndexOf("/")>0){
            return filePath.substring(filePath.lastIndexOf("/")+1);
        }else if(filePath.lastIndexOf("\\")>0){
            return filePath.substring(filePath.lastIndexOf("\\")+1);
        }else{
            return filePath;
        }
    }

    /**
     * 获取文件名称 不包含扩展名
     * @author G/2018/8/13 12:30
     * @param filePath
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if(CheckUtil.isEmpty(filePath)){
            return "";
        }else if(filePath.lastIndexOf(".")>0){
            return filePath.substring(0,filePath.lastIndexOf("."));
        }else{
            return filePath;
        }
    }

    public static String getFileExtension(String filePath) {
        if(!CheckUtil.isEmpty(filePath) && filePath.lastIndexOf(".")>0){
            return filePath.substring(filePath.lastIndexOf(".")+1);
        }else{
            return "";
        }
    }

    /**
     * 按照行读取文件
     * @author G/2018/8/11 11:15
     * @param file
     */
    public static StringBuilder readLineFromFile(File file,String split) {
        StringBuilder sb = new StringBuilder();
        try{
            // 如果文件不存在,则新建.
            createFileOrFolder(file);
            Scanner sc = new Scanner(file);
            // 先读出旧文件内容,并暂存sb中;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if(line.trim().isEmpty()){
                    continue;
                }
                sb.append(line);
                // 换行符作为间隔,扫描器读不出来,因此要自己添加.
                sb.append(split);
            }
            sc.close();
        }catch (Exception e){}
        return sb;
    }

    /**
     * 按照行写入文件
     * @author G/2018/8/11 11:15
     * @param file
     */
    public static boolean writeLinetoFile(File file,String sb) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file),true);

            // A.写入旧文件内容.
            pw.println(sb);

            pw.flush();

            pw.close();
            //写入成功标志
            return true;
        }catch(IOException ex){
            return false;
        }
    }

    /**
     * 创建文件及路径
     * @author G/2018/8/11 11:26
     * @param newFile
     */
    public static void createFileOrFolder(File newFile) throws IOException {
        if (!newFile.exists()) {
            File parentDir = new File(newFile.getParent());
            // 如果所在目录不存在,则新建.
            if (!parentDir.exists()) {
                //创建文件夹
                parentDir.mkdirs();
                //设置文件夹权限
                setFileOrFolderRights(parentDir);
            }
            newFile.createNewFile();
            //设置文件权限
            setFileOrFolderRights(newFile);
        }
    }

    /**
     * linux 服务器设置访问权限
     * @author G/2018/8/11 11:26
     * @param file
     */
    private static void setFileOrFolderRights(File file){
        try {
            //设置文件夹权限
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_WRITE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);

            Path path = Paths.get(file.getAbsolutePath());
            Files.setPosixFilePermissions(path, perms);
        }catch (Exception e){

        }
    }

    /**
     * 系统创建文件的方法
     * @author G/2018/8/11 11:26
     * @param filePath
     */
    public static File createFileOrFolder(String filePath) throws IOException {
        File newFile =new File(filePath);
        createFileOrFolder(newFile);
        return newFile;
    }


    /**
     * 文件切割
     * @author G/2018-08-16 13:54
     * @param file
     */
    public static Map<Integer,byte[]> splitFile(File file,Integer eachSize){
        try{
            Map<Integer,byte[]> resultMap = new TreeMap<>();
            //每片大小
            byte[] buf = new byte[eachSize];
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            int len = 0;
            int count = 1;
            while ((len=fis.read(buf)) != -1){
                byte[] tempBuf = new byte[len];
                System.arraycopy(buf, 0, tempBuf, 0, len);
                resultMap.put(count++,tempBuf);
            }
            fis.close();
            //返回切片集合
            return resultMap;
        }catch (IOException e){
            LogUtil.print("=="+e);
            return null;
        }
    }

    /**
     * 文件合并保存
     * @author G/2018-08-16 13:55
     */
    public static void mergeFiles(List<File> fileList,File saveFile) throws IOException {
        /**
         * 需求：使用SequenceInputStream类来合并碎片文件
         * 1.创建一个list集合,来保存指定文件夹碎片流集合
         * 2.用集合工具类方法Collections.enumeration()方法将list集合转换为Enumeration
         * 3.新建一个SequenceInputStream流对象，并传入第2步的Enumeration
         * 4.创建一个输出流对象，创建缓冲区循环写第3步SequenceInputStream读取的内容
         */
        List<FileInputStream> list = new ArrayList<>();
        for (File file:fileList){
            FileInputStream fis = new FileInputStream(file);
            list.add(fis);
        }

        Enumeration<FileInputStream> en = Collections.enumeration(list);
        SequenceInputStream sis = new SequenceInputStream(en);

        FileOutputStream fos = new FileOutputStream(saveFile);

        byte[] buf = new byte[1024];
        int len = 0;
        while ((len=sis.read(buf)) != -1){
            fos.write(buf,0,len);
        }

        fos.close();
        sis.close();
    }

    //文件切片保存到map中
    public static void mergeFiles(Map<Integer,byte[]> bytes,File saveFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(saveFile);
        for (Integer index:bytes.keySet()){
            fos.write(bytes.get(index));
        }
        fos.close();
    }

    /**
     * 如果是连接路径(如：http://image.xxx.com) 转换成 d://upload/xxxx
     * @param filePath
     * @return
     */
    public static boolean deleteLink(String filePath){
        //连接路径转换到本地路径
        String realPath=LogUtil.getFileSavePath()+filePath.substring(filePath.indexOf(LogUtil.getServUrl())>-1?LogUtil.getServUrl().length():0);
        return deleteFromDisk(realPath);
    }

    /**
     * 文件从磁盘删除
     * 1、数据库文件路径filePath=url+realPath
     * 2、先去掉url路径LogUtil.getServUrl()
     * 3、加上本地LogUtil.getLocalPath()+realPath
     * @author G/2018/2/28 16:01
     * @param filePath
     */
    public static boolean deleteFromDisk(String filePath){
        File delFile = new File(filePath);
        if(delFile.exists() && delFile.isFile()){
            return delFile.delete();
        }else{
            return false;
        }
    }

    private static void deleteFromDisk(List<File> fileList){
        for(File delFile:fileList){
            if(delFile.exists() && delFile.isFile()){
            }
        }
    }

    public static File getFileBySavePath(String savePath) {
        File file = new File(savePath);
        if(file.exists() && file.isFile()){
            return file;
        }else{
            return null;
        }
    }

    public static void main(String args[]) throws IOException {
//        String filePath=getFileNameWithoutExtension("user.doc");
//        LogUtil.print("filePath="+filePath);

//        String p="D:\\workspace\\weikajiaoyu\\src\\main\\webapp\\upload\\course\\subject1\\2018818121335_1449446345.pps";
//        File f = new File(p);
//        Map<Integer,byte[]> r =splitFile(f);
//        for(Integer i:r.keySet()){
//            LogUtil.print("filePath="+r.get(i));
//            break;
//        }

    }

    /**
     * 在Linux系统下修改文件或文件夹的权限问题
     * @param dirFile  文件
     */
    public static void changeFolderPermission(File dirFile){
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        try {
            Files.setPosixFilePermissions(dirFile.toPath(), perms);
        } catch (Exception e) {
            e.printStackTrace();
            // logUtil.error("Change folder " + dirFile.getAbsolutePath() + " permission failed.", e);
        }
    }

}
