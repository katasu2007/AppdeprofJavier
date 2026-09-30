package com.example.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.MotivationalCard
import com.example.ui.components.StreakDisplay
import com.example.ui.components.StudyButton
import com.example.ui.components.WeeklyHistory
import com.example.ui.theme.NeonAmberGlow
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
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextWhiteHighContrast
import kotlinx.coroutines.delay

@Composable
fun RachaScreen(
    viewModel: RachaViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current

    var showCelebrationBanner by remember { mutableStateOf(false) }

    // Listen to celebration triggers
    LaunchedEffect(uiState.celebrationTrigger) {
        if (uiState.celebrationTrigger > 0) {
            showCelebrationBanner = true
            triggerVibration(context)
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            delay(4000)
            showCelebrationBanner = false
        }
    }

    val shareStreakAction = {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        shareProgress(
            context = context,
            currentStreak = uiState.currentStreak,
            bestStreak = uiState.bestStreak,
            totalDays = uiState.totalDaysStudied
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(NeonDarkBackground),
        containerColor = NeonDarkBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentAlignment = Alignment.TopCenter
        ) {
            // EXPANDED MAIN CONTAINER (increased widthIn to 760.dp for larger layout)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 760.dp)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 22.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Bar: App Name, Date & Quick Share Icon
                RachaTopHeader(
                    todayFormatted = uiState.todayFormatted,
                    hasStudiedToday = uiState.isStudiedToday,
                    onShareClick = shareStreakAction
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Celebratory Alert Banner
                AnimatedVisibility(
                    visible = showCelebrationBanner,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    CelebrationBanner(
                        streak = uiState.currentStreak,
                        onDismiss = { showCelebrationBanner = false }
                    )
                }

                if (showCelebrationBanner) {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 1. Contador Grande de Días Consecutivos (Expanded Container with Share Button)
                StreakDisplay(
                    streakDays = uiState.currentStreak,
                    bestStreak = uiState.bestStreak,
                    totalDays = uiState.totalDaysStudied,
                    isStudiedToday = uiState.isStudiedToday,
                    onShareClick = shareStreakAction
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 2. Botón «Hoy sí estudié»
                StudyButton(
                    isStudiedToday = uiState.isStudiedToday,
                    onStudyClick = {
                        viewModel.markTodayStudied()
                    },
                    onUndoClick = {
                        viewModel.undoTodayStudied()
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // 3. Lista de los últimos siete días (Expanded)
                WeeklyHistory(
                    lastSevenDays = uiState.lastSevenDays,
                    studiedCount = uiState.studiedDaysThisWeek,
                    onDayClick = { dateIso ->
                        viewModel.toggleDay(dateIso)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Dedicated Share Progress Banner Button
                ShareProgressBanner(
                    currentStreak = uiState.currentStreak,
                    onShareClick = shareStreakAction
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 4. Mensaje motivador distinto cada vez (Expanded)
                MotivationalCard(
                    quote = uiState.currentQuote,
                    onShuffleQuote = {
                        viewModel.shuffleQuote()
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Offline & Privacy Badge Footer
                OfflinePrivacyBadge()

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun RachaTopHeader(
    todayFormatted: String,
    hasStudiedToday: Boolean,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(NeonOrangePrimary, NeonYellowElectric)
                        )
                    )
                    .border(
                        1.5.dp,
                        NeonYellowElectric.copy(alpha = 0.6f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = "Logo RACHA",
                    tint = NeonDarkBackground,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "RACHA",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = TextWhiteHighContrast,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "HÁBITO DE ESTUDIO",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonOrangePrimary,
                    letterSpacing = 2.sp
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Today's Date Tag
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(NeonDarkSurface)
                    .border(1.2.dp, NeonDarkBorder, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Text(
                    text = todayFormatted.ifEmpty { "Hoy" },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (hasStudiedToday) NeonLime else TextLightSlate
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Quick Share IconButton
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(NeonCyan.copy(alpha = 0.15f))
                    .border(1.2.dp, NeonCyan.copy(alpha = 0.4f), CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = NeonCyan),
                        onClick = onShareClick
                    )
                    .testTag("btn_top_share"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir racha",
                    tint = NeonCyan,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun ShareProgressBanner(
    currentStreak: Int,
    onShareClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        NeonDarkSurfaceVariant,
                        NeonDarkSurface
                    )
                )
            )
            .border(
                width = 1.5.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(NeonCyan, NeonHotPink)
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = NeonCyan),
                onClick = onShareClick
            )
            .padding(vertical = 18.dp, horizontal = 22.dp)
            .testTag("btn_share_banner")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(NeonCyan, NeonHotPink)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir",
                        tint = NeonDarkBackground,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "COMPARTIR MI PROGRESO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TextWhiteHighContrast,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Mostrá tu racha de $currentStreak ${if (currentStreak == 1) "día" else "días"} e inspirá a otros",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextLightSlate
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(NeonCyan)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "ENVIAR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonDarkBackground
                )
            }
        }
    }
}

@Composable
private fun CelebrationBanner(
    streak: Int,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        NeonOrangePrimary,
                        NeonYellowElectric
                    )
                )
            )
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .testTag("celebration_banner")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Chispas",
                    tint = NeonDarkBackground,
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "¡DÍA SUMADO A TU RACHA!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = NeonDarkBackground,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "¡Llevás $streak ${if (streak == 1) "día consecutivo" else "días consecutivos"}! Imparable.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar aviso",
                    tint = NeonDarkBackground,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun OfflinePrivacyBadge() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(NeonDarkSurface)
            .border(1.2.dp, NeonDarkBorder, RoundedCornerShape(22.dp))
            .padding(horizontal = 16.dp, vertical = 9.dp)
            .testTag("offline_privacy_badge"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "Privacidad",
            tint = NeonCyan,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "100% Offline • Sin anuncios • Datos en tu teléfono",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextMutedSlate
        )
    }
}

private fun shareProgress(
    context: Context,
    currentStreak: Int,
    bestStreak: Int,
    totalDays: Int
) {
    try {
        val shareText = buildString {
            append("🔥 ¡Llevo una RACHA de $currentStreak ")
            append(if (currentStreak == 1) "día consecutivo" else "días consecutivos")
            append(" de estudio en RACHA! ⚡📚\n\n")
            append("🏆 Récord histórico: $bestStreak días\n")
            append("📈 Total de sesiones: $totalDays\n\n")
            append("¡La disciplina y la constancia no se negocian! 💪🔥\n#RACHA #Estudio #Disciplina")
        }

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val chooser = Intent.createChooser(sendIntent, "Compartir mi progreso de RACHA")
        context.startActivity(chooser)
    } catch (_: Exception) {
        // Fallback gracefully
    }
}

private fun triggerVibration(context: Context) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            val vibrator = vibratorManager?.defaultVibrator
            vibrator?.vibrate(VibrationEffect.createOneShot(140, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(140, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(140)
            }
        }
    } catch (_: Exception) {
        // Fallback gracefully
    }
}
