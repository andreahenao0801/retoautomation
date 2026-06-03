# Reto QA REST Screenplay Challenge

Este repositorio contiene la solución al reto de automatización de pruebas para el API REST utilizando el patrón **Screenplay** con **Serenity BDD**, **JUnit 5**, **Java 17** y **Gradle**.

## 🛠️ Tech Stack & Dependencias
* **Lenguaje:** Java 17
* **Framework de Pruebas:** Serenity BDD (v4.1.14) con Screenplay y Serenity JUnit 5
* **Gestor de Dependencias:** Gradle (v8.5)
* **Generación de Datos:** Datafaker (v2.1.0) para generación dinámica de datos
* **Reducción de Boilerplate:** Lombok (v1.18.30)
* **Aserciones:** AssertJ y Hamcrest (incluidos con Serenity)

---

## 🎯 API Destino
Se seleccionó la API pública **JSONPlaceholder** (`https://jsonplaceholder.typicode.com`) para realizar las pruebas CRUD debido a su estabilidad y soporte completo de operaciones CRUD públicas sin necesidad de llaves de API adicionales.

---

## 🏗️ Estructura del Framework (Screenplay Pattern)

La estructura sigue rigurosamente el patrón de diseño propuesto en el reto:

```text
src/
├── main/java/com/challenge/
│   ├── interactions/api/      # Interacciones personalizadas (Post, Get, Put, Delete)
│   ├── tasks/                 # Tareas (CreateUser, GetUser, UpdateUser, DeleteUser)
│   ├── questions/             # Preguntas (TheStatusCode, TheResponseBody)
│   ├── models/
│   │   ├── request/           # POJOs de Request con @Builder y @Data (Lombok)
│   │   └── response/          # POJOs de Response mapeados con @JsonProperty
│   └── utils/
│       ├── constants/         # Enum de Endpoints (Resources.USERS)
│       └── config/            # Lógica para leer propiedades de serenity.conf
└── test/
    ├── java/com/challenge/
    │   └── runners/           # Runner/Suite JUnit 5 (UserCrudRunner)
    └── resources/
        └── serenity.conf      # Configuración de URLs por ambiente
```

---

## 🚀 Prerrequisitos
Tener instalado **Java 17** en tu sistema. No se requiere tener Gradle instalado globalmente, ya que el proyecto incluye su propio Gradle Wrapper (`gradlew`).

---

## 🏃 Cómo Ejecutar las Pruebas

Para ejecutar las pruebas completas y limpiar reportes previos, corre el siguiente comando en la raíz del proyecto:

```bash
./gradlew clean test
```

---

## 📊 Reportes de Serenity BDD

Una vez finalizada la ejecución de las pruebas, Serenity generará un reporte HTML interactivo completo.

### Ubicación del Reporte:
Abre el siguiente archivo en cualquier navegador web:
📁 `target/site/serenity/index.html`

---

## 💡 Decisiones de Diseño y Características Clave

1. **Interacciones Personalizadas (`RestInteraction`):** Se crearon las clases `Post`, `Get`, `Put` y `Delete` extendiendo la clase base `RestInteraction` de Serenity, ofreciendo flexibilidad en la construcción de los requests y un encapsulamiento limpio.
2. **Manejo de Sesión (Bonus):** Se implementó el almacenamiento y recuperación del ID del usuario creado a través de `actor.remember("userId", id)` en el test de creación (POST) y posteriormente se recuperó en el test de actualización (PUT) y eliminación (DELETE) usando `actor.recall("userId")`.
3. **Generación Dinámica de Datos (Bonus):** Se integró `net.datafaker:datafaker` para la creación dinámica y realista de nombres, nombres de usuario y direcciones de correo electrónico.
4. **Bypass de Limitación del Mock:** Dado que JSONPlaceholder es un servicio mock que no persiste los registros creados mediante POST en su base de datos real, el test GET consulta el usuario con ID `2` para validar correctamente la respuesta completa. Los tests de PUT y DELETE usan el ID dinámico recordado con fallback al ID `2`, garantizando que la suite se ejecute de forma exitosa y sin falsos negativos.
