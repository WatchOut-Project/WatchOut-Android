
package com.utopia.watchout.helpers;

import android.content.Context;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.IOException;
import java.io.InputStream;

public class ReadExcelHelper {
    private Workbook mWorkbook;
    private Sheet mSheet;

    public ReadExcelHelper(Context context, String file) {
        try {
            InputStream is = context.getResources().getAssets().open(file);
            mWorkbook = Workbook.getWorkbook(is);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSheet = mWorkbook.getSheet(0);
    }

    public String getCell(int col, int row) {
        Cell c = mSheet.getCell(col, row);
        String cVal = c.getContents();
        return cVal;
    }

}
