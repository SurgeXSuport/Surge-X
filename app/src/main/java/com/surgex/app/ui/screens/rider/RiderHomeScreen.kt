package com.surgex.app.ui.screens.rider

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeSurfaceLight
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun RiderHomeScreen(
    onChooseRide: () -> Unit,
    onSwitchToDriver: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {
        MapFoundation()
        TopBar(onSwitchToDriver = onSwitchToDriver, onBack = onBack)
        RideRequestSheet(onChooseRide = onChooseRide)
    }
}

@Composable
private fun MapFoundation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "MAP",
                color = Color.White.copy(alpha = 0.06f),
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "LOCATION READY",
                color = Color.White.copy(alpha = 0.05f),
                fontSize = 10.sp,
                letterSpacing = 3.sp
            )
        }
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(SurgeWhite)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun TopBar(
    onSwitchToDriver: () -> Unit,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.72f))
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "←", color = SurgeWhite, fontSize = 18.sp)
        }

        Text(
            text = "SurgeX",
            color = SurgeWhite,
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold
        )

        // Switch to Driver mode
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A))
                .clickable { onSwitchToDriver() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🚗", fontSize = 18.sp)
        }
    }
}

@Composable
private fun RideRequestSheet(onChooseRide: () -> Unit) {
    var pickupLocation by remember { mutableStateOf("Current location") }
    var destination by remember { mutableStateOf("") }
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            color = Color(0xFF0A0A0A)
        ) {
            Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 20.dp)) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF2A2A2A))
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(22.dp))
                Text(text = "Where to?", color = SurgeWhite, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(16.dp))
                LocationInput(
                    label = "Pickup location", 
                    value = pickupLocation,
                    onValueChange = { pickupLocation = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LocationInput(
                    label = "Destination", 
                    value = destination,
                    onValueChange = { destination = it }
                )
                Spacer(modifier = Modifier.height(22.dp))
                Text(text = "Quick destinations", color = SurgeGrey, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickDestination(title = "Home", modifier = Modifier.weight(1f)) { destination = "Home" }
                    QuickDestination(title = "Work", modifier = Modifier.weight(1f)) { destination = "Work" }
                    QuickDestination(title = "Recent", modifier = Modifier.weight(1f)) { destination = "Recent" }
                }
                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = onChooseRide,
                    modifier = Modifier.fillMaxWidth().height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SurgeWhite, contentColor = SurgeBlack),
                    enabled = destination.isNotEmpty()
                ) {
                    Text(text = "CHOOSE A RIDE", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun LocationInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf(value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurgeSurface)
            .clickable { isEditing = !isEditing }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(SurgeWhite))
        Spacer(modifier = Modifier.width(14.dp))
        
        if (isEditing) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, color = SurgeGrey, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(2.dp))
                BasicTextField(
                    value = inputText,
                    onValueChange = { 
                        inputText = it
                        onValueChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = SurgeWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium),
                    decorationBox = { innerTextField ->
                        if (inputText.isEmpty()) {
                            Text(text = "Type $label", color = SurgeGrey, fontSize = 14.sp)
                        }
                        innerTextField()
                    }
                )
            }
        } else {
            Column {
                Text(text = label, color = SurgeGrey, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = inputText.ifEmpty { "Tap to enter" }, color = SurgeWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun QuickDestination(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(SurgeSurfaceLight)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = SurgeWhite, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
