package com.example.githubbrowser.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.githubbrowser.R
import com.example.githubbrowser.view.repodetail.RepoDetailCollaboratorFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException

fun makeSnackErrorRetryable(
    fragment: Fragment,
    error: Throwable,
    onClickListener: View.OnClickListener
) {
    fragment.view?.let {
        if (error is HttpException) {
            makeSnackErrorRetryable(it, errorCodeHandle(fragment, error.code()), onClickListener)
        }
    }
}

private fun makeSnackErrorRetryable(
    contentView: View,
    errorMessage: String?,
    onClickListener: View.OnClickListener
) {
    Snackbar
        .make(
            contentView,
            errorMessage ?: contentView.context.getString(R.string.error_default),
            Snackbar.LENGTH_LONG
        )
        .setAction(contentView.context.getString(R.string.error_action_retry), onClickListener)
        .show()
}

fun makeSnackError(
    fragment: Fragment,
    error: Throwable,
    onClickListener: View.OnClickListener? = null
) {
    fragment.view?.let {
        if (error is HttpException) {
            makeSnackError(it, errorCodeHandle(fragment, error.code()), onClickListener)
        }
    }
}

private fun makeSnackError(
    contentView: View,
    errorMessage: String?,
    onClickListener: View.OnClickListener? = null
) {
    Snackbar
        .make(
            contentView,
            errorMessage ?: contentView.context.getString(R.string.error_default),
            Snackbar.LENGTH_LONG
        )
        .setAction(contentView.context.getString(R.string.error_action_okay), onClickListener)
        .show()
}

private fun errorCodeHandle(fragment: Fragment, errorCode: Int): String? = when (fragment) {

    is RepoDetailCollaboratorFragment ->
        when (errorCode) {
            403 -> "無權限查看Collaborators"
            else -> null
        }

    // Use Default Error Message
    else -> when (errorCode) {
        401 -> "請先登入後再重試"
        else -> null
    }
}