package com.example.effectivemobile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.effectivemobile.presentation.main_screen.catalog_screen.Tags
import com.example.effectivemobile.presentation.ui.theme.DarkBlue
import com.example.effectivemobile.presentation.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipLazyRow(
    modifier: Modifier = Modifier,
    tags: List<Tags>,
    selected: Tags,
    onSelect: (Tags) -> Unit,
    onDeselect: (Tags) -> Unit
) {
    LazyRow {
        tags.forEach { tag ->
            item {
                InputChip(
                    modifier = modifier,
                    onClick = {
                        onSelect(tag)
                    },
                    label = {
                        Text(tag.label)
                    },
                    selected = selected == tag,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = DarkBlue,
                        containerColor = LightGray,
                        selectedLabelColor = LightGray,
                        selectedTrailingIconColor = LightGray
                    ),
                    border = FilterChipDefaults.filterChipBorder(borderColor = Color.Transparent),
                    trailingIcon = {
                        if (selected == tag) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Done icon",
                                modifier = Modifier
                                    .size(FilterChipDefaults.IconSize)
                                    .clickable(
                                        indication = rememberRipple(bounded = false),
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        onDeselect(tag)
                                    }
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
