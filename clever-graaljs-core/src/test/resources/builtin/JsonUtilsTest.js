const log = LoggerFactory.getLogger("logger");

const a = {
    aaa: "111",
    bbb: "222",
    ccc: 333,
    ddd: "444",
    eee: 555,
}
const xml = `
    <apps>
        <app>
            <id>1</id>
            <name>Google Maps</name> 
            <virsion>1.0</virsion> 
        </app> 
        <app> 
            <id>2</id> 
            <name>Chrome</name> 
          <version>2.1</version> 
       </app> 
        <app> 
          <id>3</id> 
           <name>Google Play</name> 
          <version>2.3</version> 
        </app> 
    </apps>
    `;
log.info("toJson                  --------------->{}", JsonUtils.toJson(a));
log.info("toJsonP                 --------------->{}", JsonUtils.toJsonP("jsonp", a));
log.info("toMap                   --------------->{}", JsonUtils.toMap(JsonUtils.toJson(a)));
log.info("update                  --------------->{}", JsonUtils.update("{a:1,b:2}", a));
log.info("a                       --------------->{}", a);
log.info("xmlToJson               --------------->{}", JsonUtils.xmlToJson(xml));
log.info("jsonToXml               --------------->{}", JsonUtils.jsonToXml(JsonUtils.xmlToJson(xml)));
