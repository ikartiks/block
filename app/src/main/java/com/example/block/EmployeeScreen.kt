package com.example.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.block.api.pojos.Employee
import kotlin.random.Random


@Composable
fun RacesScreen(vm: EmployeeViewModel) {
    Column(modifier = Modifier.padding(20.dp)) {
        EmployeeList(vm = vm)
    }
}

@Composable
fun EmployeeList(vm: EmployeeViewModel) {

    val data = vm.employeeFlow.collectAsState()
    when(data.value){
        is EmployeeUIState.Success ->{

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp10)),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                val list = data.value.data!!
                items(items = list.employees) { employee ->
                    EmployeeComposable(employee)
                }
            }
        }
        is EmployeeUIState.Error ->{
            AppText("Some error occurred - ${data.value.message}")
        }
        is EmployeeUIState.Loading ->{
            LoadingComposable()
        }
    }
    LaunchedEffect(key1 = Unit) {
        vm.fetchLatestData()
    }
}

@Composable
@Preview
fun LoadingComposable(){
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val strokeWidth = 5.dp
        CircularProgressIndicator(
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        Color.Blue,
                        radius = size.width / 2 - strokeWidth.toPx() / 2,
                        style = Stroke(strokeWidth.toPx())
                    )
                },
            color = Color.LightGray,
            strokeWidth = strokeWidth
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Preview
fun EmployeeComposable(
    @PreviewParameter(EmployeePreviewProvider::class) employee: Employee,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = colorResource(id = R.color.purple_200),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp10))
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(id = R.dimen.dp15)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(){
            GlideImage(
                model = employee.photoUrlSmall,
                contentDescription = employee.fullName,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dp15))
                    .width(60.dp),
            )
            Column {
                AppText(employee.fullName)
                AppText(employee.phoneNumber)
            }
        }
        AppText(employee.biography)
    }

}

class EmployeePreviewProvider : PreviewParameterProvider<Employee> {

    override val values = sequenceOf(
        Employee("${Random.nextInt()}","James Kerk","0404 878 233",
        "","","","","","full_time")
    )
}


