package cn.fy.cjgl.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class PoiWriteExcel<T> {
	/**
	 * 工作薄名称
	 */
	protected String sheetName;

	/**
	 * 导出的文件名称
	 */
	protected String exportFileName;

	/**
	 * 字段及字段标题，以“{字段名1:标题名1},{字段2:标题2}”形式保存
	 */
	protected String[] headers;

	/**
	 * 需要导出的数据集LIST<字段对象> 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 * javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 */
	protected Collection<T> dataset;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public Collection<T> getDataset() {
		return dataset;
	}

	public void setDataset(Collection<T> dataset) {
		this.dataset = dataset;
	}
	
	/**
	 * 设置参数
	 * @param sheetName
	 * @param exportFileName
	 * @param headers
	 * @param dataset
	 * @return PoiWriteExcel<T>
	 */
	public PoiWriteExcel<T> build(String sheetName, String exportFileName, String[] headers, Collection<T> dataset){
		// 设置标题
		this.setSheetName(sheetName);
		// 设置生成文件路径
		this.setExportFileName(exportFileName);

		this.setHeaders(headers);

		this.setDataset(dataset);
		
		return this;
	}
	
	/**
	 * 导出文件
	 * @return
	 */
	public boolean export() {
		if ("".equals(exportFileName.trim())) {
			return false;
		}
		
		String headTitle;

		OutputStream out = null;

		try {
			out = new FileOutputStream(this.exportFileName);

			// 申明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(this.sheetName);

			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);

			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColorPredefined.SKY_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.CENTER);

			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor(Font.COLOR_RED);
			font.setFontHeightInPoints((short) 12);
			font.setBold(true);

			// 把字体应用到当前的样式
			style.setFont(font);

			// 生成并设置另一个样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
			style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style2.setBorderBottom(BorderStyle.THIN);
			style2.setBorderLeft(BorderStyle.THIN);
			style2.setBorderRight(BorderStyle.THIN);
			style2.setBorderTop(BorderStyle.THIN);
			style2.setAlignment(HorizontalAlignment.CENTER);
			style2.setVerticalAlignment(VerticalAlignment.CENTER);
			// 生成另一个字体
			HSSFFont font2 = workbook.createFont();
			font2.setBold(false);
			// 把字体应用到当前的样式
			style2.setFont(font2);

			// 产生表格标题行
			HSSFRow row = sheet.createRow(0);

			for (int i = 0; i < headers.length; i++) {
				headTitle = this.getTitleFormHead(headers[i]);
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headTitle);
				cell.setCellValue(text);
			}

			// 遍历集合数据，产生数据行
			Iterator<T> it = dataset.iterator();

			int index = 0;

			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = it.next();

				int j = 0;

				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				// 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段
				// Field[] fields = t.getClass().getDeclaredFields();
				// 直接使用数组效率会很高，并且可以控制顺序也可以直接使用通用的QUERYLIST方法

				for (int i = 0; i < headers.length; i++) {
					String fieldName = this.getFieldNameFormHead(headers[i]);
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

					try {
						Class<?> cls = t.getClass();

						Method getMethod = cls.getMethod(getMethodName, new Class[] {});

						Object value = getMethod.invoke(t, new Object[] {});

						String formatType = this.getFormatTypeHead(headers[i]);

						String textValue = null;

						// 数据类型处理
						if (value != null) {
							textValue = value.toString();
						} else {
							textValue = "";
						}

						HSSFCellStyle contextstyle = workbook.createCellStyle();

						HSSFCell contentCell = row.createCell(j);
						HSSFDataFormat df = workbook.createDataFormat(); // 此处设置数据格式

						if (!"".equals(textValue)) {
							if ("0".equals(formatType)) {
								contextstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,#0"));// 数据格式只显示整数
								contentCell.setCellStyle(contextstyle);
								contentCell.setCellValue(Double.parseDouble(textValue));
							} else if ("1".equals(formatType)) {
								contextstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));// 保留两位小数点
								contentCell.setCellStyle(contextstyle);
								contentCell.setCellValue(Double.parseDouble(textValue));
							} else if ("2".equals(formatType)) {
								contextstyle.setDataFormat(df.getFormat("¥#,##0.00"));// 金额保留两位小数点
								textValue = textValue.replace(",", "");

								if (textValue.contains("¥")) {
									textValue = textValue.substring(1, textValue.length());
								}

								contentCell.setCellStyle(contextstyle);
								contentCell.setCellValue(Double.parseDouble(textValue));
							} else {
								contentCell.setCellStyle(contextstyle);
								contentCell.setCellValue(textValue);
							}
						} else {
							contentCell.setCellStyle(contextstyle);
							contentCell.setCellValue(textValue);
						}

						j++;
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

				}
			}

			try {
				workbook.write(out);
				workbook.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 取得字段名称
	 * @param head
	 * @return
	 */
	private String getFieldNameFormHead(String head) {
		if (head.indexOf(":") < 0) {
			return "";
		}
		return head.substring(0, head.indexOf(":"));

	}

	/**
	 * 取得字段标题
	 * @param head
	 * @return
	 */
	private String getTitleFormHead(String head) {
		if (head.indexOf(":") < 0) {
			return head;
		}

		String[] arryType = head.split(":");

		return arryType[1];
	}

	/**
	 * 
	 * @param head
	 * @return 0整数 1保留两位小数 2人民币开头保留两位小数
	 */
	private String getFormatTypeHead(String head) {
		String formatType = "";

		if (head.indexOf(":") < 0) {
			formatType = "3";
		}

		String[] arryType = head.split(":");

		if (arryType.length == 3) {
			if ("0".equals(arryType[2])) {
				formatType = "0";
			} else if ("1".equals(arryType[2])) {
				formatType = "1";
			} else if ("2".equals(arryType[2])) {
				formatType = "2";
			} else {
				formatType = "3";
			}
		} else {
			formatType = "3";
		}

		return formatType;
	}
	
	/**
	 * 下载
	 * @param request
	 * @param response
	 * @param filePath
	 * @param displayName
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath, String displayName) {

		// 设置为下载application/x-download
		response.setContentType("application/x-download");

		// 中文编码转换
		try {
			displayName = new String(displayName.getBytes("GBK"), "iso-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + displayName);
		try {
			java.io.OutputStream os = response.getOutputStream();
			java.io.FileInputStream fis = new java.io.FileInputStream(filePath);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			fis.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
