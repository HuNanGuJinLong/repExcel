package com.meihao.uploadexcel.util;


import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * excel读写工具类
 *
 * @author sun.kai
 * 2016年8月21日
 */
public class POIUtil {
    private static Logger logger = Logger.getLogger(POIUtil.class);
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * 读入excel文件，解析后返回
     *
     * @param file
     * @throws IOException
     */
    public static List<String[]> readExcel(MultipartFile file, Model model) throws IOException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //工作表的数量
        int numberOfSheets = workbook.getNumberOfSheets();
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        // List<Map<String, String>> maps = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        HashMap<String, String> map = new HashMap<>();

        if (workbook != null) {
            //循环所有的工作表sheet对象
            for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
               /* //额外的异常处理方法
                if (isMergedRegion(sheet, 0, 8)){
                    System.out.println("sheet:"+sheet.getSheetName());
                    exceptionwork(sheet,list);
                    continue;
                }*/

               /* if (isMergedRegion(sheet, 0, 8)) {
                    logger.info("你的表格信息可能出错，请修改后重试:" + sheet.getSheetName());
                 //   modelAndView.addObject();
                   model.addAttribute("ex","你的表格信息可能出错，请修改后重试:" + sheet.getSheetName());
                    continue;
                }


                if (sheet.getRow(0).getCell(1) == null ||
                        sheet.getRow(1).getCell(1) == null ||
                        sheet.getRow(2).getCell(1) == null) {
                    logger.info("你的表格信息可能出错，请修改后重试:" + sheet.getSheetName());
                  //  modelAndView.addObject("rex","你的表格信息可能出错，请修改后重试:" + sheet.getSheetName());
                    model.addAttribute("rex","你的表格信息可能出错，请修改后重试:" + sheet.getSheetName());
                    continue;
                }*/
              /*  try {
                    if (isMergedRegion(sheet, 0, 8)) {
                        // arrayList.add(new Exceptions("你的表格信息可能出错，请确认表格正确:" +sheet.getSheetName()));
                        //model.addAttribute("ex",arrayList);
                        model.addAttribute("ex", "你的表格信息可能出错，请确认表格正确:" + sheet.getSheetName());
                        logger.info("你的表格信息可能出错，请确认表格正确:" + sheet.getSheetName());
                    }
                } catch (Exception e) {
                    model.addAttribute("exq", "你的表格信息出现异常，请确认表格正确:" + sheet.getSheetName());
                    logger.info(e + "你的表格信息可能出错，请确认表格正确:" + sheet.getSheetName());
                }*/

              /*  try {
                    if (sheet.getRow(2).getCell(1) == null ||
                            sheet.getRow(2).getCell(4) == null ||
                            sheet.getRow(4).getCell(3) == null) {
                        //  arrayList.add(new Exceptions("你的表格信息可能出错，请确认表格正确:" +sheet.getSheetName()));
                        // model.addAttribute("rex",arrayList);
                        model.addAttribute("rex", "你的表格信息可能出错，请确认表格正确:" + sheet.getSheetName());
                        logger.info("你的表格信息可能出错，请确认表格正确:" + sheet.getSheetName());
                    }
                } catch (Exception e) {
                    model.addAttribute("exw", "你的表格信息出现异常，请确认表格正确:" + sheet.getSheetName());
                    logger.info(e + "你的表格信息可能出错，请确认表格正确:" + sheet.getSheetName());
                }*/
               /* try {
                    for (int rowNum = firstRowNum ; rowNum <= lastRowNum; rowNum++){
                        if (sheet.getRow(rowNum).getPhysicalNumberOfCells()>9){
                            logger.info("你的表格信息可能出错，请修改后重试:" + sheet.getSheetName());
                            continue;
                        }
                    }
                }catch (Exceptions e){
                    logger.info(e);
                }*/

                //循环所有行
                try {
                    for (int rowNum = firstRowNum + 5; rowNum <= lastRowNum; rowNum++) {
                        //获得当前行
                        Row row = sheet.getRow(rowNum);//5
                      /*  if (row == null) {
                            logger.info("你的表格信息可能没有填写完整，或者格式出错，请修改后重试:" + sheet.getSheetName());
                            model.addAttribute("qex","你的表格信息可能出错，请修改后重试:" + sheet.getSheetName());
                            continue;
                        }*/

                        //获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum() + 2;//2
                        //获得当前行的列数
                        int lastCellNum = row.getPhysicalNumberOfCells() - 1;//6
                        String[] cells = new String[7];
                        String s = null;
                      /*  //判断指定的单元格是否是合并单元格
                        boolean b = isMergedRegion(sheet, 0, 4);
                        //是的话进行取值
                        if (b) {
                            //获取合并单元格的值
                            s = getMergedRegionValue(sheet, 0, 4);
                            String substring = s.substring(3);
                            cells[9] = substring;
                        }*/
                        //循环当前行，获取表格信息
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            //获取单元格信息
                            if (row.getCell(cellNum)!=null){
                                Cell cell = row.getCell(cellNum);
                                cells[cellNum] = getCellValue(cell);
                            }else {
                                model.addAttribute("aex", "你的学院信息为空，请注意检查:" + sheet.getSheetName());
                                logger.info("你的学院信息为空，请确认信息正确:" + sheet.getSheetName());
                            }
                        }
                        try {
                            //获取指定单元格的值
                            if (sheet.getRow(2).getCell(1) != null) {
                                String substring = null;
                                String cellValue = getCellValue(sheet.getRow(2).getCell(1));
                                if (!"".equals(cellValue) && null != cellValue) {
                                    String s1 = cellValue.substring(3);
                                    substring = s1.substring(0, s1.length() - 2);
                                }
                                cells[0] = substring;
                            } else {
                                model.addAttribute("oex", "你的学院信息为空，请注意检查:" + sheet.getSheetName());
                                logger.info("你的学院信息为空，请确认信息正确:" + sheet.getSheetName());
                            }
                        } catch (Exception e) {
                            logger.info(e);
                        }
                        try {
                            //获取指定单元格的值
                            if (sheet.getRow(2).getCell(3) != null) {
                                String substring = null;
                                String cellValue = getCellValue(sheet.getRow(2).getCell(3));
                                if (!"".equals(cellValue) && null != cellValue) {
                                    substring = cellValue.substring(3);
                                }
                                cells[1] = substring;
                            } else {
                                model.addAttribute("uex", "你的班级信息为空，请注意检查:" + sheet.getSheetName());
                                logger.info("你的班级信息为空，请确认信息正确:" + sheet.getSheetName());
                            }
                        } catch (Exception e) {
                            logger.info(e);
                        }
                        list.add(cells);
                    }
                } catch (Exception e) {
                    arrayList.add(new Exceptions("你的表格信息出现异常，请确认表格正确:" + sheet.getSheetName()));
                    model.addAttribute("qyx", arrayList);
                    logger.info(e + "你的表格信息出现异常，请确认表格正确:" + sheet.getSheetName());

                }
            }
            workbook.close();
        }
        return list;
    }

    /**
     * 处理异常工作表的方法
     *
     * @param sheet
     */
    private static void exceptionwork(Sheet sheet, List<String[]> list) {
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        //从第三行开始循环读取
        for (int rowNum = 3; rowNum <= lastRowNum; rowNum++) {
            String[] cells = new String[15];
            //获得当前行
            Row row = sheet.getRow(rowNum);//2
            //获得当前行的开始列
            int firstCellNum = row.getFirstCellNum();//2 1
            //获得当前行的列数
            int lastCellNum = row.getPhysicalNumberOfCells();//9
            //判断指定的单元格是否是合并单元格
            boolean b = isMergedRegion(sheet, 1, 4);

            //是的话进行取值
            if (true == b) {
                //获取合并单元格的值
                String s = getMergedRegionValue(sheet, 1, 4);
                String substring = s.substring(3);
                cells[9] = substring;
            }
            for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                //获取单元格信息
                Cell cell = row.getCell(cellNum);
                cells[cellNum] = getCellValue(cell);
            }
            if (sheet.getRow(1).getCell(5) != null) {
                String cellValue = getCellValue(sheet.getRow(1).getCell(5)).substring(3);
                cells[10] = cellValue;
            }
            if (sheet.getRow(1).getCell(6) != null) {
                String cellValue = getCellValue(sheet.getRow(1).getCell(6)).substring(4);
                cells[11] = cellValue;
            }
            if (sheet.getRow(1).getCell(7) != null) {
                String cellValue = getCellValue(sheet.getRow(1).getCell(7)).substring(4);
                cells[12] = cellValue;
            }
            if (sheet.getRow(1).getCell(8) != null) {
                String cellValue = getCellValue(sheet.getRow(1).getCell(8)).substring(5);
                cells[13] = cellValue;
            }
            list.add(cells);
        }
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查文件
     *
     * @param file
     * @throws IOException
     */
    public static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file) {
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
            logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(xls)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(xlsx)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {

            logger.info(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}

