package com.kuikly.skeleton

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.pager.Pager
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.Scroller
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View

@Page("skeleton_demo")
class SkeletonDemoPage : Pager() {
    private var motionOn by observable(false)

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                flex(1f)
                backgroundColor(Color.WHITE)
            }
            Scroller {
                attr {
                    flex(1f)
                    flexDirectionColumn()
                    padding(16f)
                    showScrollerIndicator(false)
                }
                Text {
                    attr {
                        text("骨架占位")
                        fontSize(20f)
                        fontWeightBold()
                        color(Color(0xFF1F2328L))
                    }
                }
                Text {
                    attr {
                        marginTop(6f)
                        text("列表和详情在内容返回前先占住版面。")
                        fontSize(14f)
                        color(Color(0xFF667085L))
                    }
                }
                Text {
                    attr {
                        marginTop(20f)
                        text("列表")
                        fontSize(16f)
                        fontWeightBold()
                        color(Color(0xFF1F2328L))
                    }
                }
                View {
                    attr {
                        marginTop(12f)
                    }
                    for (index in 0 until 3) {
                        View {
                            attr {
                                flexDirectionRow()
                                alignItemsCenter()
                                marginBottom(if (index == 2) 0f else 14f)
                            }
                            SkeletonCircle {
                                attr {
                                    size(44f)
                                    animated(ctx.motionOn)
                                }
                            }
                            View {
                                attr {
                                    flex(1f)
                                    marginLeft(12f)
                                }
                                SkeletonText {
                                    attr {
                                        lines(2)
                                        lineHeight(12f)
                                        lineSpacing(8f)
                                        lastLineWidthRatio(0.5f)
                                        animated(ctx.motionOn)
                                    }
                                }
                            }
                        }
                    }
                }
                Text {
                    attr {
                        marginTop(24f)
                        text("详情")
                        fontSize(16f)
                        fontWeightBold()
                        color(Color(0xFF1F2328L))
                    }
                }
                SkeletonBox {
                    attr {
                        marginTop(12f)
                        height(160f)
                        cornerRadius(12f)
                        animated(ctx.motionOn)
                    }
                }
                SkeletonText {
                    attr {
                        marginTop(16f)
                        lines(3)
                        lineHeight(14f)
                        lineSpacing(10f)
                        lastLineWidthRatio(0.42f)
                        animated(ctx.motionOn)
                    }
                }
                View {
                    attr {
                        marginTop(20f)
                        marginBottom(12f)
                        height(44f)
                        borderRadius(8f)
                        backgroundColor(Color(0xFFF2F4F7L))
                        allCenter()
                    }
                    event {
                        click {
                            ctx.motionOn = !ctx.motionOn
                        }
                    }
                    Text {
                        attr {
                            text(if (ctx.motionOn) "闪光开" else "闪光关")
                            fontSize(15f)
                            color(Color(0xFF1F2328L))
                        }
                    }
                }
            }
        }
    }
}
