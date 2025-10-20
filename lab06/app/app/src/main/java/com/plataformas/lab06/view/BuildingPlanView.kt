package com.plataformas.lab06.view
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.plataformas.lab06.model.Room

class BuildingPlanView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rooms: List<Room> = emptyList()
    private var selectedRoom: Room? = null
    private var onRoomClickListener: ((Room) -> Unit)? = null

    // Paints para dibujar
    private val roomPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#E8F4F8")
    }

    private val selectedRoomPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#B3D9E6")
    }

    private val patioPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FFF4E6")
    }

    private val selectedPatioPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FFE0B3")
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#333333")
        strokeWidth = 3f
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#333333")
        textSize = 32f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FFA500")
    }

    // Variables para transformación y escalado
    private var scaleX = 1f
    private var scaleY = 1f
    private var offsetX = 0f
    private var offsetY = 0f

    fun setRooms(rooms: List<Room>) {
        this.rooms = rooms
        calculateScale()
        invalidate()
    }

    fun setSelectedRoom(room: Room?) {
        this.selectedRoom = room
        invalidate()
    }

    fun setOnRoomClickListener(listener: (Room) -> Unit) {
        this.onRoomClickListener = listener
    }

    private fun calculateScale() {
        if (rooms.isEmpty() || width == 0 || height == 0) return

        // Encontrar límites del plano
        var minX = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var minY = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE

        rooms.forEach { room ->
            room.vertices.forEach { vertex ->
                if (vertex.x < minX) minX = vertex.x
                if (vertex.x > maxX) maxX = vertex.x
                if (vertex.y < minY) minY = vertex.y
                if (vertex.y > maxY) maxY = vertex.y
            }
        }

        val planWidth = maxX - minX
        val planHeight = maxY - minY

        // Calcular escala con margen
        val margin = 50f
        scaleX = (width - 2 * margin) / planWidth
        scaleY = (height - 2 * margin) / planHeight

        // Usar la escala menor para mantener proporciones
        val scale = minOf(scaleX, scaleY)
        scaleX = scale
        scaleY = scale

        // Calcular offsets para centrar
        offsetX = margin - minX * scaleX + (width - planWidth * scaleX - 2 * margin) / 2
        offsetY = margin - minY * scaleY + (height - planHeight * scaleY - 2 * margin) / 2
    }

    private fun transformPoint(point: PointF): PointF {
        return PointF(
            point.x * scaleX + offsetX,
            point.y * scaleY + offsetY
        )
    }

    private fun inverseTransformPoint(point: PointF): PointF {
        return PointF(
            (point.x - offsetX) / scaleX,
            (point.y - offsetY) / scaleY
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (rooms.isEmpty()) return

        // Dibujar cada ambiente
        rooms.forEach { room ->
            drawRoom(canvas, room)
        }
    }

    private fun drawRoom(canvas: Canvas, room: Room) {
        val path = Path()
        val transformedVertices = room.vertices.map { transformPoint(it) }

        if (transformedVertices.isEmpty()) return

        // Crear path del polígono
        path.moveTo(transformedVertices[0].x, transformedVertices[0].y)
        for (i in 1 until transformedVertices.size) {
            path.lineTo(transformedVertices[i].x, transformedVertices[i].y)
        }
        path.close()

        // Seleccionar paint según tipo y selección
        val fillPaint = when {
            room == selectedRoom && room.type == "patio" -> selectedPatioPaint
            room == selectedRoom -> selectedRoomPaint
            room.type == "patio" -> patioPaint
            else -> roomPaint
        }

        // Dibujar relleno y borde
        canvas.drawPath(path, fillPaint)
        canvas.drawPath(path, strokePaint)

        // Dibujar puntos en los vértices
        transformedVertices.forEach { vertex ->
            canvas.drawCircle(vertex.x, vertex.y, 8f, dotPaint)
        }

        // Dibujar etiqueta en el centro
        val center = transformPoint(room.getCenter())

        // Fondo para el texto
        val textBounds = Rect()
        textPaint.getTextBounds(room.name, 0, room.name.length, textBounds)
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#FFFFFF")
            alpha = 200
        }

        val padding = 10f
        val bgRect = RectF(
            center.x - textBounds.width() / 2 - padding,
            center.y - textBounds.height() - padding,
            center.x + textBounds.width() / 2 + padding,
            center.y + padding
        )
        canvas.drawRoundRect(bgRect, 8f, 8f, bgPaint)

        // Texto
        canvas.drawText(room.name, center.x, center.y, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val touchPoint = inverseTransformPoint(PointF(event.x, event.y))

            // Buscar qué ambiente fue tocado (en orden inverso para priorizar los de arriba)
            for (i in rooms.size - 1 downTo 0) {
                val room = rooms[i]
                if (room.containsPoint(touchPoint)) {
                    onRoomClickListener?.invoke(room)
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateScale()
    }
}