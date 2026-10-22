package com.kuikly.skeleton

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.extension.MakeKuiklyComposeNode
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.core.base.Color

@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    cornerRadius: Float = SkeletonDefaults.cornerRadius,
    baseColor: Color = SkeletonDefaults.baseColor,
    animated: Boolean = false,
) {
    MakeKuiklyComposeNode(
        factory = { SkeletonBoxView() },
        modifier = modifier,
        viewUpdate = { view ->
            view.getViewAttr().apply {
                this.cornerRadius(cornerRadius)
                this.baseColor(baseColor)
                this.animated(animated)
            }
        },
    )
}

@Composable
fun SkeletonText(
    modifier: Modifier = Modifier,
    lines: Int = 3,
    lineHeight: Float = 12f,
    lineSpacing: Float = 8f,
    lastLineWidthRatio: Float = 0.62f,
    baseColor: Color = SkeletonDefaults.baseColor,
    animated: Boolean = false,
) {
    MakeKuiklyComposeNode(
        factory = { SkeletonTextView() },
        modifier = modifier,
        viewUpdate = { view ->
            view.getViewAttr().apply {
                this.lines(lines)
                this.lineHeight(lineHeight)
                this.lineSpacing(lineSpacing)
                this.lastLineWidthRatio(lastLineWidthRatio)
                this.baseColor(baseColor)
                this.animated(animated)
            }
        },
    )
}

@Composable
fun SkeletonCircle(
    modifier: Modifier = Modifier,
    size: Float = 40f,
    baseColor: Color = SkeletonDefaults.baseColor,
    animated: Boolean = false,
) {
    MakeKuiklyComposeNode(
        factory = { SkeletonCircleView() },
        modifier = modifier,
        viewUpdate = { view ->
            view.getViewAttr().apply {
                this.size(size)
                this.baseColor(baseColor)
                this.animated(animated)
            }
        },
    )
}
