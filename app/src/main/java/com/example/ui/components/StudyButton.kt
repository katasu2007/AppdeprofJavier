package com.example.ui.components

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NeonAmberGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonDarkBackground
import com.example.ui.theme.NeonDarkSurface
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonHotPink
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonOrangePrimary
import com.example.ui.theme.NeonYellowElectric
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextWhiteHighContrast

@Composable
fun StudyButton(
    isStudiedToday: Boolean,
    onStudyClick: () -> Unit,
    onUndoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (!isStudiedToday) {
            // Expanded CTA Button with intense Neon gradient glow (72dp height)
            Button(
                onClick = onStudyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = NeonOrangePrimary,
                        spotColor = NeonYellowElectric
                    )
                    .testTag("btn_study_today"),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    NeonOrangePrimary,
                                    NeonYellowElectric,
                                    NeonAmberGlow
                                )
                            )
                        )
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                listOf(Color.White.copy(alpha = 0.6f), NeonYellowElectric)
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NeonDarkBackground.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Fuego",
                                tint = NeonDarkBackground,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "¡HOY SÍ ESTUDIÉ!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = NeonDarkBackground,
                            letterSpacing = 1.5.sp
                        )
                    }
                }
            }
        } else {
            // Expanded Completed State Card with Acid Lime / Emerald Neon Glow
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(NeonDarkSurface)
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(NeonLime, NeonEmerald, NeonCyan)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 20.dp, horizontal = 22.dp)
                    .testTag("card_study_completed"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(NeonLime.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completado",
                            tint = NeonLime,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "¡MISIÓN DE HOY CUMPLIDA!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = NeonLime,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Racha asegurada y registrada en tu dispositivo",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextLightSlate
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Undo Button
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color = TextMutedSlate),
                            onClick = onUndoClick
                        )
                        .background(NeonDarkBackground.copy(alpha = 0.6f))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("btn_undo_today"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = "Deshacer",
                        tint = TextMutedSlate,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Desmarcar hoy (deshacer)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMutedSlate
                    )
                }
            }
        }
    }
}
