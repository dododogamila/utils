package cn.zxj.utils.file;

import cn.zxj.utils.propertiy.PropertyUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadFile {
    public void downloadExcel(String name,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        InputStream inputStream = null;
        OutputStream os = null;
        try{
            response.setHeader("Content-Disposition", "attachment;fileName=" + name);
            inputStream = new FileInputStream(new File(PropertyUtil.ShareInstance().getProperty("downlaodPath")+name));
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b))!=-1) {
                System.out.println();
                os.write(b, 0, length);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(os!=null) {
                    os.close();
                }
                if(inputStream!=null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
