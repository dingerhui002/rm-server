package com.bc.rm.server.controller;

import com.bc.rm.server.cons.Constant;
import com.bc.rm.server.entity.BackLog;
import com.bc.rm.server.entity.Epic;
import com.bc.rm.server.service.BackLogService;
import com.bc.rm.server.service.EpicService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/reports")
public class ReportController {

    private static final String FINISH = "0";
    private static final String UNFINISH = "1";

    @Resource
    private EpicService epicService;

    @Resource
    private BackLogService backLogService;

    /**
     * 生成报表(版本v1)
     * @param finishStatusName 结束状态名,用于区分待办事项是否已经结束
     * @return ResponseEntity
     */
    @ApiOperation(value = "生成报表(版本:v1)", notes = "生成报表(版本:v1)")
    @PostMapping(value = "/")
    public ResponseEntity<String> generateReportV1(
            @RequestParam String finishStatusName) {
        ResponseEntity<String> responseEntity;
        try {

            // 创建Excel对象
            XSSFWorkbook workbook = new XSSFWorkbook();

            List<Epic> epicList = epicService.getAllEpicList();
            for (Epic epic : epicList) {
                Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
                paramMap.put("epicId", epic.getId());
                paramMap.put("finishStatusName", finishStatusName);

                // 未完成
                List<BackLog> unFinishedBackLogList = backLogService.getUnFinishedBackLogListByEpicId(paramMap);
                // 创建Sheet对象
                XSSFSheet unFinishedSheet = createSheetAndTitleRowV1(workbook, epic, UNFINISH);

                int unFinishedIndex = 1;
                for (BackLog unFinishedBackLog : unFinishedBackLogList) {
                    fillContentCellV1(unFinishedSheet, unFinishedBackLog, unFinishedIndex);
                    unFinishedIndex++;
                }

                // 已完成
                List<BackLog> finishedBackLogList = backLogService.getFinishedBackLogListByEpicId(paramMap);
                XSSFSheet finishedSheet = createSheetAndTitleRowV1(workbook, epic, FINISH);
                int finishedIndex = 1;
                for (BackLog finishedBackLog : finishedBackLogList) {
                    fillContentCellV1(finishedSheet, finishedBackLog, finishedIndex);
                    finishedIndex++;
                }

                // 创建输出流
                FileOutputStream fileOutputStream = new FileOutputStream("D://1_1111111//a.xls");
                // 将workbook写入流中
                workbook.write(fileOutputStream);
                // 关流
                fileOutputStream.close();
            }


            responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    private XSSFSheet createSheetAndTitleRowV1(XSSFWorkbook workbook, Epic epic, String finishFlag) {
        XSSFSheet sheet;
        if (UNFINISH.equals(finishFlag)) {
            sheet = workbook.createSheet(epic.getTitle() + "(未完成)");
        } else {
            sheet = workbook.createSheet(epic.getTitle() + "(已完成)");
        }

        // 设置每一列的宽度
        int moduleNameWidth = 15;
        int backLogDescWidth = 80;
        int backLogDeadLineWidth = 15;
        int backLogStatusWidth = 15;

        sheet.setColumnWidth(0, moduleNameWidth * 256);
        sheet.setColumnWidth(1, backLogDescWidth * 256);
        sheet.setColumnWidth(2, backLogDeadLineWidth * 256);
        sheet.setColumnWidth(3, backLogStatusWidth * 256);
        // 设置标题行
        XSSFRow titleRow = sheet.createRow(0);

        // 设置标题单元格样式
        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        // 居中
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 标题字体样式
        Font titleFontStyle = workbook.createFont();
        titleFontStyle.setBold(true);
        // 字体
        titleFontStyle.setFontName("宋体");
        // 大小
        titleFontStyle.setFontHeightInPoints((short) 14);

        // 将字体样式添加到单元格样式中
        titleCellStyle.setFont(titleFontStyle);

        XSSFCell titleCell1 = titleRow.createCell(0);
        titleCell1.setCellStyle(titleCellStyle);
        titleCell1.setCellValue("模块");

        XSSFCell titleCell2 = titleRow.createCell(1);
        titleCell2.setCellStyle(titleCellStyle);
        titleCell2.setCellValue("描述");

        XSSFCell titleCell3 = titleRow.createCell(2);
        titleCell3.setCellStyle(titleCellStyle);
        titleCell3.setCellValue("时间");

        XSSFCell titleCell4 = titleRow.createCell(3);
        titleCell4.setCellStyle(titleCellStyle);
        titleCell4.setCellValue("状态");
        return sheet;
    }

    private void fillContentCellV1(XSSFSheet sheet, BackLog backLog, int index) {
        // 内容行
        XSSFRow contentRow = sheet.createRow(index);

        // 模块名
        XSSFCell contentCell1 = contentRow.createCell(0);
        contentCell1.setCellValue(backLog.getModuleName());

        // 待办事项描述
        XSSFCell contentCell2 = contentRow.createCell(1);
        contentCell2.setCellValue(backLog.getTitle());

        // 待办事项预计结束时间
        XSSFCell contentCell3 = contentRow.createCell(2);
        contentCell3.setCellValue(backLog.getDeadLine());

        // 待办事项状态
        XSSFCell contentCell4 = contentRow.createCell(3);
        contentCell4.setCellValue(backLog.getStatusName());
    }
}
