package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NeonAmberGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonDarkBackground
import com.example.ui.theme.NeonDarkBorder
import com.example.ui.theme.NeonDarkSurface
import com.example.ui.theme.NeonDarkSurfaceVariant
import com.example.ui.theme.NeonHotPink
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonOrangePrimary
import com.example.ui.theme.NeonYellowElectric
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextWhiteHighContrast

@Composable
fun StreakDisplay(
    streakDays: Int,
    bestStreak: Int,
    totalDays: Int,
    isStudiedToday: Boolean,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Dynamic pulsating neon flame transition
    val infiniteTransition = rememberInfiniteTransition(label = "flamePulsate")
    val flameScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flameScale"
    )

    val neonGlowColors = if (streakDays > 0 || isStudiedToday) {
        listOf(
            NeonOrangePrimary,
            NeonYellowElectric,
            NeonCyan,
            NeonHotPink
        )
    } else {
        listOf(NeonDarkBorder, NeonDarkBorder)
    }

    // EXPANDED CONTAINER: Generous paddings, larger bounds, high-contrast neon borders
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NeonDarkSurfaceVariant.copy(alpha = 0.95f),
                        NeonDarkSurface
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(colors = neonGlowColors),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(vertical = 36.dp, horizontal = 24.dp)
            .testTag("streak_container"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top row with status pill and Share Progress button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Live Status Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isStudiedToday) NeonLime.copy(alpha = 0.18f)
                        else NeonYellowElectric.copy(alpha = 0.12f)
                    )
                    .border(
                        1.dp,
                        if (isStudiedToday) NeonLime else NeonYellowElectric.copy(alpha = 0.5f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = if (isStudiedToday) "✓ RACHA AL DÍA" else "⚡ EN ESPERA DE HOY",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isStudiedToday) NeonLime else NeonYellowElectric,
                    letterSpacing = 1.sp
                )
            }

            // Prominent Share Progress Button
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = NeonCyan),
                        onClick = onShareClick
                    )
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                NeonCyan.copy(alpha = 0.20f),
                                NeonHotPink.copy(alpha = 0.20f)
                            )
                        )
                    )
                    .border(
                        1.5.dp,
                        Brush.horizontalGradient(listOf(NeonCyan, NeonHotPink)),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 8.dp)
                    .testTag("btn_share_progress"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir progreso",
                    tint = NeonCyan,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Compartir",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = TextWhiteHighContrast
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Large Flame Badge with Multi-Stop Glowing Aura
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(flameScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = if (streakDays > 0 || isStudiedToday) {
                            listOf(
                                NeonOrangePrimary.copy(alpha = 0.45f),
                                NeonYellowElectric.copy(alpha = 0.25f),
                                NeonHotPink.copy(alpha = 0.15f),
                                Color.Transparent
                            )
                        } else {
                            listOf(Color(0x33475569), Color.Transparent)
                        }
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Fuego de racha",
                modifier = Modifier
                    .size(68.dp)
                    .testTag("streak_fire_icon"),
                tint = if (streakDays > 0 || isStudiedToday) NeonOrangePrimary else TextMutedSlate
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Huge Neon Typography Counter (100sp)
        AnimatedContent(
            targetState = streakDays,
            transitionSpec = {
                slideInVertically { height -> height } togetherWith
                        slideOutVertically { height -> -height }
            },
            label = "streakCounterAnim"
        ) { targetStreak ->
            Text(
                text = "$targetStreak",
                fontSize = 102.sp,
                fontWeight = FontWeight.Black,
                color = if (targetStreak > 0 || isStudiedToday) NeonYellowElectric else TextLightSlate,
                letterSpacing = (-3).sp,
                modifier = Modifier.testTag("streak_number_text")
            )
        }

        Text(
            text = if (streakDays == 1) "DÍA CONSECUTIVO" else "DÍAS CONSECUTIVOS",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = if (streakDays > 0) NeonOrangePrimary else TextMutedSlate,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Larger Stats Cards Row: Récord & Total
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ExpandedStatCard(
                icon = Icons.Default.EmojiEvents,
                title = "RÉCORD HISTÓRICO",
                value = "$bestStreak ${if (bestStreak == 1) "día" else "días"}",
                accentColor = NeonCyan,
                testTag = "stat_best_streak",
                modifier = Modifier.weight(1f)
            )

            ExpandedStatCard(
                icon = Icons.Default.School,
                title = "TOTAL ESTUDIADO",
                value = "$totalDays ${if (totalDays == 1) "día" else "días"}",
                accentColor = NeonHotPink,
                testTag = "stat_total_days",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ExpandedStatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    accentColor: Color,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(NeonDarkBackground.copy(alpha = 0.7f))
            .border(1.5.dp, accentColor.copy(alpha = 0.45f), RoundedCornerShape(20.dp))
            .padding(vertical = 14.dp, horizontal = 12.dp)
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextMutedSlate,
                    letterSpacing = 1.sp
                )
                Text(
                    text = value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = TextWhiteHighContrast
                )
            }
        }
    }
}
