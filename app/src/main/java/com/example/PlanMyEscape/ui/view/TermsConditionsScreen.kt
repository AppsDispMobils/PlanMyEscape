package com.example.PlanMyEscape.ui.view

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.PlanMyEscape.R

@Composable
fun TermsConditionsScreen(navController: NavHostController)  {
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
            text = stringResource(id = R.string.terms_and_condi),
            style =  TextStyle(fontSize = 32.sp, color = Color.Black)
        )
        HorizontalDivider()
        Spacer(modifier = Modifier.height(20.dp))
        //Terminos y condiciones

            Text(
                text = stringResource(id = R.string.terms_and_condi0_part1) + " " + stringResource(id = R.string.terms_and_condi0_part2),
                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
            )


        Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id = R.string.terms_and_condi1_part1) + " " + stringResource(id = R.string.terms_and_condi1_part2),
                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
            )


        Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id = R.string.terms_and_condi2_part1) + " " + stringResource(id = R.string.terms_and_condi2_part2),
                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
            )


        Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.terms_and_condi3_part1) + " " + stringResource(id = R.string.terms_and_condi3_part2),
                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
            )


        Spacer(modifier = Modifier.height(40.dp))

        // Un botón back homeScreen
        Button(
            onClick = { navController.popBackStack()},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(stringResource(id = R.string.about_usBoton))
        }

    }
}

