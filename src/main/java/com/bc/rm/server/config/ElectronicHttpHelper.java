package com.bc.rm.server.config;

import com.alibaba.fastjson.JSONObject;
import com.bc.rm.server.entity.electronic.ElectronContractApi;
import com.bc.rm.server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 http请求放置参数和header模块
 *
 * @author dd
 * 日期：2019年11月18日10:23:29
 */
@Slf4j
@Configuration
public class ElectronicHttpHelper {
    //测试地址
    private static final String reqInterNme = "https://smlopenapi.esign.cn";
    //存放入redis的值
    private static final String redisKey = "electronContractToken";
    //appId 引用软件id
    private static final String appId = "4438775919";
    //秘钥
    private static final String secret = "fcce7c9eb1a6e2e8f69a2c4e1ae171ff";
    //固定类型
    String grantType = "client_credentials";

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取e签宝平台的权限TOken，存入redis
     */
    public String getToken() throws Exception {
        ElectronContractApi enterprise;
        if (null == redisUtil.get(redisKey)) {
            String url = "/v1/oauth2/access_token";
            String reqUrl = reqInterNme.concat(url).concat("?appId=").concat(appId).concat("&secret=").concat(secret)
                    .concat("&grantType=").concat(grantType);
            String tokenJson = HttpHelper.httpGet(reqUrl);
            log.info("返回的token：" + tokenJson);
            // parse status from json
            enterprise = JSONObject.parseObject(tokenJson, ElectronContractApi.class);
            redisUtil.set(redisKey, enterprise, 7190);
        } else {
            enterprise = redisUtil.get(redisKey, ElectronContractApi.class);
        }
        Object token;
        token = enterprise.getData().get("token");
        String tokenStr = (String) token;
        return tokenStr;
    }

    /**
     * get方法
     *
     * @param url 申请地址
     * @params params传入的参数
     */
    public String httpGet(String url) throws Exception {
        //放置header
        HttpHead reqHeader = new HttpHead();
        String tokenStr = getToken();
        String tokenJson = "";
        if (!StringUtils.isEmpty(tokenStr)) {
            reqHeader.setHeader("X-Tsign-Open-App-Id", appId);
            reqHeader.setHeader("X-Tsign-Open-Token", tokenStr);
            reqHeader.setHeader("Content-Type", "application/json");
            String reqUri = reqInterNme.concat(url);
            tokenJson = HttpHelper.httpGet(reqUri, reqHeader.getAllHeaders());
        }
        return tokenJson;
    }

    /**
     * post方法
     *
     * @param url 申请地址
     * @params JSONObject 传递的json
     * @params reqHeader 是否往header放置数据
     * @params splice 是否拼接默认的地址  默认true。
     */
    public ElectronContractApi httpPost(String url, JSONObject json, HttpHead reqHeader, Boolean splice) throws Exception {
        //放置header
        if (null == reqHeader) {
            reqHeader = new HttpHead();
        }
        String reqUri = url;
        if (splice) {
            reqUri = reqInterNme.concat(url);
        }
        String token = getToken();
        String tokenJson = "";
        String jsonStr = json.toString();
        if (!StringUtils.isEmpty(token)) {
            reqHeader.setHeader("X-Tsign-Open-App-Id", appId);
            log.info("第三方api上传的header值X-Tsign-Open-App-Id：" + appId);
            reqHeader.setHeader("X-Tsign-Open-Token", token);
            log.info("第三方api上传的header值X-Tsign-Open-Token：" + token);
            reqHeader.setHeader("Content-Type", "application/json");

            tokenJson = HttpHelper.httpPost(reqUri, reqHeader.getAllHeaders(), jsonStr);
            log.info("第三方api返回的参数" + tokenJson);
        }
        if (splice) {
            return JSONObject.parseObject(tokenJson, ElectronContractApi.class);
        } else {
            return null;
        }
    }


    /**
     * post方法  主要用于文件传输
     *
     * @param url 申请地址
     * @params bufferFile 上传文件的二进制字节流
     * @params reqHeader 是否往header放置数据
     * @params splice 是否拼接默认的地址  默认true。
     */
    public ElectronContractApi httpPutFile(String url, HttpHead reqHeader, byte[] filebytes, Boolean splice) throws Exception {
        //放置header
        if (null == reqHeader) {
            reqHeader = new HttpHead();
        }
        String reqUri = url;
        if (splice) {
            reqUri = reqInterNme.concat(url);
        }
        String token = getToken();
        String tokenJson = "";
        if (!StringUtils.isEmpty(token)) {
            reqHeader.setHeader("X-Tsign-Open-App-Id", appId);
            reqHeader.setHeader("X-Tsign-Open-Token", token);
            // tokenJson = HttpHelper.httpPut(reqUri, reqHeader.getAllHeaders(), filebytes);
            tokenJson = HttpHelper.httpPutByte2(url, filebytes, reqHeader.getAllHeaders());
            log.info("第三方上传文件返回的参数：" + tokenJson);
        }
        if (splice) {
            return JSONObject.parseObject(tokenJson, ElectronContractApi.class);
        } else {
            return null;
        }

    }

    /**
     * put  主要用于put请求
     *
     * @param url 申请地址
     * @params bufferFile 上传文件的二进制字节流
     * @params reqHeader 是否往header放置数据
     * @params splice 是否拼接默认的地址  默认true。
     */
    public ElectronContractApi httpPut(String url, JSONObject json, HttpHead reqHeader, Boolean splice) throws Exception {
        //放置header
        if (null == reqHeader) {
            reqHeader = new HttpHead();
        }
        String reqUri = url;
        if (splice) {
            reqUri = reqInterNme.concat(url);
        }
        String token = getToken();
        String tokenJson = "";
        String jsonStr = null;
        if (!org.springframework.util.StringUtils.isEmpty(json)){
            jsonStr = json.toString();
        }
        if (!StringUtils.isEmpty(token)) {
            reqHeader.setHeader("X-Tsign-Open-App-Id", appId);
            reqHeader.setHeader("X-Tsign-Open-Token", token);
            // tokenJson = HttpHelper.httpPut(reqUri, reqHeader.getAllHeaders(), filebytes);
            tokenJson = HttpHelper.httpPut(reqUri, reqHeader.getAllHeaders(),jsonStr);
            log.info("第三方上传文件返回的参数：" + tokenJson);
        }
        if (splice) {
            return JSONObject.parseObject(tokenJson, ElectronContractApi.class);
        } else {
            return null;
        }

    }

