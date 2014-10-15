package tests;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import statements
public class WriteExcelDemo
{
	public static void main(String[] args)
	{

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		// This data needs to be written (Object[])
		Map<String, String[]> data = new TreeMap<String, String[]>();
		// data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
		data.put("2", new String[] { "1", "Amit", "Shukla" });
		data.put("3", new String[] { "2", "Lokesh", "Gupta" });
		data.put("4", new String[] { "3", "John", "Adwards" });
		data.put("5", new String[] { "4", "Brian", "Schultz" });

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		//int cellnum = 0;
		// Cell cell = null;
		/*for (String key : keyset)
		{
			String[] objArr = data.get(key);
			int rownum = 0;

			for (String obj : objArr)
			{
				if (cellnum == 0)
				{
					sheet.createRow(++rownum).createCell(cellnum).setCellValue(obj);
				}
				else
				{
					sheet.getRow(++rownum).createCell(cellnum).setCellValue(obj);
				}

			}
			++cellnum;
		}*/

		int rownum = 0;
		for (String key : keyset)
		{
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr)
			{
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		
		/*int rownum = 0;
		for (String key : keyset)
		{
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr)
			{
				sheet.createRow(0).createCell(0).setCellValue(prod);
			    sheet.createRow(1).createCell(0).setCellValue(74);
			    sheet.createRow(2).createCell(0).setCellValue(50);
			    sheet.createRow(3).createCell(0).setCellValue(51);
			    sheet.createRow(4).createCell(0).setCellValue(49);
			    sheet.createRow(5).createCell(0).setCellValue(41);
			}
		}*/
		 
		try
		{
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File("c:/test/howtodoinjava_demo1.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
