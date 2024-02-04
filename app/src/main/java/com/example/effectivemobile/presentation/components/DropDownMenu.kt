package com.example.effectivemobile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.main_screen.catalog_screen.Sort

@Composable
fun DropDownMenu(
    sortOptions: List<Sort>,
    currentSortOption: Sort,
    onClick: (Sort) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                isExpanded = true
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_sort),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(text = currentSortOption.title)

        Spacer(modifier = Modifier.width(6.dp))

        Image(painter = painterResource(R.drawable.ic_arrow_down), contentDescription = null)

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            sortOptions.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.title)
                    },
                    onClick = {
                        onClick(it)
                        isExpanded = false
                    }
                )
            }
        }
    }
}