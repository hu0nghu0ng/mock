package com.example.newmockup.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportConfig {
    private int sheetIndex;
    private int headerIndex;
    private int startRow;
    private Class dataClazz;
    private List<CellConfig> cellImportConfigs;
    public static final ImportConfig customerImport;

    static {
        customerImport = new ImportConfig();

        customerImport.setSheetIndex(0);
        customerImport.setHeaderIndex(0);
        customerImport.setStartRow(2);
        customerImport.setDataClazz(CustomerDTO.class);

        List<CellConfig> customerImportCellConfigs = new ArrayList<>();

        customerImportCellConfigs.add(new CellConfig(0, "customerNumber"));
        customerImportCellConfigs.add(new CellConfig(1, "Tên khách hàng"));

        customerImport.setCellImportConfigs(customerImportCellConfigs);

    }
}
