package com.otzg.file.service;

import com.otzg.base.AbstractServ;
import com.otzg.file.dao.FileIndexDao;
import com.otzg.file.dao.FolderDao;
import com.otzg.file.entity.FileIndex;
import com.otzg.file.entity.Folder;
import com.otzg.log.util.LogUtil;
import com.otzg.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author G./2018/2/28 11:51
 */
@Service(value = "FileServ")
public class FileServImpl extends AbstractServ implements FileServ {

    @Autowired
    FileIndexDao fileIndexDao;
    @Autowired
    FolderDao folderDao;

    //项目内保存文件的路径
    private final static String fileSavePath = "/upload";

    @Override
    public int saveFolder(Folder folder) {
        folderDao.save(folder);
        return 0;
    }

    @Override
    public int deleteFolder(Integer id) {
        folderDao.deleteById(id);
        return 0;
    }

    @Override
    public Object[] getFolder() {
        return folderDao.findAll().stream().map(Folder::getJson).toArray();
    }

    //==========================================================================

    /**
     * 保存文件上传切片集合
     * @author G/2018-08-21 9:40
     */
    private static Map<String,Map> userTempFileUploadMap = new HashMap<>();
    /**
     * 保存切片集合
     * @author G/2018-08-18 16:26
     */
    private static Map<String,Map> userTempFileDownloadMap = new HashMap<>();

    /**
     * 获取所有临时向量中的token
     * @author G/2018-08-22 11:39
     * @param
     */
    @Override
    public Set<String> getTempTokenSet(String directory){
        Set<String> tokenSet = new HashSet<>();
        //文件夹路径
        String directoryPath = LogUtil.getFileSavePath()+fileSavePath+directory+"/";
        P("file token="+directoryPath);
        //文件全部保存成功返回文件集合
        List<File> fileList = FileUtil.findByDirectory(directoryPath);
        if(fileList!=null && fileList.size()>0){
            for (File file:fileList){
                P("file token="+file.getName().substring(0,file.getName().indexOf(".")));
                tokenSet.add(file.getName().substring(0,file.getName().indexOf(".")));
            }
        }

        //获取上传文件切片集合
        Map<String,Map> um = userTempFileUploadMap;
        for(String token:um.keySet()){
            tokenSet.add(token);
        }

        //获取下载文件切片集合
        Map<String,Map> dm = userTempFileDownloadMap;
        for(String token:dm.keySet()){
            tokenSet.add(token);
        }

        return tokenSet;
    }


