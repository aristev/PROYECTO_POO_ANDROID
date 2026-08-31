# 🛡️ Battle Retro - POO Android RPG

Un juego de combates por turnos desarrollado de forma nativa para Android. Este proyecto fue construido con el propósito de llevar los conceptos teóricos de la Programación Orientada a Objetos (POO) a una aplicación práctica, interactiva y gamificada.

## 🎯 Resumen del Proyecto

Dos equipos (Equipo Rojo y Equipo Azul) se enfrentan en una arena de combate en partidas de hasta 15 rondas. El jugador puede configurar la alineación de su equipo de 4 integrantes seleccionando entre distintas clases, cada una con estadísticas y habilidades únicas. La aplicación gestiona la interfaz gráfica, el estado de la partida, los turnos y un registro (log) de batalla en tiempo real.


## 🧠 Conceptos de POO Aplicados

El núcleo lógico del juego está diseñado respetando las buenas prácticas de arquitectura de software:

* **Abstracción y Herencia:** Uso de una clase base abstracta `Personaje` que define los atributos fundamentales (HP, ataque, defensa) y firmas de métodos. Las clases `Guerrero`, `Mago` y `Místico` heredan de esta estructura.
* **Polimorfismo:** Cada tipo de personaje implementa el método `usarEstrategia()` de forma completamente distinta:
  * Los **Guerreros** usan *Burst Fury* para duplicar su ataque permanentemente.
  * Los **Magos** usan *Heal* para curar a los aliados heridos calculando porcentajes de HP máximo.
  * Los **Místicos** implementan un sistema de predicción probabilística (dados) para sumar al ataque aliado.
* **Encapsulamiento:** El estado de cada `Equipo` y `Personaje` está protegido, permitiendo modificaciones únicamente a través de métodos de control de daño (`recibirAtaque()`, `realizarAtaque()`) y gestión de vida.
* **Serialización:** Paso de objetos complejos (instancias de `Equipo`) entre las Activities de Android mediante la interfaz `Serializable`.


## 🎮 Características de la Aplicación

* **Configuración Dinámica:** Interfaz de selección mediante Spinners para asignar roles y nombrar a los personajes de ambos bandos.
* **Sistema de Batalla Táctico:** Intercambio de turnos controlados mediante botones de acción ("Lanzar Ataque" y "Usar Estrategia").
* **Feedback Visual:**
  * Barras de progreso de salud (HP) que cambian dinámicamente de color (Verde/Azul > Naranja > Rojo) según el estado crítico del personaje.
  * Sprites temáticos que diferencian visualmente las clases y facciones.
* **Bitácora Integrada:** Consola (Log) desplazable que registra daños, estrategias utilizadas y caídas en combate en cada turno.
* **Audio:** Implementación de `SoundManager` nativo usando `MediaPlayer` para la música de fondo del coliseo.



## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java
* **Entorno:** Android Studio
* **Interfaz:** XML (`ConstraintLayout`, `ScrollView`, Views personalizadas y Custom Drawables)
* **Assets:** Interfaz y sprites orientados a una estética retro/pixel art.
