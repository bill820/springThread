package com.winchem.log.sys.utils.word;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.winchem.log.sys.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.*;

/**
 * @program: freemarker_xdocxreport
 * @description:
 * @author: zhanglb
 * @create: 2020-12-01 11:03
 */
@Slf4j
public class Word2PdfAsposeUtil {


    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = FileUtil.getResourcesFileInputStream("template/license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean Doc2pdf(String inPath, String outPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return false;
        }
        FileOutputStream os = null;
        try {
            StopWatch watch = new StopWatch("pdf转换时间计算");
            watch.start("doc2pdf-pdf转换");
            // 新建一个空白pdf文档
            File file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            doc.save(os, SaveFormat.PDF);
            // EPUB, XPS, SWF 相互转换
            watch.stop();
            // 转化用时
           log.info("pdf转换成功，共耗时："+ watch.prettyPrint());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Doc2pdf.Exception"+ e);
            return false;
        }finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("Doc2pdf.IOException"+ e);
                }
            }
        }
        return true;
    }

    public static boolean Doc2pdf(String inPath, OutputStream out) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return false;
        }
        try {
            StopWatch watch = new StopWatch("pdf转换时间计算");
            watch.start("doc2pdf-pdf转换");
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            doc.save(out, SaveFormat.PDF);
            // EPUB, XPS, SWF 相互转换
            watch.stop();
            // 转化用时
            log.info("pdf转换成功，共耗时："+ watch.prettyPrint());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Doc2pdf.Exception"+ e);
            return false;
        }finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("Doc2pdf.IOException"+ e);
                }
            }
        }
        return true;
    }

    public static boolean Doc2pdf(InputStream in, OutputStream out) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return false;
        }
        try {
            StopWatch watch = new StopWatch("pdf转换时间计算");
            watch.start("doc2pdf-pdf转换");
            // Address是将要被转化的word文档
            Document doc = new Document(in);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            doc.save(out, SaveFormat.PDF);
            // EPUB, XPS, SWF 相互转换
            watch.stop();
            // 转化用时
            log.info("pdf转换成功，共耗时："+ watch.prettyPrint());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Doc2pdf.Exception"+ e);
            return false;
        }finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("Doc2pdf.IOException"+ e);
                }
            }
        }
        return true;
    }
}