    public static void main(String[] args) {
        String host = "http://businesscard.sinosecu.com.cn";
        String path = "/api/recogliu.do";
        String method = "POST";
        String appcode = "d9e687c9510f4d7c91944c806f62311b";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        String aaa = "/9j/4QCqRXhpZgAATU0AKgAAAAgABQEAAAQAAAABAAADwAEBAAQAAAABAAAC0IdpAAQAAAABAAAA\n" +
                "XgESAAMAAAABAAYAAAEyAAIAAAAUAAAASgAAAAAyMDE5OjEyOjE2IDE1OjMzOjUwAAABkggABAAA\n" +
                "AAEAAAAAAAAAAAACARIAAwAAAAEABgAAATIAAgAAABQAAACOAAAAADIwMTk6MTI6MTYgMTU6MzM6\n" +
                "NTAA/+AAEEpGSUYAAQEAAAEAAQAA/9sAQwAGBAUGBQQGBgUGBwcGCAoQCgoJCQoUDg8MEBcUGBgX\n" +
                "FBYWGh0lHxobIxwWFiAsICMmJykqKRkfLTAtKDAlKCko/9sAQwEHBwcKCAoTCgoTKBoWGigoKCgo\n" +
                "KCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgo/8AAEQgC0APAAwEi\n" +
                "AAIRAQMRAf/EABsAAAMBAQEBAQAAAAAAAAAAAAABAgMEBQYH/8QARBAAAgIBAwMDAwEHAQYDBwQD\n" +
                "AAECEQMEITEFEkEiUWEGMnETBxQjM0KBkbEVJDRScqFic8ElNTZDY4LhFidT0ZKy8f/EABkBAQEB\n" +
                "AQEBAAAAAAAAAAAAAAABAgQDBf/EACYRAQEAAwEAAgICAgIDAAAAAAABAhExAwQhEkETIhQyI1Ez\n" +
                "YfH/2gAMAwEAAhEDEQA/APtsSubZq0ThVRv3LZzvVlk5ouvSqJ5yFS4G1KCt2U+AgqGwM3tFsy0s\n" +
                "Lk5LgvO6x15NNPHtgibVb+1k4472VkKiqRWUsme0WzRmOofppclJC00fS5GjQ4rtxpeRrciiCBK5\n" +
                "lPZBhWzZUE9omeJO2x6h0kaY4VBMBTdRZkuDTLxRKQ2E1SMqbyUbS9ycKtykFGd1Ch6ePZi/Jll9\n" +
                "U0joaqKQEvYmG7chzdIcF6QFW5MV67NPFkPZr5JsikgkWQ92USlbsuKpNgkOT7YhGEV3578I6TLB\n" +
                "Gk37mvgsKxzuoMnEqgPJ6pKI+FRNmkMcI1b9xVvRol4AyybRYscahfllZN5qKLkhs0xeyZzY/Xmc\n" +
                "n4N9Q+3G2RpYem/cm1bJUMpIfgqM5P2CPkpohv0suxjjj3Z234OpkY49qKlKkBjLd0PiLCG9sWV7\n" +
                "V5ZBOGN3JkV35r8I2dQxC0sdnYVtVRo5tXKof3OuWyOLIv1cyXhFtSRvpcahi+S5Oky0qSRjne1e\n" +
                "42a+06eNycjeQoR7YKgk6BpHMtiMicskUXh3uReONybZFNqo0OKqINeBzfbBlRh92V0Wo+qyMW27\n" +
                "5bN6oKIqhvYEhtN8IfafTDMu6VeBJepItY5Pei44pN8DVNxDJh6mzZ4JtMrFpXGO/LLMaflGMVvR\n" +
                "U0jphp6bK/d0+S/hWfyjhSuQ58eTuWniiv0Y+2xr+Opc48vT43cpNcs2lGTXB3rHFLZFdi9izzT8\n" +
                "3nfpSa2RENNPvbZ6lJcIC/xxPzcP7tJoa0vudoM1+ET83KtPHyhy0sHszooC/jE/KslhivAfpxXi\n" +
                "zQZdJtn2RXCQKK9ixF0JrcGh0NjQmgKAaENBSLaFQRDQNF0KhoRQUXQiaVDW4qLaBoDNolo0aE0V\n" +
                "GdCo0oloKhohxNWiKCIaM5rZmzRnNEsV4umS/WyflmuodQ2M8KrPk/6mPO7yKPg5q6MRjjUPyUkU\n" +
                "41shpbEKib2OWK/UzJ1sjfPKosnTR7Yu+WDX0c9kQ3sU1cnZE9t/BdppjL1TSRWTaNE4t5OXsU02\n" +
                "yNDEu2P5FllUaLeyswyu2qAhXdmitqwouSqOxYlj6mKqCQPiymyZ7RZghYldscltRUFUF8iq5BTi\n" +
                "tgZRLKMM2+RRR0xVJHLi9eVtnWuCRaie8ivBMVcmWwhLcwyerKl7G72TZjj9WVyA1n4HAXLNILYC\n" +
                "Mj2ryXDaCREt8iRU3USoxn68qXg6kvBzaZXKUmdDfIhWMneT8FEx3bZXgionwOC7cbCW7oWZ9sKQ\n" +
                "Geni5ZO5m75J06rGX5KMsnKRXCEvVkbG1uQJ8Ew9UrKbtlYopL5AJfaTj8svLVCj9oIcd2Rmfg0S\n" +
                "oiSuW5Q4KkkVJ0LHu2LI9mEZQV5HJ+BN3Iuu2H5MoL1N+GRWkFbsp0lY0qiZ5XUfllQsauTkxhFV\n" +
                "BCIrm1LuSh8nRjj2xowiu/PfhHUkIUNEyG3uD4KiJOokpdzXsE34KxxphVJGed1D8mr2swn65peA\n" +
                "isaqCJce6f4NaolKtwMs/iPub4VUf7GFd2ZvwjqSqKEKjM0o2YaSNylN+R6t7JLybafG0opcFk3d\n" +
                "FuopsiUe6aO2OnTqy44Inp/HWP5I42qWxnljJw2R6axRK/Tj7F/iT+R5sMUljSNYYpJcHd2r2HRf\n" +
                "44lzriWCTHPTOUWvc7aAv8cT86446RJR34Nf0VZsFGvxiflazWKK8DWNexYF1E2lQS8DpFCQNkDQ\n" +
                "63ABUCKEVCYfkYBSChgAhMoQCoKGAQhFCZQqAYEEPkK2KoAJSGAcgJoKGACAYqKEFDa3BkEiZQmA\n" +
                "qFQwAloGhiYEtbElioCGiaLYmBBE1yaMiS2IrwobZ8vtY8Kcssm/BE9s2Wv+Y6cMKxJ+TmvXROFL\n" +
                "kP6bG+WgybRoiuXKrlXuatVFIiK7pFz5sDPhMxyv0G8uDmzJuSqyLDxrtgVWw0uEVJbFZYze2/k5\n" +
                "4fc7NdRL07cmeFNu2FbRWxGok4w28miVROfI1KVBH2Pkme8ki2ica7sv4IRctor4CLFl3Y4LYKci\n" +
                "MjqDZbMNS9lFeQHp4+ls3fAsaSgkE34BRDZA2NcC5CIyy9LFhjUfyTl3aRtxFBSRrwiYIeTaIROJ\n" +
                "NybYtQ6hRpBVAwzPul2hWunjWNWPK6i0WlUUjHI7dBBFUimxRQmRTXNmWV92RRXBs9omWKPdktlG\n" +
                "tUkiZbI0fJnkfCCFBUgfNlNkTdIilDeTNoVRnhWzZpwilZ5N5UW1SRC3yX7GvIRJM32plrcznvNR\n" +
                "CrgqgRJXsapUiHyEZZnURY1bROT1To1xKlZFVIxn68kUvBvsY41cpSKkOZlmfbBs2kc+feo+5Fit\n" +
                "NHa35N2hYo9sBt7FSpYpr0lVbHNbAYKNs1iqW4oxKewE5HSM4LyPK7SRSVJIKa4InsjRbGGaV5Ek\n" +
                "CLwx3bKyyqMq5ErjHYzlF22wjP8Am5Ynp6VeqS9kjh0kN3I9DS/fL8G/PrPpxukUAHU5wAwIEMEM\n" +
                "KBUMAFQDCiBAMChAMEAgGDAQDABANiAAAPICYDCgEKihcFCoXJQECoRQgEIpokAoBgyoQhsCKQDE\n" +
                "UAmMQAxDEAgAAEJgwAliKZLATRLKEyCSZcFmcuGFfPy/4zJH3kdvCpeDmUf99yv5Om9jly66MeFV\n" +
                "uzHM96NpOo2c8fXJtmVioR2FkW7s0Sozl8lGU3SVGaVzsO5ObVjWz2IKiiMktqNW6RzZXSKkYZX3\n" +
                "SSNYRqjCNvI74OqKtkjVKTpM52rd+TeflMwezKy+ye0Ww062bFk2Ve5eNduMn7P0mb3stcbkJW7N\n" +
                "K2BUyOefqypeDd8Mxwb5G2FjoJ3ci3wTEIGNKhPkc3UWwMEu7K34NXyRhjyzStyKvHuKe8ki0qRn\n" +
                "D1ZH8FRrJ1E58a7srbNc0qgydOvTfkDZ8bnMt5s2yuomUEKRpwiRi8gLM6iVgVQszn6pJHRXbChC\n" +
                "oszW8my5cCW0QJW6IyPc2rZsxS7slEVtBdsEOeyDykLJuVCgtm/cqqHFelIPID4Msa7sjZeR+ljx\n" +
                "KofIDZlN0i3yY6h7L5BGePdtnRHaJnjXBq+BCs8jqLYQXbjFNepI0YEM54rvzN+EbZpdsGydLF02\n" +
                "/LC8b8RILkS1QQorcJFeCQFHZik7kwlsmxQ3tsBcvdFBRUeAqW6i2c8I9+RyNdS6hXuPBH0oDSqS\n" +
                "OfO/T+ToZzyXdmURUjfFHsgjo0381/gylsqNtH/Mf4N4dZz46gAZ0ucAAAA6AEFFAMPACQDAKQDA\n" +
                "IQDABAMAEAwAQhvkAF4AYihAMCBAAyhUJjCiBAOhAJiYwoBBYxFAwGIgTAbEVAxAAUgYxAJiGJgI\n" +
                "RTJYQmIb4ERUvkQ3ySAjPK6RoyJuotgeIv8Ai8nyzprg5YyvWZPc609rOXLrox4x1MqgRp4+i3yL\n" +
                "U3KSSNYRpJEa/SZOnsZZZUjWS32OXVvwvIpGeLHcnL/BtFeoWFViSL4QiVGR02jl1D9LXudEuTky\n" +
                "u8qS4FWHgXpVo6Y8WZwVNI0lsqEKxySuRjyy8skqV7sSVID66e+RI2ktqMcfqy/g1kyIILeynwKP\n" +
                "AS5AzyuoMnBH037hm4SNcaqCRA5cCWwS5oWR1EoFuxZfYeK1El+rIgLqopBHehlQRA5tKJOBelv3\n" +
                "JyvwaxXbFIqMdS+IrybQVRSMfvzfCNnshFrLM7dBFUD3mV4AT5AJCn9oCxJSyM3m/Bnp40myuWwJ\n" +
                "kS95FS2smABN0icaq2Ob3oH6YEVcd9wW8hQfbjQ8e9sqKewl7jYPZAZZH3TSRrxSM8e82y09wCRz\n" +
                "T9eSvCN5yqLZhh3uT8kqtYItrcI8Cm6i2VGat5W/YvwPDFKF+WKWwHLqX3TjFHVjj2xOeC79Q34O\n" +
                "qW0aEWpbCrBCbpMIUnuOtthRVrcpKkBjmeyQJUkgyeuVexdBSKQkrFN9sWwjnyv9TMo+EdUFSObT\n" +
                "rulKT5s6/Ai1GV9sW/Yx0y7pymxauXp7fLN8K7cMUA3uzbSv+M69jGjXR/zn+DWHWMuO1AAI6nOY\n" +
                "IBgAAAUAHkAABoGFIAAIYCewAADBgIAAAoQwAQBYAAhiAGADAViGIAEZ5M+ODqU0mXCanFSi7QDA\n" +
                "bEAC8jABADABMRQvJUIGNiCkJjEwEDAQB5EwEwhCfOw2SFIQ2QyIGZ5OGWzDPJpbBXkZvTrZpHS9\n" +
                "kct9+sm2bZXUGcuXXRjxni9eVy8I3box067Yt+5U9yNUSZwN/qZ2vY6Ms+2DZlpo7yk1yQ414SQ5\n" +
                "8UBM5bUVGUnSdnLhjc22a6h0q8seKPbHcjTSK3FkfqHdKzLLLti2VHPJ92b8Gpngj9zfk0JCvr8K\n" +
                "pSZfIoKse5UeQkNcC8lEvZNgYZPVlR0LZGEN8lm0ntsFJbtsnI7aLgtjPnIEaLZURxbZcuDLK91H\n" +
                "3AqJtHZGS2o18CCJLuylzdRZOLdthmdR/IC08eZGk3UGLEqxoWV7UBESxRWyAA8kT9jT3MquaQVs\n" +
                "vTjSBbDe4gjPJ7DSpCe8imtgqauQS9UkgeysWPe2QU+KNIqkZx3ZrZYhJbk5XUSomeZ+qMfcB4Y1\n" +
                "jv3KqkVVKiWwMcz9Ne4QVJIMnqml4RcVuRTS2Iy7pRRsYr1Zr8IqNeEjHNKoNmkn4ObUO2oryCL0\n" +
                "sfS2/JtLkIR7YpCW7KE3SIbtpF5K2IStkFpUhSfbFstIx1DqNALHvbfkuTpCiqghy5AEY6qXoSXk\n" +
                "28HLkfdmS8IVY300aj/qat0TBVEnLLtjYGDX6uoXsjqZlpYvtc35NAgZvpV/EX4MOWb4HWSK+Gaw\n" +
                "6mXHYwWwmNHU5jAAAABBuFMQDAEAAAAAMKTBDqgCADHUZ4YZQUml3Ojmz9Qx44Np7pWamFvGbnJ1\n" +
                "3AZ6fIsuGGRcSSZoS/TXfsAAECGAAIBioAoQ6ABCYwYHyOoz5ZdY1WKTShFrtPoOlu9Iqbe5871T\n" +
                "FPF1bPkwRjLvrutnvdDyrLoYSUk9/Bqo9AB+QMqQDEyhAedr+p49HqseLJ/WrRlqOq4kksUlK03Y\n" +
                "0j1QOLo+r/fdBizpNd6umdoUMQAwEIbEAEjYgExMGJgITBiYCZI2JgJvYzmkyyJeaIrxMlQ1uReC\n" +
                "5PuSo5tdPt1s63OnEqgjly698eNEqRlJ+S5MzmRWOd9yoL7IpMpK52ZZH3ZFFeArXiBlJ70aZHt+\n" +
                "DmnKk2CM5Puy1yjdGUI0rNVwClOWxzamW1eWbzexxyayahfBFjoxqoqinxYfgmbpMrNfaNbJBET5\n" +
                "LjwRDa22M5v0s0ukYzdp+wWM8C9TZtMWCNR3B75KCr4iZ4Fu2/JeV1EMaqIQSMI+rK/ZG2SVJmen\n" +
                "jzIK2itx5NojiLJu0gggqiTPeaRozPHvJsDTgzb7p0XLZMnGtmwH5Ex0HkCW6Q8S5Yp8mkFUAEhS\n" +
                "4ZVEZeEgFDgbGtqBoCMj2opKopEvfIkVIKqCKfIJUgSCH4MYLvy2/BpkdIWBbX7jQqXJnM0fNmct\n" +
                "2KRn/UbRRnBXM24C1M3UWZ4ftcvceZ0vyVFdsKCJk9rMIruzN+Ea5NlQYo0rIrST2FHkHuxcWVEz\n" +
                "3bKgSlbNEgBukc033ZkvY3yOkc+Ldtv3FGqYvI2HkBT2ic2BNzb+TTUz9NLllaaNQV8hWr2OfUPu\n" +
                "pL3OiT5ZnGN5LCRpCPbBIH5GwfsBMOTbTu9QvwZJUaaZfxkzWPUy47a3GKijrcwAB0AkMAAVDAAo\n" +
                "AAIBggABWOwe4AeB1tSlnVP7WpLf4/8AyeVll35F3Lf9He/ez6jV6WGaanJK+Nzmy6TAlHuq2u2z\n" +
                "s8/WSSOL18rlla6+m/8AAaf/AKF/odRlpcf6WCGPxFUanLl92114zUkIGMKMtEC5AAAPIMQDAGIA\n" +
                "sTGID5vU4Jf7U1E8XqTStex2/TkYw0k1Hb1ytezsnWaGf79kz6eW80lKN+xf0/j7NPkuu9zfdXhm\n" +
                "qj1QGBlSExgwPlvqbtXU9M5puLxyXB5GLA9HLHqMSnOMl2uDd0n5PqOvzw4o4Z5Vu5dqZzdIlpdW\n" +
                "80Wt8cu12b2y1+koyj0LTRmmpJU0/wAnsmeGEMcFHGkorwjQyoYgAKTEMQQmIbEAmSNiYEsT4GyW\n" +
                "AmSxslsBMiRTZnJ/IV85rU31Xt8cnpRWy9jk1MU+qN/B193bFHLl10TiMj3MpOipO2Q9zLWkyl2K\n" +
                "zLTq+6fuRqpttRRovTjpMCZtt1ZlkXESuWKrk2RVIJsXBEm2VEZJdsHfBlplbcmuRahuT7V5Nsce\n" +
                "2KRItWYZp9tmsm0c2V2Vl96uTThGcd2WRBPaDMKuBpndQBL0oVVR2gTjVzbKlxQ8apMDLLvkSNap\n" +
                "GcfVlv2NGBhm2iaY1UEZT3mkbr2ApbEredl8CW1sqCeybQsSqNiyO6Ra2iRUZXSr3HFVFImXqmkW\n" +
                "wFewRXI3wPiIGXMjd+xnBeq/YpgPwZy3kWTy/wAhSG+BDl9pBEOWyo+qQPaKKwrayo0apAgluw8b\n" +
                "lRll5SNIqkZpd2T8GrCplsjJutzTIZT4SM1YvCtrLkEFSQp7WVGMvXlS8I2a2M8K3lJlSkqaQGcv\n" +
                "VM1S7YkYt7NJewKnkie7SKboUVbAcVtRYqFJ0UY6qXppeRQVKhZFeRWWkQD32KIXI5Oo2By5n3Z0\n" +
                "kdcF2wOXCu7I5eDqfCRItTLgcFSsKtlT2RUCEuAjtGxoAfBemf8AGivyRy2Tglesgl4NY9TLj00x\n" +
                "ie4zqcwoYhlUAxIYQAABQAAQAAAAAMAPH65qcmB4+ziUop/FtI8/LOeR58M5bwyPtr8J/wDqe11L\n" +
                "RR1kUnKq3/wzkydNlcpxb7pSUnX4r/0OrzzxmLk9MM7ldceh0+csmjwzmvU4qzpIwQ7MUYrZJcFn\n" +
                "Nlfv6dWP1AAARQFAACAAAAAAoExsT4CPn9brp6HqWVZGv0ZxTjfhm309kU8eeXcmp5HJfg87q0XP\n" +
                "rOSGVL9PsTWxz9Hw6rFnxZIZO7GszhJJf0+DWkfXzbUlRQDMqQMBMD5n6tjkWXRzf8pZo234OPpW\n" +
                "lnm6l1F4Jdq/U3/we71xYMscODUNLvlcW/dD0mDBo/1Zwmu6e7/JraH0yOfT6bJHUu3GTp+6PHz/\n" +
                "AFRhWsenjL1qvB7HT9ZDqHT/ANaP2u1/jY/P+o6GGm6tk1ClJyiu78oQr6jT/Ucc2eGDdZXkce33\n" +
                "Pplwj8z6bolPJi1mKUpTjmU1KuU3uv8ADP0rG7gn8CkqmIbEyKQmNksIQmMlsBEsbJbAUnSJu0OW\n" +
                "/JOyKFIzmW2ZTfuRXjal11B17HRN3Ry6t/79/Y3T2OXPr3xn0RM32xbKZy6vJ2xpc+xitssP8TK5\n" +
                "PhGs5chhio4vyZ5gpw3TY1swiqgtxeQhSM7tlZXSMU7d+wJEKN578I6KIxqm2vJTewhU5GYtWzWT\n" +
                "ISsD7rFvJmpnp+GzS9wjHKrnFGuxk98v4NWBE/uKfpgQt8hWZ1GgJxLljkEVUULJwBEFeSzVLczx\n" +
                "LZs2gthA0TN70XRnXdlfwVIdbjbG0RPZURSx7tssmC7YlUAnu0kExx5E95AXFVCxIb2VC8AL3Jjy\n" +
                "W36WTFbADD2Qcuiq3CoyM0x7RM3vI2/pCJbop/aQ92kXICcUaV+45PcpbRMk7sAe7IW+X4RdbWTi\n" +
                "W7ZFbRRGb7TSPBnJXOioSXbCjJ8MuTt0iMj2oirwr02U+bDHtBBIoiRUVsKrZfgRKPBE+C5GWR1B\n" +
                "soxb7sjNXwZaddy7mavkyoiZ6mVQ/JrZy5vVljH5KNdNGof9y3yOKqIMIIcik7kkWl2xM8S7sjl4\n" +
                "QFyW1IT2TG3bFPwgpXUW2PQx/iRk+WzPO9oxXk6dNGpQXhFx6mXHdwADR1uYh8g0BUC4AAAAACAG\n" +
                "IAoABgIGAwPJ6tq3p9TplB8yakvijfo2Z6jQQyPluS/w2jm6xpXm1ejnH/naf+GZ9O/W0Ol/SnG+\n" +
                "2cuPZts6NY3zmuubeU9bvn/x7YGeDIsuKM15NDnsdE+wAAFAAAAAIApAKUlFNvZI459QwxdWwjtJ\n" +
                "lwycWSOWCnB3FlsK+Q6jPN/tPIssXKNbUet9P46wTbVLutL2PN65J4etQc5fwpwpJe56/RJReKai\n" +
                "mmnuaZekAM8vruu/ccEJJ13y7SK9O0DPG6ZqMuTULvdxa9z2SD5z6qxfq5dArr+LyPLpowxNyz14\n" +
                "/uetrtJDVPG5uux2c8emxljywySbjJ2vgqOH6Qx9nRY45O6lJP8Ayzk6lpdJHWP9Vepx7Xb5Pe6d\n" +
                "o46HC8cXcbbR5/Us2hyZGsji8iKPL6KsGPIsEYXDvcUfWrZV4PnunajS4YzVwcv1KpeGfQRdpMlI\n" +
                "bEMlhQyRskAZLHZLCEyRt+5HcvAAyWN+SWAnwZzoqT2M2wrxNc0tel8G8Xsjl6g//aEaXg6Fwjlz\n" +
                "698eKm1R57f6uevCOrPOsbsw0qrHKbMV6RpkaSpGVd0vwVOQY1SYQ5bIUeLIk+6VIrLLsgBjmkrb\n" +
                "9jPD9m/khtybXubxSSS9hBUVSFkexSMpveikRJgrSBbsdbEhX3uFVjRXkdUkifcrMTGPrbKnsmOK\n" +
                "5JzvaiKnArtsWXeaReJdsfyQt8j+ALa2oyyexo2RJW0A4qoo2iZo0iglOTpMyx+WVlfpr3CKSikV\n" +
                "TbMpq5JGsjOO8yCuAbGyZBVQdJsI+4cRQ62CE3bKQhoCcguA5kwfkKI7ysuXBONbDkQKCt2W3Qkh\n" +
                "y4KiYLuk2aBBUh8gRkdIIqoL5Jm7mkXLZ0BlkdRKgqSJyfckjSKIquEJbJsbJntEqM0rdkNd2SvB\n" +
                "fERYVcnIitGvBDVs0lsmTHcoaQ/IJ7gVES5s5tS32pLyzqdHJL16jbhEI0hHsikuAfDZclRnk8L3\n" +
                "Ch7QMcK7srl4Nsv2NBgj2xILlvsKraEVH3KFml2qwxLtx7+TLM7nGK9zeXCSCEluS36i5bIzk/Sx\n" +
                "VRD15m/CO3F90fycmnVJ+51Q++P5Lj1MuO0QwOpzAAAqGIfkQUAxiCABgFC5ABMBiGIDHWZI4sE8\n" +
                "kq9Cct/g5v3zHOahaTlj/URh16OT92n2bqUHH/sefpNNPJmwzT9P7vS/7bHRhhLju1zenpZl+Mj3\n" +
                "Om5Fl0eOaVWuDqOHoia6bhT5Sf8Aqdx45/7V7ed3jLQDADLYAAYUAFgEc+tg8mlywg6k4tJ/J8zi\n" +
                "hNYlDOm8lU38n1OoTeGaTp06Z87gU5wTy5IqfksSvU6HglptDHHJt03V80eizm0EHHAk5KXyjokR\n" +
                "XyH1gs0M8ZxlUUoyj8tPg9L6ayTzLPlbuE5Jr/BxfUGSUur48EknjcL38M9ToUYxxzUeE+DX6T9v\n" +
                "VPB+roSloccopVHIm78I948f6llKOkg1Hui5pP4RItcPRM2PJqoqEu5pH0h8p0+P6PVsH6Ndk4tT\n" +
                "Vf8Ac+rFSPJ+o9Y9FoP1YtKSkv8AU8nH1zJmyZYYnbxujo+tNLLP05yi3s1a99zxOn9PzabUanuv\n" +
                "tlJSTrnYshX1XSNa9foe+SamrjJezPz7q+n1EOvPJOT/AE1yu7hWff8AR8Chp5NXcuUzwOtdOlLV\n" +
                "ucpx7e1xf9xEr53HHUfvj1Mf5cMylKCfMb5/7n6hifdii/g+R6Z0qOm0jhmyJxk6t+T63Eu3HFey\n" +
                "FIsQ2SzLRMTBiZQmSxslhCZm47lslhSZLGyWBMuDOStOi2Q+SDwtaq10fwdK+059ftr4fg27qRzZ\n" +
                "9e+PHJq590lBGi9OOMV/cxxJzzufg1k6TbMPRlkalkUUaTfajHB6sjkzSe8iAxLdtoz1U6g17m3E\n" +
                "aOLVS7skYopDhF9iZtHZfJMlSSQ7oFNujKbtr4LbIAEuQfA1sTPihEfoLJfBTJirZWYrwY5d5pGz\n" +
                "2RhH1ZrIraW0DLHu2zXJuqM0qQBIUOLFkexa2ikALk0Jiti2CsperIkaMzhvNsuTAUvcmG7Kk6Qo\n" +
                "LYCmTVzKlwTHlsCnuymqJjyOb9IE+Sm6TJgtgluARWxMuS0tiY7z/AVpFUhNb2UhZNogKG9jaCCa\n" +
                "W4S9gi1wF0mN7UiZuolRnDebl7FsmGyG9kRpHMjWKM4bs1qkEpJWRl3lSNJbIyj7giMrqJeBdsfy\n" +
                "ZZF35EjojsiLSyMmK2B8lRKEipBFbilu2EZ5HSf4MNMruT8l6l1CvLHij2wSIpy3ozjUsm/gtumy\n" +
                "caqLfkBZPVKi0qiRFXIuTAnyVwiY7yDUy7YuijPAu7NOb4WyNuZNixx7MX5KjsEqMrq14RnB3BN+\n" +
                "Q1Dbj8tlqPbGKIq8apm+N1OP5M6qhw/mR/KNTrN472CBgdUc4YLgdbAioAAAoYDFwgAAAAAAABDY\n" +
                "gJnCM1Ula+SMWCGJNQWzNRl3U1EY4RxwUY7JFABF0AAAFHgYAwEMAAw1ik9NlUHUu10fIY9Tjhg/\n" +
                "3maWTh/k+w1alLTZez7u10fAyWaerwymo9rX8SNbxZqJX1v05Gcenrvm5q24t+x6rPI+m4zx6Jwn\n" +
                "JyqT7W/Y9dmar5XruOL67ildS/Tqn53PW6MnGE901fg8j6rnGOeMkpd8I3cV4s2+lM7yS1MJd7cG\n" +
                "t5edvBf0n7fR+TyuvvJHSwnjh3qM13R+D1Ti6lqoabFGWWuyUlF38kivFw5IS6ppf0IqPKl70fSH\n" +
                "h6l4sXU9E8Ciu+1LtR7ngUeR9SamOm0KlJd0XJJo83H17BNKEVFScG1/Y1+tcM8mgxyxvjJG177n\n" +
                "zOHSaiUcC/SqWLvfvZqJX2/Sczz6NTlyz4L6jy6mWp1DWVqCn+nJfB9j9O4pRxzyKblinTin4PG6\n" +
                "v0GWt1GprK1Jy7lvsIlfPrXamenxw/UvH/DdXvVn6fp/5MPwfHf7Aw4+mY1OXbluNV7p8H2GDbFF\n" +
                "fAqxoyWNktmVJiY2S2UJkspslgJkFPglhCb3IZTIZFJszky2TLcDwupf8bD8BllWP5YdUX++4yZe\n" +
                "vJFeEc3p10YcViXbiXuZaiVR25ZvkdKjlrvy14PNtrjiowTQ4q5WVPZJIXCKicjqLfscOJfqZ3Lw\n" +
                "jfVy7YURgXZj38heNHvINqAGBDYDapjoBMhXuymtiXsEffyscOBTe5UeC1E5HUGzPTLljzP00aYY\n" +
                "1jRF/QkQVIT4CMpbzo1W7M8e8m2awW4FCm/SxsjK/ABj2j+SnuZx5SNEBGTlItLYirlZp4AmT2CP\n" +
                "Ap+xS8AUuDKe8kjaWyMY7z/AGq4I/qNGQkATdIWJbP3FP4NIoin5Jl6pJFr3FDlyKhkw9UvwOWyY\n" +
                "8S2bfkCuWRk5ovwZ8yBDSJyOoloyy+qaSIqsMaV+5rQoKkkWtkVGWR7UJKo2D3lsGTaOxFZYl3ZW\n" +
                "zZ7RonDHb5HPd0UTW5aJW5SCKXFkeC/FEvhlHJl9WVLwjbwQo05SfkpcGVJr0iltFJGlbGct2Ao7\n" +
                "JhLZFeCczqABhdyk/BGZd+WMS8K7cX53JxLum5exRrJkv7QfIpbIIyruypeEbpWyNOtnJ8s2SC1M\n" +
                "32qww7yizPPK5RguWzogu1Is6l47RoGB1RzAKGBQgGIAAYAHAhgAgGIBeQQdybatX7Be/O4DQjHP\n" +
                "qsWF1kmkzD/aeltL9WO40bdoCxzU4qUXafksCQGIAACcklCLlJ0kBTEckeoaeWTs713fJ1J2gbYd\n" +
                "QySxaTJOKtpWfmf+09RmypxSi3F3tzUj9M6hCU9LkjDaVbH5lDQ5lnhCTaXri5Je5qM19x9MTz5N\n" +
                "M8mdrfbY9xnifSuGeLpuLvn3elJ/k9pma1HznXckX1CODtuU8TX9jy+j66eh1c4yhNxeVYpSfi+D\n" +
                "s+pssHq8cscu3NBNXV2jXoccGr0vraeScrf/ANrL+kfSJ2rPK+o5Qj06SyK1JpL8nqcI8b6myYf3\n" +
                "L9PK+1uSp/NkivnMWqyaXWYnmubpygq9j7LQaqGs0mPPj+2aUkfM6aLy6/Tfq00m0n/Y+k0Gljo9\n" +
                "LDDD7Y8FqRx/UeaGHQd2RXHviv8AueG/qPBCL7Yra06Xsdn11DLLpcf0n/XG/wDJ+fZ8Oongfb3d\n" +
                "3fO0ltwyyFr9K6BrFqFlisbhTv8ANnidT1GaPVdXCGVRprtTPU+klWghbt9keeeDzer9Kz5upajL\n" +
                "GTim0017EhXmvXav9702PVO8PfUpL/sff4mnjjXFHyePo+bDqIZpy/VxypSj7fJ9ZjXbBJeBSKEx\n" +
                "sTIqRMbJKEyRskAZDKZLAlkvkpksCSGUyJAeL1hdupxSXsLCv6g602s2IcHWNHL6ddGHEaiVJsjT\n" +
                "x2cnyycl5J9iN0u2Fex5vSp5fyDCPNim6i2VlyZbyZ1HwbPlL2MsC+6bNYkUyC3sJLYqIkCG92HA\n" +
                "Ca8mU3yavaJlJ2wPv+ZGqREV7mi4CMMu8kjaqSRnHfL+DWRSs3yRk4La3Iy+CB41UTSK2FFbIrhA\n" +
                "qXyRLef4NDK+QHDyy5bIIKohMCYI0ElQSdICHvI0XJlB22awAWV7CxL02GRWy4pUBMmPwD5GBm1v\n" +
                "Za+0mS3SRfOwUP7S0qikTW6RQRGTwjRKooSVyLYGc36WRC6Kyew+AoREI3kbLfAY1sBdUhTdRKIy\n" +
                "PwEZwT5Ce5T9KIj6tyKuGyIkaPZEeShxWxT5Gh15KiPIMdEy2AxzP1KKKS3RGNd2SUnwjWPJFKRC\n" +
                "5LnwTwghMwyNuorlmz3RlBXlb8IitXtGl7BFdsEOtvyOb8FRPJGR3fyUr3CK3QVUVUUkWnSJW7Fl\n" +
                "l2xbCMcfrzSl7cHTJ7ojBGofkb+5FhXogMSOqOamAAUADEwAQ/AgAYAAgYzj6h1DBoVH9eSipcWE\n" +
                "fKLq+pX7SJdOtfu7xXX/ANtlvrOoj+0CPTVX6Dxdz/wfM6jq2nx/tNjrO5PC8aja/wCiiNV1rTx/\n" +
                "aJi10ZKWL9NRbXjZm9M7ep9XZM2f656d09ZZRw5Y7qLq+f8A+ju679LrD0jVZcGbIsuODlFqXlHy\n" +
                "31B1vFm+t+m67AnKOJRTS/L/APRn1+o+rcOXFLH+jOSkqa7Roldf7OdTn1X0zhlqZSlNScU5c14P\n" +
                "qT4j6e61HBLFpMOnnHHKXPbVWfbozWobEMCKk8/rkMs+mZ1gko5e24tnonn9cWR9Mz/pSSydtpv3\n" +
                "EK+SxQxa3WJ984NY3FvdU/DPsem/qLRYVldzUUm/c+ahjjn07hGMu+UabXufSdLx5MOgwwytucYp\n" +
                "NstSNtSu7BkV1cXufIRf7rhUdWryPburk+v1MXPBkitm4tI+Q00smXNp8Gsn/Lg1KXuxCvc+nMLx\n" +
                "aN+puEpOUb8JnrM8n6fx5MWHPCU++CyPsf8A4T1L3aZKsfI9e0s8ernkb2cu+O9eN0L6axP94jvS\n" +
                "jKUqvwzs+ou59Q08Zr+DKLt/J5Olw6rFqMuXTTuEMsGklzF8o1+kfccnifVEMX7h+pl/paSfse1C\n" +
                "+1X7Hl/UShLpmSOSqbSt+GZi15XSsLjqsU6bXyz6c+N6NqFenk8km3leNqvKs+yXAqRza/T49Vp3\n" +
                "jybRe5xx6VpV/TzuPr+olh0GR47/AFK2XufGav6mzxx41p33vsXdvVPf/wBUWSlr7vTaXDp3J4lV\n" +
                "/J8r1zrWfBlzwhFuKk4N+22x1/R/Us/UYaieavTJJb/Cf/qef17pWqy59U4JyhKamq/BZNHWHTuq\n" +
                "656eEcSeSccSyNe59roczz6bHkapyV0z5f6Q6Xlwxhmzbp4+yn+T66EVCKUUkhVgBgIyEySmSwJe\n" +
                "4mNiZUSySmSwJfLJKZLCpZEi5EMg8PrDvU4iZSShY+sutRjOecu5KK5ZzenXT58aaeN3LyaTfgeO\n" +
                "HbChVcjzaoqkjDVS2UVyzbJKt/Ywxr9Sbk1wUh1244pFUC3e4wVnJuxtg+RPcIFwLyU1SFxG2BE3\n" +
                "St8GGN90m/A9ROoP5DAqx37idLx+iR4KbqLAWXaJUicCttmnkWJVBDAVGTV5a9jWWxnBXJsg0SBj\n" +
                "iglyVEN0iKV0aT5omK9V+ERVJA92CGigJnwUTPd0QSlSNY7IiS4Ro1SAhvdlLZbkPmjVr0gZthF7\n" +
                "WKfBVVAipi7k/ZFR3kTHaNmmJemwGheSm6FjVtsqLx+WNsKoT4Azat2PwVRMuQoStGkVQlsV8hCe\n" +
                "xnzIuTsiPNgTnfpoeNeknMzTF/Lv3ClLkmO8hvyPGvIRb4Fwin7CaKEc+odRdcnQ9kcs13ZUvCJS\n" +
                "LxRqFe5pVIUd2VJ0hBlLdkyKS8kT9iKh7RbDFGoX5bKcbjRpH7vwAPYiW8jSS2IoqF8D4C9x1ewA\n" +
                "9lZnk9aivdj1Eu2KXu6KjGmgKXpiS/tsc3uG1fgsHo+AQ1wB1RzUAAFAAAgASVFPYPACAA8gB5fW\n" +
                "+kYOqwxx1C+xto9Q5tXq8WlinmkoqWysI/Kv9i4MP7SloGrwyx91f/Z/+DT/AGRp8H7S8ekUU8M8\n" +
                "fc0/wfTvpuLUfW2Pq+PKpR/SUWvmmjvz9Fwy+qMfVu9d8YdrVm9s6fF9d6bp9L+0XpOLHBLHl7e5\n" +
                "e/KP0aHSdJHjDH/B4HWukx1X1X07qUcse3Akmvw3/wD2fT/vmBc5I/5JftqFj0OnxtOOKKa80dVH\n" +
                "Pj1mDJNQhkTk/B0ozVIBiYCZ5f1L3/7H1LxupqNpnqM8z6ihkydI1EcKubjsBw49DnwRjk0s1JNX\n" +
                "R7WmlKWCEsiqVbo8iOHV6VR/Rn3pq+09fTTlkwQlJVJrdCkPPbwzUXTp0fIY5rHi/wB99OXh+x9d\n" +
                "qE5YZqPLi0j5fTYdRrZafFqoJSjF97ryWJXp/TUMmPRz75OUXNuDfsz13ueX0HHmwYs+DO7WPI1B\n" +
                "/wDh8HqBY+b+pcsZazT6Wcbjki5J+1HR0HDDHHJFU47bHJ9W5KcUoSlKEXNdvNeSPpPLKWp1EJQk\n" +
                "klFxk3ymP0n7fUHm9dWN9PyLN9r/ANT0jxPq1J9Gyp3Vq655EWvD6LpnDXRc2nB5O/ZeT7Q+X6T3\n" +
                "LJhqUT6bwKR53W9K9VpJKH3x3jufDaroGTL1GEscXCGWCk17ST3PsvqjWz0PTJ5cf3Kq/wAniarr\n" +
                "f6+KOPTqLzuCkv7liV3fSnSMvSlkjKXdGaj/AJSo7upauUMjxY/uqzL6f1888JYM6azY0rdbP8GX\n" +
                "XvVmX6TUc1VfuiH6cug6nlxfzMbWNT7XX55Ppou4po+VwTlggoam5KclcqPqcbX6argVYbExsRAi\n" +
                "SiWUJkvkpkgJksbEwJfJDKZLCJZEjRkTQV4HXHWbE/Bhpl35FJcGv1FtlxfI9FBxwr5OX0/2dHnx\n" +
                "rN0nXkUeAkt6G+DDbn1DbVLyVFduJC7blbHJ0rIIu3RRMN9yiwJ8EpbjYRCJb3CTTQ39zfgxnNJO\n" +
                "wRzZW55e1cI6Wu3HSMNJHuySckbz5a8CLX6GkTktui1yCXqKwfCIZpIyW92FEnbCKJhu2y0QXwiG\n" +
                "7ZYpbBGct2EOB1aHwFLwMBsBEr7inwTHhsBxVzsufBOPixy3KJgrnZrIjH5Zb4IMpbyRUuKFXqKr\n" +
                "cCHsqNEqiRVzNQIm6iXiVRRnkVySN1sgB8kz2RT9zLKwQY97Y3uyoLtgTHywqktxvgEJhEvgpLYG\n" +
                "rdFSVIDmzbyUfc2+2Cj8GK9ef4RtLcKh+xtFUkZJXJG3gRC8kryUJoqM8j9jJqnfuayW5H3T+ESq\n" +
                "uCFl9jRKkZS3YEPZGa9WR+yNMnDJxR2v3Iq9ox7mLHvv7hleyivJpFKMdiomXsKh8sAJ7dxxRQSa\n" +
                "jCwOSf8AE1Cj4XJ0ow0yfdKb8nS/t+QrPmQSXpZSXkGIj0I8L8DTIg/SvwWkdcc1FAMCoQDCgEAw\n" +
                "AQDCgqWtjzeudKxdV0yxZW12u00z1KFQH59rvpXqGivJ0zVzTW/bJ2j5PWdX63o9dDBr5Sxxbru4\n" +
                "TP21qzy+sdF0nVMLhqcUW/ejUyZuL5TSdC6hq8EMy103CatNM3j9Kap/frMtfk+t6XooaDRY9Pj3\n" +
                "hBUjrobWR8t0r6alo9XjzvUTk4u6bPp1wOgM2qBDYgEzHVZIYsEp5PtS3NjzuvxhPpWojkbjGUav\n" +
                "2ER5Gq6vjj1bStvtgoy7l8H0mOanCMotNNWmj5Xp+jw6zLCWo7fTBwp+U0fR9P0/7rpMeBO1BUvw\n" +
                "WkbZZKGOUn4VnhPrUHNQxw9ct40uUe5mScJKXDW54mn/AHLFjwqKv9K+1pCDu6Rro67DOUY9s4Sc\n" +
                "Jp+GjuOHpcMS/WyYf/mT7mvk7iK+e+psn6Dx5VvJJqr5TJ+lsiywnLs7Zf8AodH1DoXrYwxqMn8r\n" +
                "wZ9H0s+lxjjyzVZHUb9y/pHuHl/UMZS6Zl7EnJb0/J6Z53XO99Pn2NJ2ufYkWvD6ZqsMs+GPZUm/\n" +
                "HufVLg+B0+PJp+p4ovNvDNf/AFRZ95F3FFqR4f1jjnk6RkWOHdx/bfk+Xx9K1WDVPNFUv0k1S+dz\n" +
                "7P6g1H7r03Jlce6Ma7l7owwdT0zwwl2vgsK5Pp15lqJx1EU24JxmvK9jt6np8WTLGbmo5Eq/sc3Q\n" +
                "9YtTr9bjikseOa7f7pM6uoLTz1EVkn2yS4vkg4JY4RV5cqcfKPfxNPGu3g+ejqOnZ8eSLk7T7Xvw\n" +
                "z39NKMsEHHitiVY0ExiYEsTGxMoliZTJYCZLKZLAlklsiQEsiRZM0B8/1yPfqMH5NYKsew+rr+Pi\n" +
                "Bfajm9OvfC/SUgft4KWwmtjzejNowzPdQXk6Htuznxrvy93hEGkV2wS8id3RfuyZeWVEyVsfCGvc\n" +
                "T3AlqkcWqrZLk7cjVHCl+pmXsFkb4IqGP5E97NJtVRKQH6Gi4iKSK80ZHSM+IWPM90gkqjQaPEvT\n" +
                "Y1yCVRQ0QPlkTaRZlPeSSApcB5KSJl5CEhguA8hSlwJraimHMgGlURMpirdAUo1EUuC7IkwJjvMq\n" +
                "XAsXDfuU+AFiXLHZS2ikS9kAsa7sl+xukZ4Y1G/c1KlKXpic0vVNI6Mj2oxxK8jZKsaPgSWxpRLQ\n" +
                "AlSJW86KltEWJXbYFJbiyOotssw1L9Ne5SM9Or7pe5s/tJxxqKRWT7SKMSvct+wY1UUOrZWS8gw9\n" +
                "xS4Cssz7YNiwRdW/O4Zt3GJrBUiBTdJma2TbLyKyJ7RSBGUlZpBUvwJ7FTdQYVkvXmvwjeS2ojTx\n" +
                "qNvllT+4IVC5H4EuQqkjHVv00vJulyc0vXqPhBGmKNRUS58FRXkhq5ACVITVopjS5A7IJdkfwWLG\n" +
                "v4cfwi6Oucc96nwA6CislQD/ACACBDABUA6ABMBmOsyfo6XNl/5IOX+EBoB8/wDRnXl17RZ8qi1+\n" +
                "lk7N/KpM93UT/Sw5MniMWy6NroPJ4f0v17F1zFqJYrvDPtZ7jFhKSabpNWgPhuhdZ1Of676hoZyv\n" +
                "BBNJfg+5Qs0Sk7AYURUs87r2FajpOpxSdd0Gj0jz+uwjPpeojKbgnH7l4LCvEy/uripRTxNLdo9/\n" +
                "QPu0mJ96n6fuXk+c0sJajAsPapbdrvyfRdN037posWC7UFQqRtnl2YpyfCTZ850rLoXi3j6ZNtN8\n" +
                "H0WoSeGalxTs+U02fSYdJj0s4xeRJ9vyrEH0+ljiULwJdr9jU8v6fy48mnyLFJtRm4tPw/Y9Rkqs\n" +
                "M+ox4a/UlR5+rktZLTPC7/SyqT/FM4Pqh5MWt0uVP+Ck1OK8+x1dBy48znLHEukeweZ9QrI+l5Xh\n" +
                "TclTpHqHNrpvHppyVOlwyRXxmo0/6+fSPFkak5VP3qj6P6d/eF06MNW+7JCTjfuk9jy9PrNNqdbh\n" +
                "hjgoZYzu62PqIRSjsqLUjy/qXG8vSssErbqv8nBhWmjhcVhucIJv5Pf1H6f6bWWu1+5yKWkhleRO\n" +
                "PdVCDyPpyONdQ108UHCORqVP3qjwfrKWaHVIuUprF9rkn9p9vp/3WWWUsKj3vmjxPqHpWPVatynL\n" +
                "acaq/YsK+K00GoZ4yk3KU1C14l4f+h+ndKUo6HEpJxaW6Pj9R0DBkXZDM1+qtvV5R9rooyhpscZu\n" +
                "5JK2MiNxD8AzKoBjoTKJZLLJYEklkMCWQzQhgSyJFslgjxesbZMTfuLE7imHW/5mP8ixfYjm9Ovf\n" +
                "Di2Q+S3w2Z8Jtnm259VPtpL+5pjj240ZQj+rmt8I3k96DVTJ7E8FciSthkcIjwWyZtUBz55VF+5G\n" +
                "ki+1tizS7p9qOiC7MdeSNXiHvKwdKLY0jLK/6Ssv0qh+AQpbJmmWL3ypeC5K2RiVzbNkiKgY2twj\n" +
                "uwBqkZRXqs0ybIjGtgi0RJ70WnRFXOwHLYS3CYR4Ip0KPLZUuBRVIBhHyNukC2hZUBEuBhV0iBxV\n" +
                "RSLolbuiuGFS2Kb4B7zGl3TA1gqiikHBCe5ULK+ScK2bHLccdkRVoTW4/I/JRlmfpouCqKQpK5lV\n" +
                "uA62OafqypeEdMjGMeW+WSkXBb2TL1ZFEviIsCtykwNK2ENk3boqJm/HuDZN3lfwgl9rIrJLuyOR\n" +
                "0JUiMapblyexUrPmRnJ3k28Gr2g35Mcf22+WRYPuzJeEVNW0hY48t8tlxTvcBpUheRy5E+AM8jpK\n" +
                "vLKitzOu6fwjZKwHN9sLZhgjs5PyVqpbKC5ZcFSSAriJMfLY5+w+F+AJkt0NAt9ymB2Yv5cPwiic\n" +
                "X8qP4LOucc96QDYFZJoNhgAVsIdAUIBoCBHJ1Vf+ztV/5Uv9GdjOXqSvQaj/AMuX+jEK+C/Y0/8A\n" +
                "2Vr/APzY/wD+p951H/gNR/5cv9D8m/Zr1/F0jSazFnX3yjKLr4pn1et+tNNLTZYqMqcWr7X7G7Pt\n" +
                "mX6ef+x93h6ov/qr/Q/RXwfjP7P+uy6W9clhnOGWaknGLe+//wCD6+f1nka9Okz/AP8AgxYsryfp\n" +
                "t/8A7odTX/V/oj9NPyf6I1X75+0HV6inH9RSdPlbI/WETIgChiZlojg63pZazpufTwdOcaR3nD1j\n" +
                "VPRdPzahK/01YiPOy9PxQyYoY5vHmlG1vs6PW0sJwwQjldzSpv3PIjro5M+DJqsbhKG6lXue3jms\n" +
                "mNSi7i1aZaQskVKLi+HsfH9Q6HHTrBO1/DyVCXndn1+duOKbjyk6PmJa+fU9Np8ShKOSduS9mmIV\n" +
                "7HSNF+6QyN13ZJdzo72eZ0PV5dRDPj1EXHLgm4P5+T0wR899RucNXppSX+7u1N+z8GX08v8Af9TL\n" +
                "FO8Ekml7Mf1RmcdZpcDipY8qfdfg6OgY4Y3kUKSfhIv6HsnF1dxWhy9zpVz7HccXV4qWgzKSuLW6\n" +
                "JB8l0XS54dRjOllxN8rwfbLg+K6Dqcr6hGGLInjTrtqtj7ZcKy0jxfqqUodLbg3GXct1+T4OfUcn\n" +
                "YoJ5ZS7ZXT9nyfoX1FpZ6rps8eL7k1Jf2Z8vg6TLHJOUYya7k9uU/BYlP6HlLJq8k5zk28UJU37r\n" +
                "cr6leoXUYRtRipXC/O3B1/THSdT0/WOWTfHLGoLaqrg36/0/NqM2N4pR9M1LflIfsfLYv1pPTpTV\n" +
                "ublB03T9j7/pPf8AuWP9R3Kt9j5jF0bPB5e/Iu2Ev1ISXg+t0Hc9Lj795VuSkbeAK8CI0gTRbRNB\n" +
                "EtEstkvgCGJlfkTAzaJZoyGBDRLLZLCvE62rnifyTjXoRp1pU8b+Qw/Ymzm9OvfDhS5ow1Mu2NLk\n" +
                "3fLbOZp5cvwebcPBHthflg9zdpVRlW7C7Q+KGlsOrY2qWxUZPeRnkdJmr2Rzal+n8kqxlp49+Vyf\n" +
                "g3m9wwQ7ce/I2rYKS2RgvVJtm2WXaq9zJKo7BH6WkRldRZpwjLLujTMTh2X5NuDGD9SNGQpSdFY9\n" +
                "1ZlI3gqiBlmfgaVImW+T8FPggQoeWN8CitgFPeiiVvMqtyhMpEvZlIiirdCycJDhvKxT3mVCXA63\n" +
                "GHginBbthIpbRIexQQ5seFbth4NMaqNCIG/SyI8X7jy+3kF4ATW5UCcjorHtHfyRVeQsa4F5KgS3\n" +
                "GhgkFLkmtyyFuQTklSo1gu2KRzr1ZUvY3ZYU29mzKD2cmPK6hXuJqsdECx7py92Nq6HFVFIGrApL\n" +
                "YTW9IrwZxbuwiNQ9lEO30pEy9WS2a0FJ7bIpLbcnyXwiiHwZ5H6WavkxmrnGP+SAxRqC+TVBWw5K\n" +
                "oNlGD9We/ETZEQVRfzuW9kQLmaFIEOS9QAlRXgXDK8FhXVi/lx/BdEYP5UfwaHTjxzXpMBgaQCBi\n" +
                "SAoQwAQDABHPrlekzL/wS/0Ogx1avTZV7xf+hR+Y/sk0GDWaDXfrwUnHJGr+Ufc63o2i/cs6/Rj/\n" +
                "AC5ePg+Q/Ywq0fUf+uC/7M/RNVFy0+WK5cGq/savWZx+Zfsl0uHMuprLBSUMiq/GzP0R9P0yX8qP\n" +
                "+D4f9k2DLhn1X9WEoXkVWq9z9EkS9WPy76axRw/tP6hDGkorupf2R+org/MuhJr9qfUNn/V/oj9N\n" +
                "QyIBDEzKkzg608a6ZqHmp4+12d5y9SWJ6LMs6vF2vu/BR8/PqDyQlhWBynjgpNVyqPa6Pmx6jpuD\n" +
                "LhTUJxtJ+Pg5IarTKpRhb7O3u90d/ToY8ejxxxKoeC1I1zPtxya8I+W0uTJGMdbhwuWN26XheT6r\n" +
                "M6xydXSPldHqJZcDenl24+5+l7VuIV6H07qY6rJrZxi0v1a387HtM8b6akp49ReL9PIsjT/8Xyex\n" +
                "J0m34JVj5j6thWo0uoTdY77o+Gjr6JP+NODik+1S/szz/qlR1sIvE5tqMoygvNmv0rjlKf6mTuU4\n" +
                "Y1jfd5ov6T9vpaOLrHctBmeNXJLg7jk6mr0eVfBmNPl+jY9JPWRl3KGe+In16R8P0nBmx9Vi8+O4\n" +
                "OW0l4PukaqRya/PHBgk57WnR8Dn6hlehhqY5WmsqUl7q6P0HW6eGpwSxzVpo+IydE1C0awLHcseR\n" +
                "Si622Ymkr6vp2thnjGC+9Rtni9W188XX4Rgm+2D7on0Gi0uKEITjj7ZUeZ1fpuXJ1LFqtOrai4y2\n" +
                "5EHgYOrZ5dO6h3xbgnNRdcI+q6BJy6VpnJttwVt/g8RdN1mHBqYRxuUcrbquLPe6Hilh6bgxzXqj\n" +
                "FJikdwmUIipoTRTEBNEsuiWgIaJaNGSFZ0SzRoloDNks0aIaCPF60vVjXyTD7EjXrK9WP8meJbHN\n" +
                "6ddGHEZn2wfyTp4UrFn9eRRXg6Ix7YHm3eM5ohmskZyXgpExVilzRqlSM8i5AwyefY5Zp5Mvb4Om\n" +
                "T3onDG5OTIqqqNCSo1lGzOb7U7Kjky+rMkXJeCMScpym9zZK2SLX6JvRGTZFNuzLLx8lrEPD5Zch\n" +
                "Y1UQmFJbzRrJmeJXJsubpMIiO9huKD2K8EUeQm6Vihw2E3sVEw8stiSpCk9wG/A2xLkPIVcFSIfO\n" +
                "5fjYiXLCQ2LygCLtkVoQ+UUnsSt5Ai0tzQzirlRUnVsoifqyItEQ3bZUnSIM5erIl4NZexnh3bZp\n" +
                "ywGnSGkJbsqtioTGIL3AU3SIbqNjluyMr9NLyRT062cnyzVvgUFUUgaKjLK7nFfJrJWzGPqzN+xt\n" +
                "YUvcFyJspIgU3SJTpDlyTkdRAhbzb8GhEVsvkpgXBBIfESWVCfBnjXc3L32DK6j+R404xSZFaJbo\n" +
                "J+xUeCcrSjfkqISbkOfsOCajuLlkUR9vYYVSCtgBhJ0n8BdGWodY/lgd2kfdpsb90amWlj2abGvZ\n" +
                "GyOrHjny6BiGaQqAKGVCYDEQABQMoQpR7k0yhEHldE6LpujxzR0kVGOR3SR6lDAo58GlxYJTeKCi\n" +
                "5u3RsyhMDzsfStNj6lPWwglmnyzvGACENiARGXHHLjlCaTjJU0/JoJgcuLRYMWOMI441FUrRvGKg\n" +
                "qikl8FMAJaPNn0nB3uULhbulwem1uJkHPptPDTw7YLnl+5q1ZQqA4pdP08pNuHO/Jpg0uLA28ce1\n" +
                "s6KAbCZGSCnFxkrTNBPgKxx4MeL7IRX9jShoAiWKihBSAYFRLVgMTAmXIFCAliKYmBImUJgQ0Sym\n" +
                "JgQ0J8FMlgSyGWyZIK8XrVqWP8kRfbgv4Nus84/yck5elR9zm9eujz4NPHuk5M6nwRhgowopsxGq\n" +
                "kzSt2aT4FFbBEyMMrpX7Gz5Zy6mXpaXJKsZw3bZqlSoeKFY02XQhazZhqZdsfzsdLRyz9eRJ8BSh\n" +
                "GsaRajSsuldBJVEsZfeJ22Q/VNIuK9NkxfqCLexnJmjZnP2QqtMSqJOZ7Gq2iYz3mghpUhSexT5M\n" +
                "5btIguOyFLeQLYEUUnaIf3FE8kFLZD8gJvcotPayFuypOopImHIWCXDHFemxSe5UvtIEuAjtYA9k\n" +
                "Bpj3tiyypFQ2ijKfqmkVIceELI9qKE13NEUL0wNIcWZz3molu+AKgU2C2iS+SopITdBZEnsA1xZl\n" +
                "H1Za8I1bqLM9Pv3S9yK38ETdQY2ycu8aKJwL0t+WWxxVJJeBMgT3ZTdIUQl7BEozm+6deEXN9sSM\n" +
                "a9NvlhWkEmPyC2Q0A3vSJGLwUZSXdkS9tzT2FFbtmkeSFOqic8335YrwjbJJRi2ZYY/1PyKRpPZE\n" +
                "xKluxeWA2wuoguSM3KS8sBrdX7mc1354RrZcmz9MfwZ6VXKc35YV3Yv5cUaIzxbwTNEdWPHNl0D8\n" +
                "gBpkAAAAAAAJjACQGBQhgBAvIhgAhDEACGBRIihMBCGBBLAbEFJiGwCJENgFIRQmESluFDGUSxMo\n" +
                "RFITGmm3TAqJAbAKQhgBNEstkhEtCZVCYEvgllMTIIZDNGSyiGS0WyWB4/Wdv038nDhTyTXwdvXH\n" +
                "UYt8mGhj/DvhnP6ddHn9Rs1SFRpJbkyex5tMnuweyZSRMwrOXBxz9eWvB1ZH2xsw00O6bkReNuIp\n" +
                "BWxfar3FJUVlhnlUGY4FcLlyPM3KXajVxpJIjSYrmyXuzSW0SYK92VH3a2iTH7hzdKyca2thmLbI\n" +
                "iryDfJWHlsK0dJGK3bZrJ8mb2TYqEnyKKuVjS9ARVRsA8jfAmN8AKXAojl4BEU73FHeYvcrGvJUG\n" +
                "TkI/b+SZbyKeyoilHeX4Bu5JDgtmxRVyb9gLYpbtIaW4QVybAue0TKG8mysr8ChwUUyoL02ZvdpF\n" +
                "zfbjIIx7ybNI7yM8e0b9zXH7gqmJ7g3yCZUDM5O8lFydGWN22yKeZ1B/Oxphj240jGXrmom97UIU\n" +
                "UDW40De5dIUmJvYPIMimhVbBugi6TAxzbyUS4LdL2Ih6pOT9zWKpNhRzdFJbCiqQ63KiZc0KQ2HJ\n" +
                "BPCNY8GXOVJcG74LErn1G7UV5NEu1JGeNd+Ry8I1k/BFT4b9gS2BrwNALzRlH1ZZPwuDWb7YuXsZ\n" +
                "4VUL93YDzOsfyzXFHtxJGT9WaK8I3kyjfDvjX5ZaM9P/AC/7s0R0Y8c+XTAANsgAoAAAYAAAAAJj\n" +
                "BgIAABMGFgAgAAEAxMBUc2u1UNJiWTJ9vco/5Ok8r6ixxzdPljmnu00/ZrcsDl1SDTpb/kro+py6\n" +
                "nDkeaHbKM2l8rwfIY9c/1dNFpVkVceT6joGfLkhmx5odrhKk15RdJt36/P8Au2ky5qvsi5NHiT+o\n" +
                "MayScXcHhWWLPb1yUtLlUlcXFpo+Lx6DEpwxZYusmnePninsJFr6LQ9Tnm1sMMoupQ7068HqZp9m\n" +
                "KUn4Vnyv07DP/tJR1G0sMXCL/wCaJ9TnX8GdK9nsQfLz+opLGqXqTkvykzbpfWsmq10MLjs7/wCz\n" +
                "/wD+Hz+owzemnKGOSccrVV7nd9L4p/vEXkjKOTHN8rwy/SSvtnweH1DqUsOuxRjF9u8ZHueD5vrc\n" +
                "si12KCxX3Nrur4JFrml1jM9P+tCVxWTse/G9WfT6aUp4IOVW1ufB6jBlh2yljksazRc0t1Te597p\n" +
                "ILHghFNtJFqRz9Q1EtNBSUbj5PC0vWskJZVlrt7n27eD3+oTlDF6Y9yezPlMMM2b95j2yTUmouuB\n" +
                "Fr1+g62Wr1Os39MZpJf2PcPnvpPF/ByZ2pRnkfrjLxJbM+iolIlhQwaCpEWKgiGKiqEFTQmW0SES\n" +
                "0Sy2iWFQ9iWWyWEQ0S0WyZILHidcjf6a+R6dViW3gvrCuWP8ixfakc/p174cElvZlLk2yOo2YpW7\n" +
                "PNs2tjOexukZZV/gJHHnd+lGuOHbjVcmeOHflfsdLW1IRbUIzzS7Ys2SpbnJqXuoryKRnhVtyN0T\n" +
                "jh2wLkqXySDN7yKjGgirZUtossK+yzbKilGoomauaRctkGWcnRriVQMpbvY2l6YhaV3ZE+Ugi9hL\n" +
                "eQRV+Bye1GafdJlcgJvehyElcgk7kBL3kkW3RMV6rCT3Ipy2RS2gQ93RU3wiolfcVJih5F5Iq26i\n" +
                "GPi/cmXBpFUkA3wVHZEjbqLKjLI7kaVRnD1T/BpJ7EVnDfJ+B53ukPAvuYpb5PwBb2RotomUVbNJ\n" +
                "uo0VCGiUU3QE5XUWZwXpHld0gvYijCrm2ak41USluyh8IRUuCX7AHgI/JL5KRBMuScjpV7lL7rJk\n" +
                "rmvZAEVSSNWqSSIxq5GiVzvwhCihMtkeSgaomTpWXLdGGo3SiiC9OnTk/JeZ1F7l441FL2RGVd84\n" +
                "oqfssMe2Acy3LltVGeR1F+7CldtsqxV6V7gkQRqJelRXll12ql4Mo+vO/aJs/YBYVzJ8mjVglSGk\n" +
                "Ua4P5b/JquDPT743/wBRpR0Y8c+XQMANskAwAQBwMBAAAAMAAQAwAQMBMoAACAYhsVFAcXU8+LBg\n" +
                "U9Qk8bkou/F7HaeP9VYnl6HqopNtJNJfDsQedqI6L9XTYY44VHN+m9ls+T6LFjxwXoil+D4l6LKt\n" +
                "dBzf3apb+/p2Z9X03DnwZM0c0+7G2nB+3wWpHdNJpp8Hzmo6hpFqYJ4490JSivyuT6DUwlPDKMXU\n" +
                "q2Z+eTx5o6x96alHVyv422EWvp9H1HHk1+lUYL9PPCThJLhrwe5k2g38HzH0zpXlx4Mk9nhlLZ+L\n" +
                "Pps9rHJxW9Eo+VXVsUnkU8bjJunt5TOrpGujLXzxzjTkk4y9z5frUp/vmr7ISi01Kqf9z0fpeCn1\n" +
                "VxyX9ikrs0m33Hg+b6x1GWHVzxvG5RxtO14s+lrY+Q69Gf7/AKpRi7lCNOuTMKqHUHqG8WPHcnG6\n" +
                "rk+m0c/1NNCTi4tpWn4Piumd2n6jhyZcUlGvuPucbUoJx4ZaRy9R1KwYnfLWz+T5bT9clNLJ2Npy\n" +
                "7Z7n0nWoRnpu2cO72+D4rSaKSyaqUMapybX5EK+p6BllLJqsbqo5LTXlNWe2eL9MY4S0cc8VTmla\n" +
                "fho9vwSrCEUFBUiKoVBEtCooQVFCotksglkstiZRDIZoyWgjNks0ZLCvH6vzCycH2GnVl6oEYlUT\n" +
                "n9OvfDhZuKRGOO25Vd0mWlsebaH6UYZbo3fJnONy+AjPDDtjflleS5bbCrayiMrpM4o+vI34Rtqp\n" +
                "7UuRYYpQTrdmasCX+BT5NJbEVciggmRl32Oiu1GNKUwPsoO8jfsObM8T9N+5Ugh41cy8z8CxeWRk\n" +
                "3yJAWuCJbJlpmWV20iIrGvTZSdC4ikD2QAnyxD/pQgBOib9Q3wSgKh99+wSe4R4YLcKa+0I8tkt8\n" +
                "jh9oDd96RrZlDls0KiiMr9O4JmeaVuiLF4Fs2Vl2QR2SRE7k0gNcfpxkLy/ceR1FJAlaAvGtwnvN\n" +
                "IcdkZRdybKjTyD5CxN7sio3lkfwUkKBYFLYFtuD9ht7FQluJ8sadEzZFC5KkyUS3cqAtfaJ+mIN0\n" +
                "TJ21EDTCqi5eS47IOIpAmVA+BVsNik/SwRLe7RFd2S/YUL7bfk1wra2RWvBCVzLbolPZsqJlyYzf\n" +
                "dkXsjST7YtkYd05PyRVvkU32pv2LMdSnSivLANNF9rlLlm1JsUV2xSHwBVkzl2xBOt2Z5PU9vco6\n" +
                "NBf6DvnuZ0mWn+2X5NTow48MugAGbYIOBiAAYMQAA6BgIGAAIGMQAIYAIBiABDEyhMjJCM4OMlcW\n" +
                "qaNAog5smlxTUbjTi1JP8G1FABNHLk0OCc3OWNdzds7GIDnwabHhb/Tj23yatFABx5tBgzZHLJBN\n" +
                "vZixaDBjmpwjUlwztAbEmOXT48krnBNm4Acv7ng//jRvCChFKKpLgugaKM5xUlUkmjKOlwxb7YJW\n" +
                "7OgRBliwwxJqEUk3exZTFQCoRVCYUhFUIImhFCZVQwZRLIJJLYmBDRLLZLKiGSy2S0B5XVdpQZnH\n" +
                "bH/Y06r90DnbtJHN6ddGHDguSn7FpVEmtzDaGgrYutxMIxfIsj7YWaS5OPWZNu1cg6xiv1Mm/B0p\n" +
                "eCNPDtgm+TWvJI1WWTmiscRNeo0SpFRGTgmEdn7lTVsJbRoI+qgqSHIaJ5kRI0htEzjvNsubqJOJ\n" +
                "bWVVMy+7IaTdIzxLdsiNHyJvegZEd5gaMljZLYWFN0NLYl7yRbdKgE3Q0/RZE2Oe0AJvY0WyMkt0\n" +
                "jagU0qQ7F4EEVfLMku7Ki3tGxYFcmwrbwTFb2UyZ+mLAznLumvY3itjmxbuzpgIUZH2omKqKJzO5\n" +
                "qKNComTFyS3uC5Iq0XH5IRfCAfLYcguAsIXkTdyob9yIb7hVt0rIxb+piyPavcuNRgAn9wY43O/Y\n" +
                "mO7bNYbREVfMrH4JQyspk6Jm9kvLE/Vl/BTXrsihR8eDaCSREEt7NCxKmbJm9hszu2RYjUP0dvlm\n" +
                "kI1BL4MW+7UJeEdNApST2Igu7I5PhF5HUWx4lWPcBT5FQpDQROV9sB448e5E/XljHxyzaCp/gK6c\n" +
                "K2aLqjLTS7lN/Jqzpw48MugYIPJtgAAAAhiAAAADkQwYCAYAIAAAEMEAhUUICQGAE0MAAQDCgEIq\n" +
                "hBSoRQgFQUMAJGMAJYqKaF5AQqKfIgExMoVblEgUIglrcVDaAIklltEsqpJotolgSyGjQmiDNoTV\n" +
                "ItomQHk9VddtnJgXdI6etcRI0sKxpvyjn9Ovfz4t8UCQ69Q6MNoZLLnwZ+AIyPti2eel+pnd7rwd\n" +
                "WqlUWiNNCod3kitGktkKT9Jole5EkVEQjuW1sOEdrFNgZxW+5GR/4NWvSZNWEfWhj5JfBWPgiQsr\n" +
                "8FLZEP1ZPwVN0VU5H6QhtEUt6RXgiFYQ2tifI7pBTbIu5BJ0iYeWBcXuJu5B4smPIFczJyv1JexU\n" +
                "OW2Z8ybAvHvJs2Msa2NEIKIm6KTIb7si+AHkdRLwqoGU95JGt0qQDvcy1MtqL8GM3c69gRpgT7Uj\n" +
                "oiu2LMYcIvJLtgWFRH1ZGy5faRiVRvyVN8Iglc7jiJrYcQKRXJKKQQ2xIXLDyFTmdRryVBVBIwb7\n" +
                "s1exu3RFQ13ZPwVk+2icFu37jlu9ihwWyNEQkaRESnYpMnI90kJ/awFi8t+WXJ1GwSSSRL9U1FAb\n" +
                "Yl6VZUtkOCM5W5FQTdIhuoWE95GeeVeleXRGoemVpyfLN1yRjXakixEJq3QSdIrgzm7f4KBBQrVV\n" +
                "5Ypuo/kgWBXOU/7G8toOiIR7YpIbduvYDfSbRl+Tcw03EjY6cOPDPpgIfBtgAxWAAAedgAAAGFAr\n" +
                "GAQCGIAAAAAAAoExiATAYAIAAIAAYVIDCgEADAkBgAgGFASIoTAQqKEUIBiaAQDAghoVUWJgQxNF\n" +
                "UKtgIaJZbQqKIYi2iWgIZDRqyGiK8frEe6WNfI8SrGkX1NXOAkmoo8PTr2w4mtx1tuXFWiZ7Hm2y\n" +
                "ktyZbGiVHPqn2wYHFkvJmSXFnWoqMEjLTY/62bLeRItKqRNblz24CC23KyOEYreRtL2ISoozmzOr\n" +
                "3Lyby2CMfcivpJexpH7TNbyNJOo2GSi92TJ3NBD7W/cUN5NhQ36im96Mk7yFcsgrkb2CK2smTAme\n" +
                "4cRJvcpgNv0gtohL2B7pIBrbH+SUvTY58JDWyCnBbFkpjbKht0jPH5YZZUgiqgiAT7sn4NG9jPEt\n" +
                "2ysj9IDi9mRH7ioKsf5CK3sDVGWoldRXk0XBjfdmv2BGsOAauQoscOQKa2EPkXkCvBUVsTYXsVDJ\n" +
                "k/I+NjPK9iNJwr1NmmW1HbzsEFUEDXdJEGkY9uMFHyEn4GmVDS2HwS36khydRbAmPqcmN+BYtlv5\n" +
                "G3uAN7Nhp1bcn5JyP0teWb4lUF+AKbpbGbl2xbKyPdUYZXcowX9wRcL2bMfv1fwjfhP2RnpY3c/L\n" +
                "CtfNFPkS3kXIrKZvYhDnutiJvtxOX9iKUd5N/wCAW+WvCHjVY1fJWNcv3YFraLsiHLb8lZXskgig\n" +
                "N9N/UjYw0z3l/Y3Ojz48M+mDF5Gz0ZJKgAAgAAKAAAgAYAwEAAAAAAAAAAAAFIADyAgGAB5AAAQx\n" +
                "DAQAAAIpiAAAGAhDACfIUNgAgYAwFQmMKAQhsTARLLJYEtEstiKIYimJ8AQ0Sy6FJeAPL6h/MiRD\n" +
                "dGnUvugTjW1HP6de+HBVETRs9iGebTOSpHDqH3y7TuyyqLOPEu7K5PyKsUo9sEhqNGnbbJns6AhL\n" +
                "uY5bclx/BGRu68AZ1bCfOxdUkyMnJRk1b4LUaQJWOfAjNe9j+4eVugx8WTldziiLFPaCSJW0bKIz\n" +
                "y7Y0gFi4b9y/BMFUEgk6pEGnCM8j2LvYyyv1RQIEuBt+pIdMi7yUvAGnIo25sfEQhtFsBN3P8A+R\n" +
                "R8lRAuIeQ8CT2sozyeqSXya5NomeNd2X8GmTfYBY9oiyPwNGbd5KINpOkkSmJvcuKAbdQbMsW6b9\n" +
                "x6iVRSHFdsEgKRaRC4LAOBoT5H4KAE7AFsQD8mMn3ZFE0k9mzLCrk5EV0+AguWS3sVdIqDyNIS4F\n" +
                "KVIAi7ySa8F5d0kRhVRsqW8gHHgGCGwIjHunvwjdPYnGtinsVNou5fgxh6sspGmSXbjb8k4FUE35\n" +
                "3I0eV1jaXLNMUe3Gl7EKPdJfBpJ8IIceWxvgSE3syhfJlm9TjFe+5rwY4vVmlIitl7FcE8DyyUYu\n" +
                "wibuX4KXBGNdsLfkcpbWgNdHLulk/sdRy6GNRk/c6zo8+PDPpAAHowAAAAAAABgwABDCwEAxAFAM\n" +
                "AEAAAAAAAMQ2AhoQyhAMXwRSGABAIYUUAhgQJAwBgIQ2AUCAYCAAATENiATAbEwExMYiiRFMTCJf\n" +
                "ImMTCpJZbJYHmdQ/mQsILb5DqX8yIoqoo5/Tr2w4G7e4NDirJnstjzbc+ZbUjPHGlxuzokrEo07C\n" +
                "o4RP3OzTKTjjsAmkjOrZeTimGNbWUJ7bMyrfc1yU2Z1cqICK7bMNVKkdD2Rx6vdUuSo+nW0UZ1c7\n" +
                "Lm6iTDizKRSZjlfdNI1swXqyt+ArbgS3kPwTB8sCr3M0ryrcpPZsWLlsDTI6Rli3bY8stisSqCAf\n" +
                "IZHUaKM8gCi/BqlsZYo3ubAqZuog3UETLecUVmdRCFp190jSXuLGqxocuCiJPYjGt3IrI6RS2giK\n" +
                "XLLiyEUtotgrKb78yRr8GWFW3I1RFUuAQIbKgG92qJT3GmA7G+BDYGWV+mvJeKNRM2u6a+DZMiiv\n" +
                "Uhye6BeWTDdtlGiIyO2kilwKKTnbCNFtFEtjk/Bmt8lLhFI1i6Q2Ktxx3ILithTfgb2RMd3b8FRh\n" +
                "n9UlBG0dkkZQ9edtcI3SIppUiIvvyN+EOcu2DDBHthv53KLWxL5KZAEZHUGGBVjt8sjK7korybpJ\n" +
                "UvYgZlm9U4xRtVmGF905T8AXJ3siJ7uMV5L5kEI3Ny9tgOnT8tLijc59K+5yfg6LOjz48M+gADye\n" +
                "jBDAQAAAAAAFAJjEwGIYiAsAAoABgAAAAAAHkBDAAAPIAQJgAAMAEFDAAfAAJjACQAAAGAAIBiAB\n" +
                "DFQCYhsRQhMolgJiGxMBMllCYEkyLIlwQebr/wCZEmLtUVr/AOZEMS2s8PTr3w4fHJD+OC5si9jz\n" +
                "aR5HKor8glb3Izuo0BlzOjWqQsUb3YTqyyCGu5lpdqKitiZO1QGUvIkvI29wey2Ayyy35OGX8XLt\n" +
                "4OjO6QtNBU21uT9rePezcFLgzb7macEZKTpNkYuG/dhmdQKivSkFU/tIltA0ZjldySIK/oHBVEK8\n" +
                "DlsgMsm8kjo4VHPjXdm/B0MpSTMsr4Xllma9eX8AjeKpA3sAp7IImNubfsKXqkkVH0wv3CC3sK14\n" +
                "VE2JStgVE5PVNRKlykTDebkPH6pt+xFOWyDLKsdBlfqS9yc3qaQBjXoSNEqQoLcugDwKQ29yMj2/\n" +
                "IBHaLfuUlsKuEXIBIGxWD4AUFUb8jx202/JM3soryaxXC8BSnLtgPGqivkjJ6pqKNPIRXgaWwh8I\n" +
                "oUuRYlvJijuy+CBlRVELkqUu2Db5KhTlcgnLtxtmcFtuLPK40vOxFPS/Zb5bOlcGUI9sUvZGjaUQ\n" +
                "IyLukof3NOFRniVy7maMqUmyXwN+xGV1FkVngXfklP2Zv5M9PHsx377mjdsCc0+3G65JxR7YfL3F\n" +
                "lXfljFcLk1mwEl5Kk+yFeWEeEZy9eb4iB06SNKvg6DHAqm/wbM6PPjwz6QxMD0YOwENADENgAgAG\n" +
                "UAABACGBQhiAAGIYAAAQACGwEAAAAAAIYAAAABSsBiYQAABSYh8sQAAAVAFAFkUcCBiZQMQ2SQBJ\n" +
                "QghEsoTKpMkpksBUTJFMmQHma/8AmxHHgWs/nxKT2Of069sOJk9zNvekW3S3JXJ5vQN9q3Mb7pF5\n" +
                "ntQsUWt2EaNpRMeZWXkfhBjW263KhvZbGM3s7LyPejGUt6Yqw4LuFNlpdsTLJ9r9wrmzNyyJG6Xb\n" +
                "GkjPDHvk2+ToXO4kS16OP7UUtyftghw3MojNvKMTWJi9834N4hQzFb5b9jXI6i2Z4ltfuKRpEWR0\n" +
                "hozyvwiB6Zcv3NZMWKPbBEN3JlQ5Ok2Tp1dy9yczqBriVQSCtGRPfYtk1c/wVBKqSHxEXLCb8ECh\n" +
                "w2E32xsfCMsrtpLyFUn2wNsCrHfuYT9jeT7cSr2KVmn3ZfwNreycC3bZpLkgrH5K8kx4GVCkRXdl\n" +
                "XwU3v+BY1yyKtL1Ck9ylsiMmy2AI7sb5ocFSRN7tgEVc78I0ulZEdl+R5ZVGvcBYd5uTNfJEI9sE\n" +
                "i3wA1wTllSSXkaIfqypewGsVSG+AYrBsRW4sz4iuWXExu8zfsBpaSMlHuype25o1aHijTcv7AWvJ\n" +
                "nmfEV5NTLt7s/wAIDaCqCQ/ImLI+3E2VEQl3Tk/7EZd0o+Wx4/TBfO4RXdkvwiK1SqP4C6QN7UZ5\n" +
                "pVD8gTg9U5zLa3+Axx7MaX9ykASfbC/YWlVwbfMic7vtiuWaw9MUvYDfC/4j/Bsc+B3Nv4N7Ojz4\n" +
                "8M+hg+QA9XmYMBEUAAyhAABAAAFFgg/IBAJ8jAKQAADEMKIEDACgAAAAAAAAAgAAAoEDDgBDATKh\n" +
                "cDATIoDyAFAxMbEQAmMRUJiKJCgTGxAJiGyQAllEsIRMkUKQV5es/nRYruqDXbZlQ4ra0c/p174c\n" +
                "TLdgtkOre/JGZtLY82kSTlM1aSjTFiW1sMsr2KVnFW2atbbkwW1imyoznT3OfGm8l+DTK9q8l4op\n" +
                "Rt8kUT3Ryai2ko8nVLk54pyyW0FjTHDtxr3KlSVPyUt/wZZHborNelPgtbR/sZveaRc+DAjErbfy\n" +
                "brgzxqolsDPO9qHFVFIl+rIkatBSXDMmrmkav7SMa9dgaydRozgtr9x5XsOqSCMZ25RRujGG+Vv2\n" +
                "N0F2ZS2QqCTCCKBq3YpOoBH7VYGc+TKL7sz9kbZHSZjp1ab9wsbLeSNcitUTjjvfsXyymyhGokpF\n" +
                "5HURQ3QQ0tgb2GZ5XSCjHvFs0iqSROJVBFp+oJTlwZPeaX92XJ7k495SZFi26QlF0EuaLewENbkS\n" +
                "9WRL2NVsm2Rj8sDVCY/ALcBN0hadX3SflizOo0uWaY49sEBXuSgkwAu+2NmGLe2/LL1DrEkuWLEt\n" +
                "kvYDXt2QP2KsV7lQcJv2JwXTk/I5fbXuUlSSApcmWoldQXk1XlnKn3Z2xSNZcJDxrYK3GtiKP6jL\n" +
                "J68qj/y7mrfkzxLubn7gW34H4ElyObqLYGcfVmcvCNmZ4Y+hX5NGBppvuf4Ok59NtL+x0nR58eGf\n" +
                "SSBofAeT0YIQ2twKEMBhEgNiABsQ6ABAAACBggBggEAwEAUDAQAAAAAAAAADABAAB5ALAigTGJgI\n" +
                "YgKhiAGAMQeAABDEwEwoYmAhPgBNhQLyAAJkspksBCkhiYHla7/iIlJbKmLW/wDEIqG6Of069sOE\n" +
                "+NzCSvJSextlaSZGJJuzzbXXajJrukdGThGcVTNIPtWxjJ3bNZvajGeysEYS9WVPwbfBEI+fBa25\n" +
                "IrHM3FfJWNehS8iku6ZUU1xwASdRsySvc0nutiUu2LlYHdB92Y2e7MtOvukaR3ZlGkVsKWxSIyOk\n" +
                "yjPFvkbNTPB9rfuW+SAn7DgqTFyynsgM3vlSKm+ScfMmLI6QUYls2axRC9MEjWO0UIlNEveQ0xrk\n" +
                "ozy8pe5bdbEc5fwU35IOfUuoUXpo1BGWb1TijrgqSDV+oq6iEfcmT32KuolZZ5pXJI0gqRzwfdlb\n" +
                "OjwRTsyyeqaRo2ZxXrbYGnCKitrM5uqRpxAqM5eRwVITKi9iKcd52GR7BDgTVyQBLii0qSQquQN8\n" +
                "IC/A4+5KG9kVGb9WZL2N+EY4V6nI1kFpPkrkl7IMb5ZBORd2RL2Liqg5MhL1X7lZntGC8gWvs/IL\n" +
                "gbWySCqKgfJT2JvcJPdIAyS7cbMdOvTb8sNU7cYLls2hHtjQUl7jk+EHGwuWETldQfyNLtikiZer\n" +
                "NFeErLk7IoXBnlbcoxRp4Iw+vI5PgDaOyQlvIqWyFHkqNMLrLR1pHFj/AJqZ2nv58eOfQwFY2erA\n" +
                "Yg8AEDEMTAGIAAaBCCwAAAAYBYvIDCxAFADEEAAAUAAAAAAAAAwAQMAAAYECYPgBMoAAAAAEAAFi\n" +
                "AGDAQAxAwsBMTG2JsBAAgBiYMQCJZRMgPL1rT1CRcdor3I1kb1KNLqO5z59dGHGGV26NcUfT8mKX\n" +
                "fM6OEYjVRK72K45FF2wkyoiS3ObNd0jok6RzwTlk7vYitK9CoUl6SnyTPkqM0q3KjsmPj8Ey244A\n" +
                "l/daIzT7VS5NY0luc0vXl2A9bEqxI1xomXhGkNomQS2Rz5JWma5XSMqtxX9wsaxVRSE2OyeWQXFb\n" +
                "Ezl4LvYxbtgVBVAme8kiyYq5tgPmSRq2Z41u2WwhxRXgFwTkdQYGePdyZUnSY8aqCIyySgwrLEu/\n" +
                "Nfsdi2Rz6WNRcn5N5P0ssSpW8h5n2wFj23MdRK5JAVp1Ub9zeicaqJVgKQJA+RSdRZBDfdlSN5va\n" +
                "jDArbkzVu2UTMc/TH5FzkoU/VlSIq47R3HwrBkZXUPkC8TtN+4+W37BBVFIG/CKikLI9hxW24NWy\n" +
                "BwXpSGwjsO0UZ5XsvdmiVQMU+/OvZG8nsRUrkyUu/O34iXOXbjbIwRrHfl7gbreipbKwjwLI/TsE\n" +
                "KD2b9xx5bYltGhZH2QCs4+vO5eIm75MtP9lvlmhShvZix7xbJzuoig+2KRA4f1S8tlLkT2VIaAWW\n" +
                "VQZeGPbBGOT1ZIxR0cIqJk/UOzLHLum/g1fIFY7eRHZHg5MG+VHUe/nx5enT8jsQHq8wuAEAQwEA\n" +
                "AAAACGAAFisAAAAAAQWA7AACgACwABAAAAACBiBgAAcut1mPSQ7sjoDqsLPFwdf0uXKod1My6p9R\n" +
                "aXQ/pynNOEnTfsNG3v2KziwdR02XHGcMiqStGsdVhlxNDQ6AM55FGDlykrMNHrcWrUv0pX2umB1A\n" +
                "JkqcX5QVQWT3bjCCwEAAxAwAQmNisBAArAGSNiAQpcD8ky4A8zVfz0GSXppi1G+pozyO2kc3p10Y\n" +
                "ca4Y+5bduvIoUooSXqsy0fH5E2mOb2Vk0EZZnUWiMXpW/kqauVcopxoKF/2Ia+SpOthcoqE1SM7t\n" +
                "mk3tRKXbu+AIyOobEYoV6mOVTybcG3alHwCu5byNHwRj2RT4ZkZZXdL3KS8ktd2RfG5oRU8BFbgy\n" +
                "4rYInI6iyILhhld7IuqigqXyHEWUl5E1bSAeP7SlyN7IIreyosyy22kamXMrFIOEYZN2kbz+0iEb\n" +
                "yL2RFbQj2wSFN+DRvYwu5lSLukc0X35n8G+V9sGZ6WOzb8kWOhcUDAGyoEZ5pbUW2YX35EvBFb41\n" +
                "UEgurBug5QBB1FyfknC+6bkLNKoUisMe2C+QNbM36sqRaFBeptlRbdIiO7DK6jQQVRCtEwb3EnSF\n" +
                "HdhGl7GeWXbB+5TZhlblNRRFjXTRqFvllv7iltEmL3bAzzu5RgvJslwjnxvvyym/Gx0xQFE8yCxo\n" +
                "qCX3Ix1Eu6SijW922Yx9WWwrZKkkPyIHKothGWR92RLwV/X8IzhvJyNaIobspukQt2GSXoaAWFd0\n" +
                "3IvNPtQ8Ue2BlN92RIovEu2CvktPcXgUN2Qb4NsiOqzmw/ejpOjz48M+gAQHowAACoAAAAABhQIA\n" +
                "AABisILFKSS3Zz6vV49PBuTVo+T6l9Qylkljwbvwenn5ZZ8efp644TdfWZdZix/dJHNLq2CMqv8A\n" +
                "7nzOnwanUR/V1E2o80cmoipZO2Lbo6Mfj48teGXyL2R9fHrGnb+5f5OjH1DBOVKav8nwsdPfLYTw\n" +
                "ZoPvxZJWvkv+PheVP8nKdj9EjOMl6ZJjs/PNL1rVaadTblXufTdL6/g1VQm+2fyePp8fLD7evn8j\n" +
                "DP6e6MmMk1adoDwdChABFAMGKwBnwv1vqZx6toNPb7Ms0nR9yz8/+u//AIg6U/8A6i/1NY9Zy4+k\n" +
                "xdC0sscW470fCfXHSJ6HNpXHI5YZyqUX+T9Swfyo/g+L/aSn+no6X/zEWX7LPpWj+m5LTQlhzSjF\n" +
                "q0rDUdD1qwzlptVPvStb+Tr1HVJ6bSYsMIPvcFR09A/em8ks7bhPx7DaR5P0f13Ua3T6vR69VnwW\n" +
                "r9yPoScv9odQTlt3nVpuiz0HU9bqFvDMmzj+gv8A3hr7/wCcD7uX2vc+Y1Wqz4s04tvt76/B9NJW\n" +
                "mvc8TU9OyZJ5ov7Z8MzGi6drM2TUrFmVTStPxJHunhaPT5sTxfqxdw2s9tMCrEArALABWAMTAAFY\n" +
                "MBeAEJjYgpEyZREgPH1sq1Fl4k5NSZlr99T2+504Y1jRzZ9e+PFy425HFKhJWDMqWSrIltGhyV/k\n" +
                "UlaoDLGnbs0XAo7bMnNKo7cgJ+qVrkdbEYt1ZUnSKJXNsMkko0Uq4ZEl3SoCYRp2OcqRbVJHPmbi\n" +
                "6KnXsR2QpPYbIyOo2YWFi3lJ+FsVMWH7F8jm9yKUUOUqQLgzzSpALmSNZERj/wBiuWBURQXrbKJg\n" +
                "wi2NLYVbllCk6iZQ3HnlURQ2gvcinLdlY1VkI1WyRULI6iyMPFsnUS4RcFUSDHVS4j7m2JdsEc7/\n" +
                "AImoR1PgRaFyJvcOETZUObqNkYY72TlfCRvBVEik+RoSBuotlRlkbnkUUb8KjnwbylI6CLTBuhoz\n" +
                "yS/yVFcjTElUUgXJFU3SHjdpszyuolxVQRUOUqMtOu7I5f2Fnk1E108e2CIrSbrYjK+3E/kb3ZGR\n" +
                "d8ox8IorDGoR/wAmzexEEW2EFCbC6RMbbAWWVQFgjULfLM8r7sqijoW0UiKDLUSqNLyafJg/Xkb8\n" +
                "IC4KopFt7Ex3Bq3QDvtiFW0Tk3moo1QFN1FmOPzL3KyO9g4pIIpukGK3/cib4+TSHwFb4dsiOlnL\n" +
                "i+9HSdHnx4Z9MLEB6sAYMQQwYWAUAAuQAAABM5dfqo6bBKcnVI6W6R8F9YdTeXP+745bL7j18fO+\n" +
                "mWnl6+k88fyrg6x1fJrM0owk1jv/ACdn0z016rP+rNXFcHznCR+m/TumWn6diVU3G2fQ97PHz1i+\n" +
                "f4y+/pvJw/UeVaTTY8OPZy2PK0em76b3vc6/rL06vRt7Rdo6em408apHPjdecv8A26LN+ln/AExe\n" +
                "i9No5M2KWF2uD6WOJdtM87X412yRnHPdbyw+nznUMEZ4v1sa3XNHlttLui2pLyj3NuzLB8NNHjTi\n" +
                "4rc7/G7mq+b7/wBMtx9B9PfUThkjp9Y9nspH2kJKUU4u0/J+Q51taVM+s+jOuPI1odVL1r7G/Pwc\n" +
                "nyfj6/vi7vjfI/L+uT7MBAfPd4CwBgI+B+vNuu9Lf/jX+p96z4H69/8AfXS//MX+prFnLj7rA/4U\n" +
                "fweNr5aTqepeiy1+rB2k+T2MP8mP4PhLa+vmk/6RIV9BqcmkwarT6bU9qnsoN+T28UYxilFI/Pf2\n" +
                "hwyZOp6GOGXbkbXa/k79PrurY8UVLG20i2fRt9hrK/d8l/8AKz4v6EtdR6h/1m+TqXUckHCWJ7+T\n" +
                "t+lemy0jyZZpqWR27HIna+msBN0rMv3jH/zIy01oNjPHmhNvtlbRoFACCwhMA8isAsAFYAFiYAAh\n" +
                "ksAZMuCiJcBXjamN67c7FvHY587rVWzaHJzZ9e+PFpEyduvPuaXtuZcsyoW6p8omTo0q/hmcn6t/\n" +
                "wAJrss5sj7siRvkajFnPjj3tvhoK1iklRLXqZr4+Sa8lZT/SKEbt+SpexVUijPK+2Nvg54P9SVPd\n" +
                "I01E6SXKfIscVDdeSL+npt7meaVRr3L8mU/XqIx8IysbL0xXwieWVkfgIEAzDJ6skV4OhmGNd2e/\n" +
                "YDZ7CiOQcIBTlSY8f2IyyO2kvLNlXAFxCwXApPYrLLI+7Il4Lk7dERXqbY/NkaaJDbolOhSfpbKj\n" +
                "JerN+DWcqizPTq+6Qs79JFPSxTlKRu2Z6ZduP8l8liFImxyZEtk2RUR9eb8HS9kYadbW/Jq3uAyM\n" +
                "8qjS8jTudET9WVLwBeJVBItC8DSpFShypE13SRE5XNI1xrkC5CSoHyEmlEKyl6sqRs2Y4F3SlJmk\n" +
                "nyQYZH35VE7I7RObTx7srkdM/YsKlEw5b8srwEeSI0TpWJbqycj37UNbIobewlJRi5MUnZjqZVFR\n" +
                "XklWK0/qk5s3bM8Ue2CRQKWSXbBsyw/Zb8j1DtUOKqKQGkRrlsm6FN9uP8gKD7skpGy4M8Uagvct\n" +
                "ukUoe5CdyKm6iRhW1kFNXNfBquDNcmiKjTFtNHUcUZVlgvk7Ue/nx5enQAAerzAAAAAAAAAAAgBg\n" +
                "cnVM60+iy5H4R+VZ5yz6meWTtydn6H9YZHj6Pkr+ppH55FUj6fwcP63J8v53p/aYIbSywvi1Z+t6\n" +
                "SKjpsa9oo/IdTs0fq3RNQtV0rS5k/uxq/wA+SfOn1K18G/djl+p+nvW6B9n83H6onl/T2tjf6Wf0\n" +
                "zWzTPrGr2Z4fU+iwyzebB6Mnwcnn6S4/hk6vTzsy/PF2anNBR9LPI12oTgzDNp9ZjXa22jydZkzY\n" +
                "pVli18nv5+W+V5enrqfcXkyJRl7nDmScWJ5b8kSnfk78MPxj5fpn+eTnmrs4P1Z6bURzYm4zhK0z\n" +
                "umzz9Xy/k3ra+d1X650XXx6j07BqYf1x3Xs/J32fB/s21tw1Okk+H3xX55Puj4fth+Gdj7vll+WM\n" +
                "p+Q8iA8mwz4P9penzQ/c9dhg5xwzTkl8Oz7xmOowY9RiljyxUoy5TLLos28npHW9LrcOJY5K5rZH\n" +
                "zMv/AI+X/SfTYugafBmjkwrtadqi83SMcuo49YlWWKqyyxLK+R/aF+oup6F4d8iaa/J6mn61lWGP\n" +
                "6mB91b7Hp9d6NHX59PnX34ZJo9LHo8Tgu6Cv8DZp4ul69pJZY49TD9Nva5I+hwyhOCljacXw0eb1\n" +
                "LoWk12nljnBJvhrwc/05otV0yM9NmyPJiT9EnzRKr3Juotnzeszxjkydk/S9z6SStV7ng6roiyZZ\n" +
                "SjKk/FiFLouTv1M13u+UfQHg6Dpc9JqYzT2PcXAFEsbEwAQcAFFiYCbCAAsVgDYhibAGTJ7DJkFe\n" +
                "TqJf700dWJelHHm/407oKo0c+XXtjwpCWyDfhjjsjDSclUmiFvK35Hk/7GeR9sQMs0u6dLwXijSR\n" +
                "EFcu57o6KpWItTLnYH6RrfdEyd7FZFKQTklGmEUZah+CkZJd0qZolW3gUItL8lz23JFrrTJwb5Zy\n" +
                "fC2Buotiwqsa92zBGr3K4JitxyYVMnsycCqLb5bB7lcRAHvIc9kSnbYpOwCO80apbkY/LNI8lRbI\n" +
                "ZbexnJgQ9rHHgUhx2pEU5uoizS7cYpO8iRnqm3KMV7gi8a7cSJku6SRp4Fjjc7YGzVRom6Qsj3E9\n" +
                "olQXyRl3VDTBK52RVxXbFBdJsUmZ5pVFLywKxeZDgt2wiu3GkPwBXkqTpMiHJOolUWvcBYF3SlJn\n" +
                "RwjPCu2CRbLEFmeeXpLRhld5IxRFb4l240LJxSK8CStgaYIqMBy5DhbEsqCxp0rJe2xOV1FRXkKr\n" +
                "F6pOTNGxQj2wSFLgIIvezH+Zmvwi3LtiwxqoX55IrS/VQXuZwd2zT+kDDJ935NY+EYr1ZfhGy9wJ\n" +
                "k7yRiiprvnFEYvVJyNYL1tgrQlvcbZDZUZZpcRXlmyVROdevNfhHTL2IoiWiUqQ72Kgg71EfhnoI\n" +
                "87G6zQ+Wege3lx5enTAQz2eYAAAAYAAAAAJiKJYHg/WMe7pEl/4kfBuPsfo31FgebpeZJW0rR+fu\n" +
                "NN2fV+Fl/wAenx/nz/ll/wDTg1Mbj8o+u/Z/1JPFk0OSXqi+6F+z8HzWWCppHFp8+XQ6uGbE6lFn\n" +
                "R6+f8uFxeXh6fx57fswHjdC6zh6jpou0siW6PXT2PiZYXC6r7eOUym4UoRlykzg6n03Fq9PODilK\n" +
                "tn7M9EmXAxyuN3DLGZTVflerhLT58mLJtKDaOf8AU+bPR+r5KPWcjj5SbPFhKz73nfywmVfA9MJh\n" +
                "ncY6Gzh1b9jrlKkzzdVP1Go1i936AyvH19JP7oNH6rdn5H9CK+vQa5UWz9bXB8j5v/kfX+L/AKHY\n" +
                "gA43QAbAQUWAvIAACABoTCxANiCzi6jq3pIxn23G6YR2sRngzLNhjOPElZZVOxAJPcAbEHkAh2Ji\n" +
                "EFMTAAgEHIMAZEnsUTPhhY8nKr1Z1yk1FUcrf+9P3N1uzny698eKhJ+QexXbsRJmFJbnPnm7pbm8\n" +
                "nUTmScp2wrbFFduxpNemkOMe0m99y6ZpR2V+4nG3aY5cbCjsA2vTa5OeSWSd8M2nKl+TKCp34YU1\n" +
                "YpW9vBc9hR8hG2Z+ivdlr29iH9yb4W5WC2m35MNRouRTe9DRnlezAqG6bHP7RQVRihZpUtgFdKyk\n" +
                "rkkZviCNYLlkDltSKx8tmcnu2aQ2iUOTsT5C9yZMIV2xRdzb8IG+2LYsX8py9woxb5JNmb9Wo/Br\n" +
                "DbG37k4V65SA1a2HHZEjntECO68lFTZng3lJ/wCCpbsARUSEyr2Abe5g335kl4NZuoNmWmV3N+QO\n" +
                "iXImJvyCdgaR4MZ+vLFGr4Iwq5tgbrYljbJXJUOW0WzPDG8jk/BeThJCjsq8kVbKxrczvejWHBRT\n" +
                "JBmeWfbCTCQRl3SY4ruy/CM8KqFvybY1Sb8si1oyJew7M07k2VImfqyKKKyuo0uWKC9TkxR9eX4R\n" +
                "FapUkvYnNLti2VfLOfM+7JGP+QKxL02ysku2HyNbUiJ+vLGPjyFa4lUDRcCQ0VBJ0jKcqg2GSXqS\n" +
                "JybqiUVpo+m35NE7bCK7YJDiqQF8ik6CLM8sqi2AoO9TBLw7PVPJ0Sufe+bPWZ7+XHl69AwA9XkA\n" +
                "sTF5KKAAAAAAEwAQEZ4LJjlGXDVH571DSy0+pnBqkmfoh4H1HoVkX60VvxI6vi+v4Zav7cfzPH88\n" +
                "dzsfGzh7HFqcNrg9OcabT8GOSFpn1ZXyXl6bUZtDm78MmqPr+k/VkXFR1OzXlnzGTCmjlngq6M+n\n" +
                "lh6/7PXz98vPj9RxdZ0+SKaktzn6h1vDiwtqSuvB+bL9WK2lIJSyTVTk2vlnNPhYy9dX+ddcadV1\n" +
                "T1usnlvngxgttwUUt9iMmRRTO2fU1HFd5XdLU5UlSPNzTvyPUZW29zGCeSajHdt0kXke3nj/ANvs\n" +
                "v2c6Zz1s8zWy2TP01cHzH0V0/wDdNBFyVSZ9OfD+Rn+edr63jj+OIbFYWB4PQWDFZjl1EMWSEJyS\n" +
                "cuLKrcQrszzZo4o903SINAMZ6jHHEsjkux+So5Izgpxdp7lRdhZljzQyNqMk2vBoAzLPihmg4TSc\n" +
                "WaMQVnhxLFDtjwaWJsAhiATYUMBWFgDE+QYJgAWKwCCwBiCn4Jlwxky4ZB5OT/iZHRj3as48ja1j\n" +
                "rc7YcHPl1748aydGVO78FS90Ca7WzKsM8q2QYUq35I/mTo6IK0FOd9pKaZX+hMtiomL7cj8oppL8\n" +
                "Dile5OSVOvBUZzV7Diri0yZNtDUtvkilJ+Hyi6RKXd+Sv6fksSrzOo0uXsaR9MUjBvuzxX/KdC5P\n" +
                "No1sjKa7pJGjexGLfM78IDRbM58lykl8nTLycy/mIUi+ctexs9omUF62zSbAh+F7m72MI75V8bm0\n" +
                "mVEpEye9FN7bGSdyZFLUOoV7lPbEomWX1ZoxOhK3+AFLaKSFjVIc9wsCo7yI1EqizSPDMMvqnGPu\n" +
                "ypF41240Juy57KiCKEtymJB5sDHUyqNGmNduNIwk+/UL2R0MB+BxJKXABOXbFsenVQt8swzScpxg\n" +
                "jqSpJIAb2FETe4+EUPl2D4sFwRmdR2IKjvubeDPGqSLbKhTdI5c7cpwh87nRN26OfCu/PKXhEqx1\n" +
                "RWyRUtthIHuVE5JdsCca9H5M8r7skYG72RFROXbArDGsd+WYyueRR8HStkApOkYYvVklIucu66DH\n" +
                "HtiFNulbFp13NzIzvZRXk6Mce2CQSm+QbpB5M8z8IpEx9U3IuMbmvgUI9sUjSO25Fpvkc9opCj8k\n" +
                "33ZK8IIu6Rz5W5OjbI9jDCu7I2/BFjrxJR7EvB6B5+P70z0D38uPH0MPIAezyAgGUAAIBiaGJgAM\n" +
                "BECJywWSDjJWmWxFHx3Wumyw5HOC9J4M322pcn6VnwxzQcZq0z5PrHRZRk54lsfS+P8AJl/rk+X8\n" +
                "r4tn9sHzc9+DKSRrnxzxSakmvyck50d8m3zt2dKVexjkkkRkyVw6ObJk33NaXHdaZM1HDnzbPexZ\n" +
                "cnuYwhPLJRjFtsXUdPngz+6XufV/SPRJajURzZI7fIvp76byZsscmaO3tR+k9N0OPSYVGEUqPn/J\n" +
                "+VNfji7/AC8f3XVpsSxY1GKpI1AGfMdsDYgYmQB8b+0XNkw6TTzwzcJqezXg+xZ8T+0r/gtP/wBa\n" +
                "NTqXjb6S+pJ58n+z+p/w9XFelvia+Dp+qtc1BafE/VP2PJ+qOkfr9Ew67S+jW6eKnGS5a9jz/pXW\n" +
                "S69q1kz/AMzFSki6/bL6DV/q4PpjJ3ydqOz9jo+itTk1HQsUssnKVPc5fqTXYf8AZWp0zdZFFqjh\n" +
                "+hOq4IdHWLI6lFtOxpW/RNXl/wD1Pq8Lk3j9j7Oz846XrsWH6vz97VTXpd8n6Fp88M8bxyTRKsrW\n" +
                "bqLPGn1TtyvG77ra/J6894M+Ylg/UzTU16oybjL/ANCD2dDrf1srhJU6tP3O9ni6CH6eoSktmtn8\n" +
                "ns+AGxAAUmAeRBAwATChgAADAGIAZMuGUTLhgeM7eskjt/pOO61jZ2P3XBzZde+PDhuY6mXatjZb\n" +
                "I5MknOdMy0vTrybt9osMe2KCTuQRS4smLuSvhlN1EUEVBNbPw0c9tv3N8r3pnP8AbNLwwsVDafwO\n" +
                "dPdfcMKuVgOMaewpvuTrke6ZnkdW0VF6dOTlJ+XR0cMzwR7YL/JoebRSfItP/VL3ZGaVQfuzXDHt\n" +
                "xJAGR+l/JljV5JP4NJ7uvYnEuX8kqxrFUEvuQ1wS3u/gqDCvXJlvkMW0PyDCInKkyMS9N+48v2Mq\n" +
                "CrGvwFZQXdnk/CN+EZ4Y7N+5o9wIbCBM+UkaJJUA3tExxerK5eEa5nUScMahfllQ5vclDfNgiKOE\n" +
                "Q3tKipujOfpxfLAnTreUjVc2Tjj2Y0mX4IBclNkpbBkdQZRnhXdmcnwjpbM8Ee3H8s0AkadgEVuA\n" +
                "5OiV6qI1MqikuWaYl2xVgbR4E/cd7EydIqM5v0ti06qPywzOoV5ZpjVRRFXYm6TBmeWVY2Bnhfdl\n" +
                "nL+xvkZnp41BXy9wnuwp6dO5SfkrNLth8vYuC7YowyPvzKPhBFqNQXuU9kgfJOSVRbCs4rvz3yon\n" +
                "X4MdPCo2+WasRKRE6uy2ZSfdk7fYUi1bo0fBMVbG36gBvtiKCpNvlkz3lGJpJ0gMsjHiXbBe7Jlv\n" +
                "JG0Vv+CKpPt7T0UeZ92WK8I9M9/J4+hgAj2eRgAFAIYAJgPwIAYmMTIEAAUJkzipKpKymJklHk67\n" +
                "pGHUJulZ87r/AKafdeOz7gmUUz3w+RnhyvDP43nn2PzHP9O6hXVnLL6c1Tlvdfg/VXii+UiXgh/y\n" +
                "o9v83N5z4eEfmmm+ksk5etNn0XTPpjFgacoqz6tY4rwOqPLP5Gefa9sfHHHjn0+mhhglGKRuMR4P\n" +
                "UAwYrCiwACBM+J/aV/wWn/60fbHzn1l0ufUtFBY/uhK6LKleZ1TXX03Dp8b9U4pUfM6KGT6X+pdN\n" +
                "mmq0ur9MtuGz6vT9Fn++Ysk7eNRVJ+DX646R+/8AQpLFG82H1Qo1tNOb666bj1HSsmqxPtyJKSa8\n" +
                "nn/S3RsWs6JizO1kcafydn6+bV/RVZYv9aEO2S/B3fQ6roONNbpOxsfIdC6T+8db1WnzSbljfpkf\n" +
                "fdH0GTQzl63KEvDPnegRr6q1lqj7nwS1ZDMZafG2328mkpKKtma1GNuu5WQKOnhGSa5NiVJPhjCm\n" +
                "IAAGIAAQAAQAIAoYAwsBCnwyiZ8Mg8ZV+9ys7VvVHC1eql+Tux+zOfLr3x4Wd1DbkwxRt2VlfdKv\n" +
                "Y2xxSijLStlEzjdP3G36qG+CspcvBS4szp2Xe1BazyPfkyinOV+ULO33beTbCqSZFEt1Y1ut+SX9\n" +
                "z9iqXK4LENu42vBz5nbTRo3X4M0r3CO2KpFSEht7GFjDIu6UV4s6W0kjCC7p34NMzpEaTj9Xc/kH\n" +
                "skl52KxqoImryr2W4GvDomvRJ+7HfpbKxr0IqGlUUhS4HITW4Gc1dI0a9NIfartg34CJgq/sEils\n" +
                "iZcWBlHfJfsXHeYsaqNvyXjXLEVGZ3svJdUq9gSuab8DkEQMXkb4Cscm7S9wyR7pJCTvJ+DRLe2A\n" +
                "nyPwAPkBrcjJ6pxiaIzxLuyt+wG9UkhPkdk3uA0OPIh3UGwOea/U1KXhHUuTHCu1Ob5ZtD3EKtkS\n" +
                "3kV4bM5PtjJ+xakZv+JnUVwjpo59JF05PybydKyRaGc+d3KMV5Nk7iZQ9WZv2A0WyCK9VjZUQpyf\n" +
                "bFswwR3lJ8svNvUUUtohAubZlk9c4xRbDFG5uQVtFUhN7leDN/cGYJOk2Z4d3KT8jyW0kvJUVSoK\n" +
                "1jsmRfkqWyozyvZL3KKxbycn/YWWVOil6YUjB+rJXsRY2it7LWybZK4QZXsorlhGmmjzJ+WeiuDj\n" +
                "xqopI7Fwe3k8vQIYAezyAkMCgABAAeQYiBiAAEAMAF5F5GxNgAgAAENiABADCkIZPkIABisKBMYg\n" +
                "gODqWsjpEu9bM7zm1ulhqsXbkV07RR5mj6tjzamOOqUvPsdms12LC3jyVuuBYum4cbTUVaJ6j06G\n" +
                "rcJPmOwHL0/Lps7y4IRSUlde55GXWT+ntdLFPG/3SbtSXCPe0nS8enyKcNpI6tbo8OsxOGaCkn7j\n" +
                "Y8vp2PSajVfvmlnFynzTPcXB8Xq+g6rpmb9fpmSXbduHhn1XTs0s2lhLImp1ugRprP8Ah8nh0fG6\n" +
                "7XZIRU4trJDacfg+1ywWTHKD4ao+c1HR5PI21ezi/lAV0DXzz5+3I9mrR9H4PA6ToZabJG07Sqz3\n" +
                "kwGxAAAIYgoAACEFAANAVDYiBkZHsyyMn2sK8RN/vUq9zuk6j8nDGv3ud8Wdj3RzZddGPCxRttvc\n" +
                "34XwyYKlsZylc6IrSvPklvYrhERVsIpcE5GluzTZL4OXPLaikiFHuyPe0joM8EaVvybNCFTW9lJp\n" +
                "Jrwyd+5UEvdAZSV2hpVFrzwU15Jlugldcd02TkdRZcVUUiJbyivkwqscaRObwjV7Rv3MpK8sU/yF\n" +
                "jThfBnj37n7ujSe0GLFGoogeR1jr3NUqikZZFeSMV4NXwUSwW4DoA8GcXbZcvtZnjVIIuRGT7a9x\n" +
                "reb9khPfJFewUT9MUkapVFIyku7IkvG5q+SwJmbdsuT2IIhInI6Ra3Zll3sKWBbNvyaTdRCCqKRO\n" +
                "T1ZIxAuK2EU+DO/4lAX4KxR7Y/LGkUURN0TEnLK50aJbEAipr0pChvIqf+gGWR7xgjdKonPgXflc\n" +
                "2dLEKUnSMc79KivJpN+ozruzfCA2xrtgl7EZnxFcs0XBin3ZW/CKNH6YN/BGCNRb9x5n6UvcuKpV\n" +
                "4IE3uVAzfJqtgJl924pMi+7L8FPewFHe2bQVRIiqpGghSk9iL3Kk6VkY91YpDS9V+xcVuSUtlYDl\n" +
                "uzJevJ8IpuosWJbN+4FZHSMsCu2/I87uPyVBdsUvYKtfcOC78rfhCjsnZpgjUbfksStU6R1r7UcU\n" +
                "mdsftX4PbzeWZ2ADPZ5EMBAHkBiZAhiAoGIYmACACA5EDYgAQxAAhsQUmwYMRUAgABPkAABAwEAA\n" +
                "wABADABAABQ0mtyYwUftVIoQCCkxiAXavYOBgwEAAEAmMTAAFQ2ACfAxBRQMYgEkTk+1lkZfsZCP\n" +
                "CjL/AHia+Tvhukefi31U79z0saXb8o58uvfHhz9MTOC3tlzl3PtFxsZUZOdhxVKyadmnwBE3X4OR\n" +
                "pyyV4NtQ6VeRYY3Gwq0qjQ1ewf6FJekqHHncmXP5G3RK3aRQpOo7+CV9rHNbjSqIR1MWNd02/ZUC\n" +
                "2W48CqN+7swqpvhGcPVlk/bYub+5k6dVjcn5JVhyj3JFxW4/A8W7bYERV5m/ZUXIaVClyEJLcb5H\n" +
                "FA1uUTP7WRF+ix5XsyZ+nHXwRTw7pv5FDecmXjVYl+CcX3UBcFTbBlcEt7lREuRcJj9xZNokVKfp\n" +
                "lIUI3TZTX8NRKqtgbKjLD6sk5/2RWeXbjdcsrFHtxpeeQB7sjEvU2XLaLbJguPkDaPArKeyIm6iV\n" +
                "GUI92Rtms3UXYYY1Azyu5KPuRWmHiydRL0UuWaR2Rlk3mr8FP20wR7YL3NBRVJBLaIELljikm37g\n" +
                "uBoBZJdsGycMahb87k5X3ZIwXBtL0xAhruyX4RUnSCKpCm9wFH7i5OkTBXKxZJbMgWPhvyzTgmKp\n" +
                "JBJ+EBcSmLhIGyiMj2KSpGbfdlS8I1ZFC5JyS9UYloxg+7LJ+FsEVlfCXkviNEPed+w2+AFJW0W+\n" +
                "KEluNbsB1dI3iqRnD7i8kqi2VKlbyZ3x+1Hnw4v3PQh9i/B6+XXn6KAAPd4mJhyAUCAAgsGDAKQm\n" +
                "MTAGKwYAIACwFYAACYhvkQAJgDAQhiYAJgwYAIAAAYBYUgAAEAwAkAAIBD8CAAYAACGFBSoAAIQD\n" +
                "AKQB4AAAAQQiM32M0M832MLHg4d9TNfJ6S2iedg31U2vc7JSd7HNl17xePd9xU1cgSpKuCo+5lQ6\n" +
                "8iyO0mhz4MMsqg/koycv1MlNnQtlRhgj6rZ0VbEUJbDW0WhtbWiWyslyLh37Fdt8EuXAUSpu15E3\n" +
                "S34KqkZ5ZVFgrryOof8AY04SS8Ixyb5IR+TaG7bMDPPtjr3NIqoJLwjPJ6skYmz2IqHsjXHGoIzl\n" +
                "4XuzcsQmtjKT3NZPYzStikVwkLyOREnRFZ5d5RXux5VeyG1/Ej8FKNu2A3tChYo1bLe4VQQmTIoi\n" +
                "XIVN+pITXdJBjfd3S/sXFbtgD5JfI2yXyU0yy+rLGPjk3ZliVzlI15YGOZ24xReNb/CCSSbkysaq\n" +
                "H5IKbM8u7S9y0TFd2X8FRpVRMI+rM37G+R0mYYuL9wrUzx+vM/ZGk3UWRpo9qcnywOgzm7dF8kLe\n" +
                "QQ62C6QNkytoBYY3KU2XPd0JPtSiD9wKMpv1FraJnj9WR3wFbQVQ3M36sqXhGk3VIjHzJgU2Ed5W\n" +
                "TIuPCAqRLdJv2G3uZ5XUH8ggw73J+TXyRBVBFKwCb7YNk4Y1AM29RNFwBlkdbe448ky3yJexolwQ\n" +
                "PwOK2sRXwEPHy2Tld0i26RnfdOy1Y04R34v5cfwee2d+F/w4/g9fLrz9FgAHu8AADAQAAUCAAhMA\n" +
                "YBSfIMBAAhiABAACYhsAEwAQAxDEAgYMAEHICAAATAYrAAoAGKwgCwCwoALBBAIbEFAAAQvIAwAA\n" +
                "AGACHyACAYmFBnm+yRozPN9jfwB4OJ9ubI/k7cC7m2zzoyvPNfJ6mDbH8nNeuicaL2HsSuSnuQRP\n" +
                "k5sr7pqH+DoyOov3OfGu9pvwRY2xx7Y0aRRMV6qLrcqUPYiVWmuBy5J8sqHZL3e/5DgPkKpukn4O\n" +
                "bJvJpG036GiMcb55COmPq1Dfsjojsmc+m375Pyzok6RhUQV5HI0ZMBthSjvlS9tzZOjDBu5z93Rs\n" +
                "2EqZsUR37kxfqohFTdJsylvOC/uGZ7xS8j/+bfsgspveZrwiI7tsc3tRUCAbJboB2ZzdQbG3sTLd\n" +
                "JEWHjj244ob9homT8hNpl934JXkcnUW/ccF6UFEF2wGnsKb3Jb7UELLK2orybcROfG+7M/g3b2KD\n" +
                "hBgXpcvcU36aNI+mCQGeeVRryxJUyMj7syXsaoilPdFR2ikDQ0tyob2iTHZFS9iXwAeQF4M882oU\n" +
                "vIDhLulKX9i3wiILtjFGjIJm9h4Y0rJluy21GFhUN903XgtKlROBelyfkIyu2yobLRCe41uQHl/I\n" +
                "pK2gTuRXkKb8Ioi9yr2KhVeRt+Cm0kStiHK06CjH6ptmhONdsRvgga3ZokRBFt0i6TackqiyYfbZ\n" +
                "Od7Je5a2SCtIq2dsPsRxQOzG/Qj18uvL0aIZKYWe7yUILEQUxCsCgABWNmgAgsgAAXkbAw8AS2Nh\n" +
                "sBWF7AD5EwAbCBgIbAArAAbEArG1MTBisGjEMQ2AAYWAgoL3AbASUIbCCyhUNgsAoQ2AYgAYeRWF\n" +
                "7jYYgCxs0ACwbLsAgsLAbMc/8uRq3sY6l/wpfglqx89pvXqJ/DPVgtqPL0arLN+Gz1cVtbHNXvON\n" +
                "K2TG1RN3sgyS9IGWeS4DDCosz/mb+3JunSRFCVsqQBJmmUT5Ewbt2K9yKK3sd1yCdPfhhOn+SjNq\n" +
                "5fBoo1wEUOUt79wlf//Z";
        bodys.put("img",aaa);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
