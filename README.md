# 🍿 PopCorn

[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/marioProfeJOYFE/PopCorn)

> **⚠️ Estado del proyecto:** En desarrollo activo (Work in Progress). 🛠️

**PopCorn** es una aplicación nativa de Android diseñada para los amantes del cine. Actualmente en sus primeras fases de desarrollo, la app permite explorar un catálogo de películas, ver sus puntuaciones y marcar tus favoritas para no perderles la pista. 

---

## ✨ Características actuales

* **🎬 Catálogo de películas:** Visualización en cuadrícula (Grid) de las películas más populares.
* **⭐ Puntuaciones:** Información rápida sobre el rating de cada película.
* **❤️ Favoritos:** Funcionalidad interactiva para añadir o quitar películas de tu lista de favoritos.
* *(Más funciones próximamente: Integración con API real, detalle de películas, búsqueda, etc.)*

---

## 💻 Tecnologías y Arquitectura

Este proyecto está construido utilizando las últimas herramientas y estándares de desarrollo de Android:

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **Interfaz de usuario:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
* **Build System:** Gradle (Kotlin DSL)
* **SDK:** Target SDK 36, Min SDK 29

---

## 🚀 Cómo ejecutar el proyecto

Dado que el proyecto está en desarrollo, puedes clonarlo y ejecutarlo localmente usando Android Studio:

1. **Clona el repositorio:**
   ```bash
   git clone [https://github.com/marioProfeJOYFE/PopCorn.git](https://github.com/marioProfeJOYFE/PopCorn.git)
   ```
2. **Abre el proyecto:**
   Abre Android Studio y selecciona `File > Open`, luego navega hasta la carpeta del proyecto clonado.
3. **Sincroniza Gradle:**
   Espera a que Gradle descargue las dependencias y configure el entorno.
4. **Ejecuta la app:**
   Conecta un dispositivo físico o inicia un emulador y pulsa el botón **Run** (▶️).

---

## 📂 Estructura inicial del código

* `data/model/`: Contiene los modelos de datos como `Movie`.
* `data/repository/`: Manejo de datos. Actualmente utiliza un `MovieMockRepository` con datos estáticos (hardcodeados) para el diseño inicial.
* `ui/theme/`: Configuración del diseño base (colores, tipografía y tema de Material 3).
* `MainActivity.kt`: Punto de entrada de la app y contenedores de los Composable principales.

---

## 👨‍💻 Autor

Desarrollado por **Mario Rios Holgado**.
