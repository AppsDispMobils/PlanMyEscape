package com.example.sprint01.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.sprint01.R
import org.w3c.dom.Text
@Composable
fun TermsConditionsScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )   {

        Spacer(modifier = Modifier.height(20.dp))
        //titulo
        Text(
            text = "Terms and Conditions",
            style =  TextStyle(fontSize = 32.sp, color = Color.Black)
        )
        HorizontalDivider()
        Spacer(modifier = Modifier.height(20.dp))
        //Terminos y condiciones
        Text(
            text = stringResource(id = R.string.terms_and_condi0),
            style = TextStyle(fontSize = 18.sp, color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text =  stringResource(id = R.string.terms_and_condi1),
            style = TextStyle(fontSize = 18.sp, color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text =  stringResource(id = R.string.terms_and_condi2),
            style = TextStyle(fontSize = 18.sp, color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text =  stringResource(id = R.string.terms_and_condi3),
            style = TextStyle(fontSize = 18.sp, color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Un botón back homeScreen
        Button(
            onClick = { navController.navigate("home")},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(text = "Go Back")
        }

    }
}


