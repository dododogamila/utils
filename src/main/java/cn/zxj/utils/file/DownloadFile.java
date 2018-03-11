package cn.zxj.utils.file;

import cn.zxj.utils.propertiy.PropertyUtil;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    public void downloadExcelWithWB(HttpServletResponse response) {
        String sheetName = "教师基本信息";
        Set<String> header = new LinkedHashSet<String>();
        header.add("教师姓名");
        header.add("教师编码");
        header.add("联系方式");
        header.add("班级编号");
        header.add("班级名称");
        List<List<String>> datas = new ArrayList<List<String>>();

        HSSFWorkbook wb = createWorkBook(sheetName, header, datas);
        String excelName = "教师基本信息.xls";
        write2Response(response, wb, excelName);
    }
    private HSSFWorkbook createWorkBook(String sheetName, Set<String> header, List<List<String>> datas){
        //第一步，创建webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        createBook(sheetName, header, datas, wb);
        return wb;
    }
    private void createBook(String sheetName, Set<String> header,
                            List<List<String>> datas, HSSFWorkbook wb) {
        //第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);
        //第三步，在sheet中添加表头
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，并设置居中格式
        //设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建居中格式

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        int cellNum = 0;
        for(String str : header){
            HSSFCell cell = row.createCell(cellNum);
            cell.setCellValue(str);
            cell.setCellStyle(style);
            cellNum++;
        }
        //第五步，写入实体数据 实际应用中这些数据从数据库得到，
        for (int i = 0; i < datas.size();i++) {
            row = sheet.createRow(i+1);
            for (int j = 0; j < datas.get(i).size(); j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue((String)datas.get(i).get(j));
                cell.setCellStyle(style);
            }
        }
    }

    private void write2Response(HttpServletResponse response, HSSFWorkbook wb,String name) {
        String excelName = name;
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(excelName, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null != outputStream ){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
