const log = LoggerFactory.getLogger("logger");
const mateDataService = MateDataManage.getDefault();

mateDataService.reload();
const dataBaseSummary = mateDataService.getDataBaseSummary("test-data");
log.info("###dataBaseSummary -> {}", dataBaseSummary);
const tableSchema = mateDataService.getTableSchema("test-data", "tb_merchandise");
log.info("###tableSchema -> {}", tableSchema);







