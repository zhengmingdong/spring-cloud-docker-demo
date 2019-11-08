/* created by chenshi at 2018-05-05 */
package com.zynn.common.core.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtil {

    public static final String FIRST_SHEET_NAME = "excelUtilSheet1";

    public static final String XLS = ".xls";

    public static final String XLSX = ".xlsx";

    public static void writeExcel(List<String[]> list, File file) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1 = workbook.createSheet(FIRST_SHEET_NAME);
        log.info("------------默认创建1页sheet,名称为:{}", FIRST_SHEET_NAME);
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet1.createRow(i + 1);
            log.info("------------sheet1创建第{}行", i + 1);
            String[] strings = list.get(i);
            for (int j = 0; j < strings.length; j++) {
                String cellValue = strings[j];
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(cellValue);
                log.info("----------给单元格第{}行第{}列设值:{}", i, j, cellValue);
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            log.info("----------------------------------------excel写入完毕!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取excel文件
     *
     * @param file
     * @return
     */
    public static List<String[]> readExcel(File file) {
        List<String[]> list = new ArrayList<>();
        //文件路径
        String path = file.getPath();
        log.info("--------当前文件路径:{}", path);
        //1,获取workBook对象
        Workbook workbook = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            if (path.endsWith(XLS)) {
                workbook = new HSSFWorkbook(fis);
            } else if (path.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(fis);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("-------------成功获取到workBook对象");
        for (int number = 0; number < workbook.getNumberOfSheets(); number++) {
            //获取当前sheet
            Sheet sheet = workbook.getSheetAt(number);
            if (sheet == null) {
                continue;
            }
            log.info("-----------获取到当前sheet对象,number={}", number);
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            short firstCellNum = firstRow.getFirstCellNum();
            short lastCellNum = firstRow.getLastCellNum();
            log.info("--------当前sheet开始index:{},结束index:{},第1行开始index:{},第1行结束index:{}",
                    firstRowNum, lastRowNum, firstCellNum, lastCellNum);
            for (int i = firstRowNum + 1; i < lastRowNum + 1; i++) {
                log.info("------跳过第1行,从第2行开始读取...");
                Row row = sheet.getRow(i);
                ArrayList<Object> rowStrings = Lists.newArrayList();
                log.info("--------按照第1行的格式逐行读取指定单元格内容...");
                for (int j = firstCellNum; j < lastCellNum + 1; j++) {
                    Cell cell = row.getCell(j);
                    String stringCellValue = getCellValue(cell);
                    rowStrings.add(stringCellValue);
                    log.info("-------第{}行第{}列单元格的值:{}", i, j, stringCellValue);
                }
                rowStrings.add(sheet.getSheetName());
                list.add(rowStrings.toArray(new String[rowStrings.size()]));
            }
        }
        log.info("---------------------------------excel读取完毕!");
        return list;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
