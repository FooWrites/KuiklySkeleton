package com.kuikly.skeleton

import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ContainerAttr
import com.tencent.kuikly.core.base.ViewConst
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.event.Event
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.View

/**
 * 圆角矩形占位。宽高用 [ContainerAttr.width] / [ContainerAttr.height]。
 */
class SkeletonBoxView : ViewContainer<SkeletonBoxAttr, Event>() {
    private val motion = SkeletonMotion()
    private var fill by observable(SkeletonDefaults.baseColor)
    private var radiusPx by observable(SkeletonDefaults.cornerRadius)

    override fun createAttr(): SkeletonBoxAttr {
        return SkeletonBoxAttr().apply {
            overflow(true)
            borderRadius(SkeletonDefaults.cornerRadius)
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
        radiusPx = attr.radiusValue
        motion.applyEnabled(attr.animatedEnabled)
    }

    private fun mountFill() {
        val ctx = this
        View {
            attr {
                absolutePositionAllZero()
                skeletonSurface(ctx.fill, ctx.radiusPx, ctx.motion)
            }
            event {
                bindSkeletonPulse(ctx.motion)
            }
        }
    }
}

class SkeletonBoxAttr : ContainerAttr() {
    internal var fillColor: Color = SkeletonDefaults.baseColor
    internal var radiusValue: Float = SkeletonDefaults.cornerRadius
    internal var animatedEnabled: Boolean = false
    internal var onStyleChanged: (() -> Unit)? = null

    fun cornerRadius(radius: Float) {
        radiusValue = radius
        borderRadius(radius)
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

fun ViewContainer<*, *>.SkeletonBox(init: SkeletonBoxView.() -> Unit) {
    addChild(SkeletonBoxView(), init)
}
