
package com.example.glasslaunch

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GlassHome { packageName ->
                packageManager.getLaunchIntentForPackage(packageName)
                    ?.let { startActivity(it) }
            }
        }
    }
}

private data class AppTile(
    val label: String,
    val packageName: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
private fun GlassHome(onLaunch: (String) -> Unit) {
    val apps = listOf(
        AppTile("Telefone", "com.samsung.android.dialer",
            Icons.Default.Call, Color(0xFF36C875)),
        AppTile("Mensagens", "com.google.android.apps.messaging",
            Icons.Default.Chat, Color(0xFF438AFF)),
        AppTile("Câmera", "com.sec.android.app.camera",
            Icons.Default.CameraAlt, Color(0xFF555966)),
        AppTile("Internet", "com.sec.android.app.sbrowser",
            Icons.Default.Language, Color(0xFF48A8F5)),
        AppTile("Config.", "com.android.settings",
            Icons.Default.Settings, Color(0xFF858B98)),
        AppTile("Fotos", "com.google.android.apps.photos",
            Icons.Default.Photo, Color(0xFFDB7B9D)),
        AppTile("Play Store", "com.android.vending",
            Icons.Default.ShoppingBag, Color(0xFF49B6A4)),
        AppTile("Relógio", "com.sec.android.app.clockpackage",
            Icons.Default.Schedule, Color(0xFF8D79D9))
    )

    Box(
        Modifier.fillMaxSize().background(
            Brush.linearGradient(
                listOf(
                    Color(0xFF101B38),
                    Color(0xFF526D9B),
                    Color(0xFFCAB8DC)
                )
            )
        )
    ) {
        Column(
            Modifier.fillMaxSize().padding(
                start = 22.dp, end = 22.dp,
                top = 36.dp, bottom = 22.dp
            )
        ) {
            Text(
                "Olá",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Seu espaço, do seu jeito.",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 15.sp
            )

            Spacer(Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(apps) { app ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            onLaunch(app.packageName)
                        }
                    ) {
                        Box(
                            Modifier.size(58.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color.White.copy(alpha = 0.55f),
                                            app.color
                                        )
                                    )
                                )
                                .padding(1.dp)
                                .clip(RoundedCornerShape(17.dp))
                                .background(app.color),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                app.icon,
                                contentDescription = app.label,
                                tint = Color.White,
                                modifier = Modifier.size(29.dp)
                            )
                        }

                        Spacer(Modifier.height(7.dp))

                        Text(
                            app.label,
                            color = Color.White,
                            fontSize = 10.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White.copy(alpha = 0.24f))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                apps.take(4).forEach { app ->
                    Box(
                        Modifier.size(48.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(app.color)
                            .clickable { onLaunch(app.packageName) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            app.icon,
                            contentDescription = app.label,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

