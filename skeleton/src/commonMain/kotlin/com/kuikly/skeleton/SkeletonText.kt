package com.kuikly.skeleton

import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ContainerAttr
import com.tencent.kuikly.core.base.ViewConst
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.event.Event
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.View

/**
 * 多行文本形占位条。行数上限见 [SkeletonDefaults.maxTextLines]，末行宽度由比例决定。
 */
class SkeletonTextView : ViewContainer<SkeletonTextAttr, Event>() {
    private val motion = SkeletonMotion()
    private var fill by observable(SkeletonDefaults.baseColor)
    private var lineCount by observable(3)
    private var lineHeightPx by observable(12f)
    private var lineSpacingPx by observable(8f)
    private var lastRatio by observable(0.62f)

    override fun createAttr(): SkeletonTextAttr {
        return SkeletonTextAttr().apply {
            flexDirectionColumn()
        }
    }

    override fun createEvent(): Event = Event()

    override fun viewName(): String = ViewConst.TYPE_VIEW

    override fun didInit() {
        super.didInit()
        attr.onStyleChanged = { syncStyle() }
        syncStyle()
        mountLines()
        motion.markMounted()
    }

    private fun syncStyle() {
        fill = attr.fillColor
        lineCount = attr.lineCountValue
        lineHeightPx = attr.lineHeightValue
        lineSpacingPx = attr.lineSpacingValue
        lastRatio = attr.lastLineRatioValue
        motion.applyEnabled(attr.animatedEnabled)
    }

    private fun mountLines() {
        val ctx = this
        for (index in 0 until SkeletonDefaults.maxTextLines) {
            vif({ index < ctx.lineCount }) {
                View {
                    attr {
                        flexDirectionRow()
                        height(ctx.lineHeightPx)
                        marginBottom(if (index >= ctx.lineCount - 1) 0f else ctx.lineSpacingPx)
                    }
                    View {
                        attr {
                            val ratio = if (index >= ctx.lineCount - 1) ctx.lastRatio else 1f
                            flex(ratio)
                            height(ctx.lineHeightPx)
                            skeletonSurface(ctx.fill, ctx.lineHeightPx / 2f, ctx.motion)
                        }
                        if (index == 0) {
                            event {
                                bindSkeletonPulse(ctx.motion)
                            }
                        }
                    }
                    vif({ index >= ctx.lineCount - 1 && ctx.lastRatio < 0.999f }) {
                        View {
                            attr {
                                flex((1f - ctx.lastRatio).coerceAtLeast(0.001f))
                            }
                        }
                    }
                }
            }
        }
    }
}

class SkeletonTextAttr : ContainerAttr() {
    internal var fillColor: Color = SkeletonDefaults.baseColor
    internal var lineCountValue: Int = 3
    internal var lineHeightValue: Float = 12f
    internal var lineSpacingValue: Float = 8f
    internal var lastLineRatioValue: Float = 0.62f
    internal var animatedEnabled: Boolean = false
    internal var onStyleChanged: (() -> Unit)? = null

    fun lines(count: Int) {
        lineCountValue = count.coerceIn(1, SkeletonDefaults.maxTextLines)
        notifyChanged()
    }

    fun lineHeight(height: Float) {
        lineHeightValue = height.coerceAtLeast(1f)
        notifyChanged()
    }

    fun lineSpacing(spacing: Float) {
        lineSpacingValue = spacing.coerceAtLeast(0f)
        notifyChanged()
    }

    /** 末行宽度相对整行的比例，范围 0.2 到 1。 */
    fun lastLineWidthRatio(ratio: Float) {
        lastLineRatioValue = ratio.coerceIn(0.2f, 1f)
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

fun ViewContainer<*, *>.SkeletonText(init: SkeletonTextView.() -> Unit) {
    addChild(SkeletonTextView(), init)
}
