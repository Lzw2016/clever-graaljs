const log = LoggerFactory.getLogger("logger");

// TODO 传参问题
// const a = Interop.asJDate("2020-08-24 14:48:30")
const a = CommonUtils.now();
const xml = `
        <string>161561313</string>
    `;
log.info("toXml                  --------------->{}", XmlUtils.toXml(a));
log.info("fromXml                --------------->{}", XmlUtils.fromXml("<string>11111</string>"));
log.info("update                 --------------->{}", XmlUtils.update(xml, a));
log.info("update                 --------------->{}", a);
log.info("xmlToJson              --------------->{}", XmlUtils.xmlToJson(xml));
log.info("jsonToXml              --------------->{}", XmlUtils.jsonToXml(XmlUtils.xmlToJson(xml)));
