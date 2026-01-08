# Тестирование сервера

## Быстрая проверка работоспособности

### 1. Проверка запуска сервера

Убедитесь, что сервер запущен и доступен:
```bash
curl http://localhost:8080/api/auth/login
```

Должен вернуться ответ (возможно, ошибка авторизации, но это нормально - значит сервер работает).

### 2. Тестирование авторизации

#### Шаг 1: Авторизация
```bash
PS C:\Users\User> $body = @{
>>     username = "admin"
>>     password = "admin"
>> } | ConvertTo-Json
PS C:\Users\User> $response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" `
>>                               -Method POST `
>>                               -Body $body `
>>                               -ContentType "application/json"
PS C:\Users\User> $response
```

**Ожидаемый результат:** JSON с токеном авторизации:
```json
id         : 1
username   : admin
lastName   : ÐÐ´Ð¼Ð¸Ð½Ð¸ÑÑÑÐ°ÑÐ¾Ñ
firstName  : Ð¡Ð¸ÑÑÐµÐ¼Ð½ÑÐ¹
middleName :
roleName   : admin
token      : 90398716-4056-45c2-af21-dbbf64940a02
```
