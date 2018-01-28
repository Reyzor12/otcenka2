package ru.eleron.osa.lris.otcenka.utilities;

import ru.eleron.osa.lris.otcenka.entities.OpenReport;

import java.util.*;

public class ConvertorForUse {

    private static Map<Integer,String> statusMap;

    public static String convertStatusToString(Integer status){
        if(statusMap == null) {
            createStatusMap();
        }
        final String result = statusMap.get(status);
        if (result  != null) return result;
        return "статус не был определен";
    }

    private static void createStatusMap(){
        statusMap = new HashMap<>();
        statusMap.put(OpenReport.NEW_REPORT,"Новый НИОКР");
        statusMap.put(OpenReport.NOT_FILL_REPORT,"В этом месяцы не был заполнен");
        statusMap.put(OpenReport.FILL_REPORT,"Заполнен");
        statusMap.put(OpenReport.REPORT_BACK_WITHOUT_COMMENT, "НИОКР был возвращен без коментариев");
        statusMap.put(OpenReport.REPORT_BACK_WITH_COMMENT, "НИОКР был возвращен с комментарием");
        statusMap.put(OpenReport.REPORT_APPROVED, "НИОКР был одобрен");
    }

    public static List<String> getAllStatusInString(){
        if(statusMap == null){
            createStatusMap();
        }
        return new ArrayList<String>(statusMap.values());
    }
}
