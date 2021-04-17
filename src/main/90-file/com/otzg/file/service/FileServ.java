package com.otzg.file.service;

import com.otzg.file.entity.FileIndex;
import com.otzg.file.entity.Folder;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 *      处理文件上传类
 *      1、处理头像
 *      2、处理分类上传
 * @Author G./2018/2/28 11:15
 */
public interface FileServ {

    int saveFolder(Folder folder);
    int deleteFolder(Integer id);
    Object[] getFolder();


    //===================================================
    /**
     * 保存头像，并返回头像路径
     * @author G/2018/2/28 14:28
     * @param file
     */
    JSONObject saveHeadImg(MultipartFile file);

    FileIndex findById(Long id);

    /**
     * 通用文件上传接口,文件路径由应用指定
     * @author G/2018/3/9 16:06
     * @param file 上传文件
     * @param filePath 文件路径
     */
    Map saveLink(MultipartFile file, String filePath);

    /**
     * 通用文件上传接口,文件路径由应用指定
     * 可以加入一个文件夹信息
     * @param file
     * @param filePath
     * @param folderId
     * @return
     */
    Map saveLink(MultipartFile file, String filePath,Integer folderId);


    /**
     * 通用创建并返回文件类
     * @author G/2018/7/19 11:56
     * @param file
     * @param filePath
     */
    FileIndex saveLocal(MultipartFile file,String filePath);

    /**
     * 获取全部文件分片
     * @author G/2018-10-06 9:49
     * @param fileName
     * @param filePath
     */
    List<File> getUploadFileTemp(String fileName, String filePath);


    /**
     * 文件集合合并保存到
     * @author G/2018-10-06 9:49
     * @param fileName
     * @param key
     * @param filePath
     * @param directory
     */
    FileIndex saveLocal(String fileName, String key, String filePath,String directory);

    /**
     * 根据文件路径查找并删除文件
     * 1、数据库文件记录和文件物理位置
     * 2、从右侧匹配
     * @author G/2018/2/28 15:48
     * @param filePath
     */
    int deleteFile(String filePath);

    /**
     * 根据文件在数据库中存放的id删除
     * 1、数据库文件记录和文件物理位置
     * @author G/2018/2/28 15:50
     * @param ids
     */
    int deleteFiles(Long[] ids);

    /**
     * 删除数据库中没有的文件
     * @author G/2018/8/2 14:24
     * @param
     */
    int autoDeleteFiles();

    int deleteFile(Long id);


    /*************************************************************************************
     * 大文件切片上传
     * 方案一、文件切片、保存到内存，适合单个服务器
     * 方案二、文件切片、保存到硬盘，可以兼容负载均衡服务器，速度会有一定延迟
     * @author G/2018-08-15 19:07
     *************************************************************************************/


    /**
     * 删除指定的临时文件
     * @author G/2018-08-22 10:31
     * @param directory 文件夹路径
     * @param key 文件名头部
     */
    void clearTemp(String directory,String key);


    /** 
     * 文件上传结束后删除临时文件
     * @author G/2018-10-06 16:36
     * @param directory
     * @param key       
     */
    void removeUploadFileTemp(String directory, String key);

    /**
     * 大文件上传保存到临时文件夹
     * @author G/2018-08-21 9:58
     * @param file
     * @param key
     * @param total
     * @param index
     * @param filePath
     */
    Integer saveUploadFileTemp(MultipartFile file,
                        String key,
                        Integer total,
                        Integer index,
                        String filePath);


    /**
     * 切片文件最后保存
     * @author G/2018-08-21 9:21
     * @param fileName
     * @param key
     * @param filePath
     */
    FileIndex saveLocal(String fileName,String key, String filePath);

    /**
     * 获取存在临时文件或者临时占用内存的token集合
     * @author G/2018-08-22 11:45
     * @param
     */
    Set<String> getTempTokenSet(String directory);

    /**
     * 切片文件保存到临时向量
     * @author G/2018-08-21 9:20
     * @param key
     * @param index
     * @param total
     * @param file
     * @return -1:当前切片接收失败,
     *          0:当前切片接收成功,
     *          1:全部切片接收成功
     */
    Integer saveUploadMap(String key, Integer index, Integer total, MultipartFile file);

    /**
     * 销毁切片文件存储向量
     * @author G/2018-08-21 9:21
     * @param key
     */
    void removeUploadMap(String key);

    /**
     * 加载文件切片后保存到内存向量
     * @author G/2018-08-21 9:29
     * @param key
     * @param file
     * @param eachSize
     */
    Integer saveDownloadMap(String key,File file,Integer eachSize);

    /**
     * 获取文件下载的每一片
     * @author G/2018-08-21 9:36
     * @param index
     */
    byte[] getSplipByIndex(String key,Integer index);

    /** 
     * 删除指定的内存集合
     * @author G/2018-08-21 9:39
     * @param key       
     */
    void removeDownloadMap(String key);
}
