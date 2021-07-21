package org.clever.graaljs.core.graalvm;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.GraalConstant;
import org.clever.graaljs.core.utils.ScriptContextUtils;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.Source;
import org.junit.jupiter.api.Test;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/21 13:20 <br/>
 */
@Slf4j
public class Test01 {

    @Test
    public void t01() {
        Engine engine = Engine.newBuilder()
                .useSystemProperties(true)
                .option("engine.WarnInterpreterOnly", "false")
                .build();
        engine.getInstruments().forEach((key, instrument) -> {
            log.info("{}: id={} | name={} | version={}", key, instrument.getId(), instrument.getName(), instrument.getVersion());
            instrument.getOptions().forEach(optionDescriptor -> {
                log.info("\t DefaultValue={} | name={} | help={}", optionDescriptor.getKey().getDefaultValue(), optionDescriptor.getName(), optionDescriptor.getHelp());
            });
        });
        log.info("---------------------------------------------------------------------------------------------------------------------------");
        engine.getOptions().forEach(optionDescriptor -> {
            log.info("DefaultValue={} | name={} | help={}", optionDescriptor.getKey().getDefaultValue(), optionDescriptor.getName(), optionDescriptor.getHelp());
        });
        log.info("---------------------------------------------------------------------------------------------------------------------------");
        Language language = engine.getLanguages().get(GraalConstant.Js_Language_Id);
        OptionDescriptors optionDescriptors = language.getOptions();
        optionDescriptors.forEach(optionDescriptor -> {
            log.info("DefaultValue={} | name={} | help={}", optionDescriptor.getKey().getDefaultValue(), optionDescriptor.getName(), optionDescriptor.getHelp());
        });
        Context context = ScriptContextUtils.getContextBuilder(engine, null)
                .build();
        Source source = Source.newBuilder(GraalConstant.Js_Language_Id, "1+1", "/t01.js")
                .cached(true)
                .buildLiteral();
        context.eval(source);
    }
}
