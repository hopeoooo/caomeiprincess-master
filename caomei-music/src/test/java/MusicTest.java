import com.alibaba.fastjson.JSON;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicTest {
    /**
     *  测试方法
     */
    @Test
    public void test() throws Exception {
        Map<String, String> params = new HashMap<>();
//        params.put("s","周杰伦");
//        params.put("offset","1");
//        params.put("limit","10");
//        params.put("type","1");

        params.put("ids","[518895684]");
//        params.put("c","[{\"id\":\"518895684\",\"v\":0}]");
        String url = "https://api.i-meto.com/api/v1/meting?server=netease&type=url&id=406737893&auth=0160b6e612aac8ba90562543df39ca0e17cb793178b2047b24758dd1dfe480cc";
        Connection.Response
//                response = Jsoup.connect("http://music.163.com/api/v3/song/detail/")
                response = Jsoup.connect("http://music.163.com/api/song/detail/")
//                response = Jsoup.connect(" http://music.163.com/api/search/get/")
//                response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Length", "408")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", "_iuqxldmzr_=32; _ntes_nnid=9a70f3201fcbd6cf4edb02ba394a65c5,1531897058172; _ntes_nuid=9a70f3201fcbd6cf4edb02ba394a65c5; __remember_me=true; usertrack=ezq0o1tsBG6FVe1fBHrBAg==; _ga=GA1.2.1958241620.1533805677; UM_distinctid=16555e96742221-0bc5c3f6fc14a2-9393265-1fa400-16555e96744b4f; vjuids=-89d787cd4.16555e96924.0.e0fc8f49344fa; vjlast=1534744685.1534744685.30; __gads=ID=d7c480b9588b599f:T=1534744686:S=ALNI_Mbb9QcmO1jlX3On_eF82v6dwLy7gg; vinfo_n_f_l_n3=d99ca1905f8ab07f.1.0.1534744684876.0.1534744691814; __utmc=94650624; WM_TID=ymHXXAelaYj3mXYLUXlOTa5z2N%2B8Wk%2B6; __utmz=94650624.1535686730.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); playerid=46149715; JSESSIONID-WYYY=vcZkU3tA2do87BcvX3NOhUIoP6X%2F6Y9c%2FNUlRYpgUF1%5CIAoUpqTy%2B%5CiuVQOxJV8%2BvXO5VWIAAHDzEB%2BG0fZbRDjzIAtMtG8Wc%5CwXF8Y6FWu19Ajs0%5CjdGpPk0M6wkyrmrDhqTe7idwY452wHPkR3oEUYR0CSmWh1eCBEk%2F1N1ye8Cprn%3A1535693748773; WM_NI=i%2B%2BfZIkp2EH5%2BKEJYTld29ezN9qBqTP%2B3RQ2KF1J93rVMarCp%2BXjakOk2vKmUa%2FduI3lHsMxer2sp99K4uKDg9Hkd6lEiUpufIvdUvdCam64ZTjaYu8wLE4x1Nb82UVOS2I%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed3d85986ea8cd3c7798f8ba888d45296bdb9afcc6490aaff82ce42bbae989bb42af0fea7c3b92aabf5e587d943a9b0fea6cb3fe9958babdc60b0bcfedae8728ee7bfa3f46094f5fdb3d43a899affabbc39b28a8382f149b4f5988be4618db5fe97ca6797bfe185f843f1b48894d074a7aefe94f333fb95fc96ca50a5e7888ce73eb09ba7b6f37fa697ba85d87ea89daf8eb5409a8ca682dc7ffca984a6b774f392aed9b15f96919dd3cc37e2a3; __utma=94650624.1958241620.1533805677.1535686730.1535693520.2; __utmb=94650624.2.10.1535693520")
                .header("Connection", "keep-alive")
                .header("Host", "music.163.com")
                .header("Origin", "https://music.163.com")
                .header("Referer", "https://music.163.com/")
                .data(params)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .timeout(10000)
                .execute();
        String list = response.body();
        System.out.println(list);

        Map map = (Map) JSON.parse(list);
        List<Map> lists = (List<Map>) map.get("songs");
        System.out.println(lists);
        System.out.println("返回结果值："+response.statusCode());
        System.out.println("返回结果："+list);
    }


}
