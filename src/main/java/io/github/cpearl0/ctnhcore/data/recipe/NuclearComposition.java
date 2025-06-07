package io.github.cpearl0.ctnhcore.data.recipe;

import java.util.List;

public class NuclearComposition {
    public String name;
    public int complexity;
    public List<CompositionEntry> composition;

    public static class CompositionEntry {
        public String key;
        public int value;

        public CompositionEntry(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static final List<NuclearComposition> COMPOSITIONS = List.of(
            new NuclearComposition("neptunium", 115, List.of(
                    new CompositionEntry("neptunium_235", 2000),
                    new CompositionEntry("neptunium_237", 5000),
                    new CompositionEntry("neptunium_239", 3000)
            )),
            new NuclearComposition("plutonium", 120, List.of(
                    new CompositionEntry("plutonium_244", 9890),
                    new CompositionEntry("plutonium_245", 100),
                    new CompositionEntry("plutonium_240", 10)
            )),
            new NuclearComposition("americium", 135, List.of(
                    new CompositionEntry("americium_241", 2000),
                    new CompositionEntry("americium_243", 5000),
                    new CompositionEntry("americium_245", 3000)
            )),
            new NuclearComposition("curium", 145, List.of(
                    new CompositionEntry("curium_250", 9890),
                    new CompositionEntry("curium_247", 100),
                    new CompositionEntry("curium_246", 10)
            )),
            new NuclearComposition("berkelium", 150, List.of(
                    new CompositionEntry("berkelium_247", 2000),
                    new CompositionEntry("berkelium_249", 5000),
                    new CompositionEntry("berkelium_251", 3000)
            )),
            new NuclearComposition("californium", 160, List.of(
                    new CompositionEntry("californium_252", 9890),
                    new CompositionEntry("californium_253", 100),
                    new CompositionEntry("californium_256", 10)
            )),
            new NuclearComposition("einsteinium", 170, List.of(
                    new CompositionEntry("einsteinium_253", 2000),
                    new CompositionEntry("einsteinium_255", 5000),
                    new CompositionEntry("einsteinium_257", 3000)
            )),
            new NuclearComposition("fermium", 185, List.of(
                    new CompositionEntry("fermium_258", 9890),
                    new CompositionEntry("fermium_259", 100),
                    new CompositionEntry("fermium_262", 10)
            )),
            new NuclearComposition("mendelevium", 200, List.of(
                    new CompositionEntry("mendelevium_259", 2000),
                    new CompositionEntry("mendelevium_261", 5000),
                    new CompositionEntry("mendelevium_263", 3000)
            ))
    );

    public NuclearComposition(String name, int complexity, List<CompositionEntry> composition) {
        this.name = name;
        this.complexity = complexity;
        this.composition = composition;
    }
}
