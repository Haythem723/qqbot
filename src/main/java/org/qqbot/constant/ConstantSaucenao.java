package org.qqbot.constant;

import java.util.HashMap;
import java.util.Map;

public class ConstantSaucenao {
	public static final String URL_BASE = "https://saucenao.com/search.php";
	public static final int DB = 999;
	public static final String KEY = "257501498bb01aebc1c5cd8e659b00a1a8545e8a";
	public static final int OUTPUT_TYPE = 2;
	public static final int NUMRES = 5;
	public static final String PROXY_BASE_URL = "https://pixiv.cat/";
	public static final Map<Integer, String> DBIndex = new HashMap<Integer, String>() {{
		put(8, "Nico Nico Seiga");
		put(9, "Danbooru");
		put(10, "drawr Images");
		put(11, "Nijie Images");
		put(12, "Yande.re");
		put(19, "2D-Market");
		put(20, "MediBang");
		put(21, "Anime");
		put(25, "Gelbooru");
		put(26, "Konachan");
		put(28, "Anime-Pictures.net");
		put(29, "e621.net");
		put(30, "Idol Complex");
		put(31, "bcy.net Illust");
		put(32, "bcy.net Cosplay");
		put(33, "PortalGraphics.net");
		put(34, "deviantArt");
		put(35, "Pawoo.net");
		put(36, "Madokami (Manga)");
		put(37, "MangaDex");
		put(38, "ArtStation");
		put(39, "ArtStation");
		put(40, "FurAffinity");
		put(41, "Twitter");
		put(42, "Furry Network");
	}};

	public static final Map<Integer, String> bandDBIndex = new HashMap<Integer, String>() {{
		put(22, "H-Anime");
		put(18, "H-Misc");
		put(38, "H-Misc");
	}};
}
