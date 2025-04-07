package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.data.chemical.Element;

public class CTNHElements {
    public static void init() {}
    public static final Element STARMETAL = new Element( 120, 138, -1, null,"starmetal", "St", false);
    public static final Element ADAMANTINE = new Element( 119, 134, -1, null,"adamantine", "Ad", false);
    public static final Element TARANIUM = new Element( 121, 140, -1, null, "taranium","Tn", false);
    public static final Element INFINITY = new Element(114514, 1919810, -1, null, "infinity", "∞", false);
    public static final Element SUPERMANA = new Element(169, 169, -1, null, "super_mana","**Ma**", false);
    public static final Element MANA = new Element( 169, 169, -1, null, "mana","Ma", false);
    public static final Element MANA_MIXED_1 = new Element( 169, 169, -1, null, "mana_mixed_1","????Ma????", false);
    public static final Element MANA_MIXED_2 = new Element(169, 169, -1, null, "mana_mixed_2", "???Ma???", false);
    public static final Element MANA_LP_MIXED = new Element(169, 169, -1, null, "mana_lp_mixed", "??Ma??LP+", false);
    public static final Element MANA_DEMON_MIXED = new Element(169, 169, -1, null, "mana_demon_mixed", "?Ma?LP++", false);
    public static final Element MANA_PLUS2 = new Element(300, 300, -1, null, "mana_plus2", "Ma+", false);
    public static final Element MANA_PLUS3 = new Element(300, 300, -1, null, "mana_plus3", "Ma++++", false);
    public static final Element Antihydrogen= new Element(-1,1,-1,null,"h_bar","H-",false);
    public static final Element Antioxygen= new Element(-1,-1,-1,null,"o_bar","O-",false);
    public static final Element Inf_bar= new Element(-32678,-32678,-1,null,"inf_bar","-∞",false);
}
