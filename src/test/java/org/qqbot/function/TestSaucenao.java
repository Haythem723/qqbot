package org.qqbot.function;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.junit.jupiter.api.Test;
import org.qqbot.entity.SaucenaoDataItem;
import org.qqbot.entity.SaucenaoHeaderItem;
import org.qqbot.entity.SaucenaoResult;

import java.io.IOException;

public class TestSaucenao {
  @Test
  public void test() {
    SaucenaoResult imageResult = Saucenao.getResult("https://pixiv.cat/88396328-2.jpg");
    SaucenaoHeaderItem header = imageResult.getHeader();
    int index_id = header.getIndex_id();
    SaucenaoDataItem data = imageResult.getData();
    if (!(index_id == 5 || index_id == 21)) {
      data.setDefault(false);
      data.setPixiv(false);
      data.setAniDB_id(false);
    }
    boolean pixiv = data.isPixiv();
    boolean anidb = data.isAniDB();
    boolean q = pixiv || anidb;
    String s = Saucenao.constructSourceURL(imageResult);
  }

  @Test
  public void testJackson() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonParser parser = mapper.createParser("[    {      \"header\": {        \"similarity\": \"74.2\",        \"thumbnail\": \"https:\\/\\/img1.saucenao.com\\/res\\/pixiv\\/6977\\/69772288_p0_master1200.jpg?auth=9zlvIHjv3r8eXzNOpmYnqw&exp=1619550000\",        \"index_id\": 5,        \"index_name\": \"Index #5: Pixiv Images - 69772288_p0_master1200.jpg\",        \"dupes\": 0      },      \"data\": {        \"ext_urls\": [          \"https:\\/\\/www.pixiv.net\\/member_illust.php?mode=medium&illust_id=69772288\"        ],        \"title\": \"焚风起源\",        \"pixiv_id\": 69772288,        \"member_name\": \"Bounty Devil\",        \"member_id\": 10173453      }    },    {      \"header\": {        \"similarity\": \"60.69\",        \"thumbnail\": \"https:\\/\\/img1.saucenao.com\\/res\\/pixiv\\/6977\\/manga\\/69772288_p1.jpg?auth=CXllSG5PCTu335wdZmBBqA&exp=1619550000\",        \"index_id\": 5,        \"index_name\": \"Index #5: Pixiv Images - 69772288_p1.jpg\",        \"dupes\": 0      },      \"data\": {        \"ext_urls\": [          \"https:\\/\\/www.pixiv.net\\/member_illust.php?mode=medium&illust_id=69772288\"        ],        \"title\": \"焚风起源\",        \"pixiv_id\": 69772288,        \"member_name\": \"Bounty Devil\",        \"member_id\": 10173453      }    },    {      \"header\": {        \"similarity\": \"33.16\",        \"thumbnail\": \"https:\\/\\/img3.saucenao.com\\/frames\\/?expires=1619550000&init=aa52f504419aa8e2&data=e6c6c5a03172c253abc4095de40472129bae5b8a00d8e0faed51ce7228178377a10150de605ac001fcfd78870bed21a0c6f204319d14e3c7d89dd91725afdba51cbfe19e47699e84db74c0db7cadcfd24454fca62532e096099ca42f7295074123c090f23d47ce304e46f5ebcb8518c2&auth=039b8b27343d7a1697ae2c5a28b4a699d8a4dd7e\",        \"index_id\": 21,        \"index_name\": \"Index #21: Anime* - 10162-24-423757.jpg (5092)\",        \"dupes\": 0      },      \"data\": {        \"ext_urls\": [          \"https:\\/\\/anidb.net\\/anime\\/10305\"        ],        \"source\": \"Kamigami no Asobi\",        \"anidb_aid\": 10305,        \"part\": \"03\",        \"year\": \"2014-2014\",        \"est_time\": \"00:07:04 \\/ 00:23:42\"      }    }  ]");
    SaucenaoResult[] saucenaoResults = mapper.readValue(parser, SaucenaoResult[].class);
    String s = saucenaoResults.toString();
    System.out.println(s);
  }
}
