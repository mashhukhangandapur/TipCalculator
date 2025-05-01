package com.example.tipcalculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.components.InputField
import com.example.tipcalculator.utils.calculateTotalTip
import com.example.tipcalculator.utils.totalPerPerson
import com.example.tipcalculator.widgets.RoundedIconBar

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(), // Fill entire screen
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Optional: inner padding
            verticalArrangement = Arrangement.Top
        ) {
                MainSection()

        }
    }
}


@Composable
fun TopSection(totalPerPerson : Double = 0.0 ){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 8.dp)
        .padding(top = 18.dp)
        .height(250.dp)
        .clip(shape = CircleShape.copy(CornerSize(12.dp))),
        color = colorResource(id = R.color.softblue)
    ) {

        Column(modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "TOTAL PER PERSON ",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.SemiBold)

            Text(text = " ${String.format("%.2f", totalPerPerson)} PKR ",
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.ExtraBold)
        }
    }
}



@Composable
fun MainSection(
    modifier: Modifier = Modifier,
    onValChange : (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState.value * 100).toInt()

    val splitByState = remember {
        mutableStateOf(1)
    }

    val range = IntRange(start = 1, endInclusive = 100)

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

    TopSection(totalPerPerson = totalPerPersonState.value)

    Surface(
        modifier = modifier
            .padding(1.dp)
            .padding(top = 10.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(CornerSize(5.dp)),
        border = BorderStroke(width = 2.dp, color = Color.Gray)
    ) {

        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        )
        {
            InputField(
                valueState = totalBillState,
                enabled = true,
                label = "Enter Bill",
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                })
            if (validState) {
                Row(
                    modifier = Modifier.padding(6.dp).padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(120.dp))

                    Row(
                        modifier = Modifier.padding(3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        RoundedIconBar(
                            imageVector = Icons.Default.Remove,
                            onClick = {
                                if (splitByState.value > 1) splitByState.value =
                                    splitByState.value - 1 else 1
                                totalPerPersonState.value = totalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage, splitBy = splitByState.value
                                )
                            })

                        Text(
                            "${splitByState.value}",
                            modifier = Modifier.padding(start = 9.dp, top = 13.dp, end = 9.dp)
                        )

                        RoundedIconBar(
                            imageVector = Icons.Default.Add,
                            onClick = {
                                if (splitByState.value < range.last) splitByState.value =
                                    splitByState.value + 1
                                totalPerPersonState.value = totalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage, splitBy = splitByState.value
                                )
                            })
                    }
                }


                Row(modifier = Modifier.padding(horizontal = 4.dp, vertical = 14.dp)) {

                    Text("Tip ", modifier = Modifier.align(alignment = Alignment.CenterVertically))

                    Spacer(modifier = Modifier.width(200.dp))

                    Text(" ${tipAmountState.value} PKR",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text("$tipPercentage %")

                    Spacer(modifier = Modifier.height(15.dp))

                    Slider(
                        value = sliderPositionState.value,
                        onValueChange = { newVal ->
                            sliderPositionState.value = newVal
                            tipAmountState.value =
                                calculateTotalTip(totalBillState.value.toDouble(), tipPercentage)
                            val billAmount = totalBillState.value.toDoubleOrNull() ?: 0.0
                            val tipPercentage = (newVal * 100).toInt()

                            tipAmountState.value =
                                String.format("%.2f", calculateTotalTip(billAmount, tipPercentage))
                                    .toDouble()
                            totalPerPersonState.value = String.format(
                                "%.2f",
                                totalPerPerson(billAmount, tipPercentage, splitByState.value)
                            ).toDouble()
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 15.dp),
                        valueRange = 0f..1f,
                        steps = 5,
                        onValueChangeFinished = { }
                    )
                }
            }
        }
    }
}