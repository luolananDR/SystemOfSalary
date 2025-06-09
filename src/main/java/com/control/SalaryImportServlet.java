package com.control;
import com.dao.SalaryRecordDao;
import com.model.SalaryRecord;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/SalaryImportServlet")
@MultipartConfig
public class SalaryImportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("salaryFile");
        SalaryRecordDao salaryDAO=new SalaryRecordDao();
        if(filePart==null||filePart.getSize()==0){
            request.setAttribute("msg", "请选择上传的文件！");
            request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
            return;
        }
        try(InputStream inputStream=filePart.getInputStream()){
            //读取内容
            List<SalaryRecord> salaryList=this.readSalaryRecords(inputStream);
            int successCount = 0;
            for(SalaryRecord record:salaryList){
                if (salaryDAO.insert(record)) {
                    successCount++;
                }
            }
            if(successCount==0){
                request.setAttribute("msg", "上传失败！");
                request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "导入失败，服务器异常！");
            request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
        } request.setAttribute("msg", "上传成功！");
        request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
    }
    public  List<SalaryRecord>  readSalaryRecords(InputStream inputStream) throws Exception{
        List<SalaryRecord> list=new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(inputStream);//创建 Workbook 对象
        Sheet sheet=workbook.getSheetAt(0);//获取第一个工作表
        for(int i=1;i<=sheet.getLastRowNum();i++){
            Row row= sheet.getRow(i);
            if(row==null)continue;
            SalaryRecord record = new SalaryRecord();
            record.setStaffCode(getInteger(row.getCell(0)));
            record.setSalaryMonth(Date.valueOf(row.getCell(1).getStringCellValue() + "-01"));//yyyy-MM -> yyyy-MM-01
            record.setBaseSalary(getDecimal(row.getCell(2)));
            record.setPositionAllowance(getDecimal(row.getCell(3)));
            record.setLunchAllowance(getDecimal(row.getCell(4)));
            record.setOvertimePay(getDecimal(row.getCell(5)));
            record.setFullAttendanceBonus(getDecimal(row.getCell(6)));
            record.setSocialInsurance(getDecimal(row.getCell(7)));
            record.setHousingFund(getDecimal(row.getCell(8)));
            record.setPersonalIncomeTax(getDecimal(row.getCell(9)));
            record.setLeaveDeduction(getDecimal(row.getCell(10)));
            BigDecimal actual = record.getBaseSalary()
                    .add(record.getPositionAllowance())
                    .add(record.getLunchAllowance())
                    .add(record.getOvertimePay())
                    .add(record.getFullAttendanceBonus())
                    .subtract(record.getSocialInsurance())
                    .subtract(record.getHousingFund())
                    .subtract(record.getPersonalIncomeTax())
                    .subtract(record.getLeaveDeduction());
            record.setActualSalary(actual);
            list.add(record);
        }
        workbook.close();
        return list;
    }
    private  BigDecimal getDecimal(Cell cell) {
        if (cell == null) return BigDecimal.ZERO;
        return BigDecimal.valueOf(cell.getNumericCellValue());
    }

    private  Integer getInteger(Cell cell) {
        if (cell == null) return null;
        try {
            String cellValue;
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (int) Math.round(cell.getNumericCellValue());
                case STRING:
                    cellValue = cell.getStringCellValue().trim();
                    // 检查是否为空或非数字
                    if (cellValue.isEmpty() || !cellValue.matches("-?\\d+")) {
                        return null;
                    }
                    return Integer.parseInt(cellValue);
                case FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case NUMERIC:
                            return (int) Math.round(cell.getNumericCellValue());
                        case STRING:
                            cellValue = cell.getStringCellValue().trim();
                            if (cellValue.isEmpty() || !cellValue.matches("-?\\d+")) {
                                return null;
                            }
                            return Integer.parseInt(cellValue);
                        default:
                            return null;
                    }
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
