package com.plataformas.lab06.model
import android.graphics.PointF
import android.graphics.RectF


data class Room(
    val id: String,
    val name: String,
    val type: String, // "salon" o "patio"
    val vertices: List<PointF>,
    val description: String,
    val area: Float = 0f,
    val capacity: Int = 0,
    val additionalInfo: String = ""
) {
    // Calcular el centro del ambiente para colocar la etiqueta
    fun getCenter(): PointF {
        var sumX = 0f
        var sumY = 0f
        vertices.forEach { vertex ->
            sumX += vertex.x
            sumY += vertex.y
        }
        return PointF(sumX / vertices.size, sumY / vertices.size)
    }

    // Obtener el rectángulo delimitador para detección de clicks
    fun getBoundingBox(): RectF {
        if (vertices.isEmpty()) return RectF()

        var minX = vertices[0].x
        var maxX = vertices[0].x
        var minY = vertices[0].y
        var maxY = vertices[0].y

        vertices.forEach { vertex ->
            if (vertex.x < minX) minX = vertex.x
            if (vertex.x > maxX) maxX = vertex.x
            if (vertex.y < minY) minY = vertex.y
            if (vertex.y > maxY) maxY = vertex.y
        }

        return RectF(minX, minY, maxX, maxY)
    }

    // Verificar si un punto está dentro del polígono usando Ray Casting
    fun containsPoint(point: PointF): Boolean {
        var intersections = 0
        val n = vertices.size

        for (i in 0 until n) {
            val v1 = vertices[i]
            val v2 = vertices[(i + 1) % n]

            if (rayIntersectsSegment(point, v1, v2)) {
                intersections++
            }
        }

        return intersections % 2 == 1
    }

    private fun rayIntersectsSegment(point: PointF, v1: PointF, v2: PointF): Boolean {
        if (v1.y > v2.y) {
            return rayIntersectsSegment(point, v2, v1)
        }

        if (point.y < v1.y || point.y >= v2.y) {
            return false
        }

        if (point.x >= maxOf(v1.x, v2.x)) {
            return false
        }

        if (point.x < minOf(v1.x, v2.x)) {
            return true
        }

        val xIntersection = (point.y - v1.y) * (v2.x - v1.x) / (v2.y - v1.y) + v1.x
        return point.x < xIntersection
    }
}

data class Building(
    val name: String,
    val rooms: List<Room>
)