package org.clever.graaljs.spring;

import org.clever.graaljs.spring.core.builtin.CookieUtils;
import org.clever.graaljs.spring.core.builtin.HttpRequestUtils;
import org.clever.graaljs.spring.core.builtin.MeterRegistryUtils;
import org.clever.graaljs.spring.core.builtin.SpringContext;
import org.clever.graaljs.spring.core.builtin.constant.ExcelEnum;
import org.clever.graaljs.spring.core.builtin.wrap.ExcelUtils;
import org.clever.graaljs.spring.core.builtin.wrap.ValidatorUtils;
import org.clever.graaljs.spring.mvc.builtin.constant.HttpMethodEnum;
import org.clever.graaljs.spring.mvc.builtin.constant.MediaTypeEnum;
import org.clever.graaljs.spring.mvc.builtin.constant.SortTypeEnum;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:07 <br/>
 */
public class SpringCommonEngineGlobalUtils {
    /**
     * 设置引擎默认的全局对象
     */
    public static void putGlobalObjects(Map<String, Object> contextMap) {
        // 枚举值
        contextMap.put("ExcelTypeEnum", ExcelEnum.ExcelTypeEnum.Instance);
        contextMap.put("CellExtraTypeEnum", ExcelEnum.CellExtraTypeEnum.Instance);
        contextMap.put("CellDataTypeEnum", ExcelEnum.CellDataTypeEnum.Instance);
        contextMap.put("ExcelLocale", ExcelEnum.ExcelLocale.Instance);
        contextMap.put("BuiltinFormats", ExcelEnum.BuiltinFormats.Instance);
        contextMap.put("HorizontalAlignment", ExcelEnum.HorizontalAlignment.Instance);
        contextMap.put("VerticalAlignment", ExcelEnum.VerticalAlignment.Instance);
        contextMap.put("BorderStyle", ExcelEnum.BorderStyle.Instance);
        contextMap.put("IndexedColors", ExcelEnum.IndexedColors.Instance);
        contextMap.put("FillPatternType", ExcelEnum.FillPatternType.Instance);
        contextMap.put("ExcelDataType", ExcelEnum.ExcelDataType.Instance);
        contextMap.put("ExcelUnderline", ExcelEnum.ExcelUnderline.Instance);
        contextMap.put("ExcelTypeOffset", ExcelEnum.ExcelTypeOffset.Instance);
        contextMap.put("ExcelFontCharset", ExcelEnum.ExcelFontCharset.Instance);
        contextMap.put("WriteDirectionEnum", ExcelEnum.WriteDirectionEnum.Instance);
        contextMap.put("SortType", SortTypeEnum.SortType.Instance);
        contextMap.put("HttpMethod", HttpMethodEnum.HttpMethod.Instance);
        contextMap.put("MediaType", MediaTypeEnum.MediaType.Instance);
        // core
        contextMap.put("MeterRegistryUtils", MeterRegistryUtils.Instance);
        contextMap.put("CookieUtils", CookieUtils.Instance);
        contextMap.put("HttpRequestUtils", HttpRequestUtils.Instance);
        contextMap.put("SpringContext", SpringContext.Instance);
        contextMap.put("ValidatorUtils", ValidatorUtils.Instance);
        contextMap.put("ExcelUtils", ExcelUtils.Instance);
    }
}
