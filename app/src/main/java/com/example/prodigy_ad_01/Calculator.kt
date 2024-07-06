package com.example.prodigy_ad_01

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prodigy_ad_01.MainActivity.Companion.TAG
import com.example.prodigy_ad_01.ui.theme.PRODIGY_AD_01Theme

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    PRODIGY_AD_01Theme {
        CalculatorFrame()
    }
}

@Composable
fun Bg(img: Int) {
    Image(
        painter = painterResource(id = img),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CalculatorFrame() {
    val viewModel: CalculatorViewModel = viewModel()
    val expression by viewModel.expression.observeAsState("")
    val result by viewModel.result.observeAsState("")

    Bg(R.drawable.bg)
    Column(
        horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxSize()
    ) {
        Row(
            Modifier.padding(20.dp)
        ) {
            Column {
                ScreenUi(
                    expression = expression, result = result, modifier = Modifier.weight(0.3f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonColumns(
                    onButtonClick = { viewModel.onButtonClick(it) },
                    onClear = { viewModel.onAllClear() },
                    onEquals = { viewModel.onEquals() },
                    modifier = Modifier.weight(0.7f)
                )
            }
        }
    }
}

@Composable
private fun ScreenUi(
    expression: String, result: String, modifier: Modifier = Modifier
) {
    LaunchedEffect(expression, result) {
        Log.d(TAG, "ScreenUi: Expression - $expression, Result - $result")
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        DisplayText(
            expression,
            25,
            R.color.secondary,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
        )
        DisplayText(
            result,
            48,
            R.color.white,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun ButtonColumns(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit,
    onClear: () -> Unit,
    onEquals: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonRow(
            t1 = "C",
            t3 = "/",
            t4 = "*",
            modifier = Modifier.padding(top = 20.dp),
            bg = R.color.special_blue,
            isFirstRow = true,
            onButtonClick = onButtonClick,
            onClear = onClear,
        )

        ButtonRow(
            t1 = "7",
            t2 = "8",
            t3 = "9",
            t4 = "-",
            modifier = Modifier.padding(20.dp),
            onButtonClick = onButtonClick,
        )

        ButtonRow(
            t1 = "4",
            t2 = "5",
            t3 = "6",
            t4 = "+",
            height = 96,
            modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 0.dp),
            onButtonClick = onButtonClick,
        )

        Row(
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp)
        ) {
            Column {
                ButtonRow(
                    t1 = "1", t2 = "2", t3 = "3", modifier = Modifier, onButtonClick = onButtonClick
                )
                Spacer(modifier = Modifier.height(20.dp))
                LastButtonRow(
                    modifier = Modifier,
                    onButtonClick = onButtonClick,
                )
            }
            CustomButton(
                text = "=",
                colorId = R.color.special_blue,
                bgColor = R.color.special_black,
                fontSize = 32,
                height = 125,
                modifier = Modifier.padding(top = 20.dp),
                onClick = onEquals,
            )
        }
    }
}

@Composable
private fun LastButtonRow(
    modifier: Modifier, onButtonClick: (String) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        CalButton("0", R.color.special_blue, R.color.special_black, 20, width = 144) {
            onButtonClick("0")
        }
        Spacer(modifier = Modifier.width(20.dp))
        CalButton(".", R.color.special_blue, R.color.special_black, 20, width = 64) {
            onButtonClick(".")
        }
    }
}

@Composable
private fun ButtonRow(
    t1: String = "",
    t2: String = "",
    t3: String = "",
    t4: String = "",
    width: Int = 62,
    height: Int = 62,
    modifier: Modifier,
    fg: Int = R.color.special_blue,
    bg: Int = R.color.special_black,
    isFirstRow: Boolean = false,
    onButtonClick: (String) -> Unit = {},
    onClear: (() -> Unit)? = null
) {
    val viewModel: CalculatorViewModel = viewModel()

    val state = remember {
        mutableStateOf(t2)
    }
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        if (t1 == "C") {
            CalButton(t1,
                R.color.special_blue,
                R.color.special_black,
                25,
                onClick = { onClear?.invoke() })
        } else {
            CalButton(t1,
                R.color.special_blue,
                R.color.special_black,
                25,
                onClick = { onButtonClick(t1) })
        }
        Spacer(modifier = Modifier.width(20.dp))
        if (isFirstRow) {
            ImageButton(onClear = {
                viewModel.onClear()
            })
        } else {
            CalButton(state.value, R.color.special_blue, R.color.special_black, 25) {
                onButtonClick(t2)
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        val t3Fg = if (t3 == "/") R.color.white else fg
        CalButton(t3, t3Fg, bg, 25) {
            onButtonClick(t3)
        }
        Spacer(modifier = Modifier.width(20.dp))
        if (t4.isNotEmpty()) {
            CustomButton(
                text = t4,
                colorId = R.color.white,
                bgColor = R.color.special_blue,
                fontSize = 25,
                height = height,
                width = width
            ) {
                onButtonClick(t4)
            }
        }
    }
}


@Composable
fun ImageButton(onClear: () -> Unit) {

    FilledTonalButton(onClick = onClear,
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.special_black)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(62.dp)
            .height(62.dp),
        content = {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        })
}

@Composable
private fun CalButton(
    text: String,
    colorId: Int,
    bgColor: Int,
    fontSize: Int,
    height: Int = 62,
    width: Int = 62,
    onClick: () -> Unit
) {
    val textColor = colorResource(id = colorId)
    val buttonBgColor = colorResource(id = bgColor)

    FilledTonalButton(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(buttonBgColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        content = {
            Text(
                text = text,
                fontSize = fontSize.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        })
}

@Composable
fun CustomButton(
    text: String,
    colorId: Int,
    bgColor: Int,
    fontSize: Int,
    height: Int = 62,
    width: Int = 62,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val textColor = colorResource(id = colorId)
    val buttonBgColor = colorResource(id = bgColor)

    FilledTonalButton(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(buttonBgColor),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .width(width.dp)
            .height(height.dp),
        content = {
            Text(
                text = text,
                fontSize = fontSize.sp,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        })
}

@Composable
private fun DisplayText(
    text: String, fontSize: Int, colorId: Int, modifier: Modifier = Modifier
) {
    val textColor = colorResource(id = colorId)
    Text(
        text = text, style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            color = textColor
        ), modifier = modifier.verticalScroll(rememberScrollState())
    )
}
