package ru.eleron.osa.lris.otcenka.bussiness;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Class for transfer Open Report object into word document for restrict template of document
 *
 * @author reyzor
 * @version 1.0
 * @since 19.02.2018
 * */

@Component
public class MicrosoftReports {

    /**
     * Method find in document key word in TEXT and replace it for new one
     *
     * @param replacer - map of pairs key word - replace value
     * @param document - document to work
     * @return XWPFDocument document with replaced key words
     * */

    public XWPFDocument replaceInText(Map<String,String> replacer, XWPFDocument document) {
        for(XWPFParagraph paragraph : document.getParagraphs()){
            for(XWPFRun run : paragraph.getRuns()){
                String text = run.getText(0);
                if(text != null){
                    for(String key : replacer.keySet()){
                        if(text.contains(key) && replacer.get(key) != null){
                            text = text.replace(key,replacer.get(key));
                        }
                    }
                }
                run.setText(text,0);
            }
        }
        return document;
    }

    /**
     * Method find in document key word in TABLE and replace it for new one
     *
     * @param replacer - map of pairs key word - replace value
     * @param document - document to work
     * @return XWPFDocument document with replaced key words
     * */

    public XWPFDocument replaceInTable(Map<String,String> replacer, XWPFDocument document) {
        for(XWPFTable table : document.getTables()){
            for(XWPFTableRow row : table.getRows()){
                for(XWPFTableCell cell : row.getTableCells()){
                    for(XWPFParagraph paragraph : cell.getParagraphs()){
                        System.out.println(paragraph.getText());
                        for(XWPFRun run : paragraph.getRuns()){
                            String text = run.getText(0);
                            System.out.println(" run text = " + text);
                            if(text != null){
                                for(String key : replacer.keySet()){
                                    if(text.contains(key) && replacer.get(key) != null){
                                        text = text.replace(key,replacer.get(key));
                                    }
                                }
                            }
                            run.setText(text,0);
                        }
                    }
                }
            }
        }
        return document;
    }

    /**
     * Method find in document key word in TEXT and TABLE and replace it for new one
     *
     * @param replacer - map of pairs key word - replace value
     * @param document - document to work
     * @return XWPFDocument document with replaced key words
     * */

    public XWPFDocument replaceInDocument(Map<String,String> replacer, XWPFDocument document) {
        return replaceInText(replacer,replaceInTable(replacer,document));
    }

    /**
     * Method fill data from OpenReportList to XWPFDocument with template in document
     *
     * @param templatePath - path to template docx
     * @param openReportList - list of open reports which will fill the doc
     */

