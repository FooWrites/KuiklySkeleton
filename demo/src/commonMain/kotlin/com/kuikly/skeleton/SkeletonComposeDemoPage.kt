package com.kuikly.skeleton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tencent.kuikly.compose.ComposeContainer
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.width
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Button
import com.tencent.kuikly.compose.material3.ButtonDefaults
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.setContent
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.tencent.kuikly.core.annotations.Page

@Page("skeleton_compose_demo")
class SkeletonComposeDemoPage : ComposeContainer() {
    override fun willInit() {
        super.willInit()
        setContent {
            SkeletonComposeDemo()
        }
    }
}

@Composable
private fun SkeletonComposeDemo() {
    var motionOn by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(text = "骨架占位", fontSize = 20.sp, color = Color(0xFF1F2328))
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "Compose DSL 薄封装，形状与自研 DSL 相同。", fontSize = 14.sp, color = Color(0xFF667085))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "列表", fontSize = 16.sp, color = Color(0xFF1F2328))
        Spacer(modifier = Modifier.height(12.dp))
        repeat(3) { index ->
            Row(modifier = Modifier.fillMaxWidth()) {
                SkeletonCircle(
                    modifier = Modifier.width(44.dp).height(44.dp),
                    size = 44f,
                    animated = motionOn,
                )
                Spacer(modifier = Modifier.width(12.dp))
                SkeletonText(
                    modifier = Modifier.weight(1f),
                    lines = 2,
                    lineHeight = 12f,
                    lineSpacing = 8f,
                    lastLineWidthRatio = 0.5f,
                    animated = motionOn,
                )
            }
            if (index < 2) {
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "详情", fontSize = 16.sp, color = Color(0xFF1F2328))
        Spacer(modifier = Modifier.height(12.dp))
        SkeletonBox(
            modifier = Modifier.fillMaxWidth().height(160.dp),
            cornerRadius = 12f,
            animated = motionOn,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SkeletonText(
            modifier = Modifier.fillMaxWidth(),
            lines = 3,
            lineHeight = 14f,
            lineSpacing = 10f,
            lastLineWidthRatio = 0.42f,
            animated = motionOn,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { motionOn = !motionOn },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF2F4F7),
                contentColor = Color(0xFF1F2328),
            ),
        ) {
            Text(
                text = if (motionOn) "闪光开" else "闪光关",
                fontSize = 15.sp,
                color = Color(0xFF1F2328),
            )
        }
    }
}
