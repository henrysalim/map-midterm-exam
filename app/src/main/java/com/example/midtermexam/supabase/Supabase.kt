package com.example.midtermexam.supabase

import com.example.midtermexam.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


class Supabase {
    val supabase = createSupabaseClient(
        supabaseUrl = "${BuildConfig.SUPABASE_URL}",
        supabaseKey = "${BuildConfig.SUPABASE_ANON_KEY}"
    ) {
        install(Auth)
        install(Postgrest)
    }
}