package com.tools.timezone.net

import com.google.gson.Gson
import io.reactivex.Observable

object RetrofitBuilder {
    fun getFakeDataList(): Observable<Response> {
        return Observable.create {
            val data = Gson().fromJson(FAKE_INFO, Response::class.java)
            it.onNext(data)
        }
    }

    private const val FAKE_INFO = """
{
   "code": "200",
   "list": [
      {
         "id": 0,
         "name": "GMT-12:00",
         "zone": -12,
         "cities": [],
         "followed": false
      },
      {
         "id": 1,
         "name": "GMT-11:00",
         "zone": -11,
         "cities": [
            "中途岛",
            "萨摩亚群岛"
         ],
         "followed": false
      },
      {
         "id": 2,
         "name": "GMT-10:00",
         "zone": -10,
         "cities": ["夏威夷"],
         "followed": false
      },
      {
         "id": 3,
         "name": "GMT-9:00",
         "zone": -9,
         "cities": ["阿拉斯加"],
         "followed": false
      },
      {
         "id": 4,
         "name": "GMT-8:00",
         "zone": -8,
         "cities": ["太平洋"],
         "followed": false
      },
      {
         "id": 5,
         "name": "GMT-7:00",
         "zone": -7,
         "cities": ["美国山地", "加拿大山地"],
         "followed": false
      },
      {
         "id": 6,
         "name": "GMT-6:00",
         "zone": 0,
         "cities": ["美国中部", "加拿大中部"],
         "followed": false
      },
      {
         "id": 7,
         "name": "GMT-5:00",
         "zone": -5,
         "cities": ["波哥大","利马","基多"],
         "followed": false
      },
      {
         "id": 8,
         "name": "GMT-4:00",
         "zone": -4,
         "cities": ["加拉加斯","拉巴斯"],
         "followed": false
      },
      {
         "id": 9,
         "name": "GMT-3:00",
         "zone": -3,
         "cities": ["巴西利亚"],
         "followed": false
      },
      {
         "id": 10,
         "name": "GMT-2:00",
         "zone": -2,
         "cities": ["中大西洋"],
         "followed": false
      },
      {
         "id": 11,
         "name": "GMT-1:00",
         "zone": -1,
         "cities": ["佛得角群岛"],
         "followed": false
      },
      {
         "id": 12,
         "name": "GMT",
         "zone": 0,
         "cities": ["都柏林","爱丁堡","伦敦","里斯本"],
         "followed": false
      },
      {
         "id": 13,
         "name": "GMT+1:00",
         "zone": 1,
         "cities": ["阿姆斯特丹","柏林","伯尔尼","罗马","斯德哥尔摩"],
         "followed": false
      },
      {
         "id": 14,
         "name": "GMT+2:00",
         "zone": 2,
         "cities": ["哈拉雷","比勒陀利亚"],
         "followed": false
      },
      {
         "id": 15,
         "name": "GMT+3:00",
         "zone": 3,
         "cities": ["莫斯科","圣彼得堡","伏尔加格勒"],
         "followed": false
      },
      {
         "id": 16,
         "name": "GMT+4:00",
         "zone": 4,
         "cities": ["巴库","第比利斯","埃里温"],
         "followed": false
      },
      {
         "id": 17,
         "name": "GMT+5:00",
         "zone": 5,
         "cities": ["叶卡捷林保"],
         "followed": false
      },
      {
         "id": 18,
         "name": "GMT+6:00",
         "zone": 6,
         "cities": ["努尔苏丹","达卡"],
         "followed": false
      },
      {
         "id": 19,
         "name": "GMT+7:00",
         "zone": 7,
         "cities": ["曼谷","河内","雅加达"],
         "followed": false
      },
      {
         "id": 20,
         "name": "GMT+8:00",
         "zone": 8,
         "cities": ["北京","重庆","香港","乌鲁木齐"],
         "followed": false
      },
      {
         "id": 21,
         "name": "GMT+9:00",
         "zone": 9,
         "cities": ["首尔"],
         "followed": false
      },
      {
         "id": 22,
         "name": "GMT+10:00",
         "zone": 10,
         "cities": ["堪培拉","墨尔本","悉尼"],
         "followed": false
      },
      {
         "id": 23,
         "name": "GMT+11:00",
         "zone": 11,
         "cities": ["马加丹","索罗门群岛","新喀里多尼亚"],
         "followed": false
      },
      {
         "id": 24,
         "name": "GMT+12:00",
         "zone": 12,
         "cities": ["奥克兰","惠灵顿"],
         "followed": false
      }
   ]
}
    """
}