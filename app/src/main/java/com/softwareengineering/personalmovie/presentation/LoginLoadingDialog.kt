package com.softwareengineering.personalmovie.presentation

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.repositoryImpl.AuthRepositoryImpl
import com.softwareengineering.personalmovie.databinding.DialogLoginLoadingBinding
import com.softwareengineering.personalmovie.extension.AccessState
import com.softwareengineering.personalmovie.presentation.questions.QuestionActivity
import com.softwareengineering.personalmovie.presentation.questions.QuestionViewModel
import com.softwareengineering.personalmovie.presentation.type.TypeActivity
import kotlinx.coroutines.launch

class LoginLoadingDialog(
    private val allViewModel: AllViewModel,
    private val kakaoAuthViewModel: KakaoAuthViewModel
) :DialogFragment(){
    private var _binding:DialogLoginLoadingBinding?=null
    private val binding
        get()=_binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog=Dialog(requireContext(), R.style.CustomDialog)

        dialog.setContentView(R.layout.dialog_login_loading)
        val width=resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height=resources.getDimensionPixelSize(R.dimen.dialog_height)
        dialog.window?.setLayout(width,height)
        showLoadingImage()
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=DialogLoginLoadingBinding.inflate(inflater,container,false)
        val view=binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        moveOnNext()
        return view
    }

    private fun showLoadingImage() {
        Glide.with(this).load(R.raw.loginloadinggif).into(binding.ivFrameLoading)
    }

    private fun moveOnNext() {
        lifecycleScope.launch {
            kakaoAuthViewModel.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    collectAccessState()
                } else {
                    Log.d("startactivity", "login failed")
                }
            }
        }
    }

    private suspend fun collectAccessState() {
        allViewModel.accessState.collect { accessState ->
            when (accessState) {
                is AccessState.Success -> {
                    Log.d("loginloadingdialog", "token: ${accessState.accessToken}")
                    Handler(Looper.getMainLooper()).post {
                        val intent = Intent(requireContext(), QuestionActivity::class.java)
                        startActivity(intent)
                        dismiss()
                    }
                }

                is AccessState.Error -> {
                    Log.e("startactivity", "token doesn't exist!! ${accessState.message}")
                }

                is AccessState.Loading -> {
                    // Loading 상태 처리
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}