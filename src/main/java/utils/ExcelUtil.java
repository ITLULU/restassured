package utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

public class ExcelUtil {
    public static Logger logger=Logger.getLogger(ExcelUtil.class);

    /**
     * 读取Excel文件
     * @param filePath
     * @param sheetName
     * @return
     * @throws EncryptedDocumentException
     * @throws IOException
     */
    public static List<Map<String ,String>> readExcel(String filePath, String sheetName) throws EncryptedDocumentException, IOException {
        List<Map<String, String>> list = new LinkedList<Map<String,String>>();
        //列名称
        List<String>listname=new LinkedList<String>();

        File file=new File(filePath);
        if(!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        InputStream ins=new FileInputStream(new File(filePath));
        Workbook workbook= WorkbookFactory.create(ins);
        Sheet sheet=workbook.getSheet(sheetName);
        //拿到行号
        int rownum=sheet.getLastRowNum();
        Row row=sheet.getRow(0);
        int colnum=row.getLastCellNum();
        logger.info("总列数"+colnum);
        for(int i=0;i<colnum;i++) {
            Cell cell=row.getCell(i);
            listname.add(i, getCellFormatValue(cell));
        }
        logger.info("Excel列标题名称"+listname.toString());
        for(int i=1;i<=rownum;i++) {
            Row lrow=sheet.getRow(i);
            Map<String,String>map=new LinkedHashMap<String, String>();
            for(int j=0;j<colnum;j++) {
                Cell ccell=lrow.getCell(j);
                map.put(listname.get(j), getCellFormatValue(ccell));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 取得列对于值
     * 得到
     * @param cell
     * @return
     */
    public static String getCellFormatValue(Cell cell) {
        if(cell!=null&&!StringUtils.isBlank(cell.toString())) {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        }
        return "";
    }

    /**
     * 输出Excel表格数据
     * @param datas
     */
    public static void  printExcelData( List<Map<String ,String>>datas) {
        for(int i=0;i<datas.size();i++) {
            Map<String ,String>map=new LinkedHashMap<String, String>();
            map=datas.get(i);
            Iterator<?> it=map.entrySet().iterator();
            while(it.hasNext()) {
                @SuppressWarnings("rawtypes")
                Map.Entry entry = (Map.Entry)it.next();
                String key=String.valueOf(entry.getKey());
                String value=String.valueOf(entry.getValue());
                logger.info("key:"+key+ "  value:"+value);
            }
        }
    }

    /**
     * 拿到表格标题
     * @param fileName
     * @param sheetName
     * @return
     * @throws EncryptedDocumentException
     * @throws IOException
     */
    public static String[] getTitle(String fileName,String sheetName) throws EncryptedDocumentException, IOException {
        InputStream ins=new FileInputStream(new File(fileName));
        Workbook workbook=WorkbookFactory.create(ins);
        Sheet sheet=workbook.getSheet(sheetName);
        Row titile=sheet.getRow(0);

        int lastColum=titile.getLastCellNum();
        String[]titleList=new String[lastColum];
        for(int i=0;i<lastColum;i++) {
            Cell cell=titile.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value=cell.getStringCellValue();
            value=value.substring(0,value.indexOf("("));
            titleList[i]=value;
        }
        return titleList;
    }

    /**
     * List转Object  便于参数化
     * @param datas
     * @return
     */
    public static Object[][] getExcelData(List<Map<String ,String>>datas){
        int length=datas.size();
        int len=datas.get(0).size();
        logger.info("length:"+length+"len:"+len);
        Object [][] ob=new Object[length][len];
        for(int i=0;i<length;i++) {
            int j=0;
            Map<String ,String>map=datas.get(i);
            for(String key:map.keySet()) {
                ob[i][j++]=map.get(key);
            }
        }
        return ob;
    }

    /**
     * 得到sheet页
     * @param fileName
     * @param sheetName
     * @return
     */
    public static Sheet getSheet(String fileName, String sheetName){
        InputStream ins= null;
        Sheet sheet=null;
        try {
            ins = new FileInputStream(new File(fileName));
            Workbook workbook=WorkbookFactory.create(ins);
            sheet =workbook.getSheet(sheetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

    /**
     * 根据行号  列标题读取指定Excel内容
     * @param fileName
     * @param sheetName    sheet页
     * @param rowloum  行名称
     * @param coloum   列名称
     * @return
     * @throws EncryptedDocumentException
     * @throws IOException
     */
    public static Object[][] ReadExcelTo(String fileName,String sheetName,String  rowloum[],String coloum[]) throws EncryptedDocumentException, IOException {
        Object[][] ob=new Object[rowloum.length][coloum.length];

        String title[]=getTitle(fileName, sheetName);

        Sheet sheet=getSheet(fileName,sheetName);
        Row titleRow=sheet.getRow(0);

        //sheet页标题栏处理
        Map<String,String> cellIndexAndCellNameMapping = new HashMap();
        int lastCellNum = titleRow.getLastCellNum();
        for(int i = 0;i<lastCellNum;i++){
            //拿到cell列对象
            Cell cell = titleRow.getCell(i);
            cell.setCellType(CellType.STRING);
            //获取单元格内容
            String cellValue = cell.getStringCellValue();
            cellValue=cellValue.substring(0,cellValue.indexOf("("));
            cellIndexAndCellNameMapping.put(cellValue,String.valueOf(i));
        }

        for(int i=0;i<rowloum.length;i++) {
            int tranRowindex=Integer.valueOf(rowloum[i]);//行号减1
            Row row=sheet.getRow(tranRowindex);
            for(int j=0;j<coloum.length;j++) {
                String titlename=coloum[j];
                logger.info("titlename:"+titlename);
                logger.info("colNum:"+cellIndexAndCellNameMapping.get(titlename));
                int ColIndex= Integer.parseInt(cellIndexAndCellNameMapping.get(titlename));
                logger.info("ColIndex"+ColIndex);
                Cell cell=row.getCell(ColIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                ob[i][j]=cell.getStringCellValue();
            }

        }
        return ob;
    }

    public static void main(String[] args) throws EncryptedDocumentException, IOException {
        String fileName="src/main/resources/test.xlsx";
        String sheetName="main";

        String row[]= {"1","2","3"};
        String col[]= {"testNo","testdesc","result"};
        Object[][] obj=ReadExcelTo(fileName, sheetName,row,col);
        for(int i=0;i<obj.length;i++) {
            for(int j=0;j<obj[i].length;j++) {
                System.out.println("value:"+obj[i][j]);
            }
        }

    }
}
