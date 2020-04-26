package cn.zxj.utils.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.*;

public class ExcelUtil {  
    /** 
     * Excel文件到List 
     * @param fileName 
     * @param sheetIndex    // 工作表索引 
     * @param skipRows  // 跳过的表头 
     * @return 
     * @throws Exception  
     */  
    public static List<Object> readToList(String fileName, int sheetIndex, int skipRows) throws Exception{  
        List<Object> ls = new ArrayList<Object>();  
        Workbook wb = loadWorkbook(fileName);
        if (null != wb) {  
            Sheet sh = wb.getSheetAt(sheetIndex);
            int rows = sh.getPhysicalNumberOfRows();  
            for (int i = skipRows; i < rows; i++) {  
                Row row = sh.getRow(i);
                if(null==row){  
                    break;  
                }  
                int cells = row.getPhysicalNumberOfCells();  
                if(cells==0){  
                    continue;  
                }  
                List<String> r = new ArrayList<String>(cells);  
                for (int c = 0; c < cells; c++) {  
                    r.add(row.getCell(c).getStringCellValue());  
                }  
                ls.add(r);  
            }  
        }  
          
        return ls;  
    }  
      
    /** 
     * Excel文件到List 
     * @param sheetIndex    // 工作表索引
     * @param skipRows  // 跳过的表头 
     * @return 
     * @throws Exception  
     */  
    public static List<Object> readToList(InputStream inputStream,String excelType, int sheetIndex, int skipRows) throws Exception{  
    	List<Object> ls = new ArrayList<Object>();  
    	Workbook wb = loadWorkbook(inputStream,excelType);
    	if (null != wb) {  
    		Sheet sh = wb.getSheetAt(sheetIndex);
    		//总行数,包括中间的空白行
    		int rows=sh.getLastRowNum()+1;
    		for (int i = skipRows; i < rows; i++) {  
    			Row row = sh.getRow(i);
    			if(null==row){  
    				continue;  
    			}  
    			int cells = row.getLastCellNum();  
    			if(cells==0){  
    				continue;  
    			}  
    			List<String> r = new ArrayList<String>(cells);  
    			for (int c = 0; c < cells; c++) {  
    				if(null==row.getCell(c)){
    					r.add("");
    				}else{
                        Cell cell = row.getCell(c);
    				    switch (row.getCell(c).getCellType()){
                            case Cell.CELL_TYPE_NUMERIC:
                                String value=null;
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    value = DateFormatUtils.format(date, "yyyy-MM-dd");
                                } else {
                                    row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
                                    value =row.getCell(c).getStringCellValue();
                                }
                                r.add(value);
                                break;
                            default:
                                row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
                                r.add(row.getCell(c).getStringCellValue());
                        }
    				}
    			}  
    			ls.add(r);  
    		}  
    	}  
    	
