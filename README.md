# 🍿 PopCorn - Diseño Final & Integración API

[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/marioProfeJOYFE/PopCorn)

> **✨ Estado de la rama:** Diseño Final completado. Funcionalidad completa con consumo de API real, arquitectura MVVM y navegación.

**PopCorn** es una aplicación nativa de Android diseñada para los verdaderos cinéfilos. En esta versión, la app cobra vida conectándose a **The Movie Database (TMDB)** para ofrecer un catálogo real y actualizado, permitiendo buscar películas, ver sus detalles a fondo y disfrutar de una interfaz de usuario moderna y pulida.

---

## 🌟 Novedades en esta versión

* **🌐 Integración con TMDB API:** ¡Adiós datos estáticos! Las películas ahora se cargan dinámicamente desde la nube usando Retrofit.
* **🧭 Navegación fluida:** Implementación de `Navigation Compose` para viajar sin problemas entre la pantalla principal y los detalles de cada película.
* **🔍 Búsqueda integrada:** Encuentra exactamente la película que tienes en mente con el nuevo buscador.
* **💅 UI Avanzada:** * Fondos con gradientes (`GradientBackground`).
    * Efectos visuales modernos como Glassmorphism (`GlassContainer`).
    * Pantalla de carga inicial (Splash Screen con logo personalizado).
* **🏗️ Arquitectura robusta:** Transición a un modelo **MVVM** (Model-View-ViewModel) limpio, con repositorios y gestión de estado.

---

## 💻 Tecnologías Destacadas

Este proyecto es un escaparate de las mejores prácticas actuales en el ecosistema Android:

* **Lenguaje:** Kotlin
* **UI Declarativa:** Jetpack Compose (Material 3)
* **Navegación:** Navigation Compose
* **Red y API:** [Retrofit](https://square.github.io/retrofit/) + Gson/Moshi para parseo de JSON
* **Arquitectura:** MVVM (ViewModels, Factory, Repository Pattern)
* **Asincronía:** Kotlin Coroutines & Flows

---

## 📂 Estructura del Proyecto

El código está organizado de manera modular y escalable:

* 📁 `data/`
    * `model/`: Clases de datos (`Movie`, `MovieDetail`).
    * `remote/`: Configuración de Retrofit y Endpoints de TMDB (`TmdbApiService`).
    * `repository/`: Lógica de obtención de datos (de red o mock).
* 📁 `ui/`
    * `navigation/`: Rutas y grafo de navegación (`AppNavigation`).
    * `screen/`: Vistas y ViewModels separados por funcionalidad (`home/`, `detail/`, `search/`).
    * `theme/`: Tipografía, colores y componentes UI personalizados de alta fidelidad.

---

## 🚀 Cómo ejecutar el proyecto

1.  **Clona el repositorio** y asegúrate de cambiar a la rama del diseño final:
    ```bash
    git clone [https://github.com/marioProfeJOYFE/PopCorn.git](https://github.com/marioProfeJOYFE/PopCorn.git)
    git checkout diseno-final
    ```
2.  **Configura la API Key de TMDB (Si aplica):**
    Para que las llamadas a red funcionen, asegúrate de tener configurada tu clave de API de The Movie Database en tu entorno o en el archivo correspondiente (como `local.properties`).
3.  **Abre el proyecto en Android Studio.**
4.  **Sincroniza Gradle** y pulsa **Run** (▶️) en tu emulador o dispositivo físico.

---

## 👨‍💻 Autor

Desarrollado con ❤️ y mucho código por **Mario Rios Holgado**.
