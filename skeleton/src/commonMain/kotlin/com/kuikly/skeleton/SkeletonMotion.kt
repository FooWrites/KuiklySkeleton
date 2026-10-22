package com.kuikly.skeleton

import com.tencent.kuikly.core.base.Animation
import com.tencent.kuikly.core.base.Attr
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.event.Event
import com.tencent.kuikly.core.reactive.handler.observable

/**
 * 透明度脉冲。默认关闭，静态占位可直接用于 sDynamic。
 * 动画只走 Kuikly 属性动画，结束回调里翻转状态，不依赖协程。
 * JS 端若 completion 不回调，脉冲会停在第一次过渡，静态路径不受影响。
 */
internal class SkeletonMotion {
    var enabled by observable(false)
    var pulseOn by observable(false)
    private var mounted = false
    private var handlingEnd = false

    fun applyEnabled(value: Boolean) {
        val was = enabled
        enabled = value
        if (!value) {
            pulseOn = false
            return
        }
        if (mounted && !was) {
            pulseOn = !pulseOn
        }
    }

    fun markMounted() {
        mounted = true
        if (enabled && !pulseOn) {
            pulseOn = true
        }
    }

    fun onAnimationEnd() {
        if (!enabled || handlingEnd) {
            return
        }
        handlingEnd = true
        pulseOn = !pulseOn
        handlingEnd = false
    }
}

internal fun Attr.skeletonSurface(
    color: Color,
    radius: Float,
    motion: SkeletonMotion,
) {
    backgroundColor(color)
    borderRadius(radius)
    if (motion.enabled) {
        opacity(if (motion.pulseOn) PULSE_OPACITY else 1f)
        animate(Animation.easeInOut(PULSE_SECONDS, key = "skeleton_pulse"), motion.pulseOn)
    } else {
        opacity(1f)
    }
}

internal fun Event.bindSkeletonPulse(motion: SkeletonMotion) {
    animationCompletion {
        motion.onAnimationEnd()
    }
}

private const val PULSE_OPACITY = 0.45f
private const val PULSE_SECONDS = 0.8f
