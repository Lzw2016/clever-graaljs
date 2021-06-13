package org.clever.graaljs.spring.core.utils.excel;


import com.alibaba.excel.context.AnalysisContext;

/**
 * 处理读取Excel异常
 * <p>
 * 作者： lzw<br/>
 * 创建时间：2019-05-24 15:31 <br/>
 */
public interface ExcelReaderExceptionHand {

    /**
     * 处理读取Excel异常
     * <pre>
     *  context.readRowHolder()                     当前行相关信息
     *  context.readSheetHolder()                   当前页签相关信息
     *  context.readRowHolder().getCellMap()        行数据
     *  context.readRowHolder().getRowIndex() + 1   行号
     * </pre>
     *
     * @param throwable 当前行异常
     * @param context   Excel读取上下文信息
     */
    void exceptionHand(Throwable throwable, AnalysisContext context);
}
