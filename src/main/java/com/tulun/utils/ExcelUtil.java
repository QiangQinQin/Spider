package com.tulun.utils;

/**
 * @author QiangQin
 * * @date 2021/8/16
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具类
 */
public class ExcelUtil {
    /**
     * 这个inputStream文件可以
     *      来源于 本地文件的流，
     *      也可以来源于 上传上来的文件的流，也就是MultipartFile的流，
     *  使用getInputStream()方法进行获取。
     */
    public static List<List<Object>> getCourseListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = new ArrayList<>();

        //创建excel工作薄
        Workbook workBook = getWorkBook(in, fileName);
        if (workBook == null) {
            throw new Exception("创建Excel为null");
        }

        Sheet sheet= null;
        Row row = null;
        Cell cell = null;

        //获取所有的工作表（即sheet）的的数量
        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
            //获取一个sheet也就是一个工作本
            sheet = workBook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //获取一个sheet有多少Row
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }

                ArrayList <Object> l1 = new ArrayList <>();
                for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) { //遍历每一行的所有单元格
                    cell = row.getCell(k);
                    l1.add(cell);
                }

                list.add(l1);
            }
        }

        workBook.close();

        return list;

    }


    private static Workbook getWorkBook(InputStream in, String fileName) throws Exception {
        Workbook workbook= null;

        /**
         * 然后再读取文件的时候，应根据excel文件的 后缀名 在不同的版本中对应的 解析类 不一样
         * 要对fileName进行后缀的解析
         */
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(in);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(in);
        } else {
            throw new Exception("请上传Excel文件");
        }
        return workbook;
    }


    public static void main(String[] args) throws Exception {
        String path="E:\\tulun\\Spider\\src\\test.xlsx";
        File file = new File(path);
        if(file.exists()){
            List excel = getCourseListByExcel(new FileInputStream(file), path);
            System.out.println(excel);
        }
    }
}