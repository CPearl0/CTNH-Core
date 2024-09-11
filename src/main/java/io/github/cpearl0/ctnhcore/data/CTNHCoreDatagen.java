package io.github.cpearl0.ctnhcore.data;

import com.tterrag.registrate.providers.ProviderType;
import io.github.cpearl0.ctnhcore.data.lang.ChineseLangHandler;
import io.github.cpearl0.ctnhcore.data.lang.EnglishLangHandler;
import io.github.cpearl0.ctnhcore.data.lang.RegistrateCNLangProvider;
import io.github.cpearl0.ctnhcore.registry.CTNHRegistration;

public class CTNHCoreDatagen {
    public static final ProviderType<RegistrateCNLangProvider> CNLANG = ProviderType.register("ctnh_cn_lang", (p, e) -> new RegistrateCNLangProvider(p, e.getGenerator().getPackOutput()));

    public static void init() {
        CTNHRegistration.REGISTRATE.addDataGenerator(ProviderType.LANG, EnglishLangHandler::init);
        CTNHRegistration.REGISTRATE.addDataGenerator(CNLANG, ChineseLangHandler::init);
    }
}
