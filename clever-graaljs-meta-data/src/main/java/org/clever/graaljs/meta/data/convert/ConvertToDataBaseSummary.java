package org.clever.graaljs.meta.data.convert;

import org.clever.graaljs.meta.data.model.DataBaseSummary;
import schemacrawler.schema.Schema;

/**
 * 作者：lizw <br/>
 * 创建时间：2020-10-01 19:08 <br/>
 */
public class ConvertToDataBaseSummary {
    public static DataBaseSummary convert(Schema schema) {
        DataBaseSummary dataBaseSummary = new DataBaseSummary();
        dataBaseSummary.setSchemaName(schema.getCatalogName());
        return dataBaseSummary;
    }
}
