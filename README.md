# ProductTest

¡Bienvenido a ProductTest! Esta es una aplicación para explorar el fascinante mundo de los Product, permitiéndote navegar por una lista, ver detalles individuales y marcar tus favoritos.

## Características Principales

* **Lista de Productos:** Navega por una lista de Productes.
* **Detalles del Producto:** Visualiza información de cada Product, como sus habilidades y sprites.
* **Favoritos:** Marca Product como favoritos.

## Test unitarios

Para correr test unitarios: ```./gradlew test```

## Arquitectura

MVVM con Clean Arquitecture.

## Compatibilidad con Android

Esta aplicación está diseñada para funcionar en dispositivos con las siguientes especificaciones:

* **compileSdk:** 35 (Utiliza las últimas APIs de Android para un rendimiento y características modernos)
* **minSdk:** 24 (Compatible con dispositivos Android 7.0 Nougat y versiones superiores)
* **targetSdk:** 35 (Optimizada para la última versión de Android)

## Librerías Utilizadas

ProductTest se construyó utilizando las siguientes librerías clave para ofrecer una experiencia robusta, moderna y eficiente:

* **Room Database:**
  * Librería de persistencia que proporciona una capa de abstracción sobre SQLite. Guarda en caché la lista de productos para que los usuarios puedan seguir navegando por el catálogo incluso cuando no tienen conexión a internet.
* **Navigation Compose:**
  * Utilizada para manejar la navegación entre pantallas de forma nativa dentro del ecosistema de Jetpack Compose.
* **Kotlin Coroutines & Flows:**
  * Proporcionan una forma concisa y eficiente de manejar tareas asíncronas y flujos de datos reactivos, conectando perfectamente la base de datos local (Room) con la interfaz de usuario.
* **Hilt:**
  * Librería oficial de inyección de dependencias de Android que simplifica la gestión de instancias en toda la aplicación, mejorando enormemente la modularidad y facilitando las pruebas unitarias.
* **DataStore Preferences:**
  * Una solución moderna y segura para almacenar datos clave-valor de forma asíncrona. Se utiliza para gestionar las preferencias locales del usuario, como guardar los IDs de sus **productos favoritos**.
* **Retrofit:**
  * Una potente librería para realizar llamadas de red HTTP de forma sencilla. Se utiliza para obtener el catálogo desde la API de productos.
  * **Gson Converter:** Integrado con Retrofit para convertir las respuestas JSON de la API directamente a objetos de Kotlin (Data Classes).
* **OkHttp Logging Interceptor:**
  * Un interceptor para OkHttp que registra las solicitudes y respuestas HTTP en el Logcat, lo que facilita enormemente la depuración del tráfico de red.
* **Coil:**
  * Librería para la carga y gestión eficiente de imágenes desde URLs. Se encarga de descargar y cachear en memoria las **imágenes de los productos** para que la lista haga scroll de forma fluida.
* **MockK & Coroutines Test:**
  * Librerías esenciales para el entorno de pruebas en Kotlin. Facilitan la creación de mocks (simulaciones) y el control del tiempo en corrutinas para verificar el comportamiento de los Repositorios y ViewModels de manera aislada.
