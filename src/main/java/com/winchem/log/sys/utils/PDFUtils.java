package com.winchem.log.sys.utils;
import java.io.*;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

/**
*@Description:  根据模板导出pdf文件 工具类
*@Param:  * @param null :
*@return:  * @return : null
*@Author: zhanglb
*@date: 2020/11/25 17:55
*/
@Slf4j
public class PDFUtils {

    /**
    *@Description: 利用模板生成pdf
    *@Param:  * @param null :
    *@return:  * @return : null
    *@Author: zhanglb
    *@date: 2020/11/26 9:51
    */
    public static void FillTemplate(OutputStream out, Map<String, String> dataMap) throws IOException, DocumentException {
        // 模板路径
        ClassPathResource classPathResource = new ClassPathResource("template/check_order_report_template.pdf");
        PdfReader reader = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper = null;
        // 读取pdf模板
        reader = new PdfReader(classPathResource.getInputStream());
        bos = new ByteArrayOutputStream();
        stamper = new PdfStamper(reader, bos);
        AcroFields form = stamper.getAcroFields();
        //填充数据
        for (String s : form.getFields().keySet()) {
            form.setField(s, dataMap.get(s));
        }
        // 如果为false那么生成的PDF文件还能编辑，一定要设为true
        stamper.setFormFlattening(true);
        stamper.close();
        Document doc = new Document();
        PdfCopy copy = new PdfCopy(doc, out);
        doc.open();
        PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
        copy.addPage(importPage);
        doc.close();
        out.flush();
        out.close();

    }

}
