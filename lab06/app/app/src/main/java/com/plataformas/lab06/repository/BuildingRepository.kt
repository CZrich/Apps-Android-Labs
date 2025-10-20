package com.plataformas.lab06.repository
import android.content.Context
import android.graphics.PointF
import com.plataformas.lab06.model.Building
import com.plataformas.lab06.model.Room
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
class BuildingRepository {

    fun loadBuildingFromAssets(context: Context): Building {
        // Leer el archivo JSON desde assets
        val jsonString = context.assets.open("building_data.json").use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.readText()
            }
        }

        return parseBuildingJson(jsonString)
    }

    private fun parseBuildingJson(jsonString: String): Building {
        val jsonObject = JSONObject(jsonString)
        val buildingName = jsonObject.getString("name")
        val roomsArray = jsonObject.getJSONArray("rooms")

        val rooms = mutableListOf<Room>()

        for (i in 0 until roomsArray.length()) {
            val roomJson = roomsArray.getJSONObject(i)

            val id = roomJson.getString("id")
            val name = roomJson.getString("name")
            val type = roomJson.getString("type")
            val description = roomJson.getString("description")
            val area = roomJson.optDouble("area", 0.0).toFloat()
            val capacity = roomJson.optInt("capacity", 0)
            val additionalInfo = roomJson.optString("additionalInfo", "")

            // Parsear vértices
            val verticesArray = roomJson.getJSONArray("vertices")
            val vertices = mutableListOf<PointF>()

            for (j in 0 until verticesArray.length()) {
                val vertexJson = verticesArray.getJSONObject(j)
                val x = vertexJson.getDouble("x").toFloat()
                val y = vertexJson.getDouble("y").toFloat()
                vertices.add(PointF(x, y))
            }

            rooms.add(
                Room(
                    id = id,
                    name = name,
                    type = type,
                    vertices = vertices,
                    description = description,
                    area = area,
                    capacity = capacity,
                    additionalInfo = additionalInfo
                )
            )
        }

        return Building(buildingName, rooms)
    }
}