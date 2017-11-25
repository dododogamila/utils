package cn.zxj.utils.file;

import cn.zxj.utils.date.DateUtil;
import cn.zxj.utils.propertiy.PropertyUtil;
import cn.zxj.utils.random.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class generateExcel {

    public void genExcel(){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet1 = wb.createSheet("软件使用统计1");
        //设置单元格宽度
        sheet1.setColumnWidth((short)3, 20* 256);
        sheet1.setColumnWidth((short)4, 20* 256);
        sheet1.setColumnWidth((short)5, 20* 256);
        sheet1.setColumnWidth((short)6, 20* 256);
        sheet1.setColumnWidth((short)7, 20* 256);
        // ---->有得时候你想设置统一单元格的高度，就用这个方法
        sheet1.setDefaultRowHeight((short)300);
        //样式1
        HSSFCellStyle style = wb.createCellStyle(); // 样式对象
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        //设置标题字体格式
        Font font = wb.createFont();
        //设置字体样式
        font.setFontHeightInPoints((short)11);   //--->设置字体大小
        font.setFontName("微软雅黑");   //---》设置字体，是什么类型例如：宋体
        font.setItalic(true);     //--->设置是否是加粗
        style.setFont(font);     //--->将字体格式加入到style1中
        style.setWrapText(true);   //设置是否能够换行，能够换行为true

        int rownum = 1;
        //data
        List<Object> data = new ArrayList<Object>();
        for(Object o:data){
            generalExeclSheet(wb, sheet1,style,o,rownum);
            rownum++;
        }
    }

    private void generalExeclSheet(HSSFWorkbook wb,HSSFSheet sheet,HSSFCellStyle style,
                                  Object rowdata,int rownum) {
        HSSFRow title = sheet.createRow(0);

        String[] titleNames = {"学校", "科目", "年级", "班型", "班号", "班级名称", "教师姓名", "教师邮箱", "操作日期"};

        for (int i = 0; i < titleNames.length; i++) {
            HSSFCell cell = title.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(titleNames[i]);
        }

        JSONObject rowdataJson = (JSONObject) rowdata;
        HSSFRow statrow = sheet.createRow(rownum);   //--->创建一行
        try {
            //设置学校
            if (StringUtils.isNotBlank(rowdataJson.getString("schoolid"))) {
                //学校
                HSSFCell cell_0 = statrow.createCell(0);
                cell_0.setCellStyle(style);
                cell_0.setCellValue(rowdataJson.getString("schoolid"));
            }

            //班级信息
            if (StringUtils.isNotBlank(rowdataJson.getString("subject"))) {
                //班号
                HSSFCell cell_1 = statrow.createCell(1);
                cell_1.setCellStyle(style);
                cell_1.setCellValue(rowdataJson.getString("subject"));
            }
            //...........................

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String writeFile(HSSFWorkbook wb) {
        //写入文件
        FileOutputStream fileOut = null;
        String fileName = null;
        try{
            String dirName = PropertyUtil.ShareInstance().getProperty("downlaodPath");
//            String dirName = "D:/downlaodPath/";
            File dir = new File(dirName);
            if(dir!=null && !dir.exists()) {
                dir.mkdirs();
            }
            //fileName = RandomUtil.generateString(10)+".xls";
            fileName = DateUtil.getFormatNowTime("yyyyMMddHHmmss")+".xls";
            File file = new File(dirName+fileName);
            if(file.exists()) {
                fileName = fileName.substring(0,14)+ RandomUtil.generateString(3)+".xls";
            }
            fileOut = new FileOutputStream(dirName+fileName);
            wb.write(fileOut);
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(fileOut != null){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }


}
