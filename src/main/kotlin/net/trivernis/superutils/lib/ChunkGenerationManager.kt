package net.trivernis.superutils.lib

import com.sun.org.apache.xpath.internal.operations.Bool
import net.trivernis.superutils.SuperUtils
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.scheduler.BukkitTask
import kotlin.math.abs
import kotlin.math.max

class ChunkGenerationManager(private val superUtils: SuperUtils, private val server: Server, private val world: World) {
    var direction = 0
    private var running = false;
    lateinit var currentChunk: Pair<Int, Int>
    var genTask: BukkitTask? = null

    /**
     * Starts the chunk generation task.
     */
    public fun start(start: Pair<Int, Int>, end: Pair<Int, Int>, callback: (() -> Unit)?) {
        val upperLeft = Pair(minOf(start.first, end.first), minOf(start.second, end.second))
        val lowerRight = Pair(maxOf(start.first, end.first), maxOf(start.second, end.second))
        var progress = 0
        val total = (abs(start.first - end.first) * abs(start.second - end.second))/(16*16)
        server.consoleSender.sendMessage("upperLeft: ${upperLeft.first}, ${upperLeft.second}, " +
                "lowerRight: ${lowerRight.first}, ${lowerRight.second}")
        currentChunk = upperLeft
        genTask = server.scheduler.runTaskTimer(superUtils, Runnable {
            val chunk = world.getChunkAt(Location(world, currentChunk.first.toDouble(), 1.0, currentChunk.second.toDouble()))
            if (!world.isChunkGenerated(chunk.x, chunk.z)) {
                chunk.load(true)
                chunk.unload(true)
            }
            if (currentChunk.first > lowerRight.first) {
                if (currentChunk.second > lowerRight.second) {
                    genTask?.cancel()
                    running = false
                    server.consoleSender.sendMessage("Generation finished.")
                    callback?.invoke()
                } else {
                    currentChunk = Pair(upperLeft.first, currentChunk.second + 16)
                }
            } else {
                currentChunk = Pair(currentChunk.first + 16, currentChunk.second)
            }
            progress ++
            if (progress % 30 == 0) {
                server.consoleSender.sendMessage("ChunkGen Progress: ${(progress.toDouble()/total.toDouble())*100}%")
                server.consoleSender.sendMessage("Chunk ${chunk.x}, ${chunk.z}")
            }
        }, 10, 2)
        this.running = true;
    }

    /**
     * Stops the generation task.
     */
    public fun stop() {
        genTask?.cancel()
        this.running = false;
    }

    /**
     * Returns the running state
     */
    public fun getRunning(): Boolean {
        return this.running
    }

    private fun inArea(chunk: Pair<Int, Int>, upperLeft: Pair<Int, Int>, lowerRight: Pair<Int, Int>): Boolean {
        return chunk.first > upperLeft.first && chunk.first < lowerRight.first &&
                chunk.second > upperLeft.second && chunk.second < lowerRight.second
    }
}