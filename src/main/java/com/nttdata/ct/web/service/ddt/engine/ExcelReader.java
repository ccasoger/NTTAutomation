package com.nttdata.ct.web.service.ddt.engine;

import com.nttdata.ct.web.service.constans.Constants;
import com.nttdata.ct.web.service.datamanage.ValidateSessionData;
import com.nttdata.ct.web.service.io.ManageFiles;
import com.nttdata.ct.web.service.util.UtilWeb;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class ExcelReader {

    @Value("${ddt.file:NOT_DEFINED}")
    private String bookPath;

    /**
     * Evalua si el valor enviado se encuentra en el Libro Excel configurado con la data de entrada
     * El formato del valor debe ser el siguiente: @{{NOMBRE_HOJA-NOMBRE_COLUMNA-NUMERO_FILA}}
     * <ul>
     *     <li>
     *         NOMBRE_HOJA: Representa el nombre de la hoja que contiene la data a buscar
     *     </li>
     *     <li>
     *         NOMBRE_COLUMNA: Representa el nombre de la columna de la tabla de datos
     *     </li>
     *     <li>
     *         NUMERO_FILA: Representa al numero de la fila de la tabla de datos
     *     </li>
     * </ul>
     *
     * @param value Valor a evaluar
     * @return el valor encontrado en el libro excel, en caso no exista o no se envie un valor con el formato de busqueda tomara el valor original
     */
    public String check(String value) {
        if (ValidateSessionData.isDDtParameterFormat(value))
            return getCellData(sheetName(value), columnName(value), rowNumber(value));
        return value;
    }

    /**
     * Evalua si el DataTable contiene valores que se encuentra en el Libro Excel configurado con la data de entrada
     * El formato del valor debe ser el siguiente: @{{NOMBRE_HOJA-NOMBRE_COLUMNA-NUMERO_FILA}}
     * <ul>
     *     <li>
     *         NOMBRE_HOJA: Representa el nombre de la hoja que contiene la data a buscar
     *     </li>
     *     <li>
     *         NOMBRE_COLUMNA: Representa el nombre de la columna de la tabla de datos
     *     </li>
     *     <li>
     *         NUMERO_FILA: Representa al numero de la fila de la tabla de datos
     *     </li>
     * </ul>
     *
     * @param dataTable Tabla de datos enviado desde el feature con los valores a evaluar
     *                  La tabla de datos es soportado solo por una fila.
     * @return el valor encontrado en el libro excel, en caso no exista o no se envie un valor con el formato de busqueda tomara el valor original
     */
    public DataTable check(DataTable dataTable) {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);

        StringBuilder headBuilder = new StringBuilder("Arrays.asList(");
        for (String head : dataTable.cells().get(0))
            headBuilder.append("\"").append(head).append("\"").append(",");
        var headerAppended = headBuilder.append(")").toString().replace(",)", ")");
        Object headersObject = shell.evaluate(headerAppended);

        StringBuilder valuesBuilder = new StringBuilder("Arrays.asList(");
        for (String value : dataTable.cells().get(1))
            valuesBuilder.append("\"").append(
                    check(UtilWeb.replaceBlank(value))
            ).append("\"").append(",");

        var valuesAppended = valuesBuilder.append(")").toString().replace(",)", ")");
        Object valuesObject = shell.evaluate(valuesAppended);

        List<List<String>> infoInTheRaw = Arrays.asList(
                (List<String>) headersObject,
                (List<String>) valuesObject);
        return DataTable.create(infoInTheRaw);
    }

    /**
     * Lee el valor especifico de una celda a partir del nombre de la columna y el numero de fila formando un punto carteciano
     *
     * @param sheetName  Nombre de la hoja
     * @param columnName Nombre de la columna
     * @param rowNumber  Numero de fila
     * @return el valor de la celda encontrada
     */
    public String getCellData(String sheetName, String columnName, int rowNumber) {
        Workbook wb = initWorkBook(bookPath);
        Sheet sheet = wb.getSheet(sheetName);   //getting the XSSFSheet object at given index
        var indexColumn = getIndexColumnByName(sheet, columnName);
        return getValueFromCell(sheet, indexColumn, rowNumber);
    }

    /**
     * Inicializa el libro ExcelReader
     *
     * @param file Ruta del archivo excel
     * @return retorn al instacia de la clase Workbook
     */
    private Workbook initWorkBook(String file) {
        Workbook wb;
        try {
            FileInputStream fis = new FileInputStream(file);
            wb = new XSSFWorkbook(fis);
        } catch (IOException e) {
            if (file.equalsIgnoreCase(Constants.NOT_DEFINED))
                throw new IllegalArgumentException(ManageFiles.readAsString("logs/log-ddt-not-file.txt"));
            else throw new IllegalArgumentException(e.getMessage());
        }
        return wb;
    }

    /**
     * Obtiene el indicador de posicion de la columna por el nombre
     *
     * @param sheet      Nombre de la Hoja en el libro excel
     * @param columnName Nombre de la columna
     * @return el indicador de posicion de la columna
     */
    private int getIndexColumnByName(Sheet sheet, String columnName) {
        Row rowColumName = sheet.getRow(0);
        int cellNum = rowColumName.getPhysicalNumberOfCells();
        int indexColum = 0;
        for (int i = 0; i < cellNum; i++) {
            if (rowColumName.getCell(i).toString().equalsIgnoreCase(columnName)) {
                indexColum = i;
                break;
            }
        }
        return indexColum;
    }

    /**
     * Obtiene el valor de una celda
     *
     * @param sheet       Nombre de la Hoja en el libro excel
     * @param indexColumn Indicador de posicion de la columna
     * @param rowNumber   Indicador de posicion de la fila
     * @return retonar el valor de la celda encontrada
     */
    private String getValueFromCell(Sheet sheet, int indexColumn, int rowNumber) {
        Row row = sheet.getRow(rowNumber);
        Cell cell = row.getCell(indexColumn);
        return cell.getStringCellValue();
    }

    private String sheetName(String value) {
        String[] split = StringUtils.split(ValidateSessionData.getValueIntoDDTFormat(value), "-");
        return split[0];
    }

    private String columnName(String value) {
        String[] split = StringUtils.split(ValidateSessionData.getValueIntoDDTFormat(value), "-");
        return split[1];
    }

    private int rowNumber(String value) {
        String[] split = StringUtils.split(ValidateSessionData.getValueIntoDDTFormat(value), "-");
        return Integer.parseInt(split[2]);
    }

}