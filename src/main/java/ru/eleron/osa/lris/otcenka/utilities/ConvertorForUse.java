package ru.eleron.osa.lris.otcenka.utilities;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;

import java.util.*;

@Component
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
        statusMap.put(OpenReport.NOT_FILL_REPORT,"В этом месяцы не был заполнен");
        statusMap.put(OpenReport.FILL_REPORT,"Заполнен");
        statusMap.put(OpenReport.REPORT_BACK_WITHOUT_COMMENT, "НИОКР был возвращен без коментариев");
        statusMap.put(OpenReport.REPORT_BACK_WITH_COMMENT, "НИОКР был возвращен с комментарием");
        statusMap.put(OpenReport.REPORT_APPROVED, "НИОКР был одобрен");
        statusMap.put(OpenReport.CONSIDERED, "НИОКР проверяется");
    }

    public static Integer getStatusId(String status) {
        if(statusMap == null || statusMap.isEmpty()) return -1;
        for(Integer key : statusMap.keySet()) {
            if (statusMap.get(key).equals(status)) return key;
        }
        return -1;
    }

    public static List<String> getAllStatusInString(){
        if(statusMap == null){
            createStatusMap();
        }
        return new ArrayList<String>(statusMap.values());
    }

    /**
     * Method convert number of month to list of strings - name of month with capital letter,
     * without and in padej roditel
     * @param monthNumber - number of month
     * @return - list of names of the month
     * */

    public static List<String> monthName(Integer monthNumber) {
        switch (monthNumber%12) {
            case 1: return Arrays.asList("Январь", "январь", "января","январе");
            case 2: return Arrays.asList("Февраль", "февраль", "февраля","феврале");
            case 3: return Arrays.asList("Март", "март", "марта","марте");
            case 4: return Arrays.asList("Апрель", "апрель", "апреля","апреле");
            case 5: return Arrays.asList("Май", "май", "мая","мае");
            case 6: return Arrays.asList("Июнь", "июнь", "июня","июне");
            case 7: return Arrays.asList("Июль", "июль", "июля","июле");
            case 8: return Arrays.asList("Август", "август", "августа","августе");
            case 9: return Arrays.asList("Сентябрь", "сентабрь", "сентября","сентябре");
            case 10: return Arrays.asList("Октябрь", "октябрь", "октября","октябре");
            case 11: return Arrays.asList("Ноябрь", "ноябрь", "нооября","ноябре");
            case 0: return Arrays.asList("Декабрь", "декабрь", "декабря","декабре");
            default: return Arrays.asList("Неизвестный месяц", "Неизвестный месяц", "Неизвестный месяц", "Неизвестный месяц");
        }
    }
}
