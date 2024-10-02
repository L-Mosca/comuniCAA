package com.example.comunicaa.domain.services.firebase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseService @Inject constructor(@ApplicationContext private val context: Context) :
    FirebaseServiceContract {}