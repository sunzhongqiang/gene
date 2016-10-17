package com.mmk.tool;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;


public class ExcelExportTool {

	

	/**
	 * 对excel进行调整样式
	 * @param wb
	 * @return
	 */
	public static CellStyle styleOne(HSSFWorkbook wb) {
		// 生成单元格样式
		CellStyle cellStyle1 = wb.createCellStyle();
		// 设置单元格样式1
		cellStyle1.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);


		// 生成字体1
		Font font1 = wb.createFont();
		cellStyle1.setFont(font1);

		return cellStyle1;
	}

	/**
	 * 对excel进行调整样式
	 * @param wb
	 * @return
	 */
	public static CellStyle styleTwo(HSSFWorkbook wb) {
		// 设置单元格样式2
		CellStyle cellStyle2 = wb.createCellStyle();
		cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle2.setHidden(true);

		// 设置字体2
		Font font2 = wb.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle2.setFont(font2);
		return cellStyle2;
	}

	/**
	 * 将数据变成Excel
	 * 
	 * @param name
	 *            文件名
	 * @param title
	 *            数据标题 {key:titleName}
	 * @param data
	 *            数据内容[{key:data}]
	 * @return excel
	 */
	public static HSSFWorkbook data2Excel(String name, LinkedHashMap<String, String> title,List<Map<String, Object>> data) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(name);
		int cellNum = 0;
		int rowNum = 0;
		HSSFRow row = sheet.createRow(rowNum++);
		for (String key : title.keySet()) {
			row.createCell(cellNum).setCellValue(title.get(key));
			cellNum++;
		}
		
		
		
		for (Map<String,Object> item : data) {
			row = sheet.createRow(rowNum++);
			cellNum = 0;
			for (String key : title.keySet()) {
				row.createCell(cellNum++).setCellValue(MapUtils.getString(item, key,""));
			}
		}
		return wb;
	}

}