    	return ls;  
    }  
    
    /** 
     * 读取Excel文件，支持2000与2007格式 
     * @param fileName 
     * @return 
     * @throws Exception  
     */  
    public static Workbook loadWorkbook(String fileName) throws Exception {
        if (null == fileName)  
            return null;  
  
        Workbook wb = null;
        if (fileName.toLowerCase().endsWith(".xls")) {  
        	InputStream in = new FileInputStream(fileName);  
            try {  
                POIFSFileSystem fs = new POIFSFileSystem(in);
                wb = new HSSFWorkbook(fs);
                in.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }finally{
            	in.close();  
            }
  
        } else if (fileName.toLowerCase().endsWith(".xlsx")) {  
        	InputStream in = new FileInputStream(fileName);  
            try {  
                wb = new XSSFWorkbook(in);  
                in.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }finally{
            	in.close();  
            }  
        }else{  
            throw new Exception("不是一个有效的Excel文件");  
        }  
        return wb;  
    }  
    /** 
     * 读取Excel文件，支持2000与2007格式 
     * @return
     * @throws Exception  
     */  
    public static Workbook loadWorkbook(InputStream in, String excelType) throws Exception {
    	Workbook wb = null;
    	if (excelType.endsWith(".xls")) {  
    		try {  
    			POIFSFileSystem fs = new POIFSFileSystem(in);
    			wb = new HSSFWorkbook(fs);
    			in.close();  
    		} catch (Exception e) { 
    			throw e;  
    		}finally{
    			in.close();  
    		}
    		
    	} else if (excelType.endsWith(".xlsx")) {  
    		try {  
    			wb = new XSSFWorkbook(in);  
    			in.close();  
    		} catch (Exception e) {  
    			throw e;   
    		}finally{
    			in.close();  
    		}  
    	}else{  
    		throw new Exception("不是一个有效的Excel文件");  
    	}  
    	return wb;  
    }  
      
    public static void writeToExcel(Workbook wb, OutputStream out){
        try {  
            wb.write(out);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    public static enum ExcelType{  
        xls, xlsx;  
    }  
    public static Workbook listToWorkbook(List<?> rows, ExcelType type){
        Workbook wb = null;
        if(ExcelType.xls.equals(type)){  
            wb = new HSSFWorkbook();
        }else if(ExcelType.xlsx.equals(type)){  
            wb = new XSSFWorkbook();  
        }else{  
            return null;  
        }  
          
        Sheet sh = wb.createSheet();
        if(null!=rows){  
            for(int i=0; i<rows.size(); i++){  
                Object obj = rows.get(i);  
                Row row = sh.createRow(i);
                  
                if (obj instanceof Collection) {  
                    Collection<?> r = (Collection<?>) obj;  
                    Iterator<?> it = r.iterator();  
                    int j = 0;  
                    while(it.hasNext()){  
                        Cell cell = row.createCell(j++);
                        cell.setCellValue(String.valueOf(it.next()));  
                    }  
                }else if(obj instanceof Object[]){  
                    Object[] r = (Object[]) obj;  
                    for(int j=0; j<r.length; j++){  
                        Cell cell = row.createCell(j);
                        cell.setCellValue(String.valueOf(r[j]));  
                    }  
                }else{  
                    Cell cell = row.createCell(0);
                    cell.setCellValue(String.valueOf(obj));  
                }  
            }  
        }  
          
        return wb;  
    }
    
    
    public static Workbook listToWorkbook(List<String> header, List<?> rows, ExcelType type){
    	 Workbook wb = null;
         if(ExcelType.xls.equals(type)){  
             wb = new HSSFWorkbook();
         }else if(ExcelType.xlsx.equals(type)){  
             wb = new XSSFWorkbook();  
         }else{  
             return null;  
         }  
         int startRow = 0;
         Sheet sh = wb.createSheet();
         if(null!=header){  
        	 Row row = sh.createRow(startRow++);
        	 CellStyle style = wb.createCellStyle();
        	 style.setFillBackgroundColor(HSSFColor.BLUE.index);
             for(int i=0; i<header.size(); i++){  
                 String obj = header.get(i);  
                 Cell cell = row.createCell(i);
                 cell.setCellStyle(style);
                 cell.setCellValue(String.valueOf(obj));  
             }  
         }  
         if(null!=rows){  
             for(int i=startRow; i<rows.size()+startRow; i++){  
                 Object obj = rows.get(i-startRow);  
                 Row row = sh.createRow(i);
                   
                 if (obj instanceof Collection) {  
                     Collection<?> r = (Collection<?>) obj;  
                     Iterator<?> it = r.iterator();  
                     int j = 0;  
                     while(it.hasNext()){  
                         Cell cell = row.createCell(j++);
                         cell.setCellValue(String.valueOf(it.next()));  
                     }  
                 }else if(obj instanceof Object[]){  
                     Object[] r = (Object[]) obj;  
                     for(int j=0; j<r.length; j++){  
                         Cell cell = row.createCell(j);
                         cell.setCellValue(String.valueOf(r[j]));  
                     }  
                 }else{  
                     Cell cell = row.createCell(0);
                     cell.setCellValue(String.valueOf(obj));  
                 }  
             }  
         }  
           
         return wb;  
    }  
    
    public static void output(Workbook wb, String fileName) {
		FileOutputStream fo = null;
        try {
			File f = new File(fileName);
			if(!f.exists()){
				f.createNewFile();
			}
			fo = new FileOutputStream(f);
			ExcelUtil.writeToExcel(wb, fo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fo);
		}
	}
    public static HSSFCellStyle setSheetStyle( HSSFWorkbook wb, HSSFSheet sheet){
        sheet.setDefaultColumnWidth(20);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        return style;
    }

    public static HSSFCellStyle setBackgroundSheetStyle(HSSFWorkbook wb, HSSFSheet sheet, Short bg){

        HSSFCellStyle style = setSheetStyle( wb, sheet);
        if (bg != null){
            style .setFillPattern(HSSFCellStyle.FINE_DOTS);
            style.setFillForegroundColor(HSSFColor.BLACK.index);
            style.setFillBackgroundColor(bg);
        }
        return style;
    }

    //合并单元格边框消失问题
    public void setBorderStyle(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){
        RegionUtil.setBorderBottom(border, region, sheet, wb);
        RegionUtil.setBorderLeft(border, region, sheet, wb);
        RegionUtil.setBorderRight(border, region, sheet, wb);
        RegionUtil.setBorderTop(border, region, sheet, wb);
    }
      
    public static void main(String[] args) {  
        List<Object> rows = new ArrayList<Object>();  
          
        List<String> header = new ArrayList<String>();  
        header.add("1");  
        header.add("1");  
        header.add("1");  
        header.add("1");  
        header.add("1");  
        header.add("1");  
        header.add("1");  
        header.add("1");  
        
        List<Object> row = new ArrayList<Object>();  
        row.add("字符串");  
        row.add(11);  
        row.add(new Date());  
        row.add(1.0);  
        rows.add(((Object)row));  
          
        rows.add("中文");  
        rows.add(new Date());  
          
        listToWorkbook(rows, ExcelType.xls);  
        Workbook wb = listToWorkbook(header,rows, ExcelType.xlsx);
        
        FileOutputStream fo = null;
        try {
			File f = new File("D:\\laoyang.xlsx");
			if(!f.exists()){
				f.createNewFile();
			}
			fo = new FileOutputStream(f);
			writeToExcel(wb, fo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fo);
		}


		//-----readToList example
        MultipartFile filedata= (MultipartFile) new Object();//demo..
        List<Object> list=new ArrayList<Object>();
        if(filedata!=null){
            String originalFilename = filedata.getOriginalFilename();
            String excelType=originalFilename.substring(originalFilename.lastIndexOf("."));
            //2.解析Excel
            try {
                list=ExcelUtil.readToList(filedata.getInputStream(),excelType, 0, 1);
            } catch (Exception e) {
                //文件解析异常
            }
        }

        for (Object object:list) {
            List<String> excelContent = (List<String>) object;
        }
    }  
}  