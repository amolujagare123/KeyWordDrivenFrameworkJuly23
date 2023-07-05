package Base;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static Base.OpenBrowser.openBrowser;

public class KeywordEngine {

    WebDriver driver;
    String filePath="Keywords/Keywords.xlsx";
    public void startEngine(String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int i=1;i<rowCount;i++)
        {
            XSSFRow row = sheet.getRow(i);

            String locatorColVal = row.getCell(1).toString().trim();
            String locatorName ="";
            String locatorValue="";

            if (!locatorColVal.equalsIgnoreCase("NA"))
            {
                locatorName =locatorColVal.split("=")[0];
                locatorValue =locatorColVal.split("=")[1];
            }

            String action = row.getCell(2).toString().trim();
            String value = row.getCell(3).toString().trim();

            switch (action)
            {
                case "open browser" : driver = openBrowser(value);
                                      break;
                case "open url" : driver.get(value);
                                  break;
                case "close" : driver.close();break;
                case "Verify Page Title" :
                    Assert.assertEquals(driver.getTitle(),value
                            ,"wrong page opened");break;
                case "Verify URL" :
                    Assert.assertEquals(driver.getCurrentUrl(),value
                            ,"wrong page opened");break;
            }

            switch (locatorName)
            {
                case "id" : if(action.equalsIgnoreCase("click"))
                                driver.findElement(By.id(locatorValue)).click();
                            else if(action.equalsIgnoreCase("type"))
                                driver.findElement(By.id(locatorValue)).sendKeys(value);
                            break;
                case "name" : if(action.equalsIgnoreCase("click"))
                                driver.findElement(By.name(locatorValue)).click();
                            else if(action.equalsIgnoreCase("type"))
                                driver.findElement(By.name(locatorValue)).sendKeys(value);
                              break;
                case "linkText" : if(action.equalsIgnoreCase("click"))
                    driver.findElement(By.linkText(locatorValue)).click();
            }
        }


    }
}
