package com.kuikly.skeleton

import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ContainerAttr
import com.tencent.kuikly.core.base.ViewConst
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.event.Event
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.View

/** 圆形占位，用于头像等。 */
class SkeletonCircleView : ViewContainer<SkeletonCircleAttr, Event>() {
    private val motion = SkeletonMotion()
    private var fill by observable(SkeletonDefaults.baseColor)
    private var diameterPx by observable(40f)

    override fun createAttr(): SkeletonCircleAttr {
        return SkeletonCircleAttr().apply {
            size(40f)
        }
    }

    override fun createEvent(): Event = Event()

    override fun viewName(): String = ViewConst.TYPE_VIEW

    override fun didInit() {
        super.didInit()
        attr.onStyleChanged = { syncStyle() }
        syncStyle()
        mountFill()
        motion.markMounted()
    }

    private fun syncStyle() {
        fill = attr.fillColor
        diameterPx = attr.diameterValue
        motion.applyEnabled(attr.animatedEnabled)
    }

    private fun mountFill() {
        val ctx = this
        View {
            attr {
                absolutePositionAllZero()
                skeletonSurface(ctx.fill, ctx.diameterPx / 2f, ctx.motion)
            }
            event {
                bindSkeletonPulse(ctx.motion)
            }
        }
    }
}

class SkeletonCircleAttr : ContainerAttr() {
    internal var fillColor: Color = SkeletonDefaults.baseColor
    internal var diameterValue: Float = 40f
    internal var animatedEnabled: Boolean = false
    internal var onStyleChanged: (() -> Unit)? = null

    fun size(diameter: Float) {
        val resolved = diameter.coerceAtLeast(1f)
        diameterValue = resolved
        width(resolved)
        height(resolved)
        borderRadius(resolved / 2f)
        overflow(true)
        notifyChanged()
    }

    fun baseColor(color: Color) {
        fillColor = color
        notifyChanged()
    }

    /** 打开后做透明度脉冲。默认关闭，sDynamic / JS 请保持 false。 */
    fun animated(enabled: Boolean) {
        animatedEnabled = enabled
        notifyChanged()
    }

    private fun notifyChanged() {
        onStyleChanged?.invoke()
    }
}

fun ViewContainer<*, *>.SkeletonCircle(init: SkeletonCircleView.() -> Unit) {
    addChild(SkeletonCircleView(), init)
}
