package codenbox.utilities;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviders {

	@DataProvider(name = "LoginData")
	public String[][] getData() {

		String path = ".\\testData\\LoginData.xlsx";
		ExcelReader xlutil = new ExcelReader(path);

		//
		int rowAmount = xlutil.getRowCount("Sheet1");
		int columnAmount = xlutil.getColumnCount("Sheet1");

		String[][] cellsData = new String[rowAmount - 1][columnAmount];

		for (int rowNum = 2; rowNum <= rowAmount; rowNum++) {
			for (int colNum = 0; colNum < columnAmount; colNum++) {
				cellsData[rowNum - 2][colNum] = xlutil.getCellData("Sheet1", colNum, rowNum);
			}
		}

		return cellsData;

	}

	@Test(dataProvider = "LoginData")
	public void Cells(String userName, String column, String result) {
		System.out.println(userName + " " + column + " " + result);
	}

}
