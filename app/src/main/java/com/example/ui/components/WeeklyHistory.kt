package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.DayStatus
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonDarkBackground
import com.example.ui.theme.NeonDarkBorder
import com.example.ui.theme.NeonDarkSurface
import com.example.ui.theme.NeonDarkSurfaceVariant
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonHotPink
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonOrangePrimary
import com.example.ui.theme.NeonYellowElectric
import com.example.ui.theme.TextDarkPlaceholder
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextWhiteHighContrast

@Composable
fun WeeklyHistory(
    lastSevenDays: List<DayStatus>,
    studiedCount: Int,
    onDayClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (lastSevenDays.isNotEmpty()) studiedCount.toFloat() / 7f else 0f
    val percentage = (progress * 100).toInt()

    // EXPANDED CONTAINER with high-voltage neon cyan border
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(NeonDarkSurface)
            .border(
                width = 1.8.dp,
                brush = Brush.linearGradient(
                    colors = listOf(NeonCyan.copy(alpha = 0.6f), NeonDarkBorder)
                ),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(24.dp)
            .testTag("weekly_history_section")
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(NeonCyan.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendario",
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "ÚLTIMOS 7 DÍAS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonCyan,
                    letterSpacing = 1.5.sp
                )
            }

            // Ratio badge with neon glow
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(NeonCyan.copy(alpha = 0.15f))
                    .border(1.5.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "$studiedCount de 7 ($percentage%)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Vibrant Neon Multi-Color Progress Bar (8dp height)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(NeonDarkSurfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress.coerceIn(0f, 1f))
                    .height(8.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(NeonCyan, NeonLime, NeonYellowElectric, NeonOrangePrimary)
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Row of 7 Days with larger bubbles (48dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            lastSevenDays.forEach { day ->
                DayBubbleItem(
                    day = day,
                    onClick = { onDayClick(day.dateIso) }
                )
            }
        }
    }
}

@Composable
private fun DayBubbleItem(
    day: DayStatus,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            day.isStudied && day.isToday -> NeonYellowElectric
            day.isStudied -> NeonLime
            day.isToday -> NeonCyan
            else -> NeonDarkBorder
        },
        animationSpec = tween(350),
        label = "bubbleBorder"
    )

    val backgroundColor by animateColorAsState(
        targetValue = when {
            day.isStudied && day.isToday -> NeonOrangePrimary.copy(alpha = 0.35f)
            day.isStudied -> NeonLime.copy(alpha = 0.22f)
            day.isToday -> NeonCyan.copy(alpha = 0.18f)
            else -> NeonDarkSurfaceVariant.copy(alpha = 0.65f)
        },
        animationSpec = tween(350),
        label = "bubbleBackground"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = NeonCyan),
                onClick = onClick
            )
            .testTag("day_item_${day.dateIso}")
            .padding(horizontal = 2.dp)
    ) {
        // Day abbreviation
        Text(
            text = day.dayNameShort,
            fontSize = 12.sp,
            fontWeight = if (day.isToday) FontWeight.Black else FontWeight.ExtraBold,
            color = if (day.isToday) NeonCyan else TextMutedSlate
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Large 48dp node with neon border
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .border(
                    width = if (day.isToday) 2.5.dp else 1.5.dp,
                    color = borderColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (day.isStudied) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = "Estudiado",
                    tint = if (day.isToday) NeonYellowElectric else NeonLime,
                    modifier = Modifier.size(26.dp)
                )
            } else {
                Text(
                    text = "${day.dayNumber}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (day.isToday) NeonCyan else TextDarkPlaceholder
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // "HOY" tag
        if (day.isToday) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(NeonCyan)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "HOY",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonDarkBackground
                )
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
