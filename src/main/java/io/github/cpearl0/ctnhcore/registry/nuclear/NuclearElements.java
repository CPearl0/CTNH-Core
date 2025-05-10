package io.github.cpearl0.ctnhcore.registry.nuclear;

import com.gregtechceu.gtceu.api.data.chemical.Element;

public class NuclearElements {
    public static void init() {}
    public static final Element Th233 = create("Thorium-233",90, 143, -1, null,  "Th-233", true);
    public static final Element Pa233 = create("Protactinium-233",91, 142, -1, null,"Pa-233",true);
    public static final Element U233 = create("Uranium-233",92, 141, -1, null,"U-233",true);
    public static final Element U234 = create("Uranium-234",92, 142, -1, null,"U-234",true);
    public static final Element U239 = create("Uranium-239",92, 147, -1, null,"U-239",true);
    public static final Element Np235 = create("Neptunium-235",93, 142, -1, null,"Np-235",true);
    public static final Element Np237 = create("Neptunium-237",93, 144, -1, null,"Np-237",true);
    public static final Element Np239 = create("Neptunium-239",93, 146, -1, null,"Np-239",true);
    public static final Element Pu240 = create("Plutonium-240",94, 146, -1, null,"Pu-240",true);
    public static final Element Pu244 = create("Plutonium-244",94, 150, -1, null,"Pu-244",true);
    public static final Element Pu245 = create("Plutonium-245",94, 151, -1, null,"Pu-245",true);
    public static final Element Am241 = create("Americium-241",95, 146, -1, null,"Am-241",true);
    public static final Element Am243 = create("Americium-243",95, 148, -1, null,"Am-243",true);
    public static final Element Am245 = create("Americium-245",95, 150, -1, null,"Am-245",true);
    public static final Element Cm245 = create("Curium-245",96, 149, -1, null,"Cm-245",true);
    public static final Element Cm246 = create("Curium-246",96, 150, -1, null,"Cm-246",true);
    public static final Element Cm247 = create("Curium-247",96, 151, -1, null,"Cm-247",true);
    public static final Element Cm250 = create("Curium-250",96, 154, -1, null,"Cm-250",true);
    public static final Element Cm251 = create("Curium-251",96, 155, -1, null,"Cm-251",true);
    public static final Element Bk247 = create("Berkelium-247",97, 150, -1, null,"Bk-247",true);
    public static final Element Bk249 = create("Berkelium-249",97, 152, -1, null,"Bk-249",true);
    public static final Element Bk251 = create("Berkelium-251",97, 154, -1, null,"Bk-251",true);
    public static final Element Cf251 = create("Californium-251",98, 153, -1, null,"Cf-251",true);
    public static final Element Cf252 = create("Californium-252",98, 154, -1, null,"Cf-252",true);
    public static final Element Cf253 = create("Californium-253",98, 155, -1, null,"Cf-253",true);
    public static final Element Cf256 = create("Californium-256",98, 158, -1, null,"Cf-256",true);
    public static final Element Cf257 = create("Californium-257",98, 159, -1, null,"Cf-257",true);
    public static final Element Es253 = create("Einsteinium-253",99, 154, -1, null,"Es-253",true);
    public static final Element Es255 = create("Einsteinium-255",99, 156, -1, null,"Es-255",true);
    public static final Element Es257 = create("Einsteinium-257",99, 158, -1, null,"Es-257",true);
    public static final Element Fm257 = create("Fermium-257",100, 157, -1, null,"Fm-257",true);
    public static final Element Fm258 = create("Fermium-258",100, 158, -1, null,"Fm-258",true);
    public static final Element Fm259 = create("Fermium-259",100, 159, -1, null,"Fm-259",true);
    public static final Element Fm262 = create("Fermium-262",100, 162, -1, null,"Fm-262",true);
    public static final Element Fm263 = create("Fermium-263",100, 163, -1, null,"Fm-263",true);
    public static final Element Md259 = create("Mendelevium-259",101, 158, -1, null,"Md-259",true);
    public static final Element Md261 = create("Mendelevium-261",101, 160, -1, null,"Md-261",true);
    public static final Element Md263 = create("Mendelevium-263",101, 162, -1, null,"Md-263",true);
    public static Element create(String name, long protons, long neutrons, long halfLifeSeconds, String decayTo, String symbol, boolean isIsotope) {
        return new Element(protons, neutrons, halfLifeSeconds, decayTo, name, symbol, isIsotope);
    }
}