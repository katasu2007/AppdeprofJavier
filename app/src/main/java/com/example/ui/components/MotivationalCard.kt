package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MotivationalQuote
import com.example.ui.theme.NeonAmberGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonDarkBorder
import com.example.ui.theme.NeonDarkSurface
import com.example.ui.theme.NeonDarkSurfaceVariant
import com.example.ui.theme.NeonHotPink
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.NeonYellowElectric
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextWhiteHighContrast

@Composable
fun MotivationalCard(
    quote: MotivationalQuote,
    onShuffleQuote: () -> Unit,
    modifier: Modifier = Modifier
) {
    // EXPANDED MOTIVATIONAL CONTAINER with hyper-vivid Neon Hot Pink and Laser Purple borders
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NeonDarkSurface,
                        NeonDarkSurfaceVariant.copy(alpha = 0.85f)
                    )
                )
            )
            .border(
                width = 1.8.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        NeonHotPink,
                        NeonPurple,
                        NeonCyan.copy(alpha = 0.5f)
                    )
                ),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(26.dp)
            .testTag("motivational_quote_card")
    ) {
        // Header: Category pill & Shuffle action button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Chispas",
                    tint = NeonHotPink,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(NeonHotPink.copy(alpha = 0.18f))
                        .border(1.2.dp, NeonHotPink.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = quote.category,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = NeonHotPink,
                        letterSpacing = 1.2.sp
                    )
                }
            }

            // Shuffle Button: "Nuevo mensaje"
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = NeonCyan),
                        onClick = onShuffleQuote
                    )
                    .background(NeonCyan.copy(alpha = 0.14f))
                    .border(1.2.dp, NeonCyan.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .testTag("btn_shuffle_quote"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Sync,
                    contentDescription = "Cambiar frase",
                    tint = NeonCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Nuevo mensaje",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Large Typography Quote
        AnimatedContent(
            targetState = quote,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "quoteAnim"
        ) { targetQuote ->
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = "Comillas",
                        tint = NeonYellowElectric.copy(alpha = 0.85f),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = targetQuote.text,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhiteHighContrast,
                        lineHeight = 28.sp,
                        modifier = Modifier.testTag("motivational_quote_text")
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "— ${targetQuote.author}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = TextLightSlate,
                    modifier = Modifier
                        .padding(start = 38.dp)
                        .testTag("motivational_quote_author")
                )
            }
        }
    }
}