    //保存文件上传切片临时向量
    @Override
    public Integer saveUploadMap(String key,Integer index,Integer total,MultipartFile file){
        try{
            byte[] bytes = file.getBytes();
            Map<Integer,byte[]> tempMap = userTempFileUploadMap.get(key);
            if(tempMap==null) {
                tempMap = new TreeMap<>();
            }

            tempMap.put(index,bytes);
            userTempFileUploadMap.put(key,tempMap);
            //如果文件上传结束size=total,返回1
            if(tempMap.size()==total){
                return 1;
            }else{
                //如果上传成功返回0;没有全部传完
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            //如果产生错误则重新上传
            return -1;
        }
    }


    //按tokne取回课件向量
    private Map<Integer,byte[]> getUploadMap(String key){
        return userTempFileUploadMap.get(key);
    }

    @Override
    public void removeUploadMap(String key){
        userTempFileUploadMap.remove(key);
    }

    @Override
    public Integer saveDownloadMap(String key, File file, Integer eachSize) {
        Map<Integer,byte[]> tempFileMap = FileUtil.splitFile(file,eachSize);
        if (tempFileMap!=null && tempFileMap.size()>0){
            userTempFileDownloadMap.put(key,tempFileMap);
            return tempFileMap.size();
        }else{
            return 0;
        }
    }

    @Override
    public byte[] getSplipByIndex(String key,Integer index) {
        Map<Integer,byte[]> downloadMap = userTempFileDownloadMap.get(key);
        if(downloadMap!=null && downloadMap.size()>0){
            return downloadMap.get(index);
        }
        return new byte[0];
    }

    @Override
    public void removeDownloadMap(String key) {
        userTempFileDownloadMap.remove(key);
    }

    @Override @Transactional
    public JSONObject saveHeadImg(MultipartFile file) {
        JSONObject jo = new JSONObject();
        //原始文件名
        String fileName = file.getOriginalFilename();

        //头像文件存储路径
        String filePath = fileSavePath+"/head/"+DateUtil.yearMonthDayTimeShort()+"_"+Math.abs(fileName.hashCode())+fileName.substring(fileName.lastIndexOf("."));

        //保存文件
        try {
            // 如果文件不存在,则新建.
            File newFile=FileUtil.createFileOrFolder(LogUtil.getFileSavePath()+filePath);
            file.transferTo(newFile);

            //头像等比压缩 尺寸=100X100
            byte[] fb = ImageUtil.zoomImage(newFile,100,100,true);
            //二次保存
            ImageUtil.byte2file(fb,newFile);

            //保存到数据库
            FileIndex fi = getFileIndex(fileName,LogUtil.getServUrl()+filePath,0,file.getSize(),null);

            fileIndexDao.save(fi);

            //构建返回值
            jo.put("id",fi.getId());
            jo.put("fileName",fileName);
            jo.put("filePath",LogUtil.getServUrl()+filePath);

        } catch (IOException e) {
            rollBack();
            P("FileServImpl.saveHeadImg:"+e,"ERROR");
        }
        return jo;
    }

    @Override
    public FileIndex findById(Long id) {
        return fileIndexDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Map saveLink(MultipartFile file,String filePath) {
        //原始文件名
        String fileName = file.getOriginalFilename();

        int type= 0;
        //如果包含商品路径type=1
        if(filePath.indexOf("goods")>-1){
            type=1;
        }

        //新文件名存储
        filePath = fileSavePath+filePath+"/"+DateUtil.yearMonthDayTimeShort()+"_"+Math.abs(fileName.hashCode())+fileName.substring(fileName.lastIndexOf("."));
        //保存文件
        try {

            // 如果文件不存在,则新建.
            File newFile=FileUtil.createFileOrFolder(LogUtil.getFileSavePath()+filePath);
            file.transferTo(newFile);


            //保存到数据库
            FileIndex fi = getFileIndex(fileName,LogUtil.getServUrl()+filePath,type,file.getSize(),null);
            fileIndexDao.save(fi);
            //构建返回值
            return fi.getBaseJson();
        } catch (IOException e) {
            rollBack();
            P("FileServImpl.saveImage:"+e,"ERROR");
            return null;
        }
    }

    @Override
    @Transactional
    public Map saveLink(MultipartFile file, String filePath, Integer folderId) {
        Folder folder = null;
        if(!CheckUtil.isEmpty(folderId)){
            folder = folderDao.findById(folderId).orElse(null);
        }
        //原始文件名
        String fileName = file.getOriginalFilename();

        int type= 0;
        //如果包含商品路径type=1
        if(filePath.indexOf("goods")>-1){
            type=1;
        }

        //新文件名存储
        filePath = fileSavePath+filePath+"/"+DateUtil.yearMonthDayTimeShort()+"_"+Math.abs(fileName.hashCode())+fileName.substring(fileName.lastIndexOf("."));
        //保存文件
        try {

            // 如果文件不存在,则新建.
            File newFile=FileUtil.createFileOrFolder(LogUtil.getFileSavePath()+filePath);
            file.transferTo(newFile);

            //保存到数据库
            FileIndex fi = getFileIndex(fileName,LogUtil.getServUrl()+filePath,type,file.getSize(),folder);
            fileIndexDao.save(fi);

            //构建返回值
            return fi.getBaseJson();
        } catch (IOException e) {
            rollBack();
            P("FileServImpl.saveImage:"+e,"ERROR");
            return null;
        }
    }

    @Override
    @Transactional
    public FileIndex saveLocal(MultipartFile file,String filePath) {
        //原始文件名
        String fileName = file.getOriginalFilename();
        //保存文件
        try {

            // 如果文件不存在,则新建.
            File newFile=FileUtil.createFileOrFolder(filePath);
            file.transferTo(newFile);

            //保存到数据库
            FileIndex fi = getFileIndex(fileName,filePath,0,file.getSize(),null);
            fileIndexDao.save(fi);

            return fi;
        } catch (IOException e) {
            rollBack();
            P("FileServImpl.saveLocal"+e,"ERROR");
        }
        return null;
    }

    @Override @Transactional
    public int deleteFile(String filePath) {
        return 0;
    }

    @Override @Transactional
    public int deleteFiles(Long[] ids) {
        List<FileIndex> fileList = fileIndexDao.findByIds(FuncUtil.Array2List(ids));
        fileList.forEach(fileIndex -> {
            if(FileUtil.deleteLink(fileIndex.getPath())){
                fileIndexDao.deleteById(fileIndex.getId());
            }
        });
        return 0;
    }

    @Override
    public int autoDeleteFiles(){
        int count=0;
        List<String> filePathesDb = fileIndexDao.findAllPath();
        List<String> filePathes = FileUtil.getAllFiles(LogUtil.getFileSavePath()+"/upload/course/subject1",true);
        if(filePathes==null || !filePathes.isEmpty()){
            return 0;
        }
        if(filePathesDb==null || !filePathesDb.isEmpty()){
            return 0;
        }

//        for(String filePath:filePathes){
//            LogUtil.print("filePath =>>"+FileUtil.getFileNameWithoutDirect(filePath));
//            for(String filePathDb:filePathesDb){
//                if(!filePathesDb.contains(filePath)){
//                    LogUtil.print("delete =>>"+FileUtil.getFileNameWithoutDirect(filePath));
//                    if(deleteFromDisk(filePath)){
//                        count++;
//                    }
//                }
//            }
//        }
        return count;
    }

    @Override @Transactional
    public int deleteFile(Long id) {
        int count=0;
        Optional<FileIndex> op = fileIndexDao.findById(id);
        if(!op.isPresent()){
            return count;
        }
        FileIndex fileIndex = op.get();
        if(FileUtil.deleteFromDisk(fileIndex.getPath())){
            fileIndexDao.deleteById(fileIndex.getId());
            count++;
        }
        return count;
    }

    /**
     * 系统自动清除临时向量临时文件
     * @author G/2018-08-22 10:39
     * @param directory
     * @param key
     */
    @Override
    public void clearTemp(String directory, String key) {
        //清空临时文件
        removeUploadFileTemp(directory,key);
        //清除上传文件内存向量
        removeUploadMap(key);
        //清除下载文件内存向量
        removeDownloadMap(key);
    }
    
    /**
     * 创建fileIndex对象
     * @param fileName
     * @param filePath
     * @param type
     * @param size
     * @param folder
     * @return
     */
    FileIndex getFileIndex(String fileName, String filePath, int type, long size,Folder folder){
        FileIndex fd=new FileIndex();
        fd.setName(CodeUtil.urlDecode(fileName));
//        fd.setName(fileName);
        fd.setPath(filePath);
        fd.setUpdateTime(DateUtil.now());
        fd.setSize(size);
        fd.setType(type);
        fd.setStatus(1);
        fd.setFolder(folder);
        return fd;
    }

    /**
     * 文件从数据
     * @author G/2018/2/28 16:01
     * @param ids
     */
    private void deleteFromDbByIds(Long[] ids){
        fileIndexDao.deleteByIds(FuncUtil.Array2List(ids));
    }



    /*************************************************************************************
     * 大文件拆分并保存的临时文件夹
     * 1、以用户登录token加上切片总数+当前切片保存为临时文件
     *     =>key.total.index
     * 2、以文件名查询当前文件总数如果总数等于total数量返回文件集合
     * @author G/2018-08-15 19:07
     * @param directory
     *************************************************************************************/
    @Override
    public void removeUploadFileTemp(String directory, String key) {
        //文件夹路径
        String directoryPath = LogUtil.getFileSavePath()+fileSavePath+directory+"/";
        FileUtil.clearTmpFiles(directoryPath,key);
    }

    /**
     * 文件分片存储到临时文件夹
     * 返回文件集合
     * @author G/2018-08-16 14:46
     * @param file
     * @param key
     * @param total
     * @param index
     * @param filePath
     */
    @Override
    public Integer saveUploadFileTemp(MultipartFile file,
                           String key,
                           Integer total,
                           Integer index,
                           String filePath) {

        //文件夹路径
        String directoryPath = LogUtil.getFileSavePath()+fileSavePath+filePath+"/";
        //原始文件名
        String fileName = key+"."+total+"."+index;
        //新文件名存储
        filePath = directoryPath+fileName;

        //保存文件
        try {
            // 如果文件不存在,则新建.
            File newFile=FileUtil.createFileOrFolder(filePath);
            file.transferTo(newFile);
        } catch (IOException e) {
            P("FileServImpl.saveTemp"+e,"ERROR");
            return -1;
        }

        //文件全部保存成功返回文件集合
        List<File> fileList = FileUtil.findByfileName(FileUtil.getFileNameWithoutExtension(fileName),directoryPath);
        if(fileList.size() == total){
            //所有切片都已经上传
            return 1;
        }else{
            //表示当前切片上传成功
            return 0;
        }
    }

    /**
     * 获取全部文件分片
     * @author G/2018-08-22 9:19
     * @param fileName
     * @param filePath
     */
    @Override
    public List<File> getUploadFileTemp(String fileName, String filePath) {

        //文件夹路径
        String directoryPath = LogUtil.getFileSavePath()+fileSavePath+filePath+"/";
        //文件全部保存成功返回文件集合
        return FileUtil.findByfileName(fileName,directoryPath);
    }


    /**
     * 文件集合合并保存到
     * @author G/2018-08-16 14:47
     * @param fileName
     * @param key
     * @param filePath
     */
    @Override @Transactional
    public FileIndex saveLocal(String fileName, String key, String filePath,String directory) {
        List<File> fileList = getUploadFileTemp(key,directory);
        //新文件名存储
        filePath = LogUtil.getFileSavePath()+fileSavePath+filePath+"/"+DateUtil.yearMonthDayTimeShort()+"_"+Math.abs(fileName.hashCode())+fileName.substring(fileName.lastIndexOf("."));
        //保存文件
        try {

            // 如果文件不存在,则新建.
            File newFile=FileUtil.createFileOrFolder(filePath);
            //文件合并保存
            FileUtil.mergeFiles(fileList,newFile);

            //删除临时文件
            removeUploadFileTemp(directory,key);
            //保存到数据库
            FileIndex fi = getFileIndex(fileName,filePath,0,newFile.getTotalSpace(),null);
            fileIndexDao.save(fi);

            return fi;

        } catch (IOException e) {
            rollBack();
            P("FileServImpl.saveLocal"+e,"ERROR");
        }
        return null;
    }


    /**
     * 1、获取保存在内存或者磁盘上的文件分片
     * 2、合并文件保存
     * @author G/2018-08-22 9:21
     * @param fileName
     * @param key
     * @param filePath
     */
    @Override @Transactional
    public FileIndex saveLocal(String fileName, String key, String filePath) {
        //新文件名存储
        filePath = LogUtil.getFileSavePath()+fileSavePath+filePath+"/"+DateUtil.yearMonthDayTimeShort()+"_"+Math.abs(fileName.hashCode())+FileUtil.getFileExtension(fileName);
        //保存文件
        try {

            // 如果文件不存在,则新建.
            File newFile=FileUtil.createFileOrFolder(filePath);

            Map<Integer,byte[]> bytes = getUploadMap(key);
            //文件合并保存
            FileUtil.mergeFiles(bytes,newFile);
            //保存到内存的需要清除向量
            removeUploadMap(key);

            //保存到数据库
            FileIndex fi = getFileIndex(fileName,filePath,0,newFile.getTotalSpace(),null);
            fileIndexDao.save(fi);

            return fi;

        } catch (IOException e) {
            rollBack();
            P("FileServImpl.saveLocal"+e,"ERROR");
        }
        return null;
    }

}
