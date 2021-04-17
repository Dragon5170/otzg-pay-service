package com.otzg.util;

import com.otzg.log.util.LogUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.util.Iterator;
import java.util.Random;

public class ImageUtil {

    /**
     * 利用Thumbnails进行压缩的方法
     *
     * @param fileByte 需要压缩的文件
     * @param width    图片宽度
     * @param height   图片高度
     * @param resize   true表示等比压缩false表示定比压缩
     * @return
     */
    public static BufferedImage zoomImage(byte[] fileByte, int width, int height, boolean resize) {
        byte[] fb = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = getBufferedImage(fileByte);
        try {
            if (resize) {//等比压缩
                Thumbnails.of(image).size(width, height).outputQuality(1f).outputFormat("jpg").toOutputStream(baos);
            } else {//定比压缩
                Thumbnails.of(image).size(width, height).keepAspectRatio(false).outputQuality(1f).outputFormat("jpg").toOutputStream(baos);
            }
            fb = baos.toByteArray();
            baos.close();
        } catch (IOException e1) {
            LogUtil.addSysErrorLog("" + e1, "error");
        }
        return getBufferedImage(fb);
    }

    public static byte[] zoomImage(File file, int width, int height, boolean resize) {
        byte[] fb = null;
        String formatName;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            formatName = getImageFormatName(file);
            if (resize) {//等比压缩
                Thumbnails.of(file).size(width, height).outputQuality(1f).outputFormat(formatName).toOutputStream(baos);
            } else {//定比压缩
                Thumbnails.of(file).size(width, height).keepAspectRatio(false).outputQuality(1f).outputFormat(formatName).toOutputStream(baos);
            }
            fb = baos.toByteArray();
            baos.close();
        } catch (IOException e1) {
            LogUtil.addSysErrorLog("" + e1, "error");
        }
        return fb;
    }

    /**
     * java图片压缩基本方法
     *
     * @param file
     * @param width
     * @param height
     * @param resize
     * @return
     */
    public static byte[] zoomImg(File file, int width, int height, boolean resize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage tag = null;
        int new_w; // 压缩后大小
        int new_h;
        byte[] b = null;
        try {
            BufferedImage srcimg = ImageIO.read(file);
            if (resize) {// 等比缩放
                double rate1 = ((double) srcimg.getWidth(null)) / (double) width + 0.1;
                double rate2 = ((double) srcimg.getHeight(null)) / (double) height + 0.1;
                double rate = rate1 > rate2 ? rate1 : rate2;
                new_w = (int) ((srcimg.getWidth(null)) / rate);
                new_h = (int) ((srcimg.getHeight(null)) / rate);
            } else {
                new_w = width;
                new_h = height;
            }

            String formatName = getImageFormatName(file);

            Image image = srcimg.getScaledInstance(new_w, new_h, Image.SCALE_DEFAULT);
            tag = new BufferedImage(new_w, new_h, srcimg.getType());
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();

            ImageIO.write(tag, formatName, baos);
            b = baos.toByteArray();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 图片合成方法,参数为两个文件
     *
     * @param bgfile
     * @param fgfile
     * @return 字节码
     */
    public static byte[] admix(File bgfile, File fgfile) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage fgBufferedImage = Thumbnails.of(fgfile).scale(1f).asBufferedImage();
            Thumbnails.of(bgfile).scale(1f).watermark(Positions.CENTER, fgBufferedImage, 1f).toOutputStream(baos);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * 获取文件类型图片的格式
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getImageFormatName(File file) throws IOException {
        String formatName = null;
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReader = ImageIO.getImageReaders(iis);
        if (imageReader.hasNext()) {
            ImageReader reader = imageReader.next();
            formatName = reader.getFormatName();
        }
        return formatName;
    }

    /**
     * byte[] 到 文件的保存方式
     *
     * @param buffer
     * @return f 返回file
     */
    public static File byte2file(byte[] buffer, File f) {
        File file = f;
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file), buffer.length);
            out.write(buffer);
            out.close();
        } catch (Exception e) {
            LogUtil.print("错误" + e);
            LogUtil.addSysErrorLog("ImageUtil.bute2file:" + e, "ERROR");
        }
        return file;
    }

    /**
     * 从一个上传文件转换到ByteArrayResource(spring mvc 可以再次上传)
     *
     * @param multipartFile
     * @author G/2018/6/23 17:48
     */
    public static ByteArrayResource getByteArrayResource(MultipartFile multipartFile) throws IOException {
        return new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() throws IllegalStateException {
                //必须要返回文件名
                return multipartFile.getOriginalFilename();
            }
        };
    }


    /**
     * 图片格式枚举类
     *
     * @author Administrator
     */
    public enum IMAGE_FORMAT {
        BMP("bmp"),
        JPG("jpg"),
        WBMP("wbmp"),
        JPEG("jpeg"),
        PNG("png"),
        GIF("gif");

        private String value;

        IMAGE_FORMAT(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 设置图片显示路径,若当前用户没有图片返回默认图片，若当前用户上传
     * 图片不满足5张，余下的由默认图片填补.
     *
     * @param imglink
     * @return String 以逗号分隔的图片路径'','',''...
     */
    public static final String setDefaultImage(String imglink, String DEF_IMAGEPATH) {
        String result = "";
        if (imglink == null || imglink.length() <= 0) {
            for (int i = 0; i < 5; i++) {
                result += i < 5 ? DEF_IMAGEPATH + "," : DEF_IMAGEPATH;
            }
        } else if (imglink.split(",").length <= 5) {
            String[] path = imglink.split(",");
            for (int i = 0; i < 5; i++) {
                result += i < path.length ? path[i].replace("_src", "_105X105") + "," : i == 4 ? DEF_IMAGEPATH : DEF_IMAGEPATH + ",";
            }
        } else {
            result = imglink;
        }
        return result;
    }

    /**
     * @param image
     * @return
     * @author G/2015-4-24 下午3:27:54
     */
    public static boolean isRightType(File image) {
        try {
            ImageIO.read(image);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * If 'filename' is a CMYK file, then convert the image into RGB,
     * store it into a JPEG file, and return the new filename.
     *
     * @param filename
     */
    public final static String cmyk2rgb(String filename) throws IOException {
        // Change this format into any ImageIO supported format. 
        String format = "gif";
        File imageFile = new File(filename);
        String rgbFilename = filename;
        BufferedImage image = ImageIO.read(imageFile);
        if (image != null) {
            int colorSpaceType = image.getColorModel().getColorSpace().getType();
            if (colorSpaceType == ColorSpace.TYPE_CMYK) {
                BufferedImage rgbImage =
                        new BufferedImage(
                                image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                ColorConvertOp op = new ColorConvertOp(null);
                op.filter(image, rgbImage);
                rgbFilename = changeExtension(imageFile.getName(), format);
                rgbFilename = new File(imageFile.getParent(), format + "_" + rgbFilename).getPath();
                ImageIO.write(rgbImage, format, new File(rgbFilename));
            }
        }
        return rgbFilename;
    }

    /**
     * Change the extension of 'filename' to 'newExtension'.
     *
     * @param filename
     * @param newExtension
     * @return filename with new extension
     */
    private static String changeExtension(String filename, String newExtension) {
        String result = filename;
        if (filename != null && newExtension != null && newExtension.length() != 0) {
            int dot = filename.lastIndexOf('.');
            if (dot != -1) {
                result = filename.substring(0, dot) + '.' + newExtension;
            }
        }
        return result;
    }

    public final static boolean isCMYK(String filename) {
        boolean result = false;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(e.getMessage() + ": " + filename);
        }
        if (img != null) {
            int colorSpaceType = img.getColorModel().getColorSpace().getType();
            result = colorSpaceType == ColorSpace.TYPE_CMYK;
        }
        return result;
    }

    /**
     * 根据给定的验证码生成图片
     *
     * @param codes
     * @return
     * @author G/2017年4月19日 下午2:55:26
     */
    public final static BufferedImage getRandCodeImg(String codes) {
        int width = 14 * codes.length() + 18, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        /**
         * 绘制干扰线
         */
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        int i = 0;
        for (String code : codes.split("")) {
            if (!code.isEmpty()) {
                g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
                g.drawString(code, 14 * i + 12, 24);
                i++;
            }
        }
        g.dispose();
        return image;
    }

    public final static BufferedImage getBufferedImage(byte[] bytes) {
        //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 随机颜色图片
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    // 获得原图宽高比
    public final static int[] getRatio(File img, int width, int height) {
        BufferedImage srcimg;
        int new_w = 0;
        int new_h = 0;
        int[] ratios = new int[2];
        try {
            srcimg = ImageIO.read(img);

            if (srcimg.getWidth(null) > srcimg.getHeight(null)) {
                new_w = width;
                new_h = (int) ((double) srcimg.getHeight(null)
                        / (double) ((double) srcimg.getWidth(null) / (double) width) + 0.1);
            } else {
                new_h = height;
                new_w = (int) ((double) srcimg.getWidth(null) / (double) ((double) srcimg
                        .getHeight(null) / (double) height + 0.1));
            }
            ratios[0] = new_w;
            ratios[1] = new_h;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ratios;
    }


    /**
     * 图片转base64字符串
     * @param imgFile 图片路径
     * @return
     */
    public static String imageToBase64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * base64编码字符串转换为图片
     * @param imgStr base64编码字符串
     * @param path   图片路径
     * @return
     */
    public static boolean base64StrToImage(String imgStr, String path) {
        if (imgStr == null)
            return false;

        imgStr = imgStr.substring(imgStr.indexOf(",")+1);

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //文件夹不存在则自动创建
            File tempFile = new File(path);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String args[]) {
        String p = "E:\\log\\upload\\head\\2019101191058_2076686402.png";
        String imgStr = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAFA3PEY8MlBGQUZaVVBfeMiCeG5uePWvuZHI////////////////////////////////////////////////////2wBDAVVaWnhpeOuCguv/////////////////////////////////////////////////////////////////////////wAARCAAoACgDASIAAhEBAxEB/8QAGAABAQEBAQAAAAAAAAAAAAAAAAIDAQT/xAAdEAADAQACAwEAAAAAAAAAAAAAAQIREkEhIjFi/8QAFwEBAQEBAAAAAAAAAAAAAAAAAQMAAv/EABgRAQADAQAAAAAAAAAAAAAAAAABAhEh/9oADAMBAAIRAxEAPwD11SlazKrp94LraZIpWtoVN0u9JBnG43mlS1AyispAFq22E9+TtZvr8O3OUyRSnnHfXh+hPHzyOAzaAqJ2kDGtda1KpYzKopdaACloiepKmKfWACnWNlrMqViAALP/2Q==";
        imgStr = imgStr.substring(imgStr.indexOf(",")+1);
        LogUtil.print(imgStr);
        base64StrToImage(imgStr, p);
//        String a = imageToBase64Str(p);
//        LogUtil.print(a);
    }
}
