package com.example.data

data class MotivationalQuote(
    val id: Int,
    val text: String,
    val author: String,
    val category: String
)

object MotivationalQuotesProvider {
    val quotes = listOf(
        MotivationalQuote(
            id = 1,
            text = "La disciplina es el puente inquebrantable entre tus metas y tus logros.",
            author = "Jim Rohn",
            category = "DISCIPLINA"
        ),
        MotivationalQuote(
            id = 2,
            text = "El éxito es la suma de pequeños esfuerzos repetidos día tras día sin falta.",
            author = "Robert Collier",
            category = "CONSTANCIA"
        ),
        MotivationalQuote(
            id = 3,
            text = "No estudias solo por hoy; estudias para la persona imparable en la que te estás convirtiendo.",
            author = "Mentalidad Racha",
            category = "ENFOQUE"
        ),
        MotivationalQuote(
            id = 4,
            text = "Cada minuto concentrado construye tu futuro. No negocies con la pereza.",
            author = "Séneca",
            category = "DETERMINACIÓN"
        ),
        MotivationalQuote(
            id = 5,
            text = "La motivación te pone en marcha; el hábito diario te lleva a la cima.",
            author = "Jim Ryun",
            category = "HÁBITO"
        ),
        MotivationalQuote(
            id = 6,
            text = "La constancia siempre vence al talento cuando el talento no es constante.",
            author = "Tim Notke",
            category = "CONSTANCIA"
        ),
        MotivationalQuote(
            id = 7,
            text = "Hoy hiciste lo que el 99% posterga. Tu racha es tu mayor orgullo.",
            author = "Filosofía Foco",
            category = "SUPERACIÓN"
        ),
        MotivationalQuote(
            id = 8,
            text = "Un día a la vez. Una página a la vez. Una victoria silenciosa a la vez.",
            author = "Anónimo",
            category = "ENFOQUE"
        ),
        MotivationalQuote(
            id = 9,
            text = "El cansancio es pasajero, pero el orgullo de haber cumplido hoy es eterno.",
            author = "Kobe Bryant",
            category = "DISCIPLINA"
        ),
        MotivationalQuote(
            id = 10,
            text = "Tu versión del futuro te va a agradecer con creces lo que sembraste hoy.",
            author = "Visión Clara",
            category = "VISIÓN"
        ),
        MotivationalQuote(
            id = 11,
            text = "No busques el momento perfecto: empezá ahora y hazlo perfecto con tu esfuerzo.",
            author = "Marco Aurelio",
            category = "ACCIÓN"
        ),
        MotivationalQuote(
            id = 12,
            text = "El cerebro es un músculo: cada sesión de estudio lo vuelve más veloz y afilado.",
            author = "Neurociencia",
            category = "APRENDIZAJE"
        ),
        MotivationalQuote(
            id = 13,
            text = "La racha no es solo un número: es el testimonio vivo de tu palabra empeñada.",
            author = "RACHA",
            category = "PROPÓSITO"
        ),
        MotivationalQuote(
            id = 14,
            text = "Lo que hoy te resulta difícil y pesado, mañana será tu calentamiento.",
            author = "Anónimo",
            category = "SUPERACIÓN"
        ),
        MotivationalQuote(
            id = 15,
            text = "Quien domina su atención en un mundo lleno de distracciones, domina su destino.",
            author = "Cal Newport",
            category = "ENFOQUE"
        ),
        MotivationalQuote(
            id = 16,
            text = "El fuego de la disciplina quema cualquier excusa que intente frenarte.",
            author = "Epicteto",
            category = "DISCIPLINA"
        ),
        MotivationalQuote(
            id = 17,
            text = "Dormirás con la mente en paz sabiendo que hoy no te fallaste a ti mismo.",
            author = "Hábito Diario",
            category = "INTEGRIDAD"
        ),
        MotivationalQuote(
            id = 18,
            text = "Las grandes mentes se forjan en el silencio y la constancia de cada tarde.",
            author = "Albert Einstein",
            category = "CONSTANCIA"
        ),
        MotivationalQuote(
            id = 19,
            text = "Un grano de conocimiento cada día crea una montaña imposible de derribar.",
            author = "Proverbio",
            category = "SABIDURÍA"
        ),
        MotivationalQuote(
            id = 20,
            text = "Cada concepto dominado es una herramienta más para construir tu libertad.",
            author = "Educación Viva",
            category = "LIBERTAD"
        ),
        MotivationalQuote(
            id = 21,
            text = "Si quieres resultados que pocos tienen, haz lo que la mayoría no está dispuesta a hacer.",
            author = "Denzel Washington",
            category = "DETERMINACIÓN"
        ),
        MotivationalQuote(
            id = 22,
            text = "El secreto para avanzar es simplemente nunca negociar el primer paso.",
            author = "Mark Twain",
            category = "ACCIÓN"
        ),
        MotivationalQuote(
            id = 23,
            text = "Mantén la llama viva. Un día sin estudiar es apagar un poco tu fuego.",
            author = "RACHA",
            category = "RACHA"
        ),
        MotivationalQuote(
            id = 24,
            text = "No necesitas motivación cuando tienes una rutina que respetas como a nada.",
            author = "Jocko Willink",
            category = "DISCIPLINA"
        ),
        MotivationalQuote(
            id = 25,
            text = "La excelencia es un arte ganado por el entrenamiento y la costumbre.",
            author = "Aristóteles",
            category = "EXCELENCIA"
        ),
        MotivationalQuote(
            id = 26,
            text = "Mejorar un 1% cada día significa ser 37 veces mejor al final del año.",
            author = "James Clear",
            category = "HÁBITO"
        ),
        MotivationalQuote(
            id = 27,
            text = "El conocimiento es el único tesoro que crece cuando se invierte tiempo en él.",
            author = "Benjamin Franklin",
            category = "APRENDIZAJE"
        ),
        MotivationalQuote(
            id = 28,
            text = "No mires el reloj mientras estudias: haz lo que él hace, ¡sigue avanzando!",
            author = "Sam Levenson",
            category = "ENFOQUE"
        ),
        MotivationalQuote(
            id = 29,
            text = "Tu concentración es tu superpoder más valioso. Protégela y multiplícala.",
            author = "Deep Work",
            category = "ENFOQUE"
        ),
        MotivationalQuote(
            id = 30,
            text = "¡Estudiar hoy es tu victoria diaria! Nadie te puede quitar lo aprendido.",
            author = "RACHA",
            category = "VICTORIA"
        )
    )

    fun getRandomQuote(excludeId: Int? = null): MotivationalQuote {
        val pool = if (excludeId != null && quotes.size > 1) {
            quotes.filter { it.id != excludeId }
        } else {
            quotes
        }
        return pool.random()
    }
}
