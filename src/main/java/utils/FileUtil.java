package utils;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件类
 */
public class FileUtil {
    /**
     * 获取文件名
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        return fileName;
    }

    /**
     * 创建文件
     * @param filePath  路径
     * @param titleStr  标题
     */
    public static void creatExcel(String filePath,String titleStr) {
        String path = filePath.substring(0,filePath.lastIndexOf("/"));
        System.out.println("文件夹为："+path);
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                System.out.println("dir exists");
            }else {
                System.out.println("the same name file exists, can not create dir");
            }
        }else {
            System.out.println("dir not exists, create it ...");
            dir.mkdir();
        }

        File file = new File(filePath);
        if(!file.exists()) {
            //创建excel工作簿
            HSSFWorkbook workbook=new HSSFWorkbook();
            //创建工作表sheet
            HSSFSheet sheet=workbook.createSheet("result");
            //创建第一行
            HSSFRow row=sheet.createRow(0);
            //创建单元格样式
            CellStyle cellStyle = workbook.createCellStyle();
            Cell cell = null;
            //插入第一行数据的表头
            String[] title = titleStr.split(",");
            for(int i=0;i<title.length;i++){
                cell=row.createCell(i);
                cell.setCellValue(title[i]);
            }
            FileOutputStream stream=null;
            try {
                //将excel写入
                stream = new FileOutputStream(filePath);
                workbook.write(stream);
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(stream!=null){
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 更新文件
     * @param fileName
     * @param sheetName
     * @param colName
     * @param row
     * @param str
     * @throws Exception
     */
    public static void ExcelUpdateByColname(String fileName,String sheetName,String colName,String row,String str) throws Exception {
        int tranrow = Integer.parseInt(row);
        InputStream is = new FileInputStream(fileName);
        Workbook wb =  WorkbookFactory.create(is);
        Sheet sheet = wb.getSheet(sheetName);
        Row titleRow = sheet.getRow(0);
        //sheet页标题栏处理
        Map cellIndexAndCellNameMapping = new HashMap();
        int lastCellNum = titleRow.getLastCellNum();
        for(int i = 0;i<lastCellNum;i++){
            //拿到cell列对象
            Cell cell = titleRow.getCell(i);
            cell.setCellType(CellType.STRING);
            //获取单元格内容
            String cellValue = cell.getStringCellValue();
            cellIndexAndCellNameMapping.put(cellValue,i);
        }
        int trancol = (int) cellIndexAndCellNameMapping.get(colName);
        Row sheetRow = sheet.getRow(tranrow);
        if(sheetRow == null){
            sheetRow = sheet.createRow(tranrow);
        }
        Cell sheetCell = sheetRow.getCell(trancol);
        if(sheetCell == null) {
            sheetCell = sheetRow.createCell(trancol);
        }
        sheetCell.setCellValue(str);
        FileOutputStream os = new FileOutputStream(fileName);
        wb.write(os);
        is.close();
        os.close();
    }

    /**
     * 行列号更新
     * @param fileName
     * @param sheetName
     * @param col
     * @param row
     * @param str
     * @throws Exception
     */
    public void ExcelUpdate(String fileName,String sheetName,String col,String row,String str) throws Exception {
        int tranCol = Integer.parseInt(col);
        int tranRow = Integer.parseInt(row);
        InputStream is = new FileInputStream(fileName);
        Workbook wb =  WorkbookFactory.create(is);
        Sheet sheet = wb.getSheet(sheetName);
        Row sheetRow = sheet.getRow(tranRow);
        if(sheetRow == null){
            sheetRow = sheet.createRow(tranRow);
        }
        Cell sheetCell = sheetRow.getCell(tranCol);
        if(sheetCell == null) {
            sheetCell = sheetRow.createCell(tranCol);
        }
        sheetCell.setCellValue(str);
        FileOutputStream os = new FileOutputStream(fileName);
        wb.write(os);
        is.close();
        os.close();
    }

    /**
     * 将字符换写进文件里
     * @param json
     * @param fileName
     */
    public static void writeIntoText(String json, String fileName) {
        File dirfile = new File(fileName);
        BufferedWriter writer=null;
        if(dirfile.isDirectory()){
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
        }
        try {
            writer = new BufferedWriter(new FileWriter(dirfile));
            writer.write(JsonUtil.getJSONString(JsonUtil.StringTransForjSON(json)));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * 读取文件 json字符串
     * @param fileName
     */
    public static String readFile(String fileName){
         File file =new File(fileName);
         StringBuffer stringBuffer =new StringBuffer();
         if(!file.exists()){
             try {
                 throw new FileNotFoundException("文件查找不到");
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }
         }
        try {
            BufferedReader reader =new BufferedReader(new FileReader(file));
            String s;
            while ((s =reader.readLine())!=null){
                stringBuffer.append(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}
