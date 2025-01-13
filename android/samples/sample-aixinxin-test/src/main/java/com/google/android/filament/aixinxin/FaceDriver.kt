package com.google.android.filament.aixinxin

import android.util.Log
import com.google.android.filament.Engine
import com.google.android.filament.gltfio.FilamentAsset
import com.google.android.filament.utils.ModelViewer

class FaceDriver(private val modelViewer: ModelViewer) {
    private var engine: Engine = modelViewer.engine
    private var driveStartTimeNanos: Long = 0
    private var faceFrames: List<CoefFrame> = emptyList()

    fun startDrive(inFaceFrames: List<CoefFrame>) {
        driveStartTimeNanos = System.nanoTime()
        faceFrames = inFaceFrames
    }

    fun doFrame(frameTimeNanos: Long) {
        val asset: FilamentAsset = modelViewer.asset ?: return

        if (faceFrames.isEmpty()) {
            return
        }

        val elapsedTime = frameTimeNanos - driveStartTimeNanos
        val frameIndex = (elapsedTime / 1e9 * 30).toInt()
        if (frameIndex >= faceFrames.size) {
            faceFrames = emptyList()
            return
        }

        val rm = engine.renderableManager
        for (entity in asset.renderableEntities) {
            val morphNameIndexMap = HashMap<String, Int>()
            var morphTargetCount: Int
            asset.getMorphTargetNames(entity).let { morphTargetNames ->
                for (i in morphTargetNames.indices) {
                    morphNameIndexMap[morphTargetNames[i]] = i
                }

                morphTargetCount = morphTargetNames.size
            }

            if (morphTargetCount > 0) {
                val morphWeights = FloatArray(morphTargetCount)
                morphWeights.fill(0f, 0, morphTargetCount)
                val frame = faceFrames[frameIndex]
                for (entry in frame.coef_frame) {
                    morphNameIndexMap[entry.key]?.let { morphIndex ->
                        morphWeights[morphIndex] = entry.value
                    }
                }

                val instance = rm.getInstance(entity)
                rm.setMorphWeights(instance, morphWeights, 0)
            }
        }
    }
}
