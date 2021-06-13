package org.clever.graaljs.spring.core.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import org.clever.graaljs.spring.core.utils.excel.dto.ExcelRow;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-05-13 17:44 <br/>
 */
public interface ExcelRowReader<T> {
    /**
     * 处理Excel数据行(用于自定义校验)
     * <pre>
     *  excelRow.addErrorInColumn() 增加列错误
     *  excelRow.addErrorInRow()    增加行错误
     * </pre>
     *
     * @param data     校验通过的数据
     * @param excelRow 数据行对象
     */
    void readRow(T data, ExcelRow<T> excelRow, AnalysisContext context);

    /**
     * 读取结束时调用
     */
    void readEnd(AnalysisContext context);
}
