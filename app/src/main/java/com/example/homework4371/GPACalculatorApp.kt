package com.example.homework4371

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework4371.ui.theme.Homework4371Theme

@Composable
fun GPACalculatorApp() {
    val context = LocalContext.current

    var course1 by remember { mutableStateOf("") }
    var course2 by remember { mutableStateOf("") }
    var course3 by remember { mutableStateOf("") }
    var course4 by remember { mutableStateOf("") }
    var course5 by remember { mutableStateOf("") }
    var gpa by remember { mutableStateOf(0.0) }
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }
    var buttonText by remember { mutableStateOf("Compute GPA") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input fields for five courses
        GPAInputField(label = "Course 1 Grade", value = course1, onValueChange = { course1 = it })
        GPAInputField(label = "Course 2 Grade", value = course2, onValueChange = { course2 = it })
        GPAInputField(label = "Course 3 Grade", value = course3, onValueChange = { course3 = it })
        GPAInputField(label = "Course 4 Grade", value = course4, onValueChange = { course4 = it })
        GPAInputField(label = "Course 5 Grade", value = course5, onValueChange = { course5 = it })

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (buttonText == "Compute GPA") {
                if (validateInputs(course1, course2, course3, course4, course5)) {
                    gpa = computeGPA(course1, course2, course3, course4, course5)
                    backgroundColor = determineBackgroundColor(gpa)
                    buttonText = "Clear Form"
                } else {
                    Toast.makeText(context, "Please enter valid grades (0-100)", Toast.LENGTH_SHORT).show()
                }
            } else {
                clearForm(
                    { course1 = "" },
                    { course2 = "" },
                    { course3 = "" },
                    { course4 = "" },
                    { course5 = "" },
                    { backgroundColor = Color.Transparent },
                    { buttonText = "Compute GPA" },
                    { gpa = 0.0 }
                )
            }
        }) {
            Text(text = buttonText)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = if (gpa > 0) "Your GPA is: %.2f".format(gpa) else "GPA will be displayed here")
    }
}



@Composable
fun GPAInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

fun validateInputs(vararg grades: String): Boolean {
    return grades.all {
        it.isNotEmpty() && it.toIntOrNull() != null && it.toInt() in 0..100
    }
}

fun computeGPA(vararg grades: String): Double {
    val gradeList = grades.map { it.toInt() }
    return gradeList.average()
}

fun determineBackgroundColor(gpa: Double): Color {
    return when {
        gpa < 60 -> Color.Red
        gpa in 60.0..79.0 -> Color.Yellow
        else -> Color.Green
    }
}

fun clearForm(
    clearCourse1: () -> Unit,
    clearCourse2: () -> Unit,
    clearCourse3: () -> Unit,
    clearCourse4: () -> Unit,
    clearCourse5: () -> Unit,
    resetBackgroundColor: () -> Unit,
    resetButtonText: () -> Unit,
    resetGPA: () -> Unit
) {
    clearCourse1()
    clearCourse2()
    clearCourse3()
    clearCourse4()
    clearCourse5()
    resetBackgroundColor()
    resetButtonText()
    resetGPA()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Homework4371Theme {
        GPACalculatorApp()
    }
}