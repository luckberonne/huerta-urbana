---
doc: scope
status: approved
---

# Huerta Urbana

Una app para manejar mi propia huerta urbana sin olvidarme de regar ni de cosechar.

## The Unique Kernel
Una lista de "qué hacer hoy" para la huerta: abro la app y me dice qué regar y cuándo cosechar, sin que yo tenga que acordarme. No es un catálogo de plantas ni un recordatorio genérico: es la lista del día, calculada desde la fecha de siembra.

## Who It's For
Yo: una persona que disfruta manejar su propia huerta urbana. Hoy se me olvida regar y no sé cuándo cosechar.

## The Core Loop
Abro la app, veo la lista de hoy (por ejemplo "regar lechuga"). Riego y la marco como regada. Cuando siembro algo, cargo la fecha de siembra. Con esa fecha la app sabe cuándo la planta está lista para cosechar y lo pone en la lista.
Vuelvo cada día porque la lista me dice qué hacer.

## Inspiration & Identity
No definido todavía (no se mencionaron referencias). Se aclara en el PRD.

## Why This Matters to the Learner
"Me gusta manejar mi propia huerta." Quiere resolver dos problemas reales: se le olvida regar y no sabe cuándo cosechar. Objetivo declarado del hackathon: ganar el premio.

## What "Working" Looks Like
Abro la app y veo la lista de hoy con "regar lechuga". La marco como regada y desaparece (o queda hecha). Cargo una fecha de siembra y, al llegar al tiempo de cosecha de la lechuga, aparece "cosechar lechuga" en la lista.
El momento que lo demuestra: la lista del día cambia sola según lo que cargué.

## The POC Boundary
- Una sola planta: lechuga (cosecha en 3–9 semanas según fuentes; el valor exacto se fija en el PRD).
- Lista "qué hacer hoy" con dos tareas: regar y cosechar.
- Marcar como regada.
- Cargar la fecha de siembra.

## Later
- Más plantas (rábano, tomate cherry, albahaca, perejil).
- Notificaciones.
- Datos de riego y cosecha más precisos por variedad y clima.

## Explicitly Cut
- Notificaciones: se eligió la lista del día; es más simple para el POC.
- Otras plantas además de la lechuga: una alcanza para probar el ciclo completo.
- Cuentas de usuario y compartir: es una app personal.
