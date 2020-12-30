package com.winchem.log.sys.utils.word;

import com.winchem.log.sys.enums.WordDataTypeEnum;
import lombok.Data;

/**
 * @program: winchem_afersale
 * @description:
 * @author: zhanglb
 * @create: 2020-12-01 16:05
 */
@Data
public class TemplateData {
    private String dateName;
    private WordDataTypeEnum dataType;
    private Object data;
    private Class<?> clazz;
}
