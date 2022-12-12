package com.rootstrap.android.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rootstrap.android.R

@Composable
fun LoginSuccessPage(uiState: LoginUiState,modifier: Modifier, onAnimationFinished:()->Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val transition = updateTransition(targetState = uiState.animateSuccess, label = "transition")
    val delay = 500
    val xAxisAnimation by transition.animateDp(transitionSpec = {
        tween(
            delayMillis = delay,
            durationMillis = 500
        )
    }, label = "x") { isVisible ->
        if (isVisible)
            screenWidth.dp
        else 0.dp
    }

    val yAxisAnimation by transition.animateDp(transitionSpec = {
        // spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMediumLow)
        tween(
            delayMillis = delay,
            durationMillis = 500
        )
    }, label = "x") { isVisible ->
        if (isVisible)
            screenHeight.dp
        else 0.dp
    }

    Box(
        modifier = modifier
            .width(xAxisAnimation)
            .height(yAxisAnimation)
            .background(color = Color.Green), contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = uiState.animateSuccess,
            enter = fadeIn(animationSpec = tween(delayMillis = delay+300, durationMillis = 1000)) + expandIn(),
        ) {
            Icon(painter = painterResource(R.drawable.ic_success), contentDescription = "")
        }
    }
}