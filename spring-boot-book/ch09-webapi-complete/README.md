# キーワード検索（「の」）
```shell
curl -v -X GET "http://localhost:8080/api/idols?keyword=%E3%81%AE"
```

# ID検索
```shell
curl -v -X GET http://localhost:8080/api/idols/1
```

# ID検索（IDが存在しない）
```shell
curl -v -X GET http://localhost:8080/api/idols/99
```

# 追加
```shell
curl -v -H "Content-Type: application/json" -d @idol.json http://localhost:8080/api/idols
```

# 追加（バリデーションエラー）
```shell
curl -v -H "Content-Type: application/json" -d @idol-invalid.json http://localhost:8080/api/idols
```

# 更新
```shell
curl -v -X PUT -H "Content-Type: application/json" -d @idol.json http://localhost:8080/api/idols/1
```

# 更新（バリデーションエラー）
```shell
curl -v -X PUT -H "Content-Type: application/json" -d @idol-invalid.json http://localhost:8080/api/idols/1
```

# 更新（IDが存在しない）
```shell
curl -v -X PUT -H "Content-Type: application/json" -d @idol.json http://localhost:8080/api/idols/99
```

# 削除
```shell
curl -v -X DELETE http://localhost:8080/api/idols/1
```

# 削除（IDが存在しない）
```shell
curl -v -X DELETE http://localhost:8080/api/idols/99
```
