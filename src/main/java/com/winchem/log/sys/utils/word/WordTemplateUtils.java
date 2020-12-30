package com.winchem.log.sys.utils.word;

import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.sys.enums.WordDataTypeEnum;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: winchem_afersale
 * @description: 模板导出word
 * @author: zhanglb
 * @create: 2020-12-01 15:11
 */
@Slf4j
public class WordTemplateUtils {

    /**
     * 根据模板导出word文件
     *
     * @param templateData<TemplateData>     ReportData对象为数据对象，里面存储Map 数据
     * @param templateName   模板文件路径
     * @param outputFileName 输出文件路径
     */
    public static void WriteDoc(List<TemplateData> templateData, String templateName, String outputFileName) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //1.通过freemarker模板引擎加载文档，并缓存到registry中
            ClassPathResource classPathResource = new ClassPathResource("template/report/"+templateName);
            in = classPathResource.getInputStream();
            IXDocReport report = XDocReportRegistry
                    .getRegistry()
                    .loadReport(in, TemplateEngineKind.Freemarker);
            //2.设置填充字段、填充类以及是否为list。
            IContext context = getReportContext(report, templateData);
            //3.输出文件
            out = new FileOutputStream(new File(outputFileName));
            report.process(context, out);
            log.info("根据模板生成word完成");
        } catch (IOException e) {
            log.warn("文件流获取失败", e);
        } catch (XDocReportException e) {
            log.warn("导出失败", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.warn("文件流关闭失败", e);
            }
        }
    }

    /**
     * 根据模板导出word文件
     *
     * @param templateData<TemplateData>     ReportData对象为数据对象，里面存储Map 数据
     * @param templateName   模板文件路径
     * @param out 输出文件流
     */
    public static void WriteDoc(List<TemplateData> templateData, String templateName, ByteArrayOutputStream out) {
        InputStream in = null;
        try {
            //1.通过freemarker模板引擎加载文档，并缓存到registry中
            ClassPathResource classPathResource = new ClassPathResource("template/report/"+templateName);
            in = classPathResource.getInputStream();
            IXDocReport report = XDocReportRegistry
                    .getRegistry()
                    .loadReport(in, TemplateEngineKind.Freemarker);
            //2.设置填充字段、填充类以及是否为list。
            IContext context = getReportContext(report, templateData);
            //3.输出文件
            report.process(context, out);
        } catch (IOException e) {
            log.warn("文件流获取失败", e);
        } catch (XDocReportException e) {
            log.warn("导出失败", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.warn("文件流关闭失败", e);
            }
        }
    }

    private static IContext getReportContext(IXDocReport report, List<TemplateData> templateDatas) throws XDocReportException, IOException {
        FieldsMetadata fieldsMetadata = report.createFieldsMetadata();
        for (TemplateData templateData: templateDatas) {
            if (Objects.nonNull(templateData.getDataType())) {
                switch (Objects.requireNonNull(templateData.getDataType())) {
                    case LIST:
                        fieldsMetadata.load(templateData.getDateName(), templateData.getClazz(), true);
                        continue;
                    case STRING:
                        fieldsMetadata.load(templateData.getDateName(), String.class);
                        continue;
                    case OBJ:
                        fieldsMetadata.load(templateData.getDateName(), templateData.getClazz());
                        continue;
                    default:
                }
                report.setFieldsMetadata(fieldsMetadata);
            }
        }
        IContext context = report.createContext();
        templateDatas.stream().forEach(date -> {
            context.put(date.getDateName(), date.getData());
        });
        return context;
    }

    public static void ProcessWithTemplate() {

        try {
            //1.通过freemarker模板引擎加载文档，并缓存到registry中
            ClassPathResource classPathResource = new ClassPathResource("template/report/report_template.docx");
            InputStream in = classPathResource.getInputStream();
            IXDocReport report = XDocReportRegistry
                    .getRegistry()
                    .loadReport(in, TemplateEngineKind.Freemarker);

            //2.设置填充字段、填充类以及是否为list。
            FieldsMetadata fieldsMetadata = report.createFieldsMetadata();
            fieldsMetadata.load("employees", Employee.class, true);
//            fieldsMetadata.addFieldAsImage("logo");
            report.setFieldsMetadata(fieldsMetadata);

            //3.模拟填充数据
            List<Employee> employees = new ArrayList<Employee>();
            employees.add(new Employee("张三", "产品", "任务完成"));
            employees.add(new Employee("李四", "开发", "任务完成"));
            /*IImageProvider logo = new FileImageProvider(
                    new File("D:/1.jpg"),
                    true);
            logo.setSize(200f, 100f);*/

            //4.匹配填充字段和填充数据，进行填充
            IContext context = report.createContext();
            context.put("employees", employees);
            OutputStream out = new FileOutputStream(
                    new File("D:/test-out.docx"));
            report.process(context, out);
            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<TemplateData> list = new ArrayList<TemplateData>();
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee("张三", "产品", "任务完成"));
        employees.add(new Employee("李四", "开发", "任务完成"));
        TemplateData templateData = new TemplateData();
        templateData.setDateName("employees");
        templateData.setClazz(Employee.class);
        templateData.setDataType(WordDataTypeEnum.LIST);
        templateData.setData(employees);
        list.add(templateData);

        TemplateData templateData1 = new TemplateData();
        templateData1.setDateName("customer_name");
        templateData1.setData("深圳市奇乐餐饮管理有限公司侨城坊分公司-野湖南侨城坊九方荟店");
        list.add(templateData1);

        TemplateData templateData2 = new TemplateData();
        templateData2.setDateName("service_date");
        templateData2.setData(DateUtils.getNowStr());
        list.add(templateData2);

        TemplateData templateData3 = new TemplateData();
        templateData3.setDateName("szyd");
        templateData3.setData("214ppm");
        list.add(templateData3);

        TemplateData templateData4 = new TemplateData();
        templateData4.setDateName("xdjdd");
        templateData4.setData("13D");
        list.add(templateData4);

        TemplateData templateData5 = new TemplateData();
        templateData5.setDateName("pj");
        templateData5.setData("骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省骚的粉色fsdfsalkdf附近福建省");
        list.add(templateData5);

        WordTemplateUtils.WriteDoc(list, "report_template_f1.docx", "D:/tttt.docx");

        Word2PdfAsposeUtil.Doc2pdf("D:/tttt.docx", "D:/tttt.pdf");
    }

    private static void test1() {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee("张三", "产品", "任务完成"));
        employees.add(new Employee("李四", "开发", "任务完成"));

        TemplateData templateData = new TemplateData();
        templateData.setDateName("employees");
        templateData.setClazz(Employee.class);
        templateData.setDataType(WordDataTypeEnum.LIST);
        templateData.setData(employees);


        WordTemplateUtils.WriteDoc(new ArrayList<TemplateData>(){{add(templateData);}}, "report_template.docx", "D:/test-out.docx");


        String docPath = "D:/test-out.docx";
        String pdfPath = "D:/fwbg.pdf";
        Word2PdfAsposeUtil.Doc2pdf(docPath,pdfPath);
    }
}
