# API Документация

## Базовая информация

- Базовый URL: `http://localhost:8080/api`
- Аутентификация: Bearer Token в заголовке `Authorization: Bearer <token>`

## Эндпоинты

### Авторизация

#### POST /api/auth/login
Авторизация пользователя в системе.

**Запрос:**
```json
{
  "username": "admin",
  "password": "admin"
}
```

**Ответ:**
```json
{
  "id": 1,
  "username": "admin",
  "lastName": "Администратор",
  "firstName": "Системный",
  "middleName": "",
  "roleName": "admin",
  "token": "uuid-token"
}
```

### Сотрудники (Employees)

#### GET /api/employees
Получить список всех сотрудников.

#### GET /api/employees/{id}
Получить сотрудника по ID.

#### POST /api/employees
Создать нового сотрудника.

**Запрос:**
```json
{
  "fullName": "Иванов Иван Иванович",
  "position": "Водитель",
  "employeeNumber": "123456"
}
```

#### PUT /api/employees/{id}
Обновить сотрудника.

#### DELETE /api/employees/{id}
Удалить сотрудника.

#### POST /api/employees/{id}/photo
Загрузить фотографию сотрудника (multipart/form-data).

### Видеорегистраторы (Video Recorders)

#### GET /api/video-recorders?status=available
Получить список видеорегистраторов (опционально по статусу: available/issued).

#### GET /api/video-recorders/{id}
Получить видеорегистратор по ID.

#### POST /api/video-recorders
Создать новый видеорегистратор.

**Запрос:**
```json
{
  "number": "VR-001",
  "status": "available"
}
```

#### PUT /api/video-recorders/{id}
Обновить видеорегистратор.

#### DELETE /api/video-recorders/{id}
Удалить видеорегистратор.

### Выдача (Issues)

#### GET /api/issues
Получить список всех выдач.

#### GET /api/issues/{id}
Получить выдачу по ID.

#### GET /api/issues/video-recorder/{videoRecorderId}
Получить все выдачи для видеорегистратора.

#### GET /api/issues/employee/{employeeId}
Получить все выдачи для сотрудника.

#### POST /api/issues
Выдать видеорегистратор сотруднику.

**Запрос:**
```json
{
  "videoRecorderId": 1,
  "employeeId": 1
}
```

### Возврат (Returns)

#### GET /api/returns
Получить список всех возвратов.

#### GET /api/returns/video-recorder/{videoRecorderId}
Получить все возвраты для видеорегистратора.

#### GET /api/returns/employee/{employeeId}
Получить все возвраты для сотрудника.

#### POST /api/returns
Вернуть видеорегистратор.

**Запрос:**
```json
{
  "videoRecorderId": 1,
  "employeeId": 1
}
```

### История (History)

#### GET /api/history
Получить всю историю выдачи и возврата.

#### GET /api/history/video-recorder/{videoRecorderId}
Получить историю для видеорегистратора.

#### GET /api/history/employee/{employeeId}
Получить историю для сотрудника.

## Начальные данные

При первом запуске приложения автоматически создаются:
- Роль "admin" (Администратор системы)
- Роль "operator" (Оператор системы)
- Пользователь "admin" с паролем "admin"

## Настройка базы данных

### Запуск PostgreSQL в Docker

1. Запустите контейнер с базой данных:
   ```bash
   docker-compose up -d
   ```

2. Проверьте, что контейнер запущен:
   ```bash
   docker-compose ps
   ```

3. Для остановки контейнера:
   ```bash
   docker-compose down
   ```

4. Для остановки и удаления данных:
   ```bash
   docker-compose down -v
   ```

База данных автоматически создается при первом запуске контейнера.

Настройки подключения в `application.properties`:
- URL: `jdbc:postgresql://localhost:5432/video_recorder_db`
- Username: `postgres`
- Password: `postgres`
- Port: `5432`

При необходимости измените эти настройки в `docker-compose.yml` и `application.properties`.