    public XWPFDocument fillDataFromOpenReportList (String templatePath, List<OpenReport> openReportList){
        XWPFDocument document = new XWPFDocument();
        try {
            XWPFDocument temp = new XWPFDocument(OPCPackage.open(templatePath));
            for (OpenReport openReport : openReportList) {
                temp = fillDataFromOpenReport(openReport,temp);
                document = mergeXWPFDocument(document,temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * Method fill data from OpenReport to XWPFDocument with template in document
     *
     * @param document - document to work
     * @param openReport - open report which will fill the doc
     */

    public XWPFDocument fillDataFromOpenReport(OpenReport openReport, XWPFDocument document) {
        Map<String,String> replacer = replacerForOpenReport(openReport);
        return replaceInDocument(replacer,document);
    }

    /**
     * Method create replacer for OpenReport:
     * {$text} - openReport.getText();
     * {$problems} - openReport.getProblems();
     * {$percentagePerMonth} - openReport.getPercentagePerMonth();
     * {$reportYear} - openReport.getReportYear().getYear();
     * {$comment} - openReport.getComment();
     * {$report.shortName} - openReport.getReport().getShortName();
     * {$report.fullName} - openReport.getReport().getFullName();
     * {$report.dateStart} - openReport.getReport().getDateStart();
     * {$report.dateEnd} - openReport.getReport().getDateEnd();
     * {$report.responsible} - openReport.getReport().getResponsible();
     * {$report.performers} - openReport.getReport().getPerformers();
     * {$report.department} - openReport.getReport().getDepartment();
     * {$report.percentagePerYear} - openReport.getReport().getPercentagePerYear();
     * */

    public Map<String, String> replacerForOpenReport (OpenReport openReport) {
        Map<String,String> map = new HashMap<>();
        map.put("{$text}",openReport.getText());
        map.put("{$problems}",openReport.getProblems());
        map.put("{$percentagePerMonth}",openReport.getPercentagePerMonth().toString());
        map.put("{$reportYear}",openReport.getReportYear().getYear().toString());
        map.put("{$comment}",openReport.getComment());
        map.put("{$report.shortName}",openReport.getReport().getShortName());
        map.put("{$report.fullName}",openReport.getReport().getFullName());
        map.put("{$report.dateStart}",openReport.getReport().getDateStart().getYear().toString());
        map.put("{$report.dateEnd}",openReport.getReport().getDateEnd().getYear().toString());
        map.put("{$report.responsible}",openReport.getReport().getResponsible().toString());
        String users = openReport.getReport().getPerformers().toString();
        map.put("{$report.performers}",users.substring(1,users.length()-1));
        map.put("{$report.department}",openReport.getReport().getDepartment().getName());
        map.put("{$report.percentagePerYear}",openReport.getReport().getPercentagePerYear().toString());
        return map;
    }

    /**
     * Method merge two XWPFDocument in new One
     * @param base - base XWPFDocument in which will be added new XWPFParagraph from sub
     * @param sub  - XWPFDocument merged with base
     * @return merged base XWPFDocument
     * */

    public XWPFDocument mergeXWPFDocument(XWPFDocument base, XWPFDocument sub) {
        if (base == null || sub == null) return null;
        List<XWPFParagraph> subParagraphList = sub.getParagraphs();
        for (XWPFParagraph paragraph : subParagraphList) {
            base.setParagraph(paragraph,base.getPosOfParagraph(base.getLastParagraph())+1);
        }
        return base;
    }

    /**
     * Method merge tow XWPFDocument in new one put one of them in particular position in another one
     * @param base - base XWPFDocument in which will be added new XWPFParagraph from sub
     * @param sub - XWPFDocument merged with base
     * @param keyWord - paragraph with key word will be replaced on List of paragraph from sub
     * @return merged base XWPFDocument;
     * */

    public XWPFDocument mergeXWPFDocument(XWPFDocument base, XWPFDocument sub, String keyWord) {
        if (base == null || sub == null || keyWord.isEmpty()) return null;
        Integer index = -1;
        for (XWPFParagraph paragraph : base.getParagraphs()) {
            if (paragraph.getText().contains(keyWord)) {
                index = base.getPosOfParagraph(paragraph);
                break;
            }
        }
        for (XWPFParagraph paragraph : sub.getParagraphs()) {
            base.setParagraph(paragraph,index);
        }
        return base;
    }

    /**
     * Method to save XWPFDocument
     * @param document - XWPFDocument what have to save
     * @param pathToSave - string - path to save
     * */

    public void saveDocument(XWPFDocument document, String pathToSave) {
        try {
            document.write(new FileOutputStream(pathToSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method generate Report on base two templates for document and for description of report
     * and list of report. At the end it save doc in specific place. Key word for replacing $reports
     *
     * @param pathToMainDocTemplate - string - path to main template of document
     * @param pathToReportDescriptionTemplate - string - path to template of report description
     * @param openReportList - list of OpenReport which have to insert in report template
     * @param pathToSave - string - path to save document
     * @param keyWord - string - key word for inserting reports
     * */

    public void GenerateAndSaveReportUseTemplates (String pathToMainDocTemplate, String pathToReportDescriptionTemplate, List<OpenReport> openReportList, String pathToSave, String keyWord) {
        try {
            XWPFDocument mainDocument = new XWPFDocument(OPCPackage.open(pathToMainDocTemplate));
            XWPFDocument reportDocument = fillDataFromOpenReportList(pathToReportDescriptionTemplate,openReportList);
            mainDocument = mergeXWPFDocument(mainDocument,reportDocument,keyWord);
            saveDocument(mainDocument,pathToSave);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public void GenerateAndSaveReportUseTemplates (String pathToMainDocTemplate, String pathToReportDescriptionTemplate, List<OpenReport> openReportList, String pathToSave) {
        GenerateAndSaveReportUseTemplates(pathToMainDocTemplate, pathToReportDescriptionTemplate, openReportList, pathToSave, "$reports");
    }

    /**
     * =====================================================================================================
     * WORK FOR SPECIFIC DOCX TEMPLATE
     * =====================================================================================================
     * */

    /**
     * Method to transfer open report to word doc (save file)
     *
     * @param replacer -  map of pairs key word - replace value
     * @param openReportList - list of OpenReport for which have to create word doc
     * @param pathTemplate - path to template file
     * @param savePath - path to save the doc file
     * */

    public static void fromReportToWord(Map<String,String> replacer, List<OpenReport> openReportList, String pathTemplate, String savePath){
        XWPFDocument document = null;
        try{
            document = new XWPFDocument(OPCPackage.open(pathTemplate));
            for(XWPFParagraph paragraph : document.getParagraphs()){
                for(XWPFRun run : paragraph.getRuns()){
                    String text = run.getText(0);
                    if(text != null){
                        for(String key : replacer.keySet()){
                            if(text.contains(key)){
                                text = text.replace(key,replacer.get(key));
                            }
                        }
                    }
                    run.setText(text,0);
                }
            }
            for(XWPFTable table : document.getTables()){
                for(XWPFTableRow row : table.getRows()){
                    for(XWPFTableCell cell : row.getTableCells()){
                        for(XWPFParagraph paragraph : cell.getParagraphs()){
                            for(XWPFRun run : paragraph.getRuns()){
                                String text = run.getText(0);
                                if(text != null){
                                    for(String key : replacer.keySet()){
                                        if(text.contains(key)){
                                            text = text.replace(key,replacer.get(key));
                                        }
                                    }
                                }
                                run.setText(text,0);
                            }
                        }
                    }
                }
            }
            document.write(new FileOutputStream(savePath));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Method to transfer open report to word doc (save file)
     *
     * @param parameters - base common parameters like name of head of NTO etc.
     * @param openReportList - list of OpenReport for which have to create word doc
     * @param pathSave - path to save a doc
     * @param fileTemplate - path to template file
     * */

    public void fromOpenReportToWordDocument (List<String> parameters, List<OpenReport> openReportList, String pathSave, String fileTemplate) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(new FileInputStream(fileTemplate));
            XWPFParagraph paragraph1Line = document.getLastParagraph();
            XWPFRun run1LineOne = paragraph1Line.createRun();
            paragraph1Line.setAlignment(ParagraphAlignment.RIGHT);
            run1LineOne.setText("Начальнику НТО");

            XWPFParagraph paragraph2Line = document.createParagraph();
            XWPFRun run2LineOne = paragraph2Line.createRun();
            paragraph2Line.setAlignment(ParagraphAlignment.RIGHT);
            run2LineOne.setText(parameters.get(0));

            XWPFParagraph paragraph3Line = document.createParagraph();
            XWPFRun run3LineOne = paragraph3Line.createRun();
            paragraph3Line.setAlignment(ParagraphAlignment.CENTER);
            run3LineOne.setBold(true);
            run3LineOne.setText("СЛУЖЕБНАЯ ЗАПИСКА");

            XWPFParagraph paragraph4Line = document.createParagraph();
            XWPFRun run4LineOne = paragraph4Line.createRun();
            paragraph4Line.setAlignment(ParagraphAlignment.CENTER);
            run4LineOne.setText("О ходе выполнения ОКР в " + parameters.get(1)+ " " + parameters.get(2));

            for(OpenReport openReport : openReportList){
                XWPFParagraph paragraph1Report = document.createParagraph();
                XWPFRun run1Report = paragraph1Report.createRun();
                run1Report.setUnderline(UnderlinePatterns.SINGLE);
                run1Report.setText("СЧ ОКР \"" + openReport.getReport().getFullName() + "\", шифр \"" + openReport.getReport().getShortName() + "\"." );

                XWPFParagraph paragraph2Report = document.createParagraph();
                XWPFRun run2Report = paragraph2Report.createRun();
                run2Report.setText("а) Ведущий по ОКР: " + openReport.getReport().getResponsible());

                XWPFParagraph paragraph3Report = document.createParagraph();
                XWPFRun run3Report = paragraph3Report.createRun();
                run3Report.setText("б) Ход выполнения НИР в соответствии с план-графиком:");

                XWPFParagraph paragraph4Report = document.createParagraph();
                XWPFRun run4Report = paragraph4Report.createRun();
                run4Report.setText("    - Процент выполнения от общего объёма работ (на "
                        + parameters.get(2)
                        + "г.) (09.01 - 29.12) - "
                        + openReport.getReport().getPercentagePerYear()
                        + "%");

                XWPFParagraph paragraph5Report = document.createParagraph();
                XWPFRun run5Report = paragraph5Report.createRun();
                run5Report.setText("    - Процент выполнения за текущий месяц (01." +
                        (Calendar.getInstance().get(Calendar.MONTH)+1) +
                        " - " +
                        (new GregorianCalendar(Calendar.YEAR,Calendar.MONTH,1)).getActualMaximum(Calendar.DAY_OF_MONTH) +
                        "." +
                        (Calendar.getInstance().get(Calendar.MONTH)+1) +
                        ") - " +
                        openReport.getPercentagePerMonth() + "%");

                for(String str : ("        " + openReport.getText()).split("\n")){
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(str);
                }

                XWPFParagraph paragraph6Report = document.createParagraph();
                XWPFRun run6Report = paragraph6Report.createRun();
                run6Report.setText("в) Проблемные вопросы:");

                for(String str : ("        " + openReport.getProblems()).split("\n")){
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(str);
                }
            }

            XWPFTable table = document.createTable(1,2);

            CTTbl tableCT        = table.getCTTbl();
            CTTblPr pr         = tableCT.getTblPr();
            CTTblWidth tblW = pr.getTblW();
            tblW.setW(BigInteger.valueOf(5000));
            tblW.setType(STTblWidth.PCT);
            pr.setTblW(tblW);
            pr.unsetTblBorders();
            tableCT.setTblPr(pr);

            table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(500));

            table.getRow(0).getCell(0).getParagraphs().get(0).createRun().setText("Начальник " + parameters.get(3));
            table.getRow(0).getCell(1).getParagraphs().get(0).createRun().setText("__________" + parameters.get(4));

            FileOutputStream out = new FileOutputStream(pathSave);
            document.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
