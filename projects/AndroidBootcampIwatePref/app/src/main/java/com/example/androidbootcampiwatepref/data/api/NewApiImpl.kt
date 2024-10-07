package com.example.androidbootcampiwatepref.data.api

import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class NewApiImpl : NewApi {
    override suspend fun getNews(): NewsResponse {
        val json = """
        {
            "news": [
                {
                    "id": 0,
                    "title": "盛岡駅近くのラーメン",
                    "description": "盛岡駅近くのラーメン!!",
                    "image": "img_3105",
                    "textdes": "盛岡駅から10分くらいの横浜家系ラーメンのお店。大学帰りによく行きます!!"
                },
                {
                    "id": 1,
                    "title": "ポテト料理",
                    "description": "ポテト料理",
                    "image": "img_0001",
                    "textdes": "フランスに行った時に食べたポテト料理。そのまま食べても、クリームチーズをディップしても美味しい!!"
                },
                {
                    "id": 2,
                    "title": "栃木のネギラーメン",
                    "description": "栃木のネギラーメン",
                    "image": "img_0002",
                    "textdes": "栃木県のネギラーメン。塩と大量のネギがマッチしていて美味しい!!"
                },
                {
                    "id": 3,
                    "title": "きのこパスタ",
                    "description": "きのこパスタ",
                    "image": "img_0003",
                    "textdes": "フランスに行った時に食べました。普段食べない形のパスタで別添えソースと合わせて食べました!!現地特産のワインと合わせても美味しい!!"
                },
                {
                    "id": 4,
                    "title": "美味しいピザ",
                    "description": "美味しいピザ",
                    "image": "img_0004",
                    "textdes": "4"
                },
                {
                    "id": 5,
                    "title": "シュクルート",
                    "description": "シュクルート",
                    "image": "img_0005",
                    "textdes": "5"
                },
                {
                    "id": 6,
                    "title": "大阪の魚介ラーメン",
                    "description": "大阪の魚介ラーメン",
                    "image": "img_0006",
                    "textdes": "6"
                },
                {
                    "id": 7,
                    "title": "お好み焼き",
                    "description": "お好み焼き",
                    "image": "img_0007",
                    "textdes": "7"
                },
                {
                    "id": 8,
                    "title": "盛岡の油そば",
                    "description": "盛岡の油そば",
                    "image": "img_0008",
                    "textdes": "8"
                },
                {
                    "id": 9,
                    "title": "福島のホタテ塩ラーメン",
                    "description": "福島のホタテ塩ラーメン",
                    "image": "img_0009",
                    "textdes": "9"
                }
            ]
        }
        """.trimIndent()

        // 実際のAPIリクエストのように数秒間待機する
        delay(2_000)
        return Json.decodeFromString(json)
    }
}
