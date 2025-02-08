package com.example.cronoapp.components

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.cronoapp.R
import kotlinx.coroutines.Job

@Composable
fun MainTitle(title: String) {
    Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)

    )
}

@Composable
fun formatTime(time: Long): String{ //cuando es composable que retorna un valor el nombre debe iniciar con minuscula
    val seconds = (time / 1000) % 60
    val minutes = (time / 1000 / 60) % 60
    val hours = time / 100 / 3600

    return String.format("%02d:%02d:%02d",hours,minutes,seconds)
}

@Composable
fun CronoCard(title: String, crono: String, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onClick() }
    ){
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )
            Spacer(
                modifier = Modifier
                    .padding( vertical = 5.dp)
            )
            Row {
                Icon(
                    painter = painterResource(R.drawable.timer),
                    contentDescription = "",
                    tint = Color.Gray
                )
                Text(text = crono, fontSize = 20.sp)
            }
            Spacer(
                modifier = Modifier
                    .padding( vertical = 5.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Alert(
    title: String,
    message: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {

    BasicAlertDialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(dismissOnClickOutside = false),
    ) {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 8.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onConfirmClick) {
                        Text(text = confirmText)
                    }
                    TextButton(onClick = onDismissClick) {
                        Text(text = "Cancelar")
                    }
                }
            }
        }
    }
}

