package com.surgex.app.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun DriverDocumentScreen(
    onDocumentsUploaded: () -> Unit,
    onBack: () -> Unit
) {
    var licenseUploaded by remember { mutableStateOf(false) }
    var insuranceUploaded by remember { mutableStateOf(false) }
    var registrationUploaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "← Back",
                color = Color(0xFF303030),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onBack() }
            )
            Text(
                text = "Documents",
                color = SurgeWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.width(50.dp))
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Driver Documents",
                color = SurgeWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Upload required documents to complete verification",
                color = SurgeGrey,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // License Upload
            DocumentUploadCard(
                title = "Driver's License",
                description = "Front and back copy",
                isUploaded = licenseUploaded,
                onUpload = { licenseUploaded = true },
                icon = "🪪"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Insurance Upload
            DocumentUploadCard(
                title = "Insurance Certificate",
                description = "Valid insurance document",
                isUploaded = insuranceUploaded,
                onUpload = { insuranceUploaded = true },
                icon = "📄"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Registration Upload
            DocumentUploadCard(
                title = "Vehicle Registration",
                description = "Current registration document",
                isUploaded = registrationUploaded,
                onUpload = { registrationUploaded = true },
                icon = "🚗"
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Submit Button
            Button(
                onClick = onDocumentsUploaded,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (licenseUploaded && insuranceUploaded && registrationUploaded)
                        Color.White else Color(0xFF1C1C1C),
                    contentColor = if (licenseUploaded && insuranceUploaded && registrationUploaded)
                        Color.Black else SurgeGrey
                ),
                enabled = licenseUploaded && insuranceUploaded && registrationUploaded
            ) {
                Text(
                    text = "SUBMIT DOCUMENTS",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
private fun DocumentUploadCard(
    title: String,
    description: String,
    isUploaded: Boolean,
    onUpload: () -> Unit,
    icon: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { if (!isUploaded) onUpload() },
        shape = RoundedCornerShape(16.dp),
        color = if (isUploaded) Color(0xFF1A3A1A) else Color(0xFF0D0D0D)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = icon, fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                    Column {
                        Text(
                            text = title,
                            color = SurgeWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = description,
                            color = SurgeGrey,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            Text(
                text = if (isUploaded) "✓" else "+",
                color = if (isUploaded) Color(0xFF00E5FF) else SurgeGrey,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
