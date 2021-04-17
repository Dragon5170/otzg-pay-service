package com.otzg.file.api;

import com.otzg.file.service.FileServ;
import com.otzg.log.util.LogUtil;
import com.otzg.pay.api.AbstractController;
import com.otzg.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@RestController
public class FileApi extends AbstractController {

    @Autowired
    private FileServ fileServ;

    /**
     * 通用获取图片
     *
     * @param root
     * @param fileName
     * @param extName
     */
    @RequestMapping(value = "/upload/{root}/{fileName}.{extName}")
    public void getImg(@PathVariable("root") String root,
                       @PathVariable("fileName") String fileName,
                       @PathVariable("extName") String extName) {
        if (CheckUtil.isEmpty(fileName)
                || CheckUtil.isEmpty(extName)) {
            sendJson(false, RespTips.DATA_NULL.code, RespTips.DATA_NULL.tips);
            return;
        }
        //添加存储路径
        String filePath = LogUtil.getFileSavePath() + "/upload/" + root + "/" + fileName + "." + extName;
        P("filePath=>" + filePath);
        //获取图片文件
        File file = FileUtil.getFileBySavePath(filePath);
        if (file == null) {
            sendDataNull();
            return;
        }
        //返回结果
        sendImage(file);
    }

    /**
     * 通用上传图片
     *
     * @param file
     */
    @RequestMapping(value = {"/upload/{root}/image/upload"}, method = RequestMethod.POST)
    public void uploadImg(@PathVariable("root") String root,
                          Integer folderId,
                          MultipartFile file) {

        //判断是否是image
        if (file == null
                || !CheckUtil.isImage(file.getContentType())
                ) {
            sendParamError();
            return;
        }

        //保存
        Map jo = fileServ.saveLink(file, "/" + root, folderId);
        if (CheckUtil.isEmpty(jo)) {
            sendFail();
            return;
        }

        //返回成功并且包含路径
        sendSuccess(jo);
    }

    /**
     * 获取二维码
     * @param code
     * @param logoLink
     * @param width
     */
    @RequestMapping(value="/file/qrcode")
    public void createLinkQrCode(String code,String logoLink,Integer width){
        if(CheckUtil.isEmpty(code)){
            sendParamError();
            return;
        }

        if(!CheckUtil.isInteger(width)){
            width=250;
        }

        byte[] bfile = QrCodeUtil.getQrCodeImg(code,"png",width,width);

        //判断是否有logo图片
        if(logoLink!=null){
            File logoFile=new File(getServletContext().getRealPath("/")+logoLink);
            if(logoFile!=null&&logoFile.isFile()){
                bfile=QrCodeUtil.admix(bfile,logoFile);
            }
        }

        sendImage(bfile,null);
    }

    @RequestMapping(value="/auth/qrcode")
    public void createEncodeQrCode(String code,String logoLink,Integer width){
        code = AesUtil.getDeCode(code);
        if(CheckUtil.isEmpty(code)){
            sendParamError();
            return;
        }

        P("code="+code);

        if(!CheckUtil.isInteger(width)){
            width=250;
        }

        byte[] bfile = QrCodeUtil.getQrCodeImg(code,"png",width,width);

        //判断是否有logo图片
        if(logoLink!=null){
            File logoFile=new File(getServletContext().getRealPath("/")+logoLink);
            if(logoFile!=null&&logoFile.isFile()){
                bfile=QrCodeUtil.admix(bfile,logoFile);
            }
        }

        sendImage(bfile,null);
    }

}
