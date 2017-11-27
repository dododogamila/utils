package cn.zxj.utils.file;

import cn.zxj.utils.propertiy.PropertyUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class DownloadFile {
//    前端调用方式
//    get
//    function (fileName) {
//        var url = "...downloadExport?fileName="+fileName;
//        window.location.href = url;
//    };
//    post
//      var url = "...downloadExport?fileName="+fileName;
//     var form = $("<form id='getExcel'></form>").attr("method","post").attr("action",url + params)
//    $("body").append(form);
//    $("#getExcel").submit();
//    form.remove();
    public void downloadExcel(String name,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
//        设置文件类型，如果后缀有文件类型可以不设置，浏览器会自己识别
//        response.setContentType("multipart/form-data");
//        response.setContentType("application/vnd.ms-excel");
        InputStream inputStream = null;
        OutputStream os = null;
        try{
            //开发文件对话框
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name, "UTF-8"));//对文件名编码，针对中文
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
